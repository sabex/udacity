package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {

    private CredentialMapper credentialMapper;
    private HashService hashService;

    public CredentialService (CredentialMapper credentialMapper, HashService hashService) {
        this.credentialMapper = credentialMapper;
        this.hashService = hashService;
    }

    public int createCredential (Credential credential, User user) {
        credential.setKey(hashService.getHashedValue(credential.getPassword(), user.getSalt()));
        return (credentialMapper.insert(credential, user));
    }

    public int updateCredential (Credential credential, User user) {
        credential.setKey(hashService.getHashedValue(credential.getPassword(), user.getSalt()));
        return (credentialMapper.update(credential, user));
    }

    public List<Credential> getCredentials (User user) {
        return credentialMapper.getCredentials(user);
    }

    public int deleteCredential(Integer credentialId, User user){
        return credentialMapper.delete(user, credentialId);
    }
}
