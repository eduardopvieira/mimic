package br.edu.ufersa.mimic.repository.equipamento;

import br.edu.ufersa.mimic.model.equipamento.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findByNome(String nome);

}
