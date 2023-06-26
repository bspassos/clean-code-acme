package br.edu.infnet.acme.acme.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public class Assinatura {

    BigDecimal mensalidade;

    LocalDate begin;

    Optional<LocalDate> end;

    Cliente cliente;

    public Assinatura(BigDecimal mensalidade, LocalDate begin, Cliente cliente) {
        this.mensalidade = mensalidade;
        this.begin = begin;
        this.end = Optional.empty();
        this.cliente = cliente;
    }

    public Assinatura(BigDecimal mensalidade, LocalDate begin, LocalDate end, Cliente cliente) {
        this.mensalidade = mensalidade;
        this.begin = begin;
        this.end = Optional.of(end);
        this.cliente = cliente;
    }

    public BigDecimal getMensalidade() {
        return mensalidade;
    }

    public LocalDate getBegin() {
        return begin;
    }

    public Optional<LocalDate> getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = Optional.ofNullable(end);
    }

    public Cliente getCliente() {
        return cliente;
    }

    @Override
    public String toString() {
        return "Assinatura - " +
                mensalidade + " - " +
                begin + " - " +
                end + " - " +
                cliente.getNome();
    }
}
