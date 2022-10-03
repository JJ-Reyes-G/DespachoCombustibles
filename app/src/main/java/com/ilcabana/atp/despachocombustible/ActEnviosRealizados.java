package com.ilcabana.atp.despachocombustible;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.Toast;

import com.ilcabana.atp.database.DatabaseHandler_;
import com.ilcabana.atp.library.Httppostaux;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ActEnviosRealizados extends AppCompatActivity implements SearchView.OnQueryTextListener{
    Httppostaux post;
    private ProgressDialog pDialog;
    ListView lv_envios_realizados;
    DatabaseHandler_ dbhelper;
    int tipoLista;
    TableLayout tbl_btn_fecha_hora_quema;

    SimpleAdapter simpleAdaptador;
    private SearchView mSearchView;
    Context ctx = this;

    AdapterEnviosRealizados adpEnviosRealizados = new AdapterEnviosRealizados();
    static String COD_QUINCENA, FECHA_DIA, ENV0_ID, NOMBRE_DIA;
    ClassDescargarInicioApp classDescargarInicioApp;
    String TacoNumero = "0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_envios_realizados);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        COD_QUINCENA 	= getIntent().getExtras().getString("COD_QUINCENA");
        FECHA_DIA 		= getIntent().getExtras().getString("FECHA_DIA");
        NOMBRE_DIA      = getIntent().getExtras().getString("NOMBRE_DIA");

        Toast.makeText(this, ""+NOMBRE_DIA,Toast.LENGTH_LONG).show();
        ENV0_ID 		= "";//getIntent().getExtras().getString("ENV0_ID");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Intent i;
                i= new Intent(ctx, ActListaEnvios.class);
                i.putExtra("COD_QUINCENA", COD_QUINCENA);
                i.putExtra("FECHA_DIA", FECHA_DIA);
                i.putExtra("NOMBRE_DIA", NOMBRE_DIA);

                startActivityForResult(i, 1);
            }
        });

        classDescargarInicioApp = new ClassDescargarInicioApp(this);
        post     = new Httppostaux();
        dbhelper = new DatabaseHandler_(this);



        tbl_btn_fecha_hora_quema = (TableLayout)findViewById(R.id.tbl_btn_fecha_hora_quema);


        if(tipoLista != 1){
            tipoLista = 2;
        }

        post=new Httppostaux();
        lv_envios_realizados = (ListView)findViewById(R.id.lv_envios_realizados);
        lv_envios_realizados.setAdapter(adpEnviosRealizados.adapterEnviosRealizados(ctx, "0", "", COD_QUINCENA, FECHA_DIA, true));

        lv_envios_realizados.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View vista,
                                    int posicion, long arg3) {
                HashMap<?, ?> itemList = (HashMap<?, ?>) lv_envios_realizados.getItemAtPosition(posicion);

                ENV0_ID = itemList.get("K_ENV0_ID").toString();
                /*
                if (estadoBotonMenu == 1) {
                    Toast.makeText(ctx, "MensajeUbicacion MENSAJE DE EJEMPLO1 " + ENV0_ID, Toast.LENGTH_SHORT).show();

                    String ENVDESC = itemList.get("tvr_num_placa").toString() + " LOTE: " + itemList.get("tvr_codnom_lote").toString();
                    if (adpEnvios.setInsertTacos(ENV0_ID, ENVDESC, ctx)) {
                        Toast.makeText(ctx, "MensajeUbicacion MENSAJE DE EJEMPLO2 " + ENV0_ID, Toast.LENGTH_SHORT).show();

                        intentRESULT_OK();
                        finish();
                    }
                } else {
                    Toast.makeText(ctx, "MensajeUbicacion MENSAJE DE EJEMPLO3 " + ENV0_ID, Toast.LENGTH_SHORT).show();
                    intentRESULT_OK();
                    finish();
                }
                */
            }
        });
    }

    public boolean onQueryTextChange(String newText) {

        lv_envios_realizados.setAdapter(null);
        lv_envios_realizados.setAdapter(adpEnviosRealizados.adapterEnviosRealizados(ctx, newText, "", COD_QUINCENA, FECHA_DIA, true));
        Log.i("home", "Button action_envios!S");

        return true;
    }

    public boolean onQueryTextSubmit(String newText) {

        lv_envios_realizados.setAdapter(null);
        lv_envios_realizados.setAdapter(adpEnviosRealizados.adapterEnviosRealizados(ctx, newText, "", COD_QUINCENA, FECHA_DIA, true));

        return true;
    }


    public static boolean verificaConexion(Context ctx) {
        boolean bConectado = false;
        ConnectivityManager connec = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        // No sólo wifi, también GPRS
        NetworkInfo[] redes = connec.getAllNetworkInfo();
        // este bucle debería no ser tan mapa
        for (int i = 0; i < 2; i++) {
            // Tenemos conexión? ponemos a true
            if (redes[i].getState() == NetworkInfo.State.CONNECTED) {
                bConectado = true;
            }
        }
        return bConectado;
    }

    public String getFecha (){
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss a");
        String formattedDate = df.format(c.getTime());
        return formattedDate;

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.act_envios_realizados, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) searchItem.getActionView();
        mSearchView.setQueryHint("Buscar...");
        mSearchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                intentCancel();
                Log.i("home", "Button home!S");
                return true;

            case R.id.action_respaldar_envios:
                respaldarEnviosTonelada();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public  void metodoIniciarDescargaEnvios(){
        //Toast.makeText(ctx,"Descargar Envios",Toast.LENGTH_SHORT).show();

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Descarga de envios....");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        new MiTareaAsincronaDialog(this,0, 1).execute();

    }

    public  void respaldarEnviosTonelada(){
        //Toast.makeText(ctx,"Descargar Envios",Toast.LENGTH_SHORT).show();

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Descarga de envios....");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        new MiTareaAsincronaDialog(this,0, 2).execute();

    }

    public void addTaco(){
        Log.i("ADD NUMERO DE TACO", " 1 ");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        final EditText txtCantidad = Config.txtCapturaNumDecimal("Numero Taco","", this);

        Log.i("ADD NUMERO DE TACO", " 2 ");

        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        layout.addView(txtCantidad);
        builder.setView(layout);

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //Capturamos el valor del campo de texto
                TacoNumero = txtCantidad.getText().toString();

                metodoIniciarDescargaEnvios();
                /*
                if ((Double.parseDouble(validarCampoVacio( txtCantidad.getText().toString() )) > 0)) {
                    TacoNumero = txtCantidad.getText().toString();
                    metodoIniciarDescargaEnvios();
                } else {
                    Toast.makeText(ctx, "Digite un valor correcto", Toast.LENGTH_SHORT).show();
                    addTaco();
                }*/
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //No hacemos nada..
            }
        });

        builder.setTitle("Digite el numero de taco");
        builder.show();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch(keyCode){
            case KeyEvent.KEYCODE_BACK:
                intentCancel();

                return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public  void onActivityResult(int ticket,int result,Intent datos)
    {
        if (result == this.RESULT_OK)//resultado del intent anterior
        {
            if(ticket == 1 ) //LOTEDESC  = datos.getExtras().getString("LOTEDESC");
            {
                Toast.makeText(ctx, "Ok", Toast.LENGTH_SHORT).show();

            }

        }else{
            Toast.makeText(ctx, "Fallo", Toast.LENGTH_SHORT).show();
        }
        lv_envios_realizados.setAdapter(adpEnviosRealizados.adapterEnviosRealizados(ctx, "0", "", COD_QUINCENA, FECHA_DIA, true));


    }


    public void intentRESULT_OK(){
        Intent i= getIntent();
        this.setResult(this.RESULT_OK, i);
        finish();
    }

    public void intentRESULT_FALLO(){
        Intent i= getIntent();
        this.setResult(this.RESULT_CANCELED, i);
        finish();
    }


    public void intentCancel(){
        Intent i= getIntent();

        this.setResult(this.RESULT_CANCELED, i);
        finish();
    }




    //clase que se ejecuta mientras se ejecuta la consulta el web service y el  array
    private class MiTareaAsincronaDialog extends AsyncTask<Void, Integer, Boolean> {

        Context ctx;
        int resulCountTabla;
        int numActividad;
        //numActividad numero de actividad
        public MiTareaAsincronaDialog(Context ctx, int resulCountTabla, int numActividad)
        {
            this.ctx = ctx;
            this.resulCountTabla = resulCountTabla;
            this.numActividad = numActividad;

        }

        @Override
        protected Boolean doInBackground(Void... params)
        {
            switch (numActividad) {

                case 1:
                    if (classDescargarInicioApp.ws_bajarListadoEnviosPagoToneladaRealizados(Config.key_EmprId, Config.getKey_UsuId_(), "0")
                            ){
                        return true;
                    }else{
                        if(isCancelled())
                        {
                            Log.d("Login: ", "doInBackground, Actividad cancelada TRUE");
                            return false;
                        }
                        Log.d("Login: ", "doInBackground, Actividad cancelada FALSE");

                        return false;
                    }
                case 2:
                    if (classDescargarInicioApp.ws_guardarNuevoMovimientoCombustible(COD_QUINCENA)
                            ){
                        return true;
                    }else{
                        if(isCancelled())
                        {
                            Log.d("Login: ", "doInBackground, Actividad cancelada TRUE");
                            return false;
                        }
                        Log.d("Login: ", "doInBackground, Actividad cancelada FALSE");

                        return false;
                    }



                default:
                    return false;
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values)
        {
            int progreso = values[0].intValue();

            pDialog.setProgress(progreso);
        }

        @Override
        protected void onPreExecute()
        {
            pDialog.setOnCancelListener(new DialogInterface.OnCancelListener()
            {
                @Override
                public void onCancel(DialogInterface dialog) {
                    MiTareaAsincronaDialog.this.cancel(true);
                }
            });
            pDialog.setProgress(0);
            pDialog.setMax(5000);
            pDialog.show();
        }

        @Override
        protected void onPostExecute(Boolean result)
        {
            if(result)
            {
                pDialog.dismiss();
                Toast.makeText(this.ctx, "Descarga de credenciales correctas!", Toast.LENGTH_SHORT).show();
                Log.d("Login =", "onPostExecute, BIEN Tarea de descarga de secuencia finalizada exitosamente");
                lv_envios_realizados.setAdapter(adpEnviosRealizados.adapterEnviosRealizados(ctx, "0", "", COD_QUINCENA, FECHA_DIA, true));

            }else{
                pDialog.dismiss();
                Toast.makeText(this.ctx, "No se pudiron comprobar sus credenciales", Toast.LENGTH_SHORT).show();
                Log.d("Login =", "onPostExecute, ERROR Tarea de descarga no fue completada exitosamente");
                lv_envios_realizados.setAdapter(adpEnviosRealizados.adapterEnviosRealizados(ctx, "0", "", COD_QUINCENA, FECHA_DIA, true));

            }
        }

        @Override
        protected void onCancelled() {
            Toast.makeText(this.ctx, "Tarea cancelada!",
                    Toast.LENGTH_SHORT).show();

        }
    }



}
