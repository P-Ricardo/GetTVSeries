package com.example.gettvseries.View.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.gettvseries.R;
import com.example.gettvseries.Firebase.ConfigFirebase;
import com.example.gettvseries.Model.Entity.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText editEmail, editPassword;
    private FirebaseAuth authentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        authentication = ConfigFirebase.getFirebaseAuthentication();
        editEmail = findViewById(R.id.editLoginEmail);
        editPassword = findViewById(R.id.editLoginPassword);


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser =authentication.getCurrentUser();
        if (currentUser != null){

            openMainScreen();
        }
    }

    public void loginUser(User user){


        authentication.signInWithEmailAndPassword(
                user.getEmail(), user.getPassword()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){

                    openMainScreen();

                }else {

                    String exception = "";
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthInvalidUserException e){

                        exception = "User not registered.";

                    }catch (FirebaseAuthInvalidCredentialsException e){

                        exception = "Username and password do not match";
                    }catch (Exception e){

                        exception = "Error registering " + e.getMessage();
                        e.printStackTrace();

                    }
                    Toast.makeText(LoginActivity.this, exception, Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    public void validateUserAuthentication(View view){


        String textEmail = editEmail.getText().toString();
        String textPassword = editPassword.getText().toString();

        if (!textEmail.isEmpty()){

            if (!textPassword.isEmpty()){

                User user = new User();
                user.setEmail(textEmail);
                user.setPassword(textPassword);

                loginUser(user);

            }else {

                Toast.makeText(LoginActivity.this, "Fill the password field", Toast.LENGTH_SHORT).show();
            }
        }else{

            Toast.makeText(LoginActivity.this, "Fill the email field", Toast.LENGTH_SHORT).show();
        }

    }
    public void openRegisterScreen(View view){

        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));

    }


    public void openMainScreen(){

        startActivity(new Intent(LoginActivity.this, MainActivity.class));

    }



}
