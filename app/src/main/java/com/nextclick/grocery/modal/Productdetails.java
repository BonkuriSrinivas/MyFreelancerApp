package com.nextclick.grocery.modal;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "tbl_ProductCategories")
public class Productdetails implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "Category")
    private String Category;

    @ColumnInfo(name = "Name")
    private String Name;

    @ColumnInfo(name = "Discription")
    private String Discription;

    @ColumnInfo(name = "Quantity")
    private String Quantity;

    @ColumnInfo(name = "Price")
    private int Price;

    @ColumnInfo(name = "Discount")
    private String Discount;

    @ColumnInfo(name = "Cartvalue")
    private int Cartvalue;

    public Productdetails(String Category, String Name, String Discription, int Price, String Quantity, String Discount, int Cartvalue) {
        this.id = id;
        this.Category = Category;
        this.Name = Name;
        this.Discription = Discription;
        this.Price = Price;
        this.Quantity = Quantity;
        this.Discount = Discount;
        this.Cartvalue = Cartvalue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDiscription() {
        return Discription;
    }

    public void setDiscription(String discription) {
        Discription = discription;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public int getCartvalue() {
        return Cartvalue;
    }

    public void setCartvalue(int cartvalue) {
        Cartvalue = cartvalue;
    }

    public static Productdetails[] ProductsData() {
        return new Productdetails[]{
                new Productdetails("Groceries", "Daal", "Dal is a term used in the Indian subcontinent for dried.",120,"1kg","20%",0),
                new Productdetails("Groceries", "Soaps", "Soap is a salt of a fatty acid used in a variety of cleansing and lubricating products",49,"3","10%",0),
                new Productdetails("Groceries", "Sunflower OIL", "Sunflower oils that are not high oleic contain more omega-6, which may harm your health",320,"5ltrs","20%",0),
                new Productdetails("Fruits", "Apples", "An apple is an edible fruit produced by an apple tree.",130,"2kg","15%",0),
                new Productdetails("Fruits", "Grapes", "A grape is a fruit, botanically a berry, of the deciduous woody vines of the flowering plant genus Vitis",60,"2kg","33%",0),
                new Productdetails("Clothes", "Jeans", "Shop from the latest range of comfortable & trendy regular jeans",600,"2","40%",0),
        };
    }
}
