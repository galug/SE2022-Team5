package com.example.demo.src.post.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetPostsRes {
    private int postIdx;
    private String name;
    private String title;
    private int postLikeCount;
    private String likeOrNot;
    private String uri;
    private byte[] file;

    public GetPostsRes(String name, String title, int postLikeCount, String likeOrNot, String uri,int postIdx) {
        this.name = name;
        this.title = title;
        this.postLikeCount = postLikeCount;
        this.likeOrNot = likeOrNot;
        this.uri = uri;
        this.postIdx = postIdx;
    }
}
