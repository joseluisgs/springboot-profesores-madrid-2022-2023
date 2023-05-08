package dev.joseluisgs.tenistasprofesores.dto.tenistas;

import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor(force = true) // Para que no me de error en el constructor de jackson
@RequiredArgsConstructor // Para que me cree un constructor con los atributos finales
// Con el Builder me ahorro el mappers...
public class TenistaResponsePageDto {
    private final List<TenistaResponseDto> data;

    @Min(message = "El número de página debe ser mayor o igual a 0", value = 0)
    private final int currentPage;

    @Min(message = "El número de elementos por página debe ser mayor o igual a 1", value = 1)
    private final long totalElements;

    private final int totalPages;
    private final String sort;
}