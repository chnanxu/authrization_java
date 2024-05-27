package com.chen;

import cn.hutool.core.date.DateTime;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;

@SpringBootTest(classes = Chen_Application.class)
public class Chen_ApplicationTests {

    @Test
    public void test(){
        DateTime queryTimeParameters=DateTime.now();
        Calendar date=new Calendar.Builder().build();
        date.set(queryTimeParameters.getYear(),queryTimeParameters.getMonth(),queryTimeParameters.getDay(),00,00,00);

        System.out.println(date);
    }

}
