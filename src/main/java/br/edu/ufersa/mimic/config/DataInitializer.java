package br.edu.ufersa.mimic.config;

import br.edu.ufersa.mimic.repository.caracteristicas.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.edu.ufersa.mimic.model.caracteristicas.*;
import br.edu.ufersa.mimic.model.enums.*;
import br.edu.ufersa.mimic.repository.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    // Injeta todos os repositórios que vamos precisar
    @Autowired
    private RacaRepository racaRepository;
    @Autowired
    private SubracaRepository subracaRepository;
    @Autowired
    private ClasseRepository classeRepository;
    @Autowired
    private CaracteristicasDeClasseRepository caracteristicaDeClasseRepository;
    @Autowired
    private SubclasseRepository subclasseRepository;
    @Autowired
    private TracoRepository tracoRepository;


    @Override
    public void run(String... args) throws Exception {
        if (racaRepository.count() == 0 && classeRepository.count() == 0) {
            System.out.println("banco de dados vazio - populando");

            // --- 1. CRIAR TRAÇOS (HABILIDADES) ---
            Traco visaoNoEscuro = new Traco();
            visaoNoEscuro.setNome("Visão no Escuro");
            visaoNoEscuro.setDescricao("Você tem Visão no Escuro com um alcance de 18 metros.");
            tracoRepository.save(visaoNoEscuro);

            Traco ancestralidadeFeerica = new Traco();
            ancestralidadeFeerica.setNome("Ancestralidade Feérica");
            ancestralidadeFeerica.setDescricao("Você tem Vantagem ao realizar salvaguardas para evitar ou encerrar a condição Enfeitiçado.");
            tracoRepository.save(ancestralidadeFeerica);

            Traco furia = new Traco();
            furia.setNome("Fúria");
            furia.setDescricao("Você pode se imbuir com um poder primitivo chamado Fúria, que concede força e resiliência extraordinárias.");
            tracoRepository.save(furia);

            Traco frenesi = new Traco();
            frenesi.setNome("Frenesi");
            frenesi.setDescricao("Se você usar Ataque Imprudente enquanto sua Fúria estiver ativa, você causa dano adicional.");
            tracoRepository.save(frenesi);


            // --- 2. CRIAR RAÇA E SUB-RAÇAS (Exemplo com Elfo) ---
            Raca elfo = new Raca();
            elfo.setNome("Elfo");
            elfo.setDeslocamento(9);
            elfo.setTamanho("Médio");
            elfo.setTracosRaciais(Arrays.asList(visaoNoEscuro, ancestralidadeFeerica));
            Raca elfoSalvo = racaRepository.save(elfo); // Salva a raça principal primeiro!

            Subraca altoElfo = new Subraca();
            altoElfo.setNome("Alto Elfo");
            altoElfo.setRacaPrincipal(elfoSalvo); // Associa à raça principal
            subracaRepository.save(altoElfo);

            Subraca elfoDaFloresta = new Subraca();
            elfoDaFloresta.setNome("Elfo da Floresta");
            elfoDaFloresta.setRacaPrincipal(elfoSalvo);
            subracaRepository.save(elfoDaFloresta);


            // --- 3. CRIAR CLASSE E SUBCLASSES (Exemplo com Bárbaro) ---
            Classe barbaro = new Classe();
            barbaro.setNome(EnumNomeClasse.BARBARO);
            barbaro.setDadoDeVida(12);
            barbaro.setProficienciasArmas(new HashSet<>(Arrays.asList("Armas Simples", "Armas Marciais")));
            barbaro.setProficienciasTestesDeResistencia(new HashSet<>(Arrays.asList("FORÇA", "CONSTITUIÇÃO")));
            barbaro.setOpcoesDePericias(new HashSet<>(Arrays.asList("Atletismo", "Intimidação", "Lidar com Animais", "Natureza", "Percepção", "Sobrevivência")));
            barbaro.setQuantidadeEscolhaPericias(2);
            Classe barbaroSalvo = classeRepository.save(barbaro); // Salva a classe principal

            Subclasse berserker = new Subclasse();
            berserker.setNome("Trilha do Berserker");
            berserker.setClassePai(barbaroSalvo);
            subclasseRepository.save(berserker);

            // --- 4. LIGAR TRAÇOS ÀS CLASSES/SUBCLASSES ---
            // Fúria é do Bárbaro (classe), ganha no nível 1
            CaracteristicaDeClasse tracoFuria = new CaracteristicaDeClasse();
            tracoFuria.setNivelAdquirido(1);
            tracoFuria.setClasse(barbaroSalvo); // Liga à classe Bárbaro
            tracoFuria.setSubclasse(null); // Não é de subclasse
            tracoFuria.setTraco(furia); // Liga ao Traco "Fúria"
            caracteristicaDeClasseRepository.save(tracoFuria);

            // Frenesi é do Berserker (subclasse), ganha no nível 3
            CaracteristicaDeClasse tracoFrenesi = new CaracteristicaDeClasse();
            tracoFrenesi.setNivelAdquirido(3);
            tracoFrenesi.setClasse(null); // Não é da classe principal
            tracoFrenesi.setSubclasse(berserker); // Liga à subclasse Berserker
            tracoFrenesi.setTraco(frenesi); // Liga ao Traco "Frenesi"
            caracteristicaDeClasseRepository.save(tracoFrenesi);

            System.out.println("Dados iniciais persistidos com sucesso!");
        } else {
            System.out.println("O banco de dados já parece estar populado. Nenhum dado foi inserido.");
        }
    }
}