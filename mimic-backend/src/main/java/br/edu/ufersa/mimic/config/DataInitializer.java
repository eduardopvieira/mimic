package br.edu.ufersa.mimic.config;

import br.edu.ufersa.mimic.model.auth.Usuario;
import br.edu.ufersa.mimic.model.caracteristicas.*;
import br.edu.ufersa.mimic.model.enums.*;
import br.edu.ufersa.mimic.model.enums.equipamento.armadura.CategoriaArmadura;
import br.edu.ufersa.mimic.model.enums.equipamento.arma.CategoriaArma;
import br.edu.ufersa.mimic.model.enums.equipamento.arma.TipoArma;
import br.edu.ufersa.mimic.model.enums.equipamento.arma.TipoDeDano;
import br.edu.ufersa.mimic.model.equipamento.Arma;
import br.edu.ufersa.mimic.model.equipamento.Armadura;
import br.edu.ufersa.mimic.model.equipamento.Item;
import br.edu.ufersa.mimic.model.habilidades.Magia;
import br.edu.ufersa.mimic.model.habilidades.Talento;
import br.edu.ufersa.mimic.repository.auth.UsuarioRepository;
import br.edu.ufersa.mimic.repository.caracteristicas.*;
import br.edu.ufersa.mimic.repository.equipamento.ArmaRepository;
import br.edu.ufersa.mimic.repository.equipamento.ArmaduraRepository;
import br.edu.ufersa.mimic.repository.equipamento.ItemRepository;
import br.edu.ufersa.mimic.repository.habilidades.MagiaRepository;
import br.edu.ufersa.mimic.repository.habilidades.TalentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired private ArmaRepository armaRepository;
    @Autowired private ArmaduraRepository armaduraRepository;
    @Autowired private ItemRepository itemRepository;
    @Autowired private MagiaRepository magiaRepository;
    @Autowired private TalentoRepository talentoRepository;
    @Autowired private OrigemRepository origemRepository;
    @Autowired private RacaRepository racaRepository;
    @Autowired private ClasseRepository classeRepository;
    @Autowired private SubclasseRepository subclasseRepository;
    @Autowired private CaracteristicasDeClasseRepository caracteristicaDeClasseRepository;
    @Autowired private TracoRacialRepository tracoRacialRepository;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (racaRepository.count() > 0 || classeRepository.count() > 0) {
            System.out.println("O banco de dados já parece estar populado. Nenhum dado foi inserido.");
            return;
        }

        System.out.println("Banco de dados vazio - populando com dados iniciais...");

        criarUsuarioPadrao();
        criarArmas();
        criarArmaduras();
        criarItens();
        criarMagias();
        criarTalentosOrigensERacas();
        criarClassesSubclassesECaracteristicas();

        System.out.println("Dados iniciais persistidos com sucesso!");
    }

    private void criarArmas() {
        System.out.println("Cadastrando Armas...");
        Arma adaga = new Arma();
        adaga.setNome("Adaga");
        adaga.setCategoriaArma(CategoriaArma.SIMPLES);
        adaga.setTipo(TipoArma.CORPO_A_CORPO);
        adaga.setDano("1d4");
        adaga.setTipoDeDano(TipoDeDano.PERFURANTE);
        adaga.setPeso(0.5);
        adaga.setCusto("2 PO");
        adaga.setPropriedades(new HashSet<>(Arrays.asList("Acuidade", "Leve", "Arremesso (6/18m)")));
        adaga.setMaestria("Ágil");
        armaRepository.save(adaga);

        Arma espadaLonga = new Arma();
        espadaLonga.setNome("Espada Longa");
        espadaLonga.setCategoriaArma(CategoriaArma.MARCIAL);
        espadaLonga.setTipo(TipoArma.CORPO_A_CORPO);
        espadaLonga.setDano("1d8");
        espadaLonga.setTipoDeDano(TipoDeDano.CORTANTE);
        espadaLonga.setPeso(1.5);
        espadaLonga.setCusto("15 PO");
        espadaLonga.setPropriedades(new HashSet<>(Collections.singletonList("Versátil (1d10)")));
        espadaLonga.setMaestria("Drenar");
        armaRepository.save(espadaLonga);

        Arma arcoLongo = new Arma();
        arcoLongo.setNome("Arco Longo");
        arcoLongo.setCategoriaArma(CategoriaArma.MARCIAL);
        arcoLongo.setTipo(TipoArma.A_DISTANCIA);
        arcoLongo.setDano("1d8");
        arcoLongo.setTipoDeDano(TipoDeDano.PERFURANTE);
        arcoLongo.setPeso(1.0);
        arcoLongo.setCusto("50 PO");
        arcoLongo.setPropriedades(new HashSet<>(Arrays.asList("Pesada", "Duas Mãos", "Munição (45/180m)")));
        arcoLongo.setMaestria("Lentidão");
        armaRepository.save(arcoLongo);
    }

    private void criarArmaduras() {
        System.out.println("Cadastrando Armaduras...");
        Armadura couro = new Armadura();
        couro.setNome("Armadura de Couro");
        couro.setCategoria(CategoriaArmadura.LEVE);
        couro.setValorCA("11 + mod. Destreza");
        couro.setRequisitoForca(null);
        couro.setDesvantagemFurtividade(false);
        couro.setPeso(5.0);
        couro.setCusto("10 PO");
        armaduraRepository.save(couro);

        Armadura cotaMalhaParcial = new Armadura();
        cotaMalhaParcial.setNome("Cota de Malha Parcial");
        cotaMalhaParcial.setCategoria(CategoriaArmadura.MEDIA);
        cotaMalhaParcial.setValorCA("13 + mod. Destreza (máx 2)");
        cotaMalhaParcial.setRequisitoForca(null);
        cotaMalhaParcial.setDesvantagemFurtividade(false);
        cotaMalhaParcial.setPeso(10.0);
        cotaMalhaParcial.setCusto("50 PO");
        armaduraRepository.save(cotaMalhaParcial);

        Armadura placas = new Armadura();
        placas.setNome("Armadura de Placas");
        placas.setCategoria(CategoriaArmadura.PESADA);
        placas.setValorCA("18");
        placas.setRequisitoForca(15);
        placas.setDesvantagemFurtividade(true);
        placas.setPeso(32.0);
        placas.setCusto("1500 PO");
        armaduraRepository.save(placas);
    }

    private void criarItens() {
        System.out.println("Cadastrando Itens...");
        Item mochila = new Item();
        mochila.setNome("Mochila");
        mochila.setDescricao("Uma mochila de couro com alças, pode carregar até 15kg de equipamento.");
        mochila.setPeso(2.5);
        mochila.setCusto("2 PO");
        itemRepository.save(mochila);

        Item tocha = new Item();
        tocha.setNome("Tocha");
        tocha.setDescricao("Uma tocha que queima por 1 hora, provendo luz plena em um raio de 6m e penumbra por mais 6m.");
        tocha.setPeso(0.5);
        tocha.setCusto("1 PC");
        itemRepository.save(tocha);

        Item pocaoDeCura = new Item();
        pocaoDeCura.setNome("Poção de Cura");
        pocaoDeCura.setDescricao("Ao beber esta poção, você recupera 2d4 + 2 pontos de vida.");
        pocaoDeCura.setPeso(0.25);
        pocaoDeCura.setCusto("50 PO");
        itemRepository.save(pocaoDeCura);
    }

    private void criarMagias() {
        System.out.println("Cadastrando Magias...");
        Magia misseisMagicos = new Magia();
        misseisMagicos.setNome("Mísseis Mágicos");
        misseisMagicos.setCirculo(1);
        misseisMagicos.setEscolaDeMagia(EscolaDeMagia.EVOCACAO);
        misseisMagicos.setTempoConjuracao("1 ação");
        misseisMagicos.setAlcance("36 metros");
        misseisMagicos.setComponentes(new HashSet<>(Arrays.asList("V", "S")));
        misseisMagicos.setDuracao("Instantânea");
        misseisMagicos.setDescricao("Você cria três dardos de energia mágica. Cada dardo atinge uma criatura de sua escolha que você possa ver, dentro do alcance. Um dardo causa 1d4 + 1 de dano de energia.");
        magiaRepository.save(misseisMagicos);

        Magia bolaDeFogo = new Magia();
        bolaDeFogo.setNome("Bola de Fogo");
        bolaDeFogo.setCirculo(3);
        bolaDeFogo.setEscolaDeMagia(EscolaDeMagia.EVOCACAO);
        bolaDeFogo.setTempoConjuracao("1 ação");
        bolaDeFogo.setAlcance("45 metros");
        bolaDeFogo.setComponentes(new HashSet<>(Arrays.asList("V", "S", "M")));
        bolaDeFogo.setDuracao("Instantânea");
        bolaDeFogo.setDescricao("Uma explosão de fogo em um ponto à sua escolha. Criaturas na área de 6 metros de raio devem fazer uma salvaguarda de Destreza, sofrendo 8d6 de dano de fogo em uma falha.");
        magiaRepository.save(bolaDeFogo);

        Magia curarFerimentos = new Magia();
        curarFerimentos.setNome("Curar Ferimentos");
        curarFerimentos.setCirculo(1);
        curarFerimentos.setEscolaDeMagia(EscolaDeMagia.ABJURACAO);
        curarFerimentos.setTempoConjuracao("1 ação");
        curarFerimentos.setAlcance("Toque");
        curarFerimentos.setComponentes(new HashSet<>(Arrays.asList("V", "S")));
        curarFerimentos.setDuracao("Instantânea");
        curarFerimentos.setDescricao("Uma criatura que você toca recupera um número de pontos de vida igual a 2d8 + seu modificador de atributo de conjuração.");
        magiaRepository.save(curarFerimentos);
    }

    private void criarTalentosOrigensERacas() {
        System.out.println("Cadastrando Talentos, Origens e Raças...");
        // Talentos
        Talento talentoArtifice = criarTalento("Artífice", CategoriaTalento.ORIGEM,null, "Você adquire proficiência com três Ferramentas de Artesão e pode fabricar itens rapidamente.", false);
        Talento talentoAlerta = criarTalento("Alerta", CategoriaTalento.ORIGEM, null, "Você ganha um bônus na Iniciativa, não pode ser surpreendido e criaturas que você não vê não ganham vantagem contra você.", false);
        Talento talentoAtacanteSelvagem = criarTalento("Atacante Selvagem", CategoriaTalento.ORIGEM,null, "Uma vez por turno, ao rolar dano para um ataque com arma corpo a corpo, você pode rolar novamente os dados de dano e usar qualquer um dos totais.", false);

        // Origens
        criarOrigem("Artesão", "Você aprendeu um ofício e fez parte de uma guilda.", new HashSet<>(Arrays.asList("FORÇA", "DESTREZA", "INTELIGÊNCIA")), talentoArtifice, new HashSet<>(Arrays.asList("Investigação", "Persuasão")), "Ferramentas de Artesão (uma à escolha)");
        criarOrigem("Criminoso", "Você tem um passado de crimes e contatos no submundo.", new HashSet<>(Arrays.asList("DESTREZA", "CONSTITUIÇÃO", "INTELIGÊNCIA")), talentoAlerta, new HashSet<>(Arrays.asList("Furtividade", "Prestidigitação")), "Ferramentas de Ladrão");
        criarOrigem("Soldado", "Você treinou como um soldado e serviu em um exército.", new HashSet<>(Arrays.asList("FORÇA", "DESTREZA", "CONSTITUIÇÃO")), talentoAtacanteSelvagem, new HashSet<>(Arrays.asList("Atletismo", "Intimidação")), "Kit de Jogos (um à escolha)");

        // Traços Raciais
        TracoRacial visaoNoEscuro = criarTraco("Visão no Escuro", "Você pode ver na penumbra a até 18 metros como se fosse luz plena, e na escuridão como se fosse penumbra.");
        TracoRacial ancestralidadeFeerica = criarTraco("Ancestralidade Feérica", "Você tem Vantagem em salvaguardas contra ser enfeitiçado, e magia não pode colocá-lo para dormir.");
        TracoRacial habilidoso = criarTraco("Habilidoso", "Você ganha proficiência em uma perícia à sua escolha.");
        TracoRacial sortudo = criarTraco("Sortudo", "Quando você rola um 1 em um d20 para um ataque, teste de atributo ou salvaguarda, você pode rolar o dado novamente e deve usar o novo resultado.");

        // Raças
        Raca humano = criarRaca("Humano", 9, Tamanho.MEDIO);
        humano.setTracosRaciais(new ArrayList<>(Collections.singletonList(habilidoso)));
        racaRepository.save(humano);

        Raca elfo = criarRaca("Elfo", 9, Tamanho.MEDIO);
        elfo.setTracosRaciais(new ArrayList<>(Arrays.asList(visaoNoEscuro, ancestralidadeFeerica)));
        racaRepository.save(elfo);

        Raca pequenino = criarRaca("Pequenino", 9, Tamanho.PEQUENO);
        pequenino.setTracosRaciais(new ArrayList<>(Collections.singletonList(sortudo)));
        racaRepository.save(pequenino);
    }

    private void criarClassesSubclassesECaracteristicas() {
        System.out.println("Cadastrando Classes, Subclasses e Características...");

        // --- TRAÇOS DE CLASSE ---
        TracoRacial furia = criarTraco("Fúria", "Pode entrar em fúria como ação bônus, ganhando vantagens em combate.");
        TracoRacial frenesi = criarTraco("Frenesi", "No 3º nível, você pode entrar em frenesi ao se enfurecer, permitindo um ataque corpo a corpo adicional como ação bônus.");
        TracoRacial conjuracaoMago = criarTraco("Conjuração (Mago)", "Você usa Inteligência para conjurar magias de Mago a partir de seu grimório.");
        TracoRacial esculpirMagias = criarTraco("Esculpir Magias", "No 2º nível, você pode criar bolsões de segurança em suas magias de evocação, protegendo criaturas.");
        TracoRacial ataqueFurtivo = criarTraco("Ataque Furtivo", "Uma vez por turno, você pode causar 1d6 extra de dano a uma criatura que atingir com um ataque se tiver vantagem na jogada de ataque.");
        TracoRacial acaoArdilosa = criarTraco("Ação Ardilosa", "No 2º nível, seu raciocínio rápido e agilidade permitem que você se mova e aja rapidamente. Você pode usar uma ação bônus para Correr, Desengajar ou Esconder-se.");

        // --- CLASSES E SUBCLASSES ---
        Classe barbaro = criarClasse("Barbaro", "Um combatente feroz da fúria primitiva.", 12, new HashSet<>(Arrays.asList("Armaduras Leves", "Médias", "Escudos")), new HashSet<>(Arrays.asList("Armas Simples", "Marciais")), new HashSet<>(Arrays.asList("FORÇA", "CONSTITUIÇÃO")), new HashSet<>(Arrays.asList("Atletismo", "Intimidação", "Natureza", "Percepção", "Sobrevivência")), 2, false, null);
        Subclasse berserker = criarSubclasse("Trilha do Berserker", "Canalize sua Fúria em um Frenesi Violento.", barbaro);

        Classe mago = criarClasse("Mago", "Um estudioso usuário de magia arcana.", 6, new HashSet<>(), new HashSet<>(Collections.singletonList("Armas Simples")), new HashSet<>(Arrays.asList("INTELIGÊNCIA", "SABEDORIA")), new HashSet<>(Arrays.asList("Arcanismo", "História", "Intuição", "Investigação", "Medicina", "Religião")), 2, true, Atributo.INTELIGENCIA);
        Subclasse evocador = criarSubclasse("Escola da Evocação", "Crie efeitos elementais explosivos.", mago);

        Classe ladino = criarClasse("Ladino", "Um especialista em furtividade e subterfúgio.", 8, new HashSet<>(Collections.singletonList("Armaduras Leves")), new HashSet<>(Arrays.asList("Armas Simples", "Besta de Mão", "Espada Longa", "Rapieira", "Espada Curta")), new HashSet<>(Arrays.asList("DESTREZA", "INTELIGÊNCIA")), new HashSet<>(Arrays.asList("Acrobacia", "Atletismo", "Enganação", "Furtividade", "Intuição", "Intimidação", "Percepção", "Performance", "Persuasão", "Prestidigitação")), 4, false, null);
        Subclasse ladrao = criarSubclasse("Ladrão", "Você aprimora suas habilidades de agilidade e furto.", ladino);

        // --- ASSOCIAÇÕES ---
        criarCaracteristicaDeClasse(barbaro, furia, 1);
        criarCaracteristicaDeSubclasse(berserker, frenesi, 3);
        criarCaracteristicaDeClasse(mago, conjuracaoMago, 1);
        criarCaracteristicaDeSubclasse(evocador, esculpirMagias, 2);
        criarCaracteristicaDeClasse(ladino, ataqueFurtivo, 1);
        criarCaracteristicaDeClasse(ladino, acaoArdilosa, 2);
    }

    // --- MÉTODOS DE CRIAÇÃO GENÉRICOS ---
    private TracoRacial criarTraco(String nome, String descricao) {
        TracoRacial traco = new TracoRacial();
        traco.setNome(nome);
        traco.setDescricao(descricao);
        return tracoRacialRepository.save(traco);
    }

    private Talento criarTalento(String nome, CategoriaTalento categoria, String preRequisito, String descricao, boolean isRepetivel) {
        Talento talento = new Talento();
        talento.setNome(nome);
        talento.setCategoria(categoria);
        talento.setPreRequisito(preRequisito);
        talento.setDescricao(descricao);
        talento.setRepetivel(isRepetivel);
        return talentoRepository.save(talento);
    }

    private void criarOrigem(String nome, String descricao, HashSet<String> atributos, Talento talento, HashSet<String> pericias, String ferramenta) {
        Origem origem = new Origem();
        origem.setNome(nome);
        origem.setDescricao(descricao);
        origem.setAtributosSugeridos(atributos);
        origem.setTalentoInicial(talento);
        origem.setProficienciasPericia(pericias);
        origem.setProficienciaFerramenta(ferramenta);
        origemRepository.save(origem);
    }

    private Raca criarRaca(String nome, int deslocamento, Tamanho tamanho) {
        Raca raca = new Raca();
        raca.setNome(nome);
        raca.setDeslocamento(deslocamento);
        raca.setTamanho(tamanho);
        return racaRepository.save(raca);
    }

    private Classe criarClasse(String nome, String descricao, int dadoDeVida, Set<String> armaduras, Set<String> armas, Set<String> testesResistencia, Set<String> opcoesPericias, int qtdPericias, boolean isConjurador, Atributo atributo) {
        Classe classe = new Classe();
        classe.setNome(nome);
        classe.setDescricao(descricao);
        classe.setDadoDeVida(dadoDeVida);
        classe.setProficienciasArmaduras(armaduras);
        classe.setProficienciasArmas(armas);
        classe.setProficienciasTestesDeResistencia(testesResistencia);
        classe.setOpcoesDePericias(opcoesPericias);
        classe.setQuantidadeEscolhaPericias(qtdPericias);
        classe.setConjurador(isConjurador);
        classe.setAtributoDeConjuracao(atributo);
        return classeRepository.save(classe);
    }

    private Subclasse criarSubclasse(String nome, String descricao, Classe classePai) {
        Subclasse subclasse = new Subclasse();
        subclasse.setNome(nome);
        subclasse.setDescricao(descricao);
        subclasse.setClassePai(classePai);
        return subclasseRepository.save(subclasse);
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

    private void criarUsuarioPadrao() {
        System.out.println("Criando usuário de teste...");
        if (usuarioRepository.findByEmail("admin@admin.com").isEmpty()) {
            Usuario admin = new Usuario();
            admin.setEmail("admin@mimic.com");
            admin.setSenha(passwordEncoder.encode("123456"));
            usuarioRepository.save(admin);
            System.out.println("Usuário 'admin@mimic.com' criado com senha '123456'.");
        }
    }

}
