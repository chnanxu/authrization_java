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
