package com.incentro.sa.models;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
@Cache
public class UsersInfo {

    @Id
    private Long id;

    @Index
    private String userId;



    @Index
    private int negative = 0;
    @Index
    private int positive = 0;
    @Index
    private int slighty_negative = 0;
    @Index
    private int slighty_positive = 0;
    @Index
    private int extremely_negative = 0;
    @Index
    private int extremely_postive = 0;
    @Index
    private int neutral = 0;
    @Index
    private int unknown = 0;

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

    public int getNegative() {
        return negative;
    }

    public int getAllNegative() {
        return negative + extremely_negative + slighty_negative;
    }
    public int getAllPositive() {
        return positive + extremely_postive + slighty_positive;
    }

    public void setNegative(int negative) {
        this.negative = negative;
    }
    public void addNegative()
    {
        this.negative ++;
    }

    public int getPositive() {
        return positive;
    }

    public void setPositive(int positive) {
        this.positive = positive;
    }
    public void addPositive()
    {
        this.positive ++;
    }

    public int getSlighty_negative() {
        return slighty_negative;
    }

    public void setSlighty_negative(int slighty_negative) {
        this.slighty_negative = slighty_negative;
    }
    public void addSNegative()
    {
        this.slighty_negative ++;
    }

    public int getSlighty_positive() {
        return slighty_positive;
    }
    public void addSPositive()
    {
        this.slighty_positive ++;
    }

    public void setSlighty_positive(int slighty_positive) {
        this.slighty_positive = slighty_positive;
    }

    public int getExtremely_negative() {
        return extremely_negative;
    }

    public void setExtremely_negative(int extremely_negative) {
        this.extremely_negative = extremely_negative;
    }
    public void addENegative()
    {
        this.extremely_negative ++;
    }

    public int getExtremely_postive() {
        return extremely_postive;
    }

    public void setExtremely_postive(int extremely_postive) {
        this.extremely_postive = extremely_postive;
    }
    public void addEPositive()
    {
        this.extremely_postive ++;
    }

    public int getNeutral() {
        return neutral;
    }

    public void setNeutral(int neutral) {
        this.neutral = neutral;
    }
    public void addNeutral()
    {
        this.neutral ++;
    }

    public int getUnknown() {
        return unknown;
    }

    public void setUnknown(int unknown) {
        this.unknown = unknown;
    }
    public void addUnknown()
    {
        this.unknown ++;
    }

    @Override
    public String toString() {
        return "UsersInfo{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", negative='" + negative + '\'' +
                ", positive='" + positive + '\'' +
                ", slighty_negative='" + slighty_negative + '\'' +
                ", slighty_positive='" + slighty_positive + '\'' +
                ", extremely_negative='" + extremely_negative + '\'' +
                ", extremely_postive='" + extremely_postive + '\'' +
                ", neutral='" + neutral + '\'' +
                ", unknown='" + unknown + '\'' +
                '}';
    }

}