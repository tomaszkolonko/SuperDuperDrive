/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2020.
 */

package com.udacity.jwdnd.course1.cloudstorage.Services;

import com.udacity.jwdnd.course1.cloudstorage.Mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.Model.Credentials;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialsService {

    private CredentialsMapper credentialsMapper;

    public CredentialsService(CredentialsMapper credentialsMapper) {
        this.credentialsMapper = credentialsMapper;
    }

    public List<Credentials> getAllCredentialsByUserId(Integer userId) {
        return credentialsMapper.getAllCredentialsByUserId(userId);
    }

    public Credentials getCredentialsByUserAndId(Integer userId, Integer credentialId) {
        return credentialsMapper.getCredentialsByUserAndId(userId, credentialId);
    }

    public void addCredentials(Credentials credentials) {
        credentialsMapper.addCredentials(credentials);
    }

    public int updateCredentials(Credentials credentials) {
        return credentialsMapper.updateCredentials(credentials);
    }

    public int deleteCredentials(Integer credentialId) {
        return credentialsMapper.deleteCredentials(credentialId);
    }
}
