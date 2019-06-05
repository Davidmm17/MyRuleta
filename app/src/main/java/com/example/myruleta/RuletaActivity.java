package com.example.myruleta;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class RuletaActivity extends AppCompatActivity {

    Button button;
    TextView textView;
    ImageView iv_wheel;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    Random r;
    ToggleButton apuesta0;
    ToggleButton apuestaRojo;
    ToggleButton apuestaNegro;
    ToggleButton apuestaNumero;
    Spinner listaApuestas;
    TextView dineroActual;
    EditText dineroApostado;
    private MediaPlayer mp;
    private static final String TAG = "RuletaActivity";
    int degree = 0, degree_old =0;

    private static final float FACTOR = 4.86f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruleta);

        mp = MediaPlayer.create(this,R.raw.sample);

        mAuth =  FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference("usuarios")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);
        iv_wheel = findViewById(R.id.iv_wheel);
        apuesta0 = findViewById(R.id.apuestaAcero);
        apuestaNegro = findViewById(R.id.apuestaAnegro);
        apuestaRojo = findViewById(R.id.apuestaArojo);
        apuestaNumero = findViewById(R.id.apuestaAnumero);
        listaApuestas = findViewById(R.id.spinner);
        dineroActual = findViewById(R.id.dineroActual);
        dineroApostado = findViewById(R.id.dineroApuesta);



        readDatabase();

        r = new Random();
        this.button.setEnabled(false);
        listaApuestas.setEnabled(false);
        this.
        apuesta0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    button.setEnabled(true);
                    apuestaNegro.setChecked(false);
                    apuestaRojo.setChecked(false);
                    apuestaNumero.setChecked(false);
                    listaApuestas.setEnabled(false);
                } else {

                }
            }
        });
        apuestaNegro.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    button.setEnabled(true);
                    apuesta0.setChecked(false);
                    apuestaRojo.setChecked(false);
                    apuestaNumero.setChecked(false);
                    listaApuestas.setEnabled(false);
                } else {

                }
            }
        });
        apuestaRojo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    button.setEnabled(true);
                    apuestaNegro.setChecked(false);
                    apuesta0.setChecked(false);
                    apuestaNumero.setChecked(false);
                    listaApuestas.setEnabled(false);
                } else {

                }
            }
        });
        apuestaNumero.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    button.setEnabled(true);
                    listaApuestas.setEnabled(true);
                    apuestaNegro.setChecked(false);
                    apuestaRojo.setChecked(false);
                    apuesta0.setChecked(false);
                } else {

                }
            }
        });
    }

    public boolean comprobarApuestaRojo(String currentNumber){
        int dineroModificado;
        if(currentNumber.equals("0")){
            Toast.makeText(this, "Has perdido la apuesta", Toast.LENGTH_SHORT).show();
            dineroModificado = Integer.parseInt(dineroActual.getText().toString()) - Integer.parseInt(dineroApostado.getText().toString());
            updateDineroActual(dineroModificado);
            return false;
        }
        String [] split = currentNumber.split(" ");
        String rojo = split[1];
        System.out.println("Cadena:"+rojo);
        if(rojo.equals("rojo")){
            Toast.makeText(this, "Has ganado la apuesta", Toast.LENGTH_SHORT).show();
            dineroModificado = Integer.parseInt(dineroActual.getText().toString()) + Integer.parseInt(dineroApostado.getText().toString());
            updateDineroActual(dineroModificado);
            return true;
        }
        else {
            Toast.makeText(this, "Has perdido la apuesta", Toast.LENGTH_SHORT).show();
            dineroModificado = Integer.parseInt(dineroActual.getText().toString()) - Integer.parseInt(dineroApostado.getText().toString());
            updateDineroActual(dineroModificado);
            return false;
        }
    }
    public boolean comprobarApuestaNegro(String currentNumber){
        int dineroModificado;
        if(currentNumber.equals("0")){
            Toast.makeText(this, "Has perdido la apuesta", Toast.LENGTH_SHORT).show();
            dineroModificado = Integer.parseInt(dineroActual.getText().toString()) - Integer.parseInt(dineroApostado.getText().toString());
            updateDineroActual(dineroModificado);
            return false;
        }
        String [] split = currentNumber.split(" ");
        String rojo = split[1];
        System.out.println("Cadena:"+rojo);
        if(rojo.equals("negro")){
            Toast.makeText(this, "Has ganado la apuesta", Toast.LENGTH_SHORT).show();
            dineroModificado = Integer.parseInt(dineroActual.getText().toString()) + Integer.parseInt(dineroApostado.getText().toString());
            updateDineroActual(dineroModificado);
            return true;
        }
        else {
            Toast.makeText(this, "Has perdido la apuesta", Toast.LENGTH_SHORT).show();
            dineroModificado = Integer.parseInt(dineroActual.getText().toString()) - Integer.parseInt(dineroApostado.getText().toString());
            updateDineroActual(dineroModificado);
            return false;
        }
    }

    public boolean comprobarApuestaNumero(String currentNumber){
        int dineroModificado;
        if(currentNumber.equals("0")){
            Toast.makeText(this, "Has perdido la apuesta", Toast.LENGTH_SHORT).show();
            dineroModificado = Integer.parseInt(dineroActual.getText().toString()) - Integer.parseInt(dineroApostado.getText().toString());
            updateDineroActual(dineroModificado);
            return false;
        }
        String [] split = currentNumber.split(" ");
        String rojo = split[0];
        System.out.println("Cadena:"+rojo);
        if(currentNumber.equals(listaApuestas.getSelectedItem().toString())){
            Toast.makeText(this, "Has ganado la apuesta", Toast.LENGTH_SHORT).show();
            dineroModificado = Integer.parseInt(dineroActual.getText().toString()) + Integer.parseInt(dineroApostado.getText().toString());
            updateDineroActual(dineroModificado);

            return true;
        }
        else {
            Toast.makeText(this, "Has perdido la apuesta", Toast.LENGTH_SHORT).show();
            dineroModificado = Integer.parseInt(dineroActual.getText().toString()) - Integer.parseInt(dineroApostado.getText().toString());
            updateDineroActual(dineroModificado);
            return false;
        }
    }
    public boolean comprobarApuestaCero(String currentNumber){
        int dineroModificado;
        if(currentNumber.equals("0")){
            Toast.makeText(this, "Has ganado la apuesta", Toast.LENGTH_SHORT).show();
            dineroModificado = Integer.parseInt(dineroActual.getText().toString()) + Integer.parseInt(dineroApostado.getText().toString());
            updateDineroActual(dineroModificado);
            return true;

        }
        else {
            Toast.makeText(this, "Has perdido la apuesta", Toast.LENGTH_SHORT).show();
            dineroModificado = Integer.parseInt(dineroActual.getText().toString()) - Integer.parseInt(dineroApostado.getText().toString());
            updateDineroActual(dineroModificado);
            return false;
        }
    }

    public void girarRuleta(View v){
        mp.start();
        degree_old = degree % 260;
        degree = r.nextInt(3600) + 720;
        RotateAnimation rotate = new RotateAnimation(degree_old , degree,
                RotateAnimation.RELATIVE_TO_SELF,0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(3600);
        rotate.setFillAfter(true);
        rotate.setInterpolator(new DecelerateInterpolator());
        rotate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                textView.setText("");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                textView.setText(currentNumber(360 - (degree % 360)));
                if(apuestaRojo.isChecked()){
                    comprobarApuestaRojo(currentNumber(360 - (degree % 360)));
                }
                if(apuestaNegro.isChecked()){
                    comprobarApuestaNegro(currentNumber(360 - (degree % 360)));
                }
                if(apuestaNumero.isChecked()){
                    comprobarApuestaNumero(currentNumber(360 - (degree % 360)));
                }
                if(apuesta0.isChecked()){
                    comprobarApuestaCero(currentNumber(360 - (degree % 360)));
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        iv_wheel.startAnimation(rotate);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example, menu);

        return true;
    }

    public void readDatabase(){
// Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Usuario user = dataSnapshot.getValue(Usuario.class);
                int dinero = user.getDinero();
                Log.d(TAG, "Value is: " + user.getDinero());
                dineroActual.setText(dinero+"");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
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
                Toast.makeText(this,"Webview Selected ", Toast.LENGTH_SHORT).show();


                if(!isMyServiceRunning(MyService.class)){
                    startService(new Intent(this, MyService.class));
                }
                else if (isMyServiceRunning(MyService.class)){
                    stopService(new Intent(this, MyService.class));
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
    private boolean updateDineroActual(int dineroModificar){
        Usuario user = new Usuario(dineroModificar);

        myRef.setValue(user);

        return true;
    }

    private String currentNumber(int degrees){
        String text = "";

        if(degrees >= (FACTOR * 1) && degrees < (FACTOR * 3)){
            text = "32 rojo";
        }
        if(degrees >= (FACTOR * 3) && degrees < (FACTOR * 5)){
            text = "15 negro";
        }
        if(degrees >= (FACTOR * 5) && degrees < (FACTOR * 7)){
            text = "19 rojo";
        }
        if(degrees >= (FACTOR * 7) && degrees < (FACTOR * 9)){
            text = "4 negro";
        }
        if(degrees >= (FACTOR * 9) && degrees < (FACTOR * 11)){
            text = "21 rojo";
        }
        if(degrees >= (FACTOR * 11) && degrees < (FACTOR * 13)){
            text = "2 negro";
        }
        if(degrees >= (FACTOR * 13) && degrees < (FACTOR * 15)){
            text = "25 rojo";
        }
        if(degrees >= (FACTOR * 15) && degrees < (FACTOR * 17)){
            text = "17 negro";
        }
        if(degrees >= (FACTOR * 17) && degrees < (FACTOR * 19)){
            text = "34 rojo";
        }
        if(degrees >= (FACTOR * 19) && degrees < (FACTOR * 21)){
            text = "6 negro";
        }
        if(degrees >= (FACTOR * 21) && degrees < (FACTOR * 23)){
            text = "27 rojo";
        }
        if(degrees >= (FACTOR * 23) && degrees < (FACTOR * 25)){
            text = "13 negro";
        }
        if(degrees >= (FACTOR * 25) && degrees < (FACTOR * 27)){
            text = "36 rojo";
        }
        if(degrees >= (FACTOR * 27) && degrees < (FACTOR * 29)){
            text = "11 negro";
        }
        if(degrees >= (FACTOR * 29) && degrees < (FACTOR * 31)){
            text = "30 rojo";
        }
        if(degrees >= (FACTOR * 31) && degrees < (FACTOR * 33)){
            text = "8 negro";
        }
        if(degrees >= (FACTOR * 33) && degrees < (FACTOR * 35)){
            text = "23 rojo";
        }
        if(degrees >= (FACTOR * 35) && degrees < (FACTOR * 37)){
            text = "10 negro";
        }
        if(degrees >= (FACTOR * 37) && degrees < (FACTOR * 39)){
            text = "5 rojo";
        }
        if(degrees >= (FACTOR * 39) && degrees < (FACTOR * 41)){
            text = "24 negro";
        }
        if(degrees >= (FACTOR * 41) && degrees < (FACTOR * 43)){
            text = "16 rojo";
        }
        if(degrees >= (FACTOR * 43) && degrees < (FACTOR * 45)){
            text = "33 negro";
        }
        if(degrees >= (FACTOR * 45) && degrees < (FACTOR * 47)){
            text = "1 rojo";
        }
        if(degrees >= (FACTOR * 47) && degrees < (FACTOR * 49)){
            text = "20 negro";
        }
        if(degrees >= (FACTOR * 49) && degrees < (FACTOR * 51)){
            text = "14 rojo";
        }
        if(degrees >= (FACTOR * 51) && degrees < (FACTOR * 53)){
            text = "31 negro";
        }
        if(degrees >= (FACTOR * 53) && degrees < (FACTOR * 55)){
            text = "9 rojo";
        }
        if(degrees >= (FACTOR * 55) && degrees < (FACTOR * 57)){
            text = "22 negro";
        }
        if(degrees >= (FACTOR * 57) && degrees < (FACTOR * 59)){
            text = "18 rojo";
        }
        if(degrees >= (FACTOR * 59) && degrees < (FACTOR * 61)){
            text = "29 negro";
        }
        if(degrees >= (FACTOR * 61) && degrees < (FACTOR * 63)){
            text = "7 rojo";
        }
        if(degrees >= (FACTOR * 63) && degrees < (FACTOR * 65)){
            text = "28 negro";
        }
        if(degrees >= (FACTOR * 65) && degrees < (FACTOR * 67)){
            text = "12 rojo";
        }
        if(degrees >= (FACTOR * 67) && degrees < (FACTOR * 69)){
            text = "35 negro";
        }
        if(degrees >= (FACTOR * 69) && degrees < (FACTOR * 71)){
            text = "3 rojo";
        }
        if(degrees >= (FACTOR * 71) && degrees < (FACTOR * 73)){
            text = "26 negro";
        }

        if((degrees >= (FACTOR * 73) && degrees < 360) || (degrees >=0 && degrees < (FACTOR * 1))){
            text = "0";
        }


        return text;
    }
}
