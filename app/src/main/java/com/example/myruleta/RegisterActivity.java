package com.example.myruleta;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.security.PrivilegedAction;

public class RegisterActivity extends AppCompatActivity {

    private Button buttonRegistro;
    private EditText editTextMail;
    private EditText editTextPassword;

    private ProgressDialog loadingBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



        mAuth = FirebaseAuth.getInstance();
        buttonRegistro = findViewById(R.id.btnLogIn);
        editTextMail = findViewById(R.id.emailRegister);
        editTextPassword = findViewById(R.id.conrasenyaRegister);

        loadingBar = new ProgressDialog(this);



    }

    @Override
    protected void onStart() {
        super.onStart();

        if(mAuth.getCurrentUser() != null){
            SendUserToLoginActivity();
        }
    }

    public void createNewUser(View view) {
        final String email = editTextMail.getText().toString();
        String password = editTextPassword.getText().toString();
        System.out.println("/////////////////////////////////////////////////////////////////////////////////////");
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Falta el email", Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Falta contraseña", Toast.LENGTH_SHORT).show();
        }
        else{
            System.out.println("Me cago en dios creando cuenta usuario");
            loadingBar.setTitle("Creating new account");
            loadingBar.setMessage("Espera Por favor");
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                Usuario user = new Usuario(100);
                                FirebaseDatabase.getInstance().getReference("usuarios")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            SendUserToLoginActivity();
                                            Toast.makeText(RegisterActivity.this, "Cuenta creada", Toast.LENGTH_SHORT).show();
                                            System.out.println("Me cago en dios sin errores");
                                            loadingBar.dismiss();
                                        }
                                        else{
                                            String message = task.getException().toString();
                                            Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                                            System.out.println("Me cago en dios error");
                                            System.out.println(message);
                                            loadingBar.dismiss();
                                        }

                                    }
                                });


                            }
                            else {
                                String message = task.getException().toString();
                                Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                                System.out.println("Me cago en dios error");
                                loadingBar.dismiss();
                            }
                        }
                    });
        }


    }


    private void SendUserToLoginActivity(){
        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);
    }
}
