package br.com.patinhas.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SolicitacaoAdocao {
    private Long id;
    private Long animalId;
    private String nomeAnimal;
    private String nomePessoa;
    private String email;
    private String telefone;
    private String mensagem;
    private String status;
    private LocalDateTime data;

    public SolicitacaoAdocao() {}

    public SolicitacaoAdocao(Long id, Long animalId, String nomeAnimal, String nomePessoa,
                            String email, String telefone, String mensagem, String status,
                            LocalDateTime data) {
        this.id = id;
        this.animalId = animalId;
        this.nomeAnimal = nomeAnimal;
        this.nomePessoa = nomePessoa;
        this.email = email;
        this.telefone = telefone;
        this.mensagem = mensagem;
        this.status = status;
        this.data = data;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getAnimalId() { return animalId; }
    public void setAnimalId(Long animalId) { this.animalId = animalId; }
    public String getNomeAnimal() { return nomeAnimal; }
    public void setNomeAnimal(String nomeAnimal) { this.nomeAnimal = nomeAnimal; }
    public String getNomePessoa() { return nomePessoa; }
    public void setNomePessoa(String nomePessoa) { this.nomePessoa = nomePessoa; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getMensagem() { return mensagem; }
    public void setMensagem(String mensagem) { this.mensagem = mensagem; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getData() { return data; }
    public void setData(LocalDateTime data) { this.data = data; }
    public String getDataFormatada() {
        return data == null ? "" : data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }
}
