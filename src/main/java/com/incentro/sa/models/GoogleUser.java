package com.incentro.sa.models;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
@Cache
public class GoogleUser {

    @Id
    private Long id;

    @Index
    private String userId;

    @Index
    private String primaryEmail;

    @Index
    private String AccessToken;
    @Index
    private String RefreshToken;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPrimaryEmail() {
        return primaryEmail;
    }

    public void setPrimaryEmail(String primaryEmail) {
        this.primaryEmail = primaryEmail;
    }

    public String getRefreshToken() {
        return RefreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        RefreshToken = refreshToken;
    }

    @Override
    public String toString() {
        return "GoogleUser{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", primaryEmail='" + primaryEmail + '\'' +
                ", AccessToken='" + AccessToken + '\'' +
                ", RefreshToken='" + RefreshToken + '\'' +
                '}';
    }

    public String getAccessToken() {
        return AccessToken;
    }

    public void setAccessToken(String accessToken) {
        AccessToken = accessToken;
    }
}