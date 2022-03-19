package com.mestaoui.rvproject.beans;

public class Actor {
    private int id;
    private String fullName;
    private String img;
    private float star;

    private static int comp = 0;

    public Actor(String fullName, String img, float star) {
        this.id = ++comp;
        this.fullName = fullName;
        this.img = img;
        this.star = star;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public float getStar() {
        return star;
    }

    public void setStar(float star) {
        this.star = star;
    }
}
