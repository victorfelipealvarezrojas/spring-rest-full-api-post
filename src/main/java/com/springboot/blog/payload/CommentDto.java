package com.springboot.blog.payload;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import lombok.Data;

@Data
public class CommentDto {
    private long id;

    private String name;

    private String email;

    private String body;

    private Post post;

    public CommentDto(long id, String name, String email, String body, Post post) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.body = body;
        this.post = post;
    }

    public static CommentDtoBuilder builder() {
        return new CommentDtoBuilder();
    }

    public static class CommentDtoBuilder {
        private long id;
        private String name;
        private String email;
        private String body;
        private Post post;

        public CommentDtoBuilder id(long id) {
            this.id = id;
            return this;
        }



        public CommentDtoBuilder name(String name) {
            this.name = name;
            return this;
        }

        public CommentDtoBuilder email(String email) {
            this.email = email;
            return this;
        }

        public CommentDtoBuilder body(String body) {
            this.body = body;
            return this;
        }

        public CommentDtoBuilder post(Post post) {
            this.post = post;
            return this;
        }

        public CommentDto build() {
            return new CommentDto(
                    this.id,
                    this.name,
                    this.email,
                    this.body,
                    this.post
            );
        }

        public String toString() {
            return this.id + ", name=" +
                    this.name + ", email=" +
                    this.email + ", body=" +
                    this.body +
                    ", post=" + this.post;

        }
    }
}
