package ipss.cl.reservas.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;

public class DateUtils {
    
    private static final DateTimeFormatter FECHA_FORMATTER = 
            DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    private static final DateTimeFormatter HORA_FORMATTER = 
            DateTimeFormatter.ofPattern("HH:mm");
    
    private static final DateTimeFormatter FECHA_HORA_FORMATTER = 
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    
    private static final DateTimeFormatter FECHA_COMPLETA_FORMATTER = 
            DateTimeFormatter.ofPattern("EEEE, dd 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
    
    /**
     * Formatear fecha a string
     */
    public static String formatearFecha(LocalDate fecha) {
        return fecha != null ? fecha.format(FECHA_FORMATTER) : "";
    }
    
    /**
     * Formatear hora a string
     */
    public static String formatearHora(LocalTime hora) {
        return hora != null ? hora.format(HORA_FORMATTER) : "";
    }
    
    /**
     * Formatear fecha y hora a string
     */
    public static String formatearFechaHora(LocalDateTime fechaHora) {
        return fechaHora != null ? fechaHora.format(FECHA_HORA_FORMATTER) : "";
    }
    
    /**
     * Formatear fecha completa en español
     */
    public static String formatearFechaCompleta(LocalDate fecha) {
        return fecha != null ? fecha.format(FECHA_COMPLETA_FORMATTER) : "";
    }
    
    /**
     * Obtener inicio de la semana actual
     */
    public static LocalDate inicioSemanaActual() {
        return LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    }
    
    /**
     * Obtener fin de la semana actual
     */
    public static LocalDate finSemanaActual() {
        return LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
    }
    
    /**
     * Obtener inicio del mes actual
     */
    public static LocalDate inicioMesActual() {
        return LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
    }
    
    /**
     * Obtener fin del mes actual
     */
    public static LocalDate finMesActual() {
        return LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
    }
    
    /**
     * Verificar si una fecha es hoy
     */
    public static boolean esHoy(LocalDate fecha) {
        return fecha != null && fecha.equals(LocalDate.now());
    }
    
    /**
     * Verificar si una fecha es futura
     */
    public static boolean esFutura(LocalDate fecha) {
        return fecha != null && fecha.isAfter(LocalDate.now());
    }
    
    /**
     * Verificar si una fecha es pasada
     */
    public static boolean esPasada(LocalDate fecha) {
        return fecha != null && fecha.isBefore(LocalDate.now());
    }
    
    /**
     * Calcular diferencia en días
     */
    public static long diasEntre(LocalDate inicio, LocalDate fin) {
        if (inicio == null || fin == null) {
            return 0;
        }
        return java.time.temporal.ChronoUnit.DAYS.between(inicio, fin);
    }
    
    /**
     * Verificar si está dentro del horario de atención
     */
    public static boolean estaEnHorarioAtencion(LocalTime hora) {
        if (hora == null) {
            return false;
        }
        LocalTime apertura = LocalTime.of(12, 0);
        LocalTime cierre = LocalTime.of(23, 0);
        return !hora.isBefore(apertura) && !hora.isAfter(cierre);
    }
}
