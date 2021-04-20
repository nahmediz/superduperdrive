package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialServices {

    private CredentialMapper credentialMapper;
    private EncryptionService encryptionService;

    public CredentialServices(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public int addCredential(Credential credential) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = encryptionService.encryptValue(credential.getPassword(), encodedSalt);

        return credentialMapper.addCredential(new Credential(null, credential.getUrl(), credential.getUsername(), encodedSalt, hashedPassword, credential.getUserid()));
    }

    public String getCredentialPlainTextPassword(Credential credential) {
        return encryptionService.encryptValue(credential.getPassword(), credential.getKey());
    }

    public List<Credential> getUserCredentials(User user) {
        return credentialMapper.getUserCredentials(user);
    }

    public void updateCredential(Credential credential) {
        credentialMapper.updateCredential(credential);
    }

    public void deleteCredential(Credential credential) {
        credentialMapper.deleteCredentials(credential);
    }
}
