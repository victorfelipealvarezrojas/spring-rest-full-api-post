package com.springboot.blog.payload;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class PostDto {
    private Long id;
    private String title;
    private String description;
    private String content;
    private Set<CommentDto> comments = new HashSet<>();

    public PostDto(Long id, String title, String description, String content, Set<CommentDto> comments) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.content = content;
        this.comments = comments;
    }

    public static PostDtoBuilder builder() {
        return new PostDtoBuilder();
    }

    public static class PostDtoBuilder {
        private Long id;
        private String title;
        private String description;
        private String content;
        private Set<CommentDto> comments;

        public PostDtoBuilder() {
        }

        public PostDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public PostDtoBuilder title(String title) {
            this.title = title;
            return this;
        }

        public PostDtoBuilder description(String description) {
            this.description = description;
            return this;
        }

        public PostDtoBuilder content(String content) {
            this.content = content;
            return this;
        }

        public PostDtoBuilder comments(Set<CommentDto> comments) {
            this.comments = comments;
            return this;
        }

        public PostDto build() {
            return new PostDto(this.id, this.title, this.description, this.content, this.comments);
        }

        public String toString() {
            return "PostDto.PostDtoBuilder(id=" + this.id + ", title=" + this.title + ", description=" + this.description + ", content=" + this.content + ", comments=" + this.comments + ")";
        }
    }
}
