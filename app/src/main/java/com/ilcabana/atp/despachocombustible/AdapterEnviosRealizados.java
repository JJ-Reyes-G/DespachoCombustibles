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
public class AdapterEnviosRealizados {
    public SimpleAdapter adapterEnviosRealizados (Context ctx, String FILTRO, String IDTACO, String QuinId, String FechaDia, boolean countImporta){
        SQLiteDatabase db;
        DatabaseHandler_ dbhelper = new DatabaseHandler_(ctx);
        db = dbhelper.getReadableDatabase();

        String query = "SELECT "
                + "DISTINCT(a."+dbhelper.K_ENVR0_ID+"),  "
                + " a."+dbhelper.K_ENVR1_ENVCOD	    +", "
                + " a."+dbhelper.K_ENVR2_ENVDESC	+", "
                + " a."+dbhelper.K_ENVR5_COMENTARIO	+", "
                + " a."+dbhelper.K_ENVR6_NTACO	    +", "
                + " a."+dbhelper.K_ENVR7_UNIADAS	+", "
                + " a."+dbhelper.K_ENVR8_USUARIO	+", "
                + " a."+dbhelper.K_ENVR9_CODLOTE	+", "
                + " a."+dbhelper.K_ENVR10_PORCENTAJE    +", "
                + " a."+dbhelper.K_ENVR12_FECHADIA	    +", "
                + " a."+dbhelper.K_ENVR13_RASTRAPLACA	+", "
                + " a."+dbhelper.K_ENVR14_LLAVE	        +", "
                + " a."+dbhelper.K_ENVR15_QUINID	    +"  "
                //+ " IFNULL(( "+queryIntroSubConsultaPendientes+" ), 0) AS TotalPendientes, "
                //+ " IFNULL(( "+queryIntroSubConsultaGuardadas+"  ), 0) AS TotalGuardadas "

                + " FROM   "+dbhelper.TABLE_ENVIOS_REALIZADOS+" AS a "
                + " WHERE "
                + " CASE WHEN '"+QuinId+"' = '' THEN a."+dbhelper.K_ENVR15_QUINID+" = a."+dbhelper.K_ENVR15_QUINID+" ELSE a."+dbhelper.K_ENVR15_QUINID+" = '"+QuinId+"' END "
                + " AND CASE WHEN '"+FechaDia+"' = '' THEN a."+dbhelper.K_ENVR12_FECHADIA+" = a."+dbhelper.K_ENVR12_FECHADIA+" ELSE a."+dbhelper.K_ENVR12_FECHADIA+" = '"+FechaDia+"' END "
                + " AND CASE WHEN '"+IDTACO+"' = '' THEN a."+dbhelper.K_ENVR1_ENVCOD+" = a."+dbhelper.K_ENVR1_ENVCOD+" ELSE a."+dbhelper.K_ENVR1_ENVCOD+" = '"+IDTACO+"' END "
                +"  AND ( CASE WHEN '"+FILTRO+"' = '0' THEN a."+dbhelper.K_ENVR1_ENVCOD+" = a."+dbhelper.K_ENVR1_ENVCOD+" ELSE a."+dbhelper.K_ENVR1_ENVCOD+" LIKE '%"+FILTRO+"%' END "
                + " OR    CASE WHEN '"+FILTRO+"' = '0' THEN a."+dbhelper.K_ENVR2_ENVDESC+" = a."+dbhelper.K_ENVR2_ENVDESC+" ELSE a."+dbhelper.K_ENVR2_ENVDESC+" LIKE '%"+FILTRO+"%' END )"
                ;

        Log.i("QUERY", "QUERY: " + query);

        ArrayList<HashMap<String, String>> myListActivosOcaciones = new ArrayList<HashMap<String, String>>();
        String[] fromActivos = new String[] {"tvr_placa","tvr_fechaquin","tvr_porcentaje","tvr_num_uniadas","tvr_num_taco","tvr_num_envio", "tvr_desc_taco", " tvr_comentario", "tv_llave", "imgv_candado"};
        int[] toActivos = new int[] {R.id.tvr_placa,R.id.tvr_fechaquin,R.id.tvr_porcentaje,R.id.tvr_num_uniadas,R.id.tvr_num_taco, R.id.tvr_num_envio, R.id.tvr_desc_taco, R.id.tvr_comentario, R.id.tv_llave, R.id.imgv_candado };

        Cursor cFrentes = db.rawQuery(query, null);

        if(cFrentes.moveToFirst()){
            do {
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("tvr_num_envio"     , "ENVIO: " + cFrentes.getString(1));
                    map.put("tvr_desc_taco"     , "DESC TACO: "+cFrentes.getString(2));
                    map.put("tvr_comentario"    , "COMENTARIO: "+cFrentes.getString(3));
                    map.put("tvr_num_taco"      , "NUM TACO: "+cFrentes.getString(4));
                    map.put("tvr_num_uniadas"   , "UÑADAS: "+cFrentes.getString(5));
                    map.put("tvr_porcentaje"    , "PORCENTAJE CARGA: "+cFrentes.getString(8));
                    map.put("tvr_fechaquin"     , "FECHA: "+cFrentes.getString(9));
                    map.put("tvr_placa"         , "PLACA RASTRA: "+cFrentes.getString(10));

                    map.put("tv_llave", "LLAVE: " + cFrentes.getString(11));
                    map.put("key_llave", cFrentes.getString(11));

                    if (Integer.parseInt(cFrentes.getString(11).toString()) > 0) {
                        map.put("imgv_candado", "" + R.drawable.close);
                    } else {
                        map.put("imgv_candado", "" + R.drawable.open);
                    }

                    map.put("K_ENV0_ID", cFrentes.getString(0));



                    myListActivosOcaciones.add(map);

            }while(cFrentes.moveToNext());
        }

        cFrentes.close();
        db.close();

        SimpleAdapter adapterlistEmpleados = new SimpleAdapter(ctx, myListActivosOcaciones,R.layout.row_lista_envios_realizados, fromActivos, toActivos);

        return adapterlistEmpleados;
    }
}