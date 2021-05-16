package businessLayer;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BaseProduct extends MenuItem {
    @Serial
    private static final long serialVersionUID = 6520085098263257609L;

    public BaseProduct(List<String> fields)
    {
        super(fields);
    }

    public BaseProduct(Object[] fields)
    {
        super(fields);
    }

    @Override
    public List<Object[]> getRepresentation(HashMap<MenuItem, Boolean> menu) {
        List<Object[]> row = new ArrayList<>(1);

        row.add(new Object[] {
                getTitle(),
                getRating(),
                getCalories(),
                getProteins(),
                getFat(),
                getSodium(),
                getPrice(),
                menu.get(this)
        });

        return row;
    }
    public List<Object[]> getRepresentation()
    {
        List<Object[]> row = new ArrayList<>(1);

        row.add(new Object[] {
                getTitle(),
                getRating(),
                getCalories(),
                getProteins(),
                getFat(),
                getSodium(),
                getPrice(),
        });

        return row;
    }

    @Override
    public int getCompositeItemsCount() {
        return 1;
    }

    @Override
    public MenuItem modifyItem(Object[] fields) throws Exception {
        if (fields[0] == null && fields[1] == null && fields[2] == null && fields[3] == null && fields[4] == null && fields[5] == null && fields[6] == null)
            throw new Exception("No one field is supposed to be modified");
        return new BaseProduct( new Object[] {
                fields[0] != null ? fields[0] : getTitle(),
                fields[1] != null ? fields[1] : getRating(),
                fields[2] != null ? fields[2] : getCalories(),
                fields[3] != null ? fields[3] : getProteins(),
                fields[4] != null ? fields[4] : getFat(),
                fields[5] != null ? fields[5] : getSodium(),
                fields[6] != null ? fields[6] : getPrice()
        });
    }

}
