package com.ilcabana.atp.despachocombustible;

/**
 * Created by juan.reyes on 01/08/2016.
 */
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Preferencias extends Activity {
    private Context context;
    //MODO DE PREFERENCIAS
    int mode=MODE_PRIVATE;
    /**
     *  MODE_PRIVATE. Sólo la aplicación tiene acceso a estas preferencias.
     *MODE_WORLD_READABLE. Todas las aplicaciones pueden leer estas preferencias, pero sólo esta puede modificarlas.
     *MODE_WORLD_WRITABLE. Todas las aplicaciones pueden leer y modificar estas preferencias.
     */
    int numero;
    // TODO Auto-generated method stub
    SharedPreferences sharedPreferences;

    String PREF_NAME_IDQUINCENA     = "IDQUINCENA";
    String PREF_NAME_COD_CUADRILLA  = "COD_CUADRILLA";
    String PREF_NAME_COD_IDLOTE     = "COD_IDLOTE";
    String PREF_NAME_FECHAINICIO    = "FECHAINICIO";
    String PREF_NAME_FECHAFIN       = "FECHAFIN";
    String PREF_NAME_USERNAME       = "USERNAME";
    String PREF_NAME_USERPASS       = "USERPASS";
    String PREF_NAME_COD_ACTIVIDAD  = "COD_ACTIVIDAD";
    String PREF_NAME_ZAFRA          = "ZAFRA";
    String PREF_NAME_CARGAR_FINCA   = "CARGAR_FINCA";
    String PREF_NAME_CANTIDAD       = "CANTIDAD";
    String PREF_NAME_RECORDAR_USER  = "RECORDAR_USER";
    String PREF_NAME_USERDOMINIO    = "USERDOMINIO";
    String PREF_NAME_COD_EMP        = "COD_EMP";
    String PREF_NAME_CANT_UNIADAS   = "CANT_UNIADAS";
    String PREF_NAME_CANT_BARAZOS   = "CANT_BARAZOS";
    String PREF_NAME_COD_TACO       = "COD_TACO";

    String PREF_COD_QUINCENA;
    String PREF_COD_CUADRILLA;
    String PREF_COD_IDLOTE;
    String PREF_FECHAINICIO;
    String PREF_FECHAFIN;
    String PREF_USERNAME;
    String PREF_USERPASS;
    String PREF_COD_ACTIVIDAD;
    String PREF_ZAFRA;
    String PREF_CARGAR_FINCA;
    String PREF_CANTIDAD;
    String PREF_RECORDAR_USER;
    String PREF_USERDOMINIO;
    String PREF_COD_EMP;
    String PREF_CANT_UNIADAS;
    String PREF_CANT_BARAZOS;
    String PREF_COD_TACO;

    public Preferencias(Context context){
        this.context=context;

        sharedPreferences = context.getSharedPreferences("VALUES", MODE_PRIVATE);

        PREF_COD_QUINCENA 	    = sharedPreferences.getString(PREF_NAME_IDQUINCENA, "0").toString();
        PREF_COD_CUADRILLA 	    = sharedPreferences.getString(PREF_NAME_COD_CUADRILLA, "0").toString();
        PREF_COD_IDLOTE 	    = sharedPreferences.getString(PREF_NAME_COD_IDLOTE, "").toString();
        PREF_FECHAINICIO 	    = sharedPreferences.getString(PREF_NAME_FECHAINICIO, "").toString();
        PREF_FECHAFIN 	        = sharedPreferences.getString(PREF_NAME_FECHAFIN, "").toString();
        PREF_USERNAME 	        = sharedPreferences.getString(PREF_NAME_USERNAME, "").toString();
        PREF_USERPASS 	        = sharedPreferences.getString(PREF_NAME_USERPASS, "").toString();
        PREF_COD_ACTIVIDAD 	    = sharedPreferences.getString(PREF_NAME_COD_ACTIVIDAD, "0").toString();
        PREF_ZAFRA 	            = sharedPreferences.getString(PREF_NAME_ZAFRA, "2016-2017").toString();
        PREF_CARGAR_FINCA 	    = sharedPreferences.getString(PREF_NAME_CARGAR_FINCA, "0").toString();
        PREF_CANTIDAD 	        = sharedPreferences.getString(PREF_NAME_CANTIDAD, "1").toString();
        PREF_RECORDAR_USER 	    = sharedPreferences.getString(PREF_NAME_RECORDAR_USER, "0").toString();
        PREF_USERDOMINIO 	    = sharedPreferences.getString(PREF_NAME_USERDOMINIO, "").toString();
        PREF_COD_EMP            = sharedPreferences.getString(PREF_NAME_COD_EMP, "").toString();
        PREF_CANT_UNIADAS       = sharedPreferences.getString(PREF_NAME_CANT_UNIADAS, "0").toString();
        PREF_CANT_BARAZOS       = sharedPreferences.getString(PREF_NAME_CANT_BARAZOS, "0").toString();
        PREF_COD_TACO           = sharedPreferences.getString(PREF_NAME_COD_TACO, "0").toString();

    }

    public String getPREF_COD_TACO() {
        return PREF_COD_TACO;
    }

    public void setPREF_COD_TACO(String PREF_COD_TACO) {
        sharedPreferences.edit().putString(PREF_NAME_COD_TACO	, PREF_COD_TACO).apply();
        this.PREF_COD_TACO = PREF_COD_TACO;
    }

    public String getPREF_CANT_BARAZOS() { return PREF_CANT_BARAZOS;}

    public void setPREF_CANT_BARAZOS(String PREF_CANT_BARAZOS) {
        sharedPreferences.edit().putString(PREF_NAME_CANT_BARAZOS	, PREF_CANT_BARAZOS).apply();
        this.PREF_CANT_BARAZOS = PREF_CANT_BARAZOS;
    }

    public String getPREF_CANT_UNIADAS() { return PREF_CANT_UNIADAS;}

    public void setPREF_CANT_UNIADAS(String PREF_CANT_UNIADAS) {
        sharedPreferences.edit().putString(PREF_NAME_CANT_UNIADAS	, PREF_CANT_UNIADAS).apply();
        this.PREF_CANT_UNIADAS = PREF_CANT_UNIADAS;
    }


    public String getPREF_RECORDAR_USER() {
        return PREF_RECORDAR_USER;
    }

    public void setPREF_RECORDAR_USER(String PREF_RECORDAR_USER) {
        sharedPreferences.edit().putString(PREF_NAME_RECORDAR_USER	, PREF_RECORDAR_USER).apply();
        this.PREF_RECORDAR_USER = PREF_RECORDAR_USER;
    }

    public String getPREF_COD_EMP() { return PREF_COD_EMP;}

    public void setPREF_COD_EMP(String PREF_COD_EMP) {
        sharedPreferences.edit().putString(PREF_NAME_COD_EMP	, PREF_COD_EMP).apply();
        this.PREF_COD_EMP = PREF_COD_EMP;
    }

    public String getPREF_USERDOMINIO() {
        return PREF_USERDOMINIO;
    }

    public void setPREF_USERDOMINIO(String PREF_USERDOMINIO) {
        sharedPreferences.edit().putString(PREF_NAME_USERDOMINIO	, PREF_USERDOMINIO).apply();
        this.PREF_USERDOMINIO = PREF_USERDOMINIO;
    }

    public String getPREF_CANTIDAD() {
        return PREF_CANTIDAD;
    }

    public void setPREF_CANTIDAD(String PREF_CANTIDAD) {
        sharedPreferences.edit().putString(PREF_NAME_CANTIDAD	, PREF_CANTIDAD).apply();
        this.PREF_CANTIDAD = PREF_CANTIDAD;
    }

    public String getPREF_CARGAR_FINCA() {
        return PREF_CARGAR_FINCA;
    }

    public void setPREF_CARGAR_FINCA(String PREF_CARGAR_FINCA) {
        sharedPreferences.edit().putString(PREF_NAME_CARGAR_FINCA	, PREF_CARGAR_FINCA).apply();
        this.PREF_CARGAR_FINCA = PREF_CARGAR_FINCA;
    }

    public String getPREF_ZAFRA() {
        return PREF_ZAFRA;
    }

    public void setPREF_ZAFRA(String PREF_ZAFRA) {
        sharedPreferences.edit().putString(PREF_NAME_ZAFRA	, PREF_ZAFRA).apply();
        this.PREF_ZAFRA = PREF_ZAFRA;
    }

    public String getPREF_COD_ACTIVIDAD() {
        return PREF_COD_ACTIVIDAD;
    }

    public void setPREF_COD_ACTIVIDAD(String PREF_COD_ACTIVIDAD) {
        sharedPreferences.edit().putString(PREF_NAME_COD_ACTIVIDAD	, PREF_COD_ACTIVIDAD).apply();
        this.PREF_COD_ACTIVIDAD = PREF_COD_ACTIVIDAD;
    }

    public String getPREF_USERPASS() {
        return  PREF_USERPASS;
    }

    public void setPREF_USERPASS(String PREF_USERPASS) {
        sharedPreferences.edit().putString(PREF_NAME_USERPASS	, PREF_USERPASS).apply();
        this.PREF_USERPASS = PREF_USERPASS;
    }

    public String getPREF_USERNAME() {
        return PREF_USERNAME;
    }

    public void setPREF_USERNAME(String PREF_USERNAME) {
        sharedPreferences.edit().putString(PREF_NAME_USERNAME	, PREF_USERNAME).apply();
        this.PREF_USERNAME = PREF_USERNAME;
    }


    public String getPREF_FECHAINICIO() {
        return PREF_FECHAINICIO;
    }

    public void setPREF_FECHAINICIO(String PREF_FECHAINICIO) {
        sharedPreferences.edit().putString(PREF_NAME_FECHAINICIO	, PREF_FECHAINICIO).apply();
        this.PREF_FECHAINICIO = PREF_FECHAINICIO;
    }

    public String getPREF_FECHAFIN() {
        return PREF_FECHAFIN;
    }

    public void setPREF_FECHAFIN(String PREF_FECHAFIN) {
        sharedPreferences.edit().putString(PREF_NAME_FECHAFIN	, PREF_FECHAFIN).apply();
        this.PREF_FECHAFIN = PREF_FECHAFIN;
    }


    public String getPREF_COD_CUADRILLA() {
        return PREF_COD_CUADRILLA;
    }

    public void setPREF_COD_CUADRILLA(String PREF_COD_CUADRILLA) {
        sharedPreferences.edit().putString(PREF_NAME_COD_CUADRILLA	, PREF_COD_CUADRILLA).apply();
        this.PREF_COD_CUADRILLA = PREF_COD_CUADRILLA;
    }

    public String getPREF_COD_QUINCENA() {
        return PREF_COD_QUINCENA;
    }

    public void setPREF_COD_QUINCENA(String PREF_COD_QUINCENA) {
        sharedPreferences.edit().putString(PREF_NAME_IDQUINCENA	, PREF_COD_QUINCENA).apply();
        this.PREF_COD_QUINCENA = PREF_COD_QUINCENA;
    }

    public String getPREF_COD_IDLOTE() {
        return PREF_COD_IDLOTE;
    }

    public void setPREF_COD_IDLOTE(String PREF_COD_IDLOTE) {
        sharedPreferences.edit().putString(PREF_NAME_COD_IDLOTE	, PREF_COD_IDLOTE).apply();
        this.PREF_COD_IDLOTE = PREF_COD_IDLOTE;
    }





    public void limpiarPreferencias(){
        sharedPreferences.edit().clear().commit();
    }
    /*
    protected void savePreferences(int num){
        //Instancio la preferencia
        SharedPreferences PREFERENCIAS=context.getSharedPreferences("pref",mode);
        //Para editar las preferencias
        SharedPreferences.Editor editor=PREFERENCIAS.edit();
        //inserto en preferencia(nombre indice, valor);
        editor.putInt("numero", num);
        //Cierro edición
        editor.commit();
    }
    protected int loadPreferences(){
        //Instancio la preferencia
        SharedPreferences PREFERENCIAS=context.getSharedPreferences("pref",mode);
        //recojo valor
        numero=PREFERENCIAS.getInt("numero", 0);
        System.out.println("NUMERO= "+numero);
        return numero;
    }
    */
}