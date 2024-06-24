package com.avi6.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.avi6.blog.model.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
