package com.tinnova.domain.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.time.LocalDateTime;

@Provider
public class CustomExceptionHandler implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception e) {
        if(e.getMessage().equals("Veiculo não encontrado!")){
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorMessage(e.getMessage(), LocalDateTime.now()))
                    .build();
        }
            return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorMessage(e.getMessage(), LocalDateTime.now()))
                .build();
    }

}
