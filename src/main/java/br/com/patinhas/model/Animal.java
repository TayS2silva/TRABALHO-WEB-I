package br.com.patinhas.model;

public class Animal {
    private Long id;
    private String nome;
    private String especie;
    private int idade;
    private String sexo;
    private String porte;
    private String descricao;
    private String imagemUrl;
    private String status;

    public Animal() {}

    public Animal(Long id, String nome, String especie, int idade, String sexo,
                  String porte, String descricao, String imagemUrl, String status) {
        this.id = id;
        this.nome = nome;
        this.especie = especie;
        this.idade = idade;
        this.sexo = sexo;
        this.porte = porte;
        this.descricao = descricao;
        this.imagemUrl = imagemUrl;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEspecie() { return especie; }
    public void setEspecie(String especie) { this.especie = especie; }
    public int getIdade() { return idade; }
    public void setIdade(int idade) { this.idade = idade; }
    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }
    public String getPorte() { return porte; }
    public void setPorte(String porte) { this.porte = porte; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public String getImagemUrl() { return imagemUrl; }
    public void setImagemUrl(String imagemUrl) { this.imagemUrl = imagemUrl; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
