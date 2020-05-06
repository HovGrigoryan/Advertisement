package util;

import model.Item;
import model.User;

import java.io.*;
import java.util.List;
import java.util.Map;

public class FileUtil {
    private static final String FILE_PATH = "D:\\io\\folder2\\file3.txt";
    private static final String FILE_PATH1 = "D:\\io\\folder2\\file4.txt";

    public static void serializeUserMap(Map<String, User> userMap) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(FILE_PATH));
        objectOutputStream.writeObject(userMap);
        objectOutputStream.close();
    }

    public static Map<String, User> deserializeUserMap() throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(FILE_PATH));
        Object deserialization = objectInputStream.readObject();
        Map<String, User> us = (Map<String, User>) deserialization;
        return us;
    }

    public static void serializeItemList(List<Item> items) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(FILE_PATH1));
        objectOutputStream.writeObject(items);
        objectOutputStream.close();
    }

    public static List<Item> deserializeItemList() throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(FILE_PATH1));
        Object deserialization = objectInputStream.readObject();
        List<Item> it = (List<Item>) deserialization;
        return it;
    }
}
