package com.momo2x.mbdn.members.controller;

import com.momo2x.mbdn.members.constant.DateTimeConstants;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.ResourceUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.UUID;

import static com.momo2x.mbdn.members.constant.DefaultValueConstants.*;
import static org.apache.commons.codec.binary.Base64.encodeBase64String;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerAppIntegrationTest {

    private static String AVATAR_CONTENT = """
                {
                    "id": "%s",
                    "description": "%s",
                    "image": "%s"
                }
            """;

    private static String MEMBER_CONTENT = """
                {
                    "id": "%s",
                    "nickname": "%s",
                    "contactRefId": "%s",
                    "name": "%s",
                    "birthDate": "%s"
                }
            """;

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    static void createAvatarContent() throws IOException {
        final var image = ResourceUtils.getFile(AVATAR_FILE);

        try (final var inputStream = new FileInputStream(image)) {
            AVATAR_CONTENT = AVATAR_CONTENT.formatted(
                    ADMIN_ID,
                    AVATAR_DESCRIPTION,
                    encodeBase64String(inputStream.readAllBytes()));
        }
    }

    @BeforeAll
    static void createMemberJson() {
        MEMBER_CONTENT = MEMBER_CONTENT.formatted(
                ADMIN_ID,
                ADMIN_NICKNAME,
                ADMIN_ID,
                ADMIN_NAME,
                new SimpleDateFormat(DateTimeConstants.FORMAT_STRING_DATE)
                        .format(ADMIN_BIRTH_DATE));
    }

    @Test
    void whenNotAuthenticated_thenResultIsUnauthorized() throws Exception {
        this.mockMvc.perform(get("/v1/members/%s".formatted(ADMIN_ID)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void whenGetById_thenResultIsValidMember() throws Exception {
        this.mockMvc.perform(
                        get("/v1/members/%s".formatted(ADMIN_ID))
                                .with(httpBasic("user", "password")))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(APPLICATION_JSON_VALUE),
                        content().json(MEMBER_CONTENT));
    }

    @Test
    void whenGetById_thenResultIsBadRequest() throws Exception {
        this.mockMvc.perform(
                        get("/v1/members/abcde")
                                .with(httpBasic("user", "password")))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGetById_thenResultIsNotFound() throws Exception {
        this.mockMvc.perform(
                        get("/v1/members/%s".formatted(UUID.randomUUID()))
                                .with(httpBasic("user", "password")))
                .andExpect(status().isNotFound());
    }

}