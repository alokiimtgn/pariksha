package com.example.pariksha.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.pariksha.activities.AuthenticationActivity;
import com.example.pariksha.models.User;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.gson.Gson;

import java.util.Random;

public class FireBaseMethods {
    static Gson gson = new Gson();

    public static void logOut(GoogleSignInAccount account, GoogleSignInClient mGoogleSignInClient, final Context context) {
        account = null;
        final GoogleSignInAccount finalAccount = account;
        mGoogleSignInClient.signOut()
                .addOnCompleteListener((Activity) context, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent = new Intent(context, AuthenticationActivity.class);
                        intent.putExtra("account", gson.toJson(finalAccount));
                        context.startActivity(intent);
                        ((Activity) context).finish();
                    }
                });

        //  updateUI(account, st);
    }


    public static void createUserRecordInFbaseDb(DatabaseReference databaseReference, GoogleSignInAccount account, User user) {
        Random random = new Random();
        databaseReference.child(account.getEmail().split("@")[0]).setValue(user);
    }

    public static void createUserRecordInFbaseDb(final DatabaseReference databaseReference, final User user) {
        // Random random = new Random();
        // databaseReference.child(user.getEmail().split("@")[0]).removeValue();
//        DatabaseReference fbdb = databaseReference.child(user.getEmail().split("@")[0]);
//
//        fbdb.addValueEventListener(new ValueEventListener() {
//         @Override
//         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//         if (dataSnapshot.exists()){
//             // Log.i("DBSHOT",dataSnapshot+"");
//             User user1 = dataSnapshot.getValue(User.class);
//             Log.i("FBUSER ",user1.getCollegeName());
//             Log.i("FBUSER C ",user1.getCourse());
//             ArrayList<TestResult> testResultArrayList = user1.getReportCard();
//             Log.i("TRAL", String.valueOf(testResultArrayList.size())+"");
//             if(user1.getReportCard().size()<user.getReportCard().size()){
        databaseReference.child(user.getEmail().split("@")[0]).setValue(user);
        //        }else {

        //      }
        //}
//         }
//
//         @Override
//         public void onCancelled(@NonNull DatabaseError databaseError) {
//
//         }
//     });


        //   databaseReference.child(user.getEmail().split("@")[0]).setValue(user);
    }

    public static DatabaseReference getUserRecordInFbaseDb(DatabaseReference databaseReference, User user) {
        // Random random = new Random();
        //databaseReference.child(user.getEmail().split("@")[0]).removeValue();
        return databaseReference.child(user.getEmail().split("@")[0]).getRef();
    }
}
