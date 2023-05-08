package dev.joseluisgs.tenistasprofesores.dto.auth;

import dev.joseluisgs.tenistasprofesores.models.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO para el registro de usuarios
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDto {

    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Role role;
}
