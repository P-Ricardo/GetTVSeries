package com.example.gettvseries;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gettvseries.activity.MainActivity;
import com.example.gettvseries.config.ConfigFirebase;
import com.example.gettvseries.helper.FirebaseUsers;
import com.example.gettvseries.helper.Permission;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserSettingsFragment extends Fragment {




    private ImageButton imageButtonCamera, imageButtonGallery;
    private static final int CAMERA_SELECTION = 100;
    private static final int GALLERY_SELECTION = 200;
    private CircleImageView circleImageView;
    private StorageReference storageReference;
    private String userIdentifier;
    private EditText editProfileName;
    private View view;
    private FirebaseAuth authentication;

    private String[] necessaryPermissions = new String[]{

            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA

    };




    public UserSettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_user_settings, container, false);

        storageReference = ConfigFirebase.getFirebaseStorage();
        userIdentifier = FirebaseUsers.getUseridentifier();
        authentication = ConfigFirebase.getFirebaseAuthentication();

        imageButtonCamera = view.findViewById(R.id.imageButtonCamera);
        imageButtonGallery = view.findViewById(R.id.imageButtonGallery);
        circleImageView = view.findViewById(R.id.circleImageView);
        editProfileName = view.findViewById(R.id.editProfileName);

        Permission.validatePermissions(necessaryPermissions, getActivity(), 1);


        FirebaseUser user = FirebaseUsers.gettheCurrentUser();
        Uri url = user.getPhotoUrl();
        if (url != null){


            if (url != null){

                Glide.with(UserSettingsFragment.this)
                        .load(url)
                        .into(circleImageView);
            }
        }else {
            circleImageView.setImageResource(R.drawable.standard);
        }
        editProfileName.setText(user.getDisplayName());

        imageButtonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(intent.resolveActivity(getContext().getPackageManager()) != null){

                    startActivityForResult(intent, CAMERA_SELECTION);
                }
            }
        });

        imageButtonGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                if(intent.resolveActivity(getContext().getPackageManager()) != null){

                    startActivityForResult(intent, GALLERY_SELECTION);
                }
            }
        });




        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == getActivity().RESULT_OK){

            Bitmap image= null;

            try{

                switch (requestCode){

                    case CAMERA_SELECTION:

                        image = (Bitmap) data.getExtras().get("data");
                        break;

                    case GALLERY_SELECTION:
                        Uri localImagemSelecionada = data.getData();
                        image = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), localImagemSelecionada);break;

                }

                if (image != null){

                    circleImageView.setImageBitmap(image);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    image.compress(Bitmap.CompressFormat.JPEG, 70, baos);
                    byte[] dadosImagem = baos.toByteArray();

                    final StorageReference imagemRef = storageReference
                            .child("images")
                            .child("profile")
                            .child(userIdentifier + ".jpeg");

                    UploadTask uploadTask= imagemRef.putBytes(dadosImagem);

                    uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                            if (!task.isSuccessful()){


                                throw task.getException();
                            }

                            return imagemRef.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()){


                                Toast.makeText(getContext(), "Sucesso ao fazer upload da imagem",
                                        Toast.LENGTH_SHORT).show();
                                Uri url = task.getResult();
                                updateUserPicture(url);

                            }
                            else {

                                Toast.makeText(getContext(), "Erro ao fazer upload da imagem",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                }
            }catch (Exception e){


            }
        }


    }

    public void updateUserPicture(Uri url){


        FirebaseUsers.updateUserProfilePicture(url);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int permissionResult : grantResults){

            if (permissionResult == PackageManager.PERMISSION_DENIED){

                permissionValidationAlert();
            }

        }

    }

    public void permissionValidationAlert(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Permissões Negadas");
        builder.setMessage("Para utilizar o app é necessário aceitar as permissões");
        builder.setCancelable(false);
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }





}
