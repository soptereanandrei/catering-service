package dataLayer;

import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class Serializer {

    public void writeObject(Object object, String fileName)
    {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(object);

            objectOutputStream.close();
            fileOutputStream.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void writeObjects(List<Object> objects, String fileName)
    {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            for (Object o : objects)
                objectOutputStream.writeObject(o);
            objectOutputStream.writeObject(null);

            objectOutputStream.close();
            fileOutputStream.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void appendObject(Object o, String fileName)
    {
        List<Object> objects = loadObjects(fileName);

        if (objects == null)
            objects = new ArrayList<>();
        objects.add(o);
        objects.add(null);

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            for (Object obj : objects)
                objectOutputStream.writeObject(obj);

            objectOutputStream.close();
            fileOutputStream.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public Object loadObject(String fileName)
    {
        try {
            FileInputStream fileInputStream = new FileInputStream(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            return objectInputStream.readObject();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public List<Object> loadObjects(String fileName)
    {
        try {
            FileInputStream fileInputStream = new FileInputStream(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            List<Object> objects = new ArrayList<>();
            Object obj;
            while ((obj = objectInputStream.readObject()) != null)
            {
                objects.add(obj);
            }
            return objects;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
