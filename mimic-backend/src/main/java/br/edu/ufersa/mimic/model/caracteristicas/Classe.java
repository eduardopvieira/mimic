package br.edu.ufersa.mimic.model.caracteristicas;

import br.edu.ufersa.mimic.model.enums.Atributo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "classes")
@Getter @Setter @NoArgsConstructor
public class Classe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String nome; // Vai para o campo "Classe" no cabeçalho

    @Column(name = "dado_de_vida", nullable = false)
    private Integer dadoDeVida; // Vai para o campo "Dado de Vida" (Ex: 8, 10, 12)

    // SIMPLIFICAÇÃO: Ao invés de tabelas extras, usamos coleções simples.
    // O front recebe isso e preenche a caixa "Treino de Armadura" e "Armas"
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> proficienciasTexto;
    // Ex: "Armaduras Leves, Médias, Escudos, Armas Simples, Marciais".
    // Juntei Armas e Armaduras pois na ficha elas ficam na mesma área geral.

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Atributo> testesDeResistencia; // Para marcar as bolinhas de "Salvaguarda"

    // Mantemos as opções para o front saber quais checkboxes habilitar para o usuário
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> periciasDeClasse;

    // RELACIONAMENTOS
    // Importante: A Entidade 'CaracteristicaDeClasse' deve ter apenas (Nivel, Nome, Descricao)
    // Essas descrições serão concatenadas no campo "Características de Classe" da ficha.
    @OneToMany(mappedBy = "classe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CaracteristicaDeClasse> caracteristicas;

    @OneToMany(mappedBy = "classePai", cascade = CascadeType.ALL)
    private List<Subclasse> subclasses; // Usuário escolhe uma -> Campo "Subclasse"

    // DADOS MÁGICOS (Essenciais para a página de Magia)
    @Column(name = "e_conjurador")
    private boolean isConjurador;

    @Enumerated(EnumType.STRING)
    @Column(name = "atributo_conjuracao")
    private Atributo atributoDeConjuracao; // Preenche "Atributo de Conjuração" na pág 2
}