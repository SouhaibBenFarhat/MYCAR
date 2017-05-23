package com.esprit.nolife.carsstore.activities.login_activity.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.esprit.nolife.carsstore.R;
import com.esprit.nolife.carsstore.activities.main_acitivity.MainActivity;
import com.esprit.nolife.carsstore.dao.UserDao;
import com.esprit.nolife.carsstore.entities.User;
import com.esprit.nolife.carsstore.utils.ApplicationState;
import com.esprit.nolife.carsstore.utils.DevelopperExtension;
import com.esprit.nolife.carsstore.utils.InputValidation;
import com.esprit.nolife.carsstore.utils.ManageUserPreferences;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserInfo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class LoginFragment extends Fragment {

    UserDao userDao;
    Button btnLogin;
    EditText etUsername, etPassword;
    ImageButton btnCreateAccount;
    ProgressDialog loginProgress;
    ProgressDialog fbLoginProgress;
    FirebaseAuth mAuth;
    InputValidation inputValidation;
    CheckBox rememberMeBox;
    ManageUserPreferences manageUserPreferences;
    Intent intent;
    ApplicationState applicationState;
    SignInButton googleSigninButton;
    private static final int RC_GOOGLE_SIGN_IN = 1;
    private GoogleApiClient googleApiClient;
    private static final String TAG = "LOGIN_ACTIVITY";
    private FirebaseAuth.AuthStateListener mAuthListener;
    CallbackManager callbackManager;
    LoginButton loginButton;
    DevelopperExtension devExtension;
    private FacebookCallback<LoginResult> mCallBack = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            fbLoginProgress.setMessage("Sign in with Facebook...");
            fbLoginProgress.show();
            AccessToken accessToken = loginResult.getAccessToken();
            Profile profile = Profile.getCurrentProfile();
            handleFacebookAccessToken(accessToken);

        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException error) {

        }
    };


    //This is the video part
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginButton = (LoginButton) getActivity().findViewById(R.id.facebook_login_button);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.setFragment(this);
        loginButton.registerCallback(callbackManager, mCallBack);

    }
//THe end of the video part


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in

                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };


        //configure resetting password dialog
        ImageButton resetPasswordButton = (ImageButton) getActivity().findViewById(R.id.reset_password_button);

        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                final EditText edittext = new EditText(getActivity().getApplicationContext());
                edittext.setTextColor(Color.parseColor("#000000"));

                edittext.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                edittext.setHint("EMAIL...");
                alert.setTitle("RECUPERER VOTRE MOT DE PASSE");
                alert.setMessage("");
                alert.setView(edittext);

                alert.setPositiveButton("CONFIMER", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        final String YouEditTextValue = edittext.getText().toString();
                        FirebaseAuth auth = FirebaseAuth.getInstance();
                        if (!(YouEditTextValue.isEmpty() || YouEditTextValue == null)) {
                            auth.sendPasswordResetEmail(YouEditTextValue)

                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getActivity().getApplicationContext(), "EMAIL ENVOYEE", Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    System.out.println(e.toString());
                                    System.out.println(e.getCause());
                                    System.out.println("****************************");
                                    Toast.makeText(getActivity().getApplicationContext(), "EMAIL INVALIDE", Toast.LENGTH_SHORT).show();

                                }
                            });
                        } else {
                            Toast.makeText(getContext(), "EMAIL INVALIDE", Toast.LENGTH_SHORT).show();
                        }


                    }
                });

                alert.setNegativeButton("RETOUR", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });

                alert.show();
            }
        });


        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        googleApiClient = new GoogleApiClient.Builder(getContext()).enableAutoManage(getActivity(), new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Failed to connect with Google Account")
                        .setNegativeButton("Retry", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                // Create the AlertDialog object and return it
                builder.create();
                builder.show();


            }
        }).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();

        manageUserPreferences = new ManageUserPreferences();

        devExtension = new DevelopperExtension();
        userDao = new UserDao();
        fbLoginProgress = new ProgressDialog(getActivity());
        loginProgress = new ProgressDialog(getActivity());
        inputValidation = new InputValidation();

        applicationState = new ApplicationState();
        rememberMeBox = (CheckBox) getActivity().findViewById(R.id.cb_remember_me);

        btnLogin = (AppCompatButton) getActivity().findViewById(R.id.btn_login);
        btnCreateAccount = (ImageButton) getActivity().findViewById(R.id.btn_create_account);

        etUsername = (EditText) getActivity().findViewById(R.id.input_email);
        etPassword = (EditText) getActivity().findViewById(R.id.input_password);


        etUsername.setText(manageUserPreferences.getSigninParameters(getActivity(), "UserPreferences").get(0));
        etPassword.setText(manageUserPreferences.getSigninParameters(getActivity(), "UserPreferences").get(1));

        googleSigninButton = (SignInButton) getActivity().findViewById(R.id.btn_google_signin);
        googleSigninButton.setVisibility(View.INVISIBLE);
        if (devExtension.isNetworkAvailable(getActivity()) == false) {
            final Snackbar snackBar = Snackbar.make(getView(), "Please check your connection network", Snackbar.LENGTH_INDEFINITE);
            snackBar.setAction("Retry", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (devExtension.isNetworkAvailable(getActivity()) == true) {

                    } else {
                        Intent intent = getActivity().getIntent();
                        getActivity().finish();
                        startActivity(intent);
                    }
                }
            });
            snackBar.show();
        }
        btnLogin.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            loginProgress.setMessage("Signin processing...");
                                            loginProgress.show();

                                            final String username, password;
                                            username = etUsername.getText().toString();
                                            password = etPassword.getText().toString();
                                            mAuth = FirebaseAuth.getInstance();
                                            if (inputValidation.passwordIsValid(password) && inputValidation.mailIsValid(username)) {

                                                mAuth.signInWithEmailAndPassword(etUsername.getText().toString(), etPassword.getText().toString())
                                                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<AuthResult> task) {

                                                                if (task.isSuccessful()) {
                                                                    buildUserAnonymosly();
                                                                    manageUserPreferences.setUserState(getActivity(), "connected");
                                                                    if (rememberMeBox.isChecked()) {
                                                                        manageUserPreferences
                                                                                .insertSigninParameters(getActivity(), username, password);
                                                                        manageUserPreferences
                                                                                .insertSigninParameters(getActivity(), username, password);
                                                                        System.out.println(manageUserPreferences.getSigninParameters(getActivity(),
                                                                                "UserPreferences").get(0) + "aaaaaaaaaaaaaaaaaaa");
                                                                        Intent intent = new Intent(getActivity(), MainActivity.class);
                                                                        startActivity(intent);
                                                                        loginProgress.dismiss();

                                                                    } else {

                                                                        intent = new Intent(getActivity(), MainActivity.class);
                                                                        startActivity(intent);
                                                                        loginProgress.dismiss();
                                                                    }
                                                                } else {
                                                                    if (applicationState
                                                                            .isNetworkAvailable(getActivity()) == false) {
                                                                        loginProgress.dismiss();

                                                                        final Snackbar snackBar = Snackbar.make(getView(),
                                                                                "Somethin went wrong, Please Try again..",
                                                                                Snackbar.LENGTH_INDEFINITE);
                                                                        snackBar.setAction("Retry", new View.OnClickListener() {
                                                                            @Override
                                                                            public void onClick(View v) {
                                                                                snackBar.dismiss();

                                                                            }
                                                                        });
                                                                        snackBar.show();
                                                                    } else {
                                                                        loginProgress.dismiss();
                                                                        final Snackbar snackBar = Snackbar.make(getView(),
                                                                                "Please check your email or password again...",
                                                                                Snackbar.LENGTH_INDEFINITE);
                                                                        snackBar.setAction("Retry", new View.OnClickListener() {
                                                                            @Override
                                                                            public void onClick(View v) {
                                                                                snackBar.dismiss();

                                                                            }
                                                                        });
                                                                        snackBar.show();
                                                                    }


                                                                }
                                                            }
                                                        });
                                            } else {
                                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                                builder.setMessage("Please check your information again")
                                                        .setNegativeButton("Retry", new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int id) {
                                                                // User cancelled the dialog
                                                            }
                                                        });
                                                // Create the AlertDialog object and return it
                                                builder.create();
                                                builder.show();
                                                loginProgress.dismiss();
                                            }
                                        }


                                    }

        );

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.login_container);
                                                    viewPager.setCurrentItem(1);

                                                }
                                            }

        );

        // devExtension.setGooglePlusButtonText(googleSigninButton, "");
//        devExtension.setGoogleBackgroundImage(googleSigninButton, R.drawable.btn_google_signin_dark_normal_mdpi);
        googleSigninButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginProgress.setMessage("Google Account Signin...");
                signIn();
            }
        });

    }


    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, RC_GOOGLE_SIGN_IN);
        loginProgress.dismiss();
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        loginProgress.show();
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {

//                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(getContext(), "Sorry Try Again", Toast.LENGTH_SHORT).show();
                        } else {
                            buildUser();
                            manageUserPreferences.setUserState(getActivity(), "connected");
                            intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                            loginProgress.dismiss();
                        }
                        // ...
                    }


                });

    }


    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(getActivity(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            buildUser();
                            manageUserPreferences.setUserState(getActivity(), "connected");
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                            fbLoginProgress.dismiss();
                        }

                        // ...
                    }
                });
    }

    public void buildUser() {
        User user = new User();
        user.setUserUID(mAuth.getCurrentUser().getUid());
        user.setName(mAuth.getCurrentUser().getDisplayName());
        user.setEmail(mAuth.getCurrentUser().getEmail());
        if (mAuth.getCurrentUser().getPhotoUrl() != null) {
            user.setProfilePictureUri(mAuth.getCurrentUser().getPhotoUrl().toString());
        }

        user.setProvider(mAuth.getCurrentUser().getProviderId());
        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date dateobj = new Date();
        user.setSignedIn(df.format(dateobj));
        for (UserInfo u : FirebaseAuth.getInstance().getCurrentUser().getProviderData()) {
            if (u.getProviderId().equals("facebook.com")) {
                user.setConnectedWith(u.getProviderId());
            }
            if (u.getProviderId().equals("google.com")) {
                user.setConnectedWith(u.getProviderId());
            }
        }
        userDao.bringUserToMySide(user);
    }

    public void buildUserAnonymosly() {
        User user = new User();
        user.setUserUID(mAuth.getCurrentUser().getUid());
        user.setName(mAuth.getCurrentUser().getDisplayName());
        user.setEmail(mAuth.getCurrentUser().getEmail());
        user.setProvider(mAuth.getCurrentUser().getProviderId());
        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date dateobj = new Date();
        user.setSignedIn(df.format(dateobj));
        for (UserInfo u : FirebaseAuth.getInstance().getCurrentUser().getProviderData()) {
            if (u.getProviderId().equals("facebook.com")) {
                user.setConnectedWith(u.getProviderId());
            }
            if (u.getProviderId().equals("google.com")) {
                user.setConnectedWith(u.getProviderId());
            }
        }
        userDao.bringUserToMySide(user);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_GOOGLE_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                //-----> he you can change the redirection
                firebaseAuthWithGoogle(account);
            } else {
                final Snackbar snackBar = Snackbar.make(getView(),
                        "Sorry something went wrong with Google Account..",
                        Snackbar.LENGTH_INDEFINITE);
                snackBar.setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackBar.dismiss();

                    }
                });
                snackBar.show();
            }
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }

    }

}



