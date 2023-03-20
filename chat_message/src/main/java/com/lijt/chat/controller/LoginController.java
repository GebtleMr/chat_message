package com.lijt.chat.controller;

import com.lijt.chat.common.base.BaseResult;
import com.lijt.chat.model.MinLoginModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>类  名：com.lijt.chat.controller.LoginController</p>
 * <p>类描述：todo 登录入口</p>
 * <p>创建时间：2023/3/13 17:35</p>
 *
 * @author junting.li
 * @version 1.0
 */
@RestController
@RequestMapping("/common")
public class LoginController {


    @PostMapping("/login")
    public BaseResult login(@RequestBody MinLoginModel minLoginModel) {
        //调用腾讯小程序API 获取openId ,unionId 等信息，生成token 数据，返回

        return BaseResult.okToMap("token", "1111111111111");
    }


}
