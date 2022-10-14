package com.example.gezginapp;

public class ListViewModelClass {
    int city_picture;
    String city_name;
    String city_description;

    public ListViewModelClass(int city_picture, String city_name, String city_description) {
        this.city_picture = city_picture;
        this.city_name = city_name;
        this.city_description = city_description;
    }

    public int getCity_picture() {
        return city_picture;
    }

    public void setCity_picture(int city_picture) {
        this.city_picture = city_picture;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getCity_description() {
        return city_description;
    }

    public void setCity_description(String city_description) {
        this.city_description = city_description;
    }
}
