package com.momo2x.mbdn.members.repository;

import com.momo2x.mbdn.members.model.Member;
import com.momo2x.mbdn.members.model.MemberAvatar;
import com.momo2x.mbdn.members.model.MemberContact;
import com.momo2x.mbdn.members.security.MemberUserDetailsService;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static com.momo2x.mbdn.members.constant.DefaultValueConstants.AVATAR_FILE;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@SpringBootTest
class MemberRepositoryTest {

    private static final String TEST_NICKNAME = "UNIQUETESTNICKNAME";

    private static final String TEST_AVATAR_DESC = "UNIQUEAVATARDESCRIPTION";

    private static final String DML_SELECT_AVATAR_AUD = """
            SELECT id, rev FROM member_avatar_aud WHERE description LIKE ?
            """;

    private static final String DML_DELETE_AVATAR_AUD = """
            DELETE FROM member_avatar_aud WHERE id IN (:ids)
            """;

    private static final String DML_DELETE_AVATAR = """
            DELETE FROM member_avatar WHERE description LIKE ?
            """;

    private static final String DML_SELECT_MEMBER_AUD = """
            SELECT id, rev FROM member_aud WHERE nickname LIKE ?
            """;

    private static final String DML_DELETE_MEMBER_AUD = """
            DELETE FROM member_aud WHERE id IN (:ids)
            """;

    private static final String DML_DELETE_MEMBER = """
            DELETE FROM member WHERE nickname LIKE ?
            """;

    private static final String DML_DELETE_REV_INFO = """
            DELETE FROM audit_revision_info WHERE id IN (:ids)
            """;

    private static final String DML_SELECT_MEMBER_REV = """
            SELECT * FROM member_aud
                INNER JOIN audit_revision_info
                    ON audit_revision_info.id = member_aud.rev
            WHERE member_aud.id = ?
            ORDER BY member_aud.id
            , member_aud.rev
            """;

    private static final String DML_SELECT_AVATAR_REV = """
            SELECT * FROM member_avatar_aud
                INNER JOIN audit_revision_info
                    ON audit_revision_info.id = member_avatar_aud.rev
            WHERE member_avatar_aud.id = ?
            ORDER BY member_avatar_aud.id
            , member_avatar_aud.rev
            """;

    private static byte[] IMAGE;

    @Autowired
    private MemberRepository repository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @MockBean
    private MemberUserDetailsService userDetailsService;

    @BeforeAll
    static void loadImage() throws IOException {
        final var image = ResourceUtils.getFile(AVATAR_FILE);

        try (final var inputStream = new FileInputStream(image)) {
            IMAGE = inputStream.readAllBytes();
        }
    }

    @BeforeEach
    void deleteTestData() {
        var avatarRevs = deleteAndGetRevs(
                TEST_AVATAR_DESC,
                DML_SELECT_AVATAR_AUD,
                DML_DELETE_AVATAR_AUD,
                DML_DELETE_AVATAR);
        var memberRevs = deleteAndGetRevs(
                TEST_NICKNAME,
                DML_SELECT_MEMBER_AUD,
                DML_DELETE_MEMBER_AUD,
                DML_DELETE_MEMBER);

        final var revs = Stream
                .concat(memberRevs.stream(), avatarRevs.stream())
                .distinct()
                .toList();

        if (!revs.isEmpty()) {
            namedParameterJdbcTemplate.update(
                    DML_DELETE_REV_INFO,
                    new MapSqlParameterSource("ids", revs));
        }
    }

    @BeforeEach
    void mockUserDetails() {
        reset(userDetailsService);
        when(userDetailsService.getUsername()).thenReturn("user");
    }

    @Test
    void auditMember() {
        // 1. Create Member with Image (revtype 0 for member and avatar aud tables)

        final var savedMember = repository.save(Member.builder()
                .nickname(TEST_NICKNAME)
                .contact(MemberContact.builder()
                        .name("Whatever")
                        .birthDate(new Date())
                        .address("Wherever")
                        .email("nobody@nowhere.com")
                        .phone("+000000000000")
                        .build())
                .avatar(MemberAvatar.builder()
                        .image(IMAGE)
                        .description(TEST_AVATAR_DESC)
                        .build())
                .build());

        // 2. Updates contact and avatar data (revtype 1 for member and avatar aud tables)

        final var contact = savedMember.getContact();
        contact.setName("Updated Whatever");
        contact.setBirthDate(new Date());
        contact.setAddress("Updated Wherever");
        contact.setEmail("updatednobody@updatednowhere.com");
        contact.setPhone("+000000000001");

        final var savedAvatar = savedMember.getAvatar();
        savedAvatar.setDescription("Updated " + TEST_AVATAR_DESC);

        final var updatedMember = repository.save(Member.builder()
                .id(savedMember.getId())
                .nickname(savedMember.getNickname())
                .contact(contact)
                .avatar(savedAvatar)
                .build());

        // 3. Updates contact data only (revtype 1 for member aud table)

        updatedMember.getContact().setName("Another Updated whatever");

        repository.save(updatedMember);

        // 4. Deletes member (revtype 2 for member aud table. Avatar remains untouched)

        repository.delete(Member.builder().id(updatedMember.getId()).build());

        // Assertions

        final var memberRevTypes = getRevTypes(savedMember.getId(), DML_SELECT_MEMBER_REV);
        assertThat(memberRevTypes, equalTo(List.of(0, 1, 1, 2)));

        final var avatarRevTypes = getRevTypes(savedAvatar.getId(), DML_SELECT_AVATAR_REV);
        assertThat(avatarRevTypes, equalTo(List.of(0, 1)));
    }

    private List<Integer> deleteAndGetRevs(
            String selectFilter,
            String dmlSelectAud,
            String dmlDeleteAud,
            String dmlDeleteMain) {

        final var filterWithLike = "%%%s%%".formatted(selectFilter);
        final var avatarRevData = jdbcTemplate.queryForList(dmlSelectAud, filterWithLike);
        List<Integer> revs = new ArrayList<>();

        if (!avatarRevData.isEmpty()) {
            revs = avatarRevData.stream().map(map -> (int) map.get("rev")).distinct().toList();
            var ids = avatarRevData.stream().map(map -> (UUID) map.get("id")).distinct().toList();
            namedParameterJdbcTemplate.update(dmlDeleteAud, new MapSqlParameterSource("ids", ids));
        }

        jdbcTemplate.update(dmlDeleteMain, filterWithLike);

        return revs;
    }

    private List<Object> getRevTypes(UUID id, String dmlSelectMemberRev) {
        var audData = jdbcTemplate.queryForList(dmlSelectMemberRev, id);

        return audData.stream()
                .map(item -> item.get("revtype"))
                .sorted()
                .collect(toList());
    }

}