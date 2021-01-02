/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2020.
 */

package com.udacity.jwdnd.course1.cloudstorage.Mapper;

import com.udacity.jwdnd.course1.cloudstorage.Model.Credentials;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CredentialsMapper {
    @Select("SELECT * FROM CREDENTIALS WHERE userId = #{userId}")
    List<Credentials> getAllCredentialsByUserId(Integer userId);

    @Select("SELECT * FROM CREDENTIALS WHERE userId = #{userId} AND credentialId = #{credentialId}")
    Credentials getCredentialsByUserAndId(Integer userId, Integer credentialId);

    @Insert("INSERT INTO CREDENTIALS (url, userName, key, password, userId) VALUES (#{url}, #{userName}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int addCredentials(Credentials credentials);

    @Update("UPDATE CREDENTIALS SET url = #{url}, userName = #{userName},  key = #{key}, password = #{password}, userId = #{userId} WHERE credentialId = #{credentialId}")
    int updateCredentials(Credentials credentials);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialId = #{credentialId}")
    int deleteCredentials(Integer credentialId);
}

