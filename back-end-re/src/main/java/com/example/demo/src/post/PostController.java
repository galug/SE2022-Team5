package com.example.demo.src.post;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.post.model.PostPostsReq;
import com.example.demo.src.post.model.PostPostsRes;
import com.example.demo.utils.FileUpload;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.POST_CLOTHES_WRONG_IMAGE;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostProvider postProvider;
    private final PostService postService;
    private final JwtService jwtService;

    @Autowired
    public PostController(PostProvider postProvider, PostService postService, JwtService jwtService){
        this.postProvider = postProvider;
        this.postService = postService;
        this.jwtService = jwtService;
    }

//    @ResponseBody
//    @PostMapping("/lookup") // (Post) 127.0.0.1:9000/clothes/lookup
//    public BaseResponse<List<GetPostsRes>> getPostList(@RequestBody GetPostsReq getPostsReq)  {
//        try{
//            List<GetPostsRes> getPostsResList = postProvider.retrievePosts(getPostsReq);
//            for (GetPostsRes getPost : getPostsResList) {
//                InputStream imageStream = null;
//                try {
//                    imageStream = new FileInputStream(getPost.getUri());
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//                try {
//                    byte[] imageByteArray = IOUtils.toByteArray(imageStream);
//                    getPost.setFile(imageByteArray);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                try {
//                    imageStream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            for (GetPostsRes getPost : getPostsResList) {
//                System.out.println("getPost.getPostIdx() = " + getPost.getUri());
//            }
//            return new BaseResponse<>(getPostsResList);
//        } catch(BaseException exception){
//            return new BaseResponse<>((exception.getStatus()));
//        }
//    }

    @ResponseBody
    @PostMapping // (POST) 127.0.0.1:9000/posts
    public BaseResponse<PostPostsRes> createPost(@RequestPart MultipartFile imgFile,
                                                   @RequestPart String userIdx,
                                                   @RequestPart String title
                                                   )throws IOException {
        try{
            String contentType = imgFile.getContentType();
            if (ObjectUtils.isEmpty(contentType)){
                return new BaseResponse<>(POST_CLOTHES_WRONG_IMAGE);
            }
            System.out.println("multipartFiel = " + imgFile.toString());
            PostPostsReq postPostsReq = new PostPostsReq(Integer.parseInt(userIdx),title);
            String imgUri = FileUpload.fileSave(imgFile);
            PostPostsRes postPostsRes = postService.createPosts(postPostsReq,imgUri);
            return new BaseResponse<>(postPostsRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }




    @ResponseBody
    @PatchMapping("/{postIdx}/status") // (Patch) 127.0.0.1:9000/posts/status
    public BaseResponse<String> deletePosts(@PathVariable("postIdx") int postIdx) {
        try{
            // 생성 관련 문이기 때문에 postService이고 getUserIdx를 따로 빼주는 이유는 jwt를 통해 userIdx를 따로 받을 수 있기 때문이다.
            postService.deletePost(postIdx);
            String result = "게시물 삭제가 완료되었습니다.";
            return new BaseResponse<>(result);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
