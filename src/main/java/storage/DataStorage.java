package storage;

import model.Category;
import model.Item;
import model.User;
import util.FileUtil;

import java.io.IOException;
import java.util.*;

public class DataStorage {
    private static long itemId = 1;

    private Map<String, User> userMap = new HashMap<>();
    private List<Item> items = new ArrayList<>();

    public void add(User user) throws IOException {
        userMap.put(user.getPhoneNumber(), user);
        FileUtil.serializeUserMap(userMap);
    }

    public void add(Item item) throws IOException {
        item.setId(itemId++);
        items.add(item);
        FileUtil.serializeItemList(items);
    }

    public User getUser(String phoneNumber) {
        return userMap.get(phoneNumber);
    }

    public Item getItemById(long id) {
        for (Item item : items) {
            if (item.getId() == id) {
                return item;
            }

        }
        return null;
    }

    public void printItems() {
        for (Item item : items) {
            System.out.println(item);
        }
    }

    public void printItemsOrderByTitle() {
        List<Item> orderedList = new ArrayList<>(items);
        Collections.sort(orderedList);
//        orderedList.sort(Item::compareTo);
        for (Item item : orderedList) {
            System.out.println(item);
        }
    }

    public void printItemsOrderByDate() {
        List<Item> orderedList = new ArrayList<>(items);
        orderedList.sort(new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                return o1.getCreateddate().compareTo(o2.getCreateddate());
            }
        });
//        orderedList.sort(new ItemDateComparator());
        for (Item item : orderedList) {
            System.out.println(item);
        }
    }

    public void printItemsByUsers(User user) {
        for (Item item : items) {
            if (item.getUser().equals(user)) {
                System.out.println(item);
            }
        }
    }

    public void printItemsByCategory(Category category) {
        for (Item item : items) {
            if (item.getCategory() == category) {
                System.out.println(item);
            }
        }
    }

    public void deleteItemsByUser(User user) throws IOException {
        Iterator<Item> iterator = items.iterator();
        while (iterator.hasNext()) {
            Item next = iterator.next();
            if (next.getUser().equals(user)) {
                iterator.remove();
//                items.removeIf(item -> item.getUser().equals(user));
            }
        }
        FileUtil.serializeUserMap(userMap);

    }

    public void deleteItemsById(Long id) throws IOException {
        items.remove(getItemById(id));
        FileUtil.serializeItemList(items);
    }

    public void initData() throws IOException, ClassNotFoundException {
        userMap = FileUtil.deserializeUserMap();
        items = FileUtil.deserializeItemList();
        if (items!=null && !items.isEmpty()){
            Item item = items.get(items.size() - 1);
            itemId = item.getId() + 1;
        }
    }
}
