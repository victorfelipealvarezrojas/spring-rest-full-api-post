package com.springboot.blog.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PostResponse {
    private List<PostDto> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean last;

    public static PostResponseBuilder builder() {
        return new PostResponseBuilder();
    }

    public static class PostResponseBuilder {
        private List<PostDto> content;
        private int page;
        private int size;
        private long totalElements;
        private int totalPages;
        private boolean last;

        public PostResponseBuilder content(List<PostDto> content) {
            this.content = content;
            return this;
        }

        public PostResponseBuilder page(int page) {
            this.page = page;
            return this;
        }

        public PostResponseBuilder size(int size) {
            this.size = size;
            return this;
        }

        public PostResponseBuilder totalElements(long totalElements) {
            this.totalElements = totalElements;
            return this;
        }

        public PostResponseBuilder totalPages(int totalPages) {
            this.totalPages = totalPages;
            return this;
        }

        public PostResponseBuilder last(boolean last) {
            this.last = last;
            return this;
        }

        public PostResponse build() {
            return new PostResponse(
                    this.content,
                    this.page,
                    this.size,
                    this.totalElements,
                    this.totalPages,
                    this.last
            );
        }

        public String toString() {
            return  this.content + ", page=" +
                    this.page + ", size=" +
                    this.size + ", totalElements=" +
                    this.totalElements + ", totalPages=" +
                    this.totalPages + ", last=" +
                    this.last;
        }
    }
}
