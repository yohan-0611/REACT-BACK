package com.avi6.blog.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO {
    private Long id;
    private String content;
    private String memberId;  
    private Long postId;    
    private String createdAt;
}
