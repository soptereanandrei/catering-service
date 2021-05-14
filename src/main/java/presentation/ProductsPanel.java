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
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ProductsPanel extends JPanel {

    private HashMap<MenuItem, Boolean> menu;//Key = MenuItem, Value = unselected/selected

    private JComboBox<String> title;
    private JComboBox<Float> rating;
    private JComboBox<Integer> calories;
    private JComboBox<Integer> protein;
    private JComboBox<Integer> fat;
    private JComboBox<Integer> sodium;
    private JComboBox<Integer> price;
    private JButton findButton;
    private JButton unselectButton;

    private List<MenuItem> visibleItems;//references to objects visible now in table
    private List<MenuItem> selectedItems = new ArrayList<>();//list of objects selected
    public List<MenuItem> getSelectedItems() {
        return selectedItems;
    }

    private JTable productsTable;
    private TableModelListener tableModelListener;

    public ProductsPanel(HashSet<MenuItem> menu, Dimension panelDim)
    {
        super();

        setPreferredSize(panelDim);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(createInputPanel());
        add(createTablePanel());

        this.menu = new HashMap<>();
        menu.forEach(item -> {
            this.menu.put(item, Boolean.FALSE);
        });

        showNoFilterItems();

        findButton.addActionListener(e -> {
            filterItems(getInputFields());
        });

        unselectButton.addActionListener(e -> {
            productsTable.getModel().removeTableModelListener(tableModelListener);
            selectedItems.forEach(item -> this.menu.replace(item, false));
            selectedItems.clear();
            updateVisibleItems();
            productsTable.getModel().addTableModelListener(tableModelListener);
        });
    }

    public void updateMenu(HashSet<MenuItem> newMenu)
    {
        menu = new HashMap<>();
        newMenu.stream().forEach(item -> menu.put(item, false));
        showNoFilterItems();
    }

    public Object[] getInputFields()
    {
        try {
            return new Object[]{
                    title.getSelectedItem(),
                    rating.getSelectedItem() != null && !((String) rating.getSelectedItem()).isBlank() ? Float.parseFloat((String) rating.getSelectedItem()) : null,
                    calories.getSelectedItem() != null && !((String) calories.getSelectedItem()).isBlank() ? Integer.parseInt((String) calories.getSelectedItem()) : null,
                    protein.getSelectedItem() != null && !((String) protein.getSelectedItem()).isBlank() ? Integer.parseInt((String) protein.getSelectedItem()) : null,
                    fat.getSelectedItem() != null && !((String) fat.getSelectedItem()).isBlank() ? Integer.parseInt((String) fat.getSelectedItem()) : null,
                    sodium.getSelectedItem() != null && !((String) sodium.getSelectedItem()).isBlank() ? Integer.parseInt((String) sodium.getSelectedItem()) : null,
                    price.getSelectedItem() != null && !((String) price.getSelectedItem()).isBlank() ? Integer.parseInt((String) price.getSelectedItem()) : null
            };
        }
        catch (Exception e)
        {
            new MessageBox("Invalid input : " + e.getMessage());
        }
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
        productsTable.getModel().addTableModelListener(tableModelListener = e -> {
            if (e.getType() == TableModelEvent.UPDATE) {
                int row = e.getFirstRow();
                if ((boolean) productsTable.getModel().getValueAt(row, 7)) {
                    MenuItem selectedItem = visibleItems.get(row);
                    menu.replace(selectedItem, true);
                    selectedItems.add(selectedItem);
                    System.out.println("Added to selection obj : " + selectedItem);
                } else {
                    MenuItem unselectedItem = visibleItems.get(row);
                    menu.replace(unselectedItem, false);
                    selectedItems.remove(unselectedItem);
                    System.out.println("Removed from selection obj : " + unselectedItem);
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

    private void showNoFilterItems()
    {
        visibleItems = new ArrayList<>(menu.keySet());
        updateVisibleItems();
    }

    private void filterItems(Object[] fields)
    {
        List<Predicate<MenuItem>> predicates = new ArrayList<>();
        if (fields[0] != null)
            predicates.add( (MenuItem m) -> m.getTitle().contains((String)fields[0]));
        if (fields[1] != null)
            predicates.add( (MenuItem m) -> m.getRating() == (Float)fields[1]);
        if (fields[2] != null)
            predicates.add( (MenuItem m) -> m.getCalories() == (Integer) fields[2]);
        if (fields[3] != null)
            predicates.add( (MenuItem m) -> m.getProteins() == (Integer) fields[3]);
        if (fields[4] != null)
            predicates.add( (MenuItem m) -> m.getFat() == (Integer) fields[4]);
        if (fields[5] != null)
            predicates.add( (MenuItem m) -> m.getSodium() == (Integer) fields[5]);
        if (fields[6] != null)
            predicates.add( (MenuItem m) -> m.getPrice() == (Integer) fields[6]);

        if (predicates.size() > 0) {
            Predicate<MenuItem> predicate = predicates.stream().reduce(p -> true, Predicate::and);

            visibleItems = menu.keySet().stream().filter(predicate).collect(Collectors.toList());
            updateVisibleItems();
        }
        else {
            showNoFilterItems();
        }
    }

    /**
     * Method update the the table with visible MenuItems
     */
    private void updateVisibleItems()
    {
        DefaultTableModel tableModel = (DefaultTableModel) productsTable.getModel();

        tableModel.setRowCount(0);

        visibleItems.forEach(menuItem ->
                tableModel.addRow(new Object[] {
                        menuItem.getTitle(),
                        menuItem.getRating(),
                        menuItem.getCalories(),
                        menuItem.getProteins(),
                        menuItem.getFat(),
                        menuItem.getSodium(),
                        menuItem.getPrice(),
                        menu.get(menuItem)//check if this object is selected O(1)
                })
        );

        productsTable.setModel(tableModel);
    }
}
