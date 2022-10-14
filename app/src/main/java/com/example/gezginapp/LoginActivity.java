package com.example.gezginapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {
    private EditText login_eposta, login_sifre;
    private Button login_button, register_button;
    private FirebaseAuth mAuth;
    SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        String mail = pref.getString("email", "");
        String pass = pref.getString("password", "");
        String result = pref.getString("result", "");
        //if(!mail.equals("") && !pass.equals("")&& mail!= null && pass != null);
        if (result.equals("i")&& result != null && (result.equals("")){
            Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
        finish();
    }
        mAuth = FirebaseAuth.getInstance();
        login_eposta = findViewById(R.id.login_eposta);
        login_sifre = findViewById(R.id.login_sifre);
        login_button = findViewById(R.id.login_button);
        register_button = findViewById(R.id.register_button);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //trim edittext teki boşluk karakterlerini siler
                String userEmail = login_eposta.getText().toString().trim();
                String userPassword = login_sifre.getText().toString().trim();
                final SharedPreferences.Editor editor = pref.edit();
                editor.putString("result","i");
                editor.putString("email",userEmail);
                if (!userEmail.isEmpty() && !userPassword.isEmpty()) {
                    login(userEmail, userPassword);
                } else {
                    Toast.makeText(getApplicationContext(), "Email ya da parola boş bırakılamaz!", Toast.LENGTH_LONG).show();
                }
            }
        });



        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRegister();
            }
        });

        }

        public void login(String email, String password) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("EMail", "signInWithEmail:success");
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("Fail", "signInWithEmail:failure", task.getException());

                            }
                        }
                    });

    }

    public void goToRegister(){
        Intent i=new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(i);
    }
}