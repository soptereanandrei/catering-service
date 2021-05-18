package presentation;

import businessLayer.MenuItem;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.TypeVariable;
import java.util.*;
import java.util.List;
import java.util.function.*;
import java.util.stream.Collectors;

public class ProductsPanel<T> extends JPanel implements PropertyChangeListener {
    private final Class<T> productPanelType;

    private HashMap<MenuItem, ProductPanel> menuMap;

    private JComboBox<String> title;
    private JComboBox<Float> rating;
    private JComboBox<Integer> calories;
    private JComboBox<Integer> protein;
    private JComboBox<Integer> fat;
    private JComboBox<Integer> sodium;
    private JComboBox<Integer> price;
    private JButton findButton;
    private JButton unselectButton;

    private List<MenuItem> selectedItems = new ArrayList<>();//list of objects selected
    public List<MenuItem> getSelectedItems() {
        return selectedItems;
    }
    public void clearSelection()
    {
        selectedItems.clear();
        System.out.println("Clear all selected items.");
    }
    public int getQuantity(MenuItem item)
    {
        return ((ProductOrderPanel)menuMap.get(item)).getQuantity();
    }

    public ProductsPanel(HashSet<MenuItem> menu, Class<T> productPanelType)
    {
        super();

        this.productPanelType = productPanelType;
        createMenuMap(menu);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(createInputPanel());
        showNoFilterItems();

        findButton.addActionListener(e -> {
            filterItems(getInputFields());
        });

        unselectButton.addActionListener(e -> {
            selectedItems.forEach(item -> menuMap.get(item).forceUnselect());
            selectedItems.clear();
            System.out.println("Clear all selected items.");
        });
    }

    public void reinitializationMenu(HashSet<MenuItem> menu)
    {
        createMenuMap(menu);
        showNoFilterItems();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        MenuItem oldValue = (MenuItem)evt.getOldValue();
        MenuItem newValue = (MenuItem)evt.getNewValue();

        if (oldValue == null)//add new product
        {
            //menuMap.put(newValue, new ProductPanel(newValue, this));
            menuMap.put(newValue, createProductPanel(newValue));
        }
        else if (newValue == null)//remove product
        {
            menuMap.remove(oldValue);
        }
        else {//replace product
            menuMap.remove(oldValue);
            //menuMap.put(newValue, new ProductPanel(newValue, this));
            menuMap.put(newValue, createProductPanel(newValue));
        }
    }

    public Object[] getInputFields()
    {
        try {
            return new Object[]{
                    title.getSelectedItem() != null && !((String) title.getSelectedItem()).isBlank() ? title.getSelectedItem() : null,
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

    public void addSelectedItem(MenuItem item)
    {
        selectedItems.add(item);
        System.out.println("Added to selection obj : " + item);
    }

    public void removeSelectedItem(MenuItem item)
    {
        selectedItems.remove(item);
        System.out.println("Removed from selection obj : " + item);
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

    private void createMenuMap(HashSet<MenuItem> menu)
    {
        menuMap = new HashMap<>(menu.size());
        menu.forEach(menuItem -> {
            //ProductPanel productPanel = new ProductPanel(menuItem, this);
            ProductPanel productPanel = createProductPanel(menuItem);
            menuMap.put(menuItem, productPanel);
        });
    }

    public void showNoFilterItems()
    {
        if (getComponentCount() > 1)
            remove(1);
        add(getVisibleItemsPanel(menuMap.keySet().parallelStream().limit(100).collect(Collectors.toList())));
        updateUI();
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

            remove(1);
            add(getVisibleItemsPanel(menuMap.keySet().parallelStream().filter(predicate).collect(Collectors.toList())));
            updateUI();
        }
        else {
            showNoFilterItems();
        }
    }

    private JScrollPane getVisibleItemsPanel(List<MenuItem> visibleItems)
    {
        JPanel productsPanel = new JPanel();
        productsPanel.setLayout(new GridLayout(visibleItems.size(), 1, 0, 30));
        //productsPanel.setSize(new Dimension(1000, visibleItems.size() * (500 + 30)));

        visibleItems.parallelStream().forEach(item -> productsPanel.add(menuMap.get(item)));

        return new JScrollPane(productsPanel);
    }

    private ProductPanel createProductPanel(MenuItem item)
    {
        try {
            return (ProductPanel) productPanelType.getConstructors()[0].newInstance(item, this);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return null;
    }

}