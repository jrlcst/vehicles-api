package com.tinnova.rest.resource;

import com.tinnova.domain.exception.CustomException;
import com.tinnova.domain.service.VeiculoService;
import com.tinnova.rest.dto.request.VeiculoPartialRequest;
import com.tinnova.rest.dto.request.VeiculoRequest;
import com.tinnova.rest.dto.response.VeiculoResponse;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/api/veiculos")
public class VeiculoResource {

    @Inject
    VeiculoService service;

    @GET
    @Path("/veiculosCriadosUltimaSemana")
    public Response veiculosCriadosUltimaSemana() {
        return Response.ok(service.veiculosCriadosUltimaSemana()).build();
    }

    @GET
    @Path("/distribuicaoPorDecada")
    public Response distribuicaoPorDecada() {
        return Response.ok(service.distribuicaoPorDecada()).build();
    }

    @GET
    @Path("/buscaVeiculosPorFabricante")
    public Response buscaVeiculosPorFabricante() {
        return Response.ok(service.buscaVeiculosPorFabricante()).build();
    }

    @GET
    @Path("/buscaVeiculosNaoVendidos")
    public Response buscaVeiculosNaoVendidos() {
        return Response.ok(service.buscaVeiculosNaoVendidos()).build();
    }

    @GET
    public Response findByParamsOrFindAll(@QueryParam("marca") String marca,
                                 @QueryParam("ano") Integer ano,
                                 @QueryParam("cor") String cor){
        return Response.ok(service.findByParamsOrFindAll(marca, ano, cor)).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) throws CustomException {
        return Response.ok(VeiculoResponse.createByEntity(service.findById(id))).build();
    }

    @POST
    @Transactional
    public Response create(VeiculoRequest request) throws CustomException {
        service.create(request);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, VeiculoRequest request) throws CustomException {
        return Response.ok(service.update(id, request)).build();
    }

    @PATCH
    @Path("/{id}")
    @Transactional
    public Response updateVenda(@PathParam("id") Long id, VeiculoPartialRequest partialRequest) throws CustomException {
        return Response.ok(service.updatePartial(id, partialRequest)).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) throws CustomException {
        service.delete(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
