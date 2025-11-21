package ipss.cl.reservas.exceptions;

public class ReservaNoEncontradaException extends RuntimeException {
    
    public ReservaNoEncontradaException(String mensaje) {
        super(mensaje);
    }
    
    public ReservaNoEncontradaException(Long id) {
        super("Reserva no encontrada con ID: " + id);
    }
    
    public ReservaNoEncontradaException(String campo, String valor) {
        super(String.format("Reserva no encontrada con %s: %s", campo, valor));
    }
}
