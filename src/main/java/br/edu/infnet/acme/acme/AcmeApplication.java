package br.edu.infnet.acme.acme;

import br.edu.infnet.acme.acme.model.Assinatura;
import br.edu.infnet.acme.acme.model.Cliente;
import br.edu.infnet.acme.acme.model.Pagamento;
import br.edu.infnet.acme.acme.model.Produto;
import br.edu.infnet.acme.acme.model.enuns.TipoAssinatura;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SpringBootApplication
public class AcmeApplication {

	private static final Logger logger = LogManager.getLogger(AcmeApplication.class);

	private static final String SEPARATOR = "----------------------------------------------------------------------------------------";
	private static final String VALOR_PADRAO_ASSINATURA = "99.98";// O sonarqube que pediu isso devido ao preço se repetir no código

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

		logger.info("2 - Ordene e imprima os pagamentos pela data de compra");
		Pagamento.ordenadosPorDataCompra(pagamentos);

		logger.info(SEPARATOR);

		logger.info("3 - Calcule e Imprima a soma dos valores de um pagamento com optional");
		logger.info(pagamentos.get(0).somaDosValoresComOptional());

		logger.info(SEPARATOR);

		logger.info("3 - Calcule e Imprima a soma dos valores de um pagamento recebendo um Double diretamente");
		logger.info(pagamentos.get(0).somaDosValoresComDouble());

		logger.info(SEPARATOR);

		logger.info("4 - Calcule o Valor de todos os pagamentos da Lista de pagamentos");
		logger.info(Pagamento.totalTodosPagamentos(pagamentos));

		logger.info(SEPARATOR);

		logger.info("5 - Imprima a quantidade de cada Produto vendido.");
		Produto.qtdProdutosVendidos(pagamentos);

		logger.info(SEPARATOR);

		logger.info("6 - Crie um Mapa de <Cliente, List<Produto> , onde Cliente pode ser o nome do cliente.");
		Map<String, List<Produto>> clientesProdutos = Cliente.clientesProdutos(pagamentos);
		clientesProdutos.forEach((cliente, produtosCliente) -> logger.info("{} {}", cliente, produtosCliente));

		logger.info(SEPARATOR);

		logger.info("7 - Qual cliente gastou mais?");
		logger.info(Cliente.clienteGastouMais(clientesProdutos));

		logger.info(SEPARATOR);

		logger.info("8 - Quanto foi faturado em um determinado mês?");
		logger.info(Pagamento.totalFaturadoMes(pagamentos, 6));

		logger.info(SEPARATOR);

		logger.info("9 - Crie 3 assinaturas com assinaturas de 99.98 reais, sendo 2 deles com assinaturas encerradas");

		List<Assinatura> assinaturas = new ArrayList<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		assinaturas.add(new Assinatura(new BigDecimal(VALOR_PADRAO_ASSINATURA),
				LocalDate.parse("2023-01-01", formatter),
				LocalDate.parse("2023-05-31", formatter), clientes.get(0), TipoAssinatura.ANUAL));
		assinaturas.add(new Assinatura(new BigDecimal(VALOR_PADRAO_ASSINATURA),
				LocalDate.parse("2023-03-01", formatter),
				LocalDate.parse("2023-06-30", formatter), clientes.get(1), TipoAssinatura.SEMESTRAL));
		assinaturas.add(new Assinatura(new BigDecimal(VALOR_PADRAO_ASSINATURA),
				LocalDate.parse("2023-04-01", formatter), clientes.get(2), TipoAssinatura.TRIMESTRAL));

		assinaturas.forEach(assinatura -> logger.info(assinatura.toString()));

		logger.info(SEPARATOR);

		logger.info("10 - Imprima o tempo em meses de alguma assinatura ainda ativa.");
		Assinatura.imprimirTempoMesesAtivas(assinaturas);

		logger.info(SEPARATOR);

		logger.info("11 - Imprima o tempo de meses entre o start e end de todas assinaturas. Não utilize IFs para assinaturas sem end.");
		Assinatura.imprimirTempoMeses(assinaturas);


		logger.info(SEPARATOR);

		logger.info("12 - Calcule o valor pago em cada assinatura até o momento. ");
		Assinatura.valoresPagosAssinaturas(assinaturas)
				.forEach((assinatura, total) -> logger.info("{} => {}", assinatura, total));

		logger.info("========================================================================================");
		logger.info("======================================Entregável 2======================================");
		logger.info("========================================================================================");

		logger.info("Crie 3 tipos de assinatura, anual, semestral e trimestral.");
		logger.info("Crie um método para calcular uma taxa para cada assinatura.");

		assinaturas.forEach(assinatura -> logger.info("{} - TAXA: {}", assinatura, assinatura.calcularTaxa()));

		logger.info(SEPARATOR);

		logger.info("Crie um mecanismo para validar clientes que tentarem fazer compras com assinatura em atraso e não deixá-los comprar.");
		assinaturas.get(0).setPagamentoAtrasado(true);
		pagamentos.add(new Pagamento(Collections.singletonList(produtos.get(2)), LocalDate.now(), clientes.get(0)));


	}

}
