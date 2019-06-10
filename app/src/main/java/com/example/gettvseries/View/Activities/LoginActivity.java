package com.example.gettvseries.View.Activities;

import android.content.Intent;
import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
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
    private ProgressBar progressBar;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        authentication = ConfigFirebase.getFirebaseAuthentication();
        editEmail = findViewById(R.id.editLoginEmail);
        editPassword = findViewById(R.id.editLoginPassword);
        progressBar = findViewById(R.id.progress_login);
        button = findViewById(R.id.button);

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = authentication.getCurrentUser();
        if (currentUser != null) {

            openMainScreen();
        }
    }

    public void loginUser(User user) {

        showProgress(true);
        authentication.signInWithEmailAndPassword(
                user.getEmail(), user.getPassword()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    openMainScreen();

                } else {

                    String exception = "";
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e) {

                        exception = "User not registered.";

                    } catch (FirebaseAuthInvalidCredentialsException e) {

                        exception = "Username and password do not match";
                    } catch (Exception e) {

                        exception = "Error registering " + e.getMessage();
                        e.printStackTrace();

                    }
                    Toast.makeText(LoginActivity.this, exception, Toast.LENGTH_SHORT).show();
                }
                showProgress(false);
            }
        });


    }

    public void validateUserAuthentication(View view) {

        String textEmail = editEmail.getText().toString();
        String textPassword = editPassword.getText().toString();

        validate(textEmail, textPassword);

    }

    private void showProgress(boolean show) {
        button.setVisibility(!show?View.VISIBLE:View.GONE);
        progressBar.setVisibility(show?View.VISIBLE:View.GONE);

    }

    private void validate(String textEmail, String textPassword) {


        if (!textEmail.isEmpty()) {

            if (!textPassword.isEmpty()) {

                User user = new User();
                user.setEmail(textEmail);
                user.setPassword(textPassword);

                loginUser(user);

            } else {

                Snackbar.make(findViewById(R.id.layout_login), "Fill the password field", Snackbar.LENGTH_LONG).show();
            }
        } else {

            Snackbar.make(findViewById(R.id.layout_login), "Fill the email field", Snackbar.LENGTH_LONG).show();
        }
    }

    public void openRegisterScreen(View view) {

        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));

    }


    public void openMainScreen() {

        startActivity(new Intent(LoginActivity.this, MainActivity.class));

    }


}
