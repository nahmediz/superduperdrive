package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
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

    public Credential getCredential(int credentialId) {
        return credentialMapper.getCredential(credentialId);
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
        return encryptionService.encryptValue(credential.getPassword(), credential.getSalt());
    }

    public List<Credential> getUserCredentials(User user) {
        List<Credential> credentials = credentialMapper.getUserCredentials(user);

        for (Credential credential : credentials) {
            credential.setPassword(encryptionService.decryptValue(credential.getPassword(), credential.getSalt()));
        }

        return credentials;
    }

    public void updateCredential(Credential credential) {
        Credential dbCredential = credentialMapper.getCredential(credential.getCredentialId());
        String hashedPassword = encryptionService.encryptValue(credential.getPassword(), dbCredential.getSalt());
        dbCredential.setUrl(credential.getUrl());
        dbCredential.setUsername(credential.getUsername());
        dbCredential.setPassword(hashedPassword);
        credentialMapper.updateCredential(dbCredential);
    }

    public boolean deleteCredential(@NotNull Credential credential) {
        if (credentialMapper.getCredential(credential.getCredentialId()) != null) {
            credentialMapper.deleteCredentials(credential);
            return true;
        } else {
            return false;
        }
    }
}
