package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import java.security.SecureRandom;
import java.util.Base64;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private UserMapper userMapper;
  private HashService hashService;

  public UserService(UserMapper userMapper, HashService hashService) {
    this.userMapper = userMapper;
    this.hashService = hashService;
  }

  public int createUser(User user) {
    SecureRandom random = new SecureRandom();
    byte[] salt = new byte[16];
    random.nextBytes(salt);
    String encodedSalt = Base64.getEncoder().encodeToString(salt);
    String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);
    return userMapper.insert(
        new User(
            null,
            user.getUsername(),
            encodedSalt,
            hashedPassword,
            user.getFirstName(),
            user.getLastName()));
  }

  public boolean isUsernameAvailable(User user) {
    return (userMapper.getUser(user.getUsername()) == null ? true : false);
  }

  public User getUser(String username) {
    return userMapper.getUser(username);
  }
}
