package com.springboot.blog.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class PostDto {
    private Long id;

    @NotEmpty(message = "Title cannot be empty")
    @Size(min = 3, max = 50, message = "Title must be between 3 and 50 characters")
    private String title;

    @NotEmpty(message = "Description cannot be empty")
    @Size(min = 10, max = 50, message = "Description must be between 3 and 50 characters")
    private String description;

    @NotEmpty(message = "Content cannot be empty")
    @Size(min = 10, max = 500, message = "Content must be between 10 and 500 characters")
    private String content;

    private Set<CommentDto> comments = new HashSet<>();

    private Long categoryId;

    public PostDto(Long id, String title, String description, String content, Set<CommentDto> comments, Long categoryId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.content = content;
        this.comments = comments;
        this.categoryId = categoryId;
    }

    public static PostDtoBuilder builder() {
        return new PostDtoBuilder();
    }

    public static class PostDtoBuilder {
        private Long id;

        @NotEmpty(message = "Title cannot be empty")
        @Size(min = 3, max = 50, message = "Title must be between 3 and 50 characters")
        private String title;

        @NotEmpty(message = "Description cannot be empty")
        @Size(min = 10, max = 50, message = "Description must be between 3 and 50 characters")
        private String description;

        @NotEmpty(message = "Content cannot be empty")
        @Size(min = 10, max = 500, message = "Content must be between 10 and 500 characters")
        private String content;
        private Set<CommentDto> comments;

        private Long categoryId;

        public PostDtoBuilder() {
        }

        public PostDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public PostDtoBuilder title(
                @NotEmpty(message = "Title cannot be empty")
                @Size(min = 3, max = 50, message = "Title must be between 3 and 50 characters")
                String title
        ) {
            this.title = title;
            return this;
        }

        public PostDtoBuilder description(
                @NotEmpty(message = "Description cannot be empty")
                @Size(min = 10, max = 50, message = "Description must be between 3 and 50 characters")
                String description
        ) {
            this.description = description;
            return this;
        }

        public PostDtoBuilder content(
                @NotEmpty(message = "Content cannot be empty")
                @Size(min = 10, max = 500, message = "Content must be between 10 and 500 characters")
                String content
        ) {
            this.content = content;
            return this;
        }

        public PostDtoBuilder comments(Set<CommentDto> comments) {
            this.comments = comments;
            return this;
        }

        public PostDtoBuilder categoryId(Long categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        public PostDto build() {
            return new PostDto(
                    this.id,
                    this.title,
                    this.description,
                    this.content,
                    this.comments,
                    this.categoryId
            );
        }

        public String toString() {
            return "PostDto.PostDtoBuilder(id=" + this.id +
                    ", title=" + this.title +
                    ", description=" + this.description +
                    ", content=" + this.content +
                    ", comments=" + this.comments +
                    ", categoryId=" + this.categoryId +
                    ")";
        }
    }
}
