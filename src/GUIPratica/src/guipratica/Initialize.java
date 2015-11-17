/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guipratica;

import java.sql.PreparedStatement;
import java.util.Date;
import java.util.List;
import javax.swing.UIManager;
import model.Pessoa;
import model.Usuario;
import model.estoque.MovimentacaoTipo;
import model.fluxo.ContaCategoria;
import model.fluxo.FormaPagamento;
import model.ordem.OrdemStatus;
import net.sf.jasperreports.engine.util.JRProperties;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import services.PessoaService;
import services.UsuarioService;
import services.estoque.MovimentacaoTipoService;
import services.fluxo.ContaCategoriaService;
import services.fluxo.FormaPagamentoService;
import services.ordem.OrdemStatusService;
import utils.Globals;
import utils.HibernateUtil;
import utils.Parametros;

/**
 *
 * @author charles
 */
public class Initialize {

    public static void init() throws Exception {
        validaParametros();
        JRProperties.setProperty("net.sf.jasperreports.awt.ignore.missing.font", "true");

        criaIndices();
    }

    private static void criaIndices() {
        /*SELECT 'CREATE INDEX fk_' || conname || '_idx ON ' 
         || relname || ' ' || 
         regexp_replace(
         regexp_replace(pg_get_constraintdef(pg_constraint.oid, true), 
         ' REFERENCES.*$','',''), 'FOREIGN KEY ','','') || ';'
         FROM pg_constraint 
         JOIN pg_class 
         ON (conrelid = pg_class.oid)
         JOIN pg_namespace
         ON (relnamespace = pg_namespace.oid)
         WHERE contype = 'f'
         AND nspname = 'public'
         AND NOT EXISTS (
         SELECT * FROM pg_class pgc
         JOIN pg_namespace pgn ON (pgc.relnamespace = pgn.oid)
         WHERE relkind='i'
         AND pgc.relname = ('fk_' || conname || '_idx') );*/
        Session s = null;
        //Cria os indices automaticamente
        try {
            s = HibernateUtil.getSessionFactory().openSession();
            List<String> l = s.createSQLQuery("SELECT 'CREATE INDEX fk_' || conname || '_idx ON ' \n"
                    + "       || relname || ' ' || \n"
                    + "       regexp_replace(\n"
                    + "           regexp_replace(pg_get_constraintdef(pg_constraint.oid, true), \n"
                    + "           ' REFERENCES.*$','',''), 'FOREIGN KEY ','','') || ';'\n"
                    + "FROM pg_constraint \n"
                    + "JOIN pg_class \n"
                    + "    ON (conrelid = pg_class.oid)\n"
                    + "JOIN pg_namespace\n"
                    + "    ON (relnamespace = pg_namespace.oid)\n"
                    + "WHERE contype = 'f'\n"
                    + "  AND nspname = 'public'\n"
                    + "  AND NOT EXISTS (\n"
                    + "  SELECT pgc.relnamespace FROM pg_class pgc\n"
                    + "    JOIN pg_namespace pgn ON (pgc.relnamespace = pgn.oid)\n"
                    + "  WHERE relkind='i'\n"
                    + "    AND pgc.relname = ('fk_' || conname || '_idx') )").list();

            s.beginTransaction();
            for (String l1 : l) {
                s.doWork((c) -> {
                    c.createStatement().execute(l1);

                });

            }
        } finally {
            if (s != null) {
                s.close();
            }
        }
    }

    private static void validaParametros() throws Exception {
        ContaCategoriaService serviceCategoria = new ContaCategoriaService();
        FormaPagamentoService serviceFormaPagamento = new FormaPagamentoService();

        Parametros.getInstance().validaParametro("categoria_movimento_saida", (value) -> {
            ContaCategoria c = serviceCategoria.findById(Integer.parseInt(value));
            return c != null && c.getTipo() == ContaCategoria.TipoCategoria.saida;
        }, (value) -> {
            ContaCategoria cat = new ContaCategoria();
            cat.setNome("MOVIMENTAÇÃO SAIDA");
            cat.setTipo(ContaCategoria.TipoCategoria.saida);
            cat.setAtivo(true);
            serviceCategoria.insert(cat);
            return cat.getId() + "";
        });

        Parametros.getInstance().validaParametro("categoria_movimento_entrada", (value) -> {
            ContaCategoria c = serviceCategoria.findById(Integer.parseInt(value));
            return c != null && c.getTipo() == ContaCategoria.TipoCategoria.entrada;
        }, (value) -> {
            ContaCategoria cat = new ContaCategoria();
            cat.setNome("MOVIMENTAÇÃO ENTRADA");
            cat.setTipo(ContaCategoria.TipoCategoria.entrada);
            cat.setAtivo(true);
            serviceCategoria.insert(cat);
            return cat.getId() + "";
        });

        //forma_pagamento_a_vista
        Parametros.getInstance().validaParametro("forma_pagamento_a_vista", (value) -> {
            return serviceFormaPagamento.findById(Integer.parseInt(value)) != null;
        }, (value) -> {
            FormaPagamento cat = new FormaPagamento();
            cat.setNome("À VISTA");
            cat.setAtivo(true);
            serviceFormaPagamento.insert(cat);
            return cat.getId() + "";
        });

        //Status de finalizado
        OrdemStatusService serviceStatus = new OrdemStatusService();
        Parametros.getInstance().validaParametro("status_finalizador", (value) -> {
            OrdemStatus c = serviceStatus.findById(Integer.parseInt(value));
            return c != null && c.isFinaliza();
        }, (value) -> {
            OrdemStatus cat = new OrdemStatus();
            cat.setNome("FINALIZADO");
            cat.setAtivo(true);
            cat.setFinaliza(true);
            serviceStatus.insert(cat);
            return cat.getId() + "";
        });
        Parametros.getInstance().validaParametro("status_inicial", (value) -> {
            OrdemStatus c = serviceStatus.findById(Integer.parseInt(value));
            return c != null;
        }, (value) -> {
            OrdemStatus cat = new OrdemStatus();
            cat.setNome("ABERTO");
            cat.setAtivo(true);
            cat.setFinaliza(false);
            serviceStatus.insert(cat);
            return cat.getId() + "";
        });
        //Status aberto
        Parametros.getInstance().validaParametro("status_aberto", (value) -> {
            OrdemStatus c = serviceStatus.findById(Integer.parseInt(value));
            return c != null && !c.isFinaliza();
        }, (value) -> {
            OrdemStatus cat = new OrdemStatus();
            cat.setNome("ABERTO");
            cat.setAtivo(true);
            cat.setFinaliza(false);
            serviceStatus.insert(cat);
            return cat.getId() + "";
        });

        Parametros.getInstance().validaParametro("usuario_admin", (value) -> {
            Usuario c = new UsuarioService().findById(Integer.parseInt(value));
            return c != null;
        }, (value) -> {
            Pessoa p = new Pessoa();
            p.setNome("ADMIN");
            p.setDataNascimento(new Date());
            p.setTipo(Pessoa.TipoPessoa.fisica);
            new PessoaService().insert(p);
            Usuario cat = new Usuario();
            cat.setPessoa(p);
            cat.setAtivo(true);
            cat.setUsuario("ADMIN");
            cat.setAtivo(true);
            cat.setNivel(Usuario.Tipo.gestor);
            cat.setSenha(utils.Utils.criptografa("ADMIN"));
            new UsuarioService().insert(cat);
            return cat.getId() + "";
        });

        //forma_pagamento_a_vista
        Parametros.getInstance().validaParametro("categoria_terceiros", (value) -> {
            ContaCategoria cc = new ContaCategoriaService().findById(Integer.parseInt(value));
            return cc != null && cc.getTipo() == ContaCategoria.TipoCategoria.saida && cc.isAtivo();
        }, (value) -> {

            ContaCategoria cc = new ContaCategoria();
            cc.setAtivo(true);
            cc.setNome("SERVIÇOES DE TERCEIROS");
            cc.setTipo(ContaCategoria.TipoCategoria.saida);
            new ContaCategoriaService().insert(cc);
            return cc.getId() + "";
        });

        Parametros.getInstance().validaParametro("ajuste_saida", (value) -> {
            MovimentacaoTipo cc = new MovimentacaoTipoService().findById(Integer.parseInt(value));
            return cc != null && cc.getTipo() == MovimentacaoTipo.TipoMovimentacao.saida && cc.isAtivo();
        }, (value) -> {
            MovimentacaoTipo mT = new MovimentacaoTipo();
            mT.setAtivo(true);
            mT.setDescricao("AJUSTE SAIDA");
            mT.setTipo(MovimentacaoTipo.TipoMovimentacao.saida);
            new MovimentacaoTipoService().insert(mT);
            return mT.getId() + "";
        });
        
        Parametros.getInstance().validaParametro("ajuste_entrada", (value) -> {
            MovimentacaoTipo cc = new MovimentacaoTipoService().findById(Integer.parseInt(value));
            return cc != null && cc.getTipo() == MovimentacaoTipo.TipoMovimentacao.entrada && cc.isAtivo();
        }, (value) -> {
            MovimentacaoTipo mT = new MovimentacaoTipo();
            mT.setAtivo(true);
            mT.setDescricao("AJUSTE ENTRADA");
            mT.setTipo(MovimentacaoTipo.TipoMovimentacao.entrada);
            new MovimentacaoTipoService().insert(mT);
            return mT.getId() + "";
        });
    }
}
