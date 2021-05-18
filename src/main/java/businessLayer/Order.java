package businessLayer;

import dataLayer.Serializer;
import presentation.ClientGUI;

import java.io.Serial;
import java.io.Serializable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Order implements Serializable {
    @Serial
    private static final long serialVersionUID = 6529981098367657610L;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    private final int orderID;
    private final String clientID;
    private final Date orderDate;
    private final int price;

    public Order(int orderID, String clientID, List<Pair<MenuItem, Integer>> choiceList)
    {
        this.orderID = orderID;
        this.clientID = clientID;
        orderDate = new Date();
        price = choiceList.stream().reduce(0, (partial, current) -> partial + current.key.getPrice() * current.value, Integer::sum);
    }

    public static SimpleDateFormat getDateFormat() {
        return dateFormat;
    }

    public int getOrderID() {
        return orderID;
    }

    public String getClientID() {
        return clientID;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientID, orderDate, price);
    }
}
