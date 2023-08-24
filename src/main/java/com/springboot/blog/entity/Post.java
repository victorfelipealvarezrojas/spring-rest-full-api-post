package com.springboot.blog.entity;

import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}