package org.example.entities;

import java.util.StringJoiner;

public class Item extends _BaseEntity{
    private Usuario usuario;
    private String nome;
    private String descricao;
    private double preco;
    private int estoque;

    public Item() {
    }

    public Item(int id, Usuario usuario, String nome, String descricao, double preco, int estoque) {
        super(id);
        this.usuario = usuario;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.estoque = estoque;
    }

    public Usuario getCliente() {
        return usuario;
    }

    public void setCliente(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Item.class.getSimpleName() + "[", "]")
                .add("usuario=" + usuario)
                .add("nome='" + nome + "'")
                .add("descricao='" + descricao + "'")
                .add("preco=" + preco)
                .add("estoque=" + estoque)
                .toString();
    }
}
