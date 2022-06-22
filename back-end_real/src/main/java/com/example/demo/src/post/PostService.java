package com.example.demo.src.post;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.post.model.PostPostsLikeReq;
import com.example.demo.src.post.model.PostPostsLikeRes;
import com.example.demo.src.post.model.PostPostsReq;
import com.example.demo.src.post.model.PostPostsRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class PostService {
    private final PostDao postDao;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public PostService(PostDao postDao, JwtService jwtService) {
        this.postDao = postDao;
        this.jwtService = jwtService;
    }

    public PostPostsRes createPosts(PostPostsReq postPostsReq, String imgUri) throws BaseException {
        try{
            int postIdx = postDao.createPosts(postPostsReq,imgUri);
            return new PostPostsRes(postIdx);
        } catch (Exception exception) {
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int likePosts(PostPostsLikeReq postPostsLikeReq) throws BaseException {
        try{
            if(postDao.checkPostLikeExist(postPostsLikeReq)!=0){
                return 0;
            }else{
                int postIdx = postDao.likePosts(postPostsLikeReq);
                return postIdx;
            }
        } catch (Exception exception) {
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }



    void deletePost(int postIdx) throws BaseException
    {
        if(postDao.checkPostExist(postIdx)==0)
        {
            throw new BaseException(BaseResponseStatus.POST_EMPTY_POST_ID);
        }
        try{
            int result = postDao.deletePost(postIdx);
            if(result==0)
            {
                throw new BaseException(BaseResponseStatus.DELETE_FAIL_POST);
            }
        }
        catch (Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
}
