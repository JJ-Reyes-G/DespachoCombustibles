package com.ilcabana.atp.despachocombustible;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.ilcabana.atp.database.DatabaseHandler_;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import com.ilcabana.atp.library.Httppostaux;

public class ClassDescargarInicioApp{
	Config conf;
	String ws_datosConfiguracion   						=   Config.ws_bajarConfiguracionDispositivo;
	String ws_bajarListaEmpleadosRoza   				=   Config.ws_bajarListaEmpleadosRoza;
	String ws_cambiarPasswordUsuario					= 	Config.ws_cambiarPasswordUsuario;
	String ws_bajarQuincenas							= 	Config.ws_bajarQuincenas;
	String ws_eliminarDetallePlanillaUnadas				= 	Config.ws_eliminarDetallePlanillaUnadas;
	String ws_bajarListadoEnviosPagoTonelada 			= 	Config.ws_bajarListadoEnviosPagoTonelada;
	String ws_bajarListadoEnviosPagoToneladaRealizados 	=   Config.ws_bajarListadoEnviosPagoToneladaRealizados;
	String ws_guardarNuevoMovimientoCombustible			= 	Config.ws_guardarNuevoMovimientoCombustible;
	String ws_bajarMovCombustible						= 	Config.ws_bajarMovCombustible;

	static Httppostaux post;
	SQLiteDatabase db;
	static DatabaseHandler_ dbhelper;
	TelephonyManager tm;
	String imei;
	Context ctx;

	public ClassDescargarInicioApp(Context ctx)
	{
		
		post=new Httppostaux();	
		dbhelper = new DatabaseHandler_(ctx);
		tm = (TelephonyManager) ctx.getSystemService(ctx.TELEPHONY_SERVICE);
		imei = "";//tm.getDeviceId();
		conf = new Config();
		this.ctx = ctx;
	}


	public boolean descargarConfiguracionDispositivo( String user, String pass, String dominio )
	{
		try{
			ArrayList<NameValuePair> postparameters2send= new ArrayList<NameValuePair>();
			postparameters2send.add(new BasicNameValuePair("USERDOMINIO",dominio));
			postparameters2send.add(new BasicNameValuePair("USER",user));
			postparameters2send.add(new BasicNameValuePair("PASS",pass));
			postparameters2send.add(new BasicNameValuePair("IMEI", imei));
			postparameters2send.add(new BasicNameValuePair("NOMBREAPP" ,conf.key_nombreApp));
			postparameters2send.add(new BasicNameValuePair("APPVERSION",conf.key_versionApp));
			postparameters2send.add(new BasicNameValuePair("DBVERSION", Integer.toString(dbhelper.DATABASE_VERSION)));

			JSONArray datosjs = post.getserverdata(postparameters2send,ws_datosConfiguracion);
			Log.d(" Informe JSONArray", "Valor que contiene JSONArray los datos: " + datosjs);
			Log.d(" Informe JSONArray", "Valor que contiene POST: " + postparameters2send);
			if (datosjs != null && datosjs.length() > 0) {
				reiniciarSistema();
				for(int i=0;i<datosjs.length();i++){
					JSONObject e = datosjs.getJSONObject(i);

					db = dbhelper.getReadableDatabase();
					db.execSQL("INSERT INTO "+dbhelper.TABLE_ADMINITRACION+" VALUES ("
							+""+e.getString("usuCombId")  	     +"  , " //
							+"'"+e.getString("nombreUsuario")    +"' , " //
							+"'"+pass         					 +"' , " //
							+"'"+user       					 +"' , " //
							+"'"+e.getString("userName")  	     +"' , " //
							+"'"+e.getString("nivelAcceso")  	 +"'   " //
							+")");

					//Config.key_EmprId = e.getString("EmprId");
					//Config.key_UsuId_ = e.getString("USERID");
					db.close();
				}
				return true;
			}else{
				return false;
			}

		}catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean descargarCambiarPasswordUsuario( String USER, String PASS , String NEWPASS)
	{
		try{
			Log.d("ConfigDispositivo", "descargarConfiguracionDispositivo");
			
	    	ArrayList<NameValuePair> postparameters2send= new ArrayList<NameValuePair>();
		    postparameters2send.add(new BasicNameValuePair("USER",USER));
		    postparameters2send.add(new BasicNameValuePair("PASS",PASS));
		    postparameters2send.add(new BasicNameValuePair("NEWPASS",NEWPASS));
		    postparameters2send.add(new BasicNameValuePair("IMEI", imei));
		    postparameters2send.add(new BasicNameValuePair("NOMBREAPP" ,conf.key_nombreApp));
		    postparameters2send.add(new BasicNameValuePair("APPVERSION",conf.key_versionApp));
		    postparameters2send.add(new BasicNameValuePair("DBVERSION", Integer.toString(dbhelper.DATABASE_VERSION)));
		    
		    JSONArray datosjs = post.getserverdata(postparameters2send,ws_cambiarPasswordUsuario);
			Log.d(" Informe JSONArray", "Valor que contiene JSONArray los datos: " + datosjs);
			Log.d(" Informe JSONArray", "Valor que contiene POST: " + postparameters2send);
			if (datosjs != null && datosjs.length() > 0) {
				for(int i=0;i<datosjs.length();i++){
		  			JSONObject e = datosjs.getJSONObject(i);
		  			
			        setUpdateCampo(
							dbhelper.TABLE_ADMINITRACION,
							dbhelper.K_ADM2_PASSUSER,
							e.getString("USERPASS"),
							dbhelper.K_ADM0_ID,
							e.getString("USERID")
					);
				}
				return true;
			}else{
				return false;
			}			

		}catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}

	public boolean ws_bajarListadoEnviosPagoToneladaRealizados(String EmprId, String UsuId, String QuinId)
	{
		try{
			Log.d("MovimientosQuincena", "ws_bajarListadoEnviosPagoToneladaRealizados");

			ArrayList<NameValuePair> postparameters2send= new ArrayList<NameValuePair>();
			postparameters2send.add(new BasicNameValuePair("EmprId", EmprId));
			postparameters2send.add(new BasicNameValuePair("UsuId", UsuId));
			postparameters2send.add(new BasicNameValuePair("CuaId", QuinId));

			JSONArray datosjs = post.getserverdata(postparameters2send,ws_bajarListadoEnviosPagoToneladaRealizados);
			Log.d(" Informe JSONArray", "Valor que contiene JSONArray los datos: " + datosjs);
			Log.d(" Informe JSONArray", "Valor que contiene POST: " + postparameters2send);
			if (datosjs != null && datosjs.length() > 0) {

				db = dbhelper.getReadableDatabase();
				String queryDelete = "";
					queryDelete = "DELETE FROM "+dbhelper.TABLE_ENVIOS_REALIZADOS+
						" WHERE "+dbhelper.K_ENVR3_ESTATUSLOCAL +" = '0' "
					;
				db.execSQL(queryDelete);

				Log.d(" Insert delete", "" + queryDelete);
				db.close();
				for(int i=0;i<datosjs.length();i++){
					JSONObject e = datosjs.getJSONObject(i);

					db = dbhelper.getReadableDatabase();
					String query = "INSERT INTO " + dbhelper.TABLE_ENVIOS_REALIZADOS + " ( " +
							dbhelper.K_ENVR1_ENVCOD			+",  " +
							dbhelper.K_ENVR2_ENVDESC		+",  " +
							dbhelper.K_ENVR3_ESTATUSLOCAL	+",  " +
							dbhelper.K_ENVR4_INGRESO_MANUAL	+"   " +
							") VALUES ( " +
							" '"+e.getString("TACO") 		+"', " +
							" '"+e.getString("TACODESC") 	+"', " +
							" '0', " +
							" '0'  " +
							" ) ";

					Log.d(" Insert query", "" + query);
					db.execSQL(query);

					db.close();
				}

				return true;
			}else{
				return true;
			}

		}catch(Exception e){
			e.printStackTrace();
			return false;

		}
	}

	
	public String ws_eliminarDetallePlanillaUnadas( String USER, String LLAVE_DETALLE )
	{
		String mensaje = "";
		try{
	    	ArrayList<NameValuePair> postparameters2send= new ArrayList<NameValuePair>();
		    postparameters2send.add(new BasicNameValuePair("UsuId",Config.getKey_UsuId_()));
		    postparameters2send.add(new BasicNameValuePair("LLAVE_DETALLE",LLAVE_DETALLE));
		    int ESTADO_DIA, LLAVE;
		    
		    JSONArray datosjs = post.getserverdata(postparameters2send,ws_eliminarDetallePlanillaUnadas);
			Log.d(" Informe JSONArray", "Valor que contiene JSONArray los datos: " + datosjs);
			Log.d(" Informe JSONArray", "Valor que contiene POST: " + postparameters2send);
			if (datosjs != null && datosjs.length() > 0) {
				
				JSONObject json_data; //creamos un objeto JSON
				json_data = datosjs.getJSONObject(0); //leemos el primer segmento en nuestro caso el unico
				ESTADO_DIA = json_data.getInt("cantidad");//accedemos al valor

					if(ESTADO_DIA == 0){
					eliminarDetalle("'"+LLAVE_DETALLE+"'");
					}else{
						mensaje = "No se pudo eliminar tarea";
					}
				return mensaje;
			}else{
				return mensaje;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return mensaje;
	}

	public boolean ws_bajarListadoEnviosPagoTonelada (String Taco,String Placa)
	{
		try{
			Log.d("descargarInsertActivos", "descargarInsertActivos");
			ArrayList<NameValuePair> postparameters2send= new ArrayList<NameValuePair>();
			postparameters2send.add(new BasicNameValuePair("Taco",Taco));
			postparameters2send.add(new BasicNameValuePair("Placa", Placa));

			JSONArray datosjs = post.getserverdata(postparameters2send,ws_bajarListadoEnviosPagoTonelada);
			Log.e(" Informe JSONArray", "Valor que contiene JSONArray los datos: " + datosjs);
			db = dbhelper.getReadableDatabase();
			String queryDelete = "";
			queryDelete = "DELETE FROM "+dbhelper.TABLE_ENVIOS;

			db.execSQL(queryDelete);
			Log.d(" Insert delete", "" + queryDelete);
			db.close();
			if (datosjs != null && datosjs.length() > 0) {

				for(int i=0; i<datosjs.length(); i++){
					JSONObject e = datosjs.getJSONObject(i);
					db = dbhelper.getReadableDatabase();
					db.execSQL("INSERT INTO "+dbhelper.TABLE_ENVIOS+" VALUES ("
							+""+e.getString("TACO")				+",   " //0 CORRELATIVO QUE VIENE DEL WEB SERVICE
							+"'DESC UNIDAD EJEMPLO' , " //1
							+"'"+e.getString("LOTEDESC")       	+"' , " //6
							+"'"+e.getString("PLACA")     		+"' , "	//6
							+"'"+e.getString("TIPOTRACAR")  	+"' , "	//6
							+"'"+e.getString("CODLOTE")  		+"' , "	//6
							+"'"+e.getString("CODTRA")  		+"' , "	//6
							+"'"+e.getString("CODMOTOR")  		+"' , "	//6
							+"'"+e.getString("nomMotor")  		+"' , "	//6
							+"'"+e.getString("licencia")  		+"' , "	//6
							+"'"+e.getString("cantComb")  		+"'   "	//6
							+")");

					db.close();

				}
				return true;
			}else{
				return false;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}


	public boolean bajarListadoEnviosPagoTonelada (String EmprId,String UsuId)
	{
		try{

			Log.d("descargarInsertActivos", "descargarInsertActivos");
	    	ArrayList<NameValuePair> postparameters2send= new ArrayList<NameValuePair>();
		    postparameters2send.add(new BasicNameValuePair("EmprId", EmprId));
			postparameters2send.add(new BasicNameValuePair("UsuId", UsuId));
		    JSONArray datosjs = post.getserverdata(postparameters2send,ws_bajarListadoEnviosPagoTonelada);
		    Log.d(" Informe JSONArray", "Valor que contiene JSONArray los datos: " + datosjs);
		if (datosjs != null && datosjs.length() > 0) {

			db = dbhelper.getReadableDatabase();
			String queryDelete = "";
				queryDelete = "DELETE FROM "+dbhelper.TABLE_ENVIOS;

			db.execSQL(queryDelete);
			Log.d(" Insert query", "" + queryDelete);
			db.close();

			for(int i=0; i<datosjs.length(); i++){
	  			JSONObject e = datosjs.getJSONObject(i);
		        db = dbhelper.getReadableDatabase();
		        db.execSQL("INSERT INTO "+dbhelper.TABLE_ENVIOS+" VALUES ("
		        		+""+e.getString("TACO")				+",   " //0 CORRELATIVO QUE VIENE DEL WEB SERVICE
						+"'DESC UNIDAD EJEMPLO' , " //1
	        			+"'"+e.getString("LOTEDESC")       	+"' , " //1
	        			+"'"+e.getString("PLACA")     		+"' , "	//5
	        			+"'"+e.getString("TIPOTRACAR")  	+"'   "	//6
	        			+")");  
		        db.close();
			}
			return true;
		}else{
			return false;
		}			
			
		}catch (Exception e) {
			e.printStackTrace();
		}	
		return true;		
	}

	public boolean ws_guardarNuevoMovimientoCombustible(String MovCombustibleId){
		try{
			ArrayList<NameValuePair> postparameters2send= new ArrayList<NameValuePair>();

			db = dbhelper.getReadableDatabase();
			int LLAVE = 0;
			int resp_id = 0;

			String query = " SELECT "
					+ dbhelper.K_COMB0_ID          		+ " , "
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

					+" FROM  " +dbhelper.TABLE_DESPACHO_COMBUSTIBLE	+" a "
					+" WHERE 	a."+dbhelper.K_COMB11_LLAVE+" = '0' "
					+" AND CASE WHEN '"+MovCombustibleId+"' = '0' THEN a."+dbhelper.K_COMB0_ID+" = a."+dbhelper.K_COMB0_ID+" ELSE a."+dbhelper.K_COMB0_ID+" = '"+MovCombustibleId+"' END "
					+" ORDER BY a."+dbhelper.K_COMB0_ID+" DESC  LIMIT 1 "
					//+" AND 		a."+dbhelper.K_COMB0_ID+" = '"+MovCombustibleId+"' "
					;
			Log.e("INFORME QUERY", "MI QUERY: " + query);
			Cursor c = db.rawQuery(query, null);
			if(c.moveToFirst()){
				do{

					resp_id = Integer.parseInt(c.getString(0));
					postparameters2send.add(new BasicNameValuePair("numTaco"  		,c.getString(1)));
					postparameters2send.add(new BasicNameValuePair("placa" 			,c.getString(2)));
					postparameters2send.add(new BasicNameValuePair("codTransp" 		,c.getString(3)));
					postparameters2send.add(new BasicNameValuePair("usuIngresa" 	,c.getString(4)));
					postparameters2send.add(new BasicNameValuePair("fechaMov" 		,c.getString(5)));
					postparameters2send.add(new BasicNameValuePair("NOS" 			,c.getString(6)));
					postparameters2send.add(new BasicNameValuePair("galonesBomba" 	,c.getString(7)));
					postparameters2send.add(new BasicNameValuePair("precio" 		,c.getString(8)));
					postparameters2send.add(new BasicNameValuePair("corte" 			,c.getString(9)));
					postparameters2send.add(new BasicNameValuePair("docCorrelativo" ,c.getString(10)));


					JSONArray datosjs = post.getserverdata(postparameters2send, ws_guardarNuevoMovimientoCombustible);
					Log.e(" Informe JSONArray", "Valor que contiene JSONArray los datos: " + datosjs);
					Log.e(" Informe JSONArray", "Valor que contiene JSONArray los datos: " + postparameters2send);

					db = dbhelper.getReadableDatabase();
					String queryDelete = "";
					queryDelete = "DELETE FROM "+dbhelper.TABLE_ENVIOS;

					db.execSQL(queryDelete);
					Log.d(" Insert delete", "" + queryDelete);

					if (datosjs != null && datosjs.length() > 0) {

						JSONObject json_data; //creamos un objeto JSON
						json_data = datosjs.getJSONObject(0); //leemos el primer segmento en nuestro caso el unico
						LLAVE = json_data.getInt("idMovComb");//accedemos al valor

						Log.e("Informe", "Resultado web service resultado:" + LLAVE);//muestro por log que obtuvimos

						setUpdateCampo(
								dbhelper.TABLE_DESPACHO_COMBUSTIBLE,
								dbhelper.K_COMB11_LLAVE,
								"'"+LLAVE+"'",
								dbhelper.K_COMB0_ID,
								Integer.toString(resp_id)
						);
					}
				}while(c.moveToNext());
			}
			Log.e("INFORME POST", "valor que lo valores post: " + postparameters2send);

			c.close();
			db.close();

		}catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public boolean ws_bajarMovCombustible(String EmprId)
	{
		try{
			Log.d("ConfigDispositivo", "descargarConfiguracionDispositivo");

			ArrayList<NameValuePair> postparameters2send= new ArrayList<NameValuePair>();
			postparameters2send.add(new BasicNameValuePair("EmprId", EmprId));

			JSONArray datosjs = post.getserverdata(postparameters2send,ws_bajarMovCombustible);
			Log.d(" Informe JSONArray", "Valor que contiene JSONArray los datos: " + datosjs);
			Log.d(" Informe JSONArray", "Valor que contiene POST: " + postparameters2send);
			if (datosjs != null && datosjs.length() > 0) {

				db = dbhelper.getReadableDatabase();
				db.execSQL("DELETE FROM "+dbhelper.TABLE_DESPACHO_COMBUSTIBLE);
				db.close();
				for(int i=0;i<datosjs.length();i++){
					JSONObject e = datosjs.getJSONObject(i);

					db = dbhelper.getReadableDatabase();
					String queryInsert = "INSERT INTO " + dbhelper.TABLE_DESPACHO_COMBUSTIBLE + " ( "
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
							+ " '" + e.getString("compcomb")   	 + "' , "
							+ " '" + e.getString("placa")   	 + "' , "
							+ " '" + e.getString("trans")   	 + "' , "
							+ " '" + e.getString("usucom")   	 + "' , "
							+ " '" + e.getString("fechacom")   	 + "' , "
							+ " '" + e.getString("NOS")   		 + "' , "
							+ " '" + e.getString("galonesbomba") + "' , "
							+ " '" + e.getString("precio")   	 + "' , "
							+ " '" + e.getString("corte")   	 + "' , "
							+ " '" + e.getString("numTurno")   	 + "' , "
							+ " '" + e.getString("idMovComb")    + "'   "
							+" ) "
							;

					db.execSQL(queryInsert);

					db.close();
				}

				return true;
			}else{
				return false;
			}

		}catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}




	public boolean bajarListaEmpleadosRoza(String EmprId)
	{
		try{
			Log.d("ConfigDispositivo", "descargarConfiguracionDispositivo");
			
	    	ArrayList<NameValuePair> postparameters2send= new ArrayList<NameValuePair>();
		    postparameters2send.add(new BasicNameValuePair("EmprId", EmprId));

		    JSONArray datosjs = post.getserverdata(postparameters2send,ws_bajarListaEmpleadosRoza);
			Log.d(" Informe JSONArray", "Valor que contiene JSONArray los datos: " + datosjs);
			Log.d(" Informe JSONArray", "Valor que contiene POST: " + postparameters2send);
			if (datosjs != null && datosjs.length() > 0) {
				
		        db = dbhelper.getReadableDatabase();
		        db.execSQL("DELETE FROM "+dbhelper.TABLE_EMPLEADOS); 
		        db.close();
				for(int i=0;i<datosjs.length();i++){
		  			JSONObject e = datosjs.getJSONObject(i);
		  			
			        db = dbhelper.getReadableDatabase();
			        db.execSQL("INSERT INTO "+dbhelper.TABLE_EMPLEADOS+" VALUES ( "
			        			+""+e.getString("CORRELATIVO")    		+"  , " //0 CORRELATIVO QUE VIENE DEL WEB SERVICE
			        			+"'"+e.getString("COD_EMPLEADO")        +"' , " //1	
			        			+"'"+e.getString("NOM_EMPLEADO")   		+"' , " //2
			        			+" "+e.getString("COD_CUADRILLA") 		+"  , " //3
			        			+"'"+e.getString("NOM_CUADRILLA")  	  	+"' , " //4
			        			+"'"+e.getString("COD_FRENTE")   	 	+"' , " //5
			        			+"'"+e.getString("NOM_FRENTE")    		+"' , " //6	
			        			+"'"+e.getString("NOM_TIPO")    		+"' , " //6	
			        			+"'0' " //ESTATUS DISPONIBLE
			        			+")");  
			        
			        db.close();
				}

				return true;
			}else{
				return false;
			}			

		}catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}

	
	public boolean bajarQuincenas(String EmprId)
	{
		try{
			Log.d("Metodo descargarConfi", "descargarConfiguracionDispositivo");
			
	    	ArrayList<NameValuePair> postparameters2send= new ArrayList<NameValuePair>();
		    postparameters2send.add(new BasicNameValuePair("EmprId",EmprId));

		    JSONArray datosjs = post.getserverdata(postparameters2send,ws_bajarQuincenas);
			Log.d(" Informe JSONArray", "Valor que contiene JSONArray los datos: " + datosjs);
			Log.d(" Informe JSONArray", "Valor que contiene POST: " + postparameters2send);
			if (datosjs != null && datosjs.length() > 0) {
				
		        db = dbhelper.getReadableDatabase();
		        db.execSQL("DELETE FROM "+dbhelper.TABLE_QUINCENAS); 
		        db.close();
				for(int i=0;i<datosjs.length();i++){
		  			JSONObject e = datosjs.getJSONObject(i);
		  			
			        db = dbhelper.getReadableDatabase();
			        db.execSQL("INSERT INTO "+dbhelper.TABLE_QUINCENAS+" VALUES ( "
			        			+""+e.getString("IDQUINCENA")      	+"  , " //0 CORRELATIVO QUE VIENE DEL WEB SERVICE
			        			+"'"+e.getString("FECHADESDE")     	+"' , " //1	
			        			+"'"+e.getString("FECHAHASTA")   	+"' , " //2
			        			+"'"+e.getString("ZAFRA") 			+"' , " //3
			        			+"'"+e.getString("CORTE") 			+"' , " //3
			        			+"'"+Config.getFecha()				+"'   " //3
			        			+")");  

			        db.close();
				}

				return true;
			}else{
				return false;
			}			

		}catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}


	public void setUpdateCampo(String nombreTabla, String campoTablaUpdate, String miCampoValue, String campoTablaCondicion, String miCondicion)
	{
        db = dbhelper.getReadableDatabase();
        String query = "UPDATE "+nombreTabla+" SET "+
        		campoTablaUpdate+"  = "+miCampoValue+" "+
        		" WHERE "+campoTablaCondicion+" = "+miCondicion;
        db.execSQL(query);
        db.close();
		Log.e("MI QUERY", "Valor:" + query);

          
	}

	public boolean reiniciarSistema(){
		SQLiteDatabase db;
		DatabaseHandler_ dbhelper = new DatabaseHandler_(ctx);
		db = dbhelper.getReadableDatabase();

		db.execSQL("DELETE FROM " + dbhelper.TABLE_ADMINITRACION);
		db.execSQL("DELETE FROM " + dbhelper.TABLE_EMPLEADOS);
		db.execSQL("DELETE FROM " + dbhelper.TABLE_PLANILLA_DETALLE);
		db.execSQL("DELETE FROM " + dbhelper.TABLE_PROVEDORES);
		db.execSQL("DELETE FROM " + dbhelper.TABLE_ACTIVIDADES);

		db.close();
		return true;
	}
	
	public void eliminarDetalle(String LLAVE_DETALLE)
	{

        db = dbhelper.getReadableDatabase();
        String query = "DELETE FROM "+dbhelper.TABLE_PLANILLA_DETALLE +" WHERE "+dbhelper.K_DET5_LLAVE+ " = "+LLAVE_DETALLE;
        Log.d("MI QUERY DELETE", "Valor:" + query);
        db.execSQL(query);
        db.close();

          
	}
	


}
