package com.haila.myapplication2.forms;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.haila.myapplication2.R;

import java.util.HashMap;
import java.util.Map;

public class FormAddress extends AppCompatActivity {

    private TextView textViewFullName, profile_screen, keyWords_screen;
    private EditText editTextAddress1, editTextAddress2, editTextAddress3, editTextAddress4, editTextAddress5;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String userID, addressID;
    private Button bt_registerAddress;
    String[] messages = {"Preencha todos os campos e registre o endereço"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_address);

        getSupportActionBar().hide();
        startComponents();

        bt_registerAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String address_neighborhood = editTextAddress1.getText().toString();
                String address_city = editTextAddress2.getText().toString();
                String address_zip_code = editTextAddress3.getText().toString();
                String address_road = editTextAddress4.getText().toString();
                String address_complement = editTextAddress5.getText().toString();

                if (address_neighborhood.isEmpty() || address_city.isEmpty() ||
                    address_zip_code.isEmpty() || address_road.isEmpty() || address_complement.isEmpty()){

                    Snackbar snackbar = Snackbar.make(v, messages[0], Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }else {
                    registerAddress();
                }
            }
        });

        profile_screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String address_neighborhood = editTextAddress1.getText().toString();
                String address_city = editTextAddress2.getText().toString();
                String address_zip_code = editTextAddress3.getText().toString();
                String address_road = editTextAddress4.getText().toString();
                String address_complement = editTextAddress5.getText().toString();

                if (address_neighborhood.isEmpty() || address_city.isEmpty() ||
                    address_zip_code.isEmpty() || address_road.isEmpty() || address_complement.isEmpty()){

                    Snackbar snackbar = Snackbar.make(v, messages[0], Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                } else {

                    Intent intent = new Intent(FormAddress.this, FormProfile.class);
                    startActivity(intent);
                }
            }
        });

        keyWords_screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String address_neighborhood = editTextAddress1.getText().toString();
                String address_city = editTextAddress2.getText().toString();
                String address_zip_code = editTextAddress3.getText().toString();
                String address_road = editTextAddress4.getText().toString();
                String address_complement = editTextAddress5.getText().toString();

                if (address_neighborhood.isEmpty() || address_city.isEmpty() ||
                    address_zip_code.isEmpty() || address_road.isEmpty() || address_complement.isEmpty()){

                    Snackbar snackbar = Snackbar.make(v, messages[0], Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                } else {

                    Intent intent = new Intent(FormAddress.this, FormKeyWords.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void registerAddress(){

        String address_neighborhood = editTextAddress1.getText().toString();
        String address_city = editTextAddress2.getText().toString();
        String address_zip_code = editTextAddress3.getText().toString();
        String address_road = editTextAddress4.getText().toString();
        String address_complement = editTextAddress5.getText().toString();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Map<String, Object> address = new HashMap<>();
        address.put("address_neighborhood", address_neighborhood);
        address.put("address_city", address_city);
        address.put("address_zip_code", address_zip_code);
        address.put("address_road", address_road);
        address.put("address_complement", address_complement);
        address.put("addres_user_id", userID);

        DocumentReference documentReference = db.collection("Address").document(userID);
        documentReference.set(address).addOnSuccessListener(new OnSuccessListener<Void>() {
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

    @Override
    protected void onStart() {
        super.onStart();

        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        addressID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("Users").document(userID);

        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if (documentSnapshot != null){
                    textViewFullName.setText(documentSnapshot.getString("user_full_name"));
                }
            }
        });

        DocumentReference documentReference1 = db.collection("Address").document(addressID);

        documentReference1.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot1, @Nullable FirebaseFirestoreException error) {
                if (documentSnapshot1 != null) {
                    editTextAddress1.setText(documentSnapshot1.getString("address_neighborhood"));
                    editTextAddress2.setText(documentSnapshot1.getString("address_city"));
                    editTextAddress3.setText(documentSnapshot1.getString("address_zip_code"));
                    editTextAddress4.setText(documentSnapshot1.getString("address_road"));
                    editTextAddress5.setText(documentSnapshot1.getString("address_complement"));

                    bt_registerAddress.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void startComponents(){
        textViewFullName = findViewById(R.id.textViewFullName);
        keyWords_screen = findViewById(R.id.keyWords_screenAddress);
        profile_screen = findViewById(R.id.profile_screenAddress);
        editTextAddress1 = findViewById(R.id.editTextAddress1);
        editTextAddress2 = findViewById(R.id.editTextAddress2);
        editTextAddress3 = findViewById(R.id.editTextAddress3);
        editTextAddress4 = findViewById(R.id.editTextAddress4);
        editTextAddress5 = findViewById(R.id.editTextAddress5);
        bt_registerAddress = findViewById(R.id.buttonRegisterAddress);
    }
}