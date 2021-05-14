package businessLayer;

import java.io.Serializable;
import java.util.List;

public class BaseProduct extends MenuItem {
    public BaseProduct(List<String> fields)
    {
        super(fields);
    }

    public BaseProduct(Object[] fields)
    {
        super(fields);
    }
}
