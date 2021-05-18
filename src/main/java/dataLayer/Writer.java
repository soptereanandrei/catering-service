package dataLayer;

import java.io.FileWriter;

public class Writer {

    public static void write(String text, String fileName)
    {
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write(text);

            fileWriter.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
