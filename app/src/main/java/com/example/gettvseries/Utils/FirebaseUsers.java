package com.example.gettvseries.Utils;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.gettvseries.Firebase.ConfigurationFirebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class FirebaseUsers {

    public static String getUseridentifier(){

        FirebaseAuth user = ConfigurationFirebase.getFirebaseAuthentication();
        String email = user.getCurrentUser().getEmail();
        String userIdentifier = Base64Custom.codeBase64(email);

        return userIdentifier;
    }

    public static FirebaseUser getCurrentUser(){

        FirebaseAuth user = ConfigurationFirebase.getFirebaseAuthentication();
        return user.getCurrentUser();

    }
    public static Boolean updateUserName(String nome){

        try {

            com.google.firebase.auth.FirebaseUser user = getCurrentUser();
            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                    .setDisplayName(nome)
                    .build();
            user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (!task.isSuccessful()){

                        Log.d("Perfil", "Erro ao atualizar nome de perfil.");
                    }
                }
            });
            return true;

        }catch (Exception e){

            e.printStackTrace();
            return false;
        }
    }


    public static Boolean updateUserProfilePicture(Uri url){

        try {

            com.google.firebase.auth.FirebaseUser user = getCurrentUser();
            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                    .setPhotoUri(url)
                    .build();
            user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (!task.isSuccessful()){

                        Log.d("Profile", "Error in profile picture.");
                    }
                }
            });
            return true;

        }catch (Exception e){

            e.printStackTrace();
            return false;
        }
    }

}
