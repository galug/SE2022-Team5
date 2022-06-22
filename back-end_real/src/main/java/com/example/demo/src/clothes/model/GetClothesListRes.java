package com.example.demo.src.clothes.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetClothesListRes {
    private int clothesIdx;
    private String uri;
    private int season;
    private int bigCategory;
    private int smallCategory;
    private String color;
    private byte[] file;
    public GetClothesListRes(int clothesIdx, String uri, int season, int bigCategory, int smallCategory, String color) {
        this.clothesIdx = clothesIdx;
        this.uri = uri;
        this.season = season;
        this.bigCategory = bigCategory;
        this.smallCategory = smallCategory;
        this.color = color;
    }
}
