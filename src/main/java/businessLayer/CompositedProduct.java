package businessLayer;

import java.io.Serial;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CompositedProduct extends MenuItem {
    @Serial
    private static final long serialVersionUID = 6529985098203217609L;
    private final List<MenuItem> compositionItems;

    public CompositedProduct(String title, List<MenuItem> compositionItems)
    {
        super(title, compositionItems);
        this.compositionItems = new ArrayList<>(compositionItems);
    }

    @Override
    public List<Object[]> getRepresentation(HashMap<MenuItem, Boolean> menu) {
        List<Object[]> rows = new ArrayList<>(1 + compositionItems.size());

        rows.add(new Object[] { getTitle(), getRating(), getCalories(), getProteins(), getFat(), getSodium(), getPrice(), menu.get(this) });

        for (MenuItem compositionItem : compositionItems)
        {
            List<Object[]> compositionRows = compositionItem.getRepresentation(menu);
            compositionRows.forEach(row -> row[7] = null);
            rows.addAll(compositionRows);
        }

        return rows;
    }
    public List<Object[]> getRepresentation()
    {
        List<Object[]> rows = new ArrayList<>(1 + compositionItems.size());

        rows.add(new Object[] { getTitle(), getRating(), getCalories(), getProteins(), getFat(), getSodium(), getPrice() });

        for (MenuItem compositionItem : compositionItems)
        {
            List<Object[]> compositionRows = compositionItem.getRepresentation();
            rows.addAll(compositionRows);
        }

        return rows;
    }


    @Override
    public int getCompositeItemsCount() {
        int count = 1;
        for (MenuItem compositionItem : compositionItems)
            count += compositionItem.getCompositeItemsCount();
        return count;
    }

    @Override
    public MenuItem modifyItem(Object[] fields) throws Exception {
        if (fields[0] == null)
            throw new Exception("No valid title to be modified");
        if (fields[1] != null || fields[2] != null || fields[3] != null || fields[4] != null || fields[5] != null)
            throw new Exception("Only title may be modified for a composited item");

        return new CompositedProduct((String)fields[0], compositionItems);
    }

}
