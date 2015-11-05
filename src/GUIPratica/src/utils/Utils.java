/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import components.JTextFieldMoney;
import components.ThrowingCommand;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JSpinner;
import javax.swing.SwingWorker;
import javax.swing.text.JTextComponent;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.BeanProperty;

/**
 *
 * @author Charles
 */
public class Utils {

    public static Date convertDate(java.sql.Date date) {
        try {

            String dataString = date.toString();
            DateFormat df = DateFormat.getDateInstance(DateFormat.DEFAULT);
            Date data = df.parse(dataString);
            return data;
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean isNumber(String text) {
        try {
            Integer.parseInt(text);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static SimpleDateFormat formatBra = new SimpleDateFormat("dd/MM/yyyy");

    public static String formataDate(Date dtData) {
        
        if(dtData == null)
            return "";
        return (formatBra.format(dtData));
        
    }

    public static void safeCode(ThrowingCommand command, boolean block) {

        if (block) {
            command.action();
        } else {
            SwingWorker worker;
            worker = new CommandWorker(command);
            worker.execute();
        }
    }

    public static void safeCode(ThrowingCommand command) {
        safeCode(command, false);
    }

    public static AutoBinding createBind(Object o, String campo, JComponent componenete) {
        return createBind(o, campo, componenete, true);
    }

    public static AutoBinding createBind(Object o, String campo, JComponent componenete, boolean autoBind) {
        AutoBinding a = null;
        if (componenete instanceof JSpinner || componenete instanceof JTextFieldMoney) {
            a = org.jdesktop.beansbinding.Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE, o, BeanProperty.create(campo), componenete, BeanProperty.create("value"));
        } else if (componenete instanceof JTextComponent) {
            a = org.jdesktop.beansbinding.Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE, o, BeanProperty.create(campo), componenete, BeanProperty.create("text"));
        } else if (componenete instanceof JComboBox) {
            a = org.jdesktop.beansbinding.Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE, o, BeanProperty.create(campo), componenete, BeanProperty.create("selectedItem"));
        }
        if (autoBind) {
            a.bind();

        }
        return a;

    }

    private static class CommandWorker extends SwingWorker {

        private final ThrowingCommand command;

        public CommandWorker(ThrowingCommand command) {
            this.command = command;
        }

        @Override
        protected Object doInBackground() throws Exception {

            Forms.iniciaProgress();
            command.action();
            Forms.paraProgress();
            return null;
        }
    }
    private static DecimalFormat format;

    static {

        format = new DecimalFormat("'R$'###,##0.00");
    }

    public static String formataDinheiro(double d) {

        return format.format(d);
    }

    public static double parseDinheiro(String d) {

        return Math.round(Double.parseDouble(d.substring(2).replace(".", "").replace(",", ".")) * 100d) / 100d;
    }
}
