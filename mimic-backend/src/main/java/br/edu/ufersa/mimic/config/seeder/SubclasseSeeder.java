package br.edu.ufersa.mimic.config.seeder;

import br.edu.ufersa.mimic.model.caracteristicas.CaracteristicaDeClasse;
import br.edu.ufersa.mimic.model.caracteristicas.Classe;
import br.edu.ufersa.mimic.model.caracteristicas.Subclasse;
import br.edu.ufersa.mimic.repository.caracteristicas.ClasseRepository;
import br.edu.ufersa.mimic.repository.caracteristicas.SubclasseRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.List;

@Order(2)
@Component
public class SubclasseSeeder implements CommandLineRunner {

    @Autowired
    private SubclasseRepository subclasseRepository;

    @Autowired
    private ClasseRepository classeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        loadSubclasses();
    }

    private void loadSubclasses() {
        if (subclasseRepository.count() > 0) {
            System.out.println("Subclasses já populadas. Pulando seeder.");
            return;
        }

        try {
            InputStream inputStream = new ClassPathResource("data/subclasses.json").getInputStream();

            List<SubclasseDTO> dtos = objectMapper.readValue(inputStream, new TypeReference<List<SubclasseDTO>>() {});

            System.out.println("Iniciando população de subclasses...");

            for (SubclasseDTO dto : dtos) {
                Classe classePai = classeRepository.findById((long) dto.classePaiId)
                        .orElseThrow(() -> new RuntimeException("Classe pai não encontrada: " + dto.classePaiId));

                Subclasse subclasse = new Subclasse();
                subclasse.setNome(dto.nome);
                subclasse.setClassePai(classePai);
                subclasse.setCaracteristicas(dto.caracteristicas);

                // Vincula as características à subclasse (bidirecional)
                if (subclasse.getCaracteristicas() != null) {
                    for (CaracteristicaDeClasse c : subclasse.getCaracteristicas()) {
                        c.setSubclasse(subclasse);
                        // Nota: c.setClasse(null) é o padrão, o que está correto para características de subclasse
                    }
                }

                subclasseRepository.save(subclasse);
                System.out.println("Subclasse salva: " + subclasse.getNome() + " (Pai: " + classePai.getNome() + ")");
            }

            System.out.println("Subclasses populadas com sucesso!");

        } catch (Exception e) {
            System.err.println("Erro ao popular subclasses: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // DTO Auxiliar apenas para leitura do JSON neste arquivo
    private static class SubclasseDTO {
        public String nome;
        public int classePaiId;
        public List<CaracteristicaDeClasse> caracteristicas;
    }
}
