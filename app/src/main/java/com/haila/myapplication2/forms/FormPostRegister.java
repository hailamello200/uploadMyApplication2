package com.haila.myapplication2.forms;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.haila.myapplication2.MainActivity;
import com.haila.myapplication2.R;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FormPostRegister extends AppCompatActivity {

    private Button buttonPost;
    private EditText editTextPost1;
    String userID;
    String[] messages = {"Verifique se existe informação para registrar"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_post_register);

        getSupportActionBar().hide();
        startComponents();

        buttonPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String post_text = editTextPost1.getText().toString();

                if (post_text.isEmpty()) {

                    Snackbar snackbar = Snackbar.make(v, messages[0], Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                } else {

                    registerPost();

                    Intent intent = new Intent(FormPostRegister.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void registerPost() {

        String post_text = editTextPost1.getText().toString();
        Date current_post_date = Calendar.getInstance().getTime();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Map<String, Object> postUser = new HashMap<>();
        postUser.put("post_text", post_text);
        postUser.put("post_user_id", userID);
        postUser.put("post_date", current_post_date);

        DocumentReference documentReference = db.collection("UserPost").document(userID);
        documentReference.set(postUser).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("db", "Success in saving data");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("db_error", "Error saving data " + e.toString());
                    }
                });
    }

    private void startComponents(){

        buttonPost = findViewById(R.id.buttonPost);
        editTextPost1 = findViewById(R.id.editTextPost1);
    }
}