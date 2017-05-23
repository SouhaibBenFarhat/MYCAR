package com.esprit.nolife.carsstore.activities.sell_car_activity.sell_car_fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.esprit.nolife.carsstore.R;
import com.esprit.nolife.carsstore.custom_implementation.CustomViewPager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kosalgeek.android.photoutil.CameraPhoto;
import com.kosalgeek.android.photoutil.GalleryPhoto;

import java.util.Date;

import me.relex.circleindicator.CircleIndicator;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class SecondStepSellCarFragment extends Fragment {

    Button ajoutPhotoVueFace, ajoutPhotoVueCote, ajoutPhotoVueArrier, ajoutPhotoVueInterieur, upload;
    ImageView ivFace, ivArrier, ivCote, ivInterieur;
    Uri faceUri, coteUri, arrierUri, interieurUri;
    private static int PICK_IMAGE = 1;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference mStorage = storage.getReference();
    RequestQueue requestQueue;
    String path;
    GalleryPhoto galleryPhoto;
    CameraPhoto cameraPhoto;
    Bitmap bitmap;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second_step_sell_car, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        ajoutPhotoVueFace = (Button) getActivity().findViewById(R.id.btn_ajout_face);
        ajoutPhotoVueArrier = (Button) getActivity().findViewById(R.id.btn_ajout_arriere);
        ajoutPhotoVueCote = (Button) getActivity().findViewById(R.id.btn_ajout_cote);
        ajoutPhotoVueInterieur = (Button) getActivity().findViewById(R.id.btn_ajout_interieur);
        upload = (Button) getActivity().findViewById(R.id.btn_nextStep2);
        ivFace = (ImageView) getActivity().findViewById(R.id.iv_face);
        ivCote = (ImageView) getActivity().findViewById(R.id.iv_cote);
        ivArrier = (ImageView) getActivity().findViewById(R.id.iv_arrier);
        ivInterieur = (ImageView) getActivity().findViewById(R.id.iv_interieur);
        final CustomViewPager viewpager = (CustomViewPager) getActivity().findViewById(R.id.viewpager);
        final CircleIndicator indicator = (CircleIndicator) getActivity().findViewById(R.id.indicator);
        galleryPhoto = new GalleryPhoto(getActivity());
        cameraPhoto = new CameraPhoto(getActivity());
//        String myValue = this.getArguments().getString("condition");

        final Bundle bundle = new Bundle();


        ajoutPhotoVueFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PICK_IMAGE = 0;
                onpenGallery();
            }
        });
        ajoutPhotoVueArrier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PICK_IMAGE = 1;
                onpenGallery();
            }
        });
        ajoutPhotoVueCote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PICK_IMAGE = 2;
                onpenGallery();
            }
        });
        ajoutPhotoVueInterieur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PICK_IMAGE = 3;
                onpenGallery();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (interieurUri != null && faceUri != null && arrierUri != null && coteUri != null) {

                    Date date = new Date();
                    String ds = date.toString();


                    StorageReference filePath1 = mStorage.child("photos/").child(faceUri.getLastPathSegment() + ds + "1");

                    filePath1.putFile(faceUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            final SharedPreferences prefs = getActivity().getPreferences(0);
                            SharedPreferences.Editor editor = prefs.edit();
                            Uri postPicUrl = taskSnapshot.getDownloadUrl();
                            editor.putString("vueArriere", postPicUrl.toString());
                            //  System.out.println("face "+postPicUrl.toString());
                            editor.apply();


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(getActivity(), "face" , Toast.LENGTH_LONG).show();
                        }
                    });
                    StorageReference filePath2 = mStorage.child("photos/").child(arrierUri.getLastPathSegment() + ds + "2");
                    filePath2.putFile(arrierUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            final SharedPreferences prefs = getActivity().getPreferences(0);
                            SharedPreferences.Editor editor = prefs.edit();
                            Uri postPicUrl = taskSnapshot.getDownloadUrl();
                            editor.putString("vueArriere", postPicUrl.toString());
                            ///  System.out.println("arr "+postPicUrl.toString());
                            editor.apply();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "arrier", Toast.LENGTH_LONG).show();
                        }
                    });
                    StorageReference filePath3 = mStorage.child("photos/").child(coteUri.getLastPathSegment() + ds + "3");
                    filePath3.putFile(coteUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            final SharedPreferences prefs = getActivity().getPreferences(0);
                            SharedPreferences.Editor editor = prefs.edit();
                            Uri postPicUrl = taskSnapshot.getDownloadUrl();
                            editor.putString("vueCote", postPicUrl.toString());

                            editor.apply();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "cote", Toast.LENGTH_LONG).show();
                        }
                    });
                    StorageReference filePath4 = mStorage.child("photos/").child(interieurUri.getLastPathSegment() + ds + "4");
                    filePath4.putFile(interieurUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            final SharedPreferences prefs = getActivity().getPreferences(0);
                            SharedPreferences.Editor editor = prefs.edit();
                            Uri postPicUrl = taskSnapshot.getDownloadUrl();
                            editor.putString("vueInterieur", postPicUrl.toString());

                            editor.apply();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "interieur", Toast.LENGTH_LONG).show();
                        }
                    });


                    viewpager.setCurrentItem(2, true);
                    viewpager.setPagingEnabled(true);
                } else {
                    Toast.makeText(getActivity(), "please shoose all the pictures", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    private void onpenGallery() {
//        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
//                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//// Start the Intent
        startActivityForResult(galleryPhoto.openGalleryIntent(), PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requesCode, int resultCode, Intent data) {
        super.onActivityResult(requesCode, resultCode, data);
        switch (requesCode) {
            case 0:
                if (resultCode == RESULT_OK && requesCode == PICK_IMAGE) {

                    faceUri = data.getData();

                    ivFace.setImageURI(faceUri);

                }// Do your stuff here...
                break;
            case 1:
                if (resultCode == RESULT_OK && requesCode == PICK_IMAGE) {
                    arrierUri = data.getData();
                    ivArrier.setImageURI(arrierUri);

                }// Do your other stuff here...
                break;
            case 2:
                if (resultCode == RESULT_OK && requesCode == PICK_IMAGE) {
                    coteUri = data.getData();
                    ivCote.setImageURI(coteUri);

                }// Do your other stuff here...
                break;
            case 3:
                if (resultCode == RESULT_OK && requesCode == PICK_IMAGE) {

                    interieurUri = data.getData();
                    ivInterieur.setImageURI(interieurUri);

                }// Do your other stuff here...
                break;

        }


    }


}
