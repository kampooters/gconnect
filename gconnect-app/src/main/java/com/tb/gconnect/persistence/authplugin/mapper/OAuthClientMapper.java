package com.tb.gconnect.persistence.authplugin.mapper;


import com.tb.gconnect.persistence.authplugin.OAuthClientPojo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author abdul.rehman4 12th/07/2019
 * @version 1.0
 * @since v1.0
 * Works as {@link Mapper} marker for model {@link OAuthClientPojo}.
 * It provides all DAO facilities
 *
 * It implements the DAO pattern
 */

@Mapper
@Repository
public interface OAuthClientMapper {

    @Select("SELECT client_id as clientId, secret, owner FROM oauth_client")
    List<OAuthClientPojo> getAll();

    @Select("SELECT client_id as clientId, secret, owner FROM oauth_client where client_id=#{clientId}")
    List<OAuthClientPojo> getById(String clientId);


    @Select("SELECT client_id as clientId, secret, owner FROM oauth_client where client_id=#{clientId} OR  secret=#{secret} OR  owner=#{owner}")
    List<OAuthClientPojo> search(OAuthClientPojo authClientPojo);


    @Insert("INSERT into oauth_client (client_id, secret, owner)" +
            "values( #{clientId}, #{secret}, #{owner} )")
    void insert(OAuthClientPojo authClientPojo);

    @Insert({"<script>",
            "INSERT into oauth_client (client_id, secret, owner) " +
                    " values ",
            "<foreach collection='authClientPojoList' item='item' index='index' open='(' separator = '),(' close=')' >" +
                    "#{item.clientId}, #{item.secret}, #{item.owner}" +
                    "</foreach>",
            "</script>"})
    int batchInsert(@Param("authClientPojoList") List<OAuthClientPojo> authClientPojoList);


    @Update("UPDATE oauth_client set secret=#{secret}, owner=#{owner}" +
            " where client_id=#{clientId}")
    void update(OAuthClientPojo authClientPojo);

    @Update({"<script>",
            "    <foreach collection='authClientPojoList' item='item' index='index' open='' close='' separator=';'>" +
                    "        update oauth_client" +
                    "        <set>" +
                    "secret=#{ item.secret}, owner=#{ item.owner} " +
                    "        </set>" +
                    "        where client_id = ${item.clientId}" +
                    "    </foreach>      " +
                    "</script>"})
    void batchUpdate(@Param("authClientPojoList") List<OAuthClientPojo> authClientPojoList);

    @Delete("delete from oauth_client where client_id=#{clientId}")
    void delete(OAuthClientPojo authClientPojo);
}
