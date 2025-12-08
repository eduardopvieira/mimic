package br.edu.ufersa.mimic.config.seeder;

import br.edu.ufersa.mimic.api.dto.auxiliar.OrigemJsonDTO;
import br.edu.ufersa.mimic.model.caracteristicas.Origem;
import br.edu.ufersa.mimic.model.habilidades.Talento;
import br.edu.ufersa.mimic.repository.caracteristicas.OrigemRepository;
import br.edu.ufersa.mimic.repository.habilidades.TalentoRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Configuration
@Order(2) // Roda DEPOIS do TalentoSeeder (que deve ser @Order(1) ou padrão)
public class OrigemSeeder implements CommandLineRunner {

    private final br.edu.ufersa.mimic.repository.caracteristicas.OrigemRepository origemRepository;
    private final br.edu.ufersa.mimic.repository.habilidades.TalentoRepository talentoRepository;
    private final ObjectMapper objectMapper;

    public OrigemSeeder(OrigemRepository origemRepository, TalentoRepository talentoRepository, ObjectMapper objectMapper) {
        this.origemRepository = origemRepository;
        this.talentoRepository = talentoRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        if (origemRepository.count() == 0) {
            try {
                InputStream inputStream = TypeReference.class.getResourceAsStream("/data/origens.json");
                List<OrigemJsonDTO> dtos = objectMapper.readValue(inputStream, new TypeReference<List<OrigemJsonDTO>>(){});

                for (OrigemJsonDTO dto : dtos) {
                    Origem origem = new Origem();
                    origem.setNome(dto.getNome());
                    origem.setDescricao(dto.getDescricao());
                    origem.setAtributosPermitidos(dto.getAtributosPermitidos());
                    origem.setPericias(dto.getPericias());
                    origem.setFerramenta(dto.getFerramenta());
                    origem.setEquipamentoA(dto.getEquipamentoA());
                    origem.setEquipamentoB(dto.getEquipamentoB());

                    Optional<Talento> talentoOpt = talentoRepository.findByNome(dto.getTalentoInicial());

                    if (talentoOpt.isPresent()) {
                        origem.setTalentoInicial(talentoOpt.get());
                        origemRepository.save(origem);
                    } else {
                        System.out.println("MIMIC AVISO: Talento '" + dto.getTalentoInicial() + "' não encontrado para a origem " + dto.getNome());
                    }
                }
                System.out.println("MIMIC: Origens carregadas com sucesso!");
            } catch (IOException e) {
                System.out.println("MIMIC: Erro ao carregar origens: " + e.getMessage());
            }
        }
    }
}