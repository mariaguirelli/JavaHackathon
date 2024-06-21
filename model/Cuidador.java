package com.unialfa.model;

public class Cuidador {
    private Long id;
    private String nome;
    private Long idIdoso;

    public Cuidador(String nome, Long idIdoso) {
        this.nome = nome;
        this.idIdoso = idIdoso;
    }

    public Cuidador(Long id, String nome, Long idIdoso) {
        this.id = id;
        this.nome = nome;
        this.idIdoso = idIdoso;
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

    public Long getIdIdoso() {
        return idIdoso;
    }

    public void setIdIdoso(Long idIdoso) {
        this.idIdoso = idIdoso;
    }

    @Override
    public String toString() {
        return "Cuidador{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", idIdoso=" + idIdoso +
                '}';
    }
}


