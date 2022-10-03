package com.ilcabana.atp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler_ extends SQLiteOpenHelper {
	// All Static variables
	// Database Version
	public static final int DATABASE_VERSION = 6;

	// Database Name
	private static final String DATABASE_NAME = "DESPACHO_COMBUSTIBLE";

	// ADMINISTRACION table name
	public final String TABLE_ADMINITRACION = "ADMINISTRACION";
	// ADMINISTRACION Table Columns names
	public final String K_ADM0_ID 				= "adminid";
	public final String K_ADM1_NOMBREUSER 		= "adminnombreuser";
	public final String K_ADM2_PASSUSER 		= "adminpassuser";
	public final String K_ADM3_USERNAME 		= "username";
	public final String K_ADM4_USUID 			= "usuid";
	public final String K_ADM5_NIVELACCESO 		= "nivelacceso";



	//MOVIMIENTOS DE TABLE_ENVIOS REALIZADOS EN DESPACHOS_REALIZADOS
	public final String TABLE_DESPACHO_COMBUSTIBLE = "DESPACHO_COMBUSTIBLE";
	//ACTIVIDADES DE TRABAJO table columns name
	public final String K_COMB0_ID 				= "ID_COMBUSTIBLE";
	public final String K_COMB1_NUMTACO 		= "NUMTACO";
	public final String K_COMB2_PLACA 			= "PLACA";
	public final String K_COMB3_NUMTRASP 		= "NUMTRASP";
	public final String K_COMB4_USUARIO 		= "USUARIO";
	public final String K_COMB5_FECHA 			= "FECHA";
	public final String K_COMB6_NOS 			= "NOS";
	public final String K_COMB7_GAL_BOMBA 		= "GAL_BOMBA";
	public final String K_COMB8_GAL_PRECIO 		= "GAL_PRECIO";
	public final String K_COMB9_CORTE 			= "CORTE";
	public final String K_COMB10_CORRELATIVO 	= "CORRELATIVO";
	public final String K_COMB11_LLAVE 			= "LLAVE";


	//EMPLEADOS DE LA PLANILLA --> EMPLEADOS
	public  final String TABLE_EMPLEADOS 		= "EMPLEADOS";
	//ACTIVIDADES DE TRABAJO table columns name
	public  final String K_EM0_ID 		    	= "ID_EMPLEADO";
	public  final String K_EM1_CODEMPLEADO 		= "CODEMPLEADO";
	public  final String K_EM2_NOMEMPLEADO 		= "NOMEMPLEADO";
	public  final String K_EM3_CODCUADRILLA 	= "CODCUADRILLA";
	public  final String K_EM4_NOMCUADRILLA 	= "NOMCUADRILLA";
	public  final String K_EM5_CODFRENTE 		= "CODFRENTE";
	public  final String K_EM6_NOMFRENTE 		= "NOMFRENTE";
	public  final String K_EM7_NOMTIPO 			= "NOMTIPO";
	public  final String K_EM8_ESTS_DISPONIBLE	= "ESTATUS_DISPONIBLE";

	//EMPLEADOS DE LA PLANILLA --> ASISTENCIA_EMP
	public  final String TABLE_ASISTENCIA_EMP 	= "ASISTENCIA_EMP";
	//ACTIVIDADES DE TRABAJO table columns name
	public  final String K_ASI0_ID 		    	= "ID_ASISTENCIA_EMP";
	public  final String K_ASI1_CODEMPLEADO 	= "CODEMPLEADO";
	public  final String K_ASI2_NOMEMPLEADO 	= "NOMEMPLEADO";
	public  final String K_ASI3_CODCUADRILLA 	= "CODCUADRILLA";
	public  final String K_ASI4_TACO 			= "TACO";
	public  final String K_ASI5_FECHA			= "FECHA";
	public  final String K_ASI6_VARAZOS			= "VARAZOS";

	//EMPLEADOS DE LA PLANILLA --> EMPLEADOS
	public  final String TABLE_PLANILLA_DETALLE = "PLANILLA_DETALLE";
	//ACTIVIDADES DE TRABAJO table columns name
	public  final String K_DET0_ID 		    	= "ID_DETALLE";
	public  final String K_DET1_CODENCABEZADO 	= "CODENCABEZADO";
	public  final String K_DET2_CODEMPLEADO 	= "CODEMPLEADO";
	public  final String K_DET3_NOMEMPLEADO 	= "NOMEMPLEADO";
	public  final String K_DET4_CANTUNADAS 		= "CANTUNADAS";
	public  final String K_DET5_LLAVE			= "LLAVE";
	public  final String K_DET6_QUINID			= "QUINID";
	public  final String K_DET7_FECHA			= "FECHA";
	public  final String K_DET8_USUID			= "USUID";
	public  final String K_DET9_NOTACO			= "NOTACO";
	public  final String K_DET10_CUAID			= "CUAID";
	public  final String K_DET11_BRAZADAS 		= "BRAZADAS";
	public  final String K_DET12_NOENVIO 		= "NOENVIO";
	public  final String K_DET13_FECHAROZA 		= "FECHAROZA";


	public  final String TABLE_ACTIVIDADES 		= "ACTIVIDADES";
	//ACTIVIDADES DE TRABAJO table columns name
	public  final String K_ACT0_ID 		    	= "ID_ACTIVIDAD";
	public  final String K_ACT1_CODACTIVIDAD 	= "CODACTIVIDAD";
	public  final String K_ACT2_NOMACTIVIDAD 	= "NOMACTIVIDAD";
	public  final String K_ACT3_UMEDIDA 		= "UMEDIDA";
	public  final String K_ACT4_TARIFA 			= "TARIFA";

	//EMPLEADOS DE LA PLANILLA --> EMPLEADOS
	public  final String TABLE_QUINCENAS 		= "QUINCENAS";
	//ACTIVIDADES DE TRABAJO table columns name
	public  final String K_QUI0_ID 		    	= "ID_QUINCENA";
	public  final String K_QUI1_FECHA_INICIO 	= "FECHA_INICIO";
	public  final String K_QUI2_FECHA_FIN 		= "FECHA_FIN";
	public  final String K_QUI3_ZAFRA 			= "ZAFRA";
	public  final String K_QUI4_CORTE 			= "CORTE";
	public  final String K_QUI5_FECHAUPDATE_TABLA = "FECHAUPDATE";

	// SECUENCIAS table name
	public  final String TABLE_PROVEDORES 		= "PROVEDORES";
	// SECUENCIAS Table Columns names
	public  final String K_SEC0_ID 				= "ID_SECUENCIA";
	public  final String K_SEC1_NUM 			= "SECNUMERO";
	public  final String K_SEC2_CODCLIENTE 		= "seccodcliente";
	public  final String K_SEC3_NOMCLIENTE 		= "secnomcliente";
	public  final String K_SEC4_CODFINCA 		= "seccodfinca";
	public  final String K_SEC5_NOMFINCA 		= "secnomfinca";
	public  final String K_SEC6_CODLOTE 		= "seccodlote";
	public  final String K_SEC7_NOMLOTE 		= "secnomlote";
	public  final String K_SEC8_USUID 			= "USUID";

	// SECUENCIAS table name
	public final String TABLE_CUADRILLAS 		= "CUADRILLAS";
	// SECUENCIAS Table Columns names
	public final String K_CUA0_ID 				= "ID_CUADRILLA";
	public final String K_CUA1_NOMCUADRILLA 	= "NOMCUADRILLA";
	public final String K_CUA2_CODFRENTE 		= "CODFRENTE";
	public final String K_CUA3_NOMFRENTE 		= "NOMFRENTE";
	public final String K_CUA4_USUID 			= "USUID";
	public final String K_CUA5_CODCUADRILLA  	= "CODCUADRILLA";

	//MOVIMIENTOS DE TABLE_ENVIOS
	public  final String TABLE_ENVIOS 			= "ENVIOS";
	//ACTIVIDADES DE TRABAJO table columns name
	public  final String K_ENV0_ID 		    	= "ID_ENVIO";
	public  final String K_ENV1_UMOVDESC 		= "UMOVDESC";
	public  final String K_ENV2_LOTEDESC 		= "LOTEDESC";
	public  final String K_ENV3_PLACA 			= "PLACA";
	public  final String K_ENV4_TIPOTRACAR 		= "TIPOTRACAR";
	public  final String K_ENV5_CODLOTE 		= "CODLOTE";
	public  final String K_ENV6_CODTRA			= "CODTRA";
	public  final String K_ENV7_CODMOTOR		= "CODMOTOR";
	public  final String K_ENV8_NOMMOTOR		= "NOMMOTOR";
	public  final String K_ENV9_LICENCIAMOTOR	= "LICENCIAMOTOR";
	public  final String K_ENV10_CANTCOMB		= "CANTCOMB";


	//MOVIMIENTOS DE TABLE_ENVIOS REALIZADOS EN QUINCENAS
	public  final String TABLE_ENVIOS_REALIZADOS 	= "ENVIOS_REALIZADOS";
	//ACTIVIDADES DE TRABAJO table columns name
	public  final String K_ENVR0_ID 		    = "ID_ENVR";
	public  final String K_ENVR1_ENVCOD 		= "ENVCOD";
	public  final String K_ENVR2_ENVDESC 		= "ENVDESC";
	public  final String K_ENVR3_ESTATUSLOCAL 	= "ESTATUSLOCAL";
	public  final String K_ENVR4_INGRESO_MANUAL = "INGRESO_MANUAL";
	public  final String K_ENVR5_COMENTARIO     = "COMENTARIO";
	public  final String K_ENVR6_NTACO  	 	= "NENVIO";
	public  final String K_ENVR7_UNIADAS  	 	= "UNIADAS";
	public  final String K_ENVR8_USUARIO  	 	= "USUARIO";
	public  final String K_ENVR9_CODLOTE  	 	= "CODLOTE";
	public  final String K_ENVR10_PORCENTAJE 	= "PORCENTAJE";
	public  final String K_ENVR11_FECHAREG   	= "FECHAREG";
	public  final String K_ENVR12_FECHADIA   	= "FECHADIA";
	public  final String K_ENVR13_RASTRAPLACA   = "RASTRAPLACA";
	public  final String K_ENVR14_LLAVE   		= "LLAVE";
	public  final String K_ENVR15_QUINID   		= "QUINID";


	//MOVIMIENTOS DE TABLE_RASTRAS_CARGADAS
	public  final String TABLE_RASTRAS_CARGADAS 	= "RASTRAS_CARGADAS";
	//ACTIVIDADES DE TRABAJO table columns name
	public  final String K_RAS0_ID 		    	= "ID_ENVR";
	public  final String K_RAS1_PLACA 			= "PLACA";
	public  final String K_RAS2_DESC			= "DESCRIPCION";
	public  final String K_RAS3_TACO 	    	= "TACO";
	public  final String K_RAS4_FECHAREG    	= "FECHAREG";
	public  final String K_RAS5_COMENTARIO  	= "COMENTARIO";
	public  final String K_RAS6_NENVIO  		= "NENVIO";
	public  final String K_RAS7_UNIADAS  		= "UNIADAS";
	public  final String K_RAS8_USUARIO  		= "USUARIO";
	public  final String K_RAS9_CODLOTE  		= "CODLOTE";
	public  final String K_RAS10_PORCENTAJE 	= "PORCENTAJE";

	public DatabaseHandler_(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// CREACION DE TABLAS
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_ENVIOS = "CREATE TABLE " + TABLE_ENVIOS + "("
				+ K_ENV0_ID 			+ " INTEGER PRIMARY KEY, "
				+ K_ENV1_UMOVDESC 		+ " TEXT, "
				+ K_ENV2_LOTEDESC 		+ " TEXT, "
				+ K_ENV3_PLACA 			+ " TEXT, "
				+ K_ENV4_TIPOTRACAR 	+ " TEXT, "
				+ K_ENV5_CODLOTE 		+ " TEXT, "
				+ K_ENV6_CODTRA 		+ " TEXT, "
				+ K_ENV7_CODMOTOR 		+ " TEXT, "
				+ K_ENV8_NOMMOTOR 		+ " TEXT, "
				+ K_ENV9_LICENCIAMOTOR 	+ " TEXT, "
				+ K_ENV10_CANTCOMB 		+ " TEXT  "
				+")";
		db.execSQL(CREATE_ENVIOS);


		String CREATE_DESPACHO_COMBUSTIBLE = "CREATE TABLE " + TABLE_DESPACHO_COMBUSTIBLE + "("
				+ K_COMB0_ID 			+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ K_COMB1_NUMTACO 		+ " TEXT, "
				+ K_COMB2_PLACA 		+ " TEXT, "
				+ K_COMB3_NUMTRASP 		+ " TEXT, "
				+ K_COMB4_USUARIO 		+ " TEXT, "
				+ K_COMB5_FECHA 		+ " TEXT, "
				+ K_COMB6_NOS 			+ " TEXT, "
				+ K_COMB7_GAL_BOMBA 	+ " TEXT, "
				+ K_COMB8_GAL_PRECIO 	+ " TEXT, "
				+ K_COMB9_CORTE 		+ " TEXT, "
				+ K_COMB10_CORRELATIVO 	+ " TEXT, "
				+ K_COMB11_LLAVE 		+ " TEXT  "
				+")";
		db.execSQL(CREATE_DESPACHO_COMBUSTIBLE);


		String CREATE_ENVIOS_REALIZADOS = "CREATE TABLE " + TABLE_ENVIOS_REALIZADOS + "("
				+ K_ENVR0_ID 			 + " INTEGER PRIMARY KEY, "
				+ K_ENVR1_ENVCOD 		 + " TEXT, "
				+ K_ENVR2_ENVDESC 		 + " TEXT, "
				+ K_ENVR3_ESTATUSLOCAL 	 + " TEXT, "
				+ K_ENVR4_INGRESO_MANUAL + " TEXT, "
				+ K_ENVR5_COMENTARIO 	 + " TEXT, "
				+ K_ENVR6_NTACO 		 + " TEXT, "
				+ K_ENVR7_UNIADAS 		 + " TEXT, "
				+ K_ENVR8_USUARIO 		 + " TEXT, "
				+ K_ENVR9_CODLOTE 		 + " TEXT, "
				+ K_ENVR10_PORCENTAJE 	 + " TEXT, "
				+ K_ENVR11_FECHAREG 	 + " TEXT, "
				+ K_ENVR12_FECHADIA 	 + " TEXT, "
				+ K_ENVR13_RASTRAPLACA 	 + " TEXT, "
				+ K_ENVR14_LLAVE 	 	 + " TEXT, "
				+ K_ENVR15_QUINID 	 	 + " TEXT  "
				+")";
		db.execSQL(CREATE_ENVIOS_REALIZADOS);

		String CREATE_CUADRILLAS_TABLE = "CREATE TABLE " + TABLE_CUADRILLAS + "("
				+ K_CUA0_ID 			+ " INTEGER PRIMARY KEY, "
				+ K_CUA1_NOMCUADRILLA 	+ " TEXT, "
				+ K_CUA2_CODFRENTE 		+ " TEXT, "
				+ K_CUA3_NOMFRENTE 		+ " TEXT, "
				+ K_CUA4_USUID 			+ " TEXT,  "
				+ K_CUA5_CODCUADRILLA 	+ " TEXT  "
				+")";
		db.execSQL(CREATE_CUADRILLAS_TABLE);


		String CREATE_SECUENCIAS_TABLE = "CREATE TABLE " + TABLE_PROVEDORES + "("
				+ K_SEC0_ID 			+ " INTEGER PRIMARY KEY,"
				+ K_SEC1_NUM 			+ " TEXT,"
				+ K_SEC2_CODCLIENTE 	+ " TEXT,"
				+ K_SEC3_NOMCLIENTE 	+ " TEXT,"
				+ K_SEC4_CODFINCA 		+ " TEXT,"
				+ K_SEC5_NOMFINCA 		+ " TEXT,"
				+ K_SEC6_CODLOTE 		+ " TEXT,"
				+ K_SEC7_NOMLOTE 		+ " TEXT,"
				+ K_SEC8_USUID 			+ " TEXT "
				+")";
		db.execSQL(CREATE_SECUENCIAS_TABLE);


		String CREATE_QUINCENAS_TABLE = "CREATE TABLE " + TABLE_QUINCENAS + "("
				+ K_QUI0_ID 		    	+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ K_QUI1_FECHA_INICIO 		+ "  DATE, "
				+ K_QUI2_FECHA_FIN 			+ "  DATE, "
				+ K_QUI3_ZAFRA 				+ "  TEXT, "
				+ K_QUI4_CORTE				+ "  TEXT, "
				+ K_QUI5_FECHAUPDATE_TABLA	+ "  TEXT  "
				+")";
		db.execSQL(CREATE_QUINCENAS_TABLE);


		String CREATE_ADMINISTRACION_TABLE = "CREATE TABLE " + TABLE_ADMINITRACION + "("
				+ K_ADM0_ID 			+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ K_ADM1_NOMBREUSER 	+ " TEXT, "
				+ K_ADM2_PASSUSER 		+ " TEXT, "
				+ K_ADM3_USERNAME 		+ " TEXT, "
				+ K_ADM4_USUID 			+ " TEXT, "
				+ K_ADM5_NIVELACCESO 	+ " TEXT  "

				+")";
		db.execSQL(CREATE_ADMINISTRACION_TABLE);


		String CREATE_TABLE_EMPLEADOS = "CREATE TABLE " + TABLE_EMPLEADOS + "("
				+ K_EM0_ID 		    	+ "  INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ K_EM1_CODEMPLEADO 	+ "  TEXT,  "
				+ K_EM2_NOMEMPLEADO 	+ "  TEXT,  "
				+ K_EM3_CODCUADRILLA 	+ "  INTEGER,  "
				+ K_EM4_NOMCUADRILLA 	+ "  TEXT,  "
				+ K_EM5_CODFRENTE 		+ "  TEXT,  "
				+ K_EM6_NOMFRENTE 		+ "  TEXT,  "
				+ K_EM7_NOMTIPO			+ "  TEXT,   "
				+ K_EM8_ESTS_DISPONIBLE	+ "  TEXT   "
				+")";
		db.execSQL(CREATE_TABLE_EMPLEADOS);


		String CREATE_ASISTENCIA_EMP_TABLE = "CREATE TABLE " + TABLE_ASISTENCIA_EMP + "("
				+ K_ASI0_ID 		    + "  INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ K_ASI1_CODEMPLEADO 	+ "  TEXT, "
				+ K_ASI2_NOMEMPLEADO 	+ "  TEXT, "
				+ K_ASI3_CODCUADRILLA 	+ "  TEXT, "
				+ K_ASI4_TACO			+ "  TEXT, "
				+ K_ASI5_FECHA			+ "  TEXT, "
				+ K_ASI6_VARAZOS		+ "	 TEXT  "
				+")";
		db.execSQL(CREATE_ASISTENCIA_EMP_TABLE);


		String CREATE_PLANILLA_DETALLE_TABLE = "CREATE TABLE " + TABLE_PLANILLA_DETALLE + "("
				+ K_DET0_ID 		    + "  INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ K_DET1_CODENCABEZADO 	+ "  INTEGER, "
				+ K_DET2_CODEMPLEADO 	+ "  TEXT, "
				+ K_DET3_NOMEMPLEADO 	+ "  TEXT, "
				+ K_DET4_CANTUNADAS 	+ "  TEXT, "
				+ K_DET5_LLAVE 		    + "  TEXT, "
				+ K_DET6_QUINID			+ "  TEXT, "
				+ K_DET7_FECHA			+ "  TEXT, "
				+ K_DET8_USUID			+ "  TEXT, "
				+ K_DET9_NOTACO			+ "  TEXT, "
				+ K_DET10_CUAID			+ "  TEXT, "
				+ K_DET11_BRAZADAS 		+ "	 TEXT, "
				+ K_DET12_NOENVIO		+ "	 TEXT, "
				+ K_DET13_FECHAROZA		+ "	 TEXT  "
				+")";
		db.execSQL(CREATE_PLANILLA_DETALLE_TABLE);


		String CREATE_ACTIVIDADES_TABLE = "CREATE TABLE " + TABLE_ACTIVIDADES + "("
				+ K_ACT0_ID 		    + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ K_ACT1_CODACTIVIDAD 	+ "  TEXT, "
				+ K_ACT2_NOMACTIVIDAD 	+ "  TEXT, "
				+ K_ACT3_UMEDIDA 		+ "  TEXT, "
				+ K_ACT4_TARIFA			+ "  TEXT  "
				+")";
		db.execSQL(CREATE_ACTIVIDADES_TABLE);

		String CREATE_TABLE_RASTRAS_CARGADAS = "CREATE TABLE " + TABLE_RASTRAS_CARGADAS + "("
				+ K_RAS0_ID 			+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ K_RAS1_PLACA 			+ " TEXT, "
				+ K_RAS2_DESC 			+ " TEXT, "
				+ K_RAS3_TACO 			+ " TEXT, "
				+ K_RAS4_FECHAREG 		+ " TEXT, "
				+ K_RAS5_COMENTARIO 	+ " TEXT, "
				+ K_RAS6_NENVIO 		+ " TEXT, "
				+ K_RAS7_UNIADAS 		+ " TEXT, "
				+ K_RAS8_USUARIO 		+ " TEXT, "
				+ K_RAS9_CODLOTE 		+ " TEXT, "
				+ K_RAS10_PORCENTAJE 	+ " TEXT  "
				+")";
		db.execSQL(CREATE_TABLE_RASTRAS_CARGADAS);

	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADMINITRACION);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLEADOS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENVIOS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENVIOS_REALIZADOS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLANILLA_DETALLE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTIVIDADES);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUINCENAS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROVEDORES);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUADRILLAS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ASISTENCIA_EMP);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_RASTRAS_CARGADAS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_DESPACHO_COMBUSTIBLE);

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */
}