package ipss.cl.reservas.controllers.api;

import ipss.cl.reservas.models.dto.request.ReservaCreateRequest;
import ipss.cl.reservas.models.dto.request.ReservaUpdateRequest;
import ipss.cl.reservas.models.dto.response.ReservaResponse;
import ipss.cl.reservas.models.entities.Mesa;
import ipss.cl.reservas.models.entities.Reserva;
import ipss.cl.reservas.models.enums.EstadoReserva;
import ipss.cl.reservas.services.MesaService;
import ipss.cl.reservas.services.ReservaService;
import ipss.cl.reservas.utils.DateUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reservas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ReservaRestController {
    
    private final ReservaService reservaService;
    private final MesaService mesaService;
    
    /**
     * GET /api/reservas
     * Listar todas las reservas
     */
    @GetMapping
    public ResponseEntity<List<ReservaResponse>> listarReservas() {
        List<Reserva> reservas = reservaService.listarReservas();
        List<ReservaResponse> response = reservas.stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
    
    /**
     * GET /api/reservas/{id}
     * Obtener reserva por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponse> obtenerReserva(@PathVariable Long id) {
        return reservaService.obtenerReservaPorId(id)
                .map(this::convertirAResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * GET /api/reservas/codigo/{codigo}
     * Buscar reserva por código
     */
    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<ReservaResponse> buscarPorCodigo(@PathVariable String codigo) {
        return reservaService.buscarPorCodigo(codigo)
                .map(this::convertirAResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * GET /api/reservas/email/{email}
     * Listar reservas por email
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<List<ReservaResponse>> listarPorEmail(@PathVariable String email) {
        List<Reserva> reservas = reservaService.listarReservasPorEmail(email);
        List<ReservaResponse> response = reservas.stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
    
    /**
     * GET /api/reservas/futuras/{email}
     * Listar reservas futuras por email
     */
    @GetMapping("/futuras/{email}")
    public ResponseEntity<List<ReservaResponse>> listarFuturasPorEmail(@PathVariable String email) {
        List<Reserva> reservas = reservaService.listarReservasFuturasPorEmail(email);
        List<ReservaResponse> response = reservas.stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
    
    /**
     * GET /api/reservas/fecha/{fecha}
     * Listar reservas del día
     */
    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<ReservaResponse>> listarPorFecha(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        List<Reserva> reservas = reservaService.listarReservasDelDia(fecha);
        List<ReservaResponse> response = reservas.stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
    
    /**
     * GET /api/reservas/hoy
     * Listar reservas de hoy
     */
    @GetMapping("/hoy")
    public ResponseEntity<List<ReservaResponse>> listarReservasDeHoy() {
        List<Reserva> reservas = reservaService.listarReservasDeHoy();
        List<ReservaResponse> response = reservas.stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
    
    /**
     * GET /api/reservas/estado/{estado}
     * Listar reservas por estado
     */
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<ReservaResponse>> listarPorEstado(@PathVariable EstadoReserva estado) {
        List<Reserva> reservas = reservaService.listarReservasPorEstado(estado);
        List<ReservaResponse> response = reservas.stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
    
    /**
     * GET /api/reservas/pendientes
     * Listar reservas pendientes
     */
    @GetMapping("/pendientes")
    public ResponseEntity<List<ReservaResponse>> listarPendientes() {
        List<Reserva> reservas = reservaService.listarReservasPendientes();
        List<ReservaResponse> response = reservas.stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
    
    /**
     * POST /api/reservas
     * Crear nueva reserva
     */
    @PostMapping
    public ResponseEntity<ReservaResponse> crearReserva(@Valid @RequestBody ReservaCreateRequest request) {
        try {
            // Obtener la mesa
            Mesa mesa = mesaService.obtenerMesaPorId(request.getMesaId())
                    .orElseThrow(() -> new IllegalArgumentException("Mesa no encontrada"));
            
            // Crear la reserva
            Reserva reserva = new Reserva();
            reserva.setNombreCliente(request.getNombreCliente());
            reserva.setEmailCliente(request.getEmailCliente());
            reserva.setTelefonoCliente(request.getTelefonoCliente());
            reserva.setMesa(mesa);
            reserva.setFecha(request.getFecha());
            reserva.setHora(request.getHora());
            reserva.setNumeroPersonas(request.getNumeroPersonas());
            reserva.setObservaciones(request.getObservaciones());
            
            Reserva nuevaReserva = reservaService.crearReserva(reserva);
            ReservaResponse response = convertirAResponse(nuevaReserva);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * PUT /api/reservas/{id}
     * Actualizar reserva
     */
    @PutMapping("/{id}")
    public ResponseEntity<ReservaResponse> actualizarReserva(
            @PathVariable Long id,
            @Valid @RequestBody ReservaUpdateRequest request) {
        try {
            Reserva reserva = reservaService.obtenerReservaPorId(id)
                    .orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada"));
            
            // Actualizar campos
            if (request.getNombreCliente() != null) {
                reserva.setNombreCliente(request.getNombreCliente());
            }
            if (request.getEmailCliente() != null) {
                reserva.setEmailCliente(request.getEmailCliente());
            }
            if (request.getTelefonoCliente() != null) {
                reserva.setTelefonoCliente(request.getTelefonoCliente());
            }
            if (request.getMesaId() != null) {
                Mesa mesa = mesaService.obtenerMesaPorId(request.getMesaId())
                        .orElseThrow(() -> new IllegalArgumentException("Mesa no encontrada"));
                reserva.setMesa(mesa);
            }
            if (request.getFecha() != null) {
                reserva.setFecha(request.getFecha());
            }
            if (request.getHora() != null) {
                reserva.setHora(request.getHora());
            }
            if (request.getNumeroPersonas() != null) {
                reserva.setNumeroPersonas(request.getNumeroPersonas());
            }
            if (request.getObservaciones() != null) {
                reserva.setObservaciones(request.getObservaciones());
            }
            if (request.getEstado() != null) {
                reserva.setEstado(EstadoReserva.valueOf(request.getEstado()));
            }
            
            Reserva reservaActualizada = reservaService.actualizarReserva(id, reserva);
            return ResponseEntity.ok(convertirAResponse(reservaActualizada));
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * PATCH /api/reservas/{id}/confirmar
     * Confirmar reserva
     */
    @PatchMapping("/{id}/confirmar")
    public ResponseEntity<ReservaResponse> confirmarReserva(@PathVariable Long id) {
        try {
            Reserva reserva = reservaService.confirmarReserva(id);
            return ResponseEntity.ok(convertirAResponse(reserva));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * PATCH /api/reservas/{id}/cancelar
     * Cancelar reserva
     */
    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<ReservaResponse> cancelarReserva(@PathVariable Long id) {
        try {
            Reserva reserva = reservaService.cancelarReserva(id);
            return ResponseEntity.ok(convertirAResponse(reserva));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * PATCH /api/reservas/{id}/completar
     * Completar reserva
     */
    @PatchMapping("/{id}/completar")
    public ResponseEntity<ReservaResponse> completarReserva(@PathVariable Long id) {
        try {
            Reserva reserva = reservaService.completarReserva(id);
            return ResponseEntity.ok(convertirAResponse(reserva));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * DELETE /api/reservas/{id}
     * Eliminar reserva
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReserva(@PathVariable Long id) {
        try {
            reservaService.eliminarReserva(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Convertir Reserva a ReservaResponse
     */
    private ReservaResponse convertirAResponse(Reserva reserva) {
        return ReservaResponse.builder()
                .id(reserva.getId())
                .nombreCliente(reserva.getNombreCliente())
                .emailCliente(reserva.getEmailCliente())
                .telefonoCliente(reserva.getTelefonoCliente())
                .mesaId(reserva.getMesa().getId())
                .mesaNumero(reserva.getMesa().getNumero())
                .mesaTipo(reserva.getMesa().getTipo().name())
                .fecha(reserva.getFecha())
                .hora(reserva.getHora())
                .numeroPersonas(reserva.getNumeroPersonas())
                .estado(reserva.getEstado().name())
                .observaciones(reserva.getObservaciones())
                .codigoReserva(reserva.getCodigoReserva())
                .fechaCreacion(DateUtils.formatearFechaHora(reserva.getFechaCreacion()))
                .fechaModificacion(reserva.getFechaModificacion() != null ? 
                        DateUtils.formatearFechaHora(reserva.getFechaModificacion()) : null)
                .build();
    }
}
