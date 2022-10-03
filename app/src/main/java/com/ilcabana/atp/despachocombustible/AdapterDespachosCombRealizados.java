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
 * Created by juan.reyes on 27/09/2017.
 */
public class AdapterDespachosCombRealizados {
    public SimpleAdapter adapterDespachosCombRealizados (Context ctx, String FILTRO){
        SQLiteDatabase db;
        DatabaseHandler_ dbhelper = new DatabaseHandler_(ctx);
        db = dbhelper.getReadableDatabase();

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
                +" WHERE "
                +" ( CASE WHEN '"+FILTRO+"' = '0' THEN a."+dbhelper.K_COMB1_NUMTACO+" = a."+dbhelper.K_COMB1_NUMTACO+" ELSE a."+dbhelper.K_COMB1_NUMTACO+" LIKE '%"+FILTRO+"%' END "
                +" OR    CASE WHEN '"+FILTRO+"' = '0' THEN a."+dbhelper.K_COMB2_PLACA+" = a."+dbhelper.K_COMB2_PLACA+" ELSE a."+dbhelper.K_COMB2_PLACA+" LIKE '%"+FILTRO+"%' END "
                +" OR    CASE WHEN '"+FILTRO+"' = '0' THEN a."+dbhelper.K_COMB3_NUMTRASP+" = a."+dbhelper.K_COMB3_NUMTRASP+" ELSE a."+dbhelper.K_COMB3_NUMTRASP+" LIKE '%"+FILTRO+"%' END )"

                +" ORDER BY CAST(a."+dbhelper.K_COMB6_NOS+" as INT) DESC "
                ;

        Log.i("QUERY", "QUERY: " + query);

        ArrayList<HashMap<String, String>> myListActivosOcaciones = new ArrayList<HashMap<String, String>>();
        String[] fromActivos = new String[] {
                "tvr_codtrasport",
                "tvr_placa",
                "tvr_num_taco",
                "tvr_fecha",
                "tvr_galones_bomba",
                "tvr_galones_precio",
                "tvr_num_correlativo",
                "tvr_num_nos",
                "imgv_candado",
                "tv_llave",
                "tvr_num_envio",
};
        int[] toActivos = new int[] {
                R.id.tvr_codtrasport,
                R.id.tvr_placa,
                R.id.tvr_num_taco,
                R.id.tvr_fecha,
                R.id.tvr_galones_bomba,
                R.id.tvr_galones_precio,
                R.id.tvr_num_correlativo,
                R.id.tvr_num_nos,
                R.id.imgv_candado,
                R.id.tv_llave,
                R.id.tvr_num_envio,
};

        Cursor cFrentes = db.rawQuery(query, null);

        if(cFrentes.moveToFirst()){
            do {
                HashMap<String, String> map = new HashMap<String, String>();

                map.put("tvr_num_taco"          , "TACO: "+cFrentes.getString(1));
                map.put("tvr_placa"             , "PLACA: "+cFrentes.getString(2));
                map.put("tvr_codtrasport"       , "COD TRANSPORTISTA: " + cFrentes.getString(3));
                map.put("tvr_fecha"             , "FECHA: "+cFrentes.getString(5));
                map.put("tvr_num_nos"           , "N.O.S: "+cFrentes.getString(6));
                map.put("tvr_galones_bomba"     , "GALONES: "+cFrentes.getString(7));
                map.put("tvr_galones_precio"    , "PRECIO: "+cFrentes.getString(8));
                map.put("tvr_num_correlativo"   , "Numero Correlativo: "+cFrentes.getString(10));
                map.put("tvr_num_envio"         , "Total: "+(Double.parseDouble(cFrentes.getString(7))*Double.parseDouble(cFrentes.getString(8))));

                map.put("tv_llave", "LLAVE: " + cFrentes.getString(11));
                map.put("key_llave", cFrentes.getString(11));

                if (Integer.parseInt(cFrentes.getString(11).toString()) > 0) {
                    map.put("imgv_candado", "" + R.drawable.close);
                } else {
                    map.put("imgv_candado", "" + R.drawable.open);
                }

                map.put("K_COMB0_ID", cFrentes.getString(0));
                map.put("K_COMB11_LLAVE", cFrentes.getString(11));

                myListActivosOcaciones.add(map);

            }while(cFrentes.moveToNext());
        }

        cFrentes.close();
        db.close();

        SimpleAdapter adapterlistEmpleados = new SimpleAdapter(ctx, myListActivosOcaciones,R.layout.row_lista_despachos_realizados, fromActivos, toActivos);

        return adapterlistEmpleados;
    }
}
