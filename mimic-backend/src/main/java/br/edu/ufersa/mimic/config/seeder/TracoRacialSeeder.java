package br.edu.ufersa.mimic.config.seeder;

import br.edu.ufersa.mimic.model.caracteristicas.Raca;
import br.edu.ufersa.mimic.model.caracteristicas.TracoRacial;
import br.edu.ufersa.mimic.model.habilidades.Magia;
import br.edu.ufersa.mimic.repository.caracteristicas.RacaRepository;
import br.edu.ufersa.mimic.repository.caracteristicas.TracoRacialRepository;
import br.edu.ufersa.mimic.repository.habilidades.MagiaRepository;
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

@Order(3)
@Component
public class TracoRacialSeeder implements CommandLineRunner {

    @Autowired
    private TracoRacialRepository tracoRacialRepository;

    @Autowired
    private RacaRepository racaRepository;

    @Autowired
    private MagiaRepository magiaRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        loadTracosRaciais();
    }

    private void loadTracosRaciais() {
        if (tracoRacialRepository.count() > 0) return; // Note: SubracaSeeder já pode ter populado alguns

        try {
            InputStream is = new ClassPathResource("data/tracos_raciais.json").getInputStream();
            List<TracoRacialDTO> dtos = objectMapper.readValue(is, new TypeReference<List<TracoRacialDTO>>() {});

            for (TracoRacialDTO dto : dtos) {

                Raca raca = racaRepository.findById((long) dto.racaId).orElseThrow();

                TracoRacial traco = new TracoRacial();
                traco.setNome(dto.nome);
                traco.setDescricao(dto.descricao);
                traco.setRaca(raca);
                traco.setSubraca(null); // Traço base da raça

                if (dto.magiaNome != null) {
                    List<Magia> ms = magiaRepository.findByNomeContainingIgnoreCase(dto.magiaNome);
                    if (!ms.isEmpty()) traco.setMagiaConcedida(ms.get(0));
                }

                tracoRacialRepository.save(traco);
            }
            System.out.println("Traços Base populados!");
        } catch (Exception e) { e.printStackTrace(); }
    }

    // DTO compatível com o JSON
    private static class TracoRacialDTO {
        public int racaId;
        public String nome;
        public String descricao;
        public String magiaNome;
    }
}