package com.example.demo.utils;

import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUpload {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss_");
    public static String fileSave(MultipartFile file) throws IOException {
        try{
            String uri = "C:/sw_engineering/SE2022-Team5/pictures/";
            Date curTime = new Date();
            if (!file.isEmpty()) {
                String originalFileExtension = ".jpg";
                String contentType = file.getContentType();
                // 저장되는 파일 이름
                String fileName = dateFormat.format(curTime) + originalFileExtension;
                File dest = new File(uri + fileName);
                // 파일 생성하기
                file.transferTo(dest);
                return uri + fileName;
            }
        }catch (Exception e){
            throw new IOException("WRONG IO");
        }
        return null;
    }
}
