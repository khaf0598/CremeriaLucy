package khaf.d4me.cremerialucy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

public class SplashActivity extends Activity {
    SharedPreferences Configuraciones;
    SharedPreferences.Editor EditarConfiguracion;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Configuraciones = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        EditarConfiguracion = Configuraciones.edit();

        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(Configuraciones.getInt("IdUsuario",-1)>-1) {
                    Intent intent = new Intent(SplashActivity.this, StartActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        },3000);

    }
}