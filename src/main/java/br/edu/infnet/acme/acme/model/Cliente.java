package br.edu.infnet.acme.acme.model;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Cliente {

    String nome;

    public Cliente(String nome){
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public static Map<String, List<Produto>> clientesProdutos(List<Pagamento> pagamentos){
        return pagamentos.stream()
                .collect(Collectors.groupingBy(
                        pagamento -> pagamento.getCliente().getNome(),
                        Collectors.flatMapping(pagamento -> pagamento.getProdutos().stream(), Collectors.toList())
                ));
    }

    public static String clienteGastouMais(Map<String, List<Produto>> clientesProdutos){
        Optional<Map.Entry<String, List<Produto>>> clienteGastouMais = clientesProdutos.entrySet().stream()
                .max(Comparator.comparing(entry -> entry.getValue().stream()
                        .map(Produto::getPreco)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)));

        if(clienteGastouMais.isPresent()){
            return clienteGastouMais.get().getKey();
        }

        return "Sem informações";

    }

}
