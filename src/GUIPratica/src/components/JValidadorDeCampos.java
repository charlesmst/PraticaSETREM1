/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import services.Service;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;
import utils.AlertaTipos;

/**
 *
 * @author Charles
 */
public class JValidadorDeCampos {

    private final Map<Component, List<ValidacaoCampos>> controls;

    public JValidadorDeCampos() {
        controls = new HashMap<>();
    }

    public void validarObrigatorio(Component control) {
        addValidar(control, ValidacoesTipos.obrigatorio, null);

    }

    private void addListener(final Component contro) {

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
        }
    }

    private void addValidar(Component contro, ValidacoesTipos tipo, Object[] extras) {
        if (!controls.containsKey(contro)) {
            controls.put(contro, new ArrayList<ValidacaoCampos>());
            addListener(contro);
        }
        ValidacaoCampos v = new ValidacaoCampos(contro, tipo);
        v.adicionais(extras);
        controls.get(contro).add(v);
//        validaControl(contro);
    }

    public void validar(Component control, ValidacoesTipos tipo) {
        addValidar(control, tipo, null);
    }

    public void validarDeBanco(Component control, Service repositorio) {
        addValidar(control, ValidacoesTipos.chaveBanco, new Object[]{repositorio});
    }

    private final boolean validaControl(Component control) {
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
        for (Map.Entry<Component, List<ValidacaoCampos>> entry : controls.entrySet()) {
            Component component = entry.getKey();
            validaControl(component);

        }
    }

    public boolean isValido(boolean beep) {
        boolean valido = true;

        for (Map.Entry<Component, List<ValidacaoCampos>> entry : controls.entrySet()) {
            Component component = entry.getKey();
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

    private Component control;

    public Component getControl() {
        return control;
    }
    private ValidacoesTipos validacao;
    private Object[] adicionais;

    private String mensagem;
    JLabel label;

    public ValidacaoCampos(Component control, ValidacoesTipos validacao) {
        this.control = control;
        this.validacao = validacao;
        mensagem = "Campo inválido";
        label = new JLabel(mensagem);
        Container parent = control.getParent();
        parent.add(label);
        label.setVisible(false);
    }

    private void posicionaLabel() {

        int x = control.getX() + control.getWidth() + 3;
        int y = ((int) (control.getHeight() + 16) / 2) + control.getY() - 16;
//        label.setLocation(x, control.getY());
//        label.setLocation(0,0);
        label.setLabelFor(control);
        label.setBounds(x, y, 16, 16);
//        ((JTextArea) control).setComponentZOrder(label, 1);
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
        if (control instanceof JTextArea) {
            return ((JTextArea) control).getText();
        } else if (control instanceof JTextField) {
            return ((JTextField) control).getText();
        } else if (control instanceof JComboBox) {
            return ((JComboBox) control).getSelectedItem().toString();
        } else {
            utils.Forms.mensagem("Campo " + control.getName() + " não é reconhecido na validação", AlertaTipos.erro);
            return null;
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
