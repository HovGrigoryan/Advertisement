package model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
@Data
@NoArgsConstructor
public class Item implements Comparable<Item>, Serializable {
    private Long id;
    private String title;
    private String text;
    private double price;
    private User user;
    private Category category;
    private Date createddate;

    public Item(String title, String text, double price, User user, Category category, Date createddate) {
        this.title = title;
        this.text = text;
        this.price = price;
        this.user = user;
        this.category = category;
        this.createddate = createddate;
    }


    @Override
    public int compareTo(Item o) {
        return title.compareTo(o.getTitle());
    }
}
