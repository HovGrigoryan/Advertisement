package storage;

import main.ItemDateComparator;
import model.Category;
import model.Item;
import model.User;

import java.util.*;

public class DataStorage {
    private static long itemId = 1;

    private Map<String, User> userMap = new HashMap<>();
    private List<Item> items = new ArrayList<>();

    public void add(User user) {
        userMap.put(user.getPhoneNumber(), user);
    }

    public void add(Item item) {
        item.setId(itemId++);
        items.add(item);
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

    public void deleteItemsByUser(User user) {
        Iterator<Item> iterator = items.iterator();
        while (iterator.hasNext()) {
            Item next = iterator.next();
            if (next.getUser().equals(user)) {
                iterator.remove();
            }
        }
    }

    public void deleteItemsById(Long id) {
        items.remove(getItemById(id));

    }
}
