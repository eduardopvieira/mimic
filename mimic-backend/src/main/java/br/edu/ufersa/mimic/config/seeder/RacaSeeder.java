package br.edu.ufersa.mimic.config.seeder;

import br.edu.ufersa.mimic.model.caracteristicas.Raca;
import br.edu.ufersa.mimic.repository.caracteristicas.RacaRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order; // Importante
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Component
@Order(1) // Garante que as Raças sejam criadas antes de Sub-raças e Traços
public class RacaSeeder implements CommandLineRunner {

    @Autowired
    private RacaRepository racaRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        loadRacas();
    }

    private void loadRacas() {
        if (racaRepository.count() > 0) {
            System.out.println("Raças já populadas. Pulando seeder.");
            return;
        }

        try {
            InputStream inputStream = new ClassPathResource("data/racas.json").getInputStream();
            List<Raca> racas = objectMapper.readValue(inputStream, new TypeReference<List<Raca>>() {});

            System.out.println("Iniciando população de raças...");

            for (Raca raca : racas) {
                raca.setId(null);

                Optional<Raca> existente = racaRepository.findByNome(raca.getNome());
                if (existente.isPresent()) {
                    continue;
                }

                racaRepository.save(raca);
                System.out.println("Raça salva: " + raca.getNome());
            }

            System.out.println("Raças populadas com sucesso!");

        } catch (Exception e) {
            System.err.println("Erro ao popular raças: " + e.getMessage());
            e.printStackTrace();
        }
    }
}