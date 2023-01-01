package com.momo2x.mbdn.members.repository;

import com.momo2x.mbdn.members.model.Member;
import com.momo2x.mbdn.members.model.MemberContact;
import com.momo2x.mbdn.members.security.MemberUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@SpringBootTest
class MemberRepositoryTest {

    private static final String TEST_NICKNAME = "UNIQUETESTNICKNAME";

    private static final String DML_SELECT_AUD = """
            SELECT rev FROM member_aud WHERE nickname = ?
            """;

    private static final String DML_DELETE_REV = """
            DELETE FROM audit_revision_info WHERE id = ?
            """;

    private static final String DML_DELETE_AUD = """
            DELETE FROM member_aud WHERE rev = ?
            """;

    private static final String DML_DELETE_MEMBER = """
            DELETE FROM member WHERE nickname = ?
            """;

    private static final String DML_SELECT = """
            SELECT * FROM member_aud
                INNER JOIN audit_revision_info
                    ON audit_revision_info.id = member_aud.rev
            WHERE member_aud.id = ?
            ORDER BY member_aud.id
            , member_aud.rev
            """;

    @Autowired
    private MemberRepository repository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @MockBean
    private MemberUserDetailsService userDetailsService;

    @BeforeEach
    void deleteTestData() {
        var revData = jdbcTemplate.queryForList(DML_SELECT_AUD, TEST_NICKNAME);

        if (!revData.isEmpty()) {
            for (var row : revData) {
                var rev = row.get("rev");
                jdbcTemplate.update(DML_DELETE_AUD, rev);
                jdbcTemplate.update(DML_DELETE_REV, rev);
            }

            jdbcTemplate.update(DML_DELETE_MEMBER, TEST_NICKNAME);
        }
    }

    @BeforeEach
    void mockUserDetails() {
        reset(userDetailsService);

        when(userDetailsService.getUsername()).thenReturn("user");
    }

    @Test
    void auditMember() {
        final Member savedMember = repository.save(Member.builder()
                .nickname(TEST_NICKNAME)
                .contact(MemberContact.builder()
                        .name("Whatever")
                        .birthDate(new Date())
                        .address("Whenever")
                        .email("nobody@nowhere.com")
                        .phone("+000000000000")
                        .build())
                .build());

        final Member updatedMember = repository.save(Member.builder()
                .id(savedMember.getId())
                .nickname(TEST_NICKNAME)
                .contact(MemberContact.builder()
                        .name("New Whatever")
                        .birthDate(new Date())
                        .address("New Whenever")
                        .email("newnobody@newnowhere.com")
                        .phone("+000000000001")
                        .build())
                .build());

        updatedMember.getContact().setName("Another new whatever");

        repository.save(updatedMember);

        repository.delete(Member.builder().id(updatedMember.getId()).build());

        var audData = jdbcTemplate.queryForList(DML_SELECT, savedMember.getId());

        var expected = List.of(0, 1, 1, 2);

        assertThat(audData.size(), equalTo(expected.size()));

        var revTypes = audData.stream()
                .map(item -> item.get("revtype"))
                .sorted()
                .collect(Collectors.toList());

        assertThat(expected, equalTo(revTypes));
    }

}