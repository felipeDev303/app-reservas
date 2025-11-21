package ipss.cl.reservas.exceptions;

public class MesaNoDisponibleException extends RuntimeException {
    
    public MesaNoDisponibleException(String mensaje) {
        super(mensaje);
    }
    
    public MesaNoDisponibleException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
    
    public MesaNoDisponibleException(Long mesaId) {
        super("La mesa con ID " + mesaId + " no está disponible");
    }
}
