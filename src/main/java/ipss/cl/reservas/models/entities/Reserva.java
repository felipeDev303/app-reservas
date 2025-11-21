package ipss.cl.reservas.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ipss.cl.reservas.models.enums.EstadoReserva;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservas")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // DATOS DEL CLIENTE (sin relación - campos simples)
    @Column(nullable = false, length = 100)
    private String nombreCliente;
    
    @Column(nullable = false, length = 100)
    private String emailCliente;
    
    @Column(nullable = false, length = 20)
    private String telefonoCliente;
    
    // RELACIÓN CON MESA
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mesa_id", nullable = false)
    @JsonBackReference
    private Mesa mesa;
    
    // DATOS DE LA RESERVA
    @Column(nullable = false)
    private LocalDate fecha;
    
    @Column(nullable = false)
    private LocalTime hora;
    
    @Column(nullable = false)
    private Integer numeroPersonas;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoReserva estado;
    
    @Column(length = 500)
    private String observaciones;
    
    // CÓDIGO ÚNICO DE RESERVA
    @Column(unique = true, length = 20)
    private String codigoReserva;
    
    // AUDITORÍA
    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;
    
    private LocalDateTime fechaModificacion;
    
    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        if (estado == null) {
            estado = EstadoReserva.PENDIENTE;
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        fechaModificacion = LocalDateTime.now();
    }
}
