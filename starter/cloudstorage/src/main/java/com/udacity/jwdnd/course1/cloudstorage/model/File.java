package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class File {
  private Integer fileId;
  private String fileName;
  private String contentType;
  private String fileSize;
  private MultipartFile fileData;
}
