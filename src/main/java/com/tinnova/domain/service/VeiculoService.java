package com.tinnova.domain.service;

import com.tinnova.domain.exception.CustomException;
import com.tinnova.domain.model.Veiculo;
import com.tinnova.rest.dto.request.VeiculoPartialRequest;
import com.tinnova.rest.dto.request.VeiculoRequest;
import com.tinnova.rest.dto.response.VeiculoResponse;
import com.tinnova.rest.dto.response.VeiculosPorFabricanteDTO;

import java.util.List;
import java.util.Map;

public interface VeiculoService {

    List<VeiculoResponse> veiculosCriadosUltimaSemana();

    Map<String, Long> distribuicaoPorDecada();

    List<VeiculosPorFabricanteDTO> buscaVeiculosPorFabricante();

    List<VeiculoResponse> buscaVeiculosNaoVendidos();

    List<VeiculoResponse> findByParamsOrFindAll(String marca, Integer ano, String cor);

    Veiculo findById(Long id) throws CustomException;

    void create(VeiculoRequest request) throws CustomException;

    VeiculoResponse update(Long id, VeiculoRequest request) throws CustomException;

    VeiculoResponse updatePartial(Long id, VeiculoPartialRequest partialRequest) throws CustomException;

    void delete(Long id) throws CustomException;
}
