package br.edu.ufersa.mimic.config.seeder;

import br.edu.ufersa.mimic.model.habilidades.HabilidadeCriatura;
import br.edu.ufersa.mimic.repository.habilidades.HabilidadeCriaturaRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component
@Order(4)
public class HabilidadeCriaturaSeeder implements CommandLineRunner {

    @Autowired private HabilidadeCriaturaRepository repository;
    @Autowired private ObjectMapper objectMapper;

    @Override
    public void run(String... args) {
        if (repository.count() > 0) return;

        try {
            InputStream is = new ClassPathResource("data/habilidades_criaturas.json").getInputStream();
            List<HabilidadeDTO> dtos = objectMapper.readValue(is, new TypeReference<List<HabilidadeDTO>>() {});

            for (HabilidadeDTO dto : dtos) {
                repository.save(new HabilidadeCriatura(dto.nome, dto.descricao));
            }
            System.out.println("Habilidades de Criatura populadas!");
        } catch (Exception e) { e.printStackTrace(); }
    }

    private static class HabilidadeDTO {
        public String nome;
        public String descricao;
    }
}