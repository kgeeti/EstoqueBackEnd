package br.eti.kge.estoque.exceptions;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 *
 * @author KGe
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OptimisticLockingFailureException.class)
    public ResponseEntity<Object> handleOptimisticLock(OptimisticLockingFailureException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", HttpStatus.CONFLICT.value()); // 409
        body.put("error", "Conflict");
        body.put("message", "O recurso foi alterado por outra operação simultânea. Recarregue os dados e tente novamente.");

        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }
}