package com.unialfa.model;

// Agente de saúde /Médico
public class Agente {
    // Long é um tipo primitivo que funciona igual Integer, porém, suporta valores mais altos
    private Long id;
    private String nome;

    // Este primeiro Construtor permite criar um agente com nome
    public Agente(String nome) {
        this.nome = nome;
    }

    // Este segundo construtor permite Alterar/Excluir um agente usando o ID
    public Agente(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    // Retorna uma representação em String da instância da classe Agente
    public String toString() {
        return "Agente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }
}
