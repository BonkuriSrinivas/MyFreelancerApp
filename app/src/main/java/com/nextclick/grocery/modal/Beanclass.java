package com.nextclick.grocery.modal;

public class Beanclass {
    private int image1;
    private String discription1;
    private String date1;
    private int price1;
    private String discount;

    public int getPrice1() {
        return price1;
    }

    public void setPrice1(int price1) {
        this.price1 = price1;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public Beanclass(String discription, String date,int price,String discount) {
        this.discription1 = discription;
        this.date1 = date;
        this.price1 = price;
        this.discount = discount;
    }

    public String getDiscription1() {
        return discription1;
    }

    public void setDiscription1(String discription1) {
        this.discription1 = discription1;
    }

    public String getDate1() {
        return date1;
    }

    public void setDate1(String date1) {
        this.date1 = date1;
    }
}
