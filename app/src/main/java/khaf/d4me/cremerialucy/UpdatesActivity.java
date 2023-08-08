package khaf.d4me.cremerialucy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;

public class UpdatesActivity extends AppCompatActivity {
    public TextView lblEstadoInternet;
    Timer timer = new Timer();
    final Handler handler = new Handler();
    public ImageButton btnActualizarServidor;
    public Button btnSincronizarTodo, btnSubir, btnBajar;
    TextView lblSubirNuevosLocales, lblSubirEditadosLocales;
    DatosLocales dbLocal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updates);
        lblEstadoInternet = (TextView) findViewById(R.id.lblEstadoConexion);
        btnActualizarServidor = (ImageButton) findViewById(R.id.ibtnActualizarServidor);
        lblSubirNuevosLocales = (TextView) findViewById(R.id.lblLocalNR);
        lblSubirEditadosLocales = (TextView) findViewById(R.id.lblLocalA);

        dbLocal = new DatosLocales(this);
        btnSincronizarTodo = (Button) findViewById(R.id.btnSincronizarTodo);
        btnSubir = (Button) findViewById(R.id.btnSubir);
        btnBajar = (Button) findViewById(R.id.btnDescargar);

        lblSubirNuevosLocales.setText(String.valueOf(dbLocal.VerProveedoresSubirNuevos().size()+
                dbLocal.VerCategoriasSubirNuevos().size()+
                dbLocal.VerProductosSubirNuevos().size()+
                dbLocal.VerCategoriaProveedorSubirNuevos().size()+
                dbLocal.VerProductoProveedorSubirNuevos().size()+
                dbLocal.VerMovimientosSubirNuevos().size()));

        lblSubirEditadosLocales.setText(String.valueOf(dbLocal.VerProveedoresSubirEditados().size()+
                dbLocal.VerCategoriasSubirEditados().size()+
                dbLocal.VerProductosSubirEditados().size()));

        llamar();
    }

    public void llamar(){
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                AsyncTask mytask = new AsyncTask() {
                    @Override
                    protected Object doInBackground(Object[] objects) {

                        new Handler (Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                double latencia = getLatency("162.241.62.225");
                                if(latencia<=50) {
                                    lblEstadoInternet.setText("EXCELENTE");
                                    btnActualizarServidor.setEnabled(true);
                                    btnSincronizarTodo.setEnabled(true);
                                    btnSubir.setEnabled(true);
                                    btnBajar.setEnabled(true);
                                }
                                else if(latencia>50 && latencia <=90) {
                                    lblEstadoInternet.setText("BUENA");
                                    btnActualizarServidor.setEnabled(true);
                                    btnSincronizarTodo.setEnabled(true);
                                    btnSubir.setEnabled(true);
                                    btnBajar.setEnabled(true);
                                }
                                else if(latencia>90 && latencia <=140) {
                                    lblEstadoInternet.setText("NORMAL");
                                    btnActualizarServidor.setEnabled(true);
                                    btnSincronizarTodo.setEnabled(true);
                                    btnSubir.setEnabled(true);
                                    btnBajar.setEnabled(true);
                                }
                                else if(latencia>140 && latencia <=190) {
                                    lblEstadoInternet.setText("MALA");
                                    btnActualizarServidor.setEnabled(false);
                                    btnSincronizarTodo.setEnabled(false);
                                    btnSubir.setEnabled(false);
                                    btnBajar.setEnabled(false);
                                }
                                else if(latencia>190 && latencia <=210) {
                                    lblEstadoInternet.setText("PESIMA");
                                    btnActualizarServidor.setEnabled(false);
                                    btnSincronizarTodo.setEnabled(false);
                                    btnSubir.setEnabled(false);
                                    btnBajar.setEnabled(false);
                                }
                                else {
                                    lblEstadoInternet.setText("NO HAY");
                                    btnActualizarServidor.setEnabled(false);
                                    btnSincronizarTodo.setEnabled(false);
                                    btnSubir.setEnabled(false);
                                    btnBajar.setEnabled(false);
                                }
                            }
                        });

                        return null;
                    }
                };
                mytask.execute();
            }
        };
        timer.schedule(task,0,10000);
    }

    public double getLatency(String ipAddress){
        String pingCommand = "/system/bin/ping -c 1 " + ipAddress;
        String inputLine = "";
        double avgRtt = 0;

        try {
            // execute the command on the environment interface
            Process process = Runtime.getRuntime().exec(pingCommand);
            // gets the input stream to get the output of the executed command
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            inputLine = bufferedReader.readLine();
            while ((inputLine != null)) {
                if (inputLine.length() > 0 && inputLine.contains("avg")) {  // when we get to the last line of executed ping command
                    break;
                }
                inputLine = bufferedReader.readLine();
            }
        }
        catch (IOException e){
            //Log.v(DEBUG_TAG, "getLatency: EXCEPTION");
            e.printStackTrace();
        }

        // Extracting the average round trip time from the inputLine string
        String afterEqual = inputLine.substring(inputLine.indexOf("="), inputLine.length()).trim();
        String afterFirstSlash = afterEqual.substring(afterEqual.indexOf('/') + 1, afterEqual.length()).trim();
        String strAvgRtt = afterFirstSlash.substring(0, afterFirstSlash.indexOf('/'));
        avgRtt = Double.valueOf(strAvgRtt);

        return avgRtt;
    }
}