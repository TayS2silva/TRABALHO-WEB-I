package br.com.patinhas.controller;

import br.com.patinhas.model.Animal;
import br.com.patinhas.service.PatinhasService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SiteController {
    private final PatinhasService service;

    public SiteController(PatinhasService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String inicio(@RequestParam(required = false) String busca,
                         @RequestParam(required = false) String especie,
                         @RequestParam(required = false) String status, Model model) {
        model.addAttribute("animais", service.listarAnimais(busca, especie, status));
        model.addAttribute("busca", busca == null ? "" : busca);
        model.addAttribute("especieSelecionada", especie == null ? "" : especie);
        model.addAttribute("statusSelecionado", status == null ? "" : status);
        model.addAttribute("disponiveis", service.contarDisponiveis());
        return "index";
    }

    @GetMapping("/animais/{id}")
    public String detalhe(@PathVariable Long id, Model model, RedirectAttributes ra) {
        Animal animal = service.buscarAnimal(id);
        if (animal == null) {
            ra.addFlashAttribute("erro", "Animal não encontrado.");
            return "redirect:/";
        }
        model.addAttribute("animal", animal);
        return "animal";
    }

    @GetMapping("/adotar/{id}")
    public String formularioAdocao(@PathVariable Long id, Model model, RedirectAttributes ra) {
        Animal animal = service.buscarAnimal(id);
        if (animal == null || !"Disponível".equalsIgnoreCase(animal.getStatus())) {
            ra.addFlashAttribute("erro", "Este animal não está disponível para adoção.");
            return "redirect:/";
        }
        model.addAttribute("animal", animal);
        return "adocao";
    }

    @PostMapping("/adotar")
    public String enviarAdocao(@RequestParam Long animalId,
                               @RequestParam @NotBlank @Size(max = 100) String nomePessoa,
                               @RequestParam @Email @NotBlank String email,
                               @RequestParam @NotBlank String telefone,
                               @RequestParam(required = false) @Size(max = 1000) String mensagem,
                               RedirectAttributes ra) {
        var solicitacao = service.criarSolicitacao(animalId, nomePessoa.trim(), email.trim(),
                telefone.trim(), mensagem == null ? "" : mensagem.trim());
        if (solicitacao == null) {
            ra.addFlashAttribute("erro", "Não foi possível enviar. Confira se o animal ainda está disponível.");
            return "redirect:/";
        }
        ra.addFlashAttribute("sucesso", "Solicitação enviada! A equipe Patinhas poderá entrar em contato.");
        return "redirect:/";
    }
}
