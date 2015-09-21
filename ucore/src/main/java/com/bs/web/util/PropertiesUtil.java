package com.bs.web.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;

public class PropertiesUtil implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public final static String location = "/id5sms.properties";
	
	//营销账号：2001285密码g*39AZC%
	//通知账号200277密码U!#7FBj5
	
	public static Properties prop =  new  Properties();    
    static  {    
       InputStream in = Object.class.getResourceAsStream( location );    
        try  {    
           prop.load(in); 
       }  catch  (IOException e) {    
           e.printStackTrace();    
       }    
   }    
  
    
    public PropertiesUtil(){
    	 InputStream in = Object.class.getResourceAsStream( location );    
         try  {    
            prop.load(in); 
        }  catch  (IOException e) {    
            e.printStackTrace();    
        }   
    }
 
 
    public static void main(String args[]){    
       System.out.println(prop);    
   }    
	
}
