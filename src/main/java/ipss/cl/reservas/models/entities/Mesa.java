package ipss.cl.reservas.models.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ipss.cl.reservas.models.enums.EstadoMesa;
import ipss.cl.reservas.models.enums.TipoMesa;

import java.util.List;

@Entity
@Table(name = "mesas")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    @JsonManagedReference
    private List<Reserva> reservas;
    
    @Column(nullable = false)
    private Boolean activa = true;
}
