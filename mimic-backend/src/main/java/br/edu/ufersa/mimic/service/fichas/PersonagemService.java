package br.edu.ufersa.mimic.service.fichas;

import br.edu.ufersa.mimic.api.dto.fichas.PersonagemDTO;
import br.edu.ufersa.mimic.model.auth.Usuario; // Importante!
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

        // VINCULAR AO USUÁRIO (Segurança)
        Usuario dono = new Usuario();
        dono.setUsuarioId(usuarioId); // Hibernate só precisa do ID para fazer o link
        personagem.setUsuario(dono);

        mapearDtoParaEntidade(dto, personagem);
        return new PersonagemDTO(personagemRepository.save(personagem));
    }

    @Transactional(readOnly = true)
    public List<PersonagemDTO> listarPorUsuario(Long usuarioId) {
        // SEGURANÇA: Traz apenas os personagens deste usuário
        return personagemRepository.findByUsuario_UsuarioId(usuarioId)
                .stream()
                .map(PersonagemDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PersonagemDTO buscarPorId(Long id, Long usuarioId) {
        // SEGURANÇA: Busca pelo ID da ficha E pelo ID do usuário
        Personagem personagem = personagemRepository.findByIdAndUsuario_UsuarioId(id, usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Personagem não encontrado ou acesso negado."));

        return new PersonagemDTO(personagem);
    }

    @Transactional
    public PersonagemDTO atualizar(Long id, PersonagemDTO dto, Long usuarioId) {
        // SEGURANÇA: Garante que o personagem existe E pertence ao usuário antes de editar
        Personagem personagemExistente = personagemRepository.findByIdAndUsuario_UsuarioId(id, usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Personagem não encontrado ou acesso negado."));

        mapearDtoParaEntidade(dto, personagemExistente);
        return new PersonagemDTO(personagemRepository.save(personagemExistente));
    }

    @Transactional
    public void deletarPorId(Long id, Long usuarioId) {
        // SEGURANÇA: Garante que pertence ao usuário antes de deletar
        Personagem personagem = personagemRepository.findByIdAndUsuario_UsuarioId(id, usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Personagem não encontrado ou acesso negado."));

        personagemRepository.delete(personagem);
    }

    private void mapearDtoParaEntidade(PersonagemDTO dto, Personagem personagem) {
        // Dados simples
        personagem.setNomePersonagem(dto.getNomePersonagem());
        personagem.setNivel(dto.getNivel());
        personagem.setPontosDeExperiencia(dto.getPontosDeExperiencia());
        personagem.setAlinhamento(dto.getAlinhamento());

        // Atributos
        personagem.setForca(dto.getForca());
        personagem.setDestreza(dto.getDestreza());
        personagem.setConstituicao(dto.getConstituicao());
        personagem.setInteligencia(dto.getInteligencia());
        personagem.setSabedoria(dto.getSabedoria());
        personagem.setCarisma(dto.getCarisma());

        // Combate & Status
        personagem.setPontosDeVidaMaximos(dto.getPontosDeVidaMaximos());
        personagem.setPontosDeVidaAtuais(dto.getPontosDeVidaAtuais());
        personagem.setPontosDeVidaTemporarios(dto.getPontosDeVidaTemporarios());
        personagem.setClasseDeArmadura(dto.getClasseDeArmadura());
        personagem.setIniciativa(dto.getIniciativa());
        personagem.setDeslocamento(dto.getDeslocamento());
        personagem.setPercepcaoPassiva(dto.getPercepcaoPassiva());

        // Recursos & Notas
        personagem.setDadosDeVidaGastos(dto.getDadosDeVidaGastos());
        personagem.setInspiracaoHeroica(dto.isInspiracaoHeroica());
        personagem.setProficienciasPericias(dto.getProficienciasPericias());
        personagem.setProficienciasTestesDeResistencia(dto.getProficienciasTestesDeResistencia());

        // Dinheiro
        personagem.setPc(dto.getPc());
        personagem.setPp(dto.getPp());
        personagem.setPo(dto.getPo());
        personagem.setPl(dto.getPl());

        // --- RELACIONAMENTOS ---

        // Nota: Aqui mantive o findById simples para Classes/Raças pois assumimos que
        // Classes e Raças são sempre PÚBLICAS (do sistema).
        // Se você permitir Classes Homebrew, teria que validar o dono aqui também.

        Classe classe = classeRepository.findById(dto.getClasseId())
                .orElseThrow(() -> new EntityNotFoundException("Classe não encontrada: " + dto.getClasseId()));
        personagem.setClasse(classe);

        Raca especie = racaRepository.findById(dto.getEspecieId())
                .orElseThrow(() -> new EntityNotFoundException("Espécie não encontrada: " + dto.getEspecieId()));
        personagem.setRaca(especie); // Ajustado para setRaca (conforme entidade ajustada anteriormente)

        Origem origem = origemRepository.findById(dto.getAntecedenteId())
                .orElseThrow(() -> new EntityNotFoundException("Origem não encontrada: " + dto.getAntecedenteId()));
        personagem.setOrigem(origem);

        if (dto.getSubclasseId() != null) {
            Subclasse subclasse = subclasseRepository.findById(dto.getSubclasseId())
                    .orElseThrow(() -> new EntityNotFoundException("Subclasse não encontrada"));
            personagem.setSubclasse(subclasse);
        } else {
            personagem.setSubclasse(null);
        }

        // Listas (Inventário, Talentos, Magias)
        // Aqui assumimos que se o ID existe, pode adicionar.
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