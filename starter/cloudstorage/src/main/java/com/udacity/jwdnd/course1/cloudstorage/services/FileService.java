package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper ) {
        this.fileMapper = fileMapper;
    }

    public int createFile (MultipartFile multipartFile, User user) throws IOException {
        // validate
        if(multipartFile.isEmpty()) {
            throw new RuntimeException("Choose a file before Uploading");
        }
        if (isFileNameExists(user, multipartFile.getOriginalFilename())) {
            throw new RuntimeException("File named "+ multipartFile.getOriginalFilename() + " already exists.");
        }
        File file = File.builder()
                .fileName(multipartFile.getOriginalFilename())
                .fileSize(String.valueOf(multipartFile.getSize()))
                .contentType(multipartFile.getContentType())
                .fileData(multipartFile.getBytes())
                .build();
        return (fileMapper.insert(file, user));
    }

    public List<File> getFiles (User user) {
        return fileMapper.getFiles(user);
    }

    public File getFile (User user, Integer fileId) {
        return fileMapper.getFile(user, fileId);
    }

    public boolean isFileNameExists(User user, String filename){
        File file = fileMapper.getFileByName(user, filename);
        return (file != null);
    }

    public int deleteFile (Integer fileId, User user) {
        return fileMapper.delete(user, fileId);
    }
}
