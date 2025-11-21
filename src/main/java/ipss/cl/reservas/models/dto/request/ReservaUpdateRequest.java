package ipss.cl.reservas.models.dto.request;

import lombok.Data;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ReservaUpdateRequest {
    
    // DATOS DEL CLIENTE (opcionales en actualización)
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombreCliente;
    
    @Email(message = "Email inválido")
    private String emailCliente;
    
    @Pattern(regexp = "^[0-9]{9,15}$", message = "Teléfono inválido (9-15 dígitos)")
    private String telefonoCliente;
    
    // DATOS DE LA RESERVA
    private Long mesaId;
    
    @FutureOrPresent(message = "La fecha no puede ser del pasado")
    private LocalDate fecha;
    
    private LocalTime hora;
    
    @Min(value = 1, message = "Mínimo 1 persona")
    @Max(value = 20, message = "Máximo 20 personas")
    private Integer numeroPersonas;
    
    @Size(max = 500, message = "Observaciones máximo 500 caracteres")
    private String observaciones;
    
    // ESTADO
    private String estado;
}
