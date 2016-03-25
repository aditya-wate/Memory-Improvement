package com.msd.frontend.mimprove;

import java.util.Set;

/**
 * Created by Kiran on 3/23/2016.
 */
public class Patient extends User {

    private Set<String> correctAnswers;
    private int patientId;
    private int quizId;
    private PatientInfo patientInfo;

    public void setPatientId(int patientId){
        this.patientId = patientId;
    }
    public void setQuizId(int quizId){
        this.quizId = quizId;
    }
    public int getPatientId(){
        return this.patientId;
    }
    public int getQuizId(){
        return this.quizId;
    }

    public boolean hasGuardian(){
        return true;
    }




}
