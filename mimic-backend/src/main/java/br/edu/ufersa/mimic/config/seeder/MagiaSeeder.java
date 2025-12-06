package br.edu.ufersa.mimic.config.seeder;

import br.edu.ufersa.mimic.model.habilidades.Magia;
import br.edu.ufersa.mimic.repository.habilidades.MagiaRepository;
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
public class MagiaSeeder implements CommandLineRunner {

    @Autowired
    private MagiaRepository magiaRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        loadMagias();
    }

    private void loadMagias() {
        if (magiaRepository.count() > 0) {
            System.out.println("Magias já populadas. Pulando seeder.");
            return;
        }

        try {
            InputStream inputStream = new ClassPathResource("data/magias.json").getInputStream();
            List<Magia> magias = objectMapper.readValue(inputStream, new TypeReference<List<Magia>>() {});

            System.out.println("Iniciando população de magias...");

            magiaRepository.saveAll(magias);

            System.out.println(magias.size() + " magias populadas com sucesso!");

        } catch (Exception e) {
            System.err.println("Erro ao popular magias: " + e.getMessage());
            e.printStackTrace();
        }
    }
}