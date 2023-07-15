package com.tinnova.domain.model;

import com.tinnova.domain.exception.CustomException;
import com.tinnova.domain.strategy.VehicleStrategy;
import com.tinnova.rest.dto.request.VeiculoPartialRequest;
import com.tinnova.rest.dto.request.VeiculoRequest;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "veiculo")
public class Veiculo {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private Long id;

    @Column(name = "veiculo")
    private String veiculo;

    @Column(name = "marca")
    private String marca;

    @Column(name = "ano")
    private Integer ano;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "vendido")
    private Boolean vendido;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "updated")
    private LocalDateTime updated;

    @Column(name = "cor")
    private String cor;
    
    public static Veiculo createByRequest(VeiculoRequest request) throws CustomException {
        return Veiculo.builder()
                .id(request.getId())
                .veiculo(request.getVeiculo())
                .marca(VehicleStrategy.validaMarca(request.getMarca()))
                .ano(VehicleStrategy.validaAno(request.getAno()))
                .descricao(request.getDescricao())
                .created(LocalDateTime.now())
                .cor(request.getCor())
                .vendido(request.getVendido())
                .build();
    }

    public static Veiculo updateByRequest(Veiculo entity, VeiculoRequest request) {
        entity.setVeiculo(request.getVeiculo());
        entity.setMarca(request.getMarca());
        entity.setAno(request.getAno());
        entity.setDescricao(request.getDescricao());
        entity.setCor(request.getCor());
        entity.setVendido(request.getVendido());
        entity.setUpdated(LocalDateTime.now());
        return entity;
    }

    public static Veiculo updateByPartialRequest(Veiculo entity, VeiculoPartialRequest partialRequest) {
        entity.setVendido(partialRequest.getVendido());
        entity.setUpdated(LocalDateTime.now());
        return entity;
    }
}
