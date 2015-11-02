/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import javax.swing.*;
import javax.swing.text.Document;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

@SuppressWarnings("serial")
public class PlaceholderPasswordField extends JPasswordField {

    private String placeholder = "";

    public PlaceholderPasswordField() {
    }

    public PlaceholderPasswordField(
            final Document pDoc,
            final String pText,
            final int pColumns) {
        super(pDoc, pText, pColumns);
    }

    public PlaceholderPasswordField(final int pColumns) {
        super(pColumns);
    }

    public PlaceholderPasswordField(final String pText) {
        super(pText);
    }

    public PlaceholderPasswordField(final String pText, final int pColumns) {
        super(pText, pColumns);
    }

    public String getPlaceholder() {
        return placeholder;
    }

    @Override
    protected void paintComponent(final Graphics pG) {
        super.paintComponent(pG);

        if (placeholder == null || placeholder.length() == 0 || getText().length() > 0) {
            return;
        }

        final Graphics2D g = (Graphics2D) pG;
//        g.setRenderingHint(
//                RenderingHints.KEY_ANTIALIASING,
//                RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(getDisabledTextColor());
        g.drawString(placeholder, getInsets().left, (int) Math.ceil((getHeight() + getFont().getSize2D()) / 2));
    }

    public void setPlaceholder(final String s) {
        placeholder = s;
    }

}
