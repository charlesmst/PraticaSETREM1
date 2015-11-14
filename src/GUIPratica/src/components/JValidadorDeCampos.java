/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import services.Service;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.text.JTextComponent;
import utils.AlertaTipos;

/**
 *
 * @author Charles
 */
public class JValidadorDeCampos {

    private final Map<JComponent, List<ValidacaoCampos>> controls;

    public JValidadorDeCampos() {
        controls = new HashMap<>();
    }

    public void validarObrigatorio(JComponent control) {
        addValidar(control, ValidacoesTipos.obrigatorio, null);

    }

    private void addListener(final JComponent contro) {

        if (contro instanceof JTextComponent) {

            ((JTextComponent) contro).addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    validaControl(contro);

                }
            });
            if (contro instanceof F2) {
                ((F2) contro).setValueSelectedListener((id, text) -> {
                    validaControl(contro);
                });

            }
        } else if (contro instanceof JTable) {
            ((JTable) contro).getModel().addTableModelListener(new TableModelListener() {

                public void tableChanged(TableModelEvent e) {
                    validaControl(contro);

                }
            });
        }else if (contro instanceof JComboBox){
            ((JComboBox) contro).addActionListener((e)->{
                validaControl(contro);
            });
        }
    }

    private void addValidar(JComponent contro, ValidacoesTipos tipo, Object[] extras) {
        if (!controls.containsKey(contro)) {
            controls.put(contro, new ArrayList<ValidacaoCampos>());
            addListener(contro);
        }
        ValidacaoCampos v = new ValidacaoCampos(contro, tipo);
        v.adicionais(extras);
        controls.get(contro).add(v);
//        validaControl(contro);
    }

    public void validar(JComponent control, ValidacoesTipos tipo) {
        addValidar(control, tipo, null);
    }

    public void validarDeBanco(JComponent control, Service repositorio) {
        addValidar(control, ValidacoesTipos.chaveBanco, new Object[]{repositorio});
    }

    public void validarCustom(JComponent control, Function<String, Boolean> function, String mensagem) {
        addValidar(control, ValidacoesTipos.custom, new Object[]{function, mensagem});
    }

    private final boolean validaControl(JComponent control) {
        boolean valido = true;
        List<ValidacaoCampos> list = controls.get(control);
        int indiceInvalido = -1;
        for (int i = 0; i < list.size(); i++) {
            ValidacaoCampos validacaoCampos = list.get(i);
            if (!validacaoCampos.valida()) {
                indiceInvalido = i;
                valido = false;
            }
        }
        for (ValidacaoCampos validacaoCampos : list) {
            validacaoCampos.escondeIcone();
        }
        if (indiceInvalido != -1) {
            list.get(indiceInvalido).mudaEstadoCampo();
        } else {

            list.get(0).mudaEstadoCampo();
        }
        return valido;
    }

    public boolean isValido() {
        return isValido(true);
    }

    public void testComponents() {
        for (Map.Entry<JComponent, List<ValidacaoCampos>> entry : controls.entrySet()) {
            JComponent component = entry.getKey();
            validaControl(component);

        }
    }

    public boolean isValido(boolean beep) {
        boolean valido = true;

        for (Map.Entry<JComponent, List<ValidacaoCampos>> entry : controls.entrySet()) {
            JComponent component = entry.getKey();
            if (!validaControl(component)) {
                valido = false;
            }

        }

        if (!valido) {
            utils.Forms.mensagem(utils.Mensagens.verifiqueCampos, AlertaTipos.erro, beep);
        }
        return valido;
    }
}

class ValidacaoCampos {

    private JComponent control;

    public JComponent getControl() {
        return control;
    }
    private ValidacoesTipos validacao;
    private Object[] adicionais;

    private String mensagem;
    JLabel label;
    private Container lastAcessible;

    public ValidacaoCampos(JComponent control, ValidacoesTipos validacao) {
        this.control = control;
        this.validacao = validacao;
        mensagem = "Campo inválido";
        label = new JLabel(mensagem);

        Container parent = control;

        Container lastAcessible = control;
        while (parent != null && !(parent instanceof JPanel || parent instanceof JFrame || parent instanceof JDialog)) {
            lastAcessible = parent;
            parent = parent.getParent();
        }
        if (parent != null) {
            parent.add(label);
        }
        this.lastAcessible = lastAcessible;
//        else if((parent instanceof JScrollPane)){
//            ((JComponent) control).getParent().getParent().add(label);
//        }

        label.setVisible(false);
    }

    public boolean validaCustom() {
        if (adicionais == null || adicionais.length == 0 || !(adicionais[0] instanceof Function) || !(adicionais[1] instanceof String)) {
            return false;
        }
        String s = getValorControl();
//        //Se não tem nada é valido, pois deve ser verificado se é obrigatorio
//        if (s.equals("")) {
//            return true;
//        }

        Function<String, Boolean> function = (Function<String, Boolean>) adicionais[0];

        if (!function.apply(s)) {
            mensagem = (String) adicionais[1];
            return false;
        } else {
            return true;
        }
    }

    private void posicionaLabel() {

        int x = lastAcessible.getX() + lastAcessible.getWidth() + 3;
        int y = ((int) (lastAcessible.getHeight() + 16) / 2) + lastAcessible.getY() - 16;
//        label.setLocation(x, control.getY());
//        label.setLocation(0,0);
        label.setLabelFor(control);
        label.setBounds(x, y, 16, 16);
//        ((JTextArea) control).setJComponentZOrder(label, 1);
//            label.setLocation(Integer.parseInt(JOptionPane.showInputDialog("x")), Integer.parseInt(JOptionPane.showInputDialog("y")));
    }
    boolean valido;

    public boolean valida() {
        valido = validaInterno();
        return valido;
    }

    private boolean validaInterno() {
        switch (validacao) {
            case numero:
                return validaNumero();
            case obrigatorio:
                return validaObrigatorio();
            case chaveBanco:
                return validaChaveBanco();
            case custom:
                return validaCustom();
        }
        return false;
    }

    public void adicionais(Object[] adicionais) {
        this.adicionais = adicionais;
    }

    private boolean validaObrigatorio() {
        String valor = getValorControl();
        if (valor != null && !valor.equals("")) {
            return true;
        } else {
            mensagem = "Campo obrigatório";
            return false;
        }
    }

    private boolean validaNumero() {
        try {
            int v = Integer.parseInt(getValorControl());
            return true;
        } catch (Exception e) {
            mensagem = "Valor deve ser numérico";
            return false;
        }
    }

    public void escondeIcone() {
        label.setVisible(false);
    }

    public void mudaEstadoCampo() {

        if (valido) {
            label.setIcon(new ImageIcon(getClass().getClassLoader().getResource("resources/success.png")));
            label.setToolTipText("");

        } else {
            label.setIcon(new ImageIcon(getClass().getClassLoader().getResource("resources/erro.png")));
            label.setToolTipText(mensagem);

        }
        label.setVisible(true);
        posicionaLabel();

    }

    private String getValorControl() {
        if (control instanceof JDateField) {
            return ((JDateField) control).getDate() + "";
        } else if (control instanceof JTextArea) {
            return ((JTextArea) control).getText();
        } else if (control instanceof JTextField) {
            return ((JTextField) control).getText();
        } else if (control instanceof JComboBox) {
            return ((JComboBox) control).getSelectedItem() + "";
        } else if (control instanceof JSpinner) {
            return ((JSpinner) control).getValue() + "";
        } else {
//            utils.Forms.mensagem("Campo " + control.getName() + " não é reconhecido na validação", AlertaTipos.erro);
            return "";
        }
    }

    private boolean validaChaveBanco() {
        if (adicionais == null || adicionais.length == 0 || !(adicionais[0] instanceof Service)) {
            return false;
        }
        String s = getValorControl();
        //Se não tem nada é valido, pois deve ser verificado se é obrigatorio
        if (s.equals("")) {
            return true;
        }
        if (!validaNumero()) {
            return false;
        }

        Service r = (Service) adicionais[0];

        if (r.findById(Integer.parseInt(s)) == null) {
            mensagem = "Valor não encontrado no banco de dados";
            return false;
        } else {
            return true;
        }
    }
}
