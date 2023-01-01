package com.momo2x.mbdn.members.repository;

import com.momo2x.mbdn.members.model.MemberAvatar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MemberAvatarRepository extends JpaRepository<MemberAvatar, UUID> {
}
