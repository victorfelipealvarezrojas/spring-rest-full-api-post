package com.springboot.blog.payload;

import lombok.Data;

@Data
public class PostDto {
    private Long id;
    private String title;
    private String description;
    private String content;

    public PostDto(Long id, String title, String description, String content) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.content = content;
    }

    public static PostDtoBuilder builder() {
        return new PostDtoBuilder();
    }

    public static class PostDtoBuilder {
        private Long id;
        private String title;
        private String description;
        private String content;

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

        public PostDto build() {
            return new PostDto(this.id, this.title, this.description, this.content);
        }

        public String toString() {
            return "PostDto.PostDtoBuilder(id=" + this.id + ", title=" + this.title + ", description=" + this.description + ", content=" + this.content + ")";
        }
    }
}