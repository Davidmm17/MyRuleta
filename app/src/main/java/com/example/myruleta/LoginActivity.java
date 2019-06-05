package com.example.myruleta;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private Button buttonRegistro;
    private EditText editTextMail;
    private EditText editTextPassword;

    private ProgressDialog loadingBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() != null){

        }

        buttonRegistro = findViewById(R.id.btnLogIn);
        editTextMail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.conrasenya);

        loadingBar = new ProgressDialog(this);

    }

    public void loginUsuario(View view){
        String email = editTextMail.getText().toString();
        String password = editTextPassword.getText().toString();
        System.out.println("/////////////////////////////////////////////////////////////////////////////////////");

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Falta el email", Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Falta contraseña", Toast.LENGTH_SHORT).show();
        }
        else{
            mAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(LoginActivity.this, "Login Correcto", Toast.LENGTH_SHORT).show();
                                System.out.println("Me cago en dios sin errores");
                                loadingBar.dismiss();
                            }
                            else {
                                String message = task.getException().toString();
                                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                                System.out.println("Me cago en dios error");
                                loadingBar.dismiss();
                            }
                        }
                    });
        }
    }
}
