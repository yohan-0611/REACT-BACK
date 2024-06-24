package com.avi6.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avi6.blog.dto.MemberDTO;
import com.avi6.blog.dto.MemberLoginRequestDto;
import com.avi6.blog.dto.TokenInfo;
import com.avi6.blog.model.Member;
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
    
    
    
    private final MemberService memberService;	


    @PostMapping("/register")
    public ResponseEntity<Member> registerUser(@RequestBody MemberDTO memberDTO) {
    
    	Member member = userService.save(memberDTO);
        return ResponseEntity.ok(member);

}
    
    @PostMapping("/login")  
    public TokenInfo login(@RequestBody MemberDTO memberDTO) {
        String memberId = memberDTO.getMemberId();
        String password = memberDTO.getPassword();
        TokenInfo tokenInfo = memberService.login(memberId, password);
        return tokenInfo;
    }
     
    

}
