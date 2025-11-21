package ipss.cl.reservas.controllers.web;

import ipss.cl.reservas.models.dto.view.EstadisticasDTO;
import ipss.cl.reservas.models.entities.Mesa;
import ipss.cl.reservas.models.entities.Reserva;
import ipss.cl.reservas.models.enums.EstadoMesa;
import ipss.cl.reservas.models.enums.EstadoReserva;
import ipss.cl.reservas.services.MesaService;
import ipss.cl.reservas.services.ReservaService;
import ipss.cl.reservas.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminViewController {
    
    private final ReservaService reservaService;
    private final MesaService mesaService;
    
    /**
     * Dashboard principal de administración
     */
    @GetMapping
    public String dashboard(Model model) {
        
        // Estadísticas
        EstadisticasDTO stats = generarEstadisticas();
        model.addAttribute("estadisticas", stats);
        
        // Reservas de hoy
        List<Reserva> reservasHoy = reservaService.listarReservasDeHoy();
        model.addAttribute("reservasHoy", reservasHoy);
        
        // Reservas pendientes
        List<Reserva> reservasPendientes = reservaService.listarReservasPendientes();
        model.addAttribute("reservasPendientes", reservasPendientes);
        
        return "admin/dashboard";
    }
    
    /**
     * Gestión de mesas
     */
    @GetMapping("/mesas")
    public String mesas(Model model) {
        List<Mesa> mesas = mesaService.listarMesas();
        model.addAttribute("mesas", mesas);
        
        // Estadísticas de mesas
        model.addAttribute("totalMesas", mesaService.contarMesasActivas());
        model.addAttribute("mesasDisponibles", mesaService.contarMesasPorEstado(EstadoMesa.DISPONIBLE));
        model.addAttribute("mesasOcupadas", mesaService.contarMesasPorEstado(EstadoMesa.OCUPADA));
        
        return "admin/mesas";
    }
    
    /**
     * Gestión de reservas
     */
    @GetMapping("/reservas")
    public String reservas(Model model) {
        List<Reserva> reservas = reservaService.listarReservas();
        model.addAttribute("reservas", reservas);
        
        return "admin/reservas";
    }
    
    /**
     * Generar estadísticas para el dashboard
     */
    private EstadisticasDTO generarEstadisticas() {
        LocalDate hoy = LocalDate.now();
        LocalDate inicioSemana = DateUtils.inicioSemanaActual();
        LocalDate finSemana = DateUtils.finSemanaActual();
        LocalDate inicioMes = DateUtils.inicioMesActual();
        LocalDate finMes = DateUtils.finMesActual();
        
        return EstadisticasDTO.builder()
                // Reservas
                .totalReservasHoy(reservaService.contarReservasDeHoy())
                .totalReservasSemana(contarReservasEntreFechas(inicioSemana, finSemana))
                .totalReservasMes(contarReservasEntreFechas(inicioMes, finMes))
                
                // Estados
                .reservasPendientes(reservaService.contarReservasPorEstado(EstadoReserva.PENDIENTE))
                .reservasConfirmadas(reservaService.contarReservasPorEstado(EstadoReserva.CONFIRMADA))
                .reservasCanceladas(reservaService.contarReservasPorEstado(EstadoReserva.CANCELADA))
                .reservasCompletadas(reservaService.contarReservasPorEstado(EstadoReserva.COMPLETADA))
                
                // Mesas
                .totalMesas(mesaService.contarMesasActivas())
                .mesasDisponibles(mesaService.contarMesasPorEstado(EstadoMesa.DISPONIBLE))
                .mesasOcupadas(mesaService.contarMesasPorEstado(EstadoMesa.OCUPADA))
                .mesasReservadas(mesaService.contarMesasPorEstado(EstadoMesa.RESERVADA))
                
                // Personas
                .personasHoy(reservaService.contarPersonasDeHoy())
                
                .build();
    }
    
    /**
     * Contar reservas entre fechas (helper)
     */
    private Long contarReservasEntreFechas(LocalDate inicio, LocalDate fin) {
        // Implementación simplificada - en producción usar query
        return 0L; // TODO: Implementar query en repository
    }
}
