package br.edu.infnet.acme.acme.model;

import java.math.BigDecimal;
import java.nio.file.Path;

public class Produto {

    String nome;

    Path file;

    BigDecimal preco;

    public Produto(String nome, Path file, BigDecimal preco){
        this.nome = nome;
        this.file = file;
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public Path getFile() {
        return file;
    }

    public BigDecimal getPreco() {
        return preco;
    }
}
