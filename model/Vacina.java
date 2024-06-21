package com.unialfa.model;

import java.time.LocalDate;

public class Vacina {
    private Long id;
    private String nome;
    private LocalDate data;

    public Vacina(LocalDate data, String nome) {
        this.data = data;
        this.nome = nome;
    }

    public Vacina(Long id, String nome, LocalDate data) {
        this.id = id;
        this.nome = nome;
        this.data = data;
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

    public LocalDate getData() {

        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Vacina{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", data=" + data +
                '}';
    }
}
