package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import java.util.List;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface FileMapper {

  @Select("SELECT * FROM FILES WHERE userid = #{userid}")
  List<File> getFiles(User user);

  @Select("SELECT * FROM FILES WHERE userid = #{user.userid} AND fileId=#{fileid}")
  File getFile(User user, Integer fileid);

  @Select("SELECT * FROM FILES WHERE userid = #{user.userid} AND #{filename} = filename")
  File getFileByName(User user, String filename);

  @Insert(
      "INSERT INTO FILES (filename, contenttype, filesize, filedata, userid) "
          + "VALUES(#{file.fileName},#{file.contentType}, #{file.fileSize}, #{file.fileData}, #{user.userid})")
  @Options(useGeneratedKeys = true, keyProperty = "file.fileId")
  Integer insert(File file, User user);

  @Delete("DELETE FROM FILES WHERE userid = #{user.userid} AND fileid = #{fileId}")
  Integer delete(User user, Integer fileId);
}
