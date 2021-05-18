package dataLayer;

import businessLayer.MenuItem;
import businessLayer.Order;
import businessLayer.Pair;
import presentation.ClientGUI;

import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.List;

public class BillCreator {

    private static final SimpleDateFormat dateFormat = Order.getDateFormat();

    public static String createBill(Order order, List<Pair<MenuItem, Integer>> choiceList)
    {
        try {
            String time = dateFormat.format(order.getOrderDate());

            String orderIDString = Integer.toUnsignedString(order.getOrderID());

            StringBuilder bill = new StringBuilder();
            bill.append("Bill ID : ").append(orderIDString).append("\n");
            bill.append("Bill creation time : ").append(time).append("\n");
            bill.append("Bill placed by : ").append(order.getClientID()).append("\n");
            bill.append("Price : ").append(order.getPrice()).append("\n");
            bill.append("Order composited by:\n");
            int i = 1;
            for (Pair<MenuItem, Integer> choice : choiceList) {
                bill.append("Item ").append(i).append(" Quantity ").append(choice.value).append("\n");
                List<Object[]> rows = choice.key.getRepresentation();
                boolean header = true;
                for (Object[] row : rows) {
                    bill.append("Title: " + row[0] + " Rating: " + row[1] + " Calories: " + row[2] + " Proteins: " + row[3] + " Fat: " + row[4] + " Sodium: " + row[5] + " Price: " + row[6] + "\n");
                    if (header && rows.size() > 1)
                    {
                        bill.append("Composited:\n");
                        header = false;
                    }
                }
                i++;
            }

            Writer.write(bill.toString(), "Bill (" + orderIDString + ").txt");
            return bill.toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

}
