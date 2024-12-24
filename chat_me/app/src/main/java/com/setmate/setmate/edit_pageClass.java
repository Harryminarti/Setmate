package com.setmate.setmate;

import androidx.annotation.Keep;

@Keep

public class edit_pageClass {

    private  String name,age,bio,imageLink,user_id,gender;
    private int user_no;

    public edit_pageClass() {
    }

    public edit_pageClass(int user_no) {
        this.user_no = user_no;
    }

    public edit_pageClass(String name, String age, String bio, String imageLink, String user_id, int user_no,String gender) {
        this.name = name;
        this.age = age;
        this.bio = bio;
        this.imageLink = imageLink;
        this.user_id = user_id;
        this.gender = gender;
        this.user_no = user_no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getUser_no() {
        return user_no;
    }

    public void setUser_no(int user_no) {
        this.user_no = user_no;
    }
}
