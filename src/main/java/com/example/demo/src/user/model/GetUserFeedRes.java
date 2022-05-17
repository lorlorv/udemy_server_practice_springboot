package com.example.demo.src.user.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetUserFeedRes {
    //나의 feed를 볼 때와, 남의 feed를 볼 때 다름 -> 클라이언트에게 구별을 위한 값 전달 필요
    private boolean _isMyFeed;

    private GetUserInfoRes getUserInfo;
    private List<GetUserPostsRes> getUserPosts;

}
