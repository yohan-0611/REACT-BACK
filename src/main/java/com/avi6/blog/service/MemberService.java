package com.avi6.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.avi6.blog.dto.ChangePasswordRequestDTO;
import com.avi6.blog.dto.TokenInfo;
import com.avi6.blog.model.Member;
import com.avi6.blog.repository.MemberRepository;
import com.avi6.blog.security.jwt.JwtTokenProvider;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
	
	   @Autowired
	    private PasswordEncoder passwordEncoder;
 
    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
 
    @Transactional
    public TokenInfo login(String memberId, String password) {
        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberId, password);
 
        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
 
        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);
 
        return tokenInfo;
    }
    
    @Transactional
    public void changePassword(String memberId, String currentPassword, String newPassword) {
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원을 찾을 수 없습니다."));
        System.out.println(member.getPassword() + currentPassword + "ㅋㅋㅋㅋ");
        if (!currentPassword.equals(member.getPassword())) {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }

        member.setPassword(newPassword);

        memberRepository.save(member);
    }
}
