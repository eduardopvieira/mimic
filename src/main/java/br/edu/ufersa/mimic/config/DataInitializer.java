package br.edu.ufersa.mimic.config;

import br.edu.ufersa.mimic.model.caracteristicas.*;
import br.edu.ufersa.mimic.model.enums.Atributo;
import br.edu.ufersa.mimic.model.enums.Tamanho;
import br.edu.ufersa.mimic.repository.caracteristicas.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

@Component
public class DataInitializer implements CommandLineRunner {

    // Repositórios atualizados
    @Autowired private RacaRepository racaRepository;
    @Autowired private ClasseRepository classeRepository;
    @Autowired private CaracteristicasDeClasseRepository caracteristicaDeClasseRepository;
    @Autowired private SubclasseRepository subclasseRepository;
    @Autowired private TracoRacialRepository tracoRacialRepository; // Nome corrigido

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (racaRepository.count() > 0 || classeRepository.count() > 0) {
            System.out.println("O banco de dados já parece estar populado. Nenhum dado foi inserido.");
            return;
        }

        System.out.println("Banco de dados vazio - populando com dados iniciais...");

        // --- 1. CRIAR TRAÇOS (Habilidades Raciais e de Classe) ---
        System.out.println("Criando Traços...");

        // Traços Gerais/Élficos
        TracoRacial visaoNoEscuro18m = criarTraco("Visão no Escuro (18m)", "Você pode ver na penumbra a até 18 metros como se fosse luz plena, e na escuridão como se fosse penumbra.");
        TracoRacial ancestralidadeFeerica = criarTraco("Ancestralidade Feérica", "Você tem Vantagem em salvaguardas para evitar ou encerrar a condição Enfeitiçado.");
        TracoRacial transe = criarTraco("Transe", "Você não precisa dormir. Em vez disso, medita profundamente por 4 horas para obter os mesmos benefícios de um descanso longo.");

        // Traços Anão
        TracoRacial visaoNoEscuro36m = criarTraco("Visão no Escuro (36m)", "Você pode ver na penumbra a até 36 metros como se fosse luz plena, e na escuridão como se fosse penumbra.");
        TracoRacial resistenciaToxinas = criarTraco("Resistência a Toxinas", "Você tem Resistência a dano Venenoso e Vantagem em salvaguardas contra a condição Envenenado.");
        TracoRacial tenacidadeAna = criarTraco("Tenacidade Anã", "Seus Pontos de Vida máximos aumentam em 1, e novamente em 1, sempre que você atinge um nível de personagem.");
        TracoRacial conhecimentoPedras = criarTraco("Conhecimento de Pedras", "Como uma Ação Bônus, você adquire Sismiconsciência com um alcance de 18 metros por 10 minutos.");

        // Traços Draconato
        TracoRacial herancaDraconica = criarTraco("Herança Dracônica", "Sua linhagem deriva de um progenitor dracônico. Escolha um tipo de dragão. Sua escolha afeta suas características de Ataque de Sopro e Resistência a Dano.");
        TracoRacial ataqueDeSopro = criarTraco("Ataque de Sopro", "Você pode usar sua ação para exalar energia destrutiva. O tipo de dano e a área são determinados pela sua herança dracônica.");
        TracoRacial resistenciaDanoDraconico = criarTraco("Resistência a Dano (Draconato)", "Você tem Resistência ao tipo de dano determinado por seu traço Herança Dracônica.");
        TracoRacial vooDraconico = criarTraco("Voo Dracônico", "No nível 5, você pode usar uma Ação Bônus para manifestar asas espectrais que lhe concedem deslocamento de voo por 10 minutos.");

        // Traços de Classe (Bárbaro)
        TracoRacial furia = criarTraco("Fúria", "Em seu turno, você pode entrar em Fúria como uma Ação Bônus, ganhando bônus em dano e resistência a certos tipos de dano.");
        TracoRacial defesaSemArmaduraBarbaro = criarTraco("Defesa sem Armadura (Bárbaro)", "Enquanto não estiver vestindo armadura, sua CA é 10 + seu modificador de Destreza + seu modificador de Constituição.");
        TracoRacial ataqueImprudente = criarTraco("Ataque Imprudente", "Você pode decidir atacar de forma imprudente, concedendo Vantagem em seus ataques com Força, mas os ataques contra você também têm Vantagem.");
        TracoRacial sentidoDePerigo = criarTraco("Sentido de Perigo", "Você tem Vantagem em salvaguardas de Destreza contra efeitos que pode ver.");

        // Traços de Subclasse (Berserker)
        TracoRacial frenesi = criarTraco("Frenesi", "A partir do 3º nível, se você usar Ataque Imprudente enquanto sua Fúria estiver ativa, você causa dano adicional ao primeiro alvo atingido.");

        // Traços de Classe (Mago)
        TracoRacial conjuracaoMago = criarTraco("Conjuração (Mago)", "Você pode conjurar magias de Mago usando Inteligência como seu atributo de conjuração.");
        TracoRacial recuperacaoArcana = criarTraco("Recuperação Arcana", "Uma vez por dia, ao terminar um Descanso Curto, você pode recuperar espaços de magia gastos.");
        TracoRacial adeptoDeRitual = criarTraco("Adepto de Ritual", "Você pode conjurar uma magia de Mago como um ritual se essa magia tiver o marcador Ritual e estiver em seu livro de magias.");

        // Traços de Subclasse (Evocador)
        TracoRacial truquePotente = criarTraco("Truque Potente", "Seus truques que causam dano afetam até criaturas que evitam os efeitos deles, causando metade do dano.");


        // --- 2. CRIAR RAÇAS ---
        System.out.println("Criando Raças...");

        // Elfo (sem sub-raças)
        Raca elfo = new Raca();
        elfo.setNome("Elfo");
        elfo.setDeslocamento(9);
        elfo.setTamanho(Tamanho.MEDIO);
        elfo.setTracosRaciais(Arrays.asList(visaoNoEscuro18m, ancestralidadeFeerica, transe));
        racaRepository.save(elfo);

        // Anão
        Raca anao = new Raca();
        anao.setNome("Anão");
        anao.setDeslocamento(9);
        anao.setTamanho(Tamanho.MEDIO);
        anao.setTracosRaciais(Arrays.asList(visaoNoEscuro36m, resistenciaToxinas, tenacidadeAna, conhecimentoPedras));
        racaRepository.save(anao);

        // Draconato
        Raca draconato = new Raca();
        draconato.setNome("Draconato");
        draconato.setDeslocamento(9);
        draconato.setTamanho(Tamanho.MEDIO);
        draconato.setTracosRaciais(Arrays.asList(herancaDraconica, ataqueDeSopro, resistenciaDanoDraconico, vooDraconico, visaoNoEscuro18m));
        racaRepository.save(draconato);


        System.out.println("Criando Classes e Subclasses...");

        // Bárbaro
        Classe barbaro = new Classe();
        barbaro.setNome("Bárbaro");
        barbaro.setDescricao("Um combatente feroz da fúria primitiva.");
        barbaro.setDadoDeVida(12);
        barbaro.setProficienciasArmas(new HashSet<>(Arrays.asList("Armas Simples", "Armas Marciais")));
        barbaro.setProficienciasArmaduras(new HashSet<>(Arrays.asList("Armaduras Leves", "Armaduras Médias", "Escudos")));
        barbaro.setProficienciasTestesDeResistencia(new HashSet<>(Arrays.asList("FORÇA", "CONSTITUIÇÃO")));
        barbaro.setOpcoesDePericias(new HashSet<>(Arrays.asList("Atletismo", "Intimidação", "Lidar com Animais", "Natureza", "Percepção", "Sobrevivência")));
        barbaro.setQuantidadeEscolhaPericias(2);
        barbaro.setConjurador(false);
        Classe barbaroSalvo = classeRepository.save(barbaro);

        Subclasse berserker = new Subclasse();
        berserker.setNome("Trilha do Berserker");
        berserker.setDescricao("Canalize sua Fúria em um Frenesi Violento.");
        berserker.setClassePai(barbaroSalvo);
        Subclasse berserkerSalvo = subclasseRepository.save(berserker);

        // Mago
        Classe mago = new Classe();
        mago.setNome("Mago");
        mago.setDescricao("Um estudioso usuário de magia arcana.");
        mago.setDadoDeVida(6);
        mago.setProficienciasArmas(new HashSet<>(Collections.singletonList("Armas Simples")));
        mago.setProficienciasArmaduras(new HashSet<>());
        mago.setProficienciasTestesDeResistencia(new HashSet<>(Arrays.asList("INTELIGÊNCIA", "SABEDORIA")));
        mago.setOpcoesDePericias(new HashSet<>(Arrays.asList("Arcanismo", "História", "Intuição", "Investigação", "Medicina", "Religião")));
        mago.setQuantidadeEscolhaPericias(2);
        mago.setConjurador(true);
        mago.setAtributoDeConjuracao(Atributo.INTELIGENCIA);
        Classe magoSalvo = classeRepository.save(mago);

        Subclasse evocador = new Subclasse();
        evocador.setNome("Escola da Evocação");
        evocador.setDescricao("Crie efeitos elementais explosivos.");
        evocador.setClassePai(magoSalvo);
        Subclasse evocadorSalvo = subclasseRepository.save(evocador);


        // --- 4. LIGAR TRAÇOS ÀS CLASSES/SUBCLASSES ---
        System.out.println("Associando Características...");

        // Características do Bárbaro
        criarCaracteristicaDeClasse(barbaroSalvo, furia, 1);
        criarCaracteristicaDeClasse(barbaroSalvo, defesaSemArmaduraBarbaro, 1);
        criarCaracteristicaDeClasse(barbaroSalvo, ataqueImprudente, 2);
        criarCaracteristicaDeClasse(barbaroSalvo, sentidoDePerigo, 2);

        // Característica da Subclasse Berserker
        criarCaracteristicaDeSubclasse(berserkerSalvo, frenesi, 3);

        // Características do Mago
        criarCaracteristicaDeClasse(magoSalvo, conjuracaoMago, 1);
        criarCaracteristicaDeClasse(magoSalvo, recuperacaoArcana, 1);
        criarCaracteristicaDeClasse(magoSalvo, adeptoDeRitual, 1);

        // Característica da Subclasse Evocador
        criarCaracteristicaDeSubclasse(evocadorSalvo, truquePotente, 3);


        System.out.println("Dados iniciais persistidos com sucesso!");
    }

    // --- MÉTODOS AUXILIARES PARA LIMPAR O CÓDIGO ---

    private TracoRacial criarTraco(String nome, String descricao) {
        TracoRacial traco = new TracoRacial();
        traco.setNome(nome);
        traco.setDescricao(descricao);
        return tracoRacialRepository.save(traco); // Nome corrigido
    }

    private void criarCaracteristicaDeClasse(Classe classe, TracoRacial traco, int nivel) {
        CaracteristicaDeClasse caracteristica = new CaracteristicaDeClasse();
        caracteristica.setClasse(classe);
        caracteristica.setTraco(traco);
        caracteristica.setNivelAdquirido(nivel);
        caracteristicaDeClasseRepository.save(caracteristica);
    }

    private void criarCaracteristicaDeSubclasse(Subclasse subclasse, TracoRacial traco, int nivel) {
        CaracteristicaDeClasse caracteristica = new CaracteristicaDeClasse();
        caracteristica.setSubclasse(subclasse);
        caracteristica.setTraco(traco);
        caracteristica.setNivelAdquirido(nivel);
        caracteristicaDeClasseRepository.save(caracteristica);
    }
}
