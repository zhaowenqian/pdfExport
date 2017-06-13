/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.veriguide.utility.report;

import java.io.IOException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.Writer;
/**
 *
 * @author zwq19961228
 */

class source{
    public String SourceType;
    public String Source;
    public String VeriguideRemarks;
    public String[] Paragragh;
    public String[] SourceParagragh;
    public int ParagraphNo(){
        return Paragragh.length;
    }
    
}
class SubNumInfo{
    public String name;
    public int info;
}
class SubStringInfo{
    public String name;
    public String info;
}
class info {
    public SubNumInfo[] SubmissionNumInfo;
    public SubStringInfo[] SubmissionStringInfo;
    public source[] Comparisions;
    public int comparisionNo(){
        return Comparisions.length;
    
    }
     public int SubmissionNumInfoNo(){
        return SubmissionNumInfo.length;
    
    }
      public int SubmissionStringInfoNo(){
        return SubmissionStringInfo.length;
    
    }
    public String reviewer;
    public String confirmer;
    public String endorsor;
 
    // Getters and setters are not required for this example.
    // GSON sets the fields directly using reflection.
 
  
  
}
class Json {
  
   public info transform()throws IOException{
      try(Reader reader = new InputStreamReader(Json.class.getResourceAsStream("/example1.json"), "UTF-8")){
            Gson gson = new GsonBuilder().create();
            info p = gson.fromJson(reader, info.class);
           return p;
          
            
        }
    
   }
    
}
