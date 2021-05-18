package businessLayer;

import java.awt.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;

public abstract class MenuItem implements Serializable {
    @Serial
    private static final long serialVersionUID = 6529685098263257609L;
    private final String title;
    private final float rating;
    private final int calories;
    private final int proteins;
    private final int fat;
    private final int sodium;
    private final int price;

    public String getTitle() {
        return title;
    }

    public float getRating() {
        return rating;
    }

    public int getCalories() {
        return calories;
    }

    public int getProteins() {
        return proteins;
    }

    public int getFat() {
        return fat;
    }

    public int getSodium() {
        return sodium;
    }

    public int getPrice() {
        return price;
    }

    protected MenuItem(List<String> fields)
    {
        title = fields.get(0);
        rating = Float.parseFloat(fields.get(1));
        calories = Integer.parseInt(fields.get(2));
        proteins = Integer.parseInt(fields.get(3));
        fat = Integer.parseInt(fields.get(4));
        sodium = Integer.parseInt(fields.get(5));
        price = Integer.parseInt(fields.get(6));
    }

    protected MenuItem(Object[] fields)
    {
        title = (String)fields[0];
        rating = (Float)fields[1];
        calories = (int)fields[2];
        proteins = (int)fields[3];
        fat = (int)fields[4];
        sodium = (int)fields[5];
        price = (int)fields[6];
    }

    protected MenuItem(String title, List<MenuItem> compositionItems)
    {
        BiFunction<Object[], MenuItem, Object[]> accumulator = (Object[] partial, MenuItem item) -> {
            partial[1] = (Float)partial[1] + item.getRating();
            partial[2] = (int)partial[2] + item.getCalories();
            partial[3] = (int)partial[3] + item.getProteins();
            partial[4] = (int)partial[4] + item.getFat();
            partial[5] = (int)partial[5] + item.getSodium();
            partial[6] = (int)partial[6] + item.getPrice();

            return partial;
        };
        BinaryOperator<Object[]> combiner = (Object[] item1, Object[] item2) -> {
            item1[1] = (Float)item1[1] + (Float)item2[1];
            item1[2] = (int)item1[2] + (int)item2[2];
            item1[3] = (int)item1[3] + (int)item2[3];
            item1[4] = (int)item1[4] + (int)item2[4];
            item1[5] = (int)item1[5] + (int)item2[5];
            item1[6] = (int)item1[6] + (int)item2[6];

            return item1;
        };

        Object[] fields = compositionItems.stream().reduce(new Object[] {title, 0f, 0, 0, 0, 0, 0}, accumulator, combiner);

        this.title = (String)fields[0];
        this.rating = (Float)fields[1] / compositionItems.size();
        this.calories = (int)fields[2];
        this.proteins = (int)fields[3];
        this.fat = (int)fields[4];
        this.sodium = (int)fields[5];
        this.price = (int)fields[6];

        System.out.println(toString());
    }

    /**
     * Get representation as table row of the this MenuItem
     * @return An array of rows, every row is a array of objects
     */
    //public abstract List<Object[]> getRepresentation(HashMap<MenuItem, Boolean> menu);
    public abstract List<Object[]> getRepresentation();

    /**
     * Get number of items of which is composited
     * @return items count
     */
    public abstract int getCompositeItemsCount();

    /**
     * Get a modified MenuItem
     * @param fields - array of fields that want to be modified
     * @return a new instance of a MenuItem modified
     */
    public abstract MenuItem modifyItem(Object[] fields) throws Exception;

    /**
     * Mark that this MenuItem is selected
     * @param menu - reference to HashMap where the selected items are marked
     */
    public void select(HashMap<MenuItem, Boolean> menu)
    {
        menu.replace(this, true);
    }

    /**
     * Mark that this MenuItem is unselected
     * @param menu - reference to HashMap where the selected items are marked
     */
    public void unselect(HashMap<MenuItem, Boolean> menu)
    {
        menu.replace(this, false);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                title,
                rating,
                calories,
                proteins,
                fat,
                sodium,
                price
        );
    }

    @Override
    public String toString()
    {
        return title + " " + rating + " " + calories + " " + proteins + " " + fat + " " + sodium + " " + price;
    }
}
