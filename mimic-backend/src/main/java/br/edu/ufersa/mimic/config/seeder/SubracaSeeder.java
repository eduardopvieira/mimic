package br.edu.ufersa.mimic.config.seeder;

import br.edu.ufersa.mimic.model.caracteristicas.Raca;
import br.edu.ufersa.mimic.model.caracteristicas.Subraca;
import br.edu.ufersa.mimic.model.caracteristicas.TracoRacial;
import br.edu.ufersa.mimic.model.habilidades.Magia;
import br.edu.ufersa.mimic.repository.caracteristicas.RacaRepository;
import br.edu.ufersa.mimic.repository.caracteristicas.SubracaRepository;
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

@Order(2)
@Component
public class SubracaSeeder implements CommandLineRunner {

    @Autowired private SubracaRepository subracaRepository;
    @Autowired private RacaRepository racaRepository;
    @Autowired private TracoRacialRepository tracoRepository; // Caso precise salvar manualmente
    @Autowired private MagiaRepository magiaRepository;
    @Autowired private ObjectMapper objectMapper;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (subracaRepository.count() > 0) return;

        try {
            InputStream is = new ClassPathResource("data/subracas.json").getInputStream();
            List<SubracaDTO> dtos = objectMapper.readValue(is, new TypeReference<List<SubracaDTO>>() {});

            for (SubracaDTO dto : dtos) {
                Raca racaPai = racaRepository.findById((long) dto.racaId)
                        .orElseThrow(() -> new RuntimeException("Raça ID " + dto.racaId + " não encontrada"));

                Subraca sub = new Subraca();
                sub.setNome(dto.nome);
                sub.setRaca(racaPai);

                sub = subracaRepository.save(sub);

                // Processa os traços específicos da sub-raça
                if (dto.tracos != null) {
                    for (TracoDTO tracoDto : dto.tracos) {
                        TracoRacial traco = new TracoRacial();
                        traco.setNome(tracoDto.nome);
                        traco.setDescricao(tracoDto.descricao);
                        traco.setSubraca(sub);

                        if (tracoDto.magiaNome != null) {
                            List<Magia> magias = magiaRepository.findByNomeContainingIgnoreCase(tracoDto.magiaNome);
                            if (!magias.isEmpty()) traco.setMagiaConcedida(magias.get(0));
                        }

                        tracoRepository.save(traco);
                    }
                }
                System.out.println("Sub-raça salva: " + sub.getNome());
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    // DTOs Internos
    private static class SubracaDTO {
        public int racaId;
        public String nome;
        public List<TracoDTO> tracos;
    }
    private static class TracoDTO {
        public String nome;
        public String descricao;
        public String magiaNome;
    }
}