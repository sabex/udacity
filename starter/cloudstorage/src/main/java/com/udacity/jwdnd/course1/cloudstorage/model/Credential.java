package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.Data;

@Data
public class Credential {
  private Integer credentialid;
  private String url;
  private String username;
  private String password;
  private String key;
}
