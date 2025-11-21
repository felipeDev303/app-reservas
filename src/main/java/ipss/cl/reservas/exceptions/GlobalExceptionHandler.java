package ipss.cl.reservas.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    
    /**
     * Manejo de errores de validación
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errores.put(fieldName, errorMessage);
        });
        
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Error de validación");
        response.put("errores", errores);
        
        log.error("Error de validación: {}", errores);
        
        return ResponseEntity.badRequest().body(response);
    }
    
    /**
     * Manejo de reserva no encontrada
     */
    @ExceptionHandler(ReservaNoEncontradaException.class)
    public ResponseEntity<Map<String, Object>> handleReservaNoEncontrada(
            ReservaNoEncontradaException ex) {
        
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.NOT_FOUND.value());
        response.put("error", "Reserva no encontrada");
        response.put("mensaje", ex.getMessage());
        
        log.error("Reserva no encontrada: {}", ex.getMessage());
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
    
    /**
     * Manejo de mesa no disponible
     */
    @ExceptionHandler(MesaNoDisponibleException.class)
    public ResponseEntity<Map<String, Object>> handleMesaNoDisponible(
            MesaNoDisponibleException ex) {
        
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.CONFLICT.value());
        response.put("error", "Mesa no disponible");
        response.put("mensaje", ex.getMessage());
        
        log.error("Mesa no disponible: {}", ex.getMessage());
        
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }
    
    /**
     * Manejo de argumentos inválidos
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(
            IllegalArgumentException ex) {
        
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Argumento inválido");
        response.put("mensaje", ex.getMessage());
        
        log.error("Argumento inválido: {}", ex.getMessage());
        
        return ResponseEntity.badRequest().body(response);
    }
    
    /**
     * Manejo de estado ilegal
     */
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalState(
            IllegalStateException ex) {
        
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.CONFLICT.value());
        response.put("error", "Estado inválido");
        response.put("mensaje", ex.getMessage());
        
        log.error("Estado inválido: {}", ex.getMessage());
        
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }
    
    /**
     * Manejo de excepciones genéricas
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("error", "Error interno del servidor");
        response.put("mensaje", "Ha ocurrido un error inesperado");
        
        log.error("Error inesperado: ", ex);
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
