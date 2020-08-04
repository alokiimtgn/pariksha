package com.example.pariksha.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.pariksha.R;
import com.example.pariksha.interfaces.OnExamTimeExhausted;
import com.example.pariksha.models.QuestResponse;
import com.example.pariksha.models.QuestionPaper;
import com.example.pariksha.models.Solution;
import com.example.pariksha.models.Test;
import com.example.pariksha.models.TestResult;
import com.example.pariksha.models.User;
import com.example.pariksha.util.CommonMethods;
import com.example.pariksha.util.FireBaseMethods;
import com.example.pariksha.util.MYCONSTANTS;
import com.example.pariksha.util.MyPreferences;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.pariksha.util.MYCONSTANTS.APP_SPREFERENCE_NAME;
import static com.example.pariksha.util.MYCONSTANTS.USER_INFO_KEY;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnExamTimeExhausted {
    /**
     * Declaring the Views
     **/
    private AlertDialog.Builder builder;
    private ArrayList<QuestionPaper> questionArrayList;
    private ArrayList<Solution> solutionArrayList;
    private Button btnActionPrev, btnActionNext;
    private CardView idCdTestInfo, idCdVControl, idCdVCandidateRes, idCdVScoreCard, idTviewScoreCard2;
    private CountDownTimer countDownTimer;
    private Dialog profileDialog, instructionDialog;
    private CircleImageView idImvProfile;
    private CircleImageView idImvInfo;
    //private CircleImageView idImdProfpic;
    private ProgressBar idPbTestProgress;
    private RadioGroup idRadioGroup;
    private RadioButton idRbOpt1, idRbOpt2, idRbOpt3, idRbOpt4;
    private TableLayout idLabelTable, idTableResult;
    private TextView idTvQuestion, idTextViewName, idTviewTestDate, idTvTestTime, idTvFullMarks, idTviewTestId, idTvTestId, id_TvtestQs, idTvcount, idTvResultSummary, idTvResultSummary2;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    /**
     * Declaring the Variables
     **/

    private boolean isTestSubmitted;
    private int counter = 0;
    private int countSelectedA = 0, countSelectedB = 0, countSelectedC = 0, countSelectedD = 0, notAttempted = 0, totalCorrect = 0, totalIncorrect = 0, totalScore = 0, totalUnAttempted = 0, examfullMarks = 0;
    private static final String TAG = "MainActivity";
    private Bundle bundle;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference(MYCONSTANTS.FIREBASE_USERPATH);
            /** Initializing the View with their Ids **/
            builder = new AlertDialog.Builder(this);


            sharedPreferences = getSharedPreferences(APP_SPREFERENCE_NAME, MODE_PRIVATE);
            editor = sharedPreferences.edit();


            /** Initializing the CardView with their Ids **/
            idCdTestInfo = findViewById(R.id.idCdTestInfo);
            idCdVControl = findViewById(R.id.idCdVControl);
            idCdVCandidateRes = findViewById(R.id.idCdVCandidateRes);
            idCdVScoreCard = findViewById(R.id.idCdVScoreCard);
            idTviewScoreCard2 = findViewById(R.id.idTviewScoreCard2);

            /** Initializing the TableLayout with their Ids **/

            idLabelTable = findViewById(R.id.idLabelTable);
            idTableResult = findViewById(R.id.idTableResult);

            /** Initializing the TextView  with their Ids **/
            idTvcount = findViewById(R.id.idTvcount);
            idTviewTestId = findViewById(R.id.idTviewTestId);
            idTvResultSummary = findViewById(R.id.idTvResultSummary);
            idTvResultSummary2 = findViewById(R.id.idTvResultSummary2);
            idTvQuestion = findViewById(R.id.idTvQuestion);
            idTextViewName = findViewById(R.id.idTextViewName);
            idTviewTestDate = findViewById(R.id.idTviewTestDate);
            /** Initializing the ImageView with their Ids **/
            idImvInfo = findViewById(R.id.idImvInfo);
            idImvProfile = findViewById(R.id.idImvProfile);
            idPbTestProgress = findViewById(R.id.idPbTestProgress);
            idTvTestTime = findViewById(R.id.idTvTestTime);
            idTvFullMarks = findViewById(R.id.idTvFullMarks);
            idTvTestId = findViewById(R.id.idTvTestId);
            idTviewTestId = findViewById(R.id.idTviewTestId);
            id_TvtestQs = findViewById(R.id.id_TvtestQs);

            /** Initializing the Options Radio Group **/
            idRadioGroup = findViewById(R.id.idRadioGroup);

            /** Initializing the Options Radio Buttons **/
            idRbOpt1 = idRadioGroup.findViewById(R.id.idRbOpt1);
            idRbOpt2 = idRadioGroup.findViewById(R.id.idRbOpt2);
            idRbOpt3 = idRadioGroup.findViewById(R.id.idRbOpt3);
            idRbOpt4 = idRadioGroup.findViewById(R.id.idRbOpt4);

            /** Initializing the Question Navigation Button **/
            btnActionPrev = findViewById(R.id.btnActionPrev);
            btnActionNext = findViewById(R.id.btnActionNext);

            /** Initializing the Dialog **/
            profileDialog = new Dialog(this);
            instructionDialog = new Dialog(this);

            /** Initializing the ArrayList **/
            questionArrayList = new ArrayList<QuestionPaper>();
            solutionArrayList = new ArrayList<Solution>();
            bundle = getIntent().getExtras();
            try {
                if (idImvProfile != null) {
                    Picasso.get()
                            .load(MyPreferences.getLoginInfo(sharedPreferences, USER_INFO_KEY).getUrl_profile_pic())
                            .placeholder(R.drawable.person_orange)
                            .error(R.drawable.person_orange)
                            .into(idImvProfile);
                }
            } catch (Exception ex) {
               // Log.i(TAG, "An Exception occurred while loading images : " + ex.getMessage());
            }
            if (bundle != null) {
               // Log.i(TAG, new Gson().toJson(bundle.getString("KEY_TESTINFO")));
                if ((new Gson().fromJson(bundle.getString("KEY_TESTINFO"), Test.class) != null) && (new Gson().fromJson(bundle.getString("KEY_TESTINFO"), Test.class).getUrl() != null)) {
                    callApi(new Gson().fromJson(bundle.getString("KEY_TESTINFO"), Test.class).getUrl()); /** Will Pass url from test object**/
                    //countDownTimer = startExamTimer(MYCONSTANTS.TESTDURATION1MIN, MYCONSTANTS.TIMER_INTERVAL1S, idTvcount); /** Will Start the Exam Timer **/
                    //  callQuesToAnswer(); /** This method will convert Question in Answering mode **/
                }
            } else {
                //Log.i(TAG, "Bundle was Null");
            }
            /** Initializing the OnClickListeners **/
            idImvInfo.setOnClickListener(this);
            idImvProfile.setOnClickListener(this);
            btnActionPrev.setOnClickListener(this);
            btnActionNext.setOnClickListener(this);

            /** Initializing the boolean variable  **/
            isTestSubmitted = false;

            idRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (group.getCheckedRadioButtonId()) {

                        case R.id.idRbOpt1:     // Operations to be performed when Option A is Selected
                            //Log.i(TAG, "@ onCheckedChanged : " + counter);
                            //Log.i(TAG, "Before Modify :  " + new Gson().toJson(solutionArrayList.get(counter)));
                            if (idRbOpt1.isChecked()) {
                                selectOption(true, "A", counter);
                            }
                            //Log.i(TAG, "After Modify :  " + new Gson().toJson(solutionArrayList.get(counter)));
                            //Log.i(TAG, checkedId + "...." + group.getCheckedRadioButtonId() + "  " + (checkedId == group.getCheckedRadioButtonId()));
                            break;
                        case R.id.idRbOpt2:     // Operations to be performed when Option B is Selected
                            //Log.i(TAG, "Before Modify :  " + new Gson().toJson(solutionArrayList.get(counter)));
                            if (idRbOpt2.isChecked()) {
                                selectOption(true, "B", counter);
                            }
                            //Log.i(TAG, "After Modify :  " + new Gson().toJson(solutionArrayList.get(counter)));
                            //Log.i(TAG, checkedId + "...." + group.getCheckedRadioButtonId() + "  " + (checkedId == group.getCheckedRadioButtonId()));
                            break;
                        case R.id.idRbOpt3:     // Operations to be performed when Option C is Selected
                            //Log.i(TAG, "Before Modify :  " + new Gson().toJson(solutionArrayList.get(counter)));
                            if (idRbOpt3.isChecked()) {
                                selectOption(true, "C", counter);
                            }
                            //Log.i(TAG, "After Modify :  " + new Gson().toJson(solutionArrayList.get(counter)));
                            //Log.i(TAG, checkedId + "...." + group.getCheckedRadioButtonId() + "  " + (checkedId == group.getCheckedRadioButtonId()));
                            break;
                        case R.id.idRbOpt4:     // Operations to be performed when Option D is Selected
                            //Log.i(TAG, "Before Modify :  " + new Gson().toJson(solutionArrayList.get(counter)));
                            if (idRbOpt4.isChecked()) {
                                selectOption(true, "D", counter);
                            }
                            //Log.i(TAG, "After Modify :  " + new Gson().toJson(solutionArrayList.get(counter)));
                            //Log.i(TAG, checkedId + "...." + group.getCheckedRadioButtonId() + "  " + (checkedId == group.getCheckedRadioButtonId()));
                            break;
                    }
                }
            });

            idTextViewName.setText(MyPreferences.getLoginInfo(sharedPreferences, USER_INFO_KEY).getName());
        } catch (Exception ex) {
            showToastMessage("Exception @ onCreate() : " + ex.getMessage());
        }
    }

    private CountDownTimer startExamTimer(long testDuration, long tickInterval, TextView textViewId) {
        //   Code to start exam timer
        CountDownTimer countDownTimer1 = null;
        try {
            countDownTimer1 = CommonMethods.countDownTimer(this, getResources(), countDownTimer, testDuration + MYCONSTANTS.EXTRA_TIME, tickInterval, textViewId);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                idPbTestProgress.setProgress(1, true);
            }
        } catch (Exception ex) {
            showToastMessage("Exception @ startExamTimer(" + this + "," + getResources() + countDownTimer + "," + testDuration + "," + tickInterval + "," + textViewId + ")");
        }
        return countDownTimer1;
    }

    private void createQuestionPaper(QuestResponse questResponse) {
        //   Loads the questionPaper from available Sources
        try {
            if (questResponse != null && questionArrayList != null) {

                questionArrayList = questResponse.getQuestionPaper();
                //Log.i(TAG, "Before Shuffling : " + new Gson().toJson(questionArrayList));
                // Collections.shuffle(questionArrayList);
                //Log.i(TAG, "After shuffling : " + new Gson().toJson(questionArrayList));
                callQuesToAnswer();
                countDownTimer = startExamTimer(questionArrayList.size() * MYCONSTANTS.TESTDURATION3MIN, MYCONSTANTS.TIMER_INTERVAL1S, idTvcount); /** Will Start the Exam Timer **/

            }
        } catch (Exception ex) {
            showToastMessage("Exception @ createQuestionPaper() : " + ex.getMessage());
        }
    }

    @Override
    public void onBackPressed() {
        try {
            if (!isTestSubmitted) {
                builder.setMessage("Are you sure want to Exit without completing ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                CommonMethods.stopCountDownTimer(countDownTimer);
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                                Toast.makeText(getApplicationContext(), "Exit Operation Cancelled..",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.setTitle("Confirm Exit Test ");
                alert.show();
            } else {
                finish();
            }
        } catch (Exception ex) {
            showToastMessage("Exception @ onBackPressed() : " + ex.getMessage());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnActionPrev:
                try {
                    if (counter > 0) {
                        setData(--counter);
                        updateProgressBar(counter);
                        modifyActionButtons(counter);
                        modifyNextButton(counter);
                    }
                } catch (Exception ex) {
                    showToastMessage("Exception @ onClick Prev Button : " + ex.getMessage());
                }
                break;

            case R.id.btnActionNext:
                try {
                    if (counter < questionArrayList.size() - 1) {
                        setData(++counter);
                        updateProgressBar(counter);
                        modifyActionButtons(counter);
                        modifyNextButton(counter);
                    } else {
                        submitTest(MYCONSTANTS.CODE_TEST_CANDSUBMIT);
                    }
                } catch (Exception ex) {
                    showToastMessage("Exception @ onClick Next/Submit Button : " + ex.getMessage());
                }
                break;


            case R.id.idImvInfo:
                try {
                    showInstructionDialog();
                } catch (Exception ex) {
                    showToastMessage("Exception @ onClick Info Icon  : " + ex.getMessage());
                }
                break;
            case R.id.idImvProfile:
                try {
                    showProfileDialog();
                } catch (Exception ex) {
                    showToastMessage("Exception @ onClick profile Icon : " + ex.getMessage());
                }
                break;
        }
    }

    private void modifyActionButtons(int counter) {
        try {
            if (counter == 0) {
                btnActionPrev.setVisibility(View.GONE);
                //Log.i("Counter Next  ", counter + " Previous Button is now Invisible...");
            } else if (counter == 1) {
                btnActionPrev.setVisibility(View.VISIBLE);
                //Log.i("Counter Next  ", counter + " Previous Button is now visible...");
            }
        } catch (Exception ex) {
            showToastMessage("Exception @ modifyActionButtons : (" + counter + ") : " + ex.getMessage());
        }
    }

    private void updateProgressBar(int counter) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                idPbTestProgress.setProgress(counter, true);
            }
        } catch (Exception ex) {
            showToastMessage("Exception @ updateProgressBar : (" + counter + "): " + ex.getMessage());
        }
    }


    private void setData(int counter) {
        try {
            idRadioGroup.clearCheck();
            id_TvtestQs.setText((counter + 1) + " out of " + solutionArrayList.size());
            idRbOpt1.setChecked(false);
            idRbOpt2.setChecked(false);
            idRbOpt3.setChecked(false);
            idRbOpt4.setChecked(false);
            //Log.i("@ setData : Counter " + counter, "SolutionObj : " + new Gson().toJson(solutionArrayList.get(counter)));
            if (solutionArrayList.get(counter).getQuestionPaper().getQuestion() != null) {
                idTvQuestion.setText(MYCONSTANTS.QUESPREFIX + solutionArrayList.get(counter).getQuestionPaper().getQuesNo() + ". " + solutionArrayList.get(counter).getQuestionPaper().getQuestion());
                //   idTvQuestion.setText(MYCONSTANTS.QUESPREFIX +(counter+1) + ". " + solutionArrayList.get(counter).getQuestionPaper().getQuestion());
            } else {
                idTvQuestion.setText(MYCONSTANTS.QUESPREFIX + solutionArrayList.get(counter).getQuestionPaper().getQuesNo() + ". Null value encountered.... ");
                //    idTvQuestion.setText(MYCONSTANTS.QUESPREFIX + (counter+1) + ". Null value encountered.... ");
            }

            if (solutionArrayList.get(counter).getSelectedOption().equalsIgnoreCase("A")) {
              //  Log.i(TAG, "A Check");
                idRbOpt1.setChecked(true);
            } else {
                //Log.i(TAG, "A Not Check");
                idRbOpt1.setChecked(false);
            }

            if (solutionArrayList.get(counter).getSelectedOption().equalsIgnoreCase("B")) {
                idRbOpt2.setChecked(true);
                //Log.i(TAG, "B Check");
            } else {
                idRbOpt2.setChecked(false);
                //Log.i(TAG, "B not Check");
            }

            if (solutionArrayList.get(counter).getSelectedOption().equalsIgnoreCase("C")) {
                idRbOpt3.setChecked(true);
                //Log.i(TAG, "C Check");
            } else {
                //Log.i(TAG, "C not Check");
                idRbOpt3.setChecked(false);
            }

            if (solutionArrayList.get(counter).getSelectedOption().equalsIgnoreCase("D")) {
                idRbOpt4.setChecked(true);
                //Log.i(TAG, "D Check");
            } else {
                //Log.i(TAG, "D not Check");
                idRbOpt4.setChecked(false);
            }
            if (solutionArrayList.get(counter).getQuestionPaper().getFirstOption() != null) {
                idRbOpt1.setText("A. " + solutionArrayList.get(counter).getQuestionPaper().getFirstOption());
            }
            if (solutionArrayList.get(counter).getQuestionPaper().getSecondOption() != null) {
                idRbOpt2.setText("B. " + solutionArrayList.get(counter).getQuestionPaper().getSecondOption());
            }
            if (solutionArrayList.get(counter).getQuestionPaper().getThirdOption() != null) {
                idRbOpt3.setText("C. " + solutionArrayList.get(counter).getQuestionPaper().getThirdOption());
            }
            if (solutionArrayList.get(counter).getQuestionPaper().getFourthOption() != null) {
                idRbOpt4.setText("D. " + solutionArrayList.get(counter).getQuestionPaper().getFourthOption());
            }
        } catch (Exception ex) {
            showToastMessage("An Exception @ setData(" + counter + ") : " + ex.getMessage());
            //Log.i(TAG, "Set Data" + counter);
        }
    }

    private void modifyNextButton(int counter) {
        try {
            if (counter == questionArrayList.size() - 1) {
                btnActionNext.setText("Submit Test");
                btnActionNext.setPadding(15, 0, 15, 0);
                btnActionNext.setTextColor(Color.WHITE);
            } else if (counter < (questionArrayList.size() - 1)) {
                btnActionNext.setText("Next");
            }
        } catch (Exception ex) {
            showToastMessage("An Exception @ modifyNextButton(" + counter + ")" + ex.getMessage());
        }
    }

    private void submitTest(int status) {
        try {
            String msg = " ";
            CommonMethods.stopCountDownTimer(countDownTimer);
            idCdTestInfo.setVisibility(View.GONE);
            idPbTestProgress.setVisibility(View.GONE);
            // idTvcount.setVisibility(View.GONE);
            idCdVControl.setVisibility(View.GONE);
            idCdVCandidateRes.setVisibility(View.GONE);
            idLabelTable.setVisibility(View.VISIBLE);
            idCdVScoreCard.setVisibility(View.VISIBLE);
            idTviewScoreCard2.setVisibility(View.VISIBLE);
            if (status == 0) {
                msg = "Test was Submitted Successfully";
                CommonMethods.stopCountDownTimer(countDownTimer);
            } else {
                msg = "Test was Submitted Successfully";
            }
            showToastMessage(msg);
            btnActionNext.setVisibility(View.GONE);
            btnActionPrev.setVisibility(View.GONE);
            idTvQuestion.setVisibility(View.GONE);
            idRbOpt1.setVisibility(View.GONE);
            idRbOpt2.setVisibility(View.GONE);
            idRbOpt3.setVisibility(View.GONE);
            idRbOpt4.setVisibility(View.GONE);
            // evaluateCandidateResponse(); /** Will Evaluate the responses submitted by the Candidate & Generate the final Result**/
            showDetailedResult(solutionArrayList);
            isTestSubmitted = true;

            TestResult testResult = new TestResult();

            testResult.setTestId(new Gson().fromJson((String) bundle.get(MYCONSTANTS.KEY_TESTINFO), Test.class).getTestID());
            testResult.setTestName(new Gson().fromJson((String) bundle.get(MYCONSTANTS.KEY_TESTINFO), Test.class).getHeading());
            // testResult.setHeldOn(new Gson().fromJson((String) bundle.get(MYCONSTANTS.KEY_TESTINFO),Test.class).getHeading()"15/05/2020");
            testResult.setTotalMarks(totalScore);
            testResult.setTotalIncorrect(totalIncorrect);
            testResult.setTotalCorrect(totalCorrect);
            testResult.setTotalNegativeMarks(totalIncorrect * MYCONSTANTS.MARKS_INCORRECT_ATTEMPT);
            testResult.setTotalPositiveMarks(totalCorrect * MYCONSTANTS.MARKS_CORRECT_ATTEMPT);
            testResult.setTotalNoOfQuestion(solutionArrayList.size());
            testResult.setTotalUnattempted(totalUnAttempted);
            float percentage = ((float) totalScore / (testResult.getTotalNoOfQuestion() * MYCONSTANTS.MARKS_CORRECT_ATTEMPT)) * 100;
            testResult.setPercentage(percentage + "%");
            if (percentage < 50) {
                testResult.setResultStatus(MYCONSTANTS.RESULT_STATUS_FAIL);
            } else {
                testResult.setResultStatus(MYCONSTANTS.RESULT_STATUS_PASS);
            }
            Calendar calendar = Calendar.getInstance();
            idTviewTestId.setText("Test Id : " + testResult.getTestId());
            testResult.setHeldOn(calendar.getTime() + "");
            //Log.i("DATE", calendar.getTime() + "");
            User user = MyPreferences.getLoginInfo(sharedPreferences, MYCONSTANTS.USER_INFO_KEY);
            ArrayList<TestResult> testResultArrayList = user.getReportCard();
            testResultArrayList.add(testResult);

            idTviewTestDate.setText(testResult.getHeldOn().substring(0, 19));

            //Collections.sort(testResultArrayList, Collections.reverseOrder());
            user.setReportCard(testResultArrayList);
            MyPreferences.setLoginInfo(editor, MYCONSTANTS.USER_INFO_KEY, user);
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference(MYCONSTANTS.FIREBASE_USERPATH);
            FireBaseMethods.createUserRecordInFbaseDb(databaseReference, user);
            //Log.i(TAG, "TEST RESULT UPLOADED TO THE SERVER");
        } catch (Exception ex) {
            showToastMessage("An Exception @ submitTest(" + status + ")" + ex.getMessage());
        }
    }


    private void showProfileDialog() {
        try {
            profileDialog.setContentView(R.layout.dialog_profile);
            profileDialog.setTitle("Title...");
            profileDialog.show();
            TextView idTvName = profileDialog.findViewById(R.id.idTvName);
            TextView idTvEmail = profileDialog.findViewById(R.id.idTvEmail);
            TextView id_TvCourseBranch = profileDialog.findViewById(R.id.id_TvCourseBranch);
            TextView idTvMobileNo = profileDialog.findViewById(R.id.idTvMobileNo);
            TextView idTCollegeName = profileDialog.findViewById(R.id.idTCollegeName);
            CircleImageView idImdProfpic = profileDialog.findViewById(R.id.idImdProfpic);
            Button idBtnEditProfile = profileDialog.findViewById(R.id.idBtnEditProfile);
            idTvName.setText("Name : " + MyPreferences.getLoginInfo(sharedPreferences, USER_INFO_KEY).getName());
            idTvEmail.setText("Email : " + MyPreferences.getLoginInfo(sharedPreferences, USER_INFO_KEY).getEmail());
            id_TvCourseBranch.setText("Course : " + MyPreferences.getLoginInfo(sharedPreferences, USER_INFO_KEY).getCourse());
            idTvMobileNo.setText("Mobile : " + MyPreferences.getLoginInfo(sharedPreferences, USER_INFO_KEY).getMobileNo());
            idTCollegeName.setText("College : " + MyPreferences.getLoginInfo(sharedPreferences, USER_INFO_KEY).getCollegeName());
            idBtnEditProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    profileDialog.dismiss();
                }
            });
            try {
                Picasso.get()
                        .load(MyPreferences.getLoginInfo(sharedPreferences, USER_INFO_KEY).getUrl_profile_pic())
                        .placeholder(R.drawable.person_orange)
                        .error(R.drawable.person_orange)
                        .into(idImdProfpic);
            } catch (Exception ex) {
              //  Log.i(TAG, "An error while Loading image : " + ex.getMessage());
            }
        } catch (Exception ex) {
            showToastMessage("An Exception @ showProfileDialog()" + ex.getMessage());
        }
    }

    private void showInstructionDialog() {
        try {
            instructionDialog.setContentView(R.layout.dialog_candidate_instruction);
            instructionDialog.setTitle("Title...");
            instructionDialog.show();
        } catch (Exception ex) {
            showToastMessage("Exception @ showInstructionDialog()");
        }
    }

    private void callQuesToAnswer() {
        try {
            if (solutionArrayList != null) {
                int totalTime = 0;
                for (int i = 0; i < questionArrayList.size(); i++) {
                    solutionArrayList.add(CommonMethods.questionToAnswer(questionArrayList.get(i)));
                    // Log.i(TAG,"Call Ques to Ans : Full Marks "+examfullMarks);

                //    Log.i(TAG, "Total Marks : " + totalTime + "");
                    examfullMarks += solutionArrayList.get(i).getQuestionPaper().getMarks();
                    //Log.i(TAG,"Call Ques to Ans : Full Marks "+solutionArrayList.get(i).getQuestionPaper().getMarks()+"..."+examfullMarks);
                }
                idPbTestProgress.setMax(solutionArrayList.size());
                idTvTestTime.setText("Time : " + solutionArrayList.size() * MYCONSTANTS.MARKS_CORRECT_ATTEMPT + " Minutes ");
                idTvFullMarks.setText("FullMarks : " + examfullMarks);
                setData(counter);
            } else {
                //Log.i(TAG, "Solution Array needs to be initialized");
            }
        } catch (Exception ex) {
            showToastMessage("An Exception @ callQuesToAnswer : " + ex.getMessage());
        }
    }

    private void showToastMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        //Log.i(TAG, message);
    }

    private void selectOption(boolean status, String selectedOption, int counter) {
        try {
          //  Log.i(TAG, "@ selectOption Counter : " + counter);
            Solution solution = solutionArrayList.get(counter);
            solution.setAttempted(status);
            solution.setSelectedOption(selectedOption);
            solutionArrayList.set(counter, solution);
            //Log.i(TAG, "Analysis==================>" + new Gson().toJson(solutionArrayList));
        } catch (Exception ex) {
            showToastMessage("Exception @ selectOption : " + ex.getMessage());
        }
    }

    public void showDetailedResult(ArrayList<Solution> solutionArrayList) {
        try {
            int i = 0;
            for (i = 0; i < solutionArrayList.size(); i++) {
                TableRow tableRow = new TableRow(getApplicationContext());
                tableRow.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                // tableRow.setPadding(10, 5, 0, 5);

                TextView textView = new TextView(getApplicationContext());
                textView.setText(solutionArrayList.get(i).getQuestionPaper().getQuesNo() + ".");
                textView.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                textView.setPadding(15, 25, 0, 5);
                tableRow.addView(textView, 0);

                final TextView textViewSR = new TextView(getApplicationContext());
                textViewSR.setText(solutionArrayList.get(i).getSelectedOption() + "");
                textViewSR.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                textViewSR.setClickable(true);
                final int finalI = i;
                textViewSR.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToastMessage(finalI + 1 + ". Your response was : " + textViewSR.getText().toString());
                    }
                });
                textViewSR.setPadding(5, 25, 0, 5);
                tableRow.addView(textViewSR, 1);


                TextView textViewCR = new TextView(getApplicationContext());
                textViewCR.setText(solutionArrayList.get(i).getQuestionPaper().getCorrectOption() + "");
                textViewCR.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                textViewCR.setPadding(0, 25, 0, 5);
                tableRow.addView(textViewCR, 2);


                TextView textViewStatus = new TextView(getApplicationContext());
                textViewStatus.setText(checkSolutionStatus(solutionArrayList.get(i), textViewStatus));
                textViewStatus.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                textViewStatus.setPadding(15, 25, 0, 5);
                textViewStatus.setTypeface(null, Typeface.BOLD);
                tableRow.addView(textViewStatus, 3);

                TextView textViewScore = new TextView(getApplicationContext());
                textViewScore.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                textViewScore.setPadding(25, 25, 0, 5);
                int marks = assignMarks(solutionArrayList.get(i));
                textViewScore.setText(marks + "");
                tableRow.addView(textViewScore, 4);
                idTableResult.addView(tableRow, i);
            }
            idTvResultSummary.setText(showSummarizedResult());
            idTvResultSummary2.setText("Attempted : " + (totalCorrect + totalIncorrect) + " ✍" +
                    "\nCorrect : " + totalCorrect + " ✔ " + "\nIncorrect : " + totalIncorrect + " ✗ ⊝" + "\nUnAttempted : " + totalUnAttempted + " ✎" +
                    "\nTotal Questions : " + solutionArrayList.size() + ".");
        } catch (Exception ex) {
            Log.i(TAG, ex.getMessage());
        }
    }

    private String showSummarizedResult() {
        //Log.i(TAG, "Full Marks : " + examfullMarks);
        return "Total +ve Marks : " + totalCorrect * 3 + "\nTotal -ve Marks : " + totalIncorrect * (-1) + "\nMarks Obtained : " + totalScore + "/" + (examfullMarks) + "\nPercentage : " + ((float) totalScore / examfullMarks) * 100 + "% .";
    }


    private String checkSolutionStatus(Solution solution, TextView textViewStatus) {
        String status = "NA";
        try {
            if (solution.getSelectedOption() != "NA") {
                if (solution.getQuestionPaper().getCorrectOption().equalsIgnoreCase(solution.getSelectedOption())) {
                    status = "✔";
                    textViewStatus.setTextColor(Color.GREEN);
                } else {
                    status = "✗";
                    textViewStatus.setTextColor(Color.RED);
                }
            }
        } catch (Exception ex) {
            showToastMessage("An Exception @ checkSolutionStatus(" + solution + "," + textViewStatus.toString() + ")");
        }
        return status;
    }

    private int assignMarks(Solution solution) {
        int marks = 0;
        try {
            if (solution.isAttempted()) {
                /** When question was attempted **/
                if (solution.getSelectedOption().equalsIgnoreCase(solution.getQuestionPaper().getCorrectOption())) {
                    /** When Solution was correct **/
                    marks = solution.getQuestionPaper().getMarks();
                    totalScore += marks;
                    totalCorrect++;
          //          Log.i(TAG, "assignMarks @ Total Correct Attempts : " + totalCorrect);
            //        Log.i(TAG, "assignMarks @ Total Score : " + totalScore);
                } else if (solution.getQuestionPaper().isNegMarkActive()) {
                    /**  Marking Logic When negative marking is Active & Solution was Incorrect **/
                    marks = solution.getQuestionPaper().getNegmark();
                    //  marks = solution.getQuestionPaper().getMarks();
                    totalScore += marks;
                    totalIncorrect++;
              //      Log.i(TAG, "assignMarks @ Total Inc Attempts : " + totalIncorrect);
                //    Log.i(TAG, "assignMarks @ Total Score : " + totalScore);
                }
            } else {
                /**  Marking Logic When negative marking is InActive   **/
                marks = 0;
            }
            totalUnAttempted = solutionArrayList.size() - (totalCorrect + totalIncorrect);

            // Log.i(TAG, "assignMarks @ Total Unattempted : " + totalUnAttempted);
        } catch (Exception ex) {
            showToastMessage("An Exception @ assignMarks(" + solution.toString() + ")");
        }
        return marks;
    }

    public void callApi(String testUrl) {
        //Log.i(TAG, "TestUrl : " + testUrl);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest
                jsonObjectRequest
                = new JsonObjectRequest(
                Request.Method.GET,
                testUrl,
                null,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                        try {

                            JSONObject jsonObject = (JSONObject) response;
                            QuestResponse questResponse = new Gson().fromJson(response.toString(), QuestResponse.class);
                //            Log.i(TAG, "Res : " + new Gson().toJson(questResponse));
                            createQuestionPaper(questResponse); /** Will create a question paper with options **/
                            // idTviewTestId.setText("TEST ID : " + questResponse.getTest_id());
                            idTvTestId.setText("TEST ID : " + questResponse.getTest_id());
                        } catch (Exception ex) {
                            showToastMessage(ex.getMessage());
                  //          Log.i(TAG, "Error : " + ex.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i(TAG, "VolleyError : " + new Gson().toJson(error));
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onTestAutoSubmit(int s) {
        submitTest(s);    // will autosubmit
     //   Log.i(TAG, "Test was Auto submitted successfully.... " + s);
    }

    @Override
    protected void onPause() {
        super.onPause();
        submitTest(MYCONSTANTS.CODE_TEST_AUTOSUBMIT);
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }
}


