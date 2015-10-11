/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import components.ThrowingCommand;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.SwingWorker;

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

    public static void safeCode(ThrowingCommand command) {
        
        SwingWorker worker;
        worker = new CommandWorker(command);
        worker.execute();
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
}
