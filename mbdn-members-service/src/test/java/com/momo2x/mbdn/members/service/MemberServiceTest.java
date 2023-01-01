package com.momo2x.mbdn.members.service;

import com.momo2x.mbdn.members.model.Member;
import com.momo2x.mbdn.members.model.MemberAvatar;
import com.momo2x.mbdn.members.model.MemberContact;
import jakarta.transaction.Transactional;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ResourceUtils;

import java.io.FileInputStream;
import java.io.IOException;

import static com.momo2x.mbdn.members.constant.DefaultValueConstants.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

@SpringBootTest
class MemberServiceTest {

    private static MemberAvatar avatarSubject;
    private static Member memberSubject;

    @Autowired
    private MemberService subject;

    @BeforeAll
    static void createAvatarSubject() throws IOException {
        final var image = ResourceUtils.getFile(AVATAR_FILE);

        try (final var inputStream = new FileInputStream(image)) {
            final var imageBytes = inputStream.readAllBytes();

            avatarSubject = MemberAvatar.builder()
                    .id(ADMIN_ID)
                    .description(AVATAR_DESCRIPTION)
                    .image(imageBytes)
                    .build();
        }
    }

    @BeforeAll
    static void createAssertionSubjects() {
        memberSubject = Member.builder()
                .id(ADMIN_ID)
                .nickname(ADMIN_NICKNAME)
                .avatar(avatarSubject)
                .contact(MemberContact.builder()
                        .refId(ADMIN_ID)
                        .name(ADMIN_NAME)
                        .birthDate(ADMIN_BIRTH_DATE)
                        .build())
                .build();
    }

    @Test
    @Transactional
    void findByIdAdmin_WithAvatarInTransaction() {
        assertThat(subject.findById(ADMIN_ID), equalTo(memberSubject));
    }

    @Test
    void findByIdAdminWithAvatarOutOfTransaction() {
        Assertions.assertThrows(
                LazyInitializationException.class,
                () -> {
                    var member = subject.findById(ADMIN_ID);
                    assertThat(member.getAvatar().getImage().length, greaterThan(0));
                });
    }

    @Test
    void testFindAvatarById() {
        assertThat(subject.findAvatarById(ADMIN_ID), equalTo(avatarSubject));
    }

}