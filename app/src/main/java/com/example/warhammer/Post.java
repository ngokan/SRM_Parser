package com.example.warhammer;

public class Post {
    private String photo_id;
    private String description;
    private String name;
    private double price;
    private String normPhoto;
    private String photoThumb;

    public Post(String id, String text, String url_photo, String url_photoThumb) {
        this.photo_id = id;
        this.description = text;
        this.normPhoto = url_photo;
        this.photoThumb = url_photoThumb;
    }


    public void makeNameAndPrice(String description) {
        String name, price;
        this.name = "";
        this.price = 0;
        //this.name = name;
        //this.price = price;
    }
    public String getPhoto() {
        return normPhoto;
    }
    public void setPhoto(String albumId) {
        this.normPhoto = albumId;
    }

    public String getPhotoThumb() {
        return photoThumb;
    }
    public void setPhotoThumb(String photoThumb) {
        this.photoThumb = photoThumb;
    }

    public String getPhoto_id() {
        return photo_id;
    }
    public void setPhoto_id(String id) {
        this.photo_id = id;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String text) {
        this.description = text;
    }

    public void setName(String name){ this.name = name;}
    public String getName() {return this.name;}

    public void setPrice(double price) { this.price = price;}
    public double getPrice() {return this.price;}
}

