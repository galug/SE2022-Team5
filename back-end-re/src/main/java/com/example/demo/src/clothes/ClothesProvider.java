package com.example.demo.src.clothes;

import com.example.demo.config.BaseException;
import com.example.demo.src.clothes.model.GetClothesListReq;
import com.example.demo.src.clothes.model.GetClothesListRes;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class ClothesProvider {

    private final ClothesDao clothesDao;
    private final JwtService jwtService;

    @Autowired
    public ClothesProvider(ClothesDao clothesDao, JwtService jwtService) {
        this.clothesDao = clothesDao;
        this.jwtService = jwtService;
    }
    public List<GetClothesListRes> retrieveClothes(GetClothesListReq getClothesListReq) throws BaseException {
        try{
            List<GetClothesListRes> getPosts;
            if(getClothesListReq.getBigCategory()==10){
                getPosts = clothesDao.selectPostsAll(getClothesListReq.getUserIdx());
            }
            else{
                getPosts = clothesDao.selectPosts(getClothesListReq);
            }
            return getPosts;
        }
        catch (Exception exception) {
            System.out.println("exception = " + exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
