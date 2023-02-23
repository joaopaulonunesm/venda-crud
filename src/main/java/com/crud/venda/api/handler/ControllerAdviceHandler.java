package com.crud.venda.api.handler;

import com.crud.venda.application.exceptions.ApplicationException;
import com.crud.venda.infrastructure.exceptions.InfrastructureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ControllerAdviceHandler {

    public static final String ERRO_GENERICO = "erro.erro-generico";

    private final MessageSource messageSource;

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<Erro> applicationException(ApplicationException applicationException) {

        log.error("Houve um erro de aplicação.", applicationException);

        Erro erro = Erro.builder()
                .codigo(applicationException.getCodigo())
                .mensagem(messageSource.getMessage(applicationException.getCodigo(), applicationException.getParametros(), LocaleContextHolder.getLocaleContext().getLocale()))
                .build();

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(erro);
    }

    @ExceptionHandler(InfrastructureException.class)
    public ResponseEntity<Erro> infrastructureHandler(InfrastructureException infrastructureException) {

        log.error("Houve um erro de infraestrutura.", infrastructureException);

        Erro erro = Erro.builder()
                .codigo(infrastructureException.getCodigo())
                .mensagem(messageSource.getMessage(infrastructureException.getCodigo(), infrastructureException.getParametros(), LocaleContextHolder.getLocaleContext().getLocale()))
                .build();

        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(erro);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Erro> exceptionHandler(Exception exception) {

        log.error("Houve um erro genérico.", exception);

        Erro erro = Erro.builder()
                .codigo(ERRO_GENERICO)
                .mensagem(messageSource.getMessage(ERRO_GENERICO, null, LocaleContextHolder.getLocaleContext().getLocale()))
                .build();

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(erro);
    }
}
