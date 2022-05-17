package com.example.demo.src.post;

//import com.example.demo.config.BaseException;
//import com.example.demo.config.BaseResponse;
//import com.example.demo.config.BaseResponseStatus;
//import com.example.demo.src.post.model.GetPostsRes;
//import com.example.demo.src.post.model.PatchPostsReq;
//import com.example.demo.src.post.model.PostPostsReq;
//import com.example.demo.src.post.model.PostPostsRes;
//import com.example.demo.src.user.UserProvider;
//import com.example.demo.utils.JwtService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import  com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.post.model.*;
import com.example.demo.src.user.model.PatchUserReq;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


import static com.example.demo.config.BaseResponseStatus.INVALID_USER_JWT;
import static com.example.demo.config.BaseResponseStatus.POST_POSTS_EMPTY_IMGURL;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final PostProvider postProvider;
    @Autowired
    private final PostService postService;
    @Autowired
    private final JwtService jwtService;




    public PostController(PostProvider postProvider, PostService postService, JwtService jwtService){
        this.postProvider = postProvider;
        this.postService = postService;
        this.jwtService = jwtService;
    }

    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetPostsRes>> getPosts(@RequestParam int userIdx) {
        try{

            List<GetPostsRes> getPostsRes = postProvider.retrievePosts(userIdx); //비교는 provider가
            return new BaseResponse<>(getPostsRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostPostsRes> createPosts(@RequestBody PostPostsReq postPostsReq) {
        try{
            int userIdxByJwt = jwtService.getUserIdx();
            if(postPostsReq.getUserIdx() != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            //형식적 validation 처리 -> 이미지 , 게시글
            if(postPostsReq.getContent().length() > 450){
                return new BaseResponse<>(BaseResponseStatus.POST_POSTS_INVALID_CONTENTS);
            }

            if(postPostsReq.getPostImgUrls().size() < 1){
                return new BaseResponse<>(BaseResponseStatus.POST_POSTS_EMPTY_IMGURL);
            }

            PostPostsRes postPostsRes = postService.createPosts(postPostsReq.getUserIdx(), postPostsReq); //userIdx만 따로 빼오는 이유 : 나중의 jwt 편리를 위해
            return new BaseResponse<>(postPostsRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PatchMapping("/{postIdx}")
    public BaseResponse<String> modifyPosts(@PathVariable ("postIdx") int postIdx, @RequestBody PatchPostsReq patchPostsReq) {
        try{
            //형식적 validation 처리 -> 이미지 , 게시글
            if(patchPostsReq.getContent().length() > 450){
                return new BaseResponse<>(BaseResponseStatus.POST_POSTS_INVALID_CONTENTS);
            }
            postService.modifyPosts(patchPostsReq.getUserIdx(), postIdx, patchPostsReq); //userIdx만 따로 빼오는 이유 : 나중의 jwt 편리를 위해
            String result = "게시물 정보 수정을 완료하였습니다.";
            return new BaseResponse<>(result);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PatchMapping("/{postIdx}/status")
    public BaseResponse<String> deletePosts(@PathVariable ("postIdx") int postIdx) {
        try{
            postService.deletePost(postIdx);
            String result = "삭제를 완료하였습니다.";
            return new BaseResponse<>(result);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
