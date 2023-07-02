package br.edu.infnet.acme.acme;

import br.edu.infnet.acme.acme.model.Assinatura;
import br.edu.infnet.acme.acme.model.Cliente;
import br.edu.infnet.acme.acme.model.Pagamento;
import br.edu.infnet.acme.acme.model.Produto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootApplication
public class AcmeApplication {

	public static void main(String[] args) {
		SpringApplication.run(AcmeApplication.class, args);

		//1 Crie uma Classe com um método main para criar alguns produtos, clientes e pagamentos.
		//  Crie Pagamentos com:  a data de hoje, ontem e um do mês passado.

		// 1 - Produtos
		List<Produto> produtos = new ArrayList<>();
		produtos.add(new Produto("Camisa", null, new BigDecimal("80.00")));
		produtos.add(new Produto("Bermuda", null, new BigDecimal("120.00")));
		produtos.add(new Produto("Tênis", null, new BigDecimal("380.00")));
		produtos.add(new Produto("Calça", null, new BigDecimal("180.00")));

		// 1 - Clientes
		List<Cliente> clientes = new ArrayList<>();
		clientes.add(new Cliente("Bruno"));
		clientes.add(new Cliente("Jackeline"));
		clientes.add(new Cliente("Sophia"));

		// 1 - Pagamentos (hoje, ontem e mês passado)
		List<Pagamento> pagamentos = new ArrayList<>();
		pagamentos.add(new Pagamento(Arrays.asList(produtos.get(0), produtos.get(1)), LocalDate.now(), clientes.get(0)));
		pagamentos.add(new Pagamento(Arrays.asList(produtos.get(0), produtos.get(2), produtos.get(3)), LocalDate.now().minusDays(1), clientes.get(1)));
		pagamentos.add(new Pagamento(Arrays.asList(produtos.get(0), produtos.get(2), produtos.get(3)), LocalDate.now().minusMonths(1), clientes.get(2)));
		pagamentos.add(new Pagamento(Arrays.asList(produtos.get(2)), LocalDate.now().minusMonths(1), clientes.get(0)));

		System.out.println("2 - Ordene e imprima os pagamentos pela data de compra");
		Pagamento.ordenadosPorDataCompra(pagamentos);

		System.out.println("----------------------------------------------------------------------------------------");

		System.out.println("3 - Calcule e Imprima a soma dos valores de um pagamento com optional");
		System.out.println(pagamentos.get(0).somaDosValoresComOptional());

		System.out.println("----------------------------------------------------------------------------------------");

		System.out.println("3 - Calcule e Imprima a soma dos valores de um pagamento recebendo um Double diretamente");
		System.out.println(pagamentos.get(0).somaDosValoresComDouble());

		System.out.println("----------------------------------------------------------------------------------------");

		System.out.println("4 - Calcule o Valor de todos os pagamentos da Lista de pagamentos");
		System.out.println(Pagamento.totalTodosPagamentos(pagamentos));

		System.out.println("----------------------------------------------------------------------------------------");

		System.out.println("5 - Imprima a quantidade de cada Produto vendido.");
		Produto.qtdProdutosVendidos(pagamentos);

		System.out.println("----------------------------------------------------------------------------------------");

		System.out.println("6 - Crie um Mapa de <Cliente, List<Produto> , onde Cliente pode ser o nome do cliente.");
		Map<String, List<Produto>> clientesProdutos = Cliente.clientesProdutos(pagamentos);
		clientesProdutos.forEach((cliente, produtosCliente) -> System.out.println(cliente + " " + produtosCliente));

		System.out.println("----------------------------------------------------------------------------------------");

		System.out.println("7 - Qual cliente gastou mais?");
		System.out.println(Cliente.clienteGastouMais(clientesProdutos));

		System.out.println("----------------------------------------------------------------------------------------");

		System.out.println("8 - Quanto foi faturado em um determinado mês?");
		System.out.println(Pagamento.totalFaturadoMes(pagamentos, 6));

		System.out.println("----------------------------------------------------------------------------------------");

		System.out.println("9 - Crie 3 assinaturas com assinaturas de 99.98 reais, sendo 2 deles com assinaturas encerradas");

		List<Assinatura> assinaturas = new ArrayList<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		assinaturas.add(new Assinatura(new BigDecimal("99.98"),
				LocalDate.parse("2023-01-01", formatter),
				LocalDate.parse("2023-05-31", formatter), clientes.get(0)));
		assinaturas.add(new Assinatura(new BigDecimal("99.98"),
				LocalDate.parse("2023-03-01", formatter),
				LocalDate.parse("2023-06-30", formatter), clientes.get(1)));
		assinaturas.add(new Assinatura(new BigDecimal("99.98"),
				LocalDate.parse("2023-04-01", formatter), clientes.get(2)));

		assinaturas.forEach(System.out::println);

		System.out.println("----------------------------------------------------------------------------------------");

		System.out.println("10 - Imprima o tempo em meses de alguma assinatura ainda ativa.");
		Assinatura.imprimirTempoMesesAtivas(assinaturas);

		System.out.println("----------------------------------------------------------------------------------------");

		System.out.println("11 - Imprima o tempo de meses entre o start e end de todas assinaturas. Não utilize IFs para assinaturas sem end.");
		Assinatura.imprimirTempoMeses(assinaturas);


		System.out.println("----------------------------------------------------------------------------------------");

		System.out.println("12 - Calcule o valor pago em cada assinatura até o momento. ");
		Assinatura.valoresPagosAssinaturas(assinaturas)
				.forEach((assinatura, total) -> System.out.println(assinatura + " => " + total));

	}

}
