package com.example.demo.src.clothes.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.io.FileSystemResource;

import java.io.File;

@Getter
@Setter
@AllArgsConstructor
public class GetClothesListRes {
    private String uri;
    private int clothesIdx;
    private byte[] file;

    public GetClothesListRes(String uri, int clothesIdx) {
        this.uri = uri;
        this.clothesIdx = clothesIdx;
    }
}
