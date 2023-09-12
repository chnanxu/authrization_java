package com.chen.controller;

import com.chen.pojo.Movies;
import com.chen.service.MoviesServiceImpl;

import com.chen.utils.result.CommonCode;
import com.chen.utils.result.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
public class MoviesController {


    @Autowired
    private MoviesServiceImpl moviesService;
    @RequestMapping({"/","/index"})
    @ResponseBody
    public ResponseResult<Movies> load(){
        List<Movies> movies=moviesService.getMovies();
        return new ResponseResult(CommonCode.SUCCESS,movies);
    }


}
