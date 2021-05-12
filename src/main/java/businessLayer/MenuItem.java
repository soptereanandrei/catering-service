package businessLayer;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public abstract class MenuItem implements Serializable {
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
