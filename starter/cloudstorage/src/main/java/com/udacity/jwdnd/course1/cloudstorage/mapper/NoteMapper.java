package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import java.util.List;
import org.apache.ibatis.annotations.*;

@Mapper
public interface NoteMapper {

  @Select("SELECT * FROM NOTES WHERE userid = #{userid}")
  List<Note> getNotes(User user);

  @Insert(
      "INSERT INTO NOTES (notetitle, notedescription, userid) "
          + "VALUES(#{note.noteTitle},#{note.noteDescription},#{user.userid})")
  @Options(useGeneratedKeys = true, keyProperty = "note.noteid")
  Integer insert(User user, Note note);

  @Update(
      "UPDATE NOTES SET notetitle = #{note.noteTitle} , notedescription = #{note.noteDescription} WHERE userid = #{user.userid} AND noteid = #{note.noteid}")
  Integer update(User user, Note note);

  @Delete("DELETE FROM NOTES WHERE userid = #{user.userid} AND noteid = #{noteId}")
  Integer delete(User user, Integer noteId);
}
