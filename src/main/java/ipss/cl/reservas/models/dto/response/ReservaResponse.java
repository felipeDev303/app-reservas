package ipss.cl.reservas.models.dto.response;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservaResponse {
    
    private Long id;
    
    // DATOS DEL CLIENTE
    private String nombreCliente;
    private String emailCliente;
    private String telefonoCliente;
    
    // DATOS DE LA MESA
    private Long mesaId;
    private Integer mesaNumero;
    private String mesaTipo;
    
    // DATOS DE LA RESERVA
    private LocalDate fecha;
    private LocalTime hora;
    private Integer numeroPersonas;
    private String estado;
    private String observaciones;
    private String codigoReserva;
    
    // AUDITORÍA
    private String fechaCreacion;
    private String fechaModificacion;
}
