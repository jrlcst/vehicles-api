package com.tinnova.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class VeiculosPorFabricanteDTO {
    private String marca;
    private Long quantidade;
}
