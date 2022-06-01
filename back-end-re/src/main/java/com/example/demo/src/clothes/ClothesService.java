package com.example.demo.src.clothes;

import com.example.demo.config.BaseException;
import com.example.demo.src.clothes.model.PostClothesReq;
import com.example.demo.src.clothes.model.PostClothesRes;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class ClothesService {

    private final ClothesDao clothesDao;
    private final JwtService jwtService;

    @Autowired
    public ClothesService(ClothesDao clothesDao, JwtService jwtService) {
        this.clothesDao = clothesDao;
        this.jwtService = jwtService;
    }
    public PostClothesRes createClothes(PostClothesReq postClothesReq,String imgUri) throws BaseException {
        try{
            int clothesIdx = clothesDao.createClothes(postClothesReq,imgUri);
            return new PostClothesRes(clothesIdx);
        } catch (Exception exception) {
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
