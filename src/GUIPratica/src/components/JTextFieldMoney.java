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
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Locale;
import java.util.StringTokenizer;
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
        changeSupport.firePropertyChange("value", valueWas, valor);
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

        changeSupport.firePropertyChange("value", valueWas, getValue());
        if (number != null) {
            super.setText(text);
        }
    }

    public class NumberFilter extends DocumentFilter {

        private NumberFormat nf = NumberFormat.getInstance();
        private char[] chars; // allowed characters
        private char groupingSeparator;
        private char decimalPoint;
        private DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        int maxSize;
        int fractionsDigits;

        // limit is the maximum number of characters allowed.
        public NumberFilter(int limit) {
            this(limit, 0);
            chars = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        }

        public NumberFilter(int limit, int fractionsDigits) {
            this.fractionsDigits = fractionsDigits;
            maxSize = limit + (limit / 3 - 1); // add number of group separators
            nf.setMaximumFractionDigits(fractionsDigits);
            groupingSeparator = dfs.getGroupingSeparator();
            decimalPoint = dfs.getDecimalSeparator();
            chars = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', decimalPoint};
        }

        public NumberFilter() {
            this(100);
        }

        // This method is called when characters are inserted into the document
        public void insertString(DocumentFilter.FilterBypass fb, int offset, String str,
                AttributeSet attr) throws BadLocationException {
            int len = fb.getDocument().getLength();
            replace(fb, 0, len, str, attr);
        }

        // This method is called when characters are removed from the document
        public void remove(FilterBypass fb, int offset, int length) throws
                BadLocationException {
            int len = fb.getDocument().getLength();
            String text = fb.getDocument().getText(0, len);
            String s = text.substring(0, offset) + text.substring(offset + length);
            //String str = addSeparator(getWithoutSeparator(s));

            double d = 0;
            try {
                d = Double.parseDouble(getWithoutSeparator(s));
            } catch (NumberFormatException nfe) {
                // can appear if we remove all digits
                fb.replace(0, len, "", null);
                return;
            }

            String str = nf.format(d);

            if (s.endsWith(String.valueOf(decimalPoint))) {
                str += decimalPoint;
            } else {
                str += toAdd(s);
            }

            fb.replace(0, len, str, null);
        }

        // This method is called when characters in the document are replace with other characters
        public void replace(DocumentFilter.FilterBypass fb, int offset, int length,
                String str, AttributeSet attrs) throws BadLocationException {
            if (!verify(str)) {
                return;
            }
            int len = fb.getDocument().getLength();
            int newLength = len - length + str.length();

            String s = fb.getDocument().getText(0, len);
            int i = s.indexOf(decimalPoint);
            boolean nDigits = false;

            if (i != -1) {
                // do not allow two decimal points
                if (str.equals(String.valueOf(decimalPoint))) {
                    return;
                }
                // we have two fraction digits
                if ((i + fractionsDigits + 1) == s.length()) {
                    nDigits = true;
                }
            }

            String text = s + str;
            double d = 0;
            if (!nDigits) {
                try {
                    d = Double.parseDouble(getWithoutSeparator(text));
                } catch (NumberFormatException nfe) {
                    // can appear if we enter multiple points!
                    return;
                }
            } else {
                try {
                    d = Double.parseDouble(getWithoutSeparator(s));
                } catch (NumberFormatException nfe) {
                    // can appear if we enter multiple points!
                    return;
                }
            }

            // keep the point to be shown
            boolean decimal = false;
            if (str.indexOf(decimalPoint) != -1) {
                decimal = true;
            }
            str = nf.format(d);
            if (decimal) {
                str += decimalPoint;
            }

            String add = toAdd(text);
            str += add;

            int index = str.indexOf(decimalPoint);
            boolean withFractionDigits = false;
            if (index != -1) {
                withFractionDigits = true;
            }

            // for fraction digits we allow another (fractionDigits+1) characters
            int totalSize = maxSize;
            if (withFractionDigits) {
                totalSize += (fractionsDigits + 1);
            }

            if (newLength <= totalSize) {
            // if we have fractionDigits digits after decimal point we do not allow to enter other digits
                // without this code, if we enter a digit after fractionDigits digits, the number will be rounded!
                if (withFractionDigits) {
                    if (nDigits && (index + fractionsDigits + 1) == str.length()) {
                        throw new BadLocationException("New characters exceeds max size of document", offset);
                    } else {
                    }
                }

                fb.replace(0, len, str, attrs);
            } else {
                throw new BadLocationException("New characters exceeds max size of document", offset);
            }
        }

        private boolean verify(String s) {
            char[] sc = s.toCharArray();
            for (int i = 0, size = sc.length; i < size; i++) {
                for (int j = 0; j < chars.length; j++) {
                    if (sc[i] == chars[j]) {
                        return true;
                    }
                }
            }
            return false;
        }

        private String addSeparator(String s) {
            int len = s.length();
            StringBuffer reverse = new StringBuffer(s).reverse();
            StringBuffer result = new StringBuffer();

            // after every 3 characters add a separator
            int j = 0;
            for (int i = 0; (i + 3) < len; j = i + 3, i = i + 3) {
                result.append(reverse.substring(i, i + 3));
                result.append(String.valueOf(groupingSeparator));
            }

            // add last characters
            if (j <= len) {
                result.append(reverse.substring(j));
            }

            return result.reverse().toString();
        }

    // after a format ending zeros and maybe the decimalPoint are removed
        // this method obtain these characters if any
        private String toAdd(String s) {

            int index = s.indexOf(decimalPoint);
            if (index == -1) {
                return "";
            }

            s = s.substring(index);

            if (s.length() > fractionsDigits + 1) {
                s = s.substring(0, fractionsDigits + 1);
            } else if (!s.endsWith("0")) {
                return "";
            }

            StringBuffer sb = new StringBuffer(s);
            StringBuffer reverse = sb.reverse();
            StringBuffer r = new StringBuffer();
            for (int i = 0, size = reverse.length(); i < size; i++) {
                char c = sb.charAt(i);
                if ((c == decimalPoint) || (c == '0')) {
                    r.append(c);
                } else {
                    break;
                }
            }

            String result = r.reverse().toString();

            int allLen = s.length();
            int len = result.length();
            if ((allLen > fractionsDigits + 1)) {
                result = result.substring(0, fractionsDigits + 1 - allLen + len);
            }

            return result;
        }

        public String getWithoutSeparator(String s) {
            StringBuffer sb = new StringBuffer();
            StringTokenizer st = new StringTokenizer(s, String.valueOf(groupingSeparator));
            while (st.hasMoreTokens()) {
                sb.append(st.nextToken());
            }
            return sb.toString();
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
//                sb.insert(offs, str);
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
