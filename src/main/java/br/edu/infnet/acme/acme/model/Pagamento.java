package br.edu.infnet.acme.acme.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
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

    public static void ordenadosPorDataCompra(List<Pagamento> pagamentos){
        pagamentos.stream().sorted(Comparator.comparing(Pagamento::getDataCompra)).forEach(System.out::println);
    }

    public BigDecimal somaDosValoresComOptional(){
        Optional<BigDecimal> somaPrecosOptional = this
                .getProdutos()
                .stream()
                .map(Produto::getPreco)
                .reduce(BigDecimal::add);
        return somaPrecosOptional.orElse(somaPrecosOptional.orElse(BigDecimal.ZERO));
    }

    public Double somaDosValoresComDouble(){
        return this.getProdutos()
                .stream()
                .map(Produto::getPreco)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .doubleValue();
    }

    public static BigDecimal totalTodosPagamentos(List<Pagamento> pagamentos){
        return pagamentos.stream()
                .flatMap(pagamento -> pagamento.getProdutos().stream())
                .map(Produto::getPreco).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static BigDecimal totalFaturadoMes(List<Pagamento> pagamentos, int month){
        return pagamentos.stream()
                .filter(pagamento -> pagamento.getDataCompra().getMonthValue() == month)
                .flatMap(pagamento -> pagamento.getProdutos().stream())
                .map(Produto::getPreco)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
