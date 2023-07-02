package br.edu.infnet.acme.acme.model;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

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

    public static void qtdProdutosVendidos(List<Pagamento> pagamentos){
        pagamentos.stream()
                .flatMap(pagamento -> pagamento.getProdutos().stream())
                .collect(Collectors.groupingBy(Produto::getNome, Collectors.counting()))
                .forEach((key, value) -> System.out.println(key + ": " + value));
    }
}
