package dev.joseluisgs.tenistasprofesores.models.raquetas;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Clase Raqueta POJO
 */

// Data: Genera los getters y setters, toString, equals, hashCode y el constructor con todos los parámetros necesarios (finals)
@Data
// AllArgsConstructor: Genera el constructor con todos los parámetros
@NoArgsConstructor // Necesario para JPA
@AllArgsConstructor
@Builder // Para poder usar el patrón Builder
@Entity// Para que sea una entidad de JPA
@Table(name = "raquetas") // Para indicar la tabla de la BD, si no coge el nombre de la clase
public class Raqueta {
    @Id // Indicamos que es el ID de la tabla
    @GeneratedValue(strategy = GenerationType.AUTO) // Indicamos que es el ID generado automáticamente
    // Que se genera siguiendo el mecanismo  de la BD (Seguira una secuencia, esto lo hago porque uso un script de creación de la BD)
    @Column(name = "id") // Le cambiamos el nombre a la columna
    private Long id; // Inicializamos a 0, ya que es un valor que se genera automáticamente
    @Column(unique = true, updatable = false) // Indicamos que es un campo único y que no se puede actualizar
    private UUID uuid = UUID.randomUUID();
    @NotBlank(message = "La marca no puede estar vacía")
    private String marca;
    @NotBlank(message = "El modelo no puede estar vacío")
    private String modelo;
    @Min(value = 0, message = "El precio no puede ser negativo")
    private Double precio;
    private String imagen;
    @Column(name = "created_at") // Le cambiamos el nombre a la columna
    @Temporal(TemporalType.TIMESTAMP) // Indicamos que es un campo de tipo fecha y hora
    @CreatedDate
    private LocalDateTime createdAt = LocalDateTime.now();
    @Column(name = "updated_at") // Le cambiamos el nombre a la columna
    @Temporal(TemporalType.TIMESTAMP) // Indicamos que es un campo de tipo fecha y hora
    @LastModifiedDate
    private LocalDateTime updatedAt = LocalDateTime.now();
    private Boolean deleted = false;

    // Si quisiesemos la lista de tenistas relación con esta raqueta, tendríamos que hacer un OneToMany
    // Podemos añadir la anotación @JsonBackReference para evitar la recursividad
    // cascade = CascadeType.ALL: Si borramos una raqueta, se borran todos los tenistas relacionados
    // orphanRemoval = true: Si borramos un tenista, se borra de la lista de tenistas de la raqueta
    // @OneToMany(mappedBy = "raqueta", cascade = CascadeType.ALL, orphanRemoval = true)
    // @JsonBackReference // Evitamos recursividad
    // private Set<Tenista> tenistas;
}