package com.lijt.chat.controller;

import com.lijt.chat.common.base.BaseResult;
import com.lijt.chat.common.config.OSSFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>类  名：com.lijt.chat.controller.FileUploadController</p>
 * <p>类描述：todo 文件上传</p>
 * <p>创建时间：2022/8/26 11:02</p>
 *
 * @author ruffian_lee
 * @version 1.0
 */
@RestController
@RequestMapping("/chat/file")
public class FileUploadController {


    /**
     * @param multipartFile 文件传输对象
     * @param requestId     请求ID，为后续反写业务ID时使用
     * @param memberId      会员ID
     * @param sort          排序值
     * @return
     */
    @RequestMapping(value = "/upload")
    public BaseResult upload(@RequestParam("file") MultipartFile multipartFile,
                             @RequestParam(required = false, value = "requestId") String requestId,
                             @RequestParam("memberId") Long memberId,
                             @RequestParam(required = false, value = "sort") Integer sort
    ) {
        //上传文件
        String suffix = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
        try {
            String url = OSSFactory.build().uploadSuffix(multipartFile.getBytes(), suffix);
            return BaseResult.okToMap("url", url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return BaseResult.commonError();
    }
}
