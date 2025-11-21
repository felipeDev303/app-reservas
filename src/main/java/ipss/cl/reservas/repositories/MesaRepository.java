package ipss.cl.reservas.repositories;

import ipss.cl.reservas.models.entities.Mesa;
import ipss.cl.reservas.models.enums.EstadoMesa;
import ipss.cl.reservas.models.enums.TipoMesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, Long> {
    
    // Buscar por número
    Optional<Mesa> findByNumero(Integer numero);
    
    // Listar mesas activas
    List<Mesa> findByActivaTrue();
    
    // Listar por estado
    List<Mesa> findByEstadoAndActivaTrue(EstadoMesa estado);
    
    // Listar por tipo
    List<Mesa> findByTipoAndActivaTrue(TipoMesa tipo);
    
    // Listar por capacidad
    List<Mesa> findByCapacidadMinimaLessThanEqualAndCapacidadMaximaGreaterThanEqualAndActivaTrue(
            Integer personas, Integer personas2);
    
    // Contar mesas por estado
    Long countByEstadoAndActivaTrue(EstadoMesa estado);
    
    // Contar total de mesas activas
    Long countByActivaTrue();
    
    // Buscar mesas disponibles para una fecha/hora específica
    @Query("SELECT m FROM Mesa m WHERE m.activa = true " +
           "AND m.estado = 'DISPONIBLE' " +
           "AND m.capacidadMinima <= :personas " +
           "AND m.capacidadMaxima >= :personas " +
           "AND m.id NOT IN (" +
           "  SELECT r.mesa.id FROM Reserva r " +
           "  WHERE r.fecha = :fecha " +
           "  AND r.hora = :hora " +
           "  AND r.estado IN ('PENDIENTE', 'CONFIRMADA')" +
           ")")
    List<Mesa> findMesasDisponibles(
            @Param("fecha") LocalDate fecha,
            @Param("hora") LocalTime hora,
            @Param("personas") Integer personas);
}
