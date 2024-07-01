package com.rahgozin.appointment.application;

import lombok.experimental.UtilityClass;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@UtilityClass
public class Dateutil {
    public Integer getActionDate(Date date){
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        String format = df.format(date);
        return Integer.valueOf(format);
    }

}
