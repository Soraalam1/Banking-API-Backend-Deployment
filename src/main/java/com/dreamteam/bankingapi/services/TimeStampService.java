package com.dreamteam.bankingapi.services;



import com.dreamteam.bankingapi.models.Bill;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeStampService {

     public static String createTimeStamp(){
        String pattern = "yyyy-MM-dd";

        DateFormat df = new SimpleDateFormat(pattern);

        Date today = Calendar.getInstance().getTime();
        String timeStamp = df.format(today);
        return timeStamp;
    }

    public static String createUpcomingDate(Bill bill){
        String pattern = "yyyy-MM-dd";

        DateFormat df = new SimpleDateFormat(pattern);

        Date today = Calendar.getInstance().getTime();
        if(today.getMonth() < 11){
            today.setMonth(today.getMonth() + 1);
        }else{
            today.setYear(today.getYear() + 1901);
            today.setMonth(Calendar.JANUARY);
        }
        if(bill.getRecurringDate() < today.getDate()){
            today.setDate(bill.getRecurringDate());
        }
        String timeStamp = df.format(today);
        return timeStamp;
    }

    public static void initializeUpcomingDate(Bill bill){
        Date today = Calendar.getInstance().getTime();
        if(today.getDate() > bill.getRecurringDate()){
            bill.setUpcomingPaymentDate(TimeStampService.createUpcomingDate(bill));
        }else{
            bill.setUpcomingPaymentDate(""+ (today.getYear() + 1900)+ "-" + (today.getMonth()+1) + "-" + bill.getRecurringDate() +"");
        }
    }

}
