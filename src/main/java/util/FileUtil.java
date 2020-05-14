package util;

import model.Item;
import model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class    FileUtil {
    private static final String USER_MAP_PATH = "src/main/resources/userMap.obj";
    private static final String ITEM_LIST_PATH = "src/main/resources/itemList.obj";

    public static void serializeUserMap(Map<String, User> userMap)  {
        File userMapFile = new File(USER_MAP_PATH);
        try {
            if (!userMapFile.exists()) {
                userMapFile.createNewFile();
            }
            try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(USER_MAP_PATH))) {
                objectOutputStream.writeObject(userMap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Map<String, User> deserializeUserMap() {
        Map<String, User> result = new HashMap<>();
        File userMapFile = new File(USER_MAP_PATH);
        if (userMapFile.exists()) {
            try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(USER_MAP_PATH))) {
                Object deserialization = objectInputStream.readObject();
                return (Map<String, User>) deserialization;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    public static void serializeItemList(List<Item> items) throws IOException {
        File itemListFile = new File(ITEM_LIST_PATH);
        try {
            if (!itemListFile.exists()) {
                itemListFile.createNewFile();
            }
            try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(ITEM_LIST_PATH))) {
                objectOutputStream.writeObject(items);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Item> deserializeItemList() throws IOException, ClassNotFoundException {
        List<Item> result = new ArrayList<>();
        File itemListFile = new File(ITEM_LIST_PATH);
        if (itemListFile.exists()) {
            try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(ITEM_LIST_PATH))) {
                Object deserialization = objectInputStream.readObject();
                return (List<Item>) deserialization;

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}
