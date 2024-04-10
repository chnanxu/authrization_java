package com.chen.controller;

import com.chen.utils.result.CommonCode;
import com.chen.utils.result.ResponseResult;
import com.chen.utils.util.RedisCache;
import com.google.code.kaptcha.Producer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.OutputStream;

import static com.chen.utils.util.RedisConstants.*;

@Slf4j
@Controller
@RequestMapping("/captcha")   //验证码接口
public class CaptchaController {
    @Autowired
    private Producer producer;

    @Autowired
    private RedisCache redisCache;
    @SneakyThrows
    @RequestMapping ("/captcha")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("image/jpeg");

        String capText=producer.createText();
        log.info("验证码:{}",capText);
        request.getSession().setAttribute("captcha",capText);
        redisCache.setCacheObject((IMAGE_CAPTCHA_PREFIX_KEY +"captchaId"), capText, DEFAULT_TIMEOUT_SECONDS);
        BufferedImage image=producer.createImage(capText);
        OutputStream out=response.getOutputStream();



        ImageIO.write(image,"jpg",out);
    }

    @GetMapping("/getSmsCaptcha")
    public ResponseResult getSmsCaptcha(String phone) {
        // 示例项目，固定1234
        String smsCaptcha = "1234";
        // 存入缓存中，5分钟后过期
        redisCache.setCacheObject((SMS_CAPTCHA_PREFIX_KEY + phone), smsCaptcha, DEFAULT_TIMEOUT_SECONDS);
        return new ResponseResult(CommonCode.SUCCESS,smsCaptcha);
    }


}
