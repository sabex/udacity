package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class NoteService {

  private NoteMapper noteMapper;

  public NoteService(NoteMapper noteMapper) {
    this.noteMapper = noteMapper;
  }

  public int createNote(Note note, User user) {
    return (noteMapper.insert(user, note));
  }

  public int updateNote(Note note, User user) {
    return (noteMapper.update(user, note));
  }

  public List<Note> getNotes(User user) {
    return noteMapper.getNotes(user);
  }

  public int deleteNote(Integer noteId, User user) {
    return noteMapper.delete(user, noteId);
  }
}
