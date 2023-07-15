package com.tinnova.rest.dto.response;

import com.tinnova.domain.model.Veiculo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class VeiculoResponse {

    private Long id;
    private String veiculo;
    private String marca;
    private Integer ano;
    private String descricao;
    private Boolean vendido;
    private LocalDateTime created;
    private LocalDateTime updated;
    private String cor;

    public static VeiculoResponse createByEntity(Veiculo entity) {
        return VeiculoResponse.builder()
                .id(entity.getId())
                .veiculo(entity.getVeiculo())
                .marca(entity.getMarca())
                .ano(entity.getAno())
                .descricao(entity.getDescricao())
                .vendido(entity.getVendido())
                .created(entity.getCreated())
                .updated(entity.getUpdated())
                .cor(entity.getCor())
                .build();
    }
}
