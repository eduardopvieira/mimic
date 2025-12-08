package br.edu.ufersa.mimic.service.fichas;

import br.edu.ufersa.mimic.api.dto.fichas.PersonagemDTO;
import br.edu.ufersa.mimic.model.auth.Usuario;
import br.edu.ufersa.mimic.model.caracteristicas.*;
import br.edu.ufersa.mimic.model.enums.Atributo;
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

        // Mapeia DTO -> Entidade (Calcula Vida e CA aqui dentro)
        mapearDtoParaEntidade(dto, personagem);

        // Regra de Negócio: Na criação, a vida atual começa cheia
        personagem.setVidaAtual(personagem.getVidaMax());

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

        personagem.setImagem(file.getBytes());
        personagemRepository.save(personagem);
    }

    @Transactional
    public PersonagemDTO atualizar(Long id, PersonagemDTO dto, Long usuarioId) {
        Personagem personagemExistente = personagemRepository.findByIdAndUsuario_UsuarioId(id, usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Personagem não encontrado ou acesso negado."));

        mapearDtoParaEntidade(dto, personagemExistente);

        // Validação: Se a vida máxima diminuiu (perdeu CON/Nível), ajusta a atual
        if (dto.getPontosDeVidaAtuais() > personagemExistente.getVidaMax()) {
            personagemExistente.setVidaAtual(personagemExistente.getVidaMax());
        } else {
            personagemExistente.setVidaAtual(dto.getPontosDeVidaAtuais());
        }

        return new PersonagemDTO(personagemRepository.save(personagemExistente));
    }

    @Transactional
    public void deletarPorId(Long id, Long usuarioId) {
        Personagem personagem = personagemRepository.findByIdAndUsuario_UsuarioId(id, usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Personagem não encontrado ou acesso negado."));

        personagemRepository.delete(personagem);
    }

    /**
     * Mapeia os dados do DTO para a Entidade e aplica regras de negócio (HP e CA).
     */
    private void mapearDtoParaEntidade(PersonagemDTO dto, Personagem personagem) {
        // 1. DADOS BÁSICOS
        personagem.setNome(dto.getNomePersonagem());
        personagem.setNivel(dto.getNivel());
        personagem.setPontosDeExperiencia(dto.getPontosDeExperiencia());
        personagem.setAlinhamento(dto.getAlinhamento());

        // --- CORREÇÃO: Setando o tamanho que vem do DTO ---
        personagem.setTamanho(dto.getTamanho());
        // --------------------------------------------------

        // 2. ATRIBUTOS
        personagem.setForca(dto.getForca());
        personagem.setDestreza(dto.getDestreza());
        personagem.setConstituicao(dto.getConstituicao());
        personagem.setInteligencia(dto.getInteligencia());
        personagem.setSabedoria(dto.getSabedoria());
        personagem.setCarisma(dto.getCarisma());

        // 3. RELACIONAMENTOS (Busca no Banco)
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

        // 4. REGRA DE NEGÓCIO: CÁLCULO DE VIDA MÁXIMA
        int vidaCalculada = calcularVidaMaxima(
                personagem.getNivel(),
                personagem.getConstituicao(),
                classe.getDadoDeVida()
        );
        personagem.setVidaMax(vidaCalculada);
        personagem.setVidaTemp(dto.getPontosDeVidaTemporarios());

        // 5. REGRA DE NEGÓCIO: CÁLCULO DE CA (BASE)
        String nomeSubclasse = (personagem.getSubclasse() != null) ? personagem.getSubclasse().getNome() : "";
        int caCalculada = calcularClasseDeArmadura(
                personagem.getNivel(),
                personagem.getDestreza(),
                personagem.getConstituicao(),
                personagem.getSabedoria(),
                personagem.getCarisma(),
                classe.getNome(),
                nomeSubclasse
        );

        // Respeita valor manual se for maior (ex: armadura equipada), senão usa o calculado
        if (dto.getClasseDeArmadura() != null && dto.getClasseDeArmadura() > caCalculada) {
            personagem.setClasseDeArmadura(dto.getClasseDeArmadura());
        } else {
            personagem.setClasseDeArmadura(caCalculada);
        }

        // 6. OUTROS STATUS
        personagem.setIniciativa(dto.getIniciativa());
        personagem.setDeslocamento(dto.getDeslocamento());
        personagem.setPercepcaoPassiva(dto.getPercepcaoPassiva());
        personagem.setDadosDeVidaGastos(dto.getDadosDeVidaGastos());
        personagem.setInspiracaoHeroica(dto.isInspiracaoHeroica());
        personagem.setAtributoChaveConjuracao(dto.getAtributoChaveConjuracao());

        // 7. INVENTÁRIO E ECONOMIA
        personagem.setEscolhaEquipamentoClasse(dto.getEscolhaEquipamentoClasse());
        personagem.setEscolhaEquipamentoOrigem(dto.getEscolhaEquipamentoOrigem());
        personagem.setPc(dto.getPc());
        personagem.setPp(dto.getPp());
        personagem.setPo(dto.getPo());
        personagem.setPl(dto.getPl());

        // 8. LISTAS E RELACIONAMENTOS N:N

        personagem.setPericias(dto.getPericias());

        if (classe.getTestesDeResistencia() != null) {
            Set<String> salvaguardas = classe.getTestesDeResistencia().stream()
                    .map(Atributo::getNomeAtributo)
                    .collect(Collectors.toSet());
            personagem.setSalvaguardas(salvaguardas);
        } else {
            personagem.setSalvaguardas(Collections.emptySet());
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

    /**
     * Calcula Vida Máxima (Regra D&D 2024).
     */
    private int calcularVidaMaxima(int nivel, int valorConstituicao, Integer facesDado) {
        int dado = (facesDado != null) ? facesDado : 8;
        int modCon = (int) Math.floor((valorConstituicao - 10) / 2.0);

        int vidaNivel1 = dado + modCon;
        if (vidaNivel1 < 1) vidaNivel1 = 1;

        if (nivel == 1) {
            return vidaNivel1;
        }

        int mediaFixa = (dado / 2) + 1;
        int ganhoPorNivel = Math.max(1, mediaFixa + modCon);

        return vidaNivel1 + (ganhoPorNivel * (nivel - 1));
    }

    private int calcularClasseDeArmadura(int nivel, int des, int con, int sab, int car, String nomeClasse, String nomeSubclasse) {
        int modDes = (int) Math.floor((des - 10) / 2.0);
        int modCon = (int) Math.floor((con - 10) / 2.0);
        int modSab = (int) Math.floor((sab - 10) / 2.0);
        int modCar = (int) Math.floor((car - 10) / 2.0);

        int caFinal = 10 + modDes;

        String classe = nomeClasse.toLowerCase().trim();
        String subclasse = nomeSubclasse != null ? nomeSubclasse.toLowerCase().trim() : "";

        if (classe.equals("bárbaro")) {
            int caBarbaro = 10 + modDes + modCon;
            if (caBarbaro > caFinal) caFinal = caBarbaro;
        }
        else if (classe.equals("monge")) {
            int caMonge = 10 + modDes + modSab;
            if (caMonge > caFinal) caFinal = caMonge;
        }
        else if (classe.equals("feiticeiro") && subclasse.contains("dracônica") && nivel >= 3) {
            int caDraconica = 10 + modDes + modCar;
            if (caDraconica > caFinal) caFinal = caDraconica;
        }
        else if (classe.equals("bardo") && subclasse.contains("dança") && nivel >= 3) {
            int caDanca = 10 + modDes + modCar;
            if (caDanca > caFinal) caFinal = caDanca;
        }

        return caFinal;
    }
}