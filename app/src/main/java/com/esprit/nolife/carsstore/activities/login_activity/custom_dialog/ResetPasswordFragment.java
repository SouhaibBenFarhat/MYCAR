package com.esprit.nolife.carsstore.activities.login_activity.custom_dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.esprit.nolife.carsstore.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Souhaib on 31/12/2016.
 */

public class ResetPasswordFragment extends DialogFragment {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();


        View dialog = inflater.inflate(R.layout.dialog_reset_password, null);
        final EditText email = (EditText) getActivity().findViewById(R.id.et_email);


        final FirebaseAuth auth = FirebaseAuth.getInstance();


        builder.setView(dialog)
                // Add action buttons
                .setPositiveButton("ENVOYER", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        String emailAddress = email.getText().toString();
                        auth.sendPasswordResetEmail(emailAddress)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getActivity(), "EMAIL ENVOYEE", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                    }
                })
                .setNegativeButton("RETOUR", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        return builder.create();
    }

}
