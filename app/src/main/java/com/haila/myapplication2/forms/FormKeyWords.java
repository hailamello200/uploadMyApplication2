package com.haila.myapplication2.forms;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.haila.myapplication2.R;

public class FormKeyWords extends AppCompatActivity {

    private TextView textViewFullName, address_screen, profile_screen;
    private Button bt_registerKeyWord;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_key_words);

        getSupportActionBar().hide();
        startComponents();

        address_screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FormKeyWords.this, FormAddress.class);
                startActivity(intent);
            }
        });

        profile_screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FormKeyWords.this, FormProfile.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("Users").document(userID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if (documentSnapshot != null){
                    textViewFullName.setText(documentSnapshot.getString("user_full_name"));
                    bt_registerKeyWord.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void startComponents(){
        textViewFullName = findViewById(R.id.textViewFullName);
        address_screen = findViewById(R.id.keyWords_screenAddress);
        profile_screen = findViewById(R.id.keyWords_screenProfile);
        bt_registerKeyWord = findViewById(R.id.buttonRegisterKeyWord);
    }
}