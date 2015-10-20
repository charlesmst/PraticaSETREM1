/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.fluxo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import model.fluxo.Conta;
import model.fluxo.FormaPagamento;
import model.fluxo.Parcela;
import model.fluxo.ParcelaPagamento;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import services.ServiceException;

/**
 *
 * @author Charles
 */
public class ParcelaServiceTest {

    Conta c;

    public ParcelaServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        c = new Conta();
        FormaPagamento f = new FormaPagamento();
        c.setFormaPagamento(f);

        c.setParcelas(new ArrayList<>());
        Parcela p = new Parcela();
        p.setValor(100);
        p.setParcela(1);

        List<ParcelaPagamento> pagamentos = new ArrayList<>();

        ParcelaPagamento pp = new ParcelaPagamento();
        pp.setValor(10);
        pagamentos.add(pp);
        p.setPagamentos(pagamentos);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of gerarParcelas method, of class ParcelaService.
     */
    @Test
    public void testGerarParcelas() {
        System.out.println("gerarParcelas");
        int parcelas = 4;
        double valor = 100.0;
        Date dataPrimeiroPagamento = new Date();
        int fieldIncrementar = Calendar.DATE;
        int incrementar = 7;
        ParcelaService instance = new ParcelaService();

        c.setParcelas(new ArrayList<>());
        for (int i = 0; i < parcelas; i++) {
            Parcela p = new Parcela();
            p.setPagamentos( new ArrayList<>());
            p.getPagamentos().add(new ParcelaPagamento(){{
                setValor(100);
            
            }});
            c.getParcelas().add(p);
        }
        try {
            instance.gerarParcelas(c, parcelas, valor, dataPrimeiroPagamento, fieldIncrementar, incrementar);
            fail("Parcela gerou com pagamentos");
        } catch (IllegalArgumentException e) {
        }

        c.setParcelas(null);

        
        instance.gerarParcelas(c, parcelas, valor, dataPrimeiroPagamento, fieldIncrementar, incrementar);

        double soma = 0;
        for (Parcela parcela : c.getParcelas()) {
            soma += parcela.getValor();
        }

        assertTrue(valor == soma);

        c.setParcelas(null);

        double acrescimo = 1.5d;
        c.getFormaPagamento().setAcrescimo(acrescimo);
        instance.gerarParcelas(c, parcelas, valor, dataPrimeiroPagamento, fieldIncrementar, incrementar);
        
        soma = 0;
        
        for (Parcela parcela : c.getParcelas()) {
            soma += parcela.getValor();
        }
        
        assertTrue(valor + acrescimo*parcelas == soma);

    }

}
