package com.haila.myapplication2.forms;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.haila.myapplication2.MainActivity;
import com.haila.myapplication2.R;

public class FormLogin extends AppCompatActivity {

    private EditText editTextPersonEmail1, editTextPersonPassword2;
    private TextView textViewRegister;
    private ProgressBar progressBar;
    private Button bt_login;
    String[] messages = {"Preencha todos os campos"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_login);

        getSupportActionBar().hide();
        startComponents();

        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FormLogin.this, FormRegisterUser.class);
                startActivity(intent);

            }
        });

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = editTextPersonEmail1.getText().toString();
                String password = editTextPersonPassword2.getText().toString();

                if (email.isEmpty() || password.isEmpty()){

                    Snackbar snackbar = Snackbar.make(v, messages[0], Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }else {
                    AuthenticateUser(v);
                }
            }
        });

    }

    private void AuthenticateUser(View view){

        String email = editTextPersonEmail1.getText().toString();
        String password = editTextPersonPassword2.getText().toString();

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    progressBar.setVisibility(View.VISIBLE);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mainScreen();
                        }
                    }, 2000);
                } else {
                    String error;

                    try {
                        throw task.getException();
                    }catch (Exception e){
                        error = "Erro ao realizar login";
                    }

                    Snackbar snackbar = Snackbar.make(view, error, Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null){
            profileScreen();
        }
    }

    private void mainScreen(){
        Intent intent = new Intent(FormLogin.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void profileScreen(){
        Intent intent = new Intent(FormLogin.this, FormProfile.class);
        startActivity(intent);
        finish();
    }

    private void startComponents(){
        bt_login = findViewById(R.id.button_login);
        textViewRegister = findViewById(R.id.textView4);
        editTextPersonEmail1 = findViewById(R.id.editTextPersonEmail1);
        editTextPersonPassword2 = findViewById(R.id.editTextPersonPassword2);
        progressBar = findViewById(R.id.progressbar);
    }
}