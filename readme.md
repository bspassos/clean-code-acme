## Trabalho da Disciplina de Clean Code

### Bruno da Silva Passos

### Pattern Strategy

A Classe assinatura possui um método de calcularTaxa cuja implementação era feita nele. Com a aplicação do pattern strategy foi criada uma interface CalculaTaxa e três classes TaxaAnual, TaxaSemestral e TaxaTrimestral onde são calculados as taxas de acordo com cada situação. No construtor da classe Assinatura é então definido qual classe será implementada de acordo com o tipo de assinatura e no método cacularTaxa agora a taxa é calculada utilizando a interface.