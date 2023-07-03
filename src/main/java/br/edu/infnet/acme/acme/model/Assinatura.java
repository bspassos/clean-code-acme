package br.edu.infnet.acme.acme.model;

import br.edu.infnet.acme.acme.model.enuns.TipoAssinatura;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Assinatura {

    BigDecimal mensalidade;

    LocalDate begin;

    Optional<LocalDate> end;

    Cliente cliente;

    TipoAssinatura tipoAssinatura;

    Boolean pagamentoAtrasado = false;

    private static final Logger logger = LogManager.getLogger(Assinatura.class);


    public Assinatura(BigDecimal mensalidade, LocalDate begin, Cliente cliente, TipoAssinatura tipoAssinatura) {
        this.mensalidade = mensalidade;
        this.begin = begin;
        this.end = Optional.empty();
        this.cliente = cliente;
        cliente.adicionarAssinatura(this);
        this.tipoAssinatura = tipoAssinatura;
        this.pagamentoAtrasado = false;
    }

    public Assinatura(BigDecimal mensalidade, LocalDate begin, LocalDate end, Cliente cliente, TipoAssinatura tipoAssinatura) {
        this.mensalidade = mensalidade;
        this.begin = begin;
        this.end = Optional.of(end);
        this.cliente = cliente;
        cliente.adicionarAssinatura(this);
        this.tipoAssinatura = tipoAssinatura;
        this.pagamentoAtrasado = false;
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

    public TipoAssinatura getTipoAssinatura() {
        return tipoAssinatura;
    }
    public void setTipoAssinatura(TipoAssinatura tipoAssinatura) {
        this.tipoAssinatura = tipoAssinatura;
    }

    public Boolean getPagamentoAtrasado() {
        return pagamentoAtrasado;
    }

    public void setPagamentoAtrasado(Boolean pagamentoAtrasado) {
        this.pagamentoAtrasado = pagamentoAtrasado;
    }

    @Override
    public String toString() {
        return "Assinatura - " +
                mensalidade + " - " +
                begin + " - " +
                end + " - " +
                cliente.getNome() + " - " +
                getTipoAssinatura().toString();
    }

    public static void imprimirTempoMesesAtivas(List<Assinatura> assinaturas){
        assinaturas.stream()
                .filter(assinatura -> assinatura.getEnd().isEmpty())
                .forEach(
                        assinatura -> logger.info("Assinatura {} - {} meses", assinatura.getBegin(),
                                        Period.between(assinatura.getBegin(), LocalDate.now()).toTotalMonths())
                );
    }

    public static void imprimirTempoMeses(List<Assinatura> assinaturas){
        assinaturas
                .forEach(
                        assinatura -> logger.info("Assinatura {} - {} : {} meses", assinatura.getBegin(),
                                        assinatura.getEnd(),
                                        Period.between(assinatura.getBegin(), assinatura.getEnd().orElse(LocalDate.now())
                                        ).toTotalMonths()
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

    public BigDecimal calcularTaxa() {
        BigDecimal taxa = BigDecimal.ZERO;

        if (tipoAssinatura == TipoAssinatura.SEMESTRAL) {
            taxa = mensalidade.multiply(BigDecimal.valueOf(0.03));
        } else if (tipoAssinatura == TipoAssinatura.TRIMESTRAL) {
            taxa = mensalidade.multiply(BigDecimal.valueOf(0.05));
        }

        return taxa;
    }





}
