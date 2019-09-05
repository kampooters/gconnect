package com.org.practise.orm.mybatis;

import com.org.practise.orm.mybatis.mapper.OAuthClientMapper;
import com.org.practise.orm.mybatis.pojo.OAuthClientPojo;
import com.tb.gconnect.logger.LogManager;

import javax.annotation.Resource;
import java.util.List;

public class OAuthClientService extends LogManager {
    @Resource
    OAuthClientMapper oAuthClientMapper;

    public void select(){
       try {
           List<OAuthClientPojo> clients =  oAuthClientMapper.getAll();
           info("Fetched clients are: "+clients.size());
       }catch (Exception e){
           error(e.getMessage());
       }
    }
}
