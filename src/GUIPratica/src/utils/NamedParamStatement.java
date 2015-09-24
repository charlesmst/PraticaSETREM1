/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;

/**
 *
 * @author Charles
 */
public class NamedParamStatement {

    public NamedParamStatement(Connection conn, String sql) throws SQLException {
        int pos;
        Pattern p = Pattern.compile(":(?<field>[\\d\\w]+)");
        Matcher m = p.matcher(sql);

        while (m.find()) {
            fields.add(m.group("field"));
        }

        sql = m.replaceAll("?");
//        while((pos = sql.indexOf(":")) != -1) {
//            int end = sql.substring(pos).indexOf(" ");
//            if (end == -1)
//                end = sql.length();
//            else
//                end += pos;
//            fields.add(sql.substring(pos+1,end));
//            sql = sql.substring(0, pos) + "?" + sql.substring(end);
//        }       
        prepStmt = conn.prepareStatement(sql);
    }

    public PreparedStatement getPreparedStatement() {
        return prepStmt;
    }

    public ResultSet executeQuery() throws SQLException {
        return prepStmt.executeQuery();
    }

    public boolean execute() throws SQLException {
        return prepStmt.execute();
    }

    public void close() throws SQLException {
        prepStmt.close();
    }

//    public void setInt(String name, int value) throws SQLException {        
//        prepStmt.setInt(getIndex(name), value);
//    }
    public void setValue(String name, Object value) throws SQLException {
        int i = getIndex(name);
        if (i != -1) {
            setObject(i, value);
        }
    }

    private void setObject(int i, Object value) throws SQLException {
        if (value instanceof Date) {
            Date d = (Date) value;
            prepStmt.setDate(i, new java.sql.Date(d.getYear(), d.getMonth(), d.getDate()));
        } else {
            prepStmt.setObject(i, value);
        }

    }

    private int getIndex(String name) {
        if (fields.indexOf(name) != -1) {
            return fields.indexOf(name) + 1;
        } else {
            return -1;
        }
    }
    private PreparedStatement prepStmt;
    private List<String> fields = new ArrayList<String>();
}
