package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CredentialMapper {

  @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userid}")
  List<Credential> getCredentials(User user);

  @Insert(
      "INSERT INTO CREDENTIALS (url, username, key, password, userid) " +
              "VALUES(#{credential.url},#{credential.username},#{credential.key},#{credential.password},#{user.userid})")
  @Options(useGeneratedKeys = true, keyProperty = "credential.credentialid")
  Integer insert(Credential credential, User user);
}
