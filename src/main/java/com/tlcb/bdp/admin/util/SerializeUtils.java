package com.tlcb.bdp.admin.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
 
public class SerializeUtils {
     
    public static byte[] serializeObject(Object obj) {        
        ByteArrayOutputStream byteOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
         
        try{
            byteOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteOutputStream);
             
            objectOutputStream.writeObject(obj);
            objectOutputStream.flush();
             
            return byteOutputStream.toByteArray();
        }catch(Exception e){
            e.printStackTrace();
        } finally {
            if(null != objectOutputStream){
                try{
                    objectOutputStream.close();
                    byteOutputStream.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }      
         
        return null;
    }
     
    public static Object deserializeObject(byte[] bytes) {
        ByteArrayInputStream byteInputStream = null;
        ObjectInputStream objectInputStream = null;
         
        try{
            byteInputStream = new ByteArrayInputStream(bytes);
            objectInputStream = new ObjectInputStream(byteInputStream);
             
            return objectInputStream.readObject();
             
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if(null != objectInputStream){
                try{
                    objectInputStream.close();
                    byteInputStream.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
     
}