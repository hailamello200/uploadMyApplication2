package com.haila.myapplication2.forms;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.haila.myapplication2.R;

import java.util.HashMap;
import java.util.Map;

public class FormRegisterUser extends AppCompatActivity {

    private EditText editTextUserEmail1, editTextUserName2, editTextUserPassword5, editTextUserBirthDate3, editTextUserTelephone4, editTextUserName6;
    private Button bt_register;
    String[] messages = {"Preencha todos os campos.", "Cadastro realizado com sucesso."};
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_register);

        getSupportActionBar().hide();
        startComponents();

        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user_name = editTextUserName2.getText().toString();
                String user_email = editTextUserEmail1.getText().toString();
                String user_password = editTextUserPassword5.getText().toString();

                if (user_name.isEmpty() || user_email.isEmpty() || user_password.isEmpty()){
                    Snackbar snackbar = Snackbar.make(v, messages[0], Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                } else {
                    registerUser(v);
                }
            }
        });
    }

    private void registerUser(View v) {

        String user_email = editTextUserEmail1.getText().toString();
        String user_password = editTextUserPassword5.getText().toString();

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){

                    saveUserData();

                    Snackbar snackbar = Snackbar.make(v, messages[1], Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                } else {
                    String error;

                    try {
                        throw task.getException();

                    } catch (FirebaseAuthWeakPasswordException e) {
                        error = "A password precisa ter no mínimo 6 caracteres";

                    }catch (FirebaseAuthUserCollisionException e) {
                        error = "Já existe uma conta com este email";

                    }catch (FirebaseAuthInvalidCredentialsException e){
                        error = "E-mail inválido";

                    } catch (Exception e){
                        error = "error ao cadastrar usuário";
                    }

                    Snackbar snackbar = Snackbar.make(v, error, Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }
            }
        });
    }

    private void saveUserData(){

        String user_name = editTextUserName2.getText().toString();
        String user_date_birth = editTextUserBirthDate3.getText().toString();
        String user_full_name = editTextUserName6.getText().toString();
        String user_phone = editTextUserTelephone4.getText().toString();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> users = new HashMap<>();
        users.put("user_first_name", user_name);
        users.put("user_date_birth", user_date_birth);
        users.put("user_full_name", user_full_name);
        users.put("user_phone_number", user_phone);

        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("Users").document(userID);
        documentReference.set(users).addOnSuccessListener(new OnSuccessListener<Void>() {
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

        editTextUserEmail1 = findViewById(R.id.editTextUserEmail1);
        editTextUserName2 = findViewById(R.id.editTextUserName2);
        editTextUserBirthDate3 = findViewById(R.id.editTextUserBirthDate3);
        editTextUserTelephone4 = findViewById(R.id.editTextUserTelephone4);
        editTextUserPassword5 = findViewById(R.id.editTextUserPassword5);
        editTextUserName6 = findViewById(R.id.editTextUserName6);
        bt_register = findViewById(R.id.buttonRegister);
    }
}