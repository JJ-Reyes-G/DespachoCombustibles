package com.ilcabana.atp.despachocombustible;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.ilcabana.atp.database.DatabaseHandler_;
import com.ilcabana.atp.library.Httppostaux;

public class ActLogin extends AppCompatActivity {
    Context ctx = this;
    //String ws_datosUser = "";
    Httppostaux post;
    SQLiteDatabase db;
    DatabaseHandler_ dbhelper;
    static EditText txtuser, txtpass;//txtdominio
    String user, pass; //dominio

    Activity act;
    private ProgressDialog pDialog;
    ClassDescargarInicioApp classDescargarInicioApp;
    Preferencias pref;
    CheckBox cb_recordar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_login);

        //ActionBar bar = getActionBar();
        //bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0404B4")));

        pref = new Preferencias(this);
        post     = new Httppostaux();
        dbhelper = new DatabaseHandler_(this);
        txtuser  = (EditText) findViewById(R.id.txtuser);
        txtpass  = (EditText) findViewById(R.id.txtpass);
        //txtdominio  = (EditText) findViewById(R.id.txtdominio);

        //txtdominio.setText(pref.getPREF_USERDOMINIO());
        txtuser.setText(pref.getPREF_USERNAME());
        txtpass.setText(pref.getPREF_USERPASS());

        classDescargarInicioApp = new ClassDescargarInicioApp(this);

        cb_recordar = (CheckBox)findViewById(R.id.cb_recordar);
        if(pref.getPREF_RECORDAR_USER().equals("1"))
            cb_recordar.setChecked(true);

    }

    public void btnLogin (View v)
    {
        user = txtuser.getText().toString().trim();
        pass = txtpass.getText().toString().trim();
        //dominio = txtdominio.getText().toString().trim();

        validarCuenta(user, pass);
    }

    public void validarCuenta(String user , String pass)
    {
        pref = new Preferencias(ctx);

        if(cb_recordar.isChecked()){
            pref.setPREF_RECORDAR_USER("1");
        }else{
            pref.setPREF_RECORDAR_USER("0");
        }

        if (user.equals("") || pass.equals(""))
        {
            Toast.makeText(this, "Los campos usuario y contraseña son requeridos", Toast.LENGTH_SHORT).show();
        }else{
            db = dbhelper.getWritableDatabase();
            Cursor c = db.rawQuery("SELECT " +
                    dbhelper.K_ADM0_ID			    +", " +//0
                    dbhelper.K_ADM2_PASSUSER		+", " +//1
                    dbhelper.K_ADM1_NOMBREUSER	    +", " +//2
                    dbhelper.K_ADM3_USERNAME	    +", " +//3
                    dbhelper.K_ADM4_USUID	        +", " +//4
                    dbhelper.K_ADM5_NIVELACCESO	    +"  " +//5
                    " FROM  "+dbhelper.TABLE_ADMINITRACION+
                    " WHERE "+dbhelper.K_ADM3_USERNAME+" = '"+user+"' " +
                    " AND   "+dbhelper.K_ADM2_PASSUSER+" = '"+pass+"'" , null);
            if(c.moveToFirst()){
                do{
                    Toast.makeText(this, "Bienvenido "+c.getString(2), Toast.LENGTH_SHORT).show();
                    pref.setPREF_USERNAME(user);
                   //pref.setPREF_USERDOMINIO(dominio);
                    if(pref.getPREF_RECORDAR_USER().equals("1")) {
                        pref.setPREF_USERPASS(pass);
                    }else {
                        pref.setPREF_USERPASS("");
                    }
                    txtpass.setText(pref.getPREF_USERPASS());

                    Config.key_codigoUser 				= user;
                    Config.key_nombreUser			 	= c.getString(2);
                    Config.KEY_TIPO_USUARIO_ACCESO 	    = c.getString(5);
                    Config.key_UsuId_ 	    			= c.getString(4);
                    c.close();
                    db.close();

                    Intent i= new Intent(this, ActMenuInicio.class); //ActListaQuincenas
                    startActivity(i);

                }while(c.moveToNext());
            }else{
                pDialog = new ProgressDialog(this);
                pDialog.setMessage("Iniciando configuracion y descarga de informacion....");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(false);
                new MiTareaAsincronaDialog(this,0, 2).execute();
            }
            c.close();
            db.close();
        }
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

                case 2:
                    if(classDescargarInicioApp.descargarConfiguracionDispositivo(user, pass,"")){
                        return true;
                    }else
                        return false;

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
                Toast.makeText(this.ctx, "Descarga de credenciales correctas!",Toast.LENGTH_SHORT).show();
                Log.d("Login =", "onPostExecute, BIEN Tarea de descarga de secuencia finalizada exitosamente");
                pref.limpiarPreferencias();
                validarCuenta(user, pass);

            }else{
                pDialog.dismiss();
                Toast.makeText(this.ctx, "No se pudiron comprobar sus credenciales",Toast.LENGTH_SHORT).show();
                Log.d("Login =","onPostExecute, ERROR Tarea de descarga no fue completada exitosamente");

            }
        }

        @Override
        protected void onCancelled() {
            Toast.makeText(this.ctx, "Tarea cancelada!",
                    Toast.LENGTH_SHORT).show();

        }
    }
}
