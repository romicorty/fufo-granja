package com.example.romina.fufogranja.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by romina on 08/03/16.
 */
public class Fruit implements Serializable{
    private String id;
    private String title;
    private String description;
    private String imgSmall;
    private String imgBig;

    public String getImgBig() {
        return imgBig;
    }

    public void setImgBig(String imgBig) {
        this.imgBig = imgBig;
    }

    public Fruit(String title, String imgUrl){
        this.title = title;
        this.imgSmall = imgUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {

        return title;
    }

    public String getDescription() {
        return description+title;
    }

    public String getImgSmall() {
        return imgSmall;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImgSmall(String imgSmall) {
        this.imgSmall = imgSmall;
    }

}
