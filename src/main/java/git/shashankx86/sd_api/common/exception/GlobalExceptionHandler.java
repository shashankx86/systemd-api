package git.shashankx86.sd_api.common.exception;

import git.shashankx86.sd_api.common.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<ApiResponse<Void>> handleSecurityException(SecurityException ex) {
        return new ResponseEntity<>(
            ApiResponse.error(ex.getMessage()),
            HttpStatus.FORBIDDEN
        );
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFoundException(NoHandlerFoundException ex) {
        return new ResponseEntity<>(
            ApiResponse.error("Resource not found"),
            HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Void>> handleRuntimeException(RuntimeException ex) {
        return new ResponseEntity<>(
            ApiResponse.error(ex.getMessage()),
            HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception ex) {
        return new ResponseEntity<>(
            ApiResponse.error("Internal server error"),
            HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}