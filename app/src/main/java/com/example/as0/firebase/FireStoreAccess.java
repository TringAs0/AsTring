package com.example.as0.firebase;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.as0.MainActivity;
import com.example.as0.R;
import com.example.as0.VActivity;
import com.example.as0.login.LoginActivity;
import com.example.as0.user.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FireStoreAccess
{
    private static FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private static FirebaseUser firebaseUser;

    private static User user = User.getInstance();
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static class Auth
    {
        private static final String TAG = "TAG_AUTH";

        public static void login (Context context, PhoneAuthCredential credential)
        {
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {
                                firebaseUser = task.getResult().getUser();

                                context.startActivity(new Intent(context, VActivity.class).putExtra("user", firebaseUser.getPhoneNumber()));
                            }
                            else
                            {
                                if (task.getException() instanceof FirebaseAuthInvalidCredentialsException)
                                {
                                }
                            }
                        }
                    });
        }





        public static void signUpOrNot(Context context, String phoneNumber)
        {
            db.collection("DATA/User/UserList")
                    .document(phoneNumber)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task)
                        {
                            if (task.isSuccessful())
                            {
                                if (task.getResult().exists())
                                {

                                }
                                else
                                {

                                }
                            }
                        }
                    });
        }

        public static void checkNickName(Context context, String nickName, final FireStoreCallback callback)
        {
            DocumentReference documentReference = db.collection("DATA/User/NickNameList").document(nickName);

            documentReference
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task)
                        {
                            if (task.isSuccessful())
                            {
                                if (task.getResult().exists())
                                {
                                    Toast.makeText(context, "존재하는 닉네임입니다!", Toast.LENGTH_SHORT).show();
                                    callback.callback(true);
                                }
                                else
                                {
                                    Toast.makeText(context, "사용 가능한 닉네임입니다!", Toast.LENGTH_SHORT).show();
                                    callback.callback(false);
                                }
                            }
                        }
                    });
        }



    }

    public static class Match
    {

        private static final String TAG = "TAG_AUTH";

        public static void getUserObject(Context context, String userUid, FireStoreCallback<User> callback)
        {
            DocumentReference documentReference = db.collection("DATA/User/UserList").document(userUid);

            documentReference
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task)
                        {
                            if (task.isSuccessful())
                            {
                                if (task.getResult().exists())
                                {
                                    callback.callback(task.getResult().toObject(User.class));
                                }
                                else
                                {
                                    Toast.makeText(context, "Sorry, User deleted", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
        }

        public static void getUserMatchingUidList(Context context, String tripRegion, String tripTag)
        {
            //MatchingUid = Uid_NickName_PhotoUrl

            SharedPreferences sharedPreferencesForMatching=
                    context.getSharedPreferences(context.getResources().getString(R.string.SP_MATCHING_ALGO_UID_LIST),context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferencesForMatching.edit();

            //Set<String> matchingAlgoUidSet;
            //Set<String> matchingAlgoUidSet_Temp= new HashSet<>();

            DocumentReference documentReference = db.collection("DATA/MatchingAlgo/"+tripRegion+"/").document(tripTag);

            documentReference
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task)
                        {
                            if (task.isSuccessful())
                            {
                                if (task.getResult().exists())
                                {
                                    List<String> list =   (List<String>)task.getResult().get("MatchingAlgoUidList");
                                    Set<String> matchingAlgoUidSet= new HashSet<>(list);

                                    Log.d("here", list.toString());

                                    if (!sharedPreferencesForMatching.contains("MatchingAlgoUidList"))
                                    {
                                        editor.putStringSet("MatchingAlgoUidList", matchingAlgoUidSet);
                                        editor.commit();
                                        editor.apply();
                                    }
                                    else
                                    {
                                        editor.remove("MatchingAlgoUidList");
                                        editor.putStringSet("MatchingAlgoUidList", matchingAlgoUidSet);
                                        editor.commit();
                                        editor.apply();
                                    }

                                }
                                else
                                {
                                }
                            }
                            else
                            {
                            }
                        }
                    });
        }
    }
}

//1. how can B's info be uploaded to A's app?
//1) login -> get user list according to algo
//2) what factors I need to show up user list?
// 2-1) Nickname, Photo url,,, -> it mean i need an uid of other user
// 2-2) app get uid set on specific list and get again user object with its uid
//3) every time, I get user list? -> it maybe lower performance so I should at least minimize 2-2)
// 3-1) get user uid list and save it at SharedPreferences, so whenever I change my specific factor about matching algo, app get user uid list
// 3-2) To show up user matching list, app get NickName, PhotoUrl,...with uid
// 3-2) so when A user open chat with B -> app get all info with uid of B in SharedPreferences.