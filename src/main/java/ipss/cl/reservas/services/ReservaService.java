package ipss.cl.reservas.services;

import ipss.cl.reservas.models.entities.Mesa;
import ipss.cl.reservas.models.entities.Reserva;
import ipss.cl.reservas.models.enums.EstadoReserva;
import ipss.cl.reservas.repositories.MesaRepository;
import ipss.cl.reservas.repositories.ReservaRepository;
import ipss.cl.reservas.utils.ReservaCodeGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ReservaService {
    
    private final ReservaRepository reservaRepository;
    private final MesaRepository mesaRepository;
    private final DisponibilidadService disponibilidadService;
    
    /**
     * Listar todas las reservas
     */
    public List<Reserva> listarReservas() {
        return reservaRepository.findAll();
    }
    
    /**
     * Obtener reserva por ID
     */
    public Optional<Reserva> obtenerReservaPorId(Long id) {
        return reservaRepository.findById(id);
    }
    
    /**
     * Buscar por código de reserva
     */
    public Optional<Reserva> buscarPorCodigo(String codigo) {
        return reservaRepository.findByCodigoReserva(codigo);
    }
    
    /**
     * Listar reservas por email
     */
    public List<Reserva> listarReservasPorEmail(String email) {
        return reservaRepository.findByEmailCliente(email);
    }
    
    /**
     * Listar reservas futuras por email
     */
    public List<Reserva> listarReservasFuturasPorEmail(String email) {
        return reservaRepository.findReservasFuturasPorEmail(email, LocalDate.now());
    }
    
    /**
     * Listar reservas del día
     */
    public List<Reserva> listarReservasDelDia(LocalDate fecha) {
        return reservaRepository.findReservasDelDia(fecha);
    }
    
    /**
     * Listar reservas de hoy
     */
    public List<Reserva> listarReservasDeHoy() {
        return listarReservasDelDia(LocalDate.now());
    }
    
    /**
     * Listar reservas por estado
     */
    public List<Reserva> listarReservasPorEstado(EstadoReserva estado) {
        return reservaRepository.findByEstado(estado);
    }
    
    /**
     * Listar reservas pendientes
     */
    public List<Reserva> listarReservasPendientes() {
        return reservaRepository.findReservasPendientes(LocalDate.now());
    }
    
    /**
     * Crear nueva reserva
     */
    @Transactional
    public Reserva crearReserva(Reserva reserva) {
        log.info("Creando nueva reserva para {} el {} a las {}", 
                reserva.getNombreCliente(), reserva.getFecha(), reserva.getHora());
        
        // Validar que la mesa existe
        Mesa mesa = mesaRepository.findById(reserva.getMesa().getId())
                .orElseThrow(() -> new IllegalArgumentException("Mesa no encontrada"));
        
        // Validar disponibilidad
        if (!disponibilidadService.esMesaDisponible(mesa.getId(), reserva.getFecha(), reserva.getHora())) {
            throw new IllegalStateException("La mesa no está disponible para la fecha y hora seleccionadas");
        }
        
        // Validar capacidad
        if (reserva.getNumeroPersonas() < mesa.getCapacidadMinima() || 
            reserva.getNumeroPersonas() > mesa.getCapacidadMaxima()) {
            throw new IllegalArgumentException(String.format(
                    "La mesa seleccionada tiene capacidad para %d-%d personas", 
                    mesa.getCapacidadMinima(), mesa.getCapacidadMaxima()));
        }
        
        // Generar código único
        reserva.setCodigoReserva(ReservaCodeGenerator.generarCodigo());
        
        // Establecer estado inicial
        if (reserva.getEstado() == null) {
            reserva.setEstado(EstadoReserva.PENDIENTE);
        }
        
        return reservaRepository.save(reserva);
    }
    
    /**
     * Actualizar reserva
     */
    @Transactional
    public Reserva actualizarReserva(Long id, Reserva reservaActualizada) {
        log.info("Actualizando reserva ID: {}", id);
        
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada con ID: " + id));
        
        // Si se cambia la mesa, fecha u hora, validar disponibilidad
        boolean cambioRelevante = false;
        
        if (reservaActualizada.getMesa() != null && 
            !reservaActualizada.getMesa().getId().equals(reserva.getMesa().getId())) {
            cambioRelevante = true;
            reserva.setMesa(reservaActualizada.getMesa());
        }
        
        if (reservaActualizada.getFecha() != null && 
            !reservaActualizada.getFecha().equals(reserva.getFecha())) {
            cambioRelevante = true;
            reserva.setFecha(reservaActualizada.getFecha());
        }
        
        if (reservaActualizada.getHora() != null && 
            !reservaActualizada.getHora().equals(reserva.getHora())) {
            cambioRelevante = true;
            reserva.setHora(reservaActualizada.getHora());
        }
        
        // Validar disponibilidad si hubo cambios
        if (cambioRelevante) {
            List<Reserva> conflictos = reservaRepository.findReservasConflicto(
                    reserva.getMesa().getId(), 
                    reserva.getFecha(), 
                    reserva.getHora());
            
            // Ignorar la reserva actual en los conflictos
            conflictos = conflictos.stream()
                    .filter(r -> !r.getId().equals(id))
                    .toList();
            
            if (!conflictos.isEmpty()) {
                throw new IllegalStateException("La mesa no está disponible para la nueva fecha/hora");
            }
        }
        
        // Actualizar otros campos
        if (reservaActualizada.getNombreCliente() != null) {
            reserva.setNombreCliente(reservaActualizada.getNombreCliente());
        }
        if (reservaActualizada.getEmailCliente() != null) {
            reserva.setEmailCliente(reservaActualizada.getEmailCliente());
        }
        if (reservaActualizada.getTelefonoCliente() != null) {
            reserva.setTelefonoCliente(reservaActualizada.getTelefonoCliente());
        }
        if (reservaActualizada.getNumeroPersonas() != null) {
            reserva.setNumeroPersonas(reservaActualizada.getNumeroPersonas());
        }
        if (reservaActualizada.getObservaciones() != null) {
            reserva.setObservaciones(reservaActualizada.getObservaciones());
        }
        
        return reservaRepository.save(reserva);
    }
    
    /**
     * Cambiar estado de reserva
     */
    @Transactional
    public Reserva cambiarEstado(Long id, EstadoReserva nuevoEstado) {
        log.info("Cambiando estado de reserva ID {} a {}", id, nuevoEstado);
        
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada con ID: " + id));
        
        reserva.setEstado(nuevoEstado);
        return reservaRepository.save(reserva);
    }
    
    /**
     * Confirmar reserva
     */
    @Transactional
    public Reserva confirmarReserva(Long id) {
        return cambiarEstado(id, EstadoReserva.CONFIRMADA);
    }
    
    /**
     * Cancelar reserva
     */
    @Transactional
    public Reserva cancelarReserva(Long id) {
        return cambiarEstado(id, EstadoReserva.CANCELADA);
    }
    
    /**
     * Marcar como completada
     */
    @Transactional
    public Reserva completarReserva(Long id) {
        return cambiarEstado(id, EstadoReserva.COMPLETADA);
    }
    
    /**
     * Eliminar reserva
     */
    @Transactional
    public void eliminarReserva(Long id) {
        log.info("Eliminando reserva ID: {}", id);
        reservaRepository.deleteById(id);
    }
    
    /**
     * Buscar mesa disponible automáticamente
     */
    public Optional<Mesa> buscarMesaDisponible(LocalDate fecha, LocalTime hora, Integer numeroPersonas) {
        log.info("Buscando mesa disponible para {} personas el {} a las {}", numeroPersonas, fecha, hora);
        
        // Obtener todas las mesas activas que cumplan con la capacidad
        List<Mesa> mesasCandidatas = mesaRepository.findByActivaTrue().stream()
                .filter(mesa -> numeroPersonas >= mesa.getCapacidadMinima() && 
                                numeroPersonas <= mesa.getCapacidadMaxima())
                .toList();
        
        // Buscar la primera mesa disponible
        for (Mesa mesa : mesasCandidatas) {
            if (disponibilidadService.esMesaDisponible(mesa.getId(), fecha, hora)) {
                log.info("Mesa {} asignada automáticamente", mesa.getNumero());
                return Optional.of(mesa);
            }
        }
        
        log.warn("No se encontró mesa disponible");
        return Optional.empty();
    }
    
    /**
     * Estadísticas
     */
    public Long contarReservasPorEstado(EstadoReserva estado) {
        return reservaRepository.countByEstado(estado);
    }
    
    public Long contarReservasDeHoy() {
        return reservaRepository.countByFecha(LocalDate.now());
    }
    
    public Integer contarPersonasDeHoy() {
        return reservaRepository.countPersonasPorFecha(LocalDate.now());
    }
}
