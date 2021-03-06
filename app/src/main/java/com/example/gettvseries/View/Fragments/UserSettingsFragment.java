package com.example.gettvseries.View.Fragments;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.gettvseries.Firebase.ConfigurationFirebase;
import com.example.gettvseries.R;
import com.example.gettvseries.Utils.FirebaseUsers;
import com.example.gettvseries.Utils.Permission;
import com.example.gettvseries.View.Activities.MainActivity;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserSettingsFragment extends Fragment {

    private ImageButton imageButtonCamera, imageButtonGallery;
    private static final int CAMERA_SELECTION = 100;
    private static final int GALLERY_SELECTION = 200;
    private CircleImageView circleImageView;
    private StorageReference storageReference;
    private String userIdentifier;
    private EditText editProfileName;
    private EditText editUserPassword;
    private EditText editConfirmUpdatePassword;
    private View view;
    private FirebaseAuth authentication;
    private Button saveName, savePassword;

    private String[] necessaryPermissions = new String[]{

            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };
    private FloatingActionButton fab;


    public UserSettingsFragment() {
    }

    public static UserSettingsFragment newInstance() {
        return new UserSettingsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_user_settings, container, false);

        storageReference = ConfigurationFirebase.getFirebaseStorage();
        userIdentifier = FirebaseUsers.getUseridentifier();
        authentication = ConfigurationFirebase.getFirebaseAuthentication();

        imageButtonCamera = view.findViewById(R.id.imageButtonCamera);
        imageButtonGallery = view.findViewById(R.id.imageButtonGallery);
        circleImageView = view.findViewById(R.id.circleImageView);
        editProfileName = view.findViewById(R.id.editProfileName);
        editUserPassword = view.findViewById(R.id.editUpdateUserPassword);
        editConfirmUpdatePassword = view.findViewById(R.id.editConfirmUpdatePassword);
        saveName = view.findViewById(R.id.bnt_save_username);
        savePassword = view.findViewById(R.id.bnt_save_password);
        editProfileName.setEnabled(false);
        editUserPassword.setEnabled(false);
        editConfirmUpdatePassword.setEnabled(false);
        saveName.setEnabled(false);
        savePassword.setEnabled(false);



        fab = view.findViewById(R.id.fab);

        Permission.validatePermissions(necessaryPermissions, getActivity(), 1);

        FirebaseUser user = FirebaseUsers.getCurrentUser();
        Uri url = user.getPhotoUrl();

        if (url != null) {

            Glide.with(UserSettingsFragment.this)
                    .load(url)
                    .into(circleImageView);
        } else {
            circleImageView.setImageResource(R.drawable.standard);
        }

        editProfileName.setText(user.getDisplayName());
        editProfileName.setSelection((user.getDisplayName() != null) ? user.getDisplayName().length() : 0);

        imageButtonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                // Try-catch block added

                try {

                    if (intent.resolveActivity(getContext().getPackageManager()) != null) {

                        startActivityForResult(intent, CAMERA_SELECTION);
                    }

                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });

        imageButtonGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                // Try-catch block added
                try {
                    if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                        startActivityForResult(intent, GALLERY_SELECTION);
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });

        saveName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = editProfileName.getText().toString();
                if (!username.isEmpty()){
                    if (updateUserName(username)){

                        Snackbar.make(view, "Username updated successfully", Snackbar.LENGTH_SHORT).show();
                        enableFalse();
                    }
                }else {

                    Snackbar.make(view, "The name field is empty", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        savePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String password = editUserPassword.getText().toString();
                String confirmPassword = editConfirmUpdatePassword.getText().toString();
                if (!password.isEmpty()){
                    if (!confirmPassword.isEmpty()){

                        if (password.equals(confirmPassword)){

                            if (updateUserPassword(confirmPassword)){
                                Snackbar.make(view, "Password updated successfully", Snackbar.LENGTH_SHORT).show();
                                enableFalse();
                            }
                        }else {
                            Snackbar.make(view, "Passwords do not match", Snackbar.LENGTH_SHORT).show();
                            editProfileName.setEnabled(true);
                            editUserPassword.setEnabled(true);
                            editConfirmUpdatePassword.setEnabled(true);
                        }
                    }else {
                        Snackbar.make(view, "Confirm your password.", Snackbar.LENGTH_SHORT).show();
                    }

                }else {
                    Snackbar.make(view, "Password field empty.", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editProfileName.isEnabled()||
                        saveName.isEnabled()||
                        editUserPassword.isEnabled()||
                        savePassword.isEnabled()||
                        editConfirmUpdatePassword.isEnabled()
                ){
                    enableFalse();

                } else {
                    enableTrue();
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == getActivity().RESULT_OK) {

            Bitmap image = null;

            try {

                switch (requestCode) {

                    case CAMERA_SELECTION:

                        image = (Bitmap) data.getExtras().get("data");
                        break;

                    case GALLERY_SELECTION:
                        Uri localImagemSelecionada = data.getData();
                        image = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), localImagemSelecionada);
                        break;
                }

                if (image != null) {

                    circleImageView.setImageBitmap(image);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    image.compress(Bitmap.CompressFormat.JPEG, 70, baos);
                    byte[] dadosImagem = baos.toByteArray();

                    final StorageReference imagemRef = storageReference
                            .child("images")
                            .child("profile")
                            .child(userIdentifier + ".jpeg");

                    UploadTask uploadTask = imagemRef.putBytes(dadosImagem);

                    uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                            if (!task.isSuccessful()) {

                                throw task.getException();
                            }

                            return imagemRef.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(
                                        getContext(),
                                        "Upload Successful",
                                        Toast.LENGTH_SHORT)
                                        .show();

                                Uri url = task.getResult();
                                updateUserPicture(url);

                            } else {
                                Toast.makeText(
                                        getContext(),
                                        "Upload Error",
                                        Toast.LENGTH_SHORT)
                                        .show();
                            }
                        }
                    });
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void updateUserPicture(Uri url) {
        FirebaseUsers.updateUserProfilePicture(url);
    }

    public Boolean updateUserName(String string){

        return FirebaseUsers.updateUserName(string);
    }

    public Boolean updateUserPassword(String string){

        return FirebaseUsers.updateUserPassword(string);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int permissionResult : grantResults) {

            if (permissionResult == PackageManager.PERMISSION_DENIED) {
                permissionValidationAlert();
            }
        }
    }

    public void permissionValidationAlert() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.permission_error);
        builder.setMessage(R.string.permission_message);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.confirm_message, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void enableTrue(){

        editProfileName.setEnabled(true);
        editUserPassword.setEnabled(true);
        editConfirmUpdatePassword.setEnabled(true);
        saveName.setEnabled(true);
        savePassword.setEnabled(true);
        editProfileName.requestFocus();
        editUserPassword.requestFocus();
        editConfirmUpdatePassword.requestFocus();
        fab.setImageDrawable(ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.ic_lock_white_24dp));
    }
    public void enableFalse(){

        editProfileName.setEnabled(false);
        saveName.setEnabled(false);
        editUserPassword.setEnabled(false);
        savePassword.setEnabled(false);
        editConfirmUpdatePassword.setEnabled(false);
        fab.setImageDrawable(ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.ic_edit_white_24dp));
    }

}
