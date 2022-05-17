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
    private int userIdx;
    private String nickName;
    private String profileImgUrl;
    private String content;
    private int postLikeCount;
    private int commentCount;
    private String updateAt;
    private String likeOrNot; //내가 이 글을 좋아요 했는지 안했는지
    private List<GetPostImgRes> imgs;

}
