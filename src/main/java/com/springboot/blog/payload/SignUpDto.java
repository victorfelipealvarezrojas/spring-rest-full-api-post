package com.springboot.blog.payload;

import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDto {
    private String name;
    private String username;
    private String email;
    private String password;

}
