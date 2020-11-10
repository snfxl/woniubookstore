package com.sn.woniubookstore.test;

import com.sn.woniubookstore.annotation.NoRepeatSubmit;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: shinan
 * @Version: 1.8
 * @create: 2020/10/30 10:00
 * @Description: TODO
 */
@Controller
public class AopTest {

    @GetMapping("/testAop")
    @ResponseBody
    @NoRepeatSubmit
    public String test1(HttpServletRequest request){

        return "111";
    }

}