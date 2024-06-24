package com.avi6.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avi6.blog.dto.ChangePasswordRequestDTO;
import com.avi6.blog.dto.MemberDTO;
import com.avi6.blog.dto.MemberLoginRequestDto;
import com.avi6.blog.dto.TokenInfo;
import com.avi6.blog.model.Member;
import com.avi6.blog.repository.MemberRepository;
import com.avi6.blog.security.jwt.JwtUtils;
import com.avi6.blog.service.MemberService;
import com.avi6.blog.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@CrossOrigin(origins = {"http://localhost:3000", "http://192.168.0.32:3000"})  
@RequestMapping("/api/auth")
@RestController
@RequiredArgsConstructor
public class RegisterController {

    @Autowired
    private UserService userService;
    
    private final MemberRepository memberRepository;
    
    
    
    private final MemberService memberService;	


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody MemberDTO memberDTO) {
        // 사용자가 입력한 회원 정보를 DTO로부터 추출
        String memberId = memberDTO.getMemberId();
        String password = memberDTO.getPassword();
        
        // 아이디 중복 검사
        if (memberRepository.existsByMemberId(memberId)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("이미 사용 중인 아이디입니다.");
        }
        
        // 중복이 없을 경우 회원 등록
        Member member = userService.save(memberDTO);
        return ResponseEntity.ok(member);
    }

    
    @PostMapping("/login")  
    public TokenInfo login(@RequestBody MemberDTO memberDTO) {
        String memberId = memberDTO.getMemberId();
        String password = memberDTO.getPassword();
        System.out.println(password + "wwww");	
        TokenInfo tokenInfo = memberService.login(memberId, password);
        return tokenInfo;
    }
    
    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequestDTO request) {
    	System.out.println("asdsd");
        memberService.changePassword(request.getMemberId(), request.getCurrentPassword(), request.getNewPassword());
        return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
    }
     
    

}
