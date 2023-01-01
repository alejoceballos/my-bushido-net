package com.momo2x.mbdn.members.controller;

import com.momo2x.mbdn.members.mapping.AvatarDto;
import com.momo2x.mbdn.members.mapping.AvatarMapper;
import com.momo2x.mbdn.members.mapping.MemberDto;
import com.momo2x.mbdn.members.mapping.MemberMapper;
import com.momo2x.mbdn.members.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/v1/members")
@RequiredArgsConstructor
class MemberController {

    private final MemberService service;

    private final MemberMapper memberMapper;

    private final AvatarMapper avatarMapper;

    @GetMapping(value = "/{id}")
    public MemberDto getById(@PathVariable final UUID id) {
        return memberMapper.toMemberDto(service.findById(id));
    }

    @GetMapping(value = "/{id}/avatar")
    public AvatarDto getAvatarById(@PathVariable final UUID id) {
        return avatarMapper.toAvatarDto(service.findAvatarById(id));
    }

}