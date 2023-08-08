package khaf.d4me.cremerialucy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import khaf.d4me.cremerialucy.Models.UserModel;

public class MainActivity extends AppCompatActivity {
    Button btnEntrar;
    Backend_Queries backend_queries;
    EditText txtUsuario, txtContrasena;
    ProgressBar pbCargarLogin;
    SharedPreferences Configuraciones;
    SharedPreferences.Editor EditarConfiguracion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Configuraciones = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        EditarConfiguracion = Configuraciones.edit();

        backend_queries = new Backend_Queries();
        pbCargarLogin = (ProgressBar) findViewById(R.id.pbCargar);
        btnEntrar = (Button) findViewById(R.id.btnIngresar);
        txtUsuario = (EditText) findViewById(R.id.txtUsuario);
        txtContrasena = (EditText) findViewById(R.id.txtContrasena);

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!txtUsuario.getText().toString().equals("") && !txtContrasena.getText().toString().equals("")) {
                    UserModel usuario = new UserModel();
                    usuario.setUsuario(String.valueOf(txtUsuario.getText()));
                    usuario.setContrasena(String.valueOf(txtContrasena.getText()));
                    pbCargarLogin.setVisibility(View.VISIBLE);
                    backend_queries.Autenticar(usuario, getApplicationContext(), txtUsuario, txtContrasena, pbCargarLogin);
                }else
                    Toast.makeText(getApplicationContext(), "Ingrese usuario y contraseña", Toast.LENGTH_SHORT).show();
            }
        });
    }
}