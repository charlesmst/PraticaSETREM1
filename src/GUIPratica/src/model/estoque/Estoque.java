package model.estoque;

import java.util.Date;
import java.util.List;

public class Estoque {

	private int id;

	private EstoqueEntrada entrada;

	private int lote;

	private Item item;

	private int quantidadeCompra;

	private int quantidadeDisponivel;

	private double valorUnitario;

	private double valorUnidadeVenda;

	private Date dataValidade;

	private List movimentacoes;

	public void insert() {

	}

	public void update() {

	}

}
