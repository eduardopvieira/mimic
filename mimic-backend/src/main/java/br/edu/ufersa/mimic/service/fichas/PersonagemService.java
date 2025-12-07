package br.edu.ufersa.mimic.service.fichas;

import br.edu.ufersa.mimic.api.dto.fichas.PersonagemDTO;
import br.edu.ufersa.mimic.model.auth.Usuario;
import br.edu.ufersa.mimic.model.caracteristicas.*;
import br.edu.ufersa.mimic.model.equipamento.Item;
import br.edu.ufersa.mimic.model.fichas.Personagem;
import br.edu.ufersa.mimic.model.habilidades.Magia;
import br.edu.ufersa.mimic.model.habilidades.Talento;
import br.edu.ufersa.mimic.repository.caracteristicas.*;
import br.edu.ufersa.mimic.repository.equipamento.ItemRepository;
import br.edu.ufersa.mimic.repository.fichas.PersonagemRepository;
import br.edu.ufersa.mimic.repository.habilidades.MagiaRepository;
import br.edu.ufersa.mimic.repository.habilidades.TalentoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public PersonagemDTO salvar(PersonagemDTO dto, Long usuarioId) {
        Personagem personagem = new Personagem();

        Usuario dono = new Usuario();
        dono.setUsuarioId(usuarioId);
        personagem.setUsuario(dono);

        mapearDtoParaEntidade(dto, personagem);
        return new PersonagemDTO(personagemRepository.save(personagem));
    }

    @Transactional(readOnly = true)
    public List<PersonagemDTO> listarPorUsuario(Long usuarioId) {
        return personagemRepository.findByUsuario_UsuarioId(usuarioId)
                .stream()
                .map(PersonagemDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PersonagemDTO buscarPorId(Long id, Long usuarioId) {
        Personagem personagem = personagemRepository.findByIdAndUsuario_UsuarioId(id, usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Personagem não encontrado ou acesso negado."));

        return new PersonagemDTO(personagem);
    }

    @Transactional
    public void salvarImagem(Long personagemId, Long usuarioId, MultipartFile file) throws IOException {
        Personagem personagem = personagemRepository.findByIdAndUsuario_UsuarioId(personagemId, usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Personagem não encontrado."));

        personagem.setImagem(file.getBytes()); // Converte o arquivo para bytes e salva no banco
        personagemRepository.save(personagem);
    }

    @Transactional
    public PersonagemDTO atualizar(Long id, PersonagemDTO dto, Long usuarioId) {
        Personagem personagemExistente = personagemRepository.findByIdAndUsuario_UsuarioId(id, usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Personagem não encontrado ou acesso negado."));

        mapearDtoParaEntidade(dto, personagemExistente);
        return new PersonagemDTO(personagemRepository.save(personagemExistente));
    }

    @Transactional
    public void deletarPorId(Long id, Long usuarioId) {
        Personagem personagem = personagemRepository.findByIdAndUsuario_UsuarioId(id, usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Personagem não encontrado ou acesso negado."));

        personagemRepository.delete(personagem);
    }

    /**
     * Atualiza os dados da entidade com base no DTO.
     * Atualizado para refletir os novos nomes de campos e a estrutura limpa da Entidade.
     */
    private void mapearDtoParaEntidade(PersonagemDTO dto, Personagem personagem) {
        personagem.setNome(dto.getNomePersonagem());
        personagem.setNivel(dto.getNivel());
        personagem.setPontosDeExperiencia(dto.getPontosDeExperiencia());
        personagem.setAlinhamento(dto.getAlinhamento());
        personagem.setAparencia(dto.getAparencia());
        personagem.setHistoria(dto.getHistoria());

        personagem.setForca(dto.getForca());
        personagem.setDestreza(dto.getDestreza());
        personagem.setConstituicao(dto.getConstituicao());
        personagem.setInteligencia(dto.getInteligencia());
        personagem.setSabedoria(dto.getSabedoria());
        personagem.setCarisma(dto.getCarisma());

        personagem.setVidaMax(dto.getPontosDeVidaMaximos());
        personagem.setVidaAtual(dto.getPontosDeVidaAtuais());
        personagem.setVidaTemp(dto.getPontosDeVidaTemporarios());

        personagem.setClasseDeArmadura(dto.getClasseDeArmadura());
        personagem.setIniciativa(dto.getIniciativa());
        personagem.setDeslocamento(dto.getDeslocamento());
        personagem.setPercepcaoPassiva(dto.getPercepcaoPassiva());

        personagem.setEscolhaEquipamentoClasse(dto.getEscolhaEquipamentoClasse());
        personagem.setEscolhaEquipamentoOrigem(dto.getEscolhaEquipamentoOrigem());

        personagem.setDadosDeVidaGastos(dto.getDadosDeVidaGastos());
        personagem.setInspiracaoHeroica(dto.isInspiracaoHeroica());

        personagem.setPericias(dto.getPericias());
        personagem.setSalvaguardas(dto.getSalvaguardas());

        personagem.setAtributoChaveConjuracao(dto.getAtributoChaveConjuracao());

        personagem.setPc(dto.getPc());
        personagem.setPp(dto.getPp());
        personagem.setPo(dto.getPo());
        personagem.setPl(dto.getPl());


        Classe classe = classeRepository.findById(dto.getClasseId())
                .orElseThrow(() -> new EntityNotFoundException("Classe não encontrada: " + dto.getClasseId()));
        personagem.setClasse(classe);

        Raca raca = racaRepository.findById(dto.getRacaId())
                .orElseThrow(() -> new EntityNotFoundException("Raça/Espécie não encontrada: " + dto.getRacaId()));
        personagem.setRaca(raca);

        Origem origem = origemRepository.findById(dto.getOrigemId())
                .orElseThrow(() -> new EntityNotFoundException("Origem não encontrada: " + dto.getOrigemId()));
        personagem.setOrigem(origem);

        if (dto.getSubclasseId() != null) {
            Subclasse subclasse = subclasseRepository.findById(dto.getSubclasseId())
                    .orElseThrow(() -> new EntityNotFoundException("Subclasse não encontrada"));
            personagem.setSubclasse(subclasse);
        } else {
            personagem.setSubclasse(null);
        }


        if (dto.getInventarioIds() != null && !dto.getInventarioIds().isEmpty()) {
            List<Item> inventario = itemRepository.findAllById(dto.getInventarioIds());
            personagem.setInventario(inventario);
        } else {
            personagem.setInventario(Collections.emptyList());
        }

        if (dto.getTalentosIds() != null && !dto.getTalentosIds().isEmpty()) {
            Set<Talento> talentos = new HashSet<>(talentoRepository.findAllById(dto.getTalentosIds()));
            personagem.setTalentos(talentos);
        } else {
            personagem.setTalentos(Collections.emptySet());
        }

        if (dto.getMagiasPreparadasIds() != null && !dto.getMagiasPreparadasIds().isEmpty()) {
            Set<Magia> magias = new HashSet<>(magiaRepository.findAllById(dto.getMagiasPreparadasIds()));
            personagem.setMagiasPreparadas(magias);
        } else {
            personagem.setMagiasPreparadas(Collections.emptySet());
        }
    }
}