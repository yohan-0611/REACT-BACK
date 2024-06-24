package com.avi6.blog.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.avi6.blog.dto.MemberDTO;
import com.avi6.blog.model.Member;
import com.avi6.blog.repository.MemberRepository;


@Service
public class UserService {

    @Autowired
    private MemberRepository userRepository;


    public Member save(MemberDTO memberDTO) {
    	Member member = new Member();
    	member.setMemberId(memberDTO.getMemberId());
        member.setPassword(memberDTO.getPassword());
        member.setRoles(Collections.singletonList("USER"));
        return userRepository.save(member);
    }
    
    
    
   
}
