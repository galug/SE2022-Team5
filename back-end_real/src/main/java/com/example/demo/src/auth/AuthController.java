package com.example.demo.src.auth;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.auth.model.PostLoginReq;
import com.example.demo.src.auth.model.PostLoginRes;
import com.example.demo.utils.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.example.demo.config.BaseResponseStatus.*;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private final AuthProvider authProvider;
    @Autowired
    private final AuthService authService;
    @Autowired
    private final JwtService jwtService;

    public AuthController(AuthProvider authProvider, AuthService authService, JwtService jwtService){
        this.authProvider = authProvider;
        this.authService = authService;
        this.jwtService = jwtService;
    }



    // 로그인 기능 구현
    @ResponseBody
    @PostMapping("/login")
    public BaseResponse<PostLoginRes> logIn(@RequestBody PostLoginReq postLoginReq){
        try{

            // TODO: 탈퇴한 user에 대한 status 관리 필요
            if(postLoginReq.getId() == null){
                return new BaseResponse<>(POST_USERS_EMPTY_ID);
            }
            if(postLoginReq.getPwd() == null){
                return new BaseResponse<>(POST_USERS_EMPTY_PASSWORD);
            }

            PostLoginRes postLoginRes = authService.login(postLoginReq);
            return new BaseResponse<>(postLoginRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

}