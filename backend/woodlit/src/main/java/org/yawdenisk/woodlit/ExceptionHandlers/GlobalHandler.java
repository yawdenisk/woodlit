package org.yawdenisk.woodlit.ExceptionHandlers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.yawdenisk.woodlit.Exceptions.ProductNotFoundException;
import org.yawdenisk.woodlit.Exceptions.UserNotFoundException;

@RestControllerAdvice
public class GlobalHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity.status(e.getCode()).body(e.getMessage());
    }
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<?> handleProductNotFoundException(ProductNotFoundException e) {
        return ResponseEntity.status(e.getCode()).body(e.getMessage());
    }
}
