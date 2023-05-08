package dev.joseluisgs.tenistasprofesores.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO para la autenticación, petición
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {

    String password;
    private String email;
}
