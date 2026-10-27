package br.com.patinhas.controller;

import br.com.patinhas.model.Animal;
import br.com.patinhas.service.PatinhasService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final PatinhasService service;

    public AdminController(PatinhasService service) {
        this.service = service;
    }

    @GetMapping
    public String painel(Model model) {
        model.addAttribute("animais", service.listarTodosAnimais());
        model.addAttribute("solicitacoes", service.listarSolicitacoes());
        model.addAttribute("disponiveis", service.contarDisponiveis());
        model.addAttribute("pendentes", service.contarSolicitacoesPendentes());
        return "admin";
    }

    @GetMapping("/animais/novo")
    public String novoAnimal(Model model) {
        model.addAttribute("animal", new Animal());
        model.addAttribute("tituloFormulario", "Cadastrar animal");
        return "animal-form";
    }

    @GetMapping("/animais/{id}/editar")
    public String editarAnimal(@PathVariable Long id, Model model, RedirectAttributes ra) {
        Animal animal = service.buscarAnimal(id);
        if (animal == null) {
            ra.addFlashAttribute("erro", "Animal não encontrado.");
            return "redirect:/admin";
        }
        model.addAttribute("animal", animal);
        model.addAttribute("tituloFormulario", "Editar animal");
        return "animal-form";
    }

    @PostMapping("/animais/salvar")
    public String salvarAnimal(@ModelAttribute Animal animal, RedirectAttributes ra) {
        if (animal.getNome() == null || animal.getNome().isBlank()
                || animal.getEspecie() == null || animal.getEspecie().isBlank()
                || animal.getIdade() < 0) {
            ra.addFlashAttribute("erro", "Preencha nome, espécie e uma idade válida.");
            return "redirect:/admin/animais/novo";
        }
        if (animal.getStatus() == null || animal.getStatus().isBlank()) animal.setStatus("Disponível");
        service.salvarAnimal(animal);
        ra.addFlashAttribute("sucesso", "Animal salvo com sucesso.");
        return "redirect:/admin";
    }

    @PostMapping("/animais/{id}/excluir")
    public String excluirAnimal(@PathVariable Long id, RedirectAttributes ra) {
        if (service.excluirAnimal(id)) ra.addFlashAttribute("sucesso", "Animal excluído.");
        else ra.addFlashAttribute("erro", "Animal não encontrado.");
        return "redirect:/admin";
    }

    @PostMapping("/solicitacoes/{id}/status")
    public String atualizarStatus(@PathVariable Long id, @RequestParam String status, RedirectAttributes ra) {
        if (!java.util.List.of("Pendente", "Em análise", "Aprovada", "Recusada").contains(status)) {
            ra.addFlashAttribute("erro", "Status inválido.");
        } else if (service.atualizarStatusSolicitacao(id, status)) {
            ra.addFlashAttribute("sucesso", "Status da solicitação atualizado.");
        } else {
            ra.addFlashAttribute("erro", "Solicitação não encontrada.");
        }
        return "redirect:/admin";
    }
}
