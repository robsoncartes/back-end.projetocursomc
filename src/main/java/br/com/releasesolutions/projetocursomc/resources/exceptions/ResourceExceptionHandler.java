package br.com.releasesolutions.projetocursomc.resources.exceptions;

// Classe com a finalidade de ser o manipulador de exceções dos recursos.

import br.com.releasesolutions.projetocursomc.services.exceptions.AuthorizationException;
import br.com.releasesolutions.projetocursomc.services.exceptions.DataIntegrityException;
import br.com.releasesolutions.projetocursomc.services.exceptions.FileException;
import br.com.releasesolutions.projetocursomc.services.exceptions.ObjectNotFoundException;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {

        StandardError error = new StandardError(
                System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(), "Não encontrado.", e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(DataIntegrityException.class)
    public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException e, HttpServletRequest request) {

        StandardError error = new StandardError(
                System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "Integridade de dados.", e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {

        ValidationError error = new ValidationError(
                System.currentTimeMillis(), HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de validação.", e.getMessage(), request.getRequestURI());

        for (FieldError x : e.getBindingResult().getFieldErrors())
            error.addError(x.getField(), x.getDefaultMessage());

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<StandardError> authorization(AuthorizationException e, HttpServletRequest request) {

        StandardError error = new StandardError(System.currentTimeMillis(), HttpStatus.FORBIDDEN.value(), "Acesso negado.", e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ExceptionHandler(FileException.class)
    public ResponseEntity<StandardError> fileExceptionHandler(FileException e, HttpServletRequest request) {

        StandardError error = new StandardError(
                System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "Erro de arquivo.", e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(AmazonServiceException.class)
    public ResponseEntity<StandardError> amazonServiceExceptionHandler(AmazonServiceException e, HttpServletRequest request) {

        HttpStatus code = HttpStatus.valueOf(e.getErrorCode());

        StandardError error = new StandardError(
                System.currentTimeMillis(), code.value(), "Erro Amazon Service.", e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(code).body(error);
    }

    @ExceptionHandler(AmazonClientException.class)
    public ResponseEntity<StandardError> amazonClientExceptionHandler(AmazonClientException e, HttpServletRequest request) {

        StandardError error = new StandardError(
                System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "Erro Amazon Client.", e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(AmazonS3Exception.class)
    public ResponseEntity<StandardError> amazonS3ExceptionHandler(AmazonS3Exception e, HttpServletRequest request) {

        StandardError error = new StandardError(
                System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "Erro Amazon S3.", e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}