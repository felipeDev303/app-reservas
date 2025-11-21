package ipss.cl.reservas.controllers.api;

import ipss.cl.reservas.models.entities.Mesa;
import ipss.cl.reservas.services.DisponibilidadService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/disponibilidad")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DisponibilidadRestController {
    
    private final DisponibilidadService disponibilidadService;
    
    /**
     * GET /api/disponibilidad/mesas
     * Obtener mesas disponibles para una fecha, hora y número de personas
     */
    @GetMapping("/mesas")
    public ResponseEntity<List<Mesa>> obtenerMesasDisponibles(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime hora,
            @RequestParam Integer personas) {
        
        List<Mesa> mesas = disponibilidadService.obtenerMesasDisponibles(fecha, hora, personas);
        return ResponseEntity.ok(mesas);
    }
    
    /**
     * GET /api/disponibilidad/horarios
     * Obtener horarios disponibles para una fecha y número de personas
     */
    @GetMapping("/horarios")
    public ResponseEntity<List<LocalTime>> obtenerHorariosDisponibles(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam Integer personas) {
        
        List<LocalTime> horarios = disponibilidadService.obtenerHorariosDisponibles(fecha, personas);
        return ResponseEntity.ok(horarios);
    }
    
    /**
     * GET /api/disponibilidad/verificar
     * Verificar si una mesa específica está disponible
     */
    @GetMapping("/verificar")
    public ResponseEntity<Map<String, Boolean>> verificarDisponibilidad(
            @RequestParam Long mesaId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime hora) {
        
        boolean disponible = disponibilidadService.esMesaDisponible(mesaId, fecha, hora);
        return ResponseEntity.ok(Map.of("disponible", disponible));
    }
    
    /**
     * GET /api/disponibilidad/count
     * Contar mesas disponibles
     */
    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> contarMesasDisponibles(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime hora,
            @RequestParam Integer personas) {
        
        long count = disponibilidadService.contarMesasDisponibles(fecha, hora, personas);
        return ResponseEntity.ok(Map.of("count", count));
    }
}
