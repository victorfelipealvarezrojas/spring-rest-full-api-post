package com.springboot.blog.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PostTest {

    Post post;

    @BeforeEach
    void InstancePost() {
        this.post = new Post.PostBuilder()
                .title("title")
                .description("description")
                .content("content")
                .build();
    }

    @Test
    void testPost() {
        assertEquals(this.post.getTitle(), "title");
        assertEquals(this.post.getDescription(), "description");
        assertEquals(this.post.getContent(), "content");
    }

    @Test
    void testPostBuilderSetterGetter() {
        this.post.setTitle("editado");
        assertEquals(this.post.getTitle(), "editado");

        this.post.setDescription("editado");
        assertEquals(this.post.getDescription(), "editado");

        this.post.setContent("editado");
        assertEquals(this.post.getContent(), "editado");
    }

    @Test
    void testPostBuilderEquals() {
        Post post2 = Post.builder()
                .title("title")
                .description("description")
                .content("content")
                .build();

        assertEquals(this.post, post2);
    }

    @Test
    void testPostBuilderInstancePost() {
        assertTrue(this.post instanceof Post);
    }
}