package com.tinnova.domain.strategy;

import com.tinnova.domain.exception.CustomException;
import com.tinnova.domain.type.MarcaType;

import java.util.EnumSet;

public interface VehicleStrategy {

    static Integer validaAno(Integer ano) throws CustomException {
        if (ano > 9999) {
            throw new CustomException("Ano Inválido!");
        }

        return ano;
    }

    static String validaMarca(String marca) throws CustomException {
        var marcas = EnumSet.allOf(MarcaType.class)
                .stream()
                .map(Enum::name)
                .toList();

        if(!marcas.contains(marca.toUpperCase())) {
            throw new CustomException("Marca Inválida!");
        }

        return marca;
    }
}
