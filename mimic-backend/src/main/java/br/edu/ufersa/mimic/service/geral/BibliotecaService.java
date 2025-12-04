package br.edu.ufersa.mimic.service.geral;

import br.edu.ufersa.mimic.api.dto.caracteristicas.*;
import br.edu.ufersa.mimic.api.dto.habilidades.TalentoDTO;
import br.edu.ufersa.mimic.model.auth.Usuario;
import br.edu.ufersa.mimic.model.caracteristicas.Classe;
import br.edu.ufersa.mimic.model.caracteristicas.Origem;
import br.edu.ufersa.mimic.model.caracteristicas.Subclasse;
import br.edu.ufersa.mimic.repository.caracteristicas.*;
import br.edu.ufersa.mimic.repository.habilidades.TalentoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BibliotecaService {

    // Injeção de todos os Repositórios de Regras
    @Autowired private ClasseRepository classeRepository;
    @Autowired private SubclasseRepository subclasseRepository;
    @Autowired private RacaRepository racaRepository;
    @Autowired private OrigemRepository origemRepository;
    @Autowired private TalentoRepository talentoRepository;
    @Autowired private CaracteristicasDeClasseRepository caracteristicaRepository;

    // =========================================================================
    //                              CLASSES
    // =========================================================================

    @Transactional(readOnly = true)
    public List<ClasseDTO> listarTodasClasses() {
        return classeRepository.findAll().stream()
                .map(ClasseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ClasseDTO buscarClassePorId(Long id) {
        return classeRepository.findById(id)
                .map(ClasseDTO::new)
                .orElseThrow(() -> new EntityNotFoundException("Classe não encontrada com id: " + id));
    }

    // =========================================================================
    //                             SUBCLASSES
    // =========================================================================

    @Transactional(readOnly = true)
    public List<SubclasseDTO> listarSubclassesPorClasse(Long classeId) {
        Classe classe = classeRepository.findById(classeId)
                .orElseThrow(() -> new EntityNotFoundException("Classe não encontrada com id: " + classeId));

        return subclasseRepository.findByClassePai(classe).stream()
                .map(SubclasseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SubclasseDTO buscarSubclassePorId(Long id) {
        return subclasseRepository.findById(id)
                .map(SubclasseDTO::new)
                .orElseThrow(() -> new EntityNotFoundException("Subclasse não encontrada com id: " + id));
    }

    // =========================================================================
    //                   CARACTERÍSTICAS (FEATURES) - LÓGICA DO PDF
    // =========================================================================

    /**
     * Este é o método mais importante para o preenchimento automático.
     * Ele retorna apenas as habilidades que o personagem já ganhou no nível atual.
     */
    @Transactional(readOnly = true)
    public List<CaracteristicaDeClasseDTO> listarCaracteristicasDesbloqueadas(Long classeId, Long subclasseId, Integer nivelPersonagem) {
        List<CaracteristicaDeClasseDTO> resultado = new ArrayList<>();

        // 1. Busca Features da CLASSE base até o nível atual
        Classe classe = classeRepository.findById(classeId)
                .orElseThrow(() -> new EntityNotFoundException("Classe não encontrada: " + classeId));

        var featuresClasse = caracteristicaRepository.findByClasseAndNivelAdquiridoLessThanEqual(classe, nivelPersonagem);

        resultado.addAll(featuresClasse.stream()
                .map(CaracteristicaDeClasseDTO::new)
                .collect(Collectors.toList()));

        // 2. Se tiver SUBCLASSE, busca features dela até o nível atual
        if (subclasseId != null) {
            Subclasse subclasse = subclasseRepository.findById(subclasseId)
                    .orElseThrow(() -> new EntityNotFoundException("Subclasse não encontrada: " + subclasseId));

            var featuresSubclasse = caracteristicaRepository.findBySubclasseAndNivelAdquiridoLessThanEqual(subclasse, nivelPersonagem);

            resultado.addAll(featuresSubclasse.stream()
                    .map(CaracteristicaDeClasseDTO::new)
                    .collect(Collectors.toList()));
        }

        return resultado;
    }

    // =========================================================================
    //                              RAÇAS (ESPÉCIES)
    // =========================================================================

    @Transactional(readOnly = true)
    public List<RacaDTO> listarTodasRacas() {
        return racaRepository.findAll().stream()
                .map(RacaDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public RacaDTO buscarRacaPorId(Long id) {
        return racaRepository.findById(id)
                .map(RacaDTO::new)
                .orElseThrow(() -> new EntityNotFoundException("Espécie (Raça) não encontrada com id: " + id));
    }

    // =========================================================================
    //                              TALENTOS (LIVRO)
    // =========================================================================

    @Transactional(readOnly = true)
    public List<TalentoDTO> listarTodosTalentos() {
        // Como você decidiu que Talentos são "apenas do livro", usamos findAll simples
        return talentoRepository.findAll().stream()
                .map(TalentoDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TalentoDTO buscarTalentoPorId(Long id) {
        return talentoRepository.findById(id)
                .map(TalentoDTO::new)
                .orElseThrow(() -> new EntityNotFoundException("Talento não encontrado com id: " + id));
    }
}