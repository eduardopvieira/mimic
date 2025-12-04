package br.edu.ufersa.mimic.service.fichas;

import br.edu.ufersa.mimic.api.dto.fichas.CriaturaDTO;
import br.edu.ufersa.mimic.model.auth.Usuario;
import br.edu.ufersa.mimic.model.fichas.Criatura;
import br.edu.ufersa.mimic.repository.fichas.CriaturaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CriaturaService {

    @Autowired
    private CriaturaRepository criaturaRepository;


    @Transactional(readOnly = true)
    public List<CriaturaDTO> listarTodas(Long usuarioId) {
        return criaturaRepository.findAllPublicAndUser(usuarioId).stream()
                .map(CriaturaDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CriaturaDTO buscarPorId(Long id, Long usuarioId) {
        Criatura criatura = criaturaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Criatura não encontrada com id: " + id));

        if (criatura.getUsuario() != null && !criatura.getUsuario().getUsuarioId().equals(usuarioId)) {
            throw new IllegalArgumentException("Acesso negado a esta criatura customizada.");
        }

        return new CriaturaDTO(criatura);
    }


    @Transactional
    public CriaturaDTO salvar(CriaturaDTO dto, Long usuarioId) {
        Criatura criatura = new Criatura(dto);

        Usuario dono = new Usuario();
        dono.setUsuarioId(usuarioId);
        criatura.setUsuario(dono);

        return new CriaturaDTO(criaturaRepository.save(criatura));
    }

    @Transactional
    public CriaturaDTO atualizar(Long id, CriaturaDTO dto, Long usuarioId) {
        Criatura criaturaExistente = criaturaRepository.findByIdAndUsuario_UsuarioId(id, usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Criatura não encontrada ou você não tem permissão para editá-la (Monstros do sistema são protegidos)."));

        CriaturaDTO criaturaDTO = new CriaturaDTO(criaturaExistente);
        return new CriaturaDTO(criaturaRepository.save(criaturaExistente));
    }

    @Transactional
    public void deletarPorId(Long id, Long usuarioId) {
        Criatura criatura = criaturaRepository.findByIdAndUsuario_UsuarioId(id, usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Criatura não encontrada ou você não tem permissão para excluí-la."));

        criaturaRepository.delete(criatura);
    }

}