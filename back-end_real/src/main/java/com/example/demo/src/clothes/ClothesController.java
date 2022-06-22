package com.example.demo.src.clothes;

import com.example.demo.src.clothes.model.*;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.utils.FileUpload;
import com.example.demo.utils.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.POST_CLOTHES_WRONG_IMAGE;


@Slf4j
@RestController
@RequestMapping("clothes")
public class ClothesController {
    private final ClothesProvider clothesProvider;
    private final ClothesService clothesService;
    private final JwtService jwtService;

    @Autowired
    public ClothesController(ClothesProvider clothesProvider, ClothesService clothesService, JwtService jwtService) {
        this.clothesProvider = clothesProvider;
        this.clothesService = clothesService;
        this.jwtService = jwtService;
    }

    @ResponseBody
    @PostMapping("/lookup") // (Post) 127.0.0.1:9000/clothes/lookup
    public BaseResponse<List<GetClothesListRes>> getClothesList(@RequestBody GetClothesListReq getClothesListReq)  {
        try{
            List<GetClothesListRes> getClothesListRes = clothesProvider.retrieveClothes(getClothesListReq);
            for (GetClothesListRes getClothes : getClothesListRes) {
                InputStream imageStream = null;
                try {
                    imageStream = new FileInputStream(getClothes.getUri());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    byte[] imageByteArray = IOUtils.toByteArray(imageStream);
                    getClothes.setFile(imageByteArray);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    imageStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            for (GetClothesListRes getPost : getClothesListRes) {
                System.out.println("getPost.getPostIdx() = " + getPost.getUri());
            }
            return new BaseResponse<>(getClothesListRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PostMapping // (POST) 127.0.0.1:9000/clothes
    public BaseResponse<PostClothesRes> createClothes(@RequestPart MultipartFile imgFile,
                                                   @RequestPart String userIdx,
                                                   @RequestPart String season,
                                                   @RequestPart String bigCategory,
                                                   @RequestPart String smallCategory,
                                                   @RequestPart String color)throws IOException {
        try{
            String contentType = imgFile.getContentType();
            if (ObjectUtils.isEmpty(contentType)){
                return new BaseResponse<>(POST_CLOTHES_WRONG_IMAGE);
            }
            System.out.println("multipartFiel = " + imgFile.toString());
            PostClothesReq postClothesReq = new PostClothesReq(Integer.parseInt(userIdx), Integer.parseInt(season), Integer.parseInt(bigCategory), Integer.parseInt(smallCategory), color);
            String imgUri = FileUpload.fileSave(imgFile);
            PostClothesRes postClothesRes = clothesService.createClothes(postClothesReq,imgUri);
            return new BaseResponse<>(postClothesRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

//    @ResponseBody
//    @PostMapping("/update") // (POST) 127.0.0.1:9000/clothes
//    public BaseResponse<PostClothesRes> updateClothes(@RequestPart MultipartFile imgFile,
//                                                      @RequestPart String userIdx,
//                                                      @RequestPart int postIdx
//                                                      )throws IOException {
//        try{
//            String contentType = imgFile.getContentType();
//            if (ObjectUtils.isEmpty(contentType)){
//                return new BaseResponse<>(POST_CLOTHES_WRONG_IMAGE);
//            }
//            System.out.println("multipartFiel = " + imgFile.toString());
//            PostClothesReq postClothesReq = new UpdateClothesReq(Integer.parseInt(userIdx), Integer.parseInt(season), Integer.parseInt(bigCategory), Integer.parseInt(smallCategory), color);
//            String imgUri = FileUpload.fileSave(imgFile);
//            PostClothesRes postClothesRes = clothesService.createClothes(postClothesReq,imgUri);
//            return new BaseResponse<>(postClothesRes);
//        } catch(BaseException exception){
//            return new BaseResponse<>((exception.getStatus()));
//        }
//    }
    @ResponseBody
    @PatchMapping("/{clothesIdx}/status") // (Patch) 127.0.0.1:9000/posts/status
    public BaseResponse<String> deleteClothes(@PathVariable("clothesIdx") int clothesIdx) {
        try{
            // 생성 관련 문이기 때문에 postService이고 getUserIdx를 따로 빼주는 이유는 jwt를 통해 userIdx를 따로 받을 수 있기 때문이다.
            clothesService.deleteClothes(clothesIdx);
            String result = "옷 삭제가 완료되었습니다.";
            return new BaseResponse<>(result);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}