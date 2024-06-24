package com.avi6.blog.service;

import com.avi6.blog.dto.CommentDTO;
import com.avi6.blog.model.Comment;
import com.avi6.blog.model.Member;
import com.avi6.blog.model.Post;
import com.avi6.blog.repository.CommentRepository;
import com.avi6.blog.repository.MemberRepository;
import com.avi6.blog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public CommentDTO createComment(CommentDTO commentDTO) {
    	
        Post post = postRepository.findById(commentDTO.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid post ID"));
        
        System.out.println(commentDTO.getMemberId() + "123123");
        
        Member member = memberRepository.findById(commentDTO.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));
        

        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setPost(post);
        comment.setMember(member);

        Comment savedComment = commentRepository.save(comment);

        return mapToDTO(savedComment);
    }

    public List<CommentDTO> getCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        
        System.out.println(comments + "qwe");
        return comments.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private CommentDTO mapToDTO(Comment comment) {
        CommentDTO dto = new CommentDTO();
        dto.setId(comment.getId());
        dto.setContent(comment.getContent());
        dto.setMemberId(comment.getMember().getMemberId());
        dto.setPostId(comment.getPost().getId());
        dto.setCreatedAt(comment.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return dto;
    }
    
    public boolean deleteComment(Long postId, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid comment ID"));

        if (comment.getPost().getId().equals(postId)) {
            commentRepository.delete(comment);
            return true; 
        } else {
            return false; 
        }
    }
    
    public boolean deleteByPostId(Long postId) {
        // 해당 포스트 ID에 해당하는 모든 댓글을 가져옵니다.
        List<Comment> comments = commentRepository.findByPostId(postId);

        // 가져온 댓글들을 하나씩 삭제합니다.
        for (Comment comment : comments) {
            commentRepository.delete(comment);
        }

        return true; // 삭제 성공
    }
    }

