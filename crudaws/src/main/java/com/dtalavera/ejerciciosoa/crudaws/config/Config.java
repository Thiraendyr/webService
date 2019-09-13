package com.dtalavera.ejerciciosoa.crudaws.config;

import java.util.ResourceBundle;

//Clase que contiene las urls y autentificaciones almacenadas en el properties
public class Config {
	private static final ResourceBundle PROPERTIES = ResourceBundle.getBundle("application");
    
    public static String getUrlRN(){
        return PROPERTIES.getString("url-r-n");
    }
    
    public static String getUrlEl(){
        return PROPERTIES.getString("url-el");
    }
    
    public static String getUrlOS(){
        return PROPERTIES.getString("url-o-s");
    }
    
    public static String getUsrRN(){
        return PROPERTIES.getString("usr-r-n");
    }
    
    public static String getUsrEl(){
        return PROPERTIES.getString("usr-el");
    }
    
    public static String getUsrOS(){
        return PROPERTIES.getString("usr-o-s");
    }
    
    public static String getPwdRN(){
        return PROPERTIES.getString("pwd-r-n");
    }
    
    public static String getPwdEl(){
        return PROPERTIES.getString("pwd-el");
    }
    
    public static String getPwdOS(){
        return PROPERTIES.getString("pwd-o-s");
    }
}
