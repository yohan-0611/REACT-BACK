package com.avi6.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.avi6.blog.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	
}