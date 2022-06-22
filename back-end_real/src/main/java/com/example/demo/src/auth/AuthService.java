package com.example.demo.src.auth;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.auth.model.PostLoginReq;
import com.example.demo.src.auth.model.PostLoginRes;
import com.example.demo.src.auth.model.LoginUserInfo;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());


    private AuthDao authDao;
    private AuthProvider authProvider;
    private JwtService jwtService;

    @Autowired
    public AuthService(AuthDao authDao, AuthProvider authProvider, JwtService jwtService) {
        this.authDao = authDao;
        this.authProvider = authProvider;
        this.jwtService = jwtService;
    }

    public PostLoginRes login(PostLoginReq postLoginReq) throws BaseException{
        LoginUserInfo loginUserInfo = authDao.getPwd(postLoginReq);
        String encyptPwd;

        try{
            encyptPwd = postLoginReq.getPwd();
        }
        catch (Exception exception){
            throw new BaseException(BaseResponseStatus.PASSWORD_ENCRYPTION_ERROR);
        }
        if(loginUserInfo.getPwd().equals(encyptPwd)){
            int userIdx = loginUserInfo.getUserIdx();
            String jwt = jwtService.createJwt(userIdx);
            return new PostLoginRes(userIdx,jwt);
        }
        else
            throw new BaseException(BaseResponseStatus.FAILED_TO_LOGIN);
    }
}
