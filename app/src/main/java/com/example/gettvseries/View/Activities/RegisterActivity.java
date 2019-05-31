package com.example.gettvseries.View.Activities;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.gettvseries.R;
import com.example.gettvseries.Firebase.ConfigFirebase;
import com.example.gettvseries.Utils.Base64Custom;
import com.example.gettvseries.Utils.FirebaseUsers;
import com.example.gettvseries.Model.Entity.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText editName, editEmail,editPassword;
    private FirebaseAuth authentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editName = findViewById(R.id.editRegisterName);
        editEmail = findViewById(R.id.editRegisterEmail);
        editPassword = findViewById(R.id.editRegisterPassword);
    }
    public void registerUser(final User user){

        authentication = ConfigFirebase.getFirebaseAuthentication();
        authentication.createUserWithEmailAndPassword(

                user.getEmail(), user.getPassword()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){

                    Toast.makeText(RegisterActivity.this, "Successful user registration", Toast.LENGTH_SHORT).show();
                    FirebaseUsers.updateUserName(user.getName());
                    finish();

                    try{

                        String userIdentifier = Base64Custom.codeBase64(user.getEmail());
                        user.setUid(userIdentifier);
                        user.save();

                    }catch (Exception e){

                        e.printStackTrace();
                    }

                }else{

                    String exception = "";
                    try{

                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){

                        exception = "Enter a strong password";
                    }catch (FirebaseAuthInvalidCredentialsException e){

                        exception = "Please, enter a valid email";
                    }catch (FirebaseAuthUserCollisionException e){


                        exception = "This account is already registered";
                    }catch (Exception e){

                        exception = "Error registering: " + e.getMessage();
                        e.printStackTrace();
                    }
                    Toast.makeText(RegisterActivity.this, exception, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void validateUserRegister(View view){

        String textName = editName.getText().toString();
        String textEmail = editEmail.getText().toString();
        String textPassword = editPassword.getText().toString();

        if (!textName.isEmpty()){

            if (!textEmail.isEmpty()){

                if (!textPassword.isEmpty()){

                    User user = new User();
                    user.setName(textName);
                    user.setEmail(textEmail);
                    user.setPassword(textPassword);
                    registerUser(user);

                }else{

                    Toast.makeText(RegisterActivity.this, "Fill the password field", Toast.LENGTH_SHORT).show();
                }
            }else {

                Toast.makeText(RegisterActivity.this, "Fill the email field", Toast.LENGTH_SHORT).show();
            }
        }else{

            Toast.makeText(RegisterActivity.this, "Fill the name field", Toast.LENGTH_SHORT).show();
        }
    }
}
