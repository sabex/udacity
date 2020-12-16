package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FileMapper {

  @Select("SELECT * FROM FILES WHERE userid = #{userid}")
  List<File> getFiles(User user);

  @Insert(
      "INSERT INTO NOTES (filename, contenttype, filesize, filedata, userid) " +
              "VALUES(#{file.fileName},#{file.contentType}, #{file.fileSize}, #{file.fileData}, #{user.userid})")
  @Options(useGeneratedKeys = true, keyProperty = "file.fileId")
  Integer insert(File file, User user);
}
