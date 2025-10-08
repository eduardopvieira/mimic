package br.edu.ufersa.mimic.service.fichas;

import br.edu.ufersa.mimic.dto.PersonagemDTO;
import br.edu.ufersa.mimic.model.caracteristicas.Classe;
import br.edu.ufersa.mimic.model.caracteristicas.Origem;
import br.edu.ufersa.mimic.model.caracteristicas.Raca;
import br.edu.ufersa.mimic.model.caracteristicas.Subclasse;
import br.edu.ufersa.mimic.model.equipamento.Item;
import br.edu.ufersa.mimic.model.fichas.Personagem;
import br.edu.ufersa.mimic.model.habilidades.Magia;
import br.edu.ufersa.mimic.model.habilidades.Talento;
import br.edu.ufersa.mimic.repository.caracteristicas.ClasseRepository;
import br.edu.ufersa.mimic.repository.caracteristicas.OrigemRepository;
import br.edu.ufersa.mimic.repository.caracteristicas.RacaRepository;
import br.edu.ufersa.mimic.repository.caracteristicas.SubclasseRepository;
import br.edu.ufersa.mimic.repository.equipamento.ItemRepository;
import br.edu.ufersa.mimic.repository.fichas.PersonagemRepository;
import br.edu.ufersa.mimic.repository.habilidades.MagiaRepository;
import br.edu.ufersa.mimic.repository.habilidades.TalentoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PersonagemService {

    @Autowired private PersonagemRepository personagemRepository;
    @Autowired private ClasseRepository classeRepository;
    @Autowired private SubclasseRepository subclasseRepository;
    @Autowired private RacaRepository racaRepository;
    @Autowired private OrigemRepository origemRepository;
    @Autowired private ItemRepository itemRepository;
    @Autowired private TalentoRepository talentoRepository;
    @Autowired private MagiaRepository magiaRepository;

    @Transactional
    public PersonagemDTO salvar(PersonagemDTO dto) {
        Personagem personagem = new Personagem();
        mapearDtoParaEntidade(dto, personagem);
        return new PersonagemDTO(personagemRepository.save(personagem));
    }

    @Transactional(readOnly = true)
    public List<PersonagemDTO> listarTodos() {
        return personagemRepository.findAll().stream().map(PersonagemDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PersonagemDTO buscarPorId(Long id) {
        return personagemRepository.findById(id).map(PersonagemDTO::new)
                .orElseThrow(() -> new EntityNotFoundException("Personagem não encontrado com id: " + id));
    }

    @Transactional
    public PersonagemDTO atualizar(Long id, PersonagemDTO dto) {
        Personagem personagemExistente = personagemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Personagem não encontrado com id: " + id));
        mapearDtoParaEntidade(dto, personagemExistente);
        return new PersonagemDTO(personagemRepository.save(personagemExistente));
    }

    @Transactional
    public void deletarPorId(Long id) {
        if (!personagemRepository.existsById(id)) {
            throw new EntityNotFoundException("Personagem não encontrado com id: " + id);
        }
        personagemRepository.deleteById(id);
    }

    private void mapearDtoParaEntidade(PersonagemDTO dto, Personagem personagem) {
        personagem.setNomePersonagem(dto.getNomePersonagem());
        personagem.setNivel(dto.getNivel());
        personagem.setPontosDeExperiencia(dto.getPontosDeExperiencia());
        personagem.setAlinhamento(dto.getAlinhamento());
        personagem.setForca(dto.getForca());
        personagem.setDestreza(dto.getDestreza());
        personagem.setConstituicao(dto.getConstituicao());
        personagem.setInteligencia(dto.getInteligencia());
        personagem.setSabedoria(dto.getSabedoria());
        personagem.setCarisma(dto.getCarisma());
        personagem.setPontosDeVidaMaximos(dto.getPontosDeVidaMaximos());
        personagem.setPontosDeVidaAtuais(dto.getPontosDeVidaAtuais());
        personagem.setPontosDeVidaTemporarios(dto.getPontosDeVidaTemporarios());
        personagem.setClasseDeArmadura(dto.getClasseDeArmadura());
        personagem.setIniciativa(dto.getIniciativa());
        personagem.setDeslocamento(dto.getDeslocamento());
        personagem.setPercepcaoPassiva(dto.getPercepcaoPassiva());
        personagem.setDadosDeVidaTotais(dto.getDadosDeVidaTotais());
        personagem.setDadosDeVidaGastos(dto.getDadosDeVidaGastos());
        personagem.setInspiracaoHeroica(dto.isInspiracaoHeroica());
        personagem.setProficienciasPericias(dto.getProficienciasPericias());
        personagem.setProficienciasTestesDeResistencia(dto.getProficienciasTestesDeResistencia());
        personagem.setPc(dto.getPc());
        personagem.setPp(dto.getPp());
        personagem.setPo(dto.getPo());
        personagem.setPl(dto.getPl());

        Classe classe = classeRepository.findById(dto.getClasseId())
                .orElseThrow(() -> new EntityNotFoundException("Classe não encontrada com id: " + dto.getClasseId()));
        personagem.setClasse(classe);

        Raca especie = racaRepository.findById(dto.getEspecieId())
                .orElseThrow(() -> new EntityNotFoundException("Espécie (Raça) não encontrada com id: " + dto.getEspecieId()));
        personagem.setEspecie(especie);

        Origem origem = origemRepository.findById(dto.getAntecedenteId())
                .orElseThrow(() -> new EntityNotFoundException("Origem (Antecedente) não encontrada com id: " + dto.getAntecedenteId()));
        personagem.setOrigem(origem);

        if (dto.getSubclasseId() != null) {
            Subclasse subclasse = subclasseRepository.findById(dto.getSubclasseId())
                    .orElseThrow(() -> new EntityNotFoundException("Subclasse não encontrada com id: " + dto.getSubclasseId()));
            personagem.setSubclasse(subclasse);
        } else {
            personagem.setSubclasse(null);
        }

        if (dto.getInventarioIds() != null) {
            List<Item> inventario = itemRepository.findAllById(dto.getInventarioIds());
            personagem.setInventario(inventario);
        } else {
            personagem.setInventario(Collections.emptyList());
        }

        if (dto.getTalentosIds() != null) {
            Set<Talento> talentos = new HashSet<>(talentoRepository.findAllById(dto.getTalentosIds()));
            personagem.setTalentos(talentos);
        } else {
            personagem.setTalentos(Collections.emptySet());
        }

        if (dto.getMagiasPreparadasIds() != null) {
            Set<Magia> magias = new HashSet<>(magiaRepository.findAllById(dto.getMagiasPreparadasIds()));
            personagem.setMagiasPreparadas(magias);
        } else {
            personagem.setMagiasPreparadas(Collections.emptySet());
        }
    }
}
