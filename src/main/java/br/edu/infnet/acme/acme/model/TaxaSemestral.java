package br.edu.infnet.acme.acme.model;

import br.edu.infnet.acme.acme.interfaces.CalculadoraTaxa;

import java.math.BigDecimal;

public class TaxaSemestral implements CalculadoraTaxa {
    @Override
    public BigDecimal calcularTaxa(BigDecimal mensalidade) {
        return mensalidade.multiply(BigDecimal.valueOf(0.03));
    }
}
