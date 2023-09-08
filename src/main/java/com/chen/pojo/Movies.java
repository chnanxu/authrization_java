package com.chen.pojo;


import lombok.Data;

@Data
public class Movies {
    private int cid;
    private String name;
    private String typeId;
    private String typename;

    private String text;
    private String img;
    private float rate;
    private String director;
    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Object getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    @Override
    public String toString() {
        return "Movies{" +
                "cid=" + cid +
                ", name='" + name + '\'' +
                ", typeId='" + typeId + '\'' +
                ", typename='" + typename + '\'' +
                ", text='" + text + '\'' +
                ", img='" + img + '\'' +
                ", rate=" + rate +
                ", director='" + director + '\'' +
                '}';
    }
}
