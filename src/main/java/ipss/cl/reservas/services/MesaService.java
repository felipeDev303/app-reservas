package ipss.cl.reservas.services;

import ipss.cl.reservas.models.entities.Mesa;
import ipss.cl.reservas.models.enums.EstadoMesa;
import ipss.cl.reservas.models.enums.TipoMesa;
import ipss.cl.reservas.repositories.MesaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class MesaService {
    
    private final MesaRepository mesaRepository;
    
    /**
     * Listar todas las mesas activas
     */
    public List<Mesa> listarMesas() {
        return mesaRepository.findByActivaTrue();
    }
    
    /**
     * Obtener mesa por ID
     */
    public Optional<Mesa> obtenerMesaPorId(Long id) {
        return mesaRepository.findById(id);
    }
    
    /**
     * Obtener mesa por número
     */
    public Optional<Mesa> obtenerMesaPorNumero(Integer numero) {
        return mesaRepository.findByNumero(numero);
    }
    
    /**
     * Listar mesas por estado
     */
    public List<Mesa> listarMesasPorEstado(EstadoMesa estado) {
        return mesaRepository.findByEstadoAndActivaTrue(estado);
    }
    
    /**
     * Listar mesas por tipo
     */
    public List<Mesa> listarMesasPorTipo(TipoMesa tipo) {
        return mesaRepository.findByTipoAndActivaTrue(tipo);
    }
    
    /**
     * Listar mesas por capacidad
     */
    public List<Mesa> listarMesasPorCapacidad(Integer personas) {
        return mesaRepository.findByCapacidadMinimaLessThanEqualAndCapacidadMaximaGreaterThanEqualAndActivaTrue(
                personas, personas);
    }
    
    /**
     * Crear nueva mesa
     */
    @Transactional
    public Mesa crearMesa(Mesa mesa) {
        log.info("Creando nueva mesa número: {}", mesa.getNumero());
        
        // Verificar que no exista una mesa con ese número
        if (mesaRepository.findByNumero(mesa.getNumero()).isPresent()) {
            throw new IllegalArgumentException("Ya existe una mesa con el número " + mesa.getNumero());
        }
        
        // Establecer valores por defecto
        if (mesa.getEstado() == null) {
            mesa.setEstado(EstadoMesa.DISPONIBLE);
        }
        if (mesa.getActiva() == null) {
            mesa.setActiva(true);
        }
        
        return mesaRepository.save(mesa);
    }
    
    /**
     * Actualizar mesa
     */
    @Transactional
    public Mesa actualizarMesa(Long id, Mesa mesaActualizada) {
        log.info("Actualizando mesa ID: {}", id);
        
        Mesa mesa = mesaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Mesa no encontrada con ID: " + id));
        
        // Actualizar campos
        if (mesaActualizada.getNumero() != null && !mesaActualizada.getNumero().equals(mesa.getNumero())) {
            // Verificar que no exista otra mesa con ese número
            mesaRepository.findByNumero(mesaActualizada.getNumero())
                    .ifPresent(m -> {
                        if (!m.getId().equals(id)) {
                            throw new IllegalArgumentException("Ya existe otra mesa con el número " + mesaActualizada.getNumero());
                        }
                    });
            mesa.setNumero(mesaActualizada.getNumero());
        }
        
        if (mesaActualizada.getCapacidadMinima() != null) {
            mesa.setCapacidadMinima(mesaActualizada.getCapacidadMinima());
        }
        if (mesaActualizada.getCapacidadMaxima() != null) {
            mesa.setCapacidadMaxima(mesaActualizada.getCapacidadMaxima());
        }
        if (mesaActualizada.getTipo() != null) {
            mesa.setTipo(mesaActualizada.getTipo());
        }
        if (mesaActualizada.getEstado() != null) {
            mesa.setEstado(mesaActualizada.getEstado());
        }
        if (mesaActualizada.getDescripcion() != null) {
            mesa.setDescripcion(mesaActualizada.getDescripcion());
        }
        
        return mesaRepository.save(mesa);
    }
    
    /**
     * Cambiar estado de mesa
     */
    @Transactional
    public Mesa cambiarEstado(Long id, EstadoMesa nuevoEstado) {
        log.info("Cambiando estado de mesa ID {} a {}", id, nuevoEstado);
        
        Mesa mesa = mesaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Mesa no encontrada con ID: " + id));
        
        mesa.setEstado(nuevoEstado);
        return mesaRepository.save(mesa);
    }
    
    /**
     * Desactivar mesa (soft delete)
     */
    @Transactional
    public void desactivarMesa(Long id) {
        log.info("Desactivando mesa ID: {}", id);
        
        Mesa mesa = mesaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Mesa no encontrada con ID: " + id));
        
        mesa.setActiva(false);
        mesaRepository.save(mesa);
    }
    
    /**
     * Contar mesas por estado
     */
    public Long contarMesasPorEstado(EstadoMesa estado) {
        return mesaRepository.countByEstadoAndActivaTrue(estado);
    }
    
    /**
     * Contar total de mesas activas
     */
    public Long contarMesasActivas() {
        return mesaRepository.countByActivaTrue();
    }
}
