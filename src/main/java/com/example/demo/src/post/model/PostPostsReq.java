package com.example.demo.src.post.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PostPostsReq {
    private int userIdx; //jwt 또는 pathVariable 통해서 받을 수 있다 -> 어떤게 더 restful 한 가?
    private String content;
    private List<PostImgsUrlReq> postImgUrls;

}
