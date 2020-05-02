package main;

import model.Item;

import java.util.Comparator;

public class ItemDateComparator implements Comparator<Item> {


    @Override
    public int compare(Item o1, Item o2) {
        return o1.getCreateddate().compareTo(o2.getCreateddate());
    }
}
