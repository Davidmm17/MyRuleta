package com.example.myruleta;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    Button botoJugar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth =  FirebaseAuth.getInstance();
        botoJugar = findViewById(R.id.botonJugar);

        if(mAuth.getCurrentUser() == null){
            botoJugar.setEnabled(false);
            System.out.println("No estas logeado");
        }
        else{
            botoJugar.setEnabled(true);
            System.out.println("Si estas logeado");
            System.out.println(mAuth.getCurrentUser().getEmail());
        }
        FirebaseUser user = mAuth.getCurrentUser();


    }





    public void jugarRuleta(View view){
        Intent intent = new Intent(this, RuletaActivity.class);

        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example, menu);

        return true;
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.item1:



                if(!isMyServiceRunning(MyService.class)){
                    startService(new Intent(this, MyService.class));
                    Toast.makeText(this,"Encender musica", Toast.LENGTH_SHORT).show();
                }
                else if (isMyServiceRunning(MyService.class)){
                    stopService(new Intent(this, MyService.class));
                    Toast.makeText(this,"Apagar musica ", Toast.LENGTH_SHORT).show();
                }

                return true;

            case R.id.item2:
                Toast.makeText(this,"Webview Selected ", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                return true;
            case  R.id.item3:
                Toast.makeText(this,"Webview Selected ", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(this, LoginActivity.class);
                startActivity(intent2);
                return true;
            case  R.id.item4:
                Toast.makeText(this,"LogOut ", Toast.LENGTH_SHORT).show();
                if(mAuth.getCurrentUser() != null){
                    mAuth.signOut();
                    Intent intent4 = new Intent(this, LoginActivity.class);
                    startActivity(intent4);
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
