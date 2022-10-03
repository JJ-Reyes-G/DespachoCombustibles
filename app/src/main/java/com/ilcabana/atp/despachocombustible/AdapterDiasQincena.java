package com.ilcabana.atp.despachocombustible;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.SimpleAdapter;
import com.ilcabana.atp.database.DatabaseHandler_;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class AdapterDiasQincena {
	DecimalFormat decimales = new DecimalFormat("0.00");
	public SimpleAdapter adapterDiasQincena (Context ctx, String FECHA_INICIO, String FECHA_FIN, String IDQUINCENA){
		
		ArrayList<HashMap<String, String>> myListActivosOcaciones = new ArrayList<HashMap<String, String>>();
		String[] fromActivos = new String[] {"FECHA_DIA", "COD_QUINCENA", "tv_titulo_quincena", "tv_row_detpendientes_dia", "img_candado_dia"};
		int[] toActivos = new int[] { R.id.tv_fecha_quincena, R.id.tv_num_quincena, R.id.tv_titulo_quincena, R.id.tv_row_detpendientes_dia, R.id.img_candado_dia};

		//long oneDayMilSec = 86400000;
		SimpleDateFormat sdf 		= new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat sdflocal 	= new SimpleDateFormat("dd-MM-yyyy");

	    Date startDate;
		Date endDate;
		try {
			startDate 		= sdf.parse(FECHA_INICIO);//("2015-02-15");
		    endDate 		= sdf.parse(FECHA_FIN);//("2015-03-15");
			Calendar start 	= Calendar.getInstance();
			start.setTime(startDate );
			Calendar end = Calendar.getInstance();
			//end.setTime(endDate);
			end.setTime(endDate);
			//end.add(Calendar.DATE, );
			//for (Date date = start.getTime(); !start.after(end); start.add(Calendar.DATE, -1), date = start.getTime()) {
			for (Date date = start.getTime(); !start.after(end); start.add(Calendar.DATE, 1), date = start.getTime()) {

				SQLiteDatabase db;
				DatabaseHandler_ dbhelper = new DatabaseHandler_(ctx);
				db = dbhelper.getReadableDatabase();

				String queryIntroSubConsultaTotalPagar = "";
				String queryIntroSubConsultaPendientes = "";

				queryIntroSubConsultaPendientes = " SELECT COUNT(*) FROM "
						+dbhelper.TABLE_ENVIOS_REALIZADOS+" AS n "
						+" WHERE n."+dbhelper.K_ENVR15_QUINID    +" = m."+dbhelper.K_ENVR15_QUINID+" "
						+" AND   n."+dbhelper.K_ENVR14_LLAVE    +" = 0 "
						+" AND   n."+dbhelper.K_ENVR12_FECHADIA    +" = m."+dbhelper.K_ENVR12_FECHADIA+" "
				;

				queryIntroSubConsultaTotalPagar = " SELECT ifnull(COUNT(m."+dbhelper.K_ENVR0_ID+"), 0) AS TOTALPAGAR, "//m."+dbhelper.K_DET7_CANTIDAD+" * m."+dbhelper.K_DET12_PRECIO+"
						+" ifnull(( "+queryIntroSubConsultaPendientes+" ), 0) AS TOTALPENDIENTES "
						+" FROM "
						+dbhelper.TABLE_ENVIOS_REALIZADOS+" AS m"
						+" WHERE m."+dbhelper.K_ENVR15_QUINID    +" = "+IDQUINCENA+" "
						+ " AND CASE WHEN '"+sdflocal.format(date)+"' = '' THEN m."+dbhelper.K_ENVR12_FECHADIA+" = m."+dbhelper.K_ENVR12_FECHADIA+" ELSE m."+dbhelper.K_ENVR12_FECHADIA+" LIKE '%"+sdflocal.format(date)+"%' END "
				;
				String[] strDays = new String[]{
						"Domingo",
						"Lunes",
						"Martes",
						"Miercoles",
						"Jueves",
						"Viernes",
						"Sabado"};

				HashMap<String, String> map = new HashMap<String, String>();
				Cursor cFrentes = db.rawQuery(queryIntroSubConsultaTotalPagar, null);
				if(cFrentes.moveToFirst()) {

					map.put("tv_titulo_quincena", "Total: "+decimales.format(Double.parseDouble(cFrentes.getString(0))).toString());
					map.put("tv_row_detpendientes_dia", ""+cFrentes.getString(1));

					if(Double.parseDouble(cFrentes.getString(0)) <= 0.0){
						map.put("tv_titulo_quincena", "");
					}

					if(cFrentes.getString(1).equals("0")){
						map.put("img_candado_dia"  , "");//+R.drawable.close
						map.put("tv_row_detpendientes_dia", "");
					}else{
						map.put("img_candado_dia"  , ""+R.drawable.open);
					}
				}

    			map.put("FECHA_DIA"		, ""+sdflocal.format(date));
    			map.put("COD_QUINCENA"	, ""+strDays[getDayOfTheWeek(date)-1]);
    			myListActivosOcaciones.add(map); 
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        SimpleAdapter adapterlistEmpleados = new SimpleAdapter(ctx, myListActivosOcaciones,R.layout.row_lista_diasquincena, fromActivos, toActivos);
        
        return adapterlistEmpleados;
	}

	public static int getDayOfTheWeek(Date d){
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(d);
		return cal.get(Calendar.DAY_OF_WEEK);
	}
}
