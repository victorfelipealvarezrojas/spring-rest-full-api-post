package com.springboot.blog.payload;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDto {
    public long id;

    @NotEmpty(message = "Name cannot be empty")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String name;

    @NotEmpty(message = "Name cannot be empty")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    @Email(message = "Email must be a valid email address")
    private String email;

    @NotEmpty(message = "Name cannot be empty")
    @Size(min = 20, max = 100, message = "Name must be between 20 and 100 characters")
    private String body;

    CommentDto(long id, String name, String email, String body) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.body = body;
    }

    public static CommentDtoBuilder builder() {
        return new CommentDtoBuilder();
    }

    public static class CommentDtoBuilder {
        private long id;
        private String name;
        private String email;
        private String body;

        public CommentDtoBuilder() {
        }

        public CommentDtoBuilder id(long id) {
            this.id = id;
            return this;
        }

        public CommentDtoBuilder name(String name) {
            this.name = name;
            return this;
        }

        public CommentDtoBuilder email(String email) {
            this.email = email;
            return this;
        }

        public CommentDtoBuilder body(String body) {
            this.body = body;
            return this;
        }

        public CommentDto build() {
            return new CommentDto(this.id, this.name, this.email, this.body);
        }

        public String toString() {
            return "CommentDto.CommentDtoBuilder(id=" + this.id +
                    ", name=" + this.name +
                    ", email=" + this.email +
                    ", body=" + this.body + ")";
        }
    }
}
