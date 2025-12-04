package br.edu.ufersa.mimic.config.seeder;

import br.edu.ufersa.mimic.model.caracteristicas.CaracteristicaDeClasse;
import br.edu.ufersa.mimic.model.caracteristicas.Classe;
import br.edu.ufersa.mimic.model.caracteristicas.Subclasse;
import br.edu.ufersa.mimic.repository.caracteristicas.ClasseRepository;
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
public class ClasseSeeder implements CommandLineRunner {

    @Autowired
    private ClasseRepository classeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        loadClasses();
    }

    private void loadClasses() {
        if (classeRepository.count() > 0) {
            System.out.println("Classes já populadas. Pulando seeder.");
            return;
        }

        try {
            InputStream inputStream = new ClassPathResource("/data/classes.json").getInputStream();
            List<Classe> classes = objectMapper.readValue(inputStream, new TypeReference<List<Classe>>() {});

            System.out.println("Iniciando população de classes...");

            for (Classe classe : classes) {
                if (classe.getCaracteristicas() != null) {
                    for (CaracteristicaDeClasse c : classe.getCaracteristicas()) {
                        c.setClasse(classe); // Vincula a característica à classe pai
                    }
                }

                if (classe.getSubclasses() != null) {
                    for (Subclasse sub : classe.getSubclasses()) {
                        sub.setClassePai(classe);
                    }
                }

                // 3. Salvar a Classe (o CascadeType.ALL no model salvará os filhos)
                classeRepository.save(classe);
                System.out.println("Classe salva: " + classe.getNome());
            }

            System.out.println("Classes populadas com sucesso!");

        } catch (Exception e) {
            System.err.println("Erro ao popular classes: " + e.getMessage());
            e.printStackTrace();
        }
    }
}