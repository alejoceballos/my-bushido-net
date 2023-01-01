package com.momo2x.mbdn.members.repository;

import com.momo2x.mbdn.members.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MemberRepository extends JpaRepository<Member, UUID> {
}
