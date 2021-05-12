package presentation;

import businessLayer.MenuItem;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.*;
import java.util.List;

public class ProductsPanel extends JPanel {

    private HashSet<MenuItem> menu;
    private List<ReferenceToMenuItem> selection = new ArrayList<>();
    private List<ReferenceToMenuItem> references;

    private JComboBox<String> title;
    private JComboBox<Float> rating;
    private JComboBox<Float> calories;
    private JComboBox<Float> protein;
    private JComboBox<Float> fat;
    private JComboBox<Float> sodium;
    private JComboBox<Float> price;
    private JButton findButton;
    private JButton unselectButton;
    private JTable productsTable;

    public ProductsPanel(HashSet<MenuItem> menu, Dimension panelDim)
    {
        super();

        setPreferredSize(panelDim);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(createInputPanel());
        add(createTablePanel());

        this.menu = menu;
        updateMenu();

        unselectButton.addActionListener(e -> {
            selection.forEach(reference -> productsTable.getModel().setValueAt(false, reference.row, 7));
            //selection.clear();
        });
    }

    public void updateMenu()
    {
        DefaultTableModel tableModel = (DefaultTableModel) productsTable.getModel();

        tableModel.setRowCount(0);
        references = new ArrayList<>();

        menu.forEach(menuItem ->
                {
                    tableModel.addRow(new Object[] {
                            menuItem.getTitle(),
                            menuItem.getRating(),
                            menuItem.getCalories(),
                            menuItem.getProteins(),
                            menuItem.getFat(),
                            menuItem.getSodium(),
                            menuItem.getPrice(),
                            false
                            });
                    references.add(new ReferenceToMenuItem(menuItem, references.size()));
                });

        productsTable.setModel(tableModel);
    }

    public List<MenuItem> getSelection()
    {
        return null;
    }

    private JPanel createInputPanel()
    {
        JPanel inputPanel = new JPanel();
        Dimension panelDim = new Dimension(1000, 50);
        inputPanel.setMaximumSize(panelDim);
        inputPanel.setLayout(new GridLayout(2, 9));

        String[] labelName = new String[] { "Title", "Rating", "Calories", "Protein", "Fat", "Sodium", "Price", "", "" };
        for (String s : labelName)
            inputPanel.add(new JLabel(s));

        inputPanel.add(title = GUIUtils.createJComboBox(null, true));
        inputPanel.add(rating = GUIUtils.createJComboBox(null, true));
        inputPanel.add(calories = GUIUtils.createJComboBox(null, true));
        inputPanel.add(protein = GUIUtils.createJComboBox(null, true));
        inputPanel.add(fat = GUIUtils.createJComboBox(null, true));
        inputPanel.add(sodium = GUIUtils.createJComboBox(null, true));
        inputPanel.add(price = GUIUtils.createJComboBox(null, true));
        inputPanel.add(findButton = new JButton("Find"));
        inputPanel.add(unselectButton = new JButton("Unselect all"));

        return inputPanel;
    }

    private JPanel createTablePanel()
    {
        JPanel tablePanel = new JPanel();
        Dimension tablePanelDim = new Dimension(1000, 300);
        tablePanel.setPreferredSize(tablePanelDim);

        DefaultTableModel tableModel = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column)
            {
                return column == 7;
            }
        };
        String[] headerNames = new String[] { "Title", "Rating", "Calories", "Protein", "Fat", "Sodium", "Price", "Selected" };
        for (String s : headerNames)
            tableModel.addColumn(s);

        productsTable = new JTable();
        productsTable.setModel(tableModel);
        productsTable.getModel().addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE) {
                int row = e.getFirstRow();
                if ((boolean) productsTable.getModel().getValueAt(row, 7)) {
                    selection.add(references.get(row));
                    System.out.println("Added to selection obj : " + references.get(row).menuItem);
                } else {
                    selection.remove(references.get(row));
                    System.out.println("Removed from selection obj : " + references.get(row).menuItem);
                }
            }
        });

        TableColumn selectedColumn = productsTable.getColumnModel().getColumn(7);
        selectedColumn.setCellEditor(new DefaultCellEditor(new JCheckBox()));

        JScrollPane scrollPane = new JScrollPane(productsTable);
        scrollPane.setPreferredSize(tablePanelDim);
        tablePanel.add(scrollPane);

        return tablePanel;
    }

    private class ReferenceToMenuItem {
        public MenuItem menuItem;
        public int row;

        public ReferenceToMenuItem(MenuItem menuItem, int row)
        {
            this.menuItem = menuItem;
            this.row = row;
        }
    }
}
