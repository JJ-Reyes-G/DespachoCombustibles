package com.ilcabana.atp.despachocombustible;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class ActMenuInicio extends AppCompatActivity {
    Context ctx = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_manu_inicio);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.act_lista_quincenas, menu);
        //MenuItem searchItem = menu.findItem(R.id.action_search);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_reiniciar_sistema:
                if(Config.reiniciarSistema(ctx))
                    finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void btn_dispensarCombustible (View v){
        Intent i= new Intent(this, ActListaEnvios.class);
        startActivity(i);
    }

    public void btn_consultar (View v){
        Intent i= new Intent(this, ActDespachoCombustible.class);
        startActivity(i);
    }
}
