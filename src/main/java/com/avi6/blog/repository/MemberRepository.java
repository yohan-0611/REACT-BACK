package com.avi6.blog.repository;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;

import com.avi6.blog.model.Member;


public interface MemberRepository extends JpaRepository<Member, String> {
	 Optional<Member> findByMemberId(String username);
	 boolean existsByMemberId(String memberId);
}
