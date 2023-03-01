package com.crud.venda.api.handler;

import com.crud.venda.api.model.Erro;
import com.crud.venda.api.model.Response;
import com.crud.venda.application.exceptions.ApplicationException;
import com.crud.venda.application.exceptions.ClienteNaoEncontradoException;
import com.crud.venda.infrastructure.exceptions.InfrastructureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ControllerAdviceHandler {

    public static final String ERRO_GENERICO = "erro.erro-generico";
    public static final String ERRO_PREENCHIMENTO = "erro.preenchimento-incorreto";

    private final MessageSource messageSource;

    @ExceptionHandler(ClienteNaoEncontradoException.class)
    public ResponseEntity<Response> applicationException(ClienteNaoEncontradoException applicationException) {

        log.error("Houve um erro de aplicação.", applicationException);

        Erro erro = Erro.builder()
                .codigo(applicationException.getCodigo())
                .mensagem(messageSource.getMessage(applicationException.getCodigo(), applicationException.getParametros(), LocaleContextHolder.getLocaleContext().getLocale()))
                .build();

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Response.comErro(erro));
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<Response> applicationException(ApplicationException applicationException) {

        log.error("Houve um erro de aplicação.", applicationException);

        Erro erro = Erro.builder()
                .codigo(applicationException.getCodigo())
                .mensagem(messageSource.getMessage(applicationException.getCodigo(), applicationException.getParametros(), LocaleContextHolder.getLocaleContext().getLocale()))
                .build();

        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(Response.comErro(erro));
    }

    @ExceptionHandler(InfrastructureException.class)
    public ResponseEntity<Response> infrastructureHandler(InfrastructureException infrastructureException) {

        log.error("Houve um erro de infraestrutura.", infrastructureException);

        Erro erro = Erro.builder()
                .codigo(infrastructureException.getCodigo())
                .mensagem(messageSource.getMessage(infrastructureException.getCodigo(), infrastructureException.getParametros(), LocaleContextHolder.getLocaleContext().getLocale()))
                .build();

        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(Response.comErro(erro));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> exceptionMethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException methodArgumentNotValidException) {

        log.error("Houve um erro de preenchimento dos dados.", methodArgumentNotValidException);

        List<Erro> erros = methodArgumentNotValidException.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> Erro.builder().codigo(ERRO_PREENCHIMENTO).mensagem(fieldError.getDefaultMessage()).build())
                .toList();

        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(Response.comErros(erros));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> exceptionHandler(Exception exception) {

        log.error("Houve um erro genérico.", exception);

        Erro erro = Erro.builder()
                .codigo(ERRO_GENERICO)
                .mensagem(messageSource.getMessage(ERRO_GENERICO, null, LocaleContextHolder.getLocaleContext().getLocale()))
                .build();

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Response.comErro(erro));
    }
}
