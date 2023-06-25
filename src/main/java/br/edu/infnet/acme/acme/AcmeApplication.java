package br.edu.infnet.acme.acme;

import br.edu.infnet.acme.acme.model.Assinatura;
import br.edu.infnet.acme.acme.model.Cliente;
import br.edu.infnet.acme.acme.model.Pagamento;
import br.edu.infnet.acme.acme.model.Produto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.w3c.dom.ls.LSOutput;

import java.math.BigDecimal;
import java.nio.file.Path;
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

		pagamentos.stream().sorted(Comparator.comparing(Pagamento::getDataCompra)).forEach(System.out::println);

		System.out.println("----------------------------------------------------------------------------------------");

		System.out.println("3 - Calcule e Imprima a soma dos valores de um pagamento com optional");
		Optional<BigDecimal> somaPrecosOptional = pagamentos.get(0)
				.getProdutos()
				.stream()
				.map(Produto::getPreco)
				.reduce(BigDecimal::add);
		System.out.println(somaPrecosOptional.orElse(BigDecimal.ZERO));

		System.out.println("----------------------------------------------------------------------------------------");

		System.out.println("3 - Calcule e Imprima a soma dos valores de um pagamento recebendo um Double diretamente");
		System.out.println(
				pagamentos.get(0)
						.getProdutos()
						.stream()
						.map(Produto::getPreco)
						.reduce(BigDecimal.ZERO, BigDecimal::add)
						.doubleValue()
		);

		System.out.println("----------------------------------------------------------------------------------------");

		System.out.println("4 - Calcule o Valor de todos os pagamentos da Lista de pagamentos");
		BigDecimal totalTodosPagamentos = pagamentos.stream()
				.flatMap(pagamento -> pagamento.getProdutos().stream())
				.map(Produto::getPreco).reduce(BigDecimal.ZERO, BigDecimal::add);
		System.out.println(totalTodosPagamentos);

		System.out.println("----------------------------------------------------------------------------------------");

		System.out.println("5 - Imprima a quantidade de cada Produto vendido.");
		pagamentos.stream()
				.flatMap(pagamento -> pagamento.getProdutos().stream())
				.collect(Collectors.groupingBy(Produto::getNome, Collectors.counting()))
				.forEach((key, value) -> System.out.println(key + ": " + value));

		System.out.println("----------------------------------------------------------------------------------------");

		System.out.println("6 - Crie um Mapa de <Cliente, List<Produto> , onde Cliente pode ser o nome do cliente.");
		Map<String, List<Produto>> clientesProdutos = pagamentos.stream()
				.collect(Collectors.groupingBy(
						pagamento -> pagamento.getCliente().getNome(),
						Collectors.flatMapping(pagamento -> pagamento.getProdutos().stream(), Collectors.toList())
				));
		clientesProdutos.forEach((cliente, produtosCliente) -> System.out.println(cliente + " " + produtosCliente));

		System.out.println("----------------------------------------------------------------------------------------");

		System.out.println("7 - Qual cliente gastou mais?");
		Optional<Map.Entry<String, List<Produto>>> clienteGastouMais = clientesProdutos.entrySet().stream()
				.max(Comparator.comparing(entry -> entry.getValue().stream()
						.map(Produto::getPreco)
						.reduce(BigDecimal.ZERO, BigDecimal::add)));

		if(clienteGastouMais.isPresent()){
			System.out.println(clienteGastouMais.get().getKey());
		}else{
			System.out.println("Sem informações");
		}

		System.out.println("----------------------------------------------------------------------------------------");

		System.out.println("8 - Quanto foi faturado em um determinado mês?");
		int month = 6;
		BigDecimal totalFaturadoMes = pagamentos.stream()
				.filter(pagamento -> pagamento.getDataCompra().getMonthValue() == month)
				.flatMap(pagamento -> pagamento.getProdutos().stream())
				.map(Produto::getPreco)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		System.out.println(totalFaturadoMes);

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

		assinaturas.stream()
				.filter(assinatura -> assinatura.getEnd() == null)
				.forEach(
						assinatura -> System.out.println(
								"Assinatura " + assinatura.getBegin() + " - " +
										Period.between(assinatura.getBegin(), LocalDate.now()).toTotalMonths() + " meses"
						)
				);

		System.out.println("----------------------------------------------------------------------------------------");

		System.out.println("11 - Imprima o tempo de meses entre o start e end de todas assinaturas. Não utilize IFs para assinaturas sem end.");

		assinaturas
				.forEach(
						assinatura -> System.out.println(
								"Assinatura " + assinatura.getBegin() + " - " + assinatura.getEnd() + " : " +
										Period.between(assinatura.getBegin(),
												assinatura.getEnd() != null ? assinatura.getEnd() : LocalDate.now()
										).toTotalMonths() + " meses"
						)
				);

		System.out.println("----------------------------------------------------------------------------------------");

		System.out.println("12 - Calcule o valor pago em cada assinatura até o momento. ");

		Map<Assinatura, BigDecimal> valoresPagosAssinaturas = assinaturas.stream()
				.collect(Collectors.toMap(
						assinatura -> assinatura,
						assinatura -> {
							long meses = Period.between(assinatura.getBegin(),
									assinatura.getEnd() != null ? assinatura.getEnd() : LocalDate.now()
							).toTotalMonths();
							return assinatura.getMensalidade().multiply(BigDecimal.valueOf(meses));
						}
				));
		valoresPagosAssinaturas.forEach((assinatura, total) -> System.out.println(assinatura + " => " + total));

	}

}
