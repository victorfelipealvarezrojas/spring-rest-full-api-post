package com.springboot.blog.repository;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CommentRepositoryTest {
    List<Comment> commentToSet;

    @Autowired
    CommentRepository commentRepository;
    @Autowired
    PostRepository postRepository;

    @BeforeEach
    void setUp() {
        // SAVE REPOSITORY
        List<Comment> comentToList = getComments(getPost());
        commentToSet = commentRepository.saveAll(comentToList);
    }

    @AfterEach
    void tearDown() {
        commentRepository.deleteAll();
        postRepository.deleteAll();
    }

    @Test
    void testSaveComment() {
        commentToSet.forEach((comment) -> {
            assertNotNull(comment.getName());
            assertNotNull(comment.getEmail());
            assertNotNull(comment.getBody());
            assertNotNull(comment.getPost());
            assertEquals(comment.getPost().getTitle(),       "title-post");
            assertEquals(comment.getPost().getDescription(), "description");
            assertEquals(comment.getPost().getContent(),     "content");
        });
    }

    @Test
    void searchAllComments(){
        List<Comment> comments = commentRepository.findAll();
        assertEquals(comments.size(), 3);
    }

    @Test
    void SearchCommentById(){
        Optional<Comment> comment = commentRepository.findById(commentToSet.get(0).getId());
        assertTrue(comment.isPresent());
        assertEquals(comment.get().getName(), "name");
        assertEquals(comment.get().getEmail(), "email");
        assertEquals(comment.get().getBody(), "content");

        comment.ifPresent(c -> {
            assertEquals(c.getName(), "name");
            assertEquals(c.getEmail(), "email");
            assertEquals(c.getBody(), "content");
        });

        Optional<Comment> newObject =  comment.map(c -> {
            c.setName(c.getName() + "Edit");
            assertEquals(c.getEmail(), "email");
            assertEquals(c.getBody(), "content");
            return c;
        });

        newObject.ifPresent(c -> {
            assertEquals(c.getName(), "nameEdit");
            assertEquals(c.getEmail(), "email");
            assertEquals(c.getBody(), "content");
        });
    }

    @Test
    void CommentsByPostId() {
        List<Comment> comments = commentRepository.findByPostId(commentToSet.get(0).getPost().getId());
        assertEquals(comments.size(), 3);
    }

    @Test
    void updateCommentById() {
        Comment comment = commentRepository.findById(commentToSet.get(0).getId()).orElseThrow();

        comment.setName("nameEdit");
        commentRepository.save(comment);

        Comment commentUpdated = commentRepository.findById(commentToSet.get(0).getId()).orElseThrow();
        assertEquals(commentUpdated.getName(), "nameEdit");
    }

    @Test
    void deleteRepository() {
        assertEquals(commentRepository.findAll().size(), 3);
        Comment comment = commentRepository.findById(commentToSet.get(0).getId()).orElseThrow();
        commentRepository.delete(comment);
        assertEquals(commentRepository.findAll().size(), 2);
    }


    private static List<Comment> getComments(Post post) {
        return List.of(
                new Comment.CommentBuilder()
                        .name("name")
                        .email("email")
                        .body("content")
                        .post(post)
                        .build(),
                new Comment.CommentBuilder()
                        .name("name2")
                        .email("email2")
                        .body("content2")
                        .post(post)
                        .build(),
                new Comment.CommentBuilder()
                        .name("name3")
                        .email("email3")
                        .body("content3")
                        .post(post)
                        .build()
        );
    }

    private Post getPost() {
        return postRepository.save(new Post.PostBuilder()
                .title("title-post")
                .description("description")
                .content("content")
                .build());
    }
}