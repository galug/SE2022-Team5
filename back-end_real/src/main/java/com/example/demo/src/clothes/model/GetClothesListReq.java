package com.example.demo.src.clothes.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetClothesListReq {
    private int userIdx;
    private int bigCategory;
    private int smallCategory;
    private int season;
}
