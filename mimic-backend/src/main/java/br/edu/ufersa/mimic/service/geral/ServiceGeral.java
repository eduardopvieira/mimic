package br.edu.ufersa.mimic.service.regras;

import br.edu.ufersa.mimic.api.dto.caracteristicas.*;
import br.edu.ufersa.mimic.api.dto.habilidades.TalentoDTO;
import br.edu.ufersa.mimic.model.caracteristicas.Classe;
import br.edu.ufersa.mimic.model.caracteristicas.Subclasse;
import br.edu.ufersa.mimic.repository.caracteristicas.*;
import br.edu.ufersa.mimic.repository.habilidades.TalentoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceGeral {

    // Repositórios de Regras Estáticas/Híbridas
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
        // Classes geralmente são fixas do sistema (Guerreiro, Mago...)
        return classeRepository.findAll().stream()
                .map(ClasseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ClasseDTO buscarClassePorId(Long id) {
        return classeRepository.findById(id)
                .map(ClasseDTO::new)
                .orElseThrow(() -> new EntityNotFoundException("Classe não encontrada: " + id));
    }

    @Transactional(readOnly = true)
    public List<SubclasseDTO> listarSubclassesPorClasse(Long classeId) {
        Classe classe = classeRepository.findById(classeId)
                .orElseThrow(() -> new EntityNotFoundException("Classe não encontrada: " + classeId));

        return subclasseRepository.findByClassePai(classe).stream()
                .map(SubclasseDTO::new)
                .collect(Collectors.toList());
    }

    // --- O MÉTODO MAIS IMPORTANTE PARA O PDF ---
    // Retorna apenas as features que o personagem já desbloqueou no nível atual
    @Transactional(readOnly = true)
    public List<CaracteristicaDeClasseDTO> listarCaracteristicasDesbloqueadas(Long classeId, Long subclasseId, Integer nivelPersonagem) {
        Classe classe = classeRepository.findById(classeId)
                .orElseThrow(() -> new EntityNotFoundException("Classe não encontrada: " + classeId));

        // 1. Pega tudo da classe até o nível X
        List<CaracteristicaDeClasseDTO> features = caracteristicaRepository
                .findByClasseAndNivelAdquiridoLessThanEqual(classe, nivelPersonagem).stream()
                .map(CaracteristicaDeClasseDTO::new)
                .collect(Collectors.toList());

        // 2. Se tiver subclasse, pega tudo da subclasse até o nível X
        if (subclasseId != null) {
            Subclasse subclasse = subclasseRepository.findById(subclasseId)
                    .orElseThrow(() -> new EntityNotFoundException("Subclasse não encontrada: " + subclasseId));

            features.addAll(caracteristicaRepository
                    .findBySubclasseAndNivelAdquiridoLessThanEqual(subclasse, nivelPersonagem).stream()
                    .map(CaracteristicaDeClasseDTO::new)
                    .collect(Collectors.toList()));
        }

        return features;
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
                .orElseThrow(() -> new EntityNotFoundException("Espécie não encontrada: " + id));
    }

    // =========================================================================
    //                       ORIGENS (ANTECEDENTES) - HÍBRIDO
    // =========================================================================

    @Transactional(readOnly = true)
    public List<OrigemDTO> listarOrigens(Long usuarioId) {
        // Implementar no Repository: findAllPublicAndUser(usuarioId)
        // Aqui assumo que OrigemRepository já tem esse método (como fizemos em Magia)
        // Se ainda não tiver, use findAll() por enquanto ou adicione a query lá.
        return origemRepository.findAll().stream() // <- Ajustar para findAllPublicAndUser quando possível
                .map(OrigemDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OrigemDTO buscarOrigemPorId(Long id) {
        return origemRepository.findById(id)
                .map(OrigemDTO::new)
                .orElseThrow(() -> new EntityNotFoundException("Origem não encontrada: " + id));
    }

    // =========================================================================
    //                              TALENTOS - HÍBRIDO
    // =========================================================================

    @Transactional(readOnly = true)
    public List<TalentoDTO> listarTalentos(Long usuarioId) {
        // Mesma lógica: Idealmente filtrar por Usuario + Publico
        return talentoRepository.findAll().stream()
                .map(TalentoDTO::new)
                .collect(Collectors.toList());
    }
}