package util;

import model.Item;

import java.io.*;
import java.util.List;

public class FileUtil1 {

    private static final String FILE_PATH = "D:\\io\\folder2\\file4.txt";

    public static void serializeItemList(List<Item> items) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(FILE_PATH));
        objectOutputStream.writeObject(items);
        objectOutputStream.close();

    }

    public static List<Item> deserializeItemList() throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(FILE_PATH));
        Object deserialization = objectInputStream.readObject();
        List<Item> it = (List<Item>) deserialization;
        return it;
    }
}
