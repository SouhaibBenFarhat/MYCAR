package com.esprit.nolife.carsstore.activities.login_activity.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.esprit.nolife.carsstore.R;
import com.esprit.nolife.carsstore.activities.main_acitivity.MainActivity;
import com.esprit.nolife.carsstore.utils.DevelopperExtension;
import com.esprit.nolife.carsstore.utils.InputValidation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.ProviderQueryResult;

public class RegisterFragment extends Fragment {

    Button btnRegister;
    EditText etUsername, etPassword;
    ProgressDialog registerProgress;
    ProgressDialog signinProgress;
    FirebaseAuth firebaseAuth;
    CheckBox registerCheckLogin;
    InputValidation inputValidation;
    DevelopperExtension developperExtension;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        developperExtension = new DevelopperExtension();
        if (developperExtension.isNetworkAvailable(getActivity()) == false) {
            final Snackbar snackBar = Snackbar.make(getView(), "Please check your connection network", Snackbar.LENGTH_INDEFINITE);

            snackBar.setAction("Retry", new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (developperExtension.isNetworkAvailable(getActivity()) == false) {
                        snackBar.show();
                    } else {
                        snackBar.dismiss();
                    }
                }
            });
        }


        inputValidation = new InputValidation();
        registerProgress = new ProgressDialog(getContext());
        signinProgress = new ProgressDialog(getContext());

        registerProgress.setMessage("Processing Registration...");
        signinProgress.setMessage("Sign in processing...");

        registerCheckLogin = (CheckBox) getActivity().findViewById(R.id.register_check_login);
        btnRegister = (Button) getActivity().findViewById(R.id.btn_register);
        etUsername = (EditText) getActivity().findViewById(R.id.et_register_email);
        etPassword = (EditText) getActivity().findViewById(R.id.et_register_password);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                registerProgress.show();


                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();

                if ((username.equals("") || password.equals(""))) {
                    registerProgress.dismiss();
                    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Please enter a valid username or password")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            });
                    builder.create().show();


                } else if (inputValidation.mailIsValid(username).equals(false)) {
                    registerProgress.dismiss();
                    etUsername.setError("Please enter a valid mail");

                } else if (inputValidation.passwordIsValid(password).equals(false)) {
                    registerProgress.dismiss();
                    etPassword.setError("Password should be longer than 8 caracters");

                } else {
                    firebaseAuth = FirebaseAuth.getInstance();
                    firebaseAuth.createUserWithEmailAndPassword(etUsername.getText().toString(),
                            etPassword.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        registerProgress.dismiss();
                                        if (registerCheckLogin.isChecked()) {

                                            signinUser(username, password);

                                        } else {

                                            ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.login_container);
                                            viewPager.setCurrentItem(0);
                                        }
                                    } else {
                                        firebaseAuth.fetchProvidersForEmail(etUsername.getText().toString())
                                                .addOnCompleteListener(new OnCompleteListener<ProviderQueryResult>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<ProviderQueryResult> task) {
                                                        if (task.isSuccessful()) {
                                                            final int i = task.getResult().getProviders().size();
                                                            if (i != 0) {
                                                                registerProgress.dismiss();
                                                                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                                                builder.setTitle("Registration Failed");

                                                                builder.setMessage("This mail already existe...")
                                                                        .setPositiveButton("Sign in", new DialogInterface.OnClickListener() {
                                                                            public void onClick(DialogInterface dialog, int id) {

                                                                                ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.login_container);
                                                                                viewPager.setCurrentItem(0);
                                                                            }
                                                                        });
                                                                builder.create().show();
                                                            }
                                                        }
                                                    }
                                                });
                                    }
                                }
                            });
                }
            }
        });


    }


    public void signinUser(String username, String password) {
        signinProgress.show();
        firebaseAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            System.out.println("here..................................");
                            Intent intent
                                    = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                            signinProgress.dismiss();
                            System.out.println(firebaseAuth.getCurrentUser().getEmail() + "---------------------");
                        }

                    }
                });
    }


}
