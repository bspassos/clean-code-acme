package br.edu.infnet.acme.acme.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public static void imprimirTempoMesesAtivas(List<Assinatura> assinaturas){
        assinaturas.stream()
                .filter(assinatura -> assinatura.getEnd().isEmpty())
                .forEach(
                        assinatura -> System.out.println(
                                "Assinatura " + assinatura.getBegin() + " - " +
                                        Period.between(assinatura.getBegin(), LocalDate.now()).toTotalMonths() + " meses"
                        )
                );
    }

    public static void imprimirTempoMeses(List<Assinatura> assinaturas){
        assinaturas
                .forEach(
                        assinatura -> System.out.println(
                                "Assinatura " + assinatura.getBegin() + " - " + assinatura.getEnd() + " : " +
                                        Period.between(assinatura.getBegin(), assinatura.getEnd().orElse(LocalDate.now())
                                        ).toTotalMonths() + " meses"
                        )
                );
    }

    public static Map<Assinatura, BigDecimal> valoresPagosAssinaturas(List<Assinatura> assinaturas){
        return assinaturas.stream()
                .collect(Collectors.toMap(
                        assinatura -> assinatura,
                        assinatura -> {
                            long meses = Period.between(assinatura.getBegin(), assinatura.getEnd().orElse(LocalDate.now())
                            ).toTotalMonths();
                            return assinatura.getMensalidade().multiply(BigDecimal.valueOf(meses));
                        }
                ));
    }
}
