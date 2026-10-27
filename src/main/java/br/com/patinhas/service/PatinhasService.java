package br.com.patinhas.service;

import br.com.patinhas.model.Animal;
import br.com.patinhas.model.SolicitacaoAdocao;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class PatinhasService {
    private final List<Animal> animais = new ArrayList<>();
    private final List<SolicitacaoAdocao> solicitacoes = new ArrayList<>();
    private final AtomicLong animalSeq = new AtomicLong(0);
    private final AtomicLong solicitacaoSeq = new AtomicLong(0);

    @PostConstruct
    public void iniciarDadosExemplo() {
        salvarAnimal(new Animal(null, "Bella", "Cachorro", 2, "Fêmea", "Médio",
                "Carinhosa, brincalhona e pronta para conhecer uma nova família.", "/images/bella.jpeg", "Disponível"));
        salvarAnimal(new Animal(null, "Nina", "Cachorro", 1, "Fêmea", "Pequeno",
                "Muito dócil e curiosa. Adora companhia e passeios.", "/images/nina.jpeg", "Disponível"));
        salvarAnimal(new Animal(null, "Toby", "Cachorro", 3, "Macho", "Médio",
                "Companheiro tranquilo que merece um lar cheio de carinho.", "/images/toby.jpeg", "Disponível"));
        salvarAnimal(new Animal(null, "Mabel", "Cachorro", 2, "Fêmea", "Pequeno",
                "Amigável e cheia de energia para brincar.", "/images/mabel.jpeg", "Disponível"));
        salvarAnimal(new Animal(null, "Max", "Cachorro", 4, "Macho", "Grande",
                "Leal, carinhoso e pronto para ser seu melhor amigo.", "/images/max.jpeg", "Disponível"));
        salvarAnimal(new Animal(null, "Lili", "Gato", 1, "Fêmea", "Pequeno",
                "Gatinha curiosa, delicada e muito companheira.", "/images/lili.jfif", "Disponível"));
    }

    public synchronized List<Animal> listarAnimais(String busca, String especie, String status) {
        String termo = busca == null ? "" : busca.trim().toLowerCase(Locale.ROOT);
        return animais.stream()
                .filter(a -> termo.isBlank() || a.getNome().toLowerCase(Locale.ROOT).contains(termo)
                        || a.getDescricao().toLowerCase(Locale.ROOT).contains(termo))
                .filter(a -> especie == null || especie.isBlank() || especie.equalsIgnoreCase(a.getEspecie()))
                .filter(a -> status == null || status.isBlank() || status.equalsIgnoreCase(a.getStatus()))
                .sorted(Comparator.comparing(Animal::getId))
                .collect(Collectors.toList());
    }

    public synchronized List<Animal> listarTodosAnimais() {
        return new ArrayList<>(animais);
    }

    public synchronized Animal buscarAnimal(Long id) {
        return animais.stream().filter(a -> a.getId().equals(id)).findFirst().orElse(null);
    }

    public synchronized Animal salvarAnimal(Animal animal) {
        if (animal.getId() == null) {
            animal.setId(animalSeq.incrementAndGet());
            if (animal.getStatus() == null || animal.getStatus().isBlank()) animal.setStatus("Disponível");
            if (animal.getImagemUrl() == null || animal.getImagemUrl().isBlank()) animal.setImagemUrl("");
            animais.add(animal);
            return animal;
        }
        for (int i = 0; i < animais.size(); i++) {
            if (animais.get(i).getId().equals(animal.getId())) {
                animais.set(i, animal);
                return animal;
            }
        }
        return null;
    }

    public synchronized boolean excluirAnimal(Long id) {
        return animais.removeIf(a -> a.getId().equals(id));
    }

    public synchronized SolicitacaoAdocao criarSolicitacao(Long animalId, String nomePessoa,
            String email, String telefone, String mensagem) {
        Animal animal = buscarAnimal(animalId);
        if (animal == null || !"Disponível".equalsIgnoreCase(animal.getStatus())) return null;
        SolicitacaoAdocao s = new SolicitacaoAdocao(solicitacaoSeq.incrementAndGet(), animal.getId(),
                animal.getNome(), nomePessoa, email, telefone, mensagem, "Pendente", LocalDateTime.now());
        solicitacoes.add(s);
        return s;
    }

    public synchronized List<SolicitacaoAdocao> listarSolicitacoes() {
        return solicitacoes.stream()
                .sorted(Comparator.comparing(SolicitacaoAdocao::getData).reversed())
                .collect(Collectors.toList());
    }

    public synchronized boolean atualizarStatusSolicitacao(Long id, String status) {
        for (SolicitacaoAdocao s : solicitacoes) {
            if (s.getId().equals(id)) {
                s.setStatus(status);
                if ("Aprovada".equals(status)) {
                    Animal a = buscarAnimal(s.getAnimalId());
                    if (a != null) a.setStatus("Adotado");
                }
                return true;
            }
        }
        return false;
    }

    public synchronized long contarDisponiveis() {
        return animais.stream().filter(a -> "Disponível".equalsIgnoreCase(a.getStatus())).count();
    }

    public synchronized long contarSolicitacoesPendentes() {
        return solicitacoes.stream().filter(s -> "Pendente".equalsIgnoreCase(s.getStatus())).count();
    }
}
