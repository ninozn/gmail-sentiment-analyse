package com.incentro.sa.models;


import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.Calendar;
import java.util.Date;

@Entity
@Cache
public class EmailObject {

    @Id
    private Long id;

    @Index
    private String primaryEmail;

    @Index
    private Date dateOfMail;
    @Index
    private String messageID;
    @Index
    private String fromMail;
    @Index
    private String subjectMail;
    @Index
    private String statusMail;

    public EmailObject() {
    }

    public EmailObject(String user, Date theDate, String messID, String from, String sub) {
        primaryEmail = user;
        dateOfMail = theDate;
        fromMail = from;
        subjectMail = sub;
        messageID = messID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrimaryEmail() {
        return primaryEmail;
    }

    public void setPrimaryEmail(String primaryEmail) {
        this.primaryEmail = primaryEmail;
    }

    public Date getDateOfMail() {
        return dateOfMail;
    }

    public void setDateOfMail(Date dateOfMail) {
        this.dateOfMail = dateOfMail;
    }

    public String getFromMail() {
        return fromMail;
    }

    public void setFromMail(String fromMail) {
        this.fromMail = fromMail;
    }

    public String getSubjectMail() {
        return subjectMail;
    }

    public void setSubjectMail(String subjectMail) {
        this.subjectMail = subjectMail;
    }

    public String getStatusMail() {
        return statusMail;
    }

    public void setStatusMail(String statusMail) {
        this.statusMail = statusMail;
    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    @Override
    public String toString() {
        return "EmailObject{" +
                "id=" + id +
                ", primaryEmail='" + primaryEmail + '\'' +
                ", dateOfMail=" + dateOfMail +
                ", messageID='" + messageID + '\'' +
                ", fromMail='" + fromMail + '\'' +
                ", subjectMail='" + subjectMail + '\'' +
                '}';
    }
}