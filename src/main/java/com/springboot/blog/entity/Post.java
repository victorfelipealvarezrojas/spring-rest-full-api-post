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
@Table(
        name = "post",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "title")
        }
)
public class Post {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "description", nullable = false, length = 100)
    private String description;

    @Column(name = "content", nullable = false, length = 200)
    private String content;

    public static PostBuilder builder() {
        return new PostBuilder();
    }

    public static class PostBuilder {
        private Long id;
        private String title;
        private String description;
        private String content;

        /*public PostBuilder id(Long id) {
            this.id = id;
            return this;
        }*/

        public PostBuilder title(String title) {
            this.title = title;
            return this;
        }

        public PostBuilder description(String description) {
            this.description = description;
            return this;
        }

        public PostBuilder content(String content) {
            this.content = content;
            return this;
        }

        public Post build() {
            return new Post(this.id, this.title, this.description, this.content);
        }

        public String toString() {
            return "id=" + this.id +
                    ", title=" + this.title +
                    ", description=" + this.description +
                    ", content=" + this.content;
        }
    }
}