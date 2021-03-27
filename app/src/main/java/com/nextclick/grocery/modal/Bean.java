package com.nextclick.grocery.modal;

public class Bean {
    private int image1;
    private String discription1;
    private String date1;

    public Bean(int image, String discription, String date) {
        this.image1 = image;
        this.discription1 = discription;
        this.date1 = date;
    }

    public int getImage1() {
        return image1;
    }

    public void setImage1(int image1) {
        this.image1 = image1;
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
