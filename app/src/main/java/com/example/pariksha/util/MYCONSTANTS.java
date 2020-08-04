package com.example.pariksha.util;

import java.util.Calendar;

/**
 * This Java class contains Contants for the Apps
 **/
public class MYCONSTANTS {

    /**
     * String Constants
     **/
    public static final String DEV_NAME = "Aditi,Apurva";
    public static final String DEV_EMAIL = "aditirajpuja@gmail.com";
    public static final String DEV_MOBILENO = "+91 XXXXXXXXXX";

    public static final String APP_ID = "aditi123";
    public static final String APP_VERSION = "1.0";
    public static final String APP_SPREFERENCE_NAME = "PARIKSHA2020";
    public static final String USER_INFO_KEY = "USER_INFO";
    public static final String QUESPREFIX = "Ques ";

    public static final String URL_TESTLIST = "http://eduthirst.com/mjson/testInfo/testList.json";
    public static final int CODE_TEST_AUTOSUBMIT = 1;
    public static final int CODE_TEST_CANDSUBMIT = 0;
    public static final String GOOGLE_ACC_INFO_KEY = "GOOGLE_ACC_INFO_KEY";
    public static final String FIREBASE_USERPATH = "users-onlintest";
    public static final String KEY_TESTINFO = "KEY_TESTINFO";
    /**
     * Object attribute's default values
     **/
    public static final String NOTATTEMPTED = "NA";
    public static final String RESULT_STATUS_PASS = "PASS";
    public static final String RESULT_STATUS_FAIL = "FAIL";
    public static final long EXTRA_TIME = 12000;
    private static final String TEST_PROGRESS_PREF_NAME = "TEST_PROGRESS";
    /**
     * Server side Constants (FireBase Db)
     **/
    public static final String DB_ROOT_REFERENCE_NAME = "Pariksha2020";
    /**
     * Number Constants
     **/

    public static final int SPLASH_DELAY = 1000;
    public static final String DB_USERREFERENCE_NAME = "UserInfo";
    public static final String DB_TESTQUESTIONS_REFERENCE = "TestQuestions";
    public static final String DB_TESTNOTEBOOK_REFERENCE = "TestQuestions";
    public static final int MARKS_INCORRECT_ATTEMPT = -1;//-(MARKS_CORRECT_ATTEMPT/3);  // specifies marks for incorrect Answer


    public static final int MARKS_UNATTEMPTED = 0;
    public static final int MARKS_CORRECT_ATTEMPT = 3;   // specifies marks for question
    /**
     * TEST DURATION - MINS
     **/

    public static final long TESTDURATION1MIN = 60000;        //  1  Min  = 6000 milliseconds

    public static final int MAX_QUESTION = 180;
    public static final long TIMER_INTERVAL1S = 1000;  // 1sec = 1000 milliseconds
    public static final long TESTDURATION2MIN = 120000;       // 2  Mins = 12000 milliseconds
    public static final int TESTDURATION3MIN = 180000;   // 3  Mins = 180000 milliseconds+bonus
    public static final long TESTDURATION5MIN = 300000;       // 5  Mins = 300000 milliseconds
    /**
     * TEST DURATION - HOUR
     **/
    public static final long TESTDURATION1HOUR = 600000;           // 1Hour = 600000 milliseconds
    public static final long TESTDURATION10MIN = 600000;      // 10 Mins = 600000 milliseconds
    public static final long TESTDURATION12MIN = 7200000;     // 12 Mins = 7200000 milliseconds
    public static final long TESTDURATION15MIN = 9000000;     // 15 Mins = 9000000 milliseconds
    public static final long TESTDURATION20MIN = 12000000;    // 20 Mins = 12000000 milliseconds
    public static final long TESTDURATION30MIN = 18000000;    // 30 Mins = 18000000 milliseconds
    public static final long TESTDURATION45MIN = 27000000;    // 45 Mins = 27000000 milliseconds
    /**
     * TIMER WARNING TIME - MINS
     **/
    public static final long WARNING_TIME1M = 60000;   // 1 Min = 60000 milliseconds
    public static final long TESTDURATION1HOUR30MIN = 600000;      // 1Hour 30Mins = 600000 milliseconds
    public static final long TESTDURATION2HOUR = 600000;           // 2Hours = 600000 milliseconds
    /**
     * Boolean Constants
     **/
    public static final boolean ISAUTOSUBMIT_ON_END_ACTIVE = true;
    public static final long WARNING_TIME2M = 120000;  // 2 Mins = 120000 milliseconds
    public static final long WARNING_TIME3M = 180000;  // 3 Mins = 180000 milliseconds
    public static final long WARNING_TIME5M = 300000;  // 5 Mins = 300000 milliseconds
    public static final boolean ISNEGMARKACTIVE = true;
    /**
     * Dummy Values
     **/


    public static final String MOBILE_NO = "+91 81xxxxxxxxxx";
    /**
     * SharedPreferences Keys
     **/
    private static final String USER_SPREF_NAME = "USER_SPF";
    public static final String LAST_ACTIVE = Calendar.getInstance().getTime()+"";
    public static final String DEVICE_NAME = "MI";
    public static final String NAME = "Aditi Raj";
    public static final String COURSE = "B.TECH";

    public static final String URL_PROFILE_PIC = "https://img";
    public static final String LOGINMODE = "ONLINE";
    public static final String LOGINMODE_OFFLINE = "OFFLINE";
    public static final String LOGINMODE_LOGGEDOUT = "LOGOUT";
    public static final String LOGINMODE_ATTENDINGTEST = "ATTENDINGTEST";
    public static final String USER_TYPE = "CANDIDATE";
    public static final int TESTS_APPEARED = 0;
    public static final String LAST_TEST_DETAILS = "TEST#1";

    /** Dummy values 2 **/
}
