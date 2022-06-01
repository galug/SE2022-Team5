package com.example.demo.src.clothes.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
public class PostClothesReq {
    private int userIdx;
    private int season;
    private int bigCategory;
    private int smallCategory;
    private String color;
}
