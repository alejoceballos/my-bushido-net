package com.momo2x.mbdn.members.controller;

import com.momo2x.mbdn.members.mapping.AvatarDto;
import com.momo2x.mbdn.members.mapping.AvatarMapper;
import com.momo2x.mbdn.members.mapping.MemberDto;
import com.momo2x.mbdn.members.mapping.MemberMapper;
import com.momo2x.mbdn.members.model.Member;
import com.momo2x.mbdn.members.model.MemberAvatar;
import com.momo2x.mbdn.members.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class MemberControllerWebLayerIntegrationTest {

    private static final String DUMMY_STR = "DUMMY";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService service;

    @MockBean
    private MemberMapper memberMapper;

    @MockBean
    private AvatarMapper avatarMapper;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private SecurityFilterChain securityFilterChain;

    @BeforeEach
    void mockMappers() {
        reset(avatarMapper, memberMapper);

        when(memberMapper.toMemberDto(any()))
                .thenReturn(
                        new MemberDto(
                                DUMMY_STR,
                                DUMMY_STR,
                                DUMMY_STR,
                                DUMMY_STR,
                                DUMMY_STR,
                                DUMMY_STR,
                                DUMMY_STR,
                                DUMMY_STR,
                                DUMMY_STR));

        when(avatarMapper.toAvatarDto(any()))
                .thenReturn(
                        new AvatarDto(
                                DUMMY_STR,
                                DUMMY_STR,
                                new byte[0]));
    }

    @BeforeEach
    void resetSecurity() {
        reset(userDetailsService, securityFilterChain);
    }

    @BeforeEach
    void mockService() {
        reset(service);

        when(service.findById(any())).thenReturn(Member.builder().build());
        when(service.findAvatarById(any())).thenReturn(MemberAvatar.builder().build());
    }

    @Test
    void whenGetById_thenResult200() throws Exception {
        this.mockMvc.perform(get("/v1/members/%s".formatted(UUID.randomUUID().toString()))
                        .with(httpBasic("user", "password")))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(APPLICATION_JSON_VALUE));
    }

    @Test
    void whenGetById_thenResult400() throws Exception {
        this.mockMvc.perform(get("/v1/members/abcde")
                        .with(httpBasic("user", "password")))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenGetAvatarById_thenResult200() throws Exception {
        this.mockMvc.perform(get("/v1/members/%s/avatar".formatted(UUID.randomUUID().toString()))
                        .with(httpBasic("user", "password")))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(APPLICATION_JSON_VALUE));
    }

}