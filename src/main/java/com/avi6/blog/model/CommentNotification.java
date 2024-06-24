package com.avi6.blog.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentNotification {

    private String postId;
    private String message;

    // 생성자, getter, setter 등 필요한 메서드들

    public CommentNotification() {
        // 기본 생성자
    }

    public CommentNotification(String postId, String message) {
        this.postId = postId;
        this.message = message;
    }

    // getter, setter 생략
}
