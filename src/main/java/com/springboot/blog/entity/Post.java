package com.springboot.blog.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
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

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Post post)) return false;
        return Objects.equals(id, post.id)
                && Objects.equals(title, post.title)
                && Objects.equals(description, post.description)
                && Objects.equals(content, post.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, content);
    }

    public static PostBuilder builder() {
        return new PostBuilder();
    }

    public static class PostBuilder {
        private Long id;
        private String title;
        private String description;
        private String content;
        private Set<Comment> comments;
        private Category category;

        public PostBuilder() {
        }

        public PostBuilder id(Long id) {
            this.id = id;
            return this;
        }

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

        public PostBuilder comments(Set<Comment> comments) {
            this.comments = comments;
            return this;
        }

        public PostBuilder category(Category category) {
            this.category = category;
            return this;
        }

        public Post build() {
            return new Post(this.id, this.title, this.description,
                    this.content, this.comments, this.category);
        }

        public String toString() {
            return "Post.PostBuilder(" +
                    "id=" + this.id +
                    ", title=" + this.title +
                    ", description=" + this.description +
                    ", content=" + this.content +
                    ", comments=" + this.comments +
                    ")";
        }
    }
}