package com.music.application.be.modules.user.dto;

import com.music.application.be.modules.role.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private Long id;
    private Role role;
    private String username;
    private String email;
    private String phone;
    private String avatar;
}
