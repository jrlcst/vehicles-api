package com.tinnova.rest.resource;

import com.tinnova.domain.exception.CustomException;
import com.tinnova.domain.model.Veiculo;
import com.tinnova.domain.service.VeiculoService;
import com.tinnova.rest.dto.request.VeiculoRequest;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


@QuarkusTest
public class VeiculoResourceTest {

    @InjectMock
    VeiculoService veiculoServiceMock;

    @Test
    @Transactional
    public void testCreateSuccess() throws CustomException {
        VeiculoRequest request = VeiculoRequest.builder()
                .veiculo("UNO")
                .marca("FIAT")
                .ano(2000)
                .descricao("20000km")
                .vendido(false)
                .cor("verde")
                .build();

        Mockito.doNothing().when(veiculoServiceMock).create(request);

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .post("/api/veiculos")
                .then()
                .statusCode(201);
    }

    @Test
    @Transactional
    public void testCreateFailedByMarcaValidation() {
        VeiculoRequest request = VeiculoRequest.builder()
                .veiculo("UNO")
                .marca("FIATE")
                .ano(2000)
                .descricao("20000km")
                .vendido(false)
                .cor("verde")
                .build();

        try {
            Veiculo.createByRequest(request);
            fail("Deveria ter lançado uma CustomException");
        } catch (CustomException e) {
            assertEquals("Marca Inválida!", e.getMessage());
        }
    }

    @Test
    @Transactional
    public void testCreateFailedByAnoValidation() {
        VeiculoRequest request = VeiculoRequest.builder()
                .veiculo("UNO")
                .marca("FIAT")
                .ano(20000)
                .descricao("20000km")
                .vendido(false)
                .cor("verde")
                .build();

        try {
            Veiculo.createByRequest(request);
            fail("Deveria ter lançado uma CustomException");
        } catch (CustomException e) {
            assertEquals("Ano Inválido!", e.getMessage());
        }
    }
}
