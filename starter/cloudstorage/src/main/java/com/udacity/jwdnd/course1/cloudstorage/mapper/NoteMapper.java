package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

  @Select("SELECT * FROM NOTES WHERE userid = #{userid}")
  List<Note> getNotes(User user);

  @Insert(
      "INSERT INTO NOTES (notetitle, notedescription, userid) " +
              "VALUES(#{note.noteTitle},#{note.noteDescription},#{user.userid})")
  @Options(useGeneratedKeys = true, keyProperty = "note.noteid")
  Integer insert(Note note, User user);

  @Delete("DELETE FROM NOTES WHERE userid = #{user.userid} AND noteid = #{noteId}")
  Integer delete(User user, Integer noteId);
}
