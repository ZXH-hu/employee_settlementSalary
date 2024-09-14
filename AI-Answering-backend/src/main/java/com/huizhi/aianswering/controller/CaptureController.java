package com.huizhi.aianswering.controller;

import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import com.wf.captcha.utils.CaptchaUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/capt")
@CrossOrigin
public class CaptureController {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @ApiOperation("生成字符组合验证码")
    @GetMapping("/captcha")
    public void getCaptcha(@RequestParam String userKey, HttpServletRequest request, HttpServletResponse response) throws IOException {
        ValueOperations<String, String> redisCapture = redisTemplate.opsForValue();
        //png类型，数字加字母,len：字符个数
        SpecCaptcha specCaptcha = new SpecCaptcha(135, 33, 5);
        specCaptcha.setCharType(Captcha.TYPE_NUM_AND_UPPER);
        //存入redis
        try {
            redisCapture.set(userKey,specCaptcha.text().toLowerCase(), 60000, TimeUnit.MILLISECONDS);
        }  catch (Exception e) {
            e.printStackTrace();
        }
        CaptchaUtil.out(specCaptcha,request,response);
    }


    @ApiOperation("生成算术验证码")
    @GetMapping("/math_captcha")
    public void getMathCaptcha(@RequestParam String userKey, HttpServletRequest request, HttpServletResponse response) throws IOException {
        ValueOperations<String, String> redisCapture = redisTemplate.opsForValue();
        //算术型
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(135, 33);
        captcha.setLen(4);            //几位数运算，默认是两位
        captcha.getArithmeticString();    //获取运算的公式
        captcha.text();          //获取运算的结果
        //存入redis
        try {
            redisCapture.set(userKey,captcha.text().toLowerCase(), 60000, TimeUnit.MILLISECONDS);
        }  catch (Exception e) {
            e.printStackTrace();
        }
        CaptchaUtil.out(captcha,request,response);
    }

}
