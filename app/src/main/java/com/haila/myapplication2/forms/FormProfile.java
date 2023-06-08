package com.haila.myapplication2.forms;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.haila.myapplication2.MainActivity;
import com.haila.myapplication2.R;

public class FormProfile extends AppCompatActivity {


    private TextView textViewFirstName, textViewFullName, keyWords_screen, address_screen, post_feed, textViewTitle;
    private EditText editTextName, editTextEmail, editTextPhone, editTextDateBirth;
    private Button bt_logout;
    private ImageView profile_image;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_profile);

        getSupportActionBar().hide();
        startComponents();

        textViewTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FormProfile.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FormProfile.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        bt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(FormProfile.this, FormLogin.class);
                startActivity(intent);
                finish();
            }
        });

        keyWords_screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FormProfile.this, FormKeyWords.class);
                startActivity(intent);
            }
        });

        address_screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FormProfile.this, FormAddress.class);
                startActivity(intent);
            }
        });

        post_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FormProfile.this, FormPostRegister.class);
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
                    textViewFirstName.setText(documentSnapshot.getString("user_first_name"));
                    textViewFullName.setText(documentSnapshot.getString("user_full_name"));
                    editTextPhone.setText(documentSnapshot.getString("user_phone_number"));
                    editTextDateBirth.setText(documentSnapshot.getString("user_date_birth"));
                    editTextName.setText(documentSnapshot.getString("user_full_name"));
                    editTextEmail.setText(email);
                }
            }
        });
    }

    private void startComponents(){
        bt_logout = findViewById(R.id.bt_logout);
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPhone = findViewById(R.id.editTextPhone);
        textViewFullName = findViewById(R.id.textViewFullName);
        textViewFirstName = findViewById(R.id.textViewFirstName);
        editTextDateBirth = findViewById(R.id.editTextDateBirth);

        keyWords_screen = findViewById(R.id.keyWords_screen);
        address_screen = findViewById(R.id.address_screen_profile);
        post_feed = findViewById(R.id.postFeed_screen);
        profile_image = findViewById(R.id.profile_image);
        textViewTitle = findViewById(R.id.textViewTitle);
    }
}