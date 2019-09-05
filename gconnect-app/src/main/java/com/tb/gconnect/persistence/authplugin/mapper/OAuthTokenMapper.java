package com.tb.gconnect.persistence.authplugin.mapper;


import com.tb.gconnect.persistence.authplugin.OAuthTokenPojo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author abdul.rehman4 12th/07/2019
 * @version 1.0
 * @since v1.0
 * Works as {@link Mapper} marker for model {@link OAuthTokenPojo}.
 * It provides all DAO facilities
 *
 * It implements the DAO pattern
 */

@Mapper
@Repository
public interface OAuthTokenMapper {

    @Select("SELECT access_token as accessToken, refresh_token as refreshToken, authorize_token as authorizeToken, client_id as clientId, " +
            "callback_url as callbackUrl, access_token_creation_time as accessTokenCreationTime " +
            "FROM oauth_token")
    List<OAuthTokenPojo> getAll();

    @Select("SELECT access_token as accessToken, refresh_token as refreshToken, authorize_token as authorizeToken, client_id as clientId, " +
            "callback_url as callbackUrl, access_token_creation_time as accessTokenCreationTime " +
            "FROM oauth_token " +
            "where client_id=#{clientId}")
    OAuthTokenPojo getById(String clientId);


    @Select("SELECT access_token as accessToken, refresh_token as refreshToken, authorize_token as authorizeToken, client_id as clientId, " +
            " callback_url as callbackUrl, access_token_creation_time as accessTokenCreationTime " +
            "FROM oauth_token " +
            "where access_token=#{accessToken} OR  refresh_token=#{refreshToken} OR authorize_token=#{authorizeToken} " +
            "OR client_id=#{clientId} OR callback_url=#{callbackUrl} OR access_token_creation_time=#{accessTokenCreationTime}")
    List<OAuthTokenPojo> search(OAuthTokenPojo authTokenPojo);


    @Insert("INSERT into oauth_token (access_token, refresh_token, authorize_token, client_id, callback_url, access_token_creation_time)" +
            "values( #{accessToken}, #{refreshToken}, #{authorizeToken}, #{clientId}, #{callbackUrl}, #{accessTokenCreationTime} )")
    void insert(OAuthTokenPojo authTokenPojo);

    @Insert({"<script>",
            "INSERT into oauth_token (access_token, refresh_token, authorize_token, client_id, callback_url, access_token_creation_time) " +
                    " values ",
            "<foreach collection='authTokenPojoList' item='item' index='index' open='(' separator = '),(' close=')' >" +
                    "#{item.accessToken}, #{item.refreshToken}, #{item.authorizeToken}, #{item.clientId}, #{item.callbackUrl}, #{item.accessTokenCreationTime}" +
                    "</foreach>",
            "</script>"})
    int batchInsert(@Param("authTokenPojoList") List<OAuthTokenPojo> authTokenPojoList);


    @Update("UPDATE oauth_token set  access_token=#{accessToken}, refresh_token=#{refreshToken}, authorize_token=#{authorizeToken}, callback_url=#{callbackUrl}, access_token_creation_time=#{accessTokenCreationTime}" +
            " where client_id=#{clientId}")
    void update(OAuthTokenPojo authTokenPojo);

    @Update({"<script>",
            "    <foreach collection='authTokenPojoList' item='item' index='index' open='' close='' separator=';'>" +
                    "        update oauth_token" +
                    "        <set>" +
                    "           access_token=#{item.accessToken}, refresh_token=#{item.refreshToken}, authorize_token=#{item.authorizeToken}, callback_url=#{item.callbackUrl}, access_token_creation_time=#{item.accessTokenCreationTime} " +
                    "        </set>" +
                    "        where client_id = ${item.client_id}" +
                    "    </foreach>      " +
                    "</script>"})
    void batchUpdate(@Param("authTokenPojoList") List<OAuthTokenPojo> authTokenPojoList);

    @Delete("delete from oauth_token where client_id=#{clientId}")
    void delete(OAuthTokenPojo authTokenPojo);
}
