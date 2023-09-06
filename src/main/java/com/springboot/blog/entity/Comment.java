package com.springboot.blog.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    private String name;

    private String email;

    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id_fk", nullable = false)
    private Post post;

    public static CommentBuilder builder() {
        return new CommentBuilder();
    }

    public static class CommentBuilder {
        private long id;
        private String name;
        private String email;
        private String body;
        private Post post;

        public CommentBuilder name(String name) {
            this.name = name;
            return this;
        }

        public CommentBuilder email(String email) {
            this.email = email;
            return this;
        }

        public CommentBuilder body(String body) {
            this.body = body;
            return this;
        }

        public CommentBuilder post(Post post) {
            this.post = post;
            return this;
        }

        public Comment build() {
            return new Comment(
                    this.id,
                    this.name,
                    this.email,
                    this.body,
                    this.post
            );
        }

        public String toString() {
            return this.id     + ", name=" +
                    this.name  + ", email=" +
                    this.email + ", body=" +
                    this.body  + ", post=" +
                    this.post;
        }
    }
}
