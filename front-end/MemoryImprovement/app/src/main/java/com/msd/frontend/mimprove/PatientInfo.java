package com.msd.frontend.mimprove;

import java.util.List;

/**
 * Created by Kiran on 3/23/2016.
 */
public class PatientInfo {

    //public List<BufferedImage> images;
      private List<CustomInfo> customInfoList;

    public CustomInfo getRandomInfo(){
        return customInfoList.get(0);
    }
    public void addCustomInfo(){

    }
    public boolean editCustomInfo(){
        return true;
    }

}
