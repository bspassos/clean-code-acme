package br.edu.infnet.acme.acme.model;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class Pagamento {

    List<Produto> produtos;

    LocalDate dataCompra;

    Cliente cliente;

    public Pagamento(List<Produto> produtos, LocalDate dataCompra, Cliente cliente) {
        this.produtos = produtos;
        this.dataCompra = dataCompra;
        this.cliente = cliente;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public LocalDate getDataCompra() {
        return dataCompra;
    }

    public Cliente getCliente() {
        return cliente;
    }

    @Override
    public String toString() {
        return "Pagamento - " +
                dataCompra + " - " +
                cliente.getNome() + "[" +
                    produtos.stream()
                            .map(p -> p.nome + ":" + p.preco)
                            .collect(Collectors.joining(", "))
                + "]";
    }
}
