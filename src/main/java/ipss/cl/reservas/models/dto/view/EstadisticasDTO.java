package ipss.cl.reservas.models.dto.view;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EstadisticasDTO {
    
    // ESTADÍSTICAS GENERALES
    private Long totalReservasHoy;
    private Long totalReservasSemana;
    private Long totalReservasMes;
    
    // ESTADOS
    private Long reservasPendientes;
    private Long reservasConfirmadas;
    private Long reservasCanceladas;
    private Long reservasCompletadas;
    
    // MESAS
    private Long totalMesas;
    private Long mesasDisponibles;
    private Long mesasOcupadas;
    private Long mesasReservadas;
    
    // OCUPACIÓN
    private Double tasaOcupacionHoy;
    private Double tasaOcupacionSemana;
    
    // INGRESOS ESTIMADOS (opcional para futuro)
    private Integer personasHoy;
    private Integer personasSemana;
}
