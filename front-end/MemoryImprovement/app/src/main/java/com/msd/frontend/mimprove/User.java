package com.msd.frontend.mimprove;

/**
 * Created by Kiran on 3/23/2016.
 */
public class User {
    public int userId;
    public String userName;
    public String password;


    public void setUserId(int userId){
        this.userId = userId;
    }
    public void setUserName(String userName){
        this.userName = userName;
    }
    public void setPassword(String password){
        this.password = password;
    }

    public int getUserId(){
        return this.userId;
    }
    public String getUserName(){
        return this.userName;
    }
    public String getPassword(){
        return this.password;
    }

    public void logIn(){

    }
    public void logOut(){

    }
    public boolean isGuardian(){
        return false;
    }
    public void uploadPicture(){

    }
    public void createQuestions(){

    }



}
