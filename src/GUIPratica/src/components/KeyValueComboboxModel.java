package components;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

public class KeyValueComboboxModel extends AbstractListModel implements ComboBoxModel, Map<String, String> {

    private TreeMap<String, String> values = new TreeMap<String, String>();

    private Map.Entry<String, String> selectedItem = null;

    public KeyValueComboboxModel() {
    }

    @Override
    public Object getSelectedItem() {
        return selectedItem;
    }

    @Override
    public void setSelectedItem(Object anItem) {
        Item i = (Item) anItem;
        this.selectedItem = i.getEntry();
        fireContentsChanged(this, -1, -1);

    }

    @Override
    public Object getElementAt(int index) {
        List<Map.Entry<String, String>> list = new ArrayList<>(values.entrySet());
        return new Item(list.get(index));
    }

    @Override
    public int getSize() {
        return values.size();
    }

    @Override
    public void clear() {
        values.clear();
    }

    @Override
    public boolean containsKey(Object key) {
        return values.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return values.containsValue(value);
    }

    @Override
    public Set<java.util.Map.Entry<String, String>> entrySet() {
        return values.entrySet();
    }

    @Override
    public String get(Object key) {
        return values.get(key);
    }

    @Override
    public Set<String> keySet() {
        return values.keySet();
    }

    @Override
    public String put(String key, String value) {
        return values.put(key, value);
    }

    @Override
    public String remove(Object key) {
        return values.remove(key);
    }

    @Override
    public int size() {
        return values.size();
    }

    @Override
    public Collection<String> values() {
        return values.values();
    }

    @Override
    public boolean isEmpty() {
        return values.isEmpty();
    }

    @Override
    public void putAll(Map<? extends String, ? extends String> m) {
        values.putAll(m);
    }

    private static String entryToString(Map.Entry<String, String> entry) {
        String str = entry.getValue();
        return str;
    }

    public void setSelectedKey(String key) {
        selectedItem = values.ceilingEntry(key);
        setSelectedItem(key);
    }
//
//    public void setSelectedItem(String key, String value) {
//        values.put(key, value);
//        setSelectedKey(key);
//    }

    class Item {

        private Map.Entry<String, String> entry;

        public Item(Map.Entry<String, String> i) {
            this.entry = i;
        }

        public Entry<String, String> getEntry() {
            return entry;
        }

        @Override
        public String toString() {
            return entry.getValue();
        }

    }
}
