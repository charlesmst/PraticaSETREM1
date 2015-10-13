package model.estoque;

import java.util.Date;
import model.fluxo.Conta;
import model.Pessoa;

public class EstoqueMovimentacao {

	private int id;

	private int quantidade;

	private Date dataLancamento;

	private Estoque estoque;

	private String descricao;

	private double valorUnitario;

	private String situacao;

	private MovimentacaoTipo movimentacaoTipo;

	private Conta conta;

	private String notaFiscal;

	private Pessoa pessoa;

	public void update() {

	}

	public void insert() {

	}

}
