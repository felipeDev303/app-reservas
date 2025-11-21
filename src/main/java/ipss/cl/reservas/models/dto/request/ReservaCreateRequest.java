package ipss.cl.reservas.models.dto.request;

import lombok.Data;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ReservaCreateRequest {
    
    // DATOS DEL CLIENTE
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombreCliente;
    
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Email inválido")
    private String emailCliente;
    
    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "^[0-9]{9,15}$", message = "Teléfono inválido (9-15 dígitos)")
    private String telefonoCliente;
    
    // DATOS DE LA RESERVA
    @NotNull(message = "La mesa es obligatoria")
    private Long mesaId;
    
    @NotNull(message = "La fecha es obligatoria")
    @FutureOrPresent(message = "La fecha no puede ser del pasado")
    private LocalDate fecha;
    
    @NotNull(message = "La hora es obligatoria")
    private LocalTime hora;
    
    @NotNull(message = "El número de personas es obligatorio")
    @Min(value = 1, message = "Mínimo 1 persona")
    @Max(value = 20, message = "Máximo 20 personas")
    private Integer numeroPersonas;
    
    @Size(max = 500, message = "Observaciones máximo 500 caracteres")
    private String observaciones;
}
