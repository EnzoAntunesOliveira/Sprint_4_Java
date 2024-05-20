package org.example.entities;

import java.util.StringJoiner;
import java.util.regex.Pattern;

public class Endereco extends _BaseEntity{
    private static final Pattern CepP = Pattern.compile("^\\d{8}");
    private String cep;
    private String numero;
    private String complemento;
    private String pais;
    private Usuario usuario;

    public Endereco() {
    }

    public Endereco(int id, String cep, String numero, String complemento, String pais, Usuario usuario) {
        super(id);
        try {
            setCep(cep);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("CEP inválido");
        }
        this.numero = numero;
        this.complemento = complemento;
        this.pais = pais;
        this.usuario = usuario;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        try {
            if (!Pattern.matches(String.valueOf(CepP), cep)) {
                throw new IllegalArgumentException("CEP inválido");
            }
            this.cep = cep;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("CEP inválido");
        }
    }


    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Usuario getCliente() {
        return usuario;
    }

    public void setCliente(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Endereco.class.getSimpleName() + "[", "]")
                .add("cep='" + cep + "'")
                .add("numero='" + numero + "'")
                .add("complemento='" + complemento + "'")
                .add("pais='" + pais + "'")
                .add("usuario=" + usuario)
                .toString();
    }
}
