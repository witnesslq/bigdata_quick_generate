package com.tlcb.bdp.admin.createSqlTools.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class FileUtils {

	  public static void writeToLocal(String fileName,String content){
			File file = new File(fileName);
			if(file.exists()){
				file.delete();
			}
			try {
				OutputStream out = new FileOutputStream(file);
				out.write(content.getBytes());
				out.close();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	
}
