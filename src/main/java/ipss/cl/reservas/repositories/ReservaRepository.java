package ipss.cl.reservas.repositories;

import ipss.cl.reservas.models.entities.Reserva;
import ipss.cl.reservas.models.enums.EstadoReserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    
    // Buscar por código de reserva
    Optional<Reserva> findByCodigoReserva(String codigoReserva);
    
    // Buscar por email del cliente
    List<Reserva> findByEmailCliente(String email);
    
    // Buscar por teléfono del cliente
    List<Reserva> findByTelefonoCliente(String telefono);
    
    // Buscar por fecha
    List<Reserva> findByFecha(LocalDate fecha);
    
    // Buscar por fecha y estado
    List<Reserva> findByFechaAndEstado(LocalDate fecha, EstadoReserva estado);
    
    // Buscar por estado
    List<Reserva> findByEstado(EstadoReserva estado);
    
    // Buscar por mesa
    List<Reserva> findByMesaId(Long mesaId);
    
    // Buscar por mesa, fecha y hora (para verificar disponibilidad)
    @Query("SELECT r FROM Reserva r WHERE r.mesa.id = :mesaId " +
           "AND r.fecha = :fecha " +
           "AND r.hora = :hora " +
           "AND r.estado IN ('PENDIENTE', 'CONFIRMADA')")
    List<Reserva> findReservasConflicto(
            @Param("mesaId") Long mesaId,
            @Param("fecha") LocalDate fecha,
            @Param("hora") LocalTime hora);
    
    // Contar reservas por estado
    Long countByEstado(EstadoReserva estado);
    
    // Contar reservas de hoy
    Long countByFecha(LocalDate fecha);
    
    // Reservas entre fechas
    List<Reserva> findByFechaBetween(LocalDate fechaInicio, LocalDate fechaFin);
    
    // Reservas futuras por email
    @Query("SELECT r FROM Reserva r WHERE r.emailCliente = :email " +
           "AND r.fecha >= :fechaActual " +
           "AND r.estado IN ('PENDIENTE', 'CONFIRMADA') " +
           "ORDER BY r.fecha, r.hora")
    List<Reserva> findReservasFuturasPorEmail(
            @Param("email") String email,
            @Param("fechaActual") LocalDate fechaActual);
    
    // Reservas de hoy
    @Query("SELECT r FROM Reserva r WHERE r.fecha = :fecha " +
           "ORDER BY r.hora")
    List<Reserva> findReservasDelDia(@Param("fecha") LocalDate fecha);
    
    // Reservas pendientes de confirmar
    @Query("SELECT r FROM Reserva r WHERE r.estado = 'PENDIENTE' " +
           "AND r.fecha >= :fechaActual " +
           "ORDER BY r.fecha, r.hora")
    List<Reserva> findReservasPendientes(@Param("fechaActual") LocalDate fechaActual);
    
    // Contar personas por fecha
    @Query("SELECT COALESCE(SUM(r.numeroPersonas), 0) FROM Reserva r " +
           "WHERE r.fecha = :fecha " +
           "AND r.estado IN ('PENDIENTE', 'CONFIRMADA', 'COMPLETADA')")
    Integer countPersonasPorFecha(@Param("fecha") LocalDate fecha);
    
    // Contar personas por rango de fechas
    @Query("SELECT COALESCE(SUM(r.numeroPersonas), 0) FROM Reserva r " +
           "WHERE r.fecha BETWEEN :fechaInicio AND :fechaFin " +
           "AND r.estado IN ('PENDIENTE', 'CONFIRMADA', 'COMPLETADA')")
    Integer countPersonasEntreFechas(
            @Param("fechaInicio") LocalDate fechaInicio,
            @Param("fechaFin") LocalDate fechaFin);
}
