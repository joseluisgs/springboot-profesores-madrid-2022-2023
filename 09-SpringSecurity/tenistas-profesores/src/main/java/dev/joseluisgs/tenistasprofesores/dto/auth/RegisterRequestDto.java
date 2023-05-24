package dev.joseluisgs.tenistasprofesores.dto.auth;

import dev.joseluisgs.tenistasprofesores.models.user.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

// DTO para el registro de usuarios
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDto {
    @NotBlank(message = "El nombre no puede estar vacío")
    private String firstname;
    @NotBlank(message = "El apellido no puede estar vacío")
    private String lastname;
    @Email(message = "El email debe ser válido")
    private String email;
    @NotBlank(message = "La contraseña no puede estar vacía")
    @Length(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;
    private Role role = Role.USER;
}
