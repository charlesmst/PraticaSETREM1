/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeSupport;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Locale;
import javax.persistence.Transient;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.NumberFormatter;
import javax.swing.text.PlainDocument;

/**
 *
 * @author Charles
 */
public class JTextFieldMoney extends JTextField {

    String validos = "0123456789";

    private DecimalFormat format;
    private String decimal;

    
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    public JTextFieldMoney() {

        this.format = new DecimalFormat("'R$'###,##0.00");
//        this.format = format;

        decimal = Character.toString(format.getDecimalFormatSymbols().getDecimalSeparator());

        setColumns(format.toPattern().length());
        setHorizontalAlignment(JFormattedTextField.TRAILING);

        setText(format.format(0.0));

        AbstractDocument doc = (AbstractDocument) getDocument();
        doc.setDocumentFilter(new ABMFilter());
    }

    public void setValue(double valor) {
        double valueWas = getValue();
        
        AbstractDocument doc = (AbstractDocument) getDocument();
        doc.setDocumentFilter(null);
        setText(format.format(valor));
        
        doc.setDocumentFilter(new ABMFilter());
        changeSupport.firePropertyChange("value",valueWas,valor);
    }

    public double getValue() {
        try {
            String text = getText();
        return Double.parseDouble(text.substring(2).replace(".", "").replace(",", "."));
        } catch (Exception e) {
            return 0;
        }
        
    }

    public String getFormatted(double d) {
        return format.format(d);
    }

    @Override
    public void setText(String text) {
        double valueWas = getValue();
        Number number = format.parse(text, new ParsePosition(0));

        
        changeSupport.firePropertyChange("value",valueWas,getValue());
        if (number != null) {
            super.setText(text);
        }
    }

    public class ABMFilter extends DocumentFilter {

        public void insertString(FilterBypass fb, int offs, String str, AttributeSet a)
                throws BadLocationException {
            replace(fb, offs, 0, str, a);
        }

        public void replace(FilterBypass fb, int offs, int length, String str, AttributeSet a)
                throws BadLocationException {
            if ("0123456789".contains(str)) {
                Document doc = fb.getDocument();
                StringBuilder sb = new StringBuilder(doc.getText(0, doc.getLength()));

                int decimalOffset = sb.indexOf(decimal);

                if (decimalOffset != -1) {
                    sb.deleteCharAt(decimalOffset);
                    sb.insert(decimalOffset + 1, decimal);
                }

                sb.append(str);

                try {
                    String text = format.format(format.parse(sb.toString()));
                    super.replace(fb, 0, doc.getLength(), text, a);
                } catch (ParseException e) {
                }
            } else {
                Toolkit.getDefaultToolkit().beep();
            }
        }

        public void remove(DocumentFilter.FilterBypass fb, int offset, int length)
                throws BadLocationException {
            Document doc = fb.getDocument();
            StringBuilder sb = new StringBuilder(doc.getText(0, doc.getLength()));

            int decimalOffset = sb.indexOf(decimal);

            if (decimalOffset != -1) {
                sb.deleteCharAt(decimalOffset);
                sb.insert(decimalOffset - 1, decimal);
            }

            sb.deleteCharAt(sb.length() - 1);

            try {
                String text = format.format(format.parse(sb.toString()));
                super.replace(fb, 0, doc.getLength(), text, null);
            } catch (ParseException e) {
            }
        }
    }

}
