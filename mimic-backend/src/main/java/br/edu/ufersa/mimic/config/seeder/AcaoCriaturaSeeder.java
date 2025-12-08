package br.edu.ufersa.mimic.config.seeder;

import br.edu.ufersa.mimic.api.dto.habilidades.AcaoCriaturaDTO;
import br.edu.ufersa.mimic.model.habilidades.AcaoCriatura;
import br.edu.ufersa.mimic.repository.habilidades.AcaoCriaturaRepository;
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
@Order(5)
public class AcaoCriaturaSeeder implements CommandLineRunner {

    @Autowired private AcaoCriaturaRepository repository;
    @Autowired private ObjectMapper objectMapper;

    @Override
    public void run(String... args) {
        if (repository.count() > 0) return;

        try {
            InputStream is = new ClassPathResource("data/acoes_criaturas.json").getInputStream();
            List<AcaoCriaturaDTO> dtos = objectMapper.readValue(is, new TypeReference<List<AcaoCriaturaDTO>>() {});

            for (AcaoCriaturaDTO dto : dtos) {
                repository.save(new AcaoCriatura(dto.nome, dto.descricao));
            }
            System.out.println("Ações de Criatura populadas!");
        } catch (Exception e) { e.printStackTrace(); }
    }
}