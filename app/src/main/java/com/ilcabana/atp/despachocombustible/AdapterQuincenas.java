package com.ilcabana.atp.despachocombustible;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.ilcabana.atp.database.DatabaseHandler_;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterQuincenas {
	DecimalFormat decimales = new DecimalFormat("0.00");
	Context ctx;
	public SimpleAdapter adapterQuincenas (Context ctx, String FILTRO, String QuinId, String FECHA_DIA, String ENV0_ID){
		this.ctx = ctx;
		SQLiteDatabase db;
		DatabaseHandler_ dbhelper = new DatabaseHandler_(ctx);
		db = dbhelper.getReadableDatabase();

		String queryIntroSubConsultaTotalPagarPendientes = "";
		String queryIntroSubConsultaTotalPagarGuardadas = "";
		String queryIntroSubConsultaPendientes = "";

		queryIntroSubConsultaTotalPagarPendientes = " SELECT SUM(m."+dbhelper.K_DET4_CANTUNADAS+") FROM "//
				+dbhelper.TABLE_PLANILLA_DETALLE+" AS m"
				+" WHERE m."+dbhelper.K_DET6_QUINID    +" = a."+dbhelper.K_QUI0_ID+" "
				+" AND   m."+dbhelper.K_DET5_LLAVE    +" = '0' "
				+ " AND (CASE WHEN '"+FECHA_DIA+"' = '' THEN m."+dbhelper.K_DET7_FECHA+" = m."+dbhelper.K_DET7_FECHA+" ELSE m."+dbhelper.K_DET7_FECHA+" LIKE '%"+FECHA_DIA+"%' END ) "
				+ " AND (CASE WHEN '"+ENV0_ID+"' = '0' THEN m."+dbhelper.K_DET9_NOTACO+" = m."+dbhelper.K_DET9_NOTACO+" ELSE m."+dbhelper.K_DET9_NOTACO+" LIKE '"+ENV0_ID+"' END) "
		;

		queryIntroSubConsultaTotalPagarGuardadas = " SELECT SUM(m."+dbhelper.K_DET4_CANTUNADAS+") FROM "//m."+dbhelper.K_DET7_CANTIDAD+" * m."+dbhelper.K_DET12_PRECIO+"
				+dbhelper.TABLE_PLANILLA_DETALLE+" AS m"
				+" WHERE m."+dbhelper.K_DET6_QUINID    +" = a."+dbhelper.K_QUI0_ID+" "
				+" AND   m."+dbhelper.K_DET5_LLAVE    +" <> '0' "
				+ " AND (CASE WHEN '"+FECHA_DIA+"' = '' THEN m."+dbhelper.K_DET7_FECHA+" = m."+dbhelper.K_DET7_FECHA+" ELSE m."+dbhelper.K_DET7_FECHA+" LIKE '%"+FECHA_DIA+"%' END ) "
				+ " AND (CASE WHEN '"+ENV0_ID+"' = '0' THEN m."+dbhelper.K_DET9_NOTACO+" = m."+dbhelper.K_DET9_NOTACO+" ELSE m."+dbhelper.K_DET9_NOTACO+" LIKE '"+ENV0_ID+"' END) "

		;

		queryIntroSubConsultaPendientes = " SELECT COUNT(*) FROM "
				+dbhelper.TABLE_PLANILLA_DETALLE+" AS m"
				+" WHERE m."+dbhelper.K_DET6_QUINID    +" = a."+dbhelper.K_QUI0_ID+" "
				+" AND   m."+dbhelper.K_DET5_LLAVE    +" = 0 "
				+ " AND (CASE WHEN '"+FECHA_DIA+"' = '' THEN m."+dbhelper.K_DET7_FECHA+" = m."+dbhelper.K_DET7_FECHA+" ELSE m."+dbhelper.K_DET7_FECHA+" LIKE '%"+FECHA_DIA+"%' END ) "
				+ " AND (CASE WHEN '"+ENV0_ID+"' = '0' THEN m."+dbhelper.K_DET9_NOTACO+" = m."+dbhelper.K_DET9_NOTACO+" ELSE m."+dbhelper.K_DET9_NOTACO+" LIKE '%"+ENV0_ID+"%' END) "
		;
		
		String query = "SELECT "
				+ " a."+dbhelper.K_QUI0_ID				+", "
				+ " a."+dbhelper.K_QUI1_FECHA_INICIO	+", "
				+ " a."+dbhelper.K_QUI2_FECHA_FIN		+", "
				+ " a."+dbhelper.K_QUI3_ZAFRA			+", "
				+ " a."+dbhelper.K_QUI4_CORTE			+", "
				+" ifnull(( "+queryIntroSubConsultaTotalPagarPendientes+" ), 0) AS TOTALPENDIENTES1, "
				+" ifnull(( "+queryIntroSubConsultaPendientes+" ), 0) AS TOTALPENDIENTES,  "
				+" ifnull(( "+queryIntroSubConsultaTotalPagarGuardadas+" ), 0) AS TOTALGUARDADAS  "

				+ " FROM   "+dbhelper.TABLE_QUINCENAS+" AS a "
				+ " WHERE "
				+ " CASE WHEN '"+QuinId+"' = '' THEN a."+dbhelper.K_QUI0_ID+" = a."+dbhelper.K_QUI0_ID+" ELSE a."+dbhelper.K_QUI0_ID+" LIKE '"+QuinId+"' END  "
				+ " AND( CASE WHEN '"+FILTRO+"' = '' THEN a."+dbhelper.K_QUI1_FECHA_INICIO+" = a."+dbhelper.K_QUI1_FECHA_INICIO+" ELSE a."+dbhelper.K_QUI1_FECHA_INICIO+" LIKE '%"+FILTRO+"%' END "
				+ " OR   CASE WHEN '"+FILTRO+"' = '' THEN a."+dbhelper.K_QUI2_FECHA_FIN+" = a."+dbhelper.K_QUI2_FECHA_FIN+" ELSE a."+dbhelper.K_QUI2_FECHA_FIN+" LIKE '%"+FILTRO+"%' END )"
				;
		
		Log.i("QUERY", "QUERY: " + query);

		ArrayList<HashMap<String, String>> myListActivosOcaciones = new ArrayList<HashMap<String, String>>();
		String[] fromActivos = new String[] {
				"tv_row_QuinId",
				"tv_row_rango_fecha",
				"tv_row_zafra",
				"tv_row_totalquincena_pendiente",
				"tv_row_detpendientes",
				"tv_fecdesde_quin",
				"tv_fechasta_quin",
				"img_candado_quin",
				"tv_row_totalquincena_guardadas",
				"tv_row_totalquincena"};
		int[] toActivos = new int[] {
				R.id.tv_row_QuinId,
				R.id.tv_row_rango_fecha,
				R.id.tv_row_empnombre,
				R.id.tv_row_totalquincena_pendiente,
				R.id.tv_row_detpendientes_dia,
				R.id.tv_fecdesde_quin,
				R.id.tv_fechasta_quin,
				R.id.img_candado_quin,
				R.id.tv_row_totalquincena_guardadas,
				R.id.tv_row_totalquincena};
		
        Cursor cFrentes = db.rawQuery(query, null);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdflocal = new SimpleDateFormat("dd-MM-yyyy");
        if(cFrentes.moveToFirst()){
            do{	
    			HashMap<String, String> map = new HashMap<String, String>();
    			map.put("COD_QUINCENA"	, cFrentes.getString(0));
				try {
					map.put("tv_row_QuinId"			, ""+cFrentes.getString(0));
					map.put("tv_row_rango_fecha" 	, sdflocal.format(sdf.parse(cFrentes.getString(1))) + " / " + sdflocal.format(sdf.parse(cFrentes.getString(2))));
					map.put("tv_row_zafra"			, cFrentes.getString(3));
					map.put("tv_fecdesde_quin"		, ""+sdflocal.format(sdf.parse(cFrentes.getString(1))) );
					map.put("tv_fechasta_quin"		, ""+sdflocal.format(sdf.parse(cFrentes.getString(2))) );
					map.put("tv_idquincena"			, cFrentes.getString(4));
					map.put("FECHA_INICIO"			, sdflocal.format(sdf.parse(cFrentes.getString(1))) );
					map.put("FECHA_FIN"				, sdflocal.format(sdf.parse(cFrentes.getString(2))) );
					map.put("tv_row_totalquincena_pendiente"	, "Pend: "+decimales.format(Double.parseDouble(cFrentes.getString(5))).toString());
					map.put("tv_row_detpendientes"	, cFrentes.getString(6));
					map.put("tv_row_totalquincena_guardadas"	, "Guar: "+decimales.format(Double.parseDouble(cFrentes.getString(7))).toString());
					map.put("tv_row_totalquincena"              , "Tot: "+decimales.format(Double.parseDouble(cFrentes.getString(7)) + Double.parseDouble(cFrentes.getString(5))).toString());

					if(cFrentes.getString(6).equals("0")){
						map.put("img_candado_quin"  	, ""+R.drawable.close);
						map.put("tv_row_detpendientes"	, "");
					}else{
						map.put("img_candado_quin"  , ""+R.drawable.open);
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			
    			map.put("ZAFRA"			, cFrentes.getString(3));

    			myListActivosOcaciones.add(map); 
   	
            }while(cFrentes.moveToNext());
        }
        
        cFrentes.close();
        db.close();	

        SimpleAdapter adapterlistEmpleados = new MyAdapterQuincenas(
        		ctx, 
        		myListActivosOcaciones, 
        		R.layout.row_lista_quincenas,
        		fromActivos, 
        		toActivos) {

          };
        
        return adapterlistEmpleados;
	}
	
	private class MyAdapterQuincenas extends SimpleAdapter {
		Context ctx;
	    public MyAdapterQuincenas(Context context, List<? extends Map<String, ?>> data,
	            int resource, String[] from, int[] to) {
	        super(context, data, resource, from, to);
			ctx = context;
	    }

	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	        View v = super.getView(position, convertView, parent);

			Button b =(Button)v.findViewById(R.id.btn_check_estatus);
			Button ibt_consuta =(Button)v.findViewById(R.id.ibtn_consulta_tacos);

			TextView quincena =(TextView)v.findViewById(R.id.tv_row_QuinId);
			final TextView tv_fecdesde_quin =(TextView)v.findViewById(R.id.tv_fecdesde_quin);
			final TextView tv_fechasta_quin =(TextView)v.findViewById(R.id.tv_fechasta_quin);
			final String idQuin = quincena.getText().toString();

			b.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					addDiaPlanilla(tv_fecdesde_quin.getText().toString(), tv_fechasta_quin.getText().toString(), idQuin, "0");
					//ventaListCuadrilla("0", idQuin, tv_fecdesde_quin.getText().toString(), tv_fechasta_quin.getText().toString(), 1);
				}
			});

			ibt_consuta.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					//consultaTacosRealizados( idQuin);
					//ventaListCuadrilla("0", idQuin, tv_fecdesde_quin.getText().toString(), tv_fechasta_quin.getText().toString(), 2);
				}
			});

	        return v;
	    }

		public void addDiaPlanilla(final String FECHA_INICIO, final String FECHA_FIN, final String QuinId, final String ENV0_ID){
			Log.d("ADD ACTIVIDADES", " 1 ");
			//final String fechaInicioQuincena = this.fechaInicioQuincena;
			AdapterDiasQincena adpDias = new AdapterDiasQincena();
			final ListView myListActiv = new ListView(ctx);
			LinearLayout layout = new LinearLayout(ctx);
			layout.setOrientation(LinearLayout.VERTICAL);

			myListActiv.setAdapter(adpDias.adapterDiasQincena(ctx, FECHA_INICIO, FECHA_FIN, QuinId));
			AlertDialog.Builder builder = new AlertDialog.Builder(ctx);

			layout.addView(myListActiv);

			builder.setView(layout);
			final AlertDialog AlertDialogUpdate = builder.create();

			AlertDialogUpdate.setTitle("" + QuinId + " -> " + FECHA_INICIO + " / " + FECHA_FIN);

			AlertDialogUpdate.show();
			final Intent i= new Intent(ctx, ActEnviosRealizados.class);
			myListActiv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View vista,
										int posicion, long arg3) {
					AlertDialogUpdate.cancel();
					HashMap<?, ?> itemList = (HashMap<?, ?>) myListActiv.getItemAtPosition(posicion);
					//Toast.makeText(ctx, "Opcion quincena dia seleccionado!", Toast.LENGTH_SHORT).show();

					i.putExtra("COD_QUINCENA", QuinId);
					i.putExtra("FECHA_DIA",    itemList.get("FECHA_DIA").toString());
					i.putExtra("NOMBRE_DIA",   itemList.get("COD_QUINCENA").toString());

					ctx.startActivity(i);

					Log.e("ACTIVIDADES", "HORA: " + Config.getHora());

				}
			});

		}
/*
		public void consultaTacosRealizados(final String QuinId){
			Log.d("ADD ACTIVIDADES", " 1 ");
			AdapterEnviosRealizados adpEnviosRealizados = new AdapterEnviosRealizados();
			final ListView myListTacos = new ListView(ctx);
			LinearLayout layout = new LinearLayout(ctx);
			layout.setOrientation(LinearLayout.VERTICAL);

			final Preferencias pref = new Preferencias(this.ctx);

			myListTacos.setAdapter(adpEnviosRealizados.adapterEnviosRealizados(ctx, "0", "", QuinId, "", true));
			AlertDialog.Builder builder = new AlertDialog.Builder(ctx);

			layout.addView(myListTacos);
			builder.setView(layout);
			final AlertDialog AlertDialogUpdate = builder.create();

			AlertDialogUpdate.setTitle("Consulta de tacos");

			AlertDialogUpdate.show();
			final Intent i= new Intent(ctx, ActQuincenas.class);//(ctx, ActQuincenas.class);
			myListTacos.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View vista,
										int posicion, long arg3) {
					AlertDialogUpdate.cancel();
					HashMap<?, ?> itemList = (HashMap<?, ?>) myListTacos.getItemAtPosition(posicion);
					pref.setPREF_COD_TACO(itemList.get("K_ENV0_ID").toString());
					i.putExtra("ID_CUADRILLA"	, "0");
					i.putExtra("COD_QUINCENA"	, QuinId);
					i.putExtra("FECHA_DIA"		, "");
					i.putExtra("ENV0_ID"		, itemList.get("K_ENV0_ID").toString());

					ctx.startActivity(i);
					((Activity) ctx).finish();

					Log.i("ACTIVIDADES", "HORA: " + Config.getHora());
				}
			});

		}*/
	}


}
