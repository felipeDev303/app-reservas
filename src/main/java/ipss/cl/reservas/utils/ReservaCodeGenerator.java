package ipss.cl.reservas.utils;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReservaCodeGenerator {
    
    private static final String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int LONGITUD_CODIGO = 8;
    private static final SecureRandom random = new SecureRandom();
    
    /**
     * Generar código único de reserva
     * Formato: RSV-XXXXXXXX
     */
    public static String generarCodigo() {
        String codigoAleatorio = generarCodigoAleatorio(LONGITUD_CODIGO);
        return "RSV-" + codigoAleatorio;
    }
    
    /**
     * Generar código con fecha
     * Formato: RSV-YYYYMMDD-XXXX
     */
    public static String generarCodigoConFecha() {
        String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String codigoAleatorio = generarCodigoAleatorio(4);
        return "RSV-" + fecha + "-" + codigoAleatorio;
    }
    
    /**
     * Generar código alfanumérico aleatorio
     */
    private static String generarCodigoAleatorio(int longitud) {
        StringBuilder codigo = new StringBuilder(longitud);
        for (int i = 0; i < longitud; i++) {
            int index = random.nextInt(CARACTERES.length());
            codigo.append(CARACTERES.charAt(index));
        }
        return codigo.toString();
    }
    
    /**
     * Validar formato de código
     */
    public static boolean esCodigoValido(String codigo) {
        if (codigo == null || codigo.isEmpty()) {
            return false;
        }
        return codigo.matches("^RSV-[A-Z0-9]{8}$") || 
               codigo.matches("^RSV-\\d{8}-[A-Z0-9]{4}$");
    }
}
