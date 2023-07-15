package com.tinnova.domain.repository;

import com.tinnova.domain.model.Veiculo;
import com.tinnova.rest.dto.response.VeiculosPorFabricanteDTO;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@ApplicationScoped
public class VeiculoRepository implements PanacheRepository<Veiculo> {

    @Inject
    EntityManager em;

    public List<Veiculo> findByParamsOrFindAll(String marca, Integer ano, String cor) {
        var params =
                Parameters.with("marca", marca)
                        .and("ano", ano)
                        .and("cor", cor).map();

        return find("Select v from Veiculo v where (:marca is null or marca = :marca) and (:ano is null or ano = :ano) and (:cor is null or cor = :cor)", params).list();
    }

    public Map<String, Long> distribuicaoPorDecada() {
        List<Object[]> results = em.createQuery(
                "SELECT CONCAT(SUBSTRING(CAST(v.ano AS STRING), 1, 3), '0s') AS decada, COUNT(v.id) " +
                        "FROM Veiculo v " +
                        "GROUP BY decada ").getResultList();

        Map<String, Long> map = new TreeMap<>(Comparator.reverseOrder());

        for (Object[] result : results) {
            String decada = (String) result[0];
            Long quantidade = (Long) result[1];
            map.put(decada, quantidade);
        }

        return map;
    }

    public List<VeiculosPorFabricanteDTO> buscaVeiculosPorFabricante() {
        return em.createQuery("SELECT new com.tinnova.rest.dto.response.VeiculosPorFabricanteDTO(v.marca, count(v.id)) " +
                "FROM Veiculo v " +
                "GROUP BY v.marca", VeiculosPorFabricanteDTO.class).getResultList();
    }

    public List<Veiculo> veiculosCriadosUltimaSemana() {
        LocalDateTime lastWeek = LocalDateTime.now().minusWeeks(1);
        return find("Select v from Veiculo v where v.created >= :lastWeek", Parameters.with("lastWeek", lastWeek)).list();
    }
}
