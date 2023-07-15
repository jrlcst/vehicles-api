package com.tinnova.domain.service.impl;

import com.tinnova.domain.exception.CustomException;
import com.tinnova.domain.model.Veiculo;
import com.tinnova.domain.repository.VeiculoRepository;
import com.tinnova.domain.service.VeiculoService;
import com.tinnova.rest.dto.request.VeiculoPartialRequest;
import com.tinnova.rest.dto.request.VeiculoRequest;
import com.tinnova.rest.dto.response.VeiculoResponse;
import com.tinnova.rest.dto.response.VeiculosPorFabricanteDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Map;

@ApplicationScoped
public class VeiculoServiceImpl implements VeiculoService {

    @Inject
    VeiculoRepository repository;

    @Override
    public List<VeiculoResponse> veiculosCriadosUltimaSemana() {
        return repository.veiculosCriadosUltimaSemana().stream().map(VeiculoResponse::createByEntity).toList();
    }

    @Override
    public Map<String, Long> distribuicaoPorDecada() {
        return repository.distribuicaoPorDecada();
    }

    @Override
    public List<VeiculosPorFabricanteDTO> buscaVeiculosPorFabricante() {
        return repository.buscaVeiculosPorFabricante();
    }

    @Override
    public List<VeiculoResponse> buscaVeiculosNaoVendidos() {
        return repository.find("vendido", false).stream().map(VeiculoResponse::createByEntity).toList();
    }

    @Override
    public List<VeiculoResponse> findByParamsOrFindAll(String marca, Integer ano, String cor) {
        return repository.findByParamsOrFindAll(marca, ano, cor).stream().map(VeiculoResponse::createByEntity).toList();
    }

    @Override
    public Veiculo findById(Long id) throws CustomException {
        return repository.findByIdOptional(id).orElseThrow(() -> new CustomException("Veiculo não encontrado!"));
    }

    @Override
    public void create(VeiculoRequest request) throws CustomException {
        var entity = Veiculo.createByRequest(request);
        repository.persist(entity);
    }

    @Override
    public VeiculoResponse update(Long id, VeiculoRequest request) throws CustomException {
        var entity = Veiculo.updateByRequest(findById(id), request);
        repository.persist(entity);

        return VeiculoResponse.createByEntity(entity);
    }

    @Override
    public VeiculoResponse updatePartial(Long id, VeiculoPartialRequest partialRequest) throws CustomException {
        var entity = Veiculo.updateByPartialRequest(findById(id), partialRequest);
        repository.persist(entity);

        return VeiculoResponse.createByEntity(entity);
    }

    @Override
    public void delete(Long id) throws CustomException {
        repository.delete(findById(id));
    }
}
