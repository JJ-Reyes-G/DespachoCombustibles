package com.ilcabana.atp.despachocombustible;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.ilcabana.atp.database.DatabaseHandler_;

import java.util.HashMap;

public class ActListaQuincenas extends AppCompatActivity {
	ListView lv_quincenas;
	private AdapterQuincenas adapterQuincenas;
	Context ctx = this;
	private ProgressDialog pDialog;
	String COD_QUINCENA, FECHA_INICIO, FECHA_FIN, fechaInicioQuincena;
	Preferencias pref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_act_lista_quincenas);
		lv_quincenas = (ListView)findViewById(R.id.lv_quincenas);
		adapterQuincenas = new AdapterQuincenas();
		pref = new Preferencias(ctx);

		llenarListasInicio();
		//onCLicListaQuincenas();
		//registerForContextMenu(lv_quincenas);
	}

	public void llenarListasInicio() {
		lv_quincenas.setAdapter(adapterQuincenas.adapterQuincenas(ctx, "", "", "", "0"));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.act_lista_quincenas, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		int id = item.getItemId();
		if (id == R.id.menu_reiniciar_sistema) {
			if (Config.verificaConexion(this) == false) {
				Toast.makeText(getBaseContext(),
						"Verifica tu conexion a Internet.", Toast.LENGTH_SHORT).show();
			} else {
				Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse(Config.ws_bajarActualizacionApp));
				startActivity(viewIntent);
			}
		}

		//int id = item.getItemId();
		/*
		if (id == R.id.menu_reiniciar_sistema) {
			reiniciarSistema();
			return true;

		}
		*/

		return super.onOptionsItemSelected(item);

	}
	
	public void reiniciarSistema(){
		SQLiteDatabase db;
		DatabaseHandler_ dbhelper = new DatabaseHandler_(ctx);
        db = dbhelper.getReadableDatabase();

		db.execSQL("DELETE FROM " + dbhelper.TABLE_ADMINITRACION);
		db.execSQL("DELETE FROM " + dbhelper.TABLE_EMPLEADOS);
		db.execSQL("DELETE FROM " + dbhelper.TABLE_PLANILLA_DETALLE);
		db.execSQL("DELETE FROM " + dbhelper.TABLE_PROVEDORES);
		db.execSQL("DELETE FROM " + dbhelper.TABLE_ENVIOS);
		db.execSQL("DELETE FROM " + dbhelper.TABLE_ACTIVIDADES);

        db.close();
        finish();
	}
	
	public void onCLicListaQuincenas(){
		lv_quincenas.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View vista,
									int posicion, long arg3) {
				HashMap<?, ?> itemList = (HashMap<?, ?>) lv_quincenas.getItemAtPosition(posicion);
				//modalFormRastras();
				COD_QUINCENA = itemList.get("COD_QUINCENA").toString();
				FECHA_INICIO = itemList.get("FECHA_INICIO").toString();
				fechaInicioQuincena = itemList.get("FECHA_INICIO").toString();
				FECHA_FIN = itemList.get("FECHA_FIN").toString();

				intentBuscarProveedores(itemList.get("COD_QUINCENA").toString());

			}
		});
     
		
	}

	public void  intentBuscarProveedores(String COD_QUINCENA)
	{
		//Toast.makeText(ctx, "Act lista de envios", Toast.LENGTH_SHORT).show();

		Intent i;
		i= new Intent(this, ActListaEnvios.class);
		i.putExtra("COD_QUINCENA", COD_QUINCENA);
		startActivityForResult(i, 3);

	}

	//clase que se ejecuta mientras se ejecuta la consulta el web service y el  array
	private class MiTareaAsincronaDialog extends AsyncTask<Void, Integer, Boolean> {

		Context ctx;
		String LLAVE_DETALLE;
		ClassDescargarInicioApp classDescargarInicioApp;
		String mensaje = "";
		int tipoDescarga;

		//numActividad numero de actividad
		public MiTareaAsincronaDialog(Context ctx, String LLAVE_DETALLE, int TipoDescarga) {
			this.ctx = ctx;
			classDescargarInicioApp = new ClassDescargarInicioApp(ctx);
			this.LLAVE_DETALLE = LLAVE_DETALLE;
			tipoDescarga = TipoDescarga;
		}

		@Override
		protected Boolean doInBackground(Void... params) {

			switch (tipoDescarga) {
				case 1:
					mensaje = classDescargarInicioApp.ws_eliminarDetallePlanillaUnadas(Config.key_codigoUser, LLAVE_DETALLE);
					if (mensaje == "") {

						return true;

					} else {
						if (isCancelled()) {
							Log.d("Login: ", "doInBackground, Actividad cancelada TRUE");
							return false;
						}
						Log.d("Login: ", "doInBackground, Actividad cancelada FALSE");

						return false;
					}
				case 2:
					if (classDescargarInicioApp.ws_guardarNuevoMovimientoCombustible("0")) {

						return true;

					} else {
						if (isCancelled()) {
							Log.d("Login: ", "doInBackground, Actividad cancelada TRUE");
							return false;
						}
						Log.d("Login: ", "doInBackground, Actividad cancelada FALSE");

						return false;
					}
				case 3:
					if (classDescargarInicioApp.bajarQuincenas(Config.key_EmprId)
					){
						return true;
					}else{
						if (isCancelled()) {
							Log.d("Login: ", "doInBackground, Actividad cancelada TRUE");
							return false;
						}
						Log.d("Login: ", "doInBackground, Actividad cancelada FALSE");
						return false;
					}
				case 4:
					if (classDescargarInicioApp.bajarListaEmpleadosRoza(Config.key_EmprId)
							) {

						return true;

					} else {
						if (isCancelled()) {
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
		protected void onProgressUpdate(Integer... values) {
			int progreso = values[0].intValue();

			pDialog.setProgress(progreso);
		}

		@Override
		protected void onPreExecute() {
			pDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
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
		protected void onPostExecute(Boolean result) {
			if (result) {
				pDialog.dismiss();
				Toast.makeText(this.ctx, "Completado exitosamente", Toast.LENGTH_SHORT).show();
				Log.d("Login =", "onPostExecute, BIEN Tarea de descarga de secuencia finalizada exitosamente");

				llenarListasInicio();
			} else {
				pDialog.dismiss();
				Toast.makeText(this.ctx, "" + mensaje, Toast.LENGTH_SHORT).show();
				Log.d("Login =", "onPostExecute, ERROR Tarea de descarga no fue completada exitosamente");

			}
		}
	}


	//---------------------Menu contextual--------------------------
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
									ContextMenu.ContextMenuInfo menuInfo)
	{
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_ctx_select_quincena, menu);

		AdapterView.AdapterContextMenuInfo info =
				(AdapterView.AdapterContextMenuInfo)menuInfo;
		HashMap<?, ?> itemList = (HashMap<?, ?>) lv_quincenas.getItemAtPosition(info.position);

		COD_QUINCENA = itemList.get("COD_QUINCENA").toString();

	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {

		DatabaseHandler_ dbhelper = new DatabaseHandler_(ctx);
		//final ArrayList<Parent> dummyList;

		switch (item.getItemId()) {
			//BEGIN Menu contextual de lista de secuencia de ordenes

			case R.id.CtxLstOpcCancelar:

				return super.onContextItemSelected(item);

			case R.id.CtxMeGuardarPlanilla:
				//Toast.makeText(ctx,"Btn menu GUARDAR DETALLES DE PLANILLA",	Toast.LENGTH_SHORT).show();
				pDialog = new ProgressDialog(ctx);
				pDialog.setMessage("Guardar detalles de planilla");
				pDialog.setIndeterminate(false);
				pDialog.setCancelable(false);
				new MiTareaAsincronaDialog(ctx, ""+COD_QUINCENA, 2).execute();
				return super.onContextItemSelected(item);

			case R.id.CtxLstOpcBajarQuincena:
				pDialog = new ProgressDialog(ctx);
				pDialog.setMessage("Descargar planilla de esta quincena");
				pDialog.setIndeterminate(false);
				pDialog.setCancelable(false);
				new MiTareaAsincronaDialog(ctx, ""+COD_QUINCENA, 3).execute();

				return super.onContextItemSelected(item);
			case R.id.CtxLstOpcActualizarListaEmpleados:
				pDialog = new ProgressDialog(ctx);
				pDialog.setMessage("Descargar listado de empleados");
				pDialog.setIndeterminate(false);
				pDialog.setCancelable(false);
				new MiTareaAsincronaDialog(ctx, ""+COD_QUINCENA, 4).execute();
				return super.onContextItemSelected(item);
		default:
				return super.onContextItemSelected(item);
		}
	}
	//---------------------END Menu contextual--------------------------
	/*
	public void onCLicListaQuincenas(){
		lv_quincenas.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View vista,
									int posicion, long arg3) {
				HashMap<?, ?> itemList = (HashMap<?, ?>) lv_quincenas.getItemAtPosition(posicion);

				pref.setPREF_COD_QUINCENA(itemList.get("tv_row_QuinId").toString());
				pref.setPREF_FECHAINICIO(itemList.get("FECHA_INICIO").toString());
				pref.setPREF_FECHAFIN(itemList.get("FECHA_FIN").toString());

			}
		});
	}
*/
	public void modalFormRastras(){
		Log.i("ADD ACTIVIDADES", " 1 ");

		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		final EditText txtTaco 	= Config.txtCapturaNumDecimal("Taco","", this);
		final EditText txtPlaca = Config.txtCapturaTexto("Placa", "", this);

		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);

		layout.addView(txtTaco);
		layout.addView(txtPlaca);

		builder.setView(layout);
		final AlertDialog AlertDialogUpdate = builder.create();

		AlertDialogUpdate.setTitle("Digite el Taco o la Placa");
		AlertDialogUpdate.setButton(RESULT_OK, "ACEPTAR", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				Toast.makeText(ctx, "Empleado seleccionado exitosamente", Toast.LENGTH_SHORT).show();
/*
				final Intent i = new Intent(ctx, AcDescTacos.class);
				i.putExtra("codTaco", validarCampoVacio(txtTaco.getText().toString()));
				i.putExtra("codPlaca", validarCampoVacio(txtPlaca.getText().toString()));
				startActivityForResult(i, 1);
*/
			}
		});
		AlertDialogUpdate.show();
	}

/*
	public void modalListEnvios(){
		Log.i("ADD ACTIVIDADES", " 1 ");

		final ListView myList = new ListView(this);
		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);

		AdapterEnvios adpEnvios = new AdapterEnvios();
		myList.setAdapter(adpEnvios.adapterEnvios(ctx, "0", ""));

		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);

		layout.addView(myList);

		builder.setView(layout);
		final AlertDialog AlertDialogUpdate = builder.create();

		AlertDialogUpdate.setTitle("Confirme el taco o la placa");
		AlertDialogUpdate.setButton(RESULT_OK, "ACEPTAR", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {

				if (txtCantidad.getText().toString().equals("") || txtCantidad.getText().toString().equals(".")) {
					Toast.makeText(ctx, "Digite una cantidad correcta", Toast.LENGTH_SHORT).show();
				} else {
					pref.setPREF_CANTIDAD(txtCantidad.getText().toString());
					cargarPreferencias();
				}

			}
		});
		AlertDialogUpdate.show();
		myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View vista,
									int posicion, long arg3) {
				AlertDialogUpdate.cancel();
				HashMap<?, ?> itemList = (HashMap<?, ?>) myList.getItemAtPosition(posicion);
				if (crateNewRastra(itemList.get("K_ENV0_ID").toString())) {

					pref.setPREF_COD_TACO(itemList.get("K_ENV0_ID").toString());
					pref.setPREF_COD_EMP("");
					pref.setPREF_CANT_UNIADAS("0");
					pref.setPREF_CANT_BARAZOS("0");

				}


			}
		});
	}
*/
	public boolean crateNewRastra(String K_ENV0_ID){
		SQLiteDatabase db;
		DatabaseHandler_ dbhelper = new DatabaseHandler_(ctx);
		db = dbhelper.getReadableDatabase();

		String queryNOTIN = "SELECT b."+dbhelper.K_RAS3_TACO+" FROM "+ dbhelper.TABLE_RASTRAS_CARGADAS +" AS b WHERE b."+dbhelper.K_RAS3_TACO +" = '"+K_ENV0_ID+"' ";

		String query = "INSERT INTO " + dbhelper.TABLE_RASTRAS_CARGADAS + " (" +
				dbhelper.K_RAS1_PLACA				+", " +
				dbhelper.K_RAS2_DESC				+", " +
				dbhelper.K_RAS3_TACO				+", " +
				dbhelper.K_RAS4_FECHAREG			+", " +
				dbhelper.K_RAS5_COMENTARIO			+"  " +

				") SELECT " +
				"a."+dbhelper.K_ENV3_PLACA 			+", " +
				"a."+dbhelper.K_ENV4_TIPOTRACAR 	+", " +
				"a."+dbhelper.K_ENV0_ID 			+", " +
				" date('now') , " +
				" '' " +

				" FROM " +dbhelper.TABLE_ENVIOS+" AS a "+
				" WHERE a."+dbhelper.K_ENV0_ID          +" = '"+K_ENV0_ID+"' "+
				" AND a."+dbhelper.K_ENV0_ID  +" NOT IN ("+queryNOTIN+" )"+
				" ";
		db = dbhelper.getReadableDatabase();
		db.execSQL(query);
		db.close();
		return true;

	}
	@Override
	public  void onActivityResult(int ticket,int result,Intent datos)
	{
		if (result == this.RESULT_OK)//resultado del intent anterior
		{
			if(ticket == 1 ) //LOTEDESC  = datos.getExtras().getString("LOTEDESC");
			{
				//modalListEnvios();
				Toast.makeText(ctx, "Lista de envios", Toast.LENGTH_SHORT).show();

			}
			if (ticket == 2)
			{
				Toast.makeText(ctx, "Empleado seleccionado exitosamente", Toast.LENGTH_SHORT).show();
			}
			if (ticket == 3)
			{
				Toast.makeText(ctx, "Empleado seleccionado exitosamente ejemplo juan jose", Toast.LENGTH_SHORT).show();

				if (crateNewRastra(datos.getExtras().getString("ENV0_ID"))) {

					pref.setPREF_COD_TACO(datos.getExtras().getString("ENV0_ID"));

					if(!pref.getPREF_FECHAINICIO().toString().equals("")){
						FECHA_INICIO = pref.getPREF_FECHAINICIO().toString();
					}

					addDiaPlanilla(FECHA_INICIO, FECHA_FIN, COD_QUINCENA, pref.getPREF_COD_TACO());

					Toast.makeText(ctx, "TACO: " + pref.getPREF_COD_TACO(), Toast.LENGTH_SHORT).show();
					pref.setPREF_COD_EMP("");
					pref.setPREF_CANT_UNIADAS("0");
					pref.setPREF_CANT_BARAZOS("0");

				}
			}
		}

	}

	public String validarCampoVacio(String valor){
		if (valor.equals(""))
			valor = "0";
		return valor;
	}

	public void addDiaPlanilla(final String FECHA_INICIO, final String FECHA_FIN, final String QuinId, final String ENV0_ID){
		Log.d("ADD ACTIVIDADES", " 1 ");
		final String fechaInicioQuincena = this.fechaInicioQuincena;
		AdapterDiasQincena adpDias = new AdapterDiasQincena();
		final ListView myListActiv = new ListView(ctx);
		LinearLayout layout = new LinearLayout(ctx);
		layout.setOrientation(LinearLayout.VERTICAL);

		myListActiv.setAdapter(adpDias.adapterDiasQincena(ctx, FECHA_INICIO, FECHA_FIN, QuinId));
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);

		layout.addView(myListActiv);

		builder.setView(layout);
		final AlertDialog AlertDialogUpdate = builder.create();

		AlertDialogUpdate.setTitle(""+QuinId+" -> "+fechaInicioQuincena+" / "+FECHA_FIN);

		AlertDialogUpdate.setButton(RESULT_OK, "TODAS LAS FECHAS", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {

				addDiaPlanilla(fechaInicioQuincena, FECHA_FIN, COD_QUINCENA, ENV0_ID);

			}
		});

		AlertDialogUpdate.show();
		final Intent i= new Intent(ctx, ActEnviosRealizados.class);//(ctx, ActQuincenas.class);
		myListActiv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View vista,
									int posicion, long arg3) {
				AlertDialogUpdate.cancel();
				HashMap<?, ?> itemList = (HashMap<?, ?>) myListActiv.getItemAtPosition(posicion);

				pref.setPREF_FECHAINICIO(itemList.get("FECHA_DIA").toString());

					i.putExtra("COD_QUINCENA", QuinId);
					i.putExtra("FECHA_DIA",    itemList.get("FECHA_DIA").toString());
				i.putExtra("NOMBRE_DIA",   itemList.get("COD_QUINCENA").toString());
					//i.putExtra("ENV0_ID",      ENV0_ID);
				Toast.makeText(ctx, ""+itemList.get("COD_QUINCENA").toString() ,Toast.LENGTH_LONG).show();

				//ctx.startActivity(i);
				//	((Activity) ctx).finish();

				Log.e("ACTIVIDADES", "HORA: " + Config.getHora());

			}
		});

	}

}
