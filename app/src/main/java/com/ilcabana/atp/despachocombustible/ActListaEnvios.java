package com.ilcabana.atp.despachocombustible;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ilcabana.atp.database.DatabaseHandler_;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Calendar;
import java.util.HashMap;


import com.ilcabana.atp.library.Httppostaux;

public class ActListaEnvios extends AppCompatActivity implements SearchView.OnQueryTextListener {
    Httppostaux post;
    private ProgressDialog pDialog;
    ListView lv_envios;
    SQLiteDatabase db;
    DatabaseHandler_ dbhelper;
    int tipoLista;
    TableLayout tbl_btn_fecha_hora_quema;
//sdsdjsd
    SimpleAdapter simpleAdaptador;
    private SearchView mSearchView;
    Context ctx = this;
    AdapterEnvios adpEnvios = new AdapterEnvios();
    static String COD_QUINCENA, FECHA_DIA, COD_CUADRILLA, ENV0_ID, NOMBRE_DIA;
    static String NUMTACO, CODLOTE, DESCLOTE;
    static int estadoBotonMenu = 2;
    ClassDescargarInicioApp classDescargarInicioApp;
    String TacoNumero = "0";
    String PlacaNumero = "0";
    TextView tv_fecha_quin;
    EditText txt_codTransportista, txt_placa, txt_num_nos, txt_num_correlativo, txt_precioGalon, txt_galonesBomba, txt_comentario, txt_num_taco; //txt_num_uniadas_otro_frente
    //TextInputLayout laytxt_uniadas_otro_frente;
    Spinner spinner_numcorte;
    private int mYear, mMonth, mDay;
    String fechaNewAplicacion = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_lista_envios);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Snackbar.make(view, "Opcion en mantenimiento"+spinner_numcorte.getSelectedItem().toString(), Snackbar.LENGTH_LONG)
                        .setAction("Accion", null).show();
                */
                if(txt_comentario.getText().toString().equals(""))
                    txt_comentario.setText("0");

                if(     txt_comentario.getText().toString().equals("")
                        || txt_placa.getText().toString().equals("")
                        || txt_num_taco.getText().toString().equals("")

                        || txt_precioGalon.getText().toString().equals("")
                        || txt_galonesBomba.getText().toString().equals("")
                        || txt_num_nos.getText().toString().equals("")
                        || txt_num_correlativo.getText().toString().equals("")
                        )
                {
                    Toast.makeText(ctx, "Todos los campos con arterisco son obligatorios", Toast.LENGTH_SHORT).show();
                    Snackbar.make(view, "Todos los campos con arterisco son obligatorios", Snackbar.LENGTH_LONG)
                            .setAction("Advertencia", null).show();
                }else {
                    if (Config.key_codigoUser.equals("") || Config.key_codigoUser.equals(null)) {
                        Toast.makeText(ctx, "Inicie sesion de nuevo", Toast.LENGTH_SHORT).show();
                    } else{ if (setInsertDespachoCombustible(
                            txt_num_taco.getText().toString(),
                            txt_placa.getText().toString(),
                            txt_codTransportista.getText().toString(),
                            Config.key_codigoUser,
                            FECHA_DIA,
                            txt_num_nos.getText().toString(),
                            txt_galonesBomba.getText().toString(),
                            txt_precioGalon.getText().toString(),
                            spinner_numcorte.getSelectedItem().toString(),
                            txt_num_correlativo.getText().toString()
                    )) {
                        if (verificaConexion(ctx)) {
                            metodoRespaldarEnvios();
                        } else {
                            Toast.makeText(ctx, "No hay conexión a Internet, verifique y reintentar", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(ctx, ActDespachoCombustible.class);
                            startActivity(i);
                        }
                    }
                }
                }

            }
        });

        txt_codTransportista = (EditText)findViewById(R.id.txt_codTransportista);
        txt_placa           = (EditText)findViewById(R.id.txt_placa);
        txt_num_taco        = (EditText)findViewById(R.id.txt_num_taco);
        txt_precioGalon     = (EditText)findViewById(R.id.txt_precioGalon);
        txt_galonesBomba    = (EditText)findViewById(R.id.txt_galonesBomba);
        txt_num_nos         = (EditText)findViewById(R.id.txt_num_nos);
        txt_num_correlativo = (EditText)findViewById(R.id.txt_num_correlativo);

        txt_comentario = (EditText)findViewById(R.id.txt_comentario);


        classDescargarInicioApp = new ClassDescargarInicioApp(this);
        post     = new Httppostaux();
        dbhelper = new DatabaseHandler_(this);


        //txt_num_uniadas_otro_frente.setEnabled(false);
        /*
        txt_uniadas.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(ctx,"Campo habilitado para digitar las uñadas de la otra cuadrilla", Toast.LENGTH_LONG).show();
                laytxt_uniadas_otro_frente.setHint("Uñadas otro frente");
                txt_num_uniadas_otro_frente.setEnabled(true);
                //((Activity)mContext).openContextMenu(v);
                return true;
            }
        });
        */

        COD_QUINCENA 	= "1";//getIntent().getExtras().getString("COD_QUINCENA");
        FECHA_DIA 		= getFecha();//getIntent().getExtras().getString("FECHA_DIA");
        NOMBRE_DIA      = "Lunes";//getIntent().getExtras().getString("NOMBRE_DIA");
        ENV0_ID 		= "";//getIntent().getExtras().getString("ENV0_ID");

        tv_fecha_quin = (TextView)findViewById(R.id.tv_fecha_quin);
        tv_fecha_quin.setText(""+NOMBRE_DIA +" "+FECHA_DIA);

        tbl_btn_fecha_hora_quema = (TableLayout)findViewById(R.id.tbl_btn_fecha_hora_quema);


        if(tipoLista != 1){
            tipoLista = 2;
        }

        post=new Httppostaux();
        lv_envios = (ListView)findViewById(R.id.lv_envios);
        lv_envios.setAdapter(adpEnvios.adapterEnvios(ctx, "0", ""));

        lv_envios.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View vista,
                                    int posicion, long arg3) {
                HashMap<?, ?> itemList = (HashMap<?, ?>) lv_envios.getItemAtPosition(posicion);

                ENV0_ID = itemList.get("K_ENV0_ID").toString();
                NUMTACO = itemList.get("K_ENV0_ID").toString();
                CODLOTE = itemList.get("K_ENV5_CODLOTE").toString();
                DESCLOTE = itemList.get("K_ENV2_LOTEDESC").toString();

                txt_placa.setText(itemList.get("K_ENV3_PLACA").toString());
                txt_num_taco.setText(itemList.get("K_ENV0_ID").toString());
                txt_codTransportista.setText(itemList.get("K_ENV6_CODTRA").toString());
                txt_precioGalon.setText(itemList.get("ultimoPrecio").toString());

            }
        });

        txt_galonesBomba.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                DecimalFormat df = new DecimalFormat("########.##");
                txt_comentario.setText("Total: "+Double.parseDouble(validarCampoVacio(txt_precioGalon.getText().toString())) * Double.parseDouble(validarCampoVacio(txt_galonesBomba.getText().toString())));
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });

        spinner_numcorte = (Spinner)findViewById(R.id.spinner_numcorte);
        ArrayAdapter<String> spinnerPorcentajeArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.porcentaje_carga));
        spinner_numcorte.setAdapter(spinnerPorcentajeArrayAdapter);


        addTaco();
    }



    public boolean onQueryTextChange(String newText) {

            estadoBotonMenu = 1;
            lv_envios.setAdapter(null);
            lv_envios.setAdapter(adpEnvios.adapterEnvios(ctx, newText, ""));
            Log.i("home", "Button action_envios!S");

        return true;
    }

    public boolean onQueryTextSubmit(String text) {

            estadoBotonMenu = 1;
            lv_envios.setAdapter(null);
            lv_envios.setAdapter(adpEnvios.adapterEnvios(ctx, text, ""));
            Log.i("home", "Button action_envios!S");


        return true;
    }

/*
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
*/

    public String getFecha (){
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a");
        String formattedDate = df.format(c.getTime());
        return formattedDate;

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.act_lista_envios, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) searchItem.getActionView();
        mSearchView.setQueryHint("Search...");
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
            case R.id.action_envios_descargar:
                addTaco();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public  void metodoIniciarDescargaEnvios(){
        Log.i("settings", "Button settings descargar un taco en particular");
        estadoBotonMenu = 1;
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Descarga de envios....");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        new MiTareaAsincronaDialog(this,0, 1).execute();
    }

    public  void metodoRespaldarEnvios(){
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
        final EditText txtNumTaco = Config.txtCapturaNumDecimal("Numero Taco","", this);
        //final EditText txtNumPlaca = Config.txtCapturaTexto("Numero Placa","", this);

        Log.i("ADD NUMERO DE TACO", " 2 ");

        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        layout.addView(txtNumTaco);
        //layout.addView(txtNumPlaca);
        builder.setView(layout);

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                TacoNumero = txtNumTaco.getText().toString();
                //PlacaNumero = txtNumPlaca.getText().toString();

                metodoIniciarDescargaEnvios();
                /*
                if ((Double.parseDouble(validarCampoVacio( txtCantidad.getText().toString() )) > 0)) {
                    TacoNumero = txtCantidad.getText().toString();
                    metodoIniciarDescargaEnvios();
                }
                */
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

    public void intentRESULT_OK(){
        Intent i= getIntent();
        this.setResult(this.RESULT_OK, i);
        finish();
        Intent e= new Intent(this, ActDespachoCombustible.class);
        startActivity(e);
    }

    public void intentRESULT_FALLO(){
        Intent i= getIntent();
        i.putExtra("ENV0_ID", ENV0_ID);
        this.setResult(this.RESULT_CANCELED, i);
        finish();
        Intent e= new Intent(this, ActDespachoCombustible.class);
        startActivity(e);
    }


    public void intentCancel(){
        Intent i= getIntent();

        i.putExtra("COD_QUINCENA" , COD_QUINCENA);
        i.putExtra("ID_CUADRILLA" , COD_CUADRILLA);
        i.putExtra("FECHA_DIA"	  , FECHA_DIA);
        i.putExtra("ENV0_ID"	  , ENV0_ID);

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
                        if (classDescargarInicioApp.ws_bajarListadoEnviosPagoTonelada(validarCampoVacio(TacoNumero), "0")
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
                    if (classDescargarInicioApp.ws_guardarNuevoMovimientoCombustible("0")){
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
                Toast.makeText(this.ctx, "ACTIVIDAD CORRECTA!", Toast.LENGTH_LONG).show();
                Log.d("Login =", "onPostExecute, BIEN Tarea de descarga de secuencia finalizada exitosamente");
                lv_envios.setAdapter(adpEnvios.adapterEnvios(ctx, "0", ""));
                switch (numActividad) {

                    case 2:
                        intentRESULT_OK();
                }

            }else{
                pDialog.dismiss();
                Toast.makeText(this.ctx, "No se pudo completar la operacion", Toast.LENGTH_LONG).show();
                Log.d("Login =", "onPostExecute, ERROR Tarea de descarga no fue completada exitosamente");
                lv_envios.setAdapter(adpEnvios.adapterEnvios(ctx, "0", ""));
                switch (numActividad) {
                    case 2:
                        intentRESULT_FALLO();
                }
            }
        }

        @Override
        protected void onCancelled() {
            Toast.makeText(this.ctx, "Tarea cancelada!",
                    Toast.LENGTH_SHORT).show();

        }
    }
    public String validarCampoVacio(String valor){
        if (valor.equals(""))
            valor = "0";
        return valor;
    }

    public boolean setInsertDespachoCombustible(
            String K_COMB1_NUMTACO,
            String K_COMB2_PLACA,
            String K_COMB3_NUMTRASP,
            String K_COMB4_USUARIO,
            String K_COMB5_FECHA,
            String K_COMB6_NOS,
            String K_COMB7_GAL_BOMBA,
            String K_COMB8_GAL_PRECIO,
            String K_COMB9_CORTE,
            String K_COMB10_CORRELATIVO
    ){
        try {
            SQLiteDatabase db;
            DatabaseHandler_ dbhelper = new DatabaseHandler_(ctx);
            db = dbhelper.getReadableDatabase();
            String query = "INSERT INTO " + dbhelper.TABLE_DESPACHO_COMBUSTIBLE + " ( "
                    + dbhelper.K_COMB1_NUMTACO          + " , "
                    + dbhelper.K_COMB2_PLACA            + " , "
                    + dbhelper.K_COMB3_NUMTRASP         + " , "
                    + dbhelper.K_COMB4_USUARIO          + " , "
                    + dbhelper.K_COMB5_FECHA            + " , "
                    + dbhelper.K_COMB6_NOS              + " , "
                    + dbhelper.K_COMB7_GAL_BOMBA        + " , "
                    + dbhelper.K_COMB8_GAL_PRECIO       + " , "
                    + dbhelper.K_COMB9_CORTE            + " , "
                    + dbhelper.K_COMB10_CORRELATIVO     + " , "
                    + dbhelper.K_COMB11_LLAVE           + "   "

                    + " ) VALUES ( "
                    + " '" + K_COMB1_NUMTACO            + "' , "
                    + " '" + K_COMB2_PLACA              + "' , "
                    + " '" + K_COMB3_NUMTRASP           + "' , "
                    + " '" + K_COMB4_USUARIO            + "' , "
                    + " '" + K_COMB5_FECHA              + "' , "
                    + " '" + K_COMB6_NOS                + "' , "
                    + " '" + K_COMB7_GAL_BOMBA          + "' , "
                    + " '" + K_COMB8_GAL_PRECIO         + "' , "
                    + " '" + K_COMB9_CORTE              + "' , "
                    + " '" + K_COMB10_CORRELATIVO       + "' , "
                    + " '0'   "

                    +" ) "
                    ;
            db.execSQL(query);
            db.close();
            Log.e("MI QUERY", "Valor:" + query);
            return true;
        }catch( Exception e){
            Log.e("Exception", "Exception: "+e);
            return false;

        }
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

    public void MostrarCalendario(final String IDSECUENCIA, final int tipoOperacion)
    {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        // FECHA PICKER
        DatePickerDialog dpd = new DatePickerDialog(ctx,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        CharSequence strDate = null;
                        Time chosenDate = new Time();
                        chosenDate.set(dayOfMonth, monthOfYear, year);
                        long dtDob = chosenDate.toMillis(true);

                        strDate = DateFormat.format("dd-MM-yyyy", dtDob);

                        fechaNewAplicacion = strDate.toString();

                        final Calendar c = Calendar.getInstance();
                        //final Intent i= new Intent(ctx, DetallesLabores.class);
                        /*
                        if(tipoOperacion == 1) {
                            if (adpLaboresLotes.insertNewAplicacion(ctx, IDSECUENCIA, fechaNewAplicacion)) {
                                i.putExtra("ID", adpLaboresLotes.ultimoIdMuestra(ctx));
                                startActivity(i);
                                finish();

                            }
                        }else{
                            addNuevaVisita(fechaNewAplicacion, IDSECUENCIA);
                        }
                        */

                    }
                }, mYear, mMonth, mDay);
        dpd.show();
    }

/*
    public boolean setUpdateCampo(
            String EN1_ENVCOD,
            String EN2_ENVDESC,
            String EN5_COMENTARIO,
            String EN6_NTACO,
            String EN7_UNIADAS,
            String EN8_USUARIO,
            String EN9_CODLOTE,
            String EN10_PORCENTAJE,
            String EN11_FECHAREG,
            String EN12_FECHADIA,
            String EN13_RASTRAPLACA,
            String EN15_QUINID
    )
    {
        try {
            SQLiteDatabase db;
            DatabaseHandler_ dbhelper = new DatabaseHandler_(ctx);
            db = dbhelper.getReadableDatabase();
            String query = "INSERT INTO " + dbhelper.TABLE_ENVIOS_REALIZADOS + " ( "
                    + dbhelper.K_ENVR1_ENVCOD           + " , "
                    + dbhelper.K_ENVR2_ENVDESC          + " , "
                    + dbhelper.K_ENVR3_ESTATUSLOCAL     + " , "
                    + dbhelper.K_ENVR4_INGRESO_MANUAL   + " , "
                    + dbhelper.K_ENVR5_COMENTARIO       + " , "
                    + dbhelper.K_ENVR6_NTACO            + " , "
                    + dbhelper.K_ENVR7_UNIADAS          + " , "
                    + dbhelper.K_ENVR8_USUARIO          + " , "
                    + dbhelper.K_ENVR9_CODLOTE          + " , "
                    + dbhelper.K_ENVR10_PORCENTAJE      + " , "
                    + dbhelper.K_ENVR11_FECHAREG        + " , "
                    + dbhelper.K_ENVR12_FECHADIA        + " , "
                    + dbhelper.K_ENVR13_RASTRAPLACA     + " , "
                    + dbhelper.K_ENVR14_LLAVE           + " , "
                    + dbhelper.K_ENVR15_QUINID          + "   "

                    + " ) VALUES ( "
                    + " '" + EN1_ENVCOD         + "' , "
                    + " '" + EN2_ENVDESC        + "' , "
                    + " '1' , "
                    + " '1' , "
                    + " '" + EN5_COMENTARIO          + "' , "
                    + " '" + EN6_NTACO          + "' , "
                    + " '" + EN7_UNIADAS        + "' , "
                    + " '" + EN8_USUARIO        + "' , "
                    + " '" + EN9_CODLOTE        + "' , "
                    + " '" + EN10_PORCENTAJE    + "' , "
                    + " '" + EN11_FECHAREG      + "' , "
                    + " '" + EN12_FECHADIA      + "' , "
                    + " '" + EN13_RASTRAPLACA   + "' , "
                    + " '0' , "
                    + " '" + EN15_QUINID        + "'  "

                    +" ) "
                    ;
            db.execSQL(query);
            db.close();
            Log.e("MI QUERY", "Valor:" + query);
            return true;
        }catch( Exception e){
            Log.e("Exception", "Exception: "+e);
            return false;

        }

    }
*/

}
