package com.example.demo.bean;

import java.util.Date;

public class Emp1 {
    private int id;
    private String ename;
    private String pwd;
    private String sex;
    private int age;
    private String idcard;

    @Override
    public String toString() {
        return "Emp1{" + "id=" + id + ", ename='" + ename + '\'' + ", pwd='" + pwd + '\'' + ", sex='" + sex + '\'' + ", age=" + age + ", idcard='" + idcard + '\'' + ", tel='" + tel + '\'' + ", birth=" + birth + ", face='" + face + '\'' + ", did=" + did + '}';
    }

    private String tel;
    private Date birth;
    private String face;
    private int did;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
    }
}
