package ipss.cl.reservas.services;

import ipss.cl.reservas.models.entities.Mesa;
import ipss.cl.reservas.models.entities.Reserva;
import ipss.cl.reservas.repositories.MesaRepository;
import ipss.cl.reservas.repositories.ReservaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class DisponibilidadService {
    
    private final MesaRepository mesaRepository;
    private final ReservaRepository reservaRepository;
    
    /**
     * Obtener mesas disponibles para una fecha, hora y número de personas
     */
    public List<Mesa> obtenerMesasDisponibles(LocalDate fecha, LocalTime hora, Integer numeroPersonas) {
        log.info("Buscando mesas disponibles para {} a las {} para {} personas", fecha, hora, numeroPersonas);
        return mesaRepository.findMesasDisponibles(fecha, hora, numeroPersonas);
    }
    
    /**
     * Verificar si una mesa está disponible
     */
    public boolean esMesaDisponible(Long mesaId, LocalDate fecha, LocalTime hora) {
        List<Reserva> conflictos = reservaRepository.findReservasConflicto(mesaId, fecha, hora);
        return conflictos.isEmpty();
    }
    
    /**
     * Obtener horarios disponibles para una fecha y número de personas
     */
    public List<LocalTime> obtenerHorariosDisponibles(LocalDate fecha, Integer numeroPersonas) {
        // Horarios de apertura del restaurante (ejemplo: 12:00 a 23:00)
        List<LocalTime> horariosDisponibles = List.of(
                LocalTime.of(12, 0),
                LocalTime.of(12, 30),
                LocalTime.of(13, 0),
                LocalTime.of(13, 30),
                LocalTime.of(14, 0),
                LocalTime.of(14, 30),
                LocalTime.of(19, 0),
                LocalTime.of(19, 30),
                LocalTime.of(20, 0),
                LocalTime.of(20, 30),
                LocalTime.of(21, 0),
                LocalTime.of(21, 30),
                LocalTime.of(22, 0)
        );
        
        // Filtrar solo horarios con mesas disponibles
        return horariosDisponibles.stream()
                .filter(hora -> !obtenerMesasDisponibles(fecha, hora, numeroPersonas).isEmpty())
                .toList();
    }
    
    /**
     * Contar mesas disponibles en un horario
     */
    public long contarMesasDisponibles(LocalDate fecha, LocalTime hora, Integer numeroPersonas) {
        return obtenerMesasDisponibles(fecha, hora, numeroPersonas).size();
    }
}
