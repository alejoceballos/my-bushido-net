package com.momo2x.mbdn.contacts.repository;

import com.momo2x.mbdn.contacts.constant.DefaultValueConstants;
import com.momo2x.mbdn.contacts.model.*;
import com.momo2x.mbdn.contacts.security.ContactUserDetailsService;
import jakarta.transaction.Transactional;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.util.ResourceUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

import static com.momo2x.mbdn.contacts.model.DocumentType.NATIONAL_ID_NUMBER;
import static com.momo2x.mbdn.contacts.model.DocumentType.PASSPORT;
import static java.util.Calendar.JANUARY;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@SpringBootTest
class ContactRepositoryTest {

    private static final String TEST_UNIQUE_NAME = "UNIQUECONTACTFIRSTNAME";

    private static final String TEST_UNIQUE_EMAIL = "uniquetestemail@nonexistingcompany.com.zz";
    private static final String DML_SELECT = """
            SELECT DISTINCT
                   c_aud.id       AS contact_id
                 , c_aud.rev      AS contact_rev
                 , c_aud.revtype  AS contact_revtype
                 , p_aud.rev      AS person_rev
                 , co_aud.rev     AS company_rev
                 , d_aud.id       AS doc_id
                 , d_aud.rev      AS doc_rev
                 , d_aud.revtype  AS doc_revtype
                 , pn_aud.id      AS phone_id
                 , pn_aud.rev     AS phone_rev
                 , pn_aud.revtype AS phone_revtype
            FROM contact_aud c_aud
                 LEFT OUTER JOIN person_aud p_aud
                    ON c_aud.id = p_aud.id
                 LEFT OUTER JOIN company_aud co_aud
                    ON c_aud.id = co_aud.id
                 LEFT OUTER JOIN contact_document_aud d_aud
                    ON d_aud.contact_id = c_aud.id
                 LEFT OUTER JOIN contact_phone_number_aud pn_aud
                    ON pn_aud.contact_id = c_aud.id
            WHERE c_aud.id IN (SELECT id from contact_aud WHERE email LIKE ?)
            """;
    private static final String DML_DELETE = """
            DELETE FROM %s WHERE %s IN (:values)
            """;
    private static byte[] IMAGE;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private ContactRepository repository;

    @MockBean
    private ContactUserDetailsService userDetailsService;

    @BeforeAll
    static void loadImage() throws IOException {
        final var image = ResourceUtils.getFile(DefaultValueConstants.ADMIN_PHOTO_FILE);

        try (final var inputStream = new FileInputStream(image)) {
            IMAGE = inputStream.readAllBytes();
        }
    }

    private static Contact createDefaultContact() {
        return Person.builder()
                .firstName(TEST_UNIQUE_NAME)
                .middleName("Middle")
                .lastName("Last")
                .email(TEST_UNIQUE_EMAIL)
                .birthDate(new GregorianCalendar(2023, JANUARY, 7).getTime())
                .referenceContact("Reference Contact")
                .photo(IMAGE)
                .address(Address.builder()
                        .street("Street")
                        .number("Number")
                        .complement1("Complement 1")
                        .complement2("Complement 2")
                        .city("City")
                        .zipCode("Zip Code")
                        .country("Country 1")
                        .build())
                .documents(List.of(
                        Document.builder()
                                .type(PASSPORT)
                                .number("Passport Number")
                                .description("Temporary Passport")
                                .country("Country 2")
                                .build(),
                        Document.builder()
                                .type(NATIONAL_ID_NUMBER)
                                .number("National ID Number")
                                .country("Country 1")
                                .build()
                ))
                .phoneNumbers(List.of(
                        PhoneNumber.builder()
                                .countryCode("000")
                                .areaCode("00")
                                .number("000000000")
                                .main(true)
                                .build(),
                        PhoneNumber.builder()
                                .countryCode("001")
                                .areaCode("01")
                                .number("000000001")
                                .build()
                ))
                .build();
    }

    @BeforeEach
    @AfterEach
    void deleteTestData() {
        final var idsAndRevs = getRelatedIdsAndRevs();

        if (idsAndRevs.isEmpty()) {
            return;
        }

        var contactIds = (Set<UUID>) idsAndRevs.get("contactId");
        var documentIds = (Set<UUID>) idsAndRevs.get("documentId");
        var phoneNumberIds = (Set<UUID>) idsAndRevs.get("phoneNumberId");
        var revs = (Set<Integer>) idsAndRevs.get("contactRev");
        revs.addAll((Set<Integer>) idsAndRevs.get("documentRev"));
        revs.addAll((Set<Integer>) idsAndRevs.get("phoneNumberRev"));

        Stream.of(
                        Map.of("table", "contact_phone_number_aud", "column", "id", "values", phoneNumberIds),
                        Map.of("table", "phone_number_aud", "column", "id", "values", phoneNumberIds),
                        Map.of("table", "phone_number", "column", "id", "values", phoneNumberIds),
                        Map.of("table", "contact_document_aud", "column", "id", "values", documentIds),
                        Map.of("table", "document_aud", "column", "id", "values", documentIds),
                        Map.of("table", "document", "column", "id", "values", documentIds),
                        Map.of("table", "person_aud", "column", "id", "values", contactIds),
                        Map.of("table", "person", "column", "id", "values", contactIds),
                        Map.of("table", "company_aud", "column", "id", "values", contactIds),
                        Map.of("table", "company", "column", "id", "values", contactIds),
                        Map.of("table", "contact_aud", "column", "id", "values", contactIds),
                        Map.of("table", "contact", "column", "id", "values", contactIds),
                        Map.of("table", "audit_revision_info", "column", "id", "values", revs))
                .forEach(params -> {
                    var values = (Set<Integer>) params.get("values");

                    if (!values.isEmpty()) {
                        namedParameterJdbcTemplate.update(
                                DML_DELETE.formatted(params.get("table"), params.get("column")),
                                new MapSqlParameterSource("values", values));
                    }
                });
    }

    @BeforeEach
    void mockUserDetails() {
        reset(userDetailsService);
        when(userDetailsService.getUsername()).thenReturn("user");
    }

    @Test
    @Transactional
    void saveAndLoadPersonInTransactionalContext() {
        final var contact = createDefaultContact();
        final var savedPerson = (Person) repository.save(contact);
        final var loadedPerson = (Person) repository.findById(savedPerson.getId()).orElse(null);

        assertThat(contact, sameInstance(loadedPerson));
    }

    @Test
    void saveAndLoadPersonNotInTransactionalContext() {
        final var contact = createDefaultContact();
        final var savedPerson = (Person) repository.save(contact);
        final var loadedPerson = (Person) repository.findById(savedPerson.getId()).orElse(null);

        assertThat(contact, sameInstance(savedPerson));
        assertThat(savedPerson, not(sameInstance(loadedPerson)));
        assertThrows(LazyInitializationException.class, () -> loadedPerson.getPhoneNumbers().get(0));
        assertThrows(LazyInitializationException.class, () -> loadedPerson.getDocuments().get(0));
    }

    @Test
    void auditTest() {
        final var contact = createDefaultContact();
        final var savedPerson = (Person) repository.save(contact);

        savedPerson.setEmail("OTHER" + savedPerson.getEmail());
        final var updatedPerson1 = (Person) repository.save(savedPerson);

        updatedPerson1.setFirstName("OTHER" + updatedPerson1.getFirstName());
        final var updatedPerson2 = (Person) repository.save(updatedPerson1);

        repository.delete(updatedPerson2);

        final var idsAndRevs = getRelatedIdsAndRevs();

        assertThat(idsAndRevs.get("contactId").size(), is(equalTo(1)));
        assertThat(idsAndRevs.get("contactRev").size(), is(equalTo(4)));
        assertThat(idsAndRevs.get("contactRevType"), equalTo(List.of(0, 1, 2)));
        assertThat(idsAndRevs.get("personRev").size(), is(equalTo(4)));
        assertThat(idsAndRevs.get("companyRev").size(), is(equalTo(0)));
        assertThat(idsAndRevs.get("documentId").size(), is(equalTo(2)));
        assertThat(idsAndRevs.get("documentRevType"), equalTo(List.of(0, 2)));
        assertThat(idsAndRevs.get("phoneNumberId").size(), is(equalTo(2)));
        assertThat(idsAndRevs.get("phoneNumberRevType"), equalTo(List.of(0, 2)));
    }

    private Map<String, Collection<?>> getRelatedIdsAndRevs() {
        final var filterWithLike = "%%%s%%".formatted(TEST_UNIQUE_EMAIL);
        final var avatarRevData = jdbcTemplate.queryForList(DML_SELECT, filterWithLike);

        if (avatarRevData.isEmpty()) {
            return new HashMap<>();
        }

        Set<UUID> contactIds = new HashSet<>();
        Set<Integer> contactRevs = new HashSet<>();
        Set<Integer> contactRevTypes = new HashSet<>();
        Set<Integer> personRevs = new HashSet<>();
        Set<Integer> companyRevs = new HashSet<>();
        Set<UUID> documentIds = new HashSet<>();
        Set<Integer> documentRevs = new HashSet<>();
        Set<Integer> documentRevTypes = new HashSet<>();
        Set<UUID> phoneNumberIds = new HashSet<>();
        Set<Integer> phoneNumberRevs = new HashSet<>();
        Set<Integer> phoneNumberRevTypes = new HashSet<>();

        avatarRevData.forEach(row -> {
            if (!Objects.isNull(row.get("contact_id"))) contactIds.add((UUID) row.get("contact_id"));
            if (!Objects.isNull(row.get("contact_rev"))) contactRevs.add((Integer) row.get("contact_rev"));
            if (!Objects.isNull(row.get("contact_revtype"))) contactRevTypes.add((Integer) row.get("contact_revtype"));
            if (!Objects.isNull(row.get("person_rev"))) personRevs.add((Integer) row.get("person_rev"));
            if (!Objects.isNull(row.get("company_rev"))) companyRevs.add((Integer) row.get("person_rev"));
            if (!Objects.isNull(row.get("doc_id"))) documentIds.add((UUID) row.get("doc_id"));
            if (!Objects.isNull(row.get("doc_rev"))) documentRevs.add((Integer) row.get("doc_rev"));
            if (!Objects.isNull(row.get("doc_revtype"))) documentRevTypes.add((Integer) row.get("doc_revtype"));
            if (!Objects.isNull(row.get("phone_id"))) phoneNumberIds.add((UUID) row.get("phone_id"));
            if (!Objects.isNull(row.get("phone_rev"))) phoneNumberRevs.add((Integer) row.get("phone_rev"));
            if (!Objects.isNull(row.get("phone_revtype"))) phoneNumberRevTypes.add((Integer) row.get("phone_revtype"));
        });

        return new HashMap<>() {{
            put("contactId", contactIds);
            put("contactRev", contactRevs);
            put("contactRevType", contactRevTypes.stream().sorted().toList());
            put("personRev", personRevs);
            put("companyRev", companyRevs);
            put("documentId", documentIds);
            put("documentRev", documentRevs);
            put("documentRevType", documentRevTypes.stream().sorted().toList());
            put("phoneNumberId", phoneNumberIds);
            put("phoneNumberRev", phoneNumberRevs);
            put("phoneNumberRevType", phoneNumberRevTypes.stream().sorted().toList());
        }};
    }

}