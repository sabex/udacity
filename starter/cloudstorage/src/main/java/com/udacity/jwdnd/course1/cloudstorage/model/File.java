package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class File {
  private Integer fileId;
  private String fileName;
  private String contentType;
  private String fileSize;
  private byte[] fileData;
}
