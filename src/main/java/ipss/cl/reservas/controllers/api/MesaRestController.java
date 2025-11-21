package ipss.cl.reservas.controllers.api;

import ipss.cl.reservas.models.entities.Mesa;
import ipss.cl.reservas.models.enums.EstadoMesa;
import ipss.cl.reservas.models.enums.TipoMesa;
import ipss.cl.reservas.services.MesaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mesas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MesaRestController {
    
    private final MesaService mesaService;
    
    /**
     * GET /api/mesas
     * Listar todas las mesas
     */
    @GetMapping
    public ResponseEntity<List<Mesa>> listarMesas() {
        List<Mesa> mesas = mesaService.listarMesas();
        return ResponseEntity.ok(mesas);
    }
    
    /**
     * GET /api/mesas/{id}
     * Obtener mesa por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Mesa> obtenerMesa(@PathVariable Long id) {
        return mesaService.obtenerMesaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * GET /api/mesas/numero/{numero}
     * Obtener mesa por número
     */
    @GetMapping("/numero/{numero}")
    public ResponseEntity<Mesa> obtenerMesaPorNumero(@PathVariable Integer numero) {
        return mesaService.obtenerMesaPorNumero(numero)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * GET /api/mesas/estado/{estado}
     * Listar mesas por estado
     */
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Mesa>> listarPorEstado(@PathVariable EstadoMesa estado) {
        List<Mesa> mesas = mesaService.listarMesasPorEstado(estado);
        return ResponseEntity.ok(mesas);
    }
    
    /**
     * GET /api/mesas/tipo/{tipo}
     * Listar mesas por tipo
     */
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Mesa>> listarPorTipo(@PathVariable TipoMesa tipo) {
        List<Mesa> mesas = mesaService.listarMesasPorTipo(tipo);
        return ResponseEntity.ok(mesas);
    }
    
    /**
     * GET /api/mesas/capacidad/{personas}
     * Listar mesas por capacidad
     */
    @GetMapping("/capacidad/{personas}")
    public ResponseEntity<List<Mesa>> listarPorCapacidad(@PathVariable Integer personas) {
        List<Mesa> mesas = mesaService.listarMesasPorCapacidad(personas);
        return ResponseEntity.ok(mesas);
    }
    
    /**
     * POST /api/mesas
     * Crear nueva mesa
     */
    @PostMapping
    public ResponseEntity<Mesa> crearMesa(@RequestBody Mesa mesa) {
        try {
            Mesa nuevaMesa = mesaService.crearMesa(mesa);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaMesa);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * PUT /api/mesas/{id}
     * Actualizar mesa
     */
    @PutMapping("/{id}")
    public ResponseEntity<Mesa> actualizarMesa(@PathVariable Long id, @RequestBody Mesa mesa) {
        try {
            Mesa mesaActualizada = mesaService.actualizarMesa(id, mesa);
            return ResponseEntity.ok(mesaActualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * PATCH /api/mesas/{id}/estado
     * Cambiar estado de mesa
     */
    @PatchMapping("/{id}/estado")
    public ResponseEntity<Mesa> cambiarEstado(
            @PathVariable Long id,
            @RequestParam EstadoMesa estado) {
        try {
            Mesa mesa = mesaService.cambiarEstado(id, estado);
            return ResponseEntity.ok(mesa);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * DELETE /api/mesas/{id}
     * Desactivar mesa (soft delete)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desactivarMesa(@PathVariable Long id) {
        try {
            mesaService.desactivarMesa(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
