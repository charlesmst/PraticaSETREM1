/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import java.util.Date;
import model.Cidade;
import model.Pessoa;
import model.fluxo.ContaCategoria;
import services.CidadeService;
import services.PessoaService;
import services.ServiceException;
import services.fluxo.ContaCategoriaService;

/**
 *
 * @author Charles
 */
public class Feeder {
    public static void main(String[] args) throws ServiceException{
        Cidade cid = new Cidade();
        cid.setCep(98910000);
        cid.setNome("TRÊS DE MAIO");
        cid.setUf("RS");
        new CidadeService().saveOrUpdate(cid);
        
        Pessoa p = new Pessoa();
        p.setNome("Pessoa Teste Sistema");
        p.setCidade(cid);
        p.setDataNascimento(new Date());
        p.setTipo(Pessoa.TipoPessoa.fisica);
        new PessoaService().insert(p);
        
        ContaCategoria c = new ContaCategoria();
        c.setNome("Teste A receber");
        c.setTipo(ContaCategoria.TipoCategoria.entrada);
        c.setAtivo(true);
        
        ContaCategoria c2 = new ContaCategoria();
        c2.setNome("Teste a pagar");
        c2.setTipo(ContaCategoria.TipoCategoria.saida);
        c2.setAtivo(true);
        
        ContaCategoriaService serviceCategoria = new ContaCategoriaService();
        serviceCategoria.insert(c);
        serviceCategoria.insert(c2);
    }
}
