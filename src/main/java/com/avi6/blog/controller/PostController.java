package com.avi6.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.avi6.blog.dto.CommentDTO;
import com.avi6.blog.model.Post;
import com.avi6.blog.service.CommentService;
import com.avi6.blog.service.PostService;

import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000") 
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    @Autowired
    private PostService postService;
    
    private final CommentService commentService;
    
    @Value("${com.avi6.upload.path}")
    private String imageUploadDir;

    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/{id}")
    public Post getPostById(@PathVariable("id") Long id) {
        return postService.getPostById(id);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Post createPost(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("author") String author,
            @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {

        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setAuthor(author);

        if (image != null && !image.isEmpty()) {
            // 이미지 파일 저장
            String originalFilename = StringUtils.cleanPath(image.getOriginalFilename());
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFilename = "image_" + System.currentTimeMillis() + fileExtension;

            String imagePath = imageUploadDir + File.separator + newFilename;
            Files.createDirectories(Paths.get(imageUploadDir));
            image.transferTo(new File(imagePath));

            post.setImageUrl(newFilename);
        }

        return postService.savePost(post);
    }



    @PutMapping("/{id}")
    public Post updatePost(@PathVariable("id") Long id, @RequestBody Post post) {
        return postService.updatePost(id, post);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable("id") Long id) {
        postService.deletePost(id);
    }
    
    @PostMapping("/{postId}/comments")
    public ResponseEntity<CommentDTO> createComment(@PathVariable("postId")  Long postId, @RequestBody CommentDTO commentDTO) {
        commentDTO.setPostId(postId);
        System.out.println(commentDTO.getContent() + 123333 + commentDTO.getMemberId());
        
        
        
        CommentDTO createdComment = commentService.createComment(commentDTO);
        return ResponseEntity.ok(createdComment);
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<CommentDTO>> getCommentsByPostId(@PathVariable("postId") Long postId) {

        List<CommentDTO> comments = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }
    
    @DeleteMapping("/{postId}/comments/{commentId}")
    public void deletecomments(@PathVariable("postId") Long id ,@PathVariable("commentId") Long commentId ) {
    	System.out.println("3333");
    	commentService.deleteComment(id,commentId);
    }
}