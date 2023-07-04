package br.edu.infnet.acme.acme.interfaces;

import java.math.BigDecimal;

public interface CalculadoraTaxa {
    BigDecimal calcularTaxa(BigDecimal mensalidade);
}
