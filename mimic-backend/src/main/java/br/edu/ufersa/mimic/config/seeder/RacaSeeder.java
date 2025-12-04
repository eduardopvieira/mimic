package br.edu.ufersa.mimic.config.seeder;

import br.edu.ufersa.mimic.model.caracteristicas.Raca;
import br.edu.ufersa.mimic.model.caracteristicas.TracoRacial;
import br.edu.ufersa.mimic.repository.caracteristicas.RacaRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.List;

@Component
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
            // Carrega o arquivo racas.json da pasta resources/data/
            InputStream inputStream = new ClassPathResource("/data/racas.json").getInputStream();
            List<Raca> racas = objectMapper.readValue(inputStream, new TypeReference<List<Raca>>() {});

            System.out.println("Iniciando população de raças (espécies)...");

            for (Raca raca : racas) {
                if (raca.getTracosRaciais() != null) {
                    for (TracoRacial traco : raca.getTracosRaciais()) {
                        traco.setRaca(raca);
                    }
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