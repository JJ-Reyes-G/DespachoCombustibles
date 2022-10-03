package com.ilcabana.atp.despachocombustible;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.SimpleAdapter;

import com.ilcabana.atp.database.DatabaseHandler_;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Juan Jose on 23/12/2015.
 */
public class AdapterEnvios {
    public SimpleAdapter adapterEnvios (Context ctx, String FILTRO, String IDTACO){

        SQLiteDatabase db;
        DatabaseHandler_ dbhelper = new DatabaseHandler_(ctx);
        db = dbhelper.getReadableDatabase();


        String query = "SELECT "
                + "DISTINCT(a."+dbhelper.K_ENV0_ID+"),  "
                + " a."+dbhelper.K_ENV1_UMOVDESC	+", "
                + " a."+dbhelper.K_ENV2_LOTEDESC	+", "
                + " a."+dbhelper.K_ENV3_PLACA	    +", "
                + " a."+dbhelper.K_ENV4_TIPOTRACAR	+", "
                + " a."+dbhelper.K_ENV5_CODLOTE	    +", "
                + " a."+dbhelper.K_ENV6_CODTRA	    +", "
                + " a."+dbhelper.K_ENV7_CODMOTOR	+", "
                + " a."+dbhelper.K_ENV8_NOMMOTOR	+", "
                + " a."+dbhelper.K_ENV9_LICENCIAMOTOR +", "
                + " a."+dbhelper.K_ENV10_CANTCOMB     +"  "

                + " FROM   "+dbhelper.TABLE_ENVIOS+" AS a "
                + " WHERE "
                + " CASE WHEN '"+IDTACO+"' = '' THEN a."+dbhelper.K_ENV0_ID+" = a."+dbhelper.K_ENV0_ID+" ELSE a."+dbhelper.K_ENV0_ID+" = '"+IDTACO+"' END "

                +"  AND ( CASE WHEN '"+FILTRO+"' = '0' THEN a."+dbhelper.K_ENV2_LOTEDESC+" = a."+dbhelper.K_ENV2_LOTEDESC+" ELSE a."+dbhelper.K_ENV2_LOTEDESC+" LIKE '%"+FILTRO+"%' END "
                + " OR    CASE WHEN '"+FILTRO+"' = '0' THEN a."+dbhelper.K_ENV0_ID+" = a."+dbhelper.K_ENV0_ID+" ELSE a."+dbhelper.K_ENV0_ID+" LIKE '%"+FILTRO+"%' END "
                + " OR    CASE WHEN '"+FILTRO+"' = '0' THEN a."+dbhelper.K_ENV3_PLACA+" = a."+dbhelper.K_ENV3_PLACA+" ELSE a."+dbhelper.K_ENV3_PLACA+" LIKE '%"+FILTRO+"%' END "
                + " OR    CASE WHEN '"+FILTRO+"' = '0' THEN a."+dbhelper.K_ENV4_TIPOTRACAR+" = a."+dbhelper.K_ENV4_TIPOTRACAR+" ELSE a."+dbhelper.K_ENV4_TIPOTRACAR+" LIKE '%"+FILTRO+"%' END )"
                + " LIMIT 1"
                ;

        Log.i("QUERY", "QUERY: " + query);

        ArrayList<HashMap<String, String>> myListActivosOcaciones = new ArrayList<HashMap<String, String>>();
        String[] fromActivos = new String[] {"tv_galones_asignados", "tvr_num_taco", "tvr_codnom_lote", "tvr_num_placa", "tvr_tipo_rastra", "tvr_codtrasport"};        //, "tv_det_pendientes", "tv_una_pendientes"
        int[] toActivos = new int[] { R.id.tv_galones_asignados,  R.id.tvr_num_envio, R.id.tvr_codnom_lote , R.id.tvr_num_placa, R.id.tvr_tipo_rastra, R.id.tvr_codtrasport};  //, R.id.tv_det_pendientes, R.id.tv_una_pendientes

        Cursor cFrentes = db.rawQuery(query, null);

        if(cFrentes.moveToFirst()){
            do{
                HashMap<String, String> map = new HashMap<String, String>();

                map.put("tv_galones_asignados"      , "Ultimo Precio: "+cFrentes.getString(10));

                map.put("tvr_num_taco"      , "TACO: "+cFrentes.getString(0));
                map.put("tvr_codnom_lote"	, "Motorista:"+cFrentes.getString(7)+" / "+cFrentes.getString(8)+" / licencia: "+cFrentes.getString(9));//cFrentes.getString(2)
                map.put("tvr_num_placa"	    , "PLACAS: "+cFrentes.getString(3));
                map.put("tvr_tipo_rastra"	, cFrentes.getString(4));
                map.put("tvr_codtrasport"	, cFrentes.getString(6));
                map.put("tv_det_pendientes"	, "0");//cFrentes.getString(5));
                map.put("tv_una_pendientes"	, "0");//cFrentes.getString(6));




                map.put("K_ENV0_ID"         , cFrentes.getString(0));
                map.put("K_ENV2_LOTEDESC"	, cFrentes.getString(2));
                map.put("K_ENV3_PLACA"	    , cFrentes.getString(3));
                map.put("K_ENV4_TIPOTRACAR"	, cFrentes.getString(4));
                map.put("K_ENV5_CODLOTE"	, cFrentes.getString(5));
                map.put("K_ENV6_CODTRA"	    , cFrentes.getString(6));
                map.put("ultimoPrecio"	    , cFrentes.getString(10));

                myListActivosOcaciones.add(map);

            }while(cFrentes.moveToNext());
        }

        cFrentes.close();
        db.close();

        SimpleAdapter adapterlistEmpleados = new SimpleAdapter(ctx, myListActivosOcaciones,R.layout.row_lista_envios, fromActivos, toActivos);

        return adapterlistEmpleados;
    }

    public Cursor cursorEnvios(Context ctx, String FILTRO, String IDTACO){
        SQLiteDatabase db;
        DatabaseHandler_ dbhelper = new DatabaseHandler_(ctx);
        db = dbhelper.getReadableDatabase();

        String queryIntroSubConsultaPendientes = " SELECT COUNT(*)  "//AS TOTALPAGAR //m."+dbhelper.K_DET7_CANTIDAD+" * m."+dbhelper.K_DET12_PRECIO+"
                +" FROM "
                +dbhelper.TABLE_PLANILLA_DETALLE+" AS n"
                +" WHERE "
                +" n."+dbhelper.K_DET9_NOTACO    +" = a."+dbhelper.K_ENV0_ID+" "
                +" AND   n."+dbhelper.K_DET5_LLAVE     +" = '0' "
                ;

        String queryIntroSubConsultaTotalUnadas = " SELECT IFNULL(SUM(m."+dbhelper.K_DET4_CANTUNADAS+"), 0)  "//AS TOTALPAGAR //m."+dbhelper.K_DET7_CANTIDAD+" * m."+dbhelper.K_DET12_PRECIO+"
                +" FROM "
                +dbhelper.TABLE_PLANILLA_DETALLE+" AS m"
                +" WHERE "
                +" m."+dbhelper.K_DET9_NOTACO    +" = a."+dbhelper.K_ENV0_ID+" "
                ;


        String query = "SELECT "
                + "DISTINCT(a."+dbhelper.K_ENV0_ID+"),  "
                + " a."+dbhelper.K_ENV1_UMOVDESC	+", "
                + " a."+dbhelper.K_ENV2_LOTEDESC	+", "
                + " a."+dbhelper.K_ENV3_PLACA	    +", "
                + " a."+dbhelper.K_ENV4_TIPOTRACAR	+", "
                + " IFNULL(( "+queryIntroSubConsultaTotalUnadas+" ), 0) AS TotalUnadas, "
                + " IFNULL(( "+queryIntroSubConsultaPendientes+" ), 0) AS TotalPendientes "

                + " FROM   "+dbhelper.TABLE_ENVIOS+" AS a "
                + " WHERE "
                + " CASE WHEN '"+IDTACO+"' = '' THEN a."+dbhelper.K_ENV0_ID+" = a."+dbhelper.K_ENV0_ID+" ELSE a."+dbhelper.K_ENV0_ID+" = '"+IDTACO+"' END "

                +"  AND ( CASE WHEN '"+FILTRO+"' = '0' THEN a."+dbhelper.K_ENV2_LOTEDESC+" = a."+dbhelper.K_ENV2_LOTEDESC+" ELSE a."+dbhelper.K_ENV2_LOTEDESC+" LIKE '%"+FILTRO+"%' END "
                + " OR    CASE WHEN '"+FILTRO+"' = '0' THEN a."+dbhelper.K_ENV3_PLACA+" = a."+dbhelper.K_ENV3_PLACA+" ELSE a."+dbhelper.K_ENV3_PLACA+" LIKE '%"+FILTRO+"%' END "
                + " OR    CASE WHEN '"+FILTRO+"' = '0' THEN a."+dbhelper.K_ENV4_TIPOTRACAR+" = a."+dbhelper.K_ENV4_TIPOTRACAR+" ELSE a."+dbhelper.K_ENV4_TIPOTRACAR+" LIKE '%"+FILTRO+"%' END )"

                ;

        Log.i("QUERY", "QUERY: " + query);

        Cursor cEnvios = db.rawQuery(query, null);
        return cEnvios;
    }

    public boolean setInsertTacos(String ENVCOD, String ENVDESC, Context ctx)
    {
        try {
            SQLiteDatabase db;
            DatabaseHandler_ dbhelper = new DatabaseHandler_(ctx);
            db = dbhelper.getReadableDatabase();
            String query = "INSERT INTO " + dbhelper.TABLE_ENVIOS_REALIZADOS + " ( "
                    + dbhelper.K_ENVR1_ENVCOD           + " , "
                    + dbhelper.K_ENVR2_ENVDESC          + " , "
                    + dbhelper.K_ENVR3_ESTATUSLOCAL     + " , "
                    + dbhelper.K_ENVR4_INGRESO_MANUAL
                    + " ) VALUES ( "
                    + " '" + ENVCOD     + "', "
                    + " '" + ENVDESC    + "', "
                    + " '1', "
                    + " '0'  "
                    +") ";

            db.execSQL(query);
            db.close();
            Log.e("MI QUERY", "Valor:" + query);
            return true;
        }catch( Exception e){
            Log.e("Exception", "Exception: " + e);
            return false;

        }

    }

}