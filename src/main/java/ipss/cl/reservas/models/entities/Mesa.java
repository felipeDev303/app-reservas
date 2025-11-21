package ipss.cl.reservas.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import ipss.cl.reservas.models.enums.EstadoMesa;
import ipss.cl.reservas.models.enums.TipoMesa;

import java.util.List;

@Entity
@Table(name = "mesas")
@Data
public class Mesa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private Integer numero;
    
    @Column(nullable = false)
    private Integer capacidadMinima;
    
    @Column(nullable = false)
    private Integer capacidadMaxima;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoMesa tipo;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoMesa estado;
    
    @Column(length = 200)
    private String descripcion;
    
    @OneToMany(mappedBy = "mesa", cascade = CascadeType.ALL)
    private List<Reserva> reservas;
    
    @Column(nullable = false)
    private Boolean activa = true;
}
