package com.chen.controller;

import com.chen.utils.util.RedisCache;
import com.google.code.kaptcha.Producer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.OutputStream;

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
        BufferedImage image=producer.createImage(capText);
        OutputStream out=response.getOutputStream();

        redisCache.setCacheObject("captcha",capText);

        ImageIO.write(image,"jpg",out);
    }

}
