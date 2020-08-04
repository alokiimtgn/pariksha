package com.example.pariksha.util;

import android.content.Context;
import android.content.res.Resources;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pariksha.R;
import com.example.pariksha.interfaces.OnExamTimeExhausted;
import com.example.pariksha.models.QuestionPaper;
import com.example.pariksha.models.Solution;
import com.google.gson.Gson;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class CommonMethods {
    private static final String TAG = "CommonMethods";
    public static Solution questionToAnswer(QuestionPaper questionPaper) {
      //  Log.i(TAG,"Question : "+new Gson().toJson(questionPaper));
        return new Solution(questionPaper,"NA",false);
    }

    /**
     * Method to check a character in number , alphabet or special Symbol Toast Message
     **/
    public static int checkCharacterCateg(char c) {
        int categ = 1;
        if (c >= '0' && c <= '9') {
            categ = 1; /** for Number **/
        } else if ((c >= 'A' && c < 'Z') || (c >= 'a' && c < 'z')) {
            categ = 0; /** for Alphabets **/
        } else if (c == ' ') {
            categ = 2; /** for White Space **/
        } else {
            categ = -1; /** for Special Symbol **/
        }
        return categ;
    }

    /**
     * Method for showing Toast Message
     **/
    public static void showToastMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Method for initializing CountDownTimer
     **/
    public static CountDownTimer countDownTimer(final Context context, final Resources resources, CountDownTimer countDownTimer, final long millisInFuture, long tickInterval, final TextView idTvcount) {
        countDownTimer = new CountDownTimer(millisInFuture, tickInterval) {
            @Override
            public void onTick(long millis) {
                idTvcount.setTextColor(resources.getColor(R.color.colorPrimary));
                if(millis%2==0 && millis%3 ==0) {
                    idTvcount.setText("⌚ : " + String.format("%02dH:%02dM:%02ds",
                            TimeUnit.MILLISECONDS.toHours(millis),
                            TimeUnit.MILLISECONDS.toMinutes(millis) -
                                    TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                            TimeUnit.MILLISECONDS.toSeconds(millis) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))));
                }else {
                    idTvcount.setText("⌛ : " + String.format("%02dH:%02dM:%02ds",
                            TimeUnit.MILLISECONDS.toHours(millis),
                            TimeUnit.MILLISECONDS.toMinutes(millis) -
                                    TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                            TimeUnit.MILLISECONDS.toSeconds(millis) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))));
                }
               // Log.i("Time : ", "M " + millis + " " + (millisInFuture - 5));
                if (millis < 60000) {
                    idTvcount.setTextColor(resources.getColor(R.color.text_warning));
                } else {
                    idTvcount.setTextColor(resources.getColor(R.color.colorPrimary));

                }
            }

            @Override
            public void onFinish() {
                // System.exit(0);
                // idTvcount.setText("00H:00M:00s");
                try {
                    OnExamTimeExhausted onExamTimeExhausted = (OnExamTimeExhausted) context;
                    onExamTimeExhausted.onTestAutoSubmit(MYCONSTANTS.CODE_TEST_AUTOSUBMIT); // will call on autosubmission of test
                  //  Log.i(TAG, "Auto submit Interface method invoked...." + MYCONSTANTS.CODE_TEST_AUTOSUBMIT);
                    idTvcount.setText("Time : --H:--M:--s");
                    showToastMessage(context, "Test Submitted Successfully ...");
                }catch (Exception ex){
                    showToastMessage(context,"An Exception has Occured @ timer onFinish() ");
                }
            }
        };
        countDownTimer.start();
        return countDownTimer;
    }

    public static void startCountDownTimer() {

    }

    public static void pauseCounDownTimer() {

    }

    public static void stopCountDownTimer(CountDownTimer countDownTimer) {
      countDownTimer.cancel();
      //Log.i(TAG,"CountDown Timer Stopped...."+countDownTimer.toString());
    }

    public static void resumeCountDownTimer() {

    }


    public static String getFormatedDate() {
        return null;
    }

    public static String getFormatedTime(String tag, int i) {
        return null;
    }

    public static boolean submitTest() {

        return false;
    }


    /**
     * Method to convert from formatted time to milliseconds
     **/
    public static long timeToMilliSeconds(String timeString) {
        long hh = Integer.parseInt(timeString.split(":")[0]);    //*60*60*1000;
        long mm = Integer.parseInt(timeString.split(":")[1]);    //*60*1000;
        long ss = Integer.parseInt(timeString.split(":")[2]);    //*1000;
      //  Log.i("Time Conversion : ", " TimeString : " + timeString + "\nHour : " + hh + "\nMin : " + mm + "\nSec : " + ss + "\nTotal in Millis : " + ((hh * 60 * 60 * 1000) + (mm * 60 * 1000) + (ss * 1000)));
        return (hh * 60 * 60 * 1000) + (mm * 60 * 1000) + (ss * 1000);
    }

    /**
     * Method to convert from milliseconds to formatted time
     * Needs more testings
     **/
    public static String millisToFormattedTime(long timeInMillis) {
        long totalTimeInSec = timeInMillis / 1000;
       // Log.i("FT : ", "Total Seconds : " + totalTimeInSec + "");
        long ss = totalTimeInSec % 60;
        //Log.i("FT : ", "SS : " + ss + "");
        long hh = totalTimeInSec / 3600;
        //Log.i("FT : ", "Hour : " + hh);
        long mm = totalTimeInSec % 3600;
        if (mm > 60) {
            mm = mm / 60;
        }
        //Log.i("FT : ", "Min : " + mm);
        //Log.i("FT : ", "Time : " + hh + "H:" + mm + "M:" + ss + "S");
        return hh + ":" + mm + ":" + ss;
    }

    /**
     * Method to generate Random Number
     **/
    public int generateRandomNumber() {
        Random random = new Random();
        int randomNumber = random.nextInt();
        return randomNumber;
    }

    /**
     * Method to generate Random Number till a given Range
     **/
    public int generateRandomNumber(int max) {
        Random random = new Random();
        int randomNumber = random.nextInt(max);
        return randomNumber;
    }
}
