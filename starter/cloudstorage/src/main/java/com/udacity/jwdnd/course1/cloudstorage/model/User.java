package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
  private Integer userid;
  private String username;
  private String salt;
  private String password;
  private String firstName;
  private String lastName;
}
