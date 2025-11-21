package ipss.cl.reservas.config;

import ipss.cl.reservas.models.entities.Mesa;
import ipss.cl.reservas.models.entities.Reserva;
import ipss.cl.reservas.models.enums.EstadoMesa;
import ipss.cl.reservas.models.enums.EstadoReserva;
import ipss.cl.reservas.models.enums.TipoMesa;
import ipss.cl.reservas.repositories.MesaRepository;
import ipss.cl.reservas.repositories.ReservaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalTime;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DataLoader {

    private final MesaRepository mesaRepository;
    private final ReservaRepository reservaRepository;

    @Bean
    public CommandLineRunner loadData() {
        return args -> {
            // Verificar si ya hay datos
            if (mesaRepository.count() > 0) {
                log.info("La base de datos ya contiene datos, omitiendo carga inicial");
                return;
            }

            log.info("Cargando datos de ejemplo...");

            // Crear Mesas
            Mesa mesa1 = Mesa.builder()
                    .numero(1)
                    .tipo(TipoMesa.INTERIOR)
                    .capacidadMinima(2)
                    .capacidadMaxima(4)
                    .descripcion("Mesa junto a la ventana")
                    .estado(EstadoMesa.DISPONIBLE)
                    .activa(true)
                    .build();

            Mesa mesa2 = Mesa.builder()
                    .numero(2)
                    .tipo(TipoMesa.TERRAZA)
                    .capacidadMinima(4)
                    .capacidadMaxima(6)
                    .descripcion("Mesa en la terraza con vista")
                    .estado(EstadoMesa.DISPONIBLE)
                    .activa(true)
                    .build();

            Mesa mesa3 = Mesa.builder()
                    .numero(3)
                    .tipo(TipoMesa.VIP)
                    .capacidadMinima(2)
                    .capacidadMaxima(8)
                    .descripcion("Mesa VIP privada")
                    .estado(EstadoMesa.DISPONIBLE)
                    .activa(true)
                    .build();

            Mesa mesa4 = Mesa.builder()
                    .numero(4)
                    .tipo(TipoMesa.BARRA)
                    .capacidadMinima(1)
                    .capacidadMaxima(2)
                    .descripcion("Asientos en la barra")
                    .estado(EstadoMesa.DISPONIBLE)
                    .activa(true)
                    .build();

            Mesa mesa5 = Mesa.builder()
                    .numero(5)
                    .tipo(TipoMesa.EXTERIOR)
                    .capacidadMinima(4)
                    .capacidadMaxima(6)
                    .descripcion("Mesa exterior en el jardín")
                    .estado(EstadoMesa.DISPONIBLE)
                    .activa(true)
                    .build();

            Mesa mesa6 = Mesa.builder()
                    .numero(6)
                    .tipo(TipoMesa.INTERIOR)
                    .capacidadMinima(6)
                    .capacidadMaxima(10)
                    .descripcion("Mesa grande para grupos")
                    .estado(EstadoMesa.DISPONIBLE)
                    .activa(true)
                    .build();

            mesaRepository.save(mesa1);
            mesaRepository.save(mesa2);
            mesaRepository.save(mesa3);
            mesaRepository.save(mesa4);
            mesaRepository.save(mesa5);
            mesaRepository.save(mesa6);

            log.info("Se crearon {} mesas", mesaRepository.count());

            // Crear Reservas de ejemplo
            LocalDate hoy = LocalDate.now();
            LocalDate manana = hoy.plusDays(1);
            LocalDate pasadoManana = hoy.plusDays(2);

            Reserva reserva1 = Reserva.builder()
                    .mesa(mesa1)
                    .nombreCliente("Juan Pérez")
                    .emailCliente("juan.perez@example.com")
                    .telefonoCliente("+56912345678")
                    .fecha(manana)
                    .hora(LocalTime.of(19, 0))
                    .numeroPersonas(3)
                    .observaciones("Cumpleaños, necesitamos decoración")
                    .estado(EstadoReserva.PENDIENTE)
                    .build();

            Reserva reserva2 = Reserva.builder()
                    .mesa(mesa2)
                    .nombreCliente("María González")
                    .emailCliente("maria.gonzalez@example.com")
                    .telefonoCliente("+56987654321")
                    .fecha(manana)
                    .hora(LocalTime.of(20, 30))
                    .numeroPersonas(5)
                    .observaciones("Preferencia por terraza")
                    .estado(EstadoReserva.CONFIRMADA)
                    .build();

            Reserva reserva3 = Reserva.builder()
                    .mesa(mesa3)
                    .nombreCliente("Carlos Rodríguez")
                    .emailCliente("carlos.rodriguez@example.com")
                    .telefonoCliente("+56911111111")
                    .fecha(pasadoManana)
                    .hora(LocalTime.of(21, 0))
                    .numeroPersonas(6)
                    .observaciones("Cena de negocios")
                    .estado(EstadoReserva.PENDIENTE)
                    .build();

            Reserva reserva4 = Reserva.builder()
                    .mesa(mesa5)
                    .nombreCliente("Ana Martínez")
                    .emailCliente("ana.martinez@example.com")
                    .telefonoCliente("+56922222222")
                    .fecha(hoy)
                    .hora(LocalTime.of(13, 0))
                    .numeroPersonas(4)
                    .observaciones("Almuerzo de trabajo")
                    .estado(EstadoReserva.CONFIRMADA)
                    .build();

            Reserva reserva5 = Reserva.builder()
                    .mesa(mesa6)
                    .nombreCliente("Pedro Sánchez")
                    .emailCliente("pedro.sanchez@example.com")
                    .telefonoCliente("+56933333333")
                    .fecha(pasadoManana)
                    .hora(LocalTime.of(19, 30))
                    .numeroPersonas(8)
                    .observaciones("Reunión familiar")
                    .estado(EstadoReserva.PENDIENTE)
                    .build();

            reservaRepository.save(reserva1);
            reservaRepository.save(reserva2);
            reservaRepository.save(reserva3);
            reservaRepository.save(reserva4);
            reservaRepository.save(reserva5);

            log.info("Se crearon {} reservas", reservaRepository.count());
            log.info("Datos de ejemplo cargados exitosamente!");
            log.info("La aplicación está lista en http://localhost:8080");
            log.info("Consola H2 disponible en http://localhost:8080/h2-console");
        };
    }
}
