package br.edu.ufersa.mimic.config.seeder;

import br.edu.ufersa.mimic.model.habilidades.Talento;
import br.edu.ufersa.mimic.repository.habilidades.TalentoRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Order(1)
@Configuration
public class TalentoSeeder implements CommandLineRunner {

    private final TalentoRepository talentoRepository;
    private final ObjectMapper objectMapper;

    public TalentoSeeder(TalentoRepository talentoRepository, ObjectMapper objectMapper) {
        this.talentoRepository = talentoRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        carregarTalentos();
    }

    private void carregarTalentos() {
        if (talentoRepository.count() == 0) {
            try {
                InputStream inputStream = TypeReference.class.getResourceAsStream("/data/talentos.json");

                List<Talento> talentos = objectMapper.readValue(inputStream, new TypeReference<List<Talento>>(){});

                talentoRepository.saveAll(talentos);

                System.out.println("MIMIC: Talentos carregados com sucesso! Total: " + talentos.size());
            } catch (IOException e) {
                System.out.println("MIMIC: Falha ao carregar talentos: " + e.getMessage());
            }
        } else {
            System.out.println("MIMIC: Banco de talentos já populado.");
        }
    }
}