package br.edu.ufersa.mimic.api.controller.habilidades;

import br.edu.ufersa.mimic.api.dto.MagiaDTO;
import br.edu.ufersa.mimic.model.auth.Usuario;
import br.edu.ufersa.mimic.model.habilidades.Magia;
import br.edu.ufersa.mimic.service.habilidades.MagiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/magias")
public class MagiaController {

    private final MagiaService magiaService;

    @Autowired
    public MagiaController(MagiaService magiaService) {
        this.magiaService = magiaService;
    }

    // LISTAR TUDO (GET /api/magias)
    @GetMapping
    public ResponseEntity<List<MagiaDTO>> listarTodas() {
        List<Magia> magias = magiaService.listarTodas();
        // Converte a lista de Entidades para lista de DTOs
        List<MagiaDTO> dtos = magias.stream()
                                    .map(MagiaDTO::new)
                                    .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // BUSCAR POR ID (GET /api/magias/{id})
    @GetMapping("/{id}")
    public ResponseEntity<MagiaDTO> buscarPorId(@PathVariable Long id) {
        Magia magia = magiaService.buscarPorId(id);
        // Se o service lançar exceção quando não acha, o Spring trata. 
        // Caso retorne null, precisaria verificar. Assumindo que retorna objeto:
        return ResponseEntity.ok(new MagiaDTO(magia));
    }

    // BUSCAR POR NOME (GET /api/magias/busca?nome=Bola de Fogo)
    @GetMapping("/busca")
    public ResponseEntity<List<MagiaDTO>> buscarPorNome(@RequestParam String nome) {
        // ATENÇÃO: Você precisa garantir que o Service tenha esse método 'buscarPorNome'
        List<Magia> magias = magiaService.buscarPorNome(nome);
        
        List<MagiaDTO> dtos = magias.stream()
                                    .map(MagiaDTO::new)
                                    .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // CRIAR (POST /api/magias)
    @PostMapping
    public ResponseEntity<MagiaDTO> criar(@RequestBody MagiaDTO dto) {
        Magia magiaParaSalvar = converterParaEntity(dto);
        Magia magiaSalva = magiaService.salvar(magiaParaSalvar);
        return new ResponseEntity<>(new MagiaDTO(magiaSalva), HttpStatus.CREATED);
    }

    // ATUALIZAR (PUT /api/magias/{id})
    @PutMapping("/{id}")
    public ResponseEntity<MagiaDTO> atualizar(@PathVariable Long id, @RequestBody MagiaDTO dto) {
        // Aqui assumo que o Service cuida de manter o ID correto ou fazemos merge
        Magia magiaParaAtualizar = converterParaEntity(dto);
        
        // O service deve buscar pelo ID da URL e atualizar os dados
        Magia magiaAtualizada = magiaService.atualizar(id, magiaParaAtualizar);
        
        return ResponseEntity.ok(new MagiaDTO(magiaAtualizada));
    }

    // DELETAR (DELETE /api/magias/{id})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        magiaService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }

    // --- MÉTODOS AUXILIARES ---
    
    // Converte DTO -> Entity (Inverso do construtor do DTO)
    private Magia converterParaEntity(MagiaDTO dto) {
        Magia magia = new Magia();
        magia.setId(dto.getId());
        magia.setNome(dto.getNome());
        magia.setCirculo(dto.getCirculo());
        magia.setEscola(dto.getEscola());
        magia.setTempoConjuracao(dto.getTempoConjuracao());
        magia.setAlcance(dto.getAlcance());
        magia.setComponentes(dto.getComponentes());
        magia.setDuracao(dto.getDuracao());
        magia.setConcentracao(dto.isConcentracao());
        magia.setRitual(dto.isRitual());
        magia.setFormulaDano(dto.getFormulaDano());
        magia.setTipoDano(dto.getTipoDano());
        magia.setDescricao(dto.getDescricao());

        // Lógica simples para vincular usuário pelo ID
        if (dto.getUsuarioId() != null) {
            Usuario u = new Usuario();
            u.setId(dto.getUsuarioId());
            magia.setUsuario(u);
        }
        
        return magia;
    }
}