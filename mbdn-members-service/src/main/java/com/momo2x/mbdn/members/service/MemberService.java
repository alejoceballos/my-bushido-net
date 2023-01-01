package com.momo2x.mbdn.members.service;

import com.momo2x.mbdn.members.exception.AvatarNotFoundException;
import com.momo2x.mbdn.members.exception.MemberNotFoundException;
import com.momo2x.mbdn.members.model.Member;
import com.momo2x.mbdn.members.model.MemberAvatar;
import com.momo2x.mbdn.members.repository.MemberAvatarRepository;
import com.momo2x.mbdn.members.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberAvatarRepository avatarRepository;

    public Member findById(final UUID id) {
        return memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
    }

    public MemberAvatar findAvatarById(final UUID id) {
        return avatarRepository.findById(id).orElseThrow(AvatarNotFoundException::new);
    }

}

