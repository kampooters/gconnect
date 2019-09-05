package com.org.practise.orm.mybatis;

import javax.annotation.Resource;

public class MybatisMain {
    @Resource
    static OAuthClientService oAuthClientService;

    public static void main(String [] ags){
//        OAuthClientService oAuthClientService = new OAuthClientService();


        oAuthClientService.select();
    }
}
