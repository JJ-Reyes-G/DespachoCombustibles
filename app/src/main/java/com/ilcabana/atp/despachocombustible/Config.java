package com.ilcabana.atp.despachocombustible;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Gravity;
import android.widget.EditText;

import com.ilcabana.atp.database.DatabaseHandler_;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Config extends Application {
	//private static final String key_ipserver 			= "http://10.1.1.200:8080/wsmoviles/ws_reqmovil/";
	private static final String key_ipserver 			= "http://intranet.grupolacabana.net:8080/wsmoviles/ws_reqmovil/";
	private static String planilla_roza_ws 				= "planilla_zafra_ws.php";
	private static String despacho_combustible_ws 		= "despacho_combustible_ws.php";

	public static final String ws_bajarActualizacionApp	= key_ipserver+"appmoviles/appapk/envtonelada.apk";

	//WEB SERVICES
	public static final String ws_cambiarPasswordUsuario    				= "/";
	public static final String ws_bajarListaEmpleadosRoza 		 			= key_ipserver+planilla_roza_ws+"/bajarListaEmpleadosRoza";
	public static final String ws_bajarQuincenas				 			= key_ipserver+planilla_roza_ws+"/bajarQuincenas";
	public static final String ws_bajarListadoEnviosPagoToneladaRealizados 	= key_ipserver+planilla_roza_ws+"/bajarListadoEnviosPagoToneladaRealizados";
	public static final String ws_eliminarDetallePlanillaUnadas		 		= key_ipserver+planilla_roza_ws+"/eliminarDetallePlanillaUnadas";

	public static final String ws_bajarConfiguracionDispositivo	 			= key_ipserver+despacho_combustible_ws+"/bajarConfiguracionDispositivo";
	public static final String ws_guardarNuevoMovimientoCombustible 		= key_ipserver+despacho_combustible_ws+"/guardarNuevoMovimientoCombustible";
	public static final String ws_bajarListadoEnviosPagoTonelada 			= key_ipserver+despacho_combustible_ws+"/bajarListadoEnviosPagoTonelada";
	public static final	String ws_bajarMovCombustible						= key_ipserver+despacho_combustible_ws+"/bajarMovCombustible";

	//END WEB SERVICES
	
	public static final String key_versionApp 		= "2";
	//public static final String key_nombreApp 		= "PLANILLAROZA";
	public static final String key_nombreApp 		= "DESPACHO_COMBUSTIBLE";
	public static String key_nombreUser 			= "";//NOMBRE COMPLETO DEL SISTEMA
	public static String key_codigoUser 			= "";//NOMBRE DE USUARIO JREYES EJEMPLO

		public static String getKey_UsuId_() {
			if(key_UsuId_.equals("") )
				System.exit(0);

			return key_UsuId_;
		}

		public static String key_UsuId_ 				= "";//USUARIO ID
		public static String KEY_COD_PAN_MANTENIMIENTO 	= "";
		public static String KEY_TIPO_USUARIO_ACCESO   	= "";
		public static String KEY_PASS_USUARIO			= "";

		public static String key_EmprId 				= "";

		//public static String codActivo = "";
	
		SQLiteDatabase db;
		DatabaseHandler_ dbhelper;
		//BEGIN VARIABLES STATICAS DE LA CLASE BITACORA

		//END VARIABLES STATICAS DE LA CLASE BITACORA
		public static String getFecha (){
			Calendar c = Calendar.getInstance();
			System.out.println("Current time => " + c.getTime());

			SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
			String formattedDate = df.format(c.getTime());
			return formattedDate;

		}
	
		public static String getHora (){
			Calendar c = Calendar.getInstance();
			System.out.println("Current time => " + c.getTime());

			SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
			String formattedDate = df.format(c.getTime());
			return formattedDate;
		}
	
	
		public void setdatosUsuario()
		{
			dbhelper = new DatabaseHandler_(this);
	        db = dbhelper.getReadableDatabase();
	        Cursor c = db.rawQuery(" SELECT " +
	        		dbhelper.K_ADM1_NOMBREUSER  +" "+
	        		" FROM "+dbhelper.TABLE_ADMINITRACION +" TIMIT 1", null);  
	        if(c.moveToFirst()){
	            do{
	            	key_nombreUser = c.getString(0);
	
	            }while(c.moveToNext());
	        }
	        c.close();
	        db.close();		
		}
		 public static boolean verificaConexion(Context ctx) {
			    boolean bConectado = false;
			    ConnectivityManager connec = (ConnectivityManager) ctx
			            .getSystemService(Context.CONNECTIVITY_SERVICE);
			    // No sólo wifi, también GPRS
			    NetworkInfo[] redes = connec.getAllNetworkInfo();
			    // este bucle debería no ser tan apa
			    for (int i = 0; i < 2; i++) {
			        // Tenemos conexión? ponemos a true
			        if (redes[i].getState() == NetworkInfo.State.CONNECTED) {
			            bConectado = true;
			        }
			    }
			    return bConectado;
		 }
	 
	    public static EditText txtCapturaNumDecimal(String hitText, String value, Context ctx){
		       final EditText input = new EditText(ctx);

		       //input.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
		       input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
		       input.setHint(hitText);
		       input.setText(value);
		       //CODIGO QUE PERMITE DEFINIR EL NUMERO DE CARACTERES
		       int maxLength = 300;
		       InputFilter[] fArray = new InputFilter[1];
		       fArray[0] = new InputFilter.LengthFilter(maxLength);
		       input.setFilters(fArray);
		       input.setTextSize(20);
		       //input.setTextColor(Color.rgb(0xff, 0, 0));
		       input.setTypeface(Typeface.SERIF, Typeface.ITALIC);
		       input.setSingleLine(false);
		       input.setLines(2);
		       input.setMinLines(2);
		       input.setMaxLines(2);
		       input.setGravity(Gravity.LEFT | Gravity.TOP);
		       return input;
	    }

		public static EditText txtCapturaComentario(String hitText, String value, Context ctx){
			final EditText input = new EditText(ctx);
			//final TextView input = new TextView(this);

			input.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
			//input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
			input.setText(value);
			input.setHint(hitText);
			//fragmento de codigo que permite que define el numero de caracteres
			int maxLength = 300;
			InputFilter[] fArray = new InputFilter[1];
			fArray[0] = new InputFilter.LengthFilter(maxLength);
			input.setFilters(fArray);
			input.setTextSize(20);
			input.setTextColor(Color.rgb(0xff, 0, 0));
			input.setTypeface(Typeface.SERIF, Typeface.ITALIC);
			input.setSingleLine(false);
			input.setLines(14);
			input.setMinLines(13);
			input.setMaxLines(15);
			input.setGravity(Gravity.LEFT | Gravity.TOP);
			return input;
		}
	public static EditText txtCapturaTexto(String hitText, String value, Context ctx){
		final EditText input = new EditText(ctx);
		//input.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
		input.setInputType(InputType.TYPE_CLASS_TEXT| InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);//InputType.TYPE_TEXT_FLAG_CAP_SENTENCES,
		input.setHint(hitText);
		input.setText(value);
		//CODIGO QUE PERMITE DEFINIR EL NUMERO DE CARACTERES
		int maxLength = 300;
		InputFilter[] fArray = new InputFilter[1];
		fArray[0] = new InputFilter.LengthFilter(maxLength);
		input.setFilters(fArray);
		input.setTextSize(20);
		//input.setTextColor(Color.rgb(0xff, 0, 0));
		input.setTypeface(Typeface.SERIF, Typeface.ITALIC);
		input.setSingleLine(false);
		input.setLines(2);
		input.setMinLines(2);
		input.setMaxLines(2);
		input.setGravity(Gravity.LEFT | Gravity.TOP);
		//input.setSelected(false);
		return input;
	}

	public static boolean reiniciarSistema(Context ctx){
		Preferencias pref = new Preferencias(ctx);
		pref.limpiarPreferencias();
		SQLiteDatabase db;
		DatabaseHandler_ dbhelper = new DatabaseHandler_(ctx);
		db = dbhelper.getReadableDatabase();

		db.execSQL("DELETE FROM " + dbhelper.TABLE_ADMINITRACION);
		db.execSQL("DELETE FROM " + dbhelper.TABLE_EMPLEADOS);
		db.execSQL("DELETE FROM " + dbhelper.TABLE_ENVIOS);
		db.execSQL("DELETE FROM " + dbhelper.TABLE_ENVIOS_REALIZADOS);
		db.execSQL("DELETE FROM " + dbhelper.TABLE_PLANILLA_DETALLE);
		db.execSQL("DELETE FROM " + dbhelper.TABLE_ACTIVIDADES);

		db.execSQL("DELETE FROM " + dbhelper.TABLE_QUINCENAS);
		db.execSQL("DELETE FROM " + dbhelper.TABLE_PROVEDORES);
		db.execSQL("DELETE FROM " + dbhelper.TABLE_CUADRILLAS);
		db.execSQL("DELETE FROM " + dbhelper.TABLE_ASISTENCIA_EMP);
		db.execSQL("DELETE FROM " + dbhelper.TABLE_RASTRAS_CARGADAS);
		db.execSQL("DELETE FROM " + dbhelper.TABLE_DESPACHO_COMBUSTIBLE);

		db.close();
		return  true;
	}
}