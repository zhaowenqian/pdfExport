/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


//zhaowenqian 

/*
modifications have been made
1.the number of submission information terms is flexible;
2.chinese is already displayable;
3.For paragraph comparisions, at the end of a page, a cell shorter than 4 lines cannot split, but longer can(both chinese and english work well);
4.Semester has to be 1 or 2 or 3    (3 is the summer sessions);
5.Assume that year has to be 2016-2030;
6.Assignment number has to be positive;
7.default student ID lengh has to be 9 otherwise will report error
8. student ID, Course code, Case ID missing will report a error
9.paragraph or source paragraphs' length has too be shorter than 5000
10.other format problems will be taken as "wrong input format"
*/
package com.mycompany.helloworld;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.action.PdfAction;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.forms.fields.PdfButtonFormField;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.layout.element.Cell;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.layout.element.Table;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfAConformanceLevel;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.element.List;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.layout.element.ListItem;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.test.annotations.WrapToTest;
import com.itextpdf.test.annotations.type.SampleTest;
import org.junit.experimental.categories.Category;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;

 
import java.io.File;
import java.io.IOException;

/**
 *
 * @author zwq19961228
 */
public class CreateNewPdf {
   public static final String DEST ="results/chapter01/result1.pdf";
   
   public static void main(String arg[]) throws IOException{
       
       File file =new File(DEST);
       new CreateNewPdf().createPdf(DEST);
       int returnValue;
       
   }
   public int createPdf(String dest) throws IOException {
       
       
            
             //Initialize PDF writer
     
        PdfWriter writer = new PdfWriter(dest);
        
        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(writer);
 
        // Initialize document
        
        
        Document document = new Document(pdf);
        
        
      try{
        //read from the json file
        Json json=new Json();
        
        info info=json.transform();
        System.out.println(info.Comparisions[0].Paragragh[0].length()+"is the length");
        int i,j;
        int year;
        boolean missingStudentID=true;
        boolean missingCourseCode=true;
        boolean missingCaseID=true;
        j=info.SubmissionNumInfoNo();
       //exception handling
        for(i=0;i<j;i++){
            
           if(info.SubmissionNumInfo[i].name.equals("Academic Year:")&&(info.SubmissionNumInfo[i].info<2016||info.SubmissionNumInfo[i].info>2030)){
               throw new YearException();
           }
           if(info.SubmissionNumInfo[i].name.equals("Semester:")&&info.SubmissionNumInfo[i].info!=1&&info.SubmissionNumInfo[i].info!=2&&info.SubmissionNumInfo[i].info!=3){
               throw new SemesterException();
           }
           if(info.SubmissionNumInfo[i].name.equals("Assignment No.:")&&info.SubmissionNumInfo[i].info<=0){
               throw new AssignmentNumberException();
           }
           
           
        }
        
        
        j=info.SubmissionStringInfoNo();
        for(i=0;i<j;i++){
            if(info.SubmissionStringInfo[i].name.equals("Student ID:")&&info.SubmissionStringInfo[i].info.length()!=9){
                throw new StudentIDLengthException();
            }
            if(info.SubmissionStringInfo[i].name.equals("Student ID:")){
                missingStudentID=false;
            }
            if(info.SubmissionStringInfo[i].name.equals("Case ID:")){
                missingCaseID=false;
            }
            if(info.SubmissionStringInfo[i].name.equals("Course Code:")){
                missingCourseCode=false;
            }
        }
        if(missingStudentID==true){
            throw new MissingStudentIDException();
        }
        if(missingCourseCode==true){
            throw new MissingCourseCodeException();
        }
         if(missingCaseID==true){
            throw new MissingCaseIDException();
        }
         
        boolean paragraphTooLong=false;
        j=info.comparisionNo();
        int k,l;
        for(i=0;i<j;i++){
            k=info.Comparisions[i].ParagraphNo();
            for(l=0;l<k;l++){
                if(info.Comparisions[i].Paragragh[l].length()>5000){
                    paragraphTooLong=true;
                }
                if(info.Comparisions[i].SourceParagragh[l].length()>5000){
                    paragraphTooLong=true;
                }
            }
        }
        if(paragraphTooLong==true){
            throw new ParagraphTooLongException();
        }
        
        
      
        //Title
        PdfFont bold = PdfFontFactory.createFont(FontConstants.TIMES_BOLD);
        PdfFont roman = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);
        //PdfFont time = PdfFontFactory.createFont(FontConstants.TIMES);
        Paragraph title=new Paragraph("Case Report Form").setFont(bold).setFontSize(11).setTextAlignment(TextAlignment.CENTER).setUnderline(1, -3);
       
        document.add(title);
        
        Table table = new Table(1);
       
            Cell cell;
            cell=new Cell();
            cell.add("Teachers should pass the entire set of case report form, i.e. including the part showing the similarities, to Dean/Head/Director via Department Chairman/Programme Director before returning the form to Academic and Quality Section by 9 June 2017, for submission to Professor CHAN Tai Man, Pro-Vice-Chancellor.").setFont(bold).setFontSize(8);
             table.addCell(cell);
        document.add(table);
        
        document.add(new Paragraph(""));
        // submission information
        document.add(new Paragraph("I.  Submission Information").setFont(bold).setFontSize(9));
        Table si=new Table(4);
        
        
        submissionInfo(si,info);
        
        
        
        document.add(si);
        document.add(new Paragraph(""));
        
 
        
        //paragraph comparisions
        document.add(new Paragraph("II.  Paragragh Comparisions").setFont(bold).setFontSize(9));
        document.add(new Paragraph(""));
        paragraphComparision(document , info,pdf);
        
        document.add(new Paragraph(""));
        //Teacher's Feedback
        document.add(new Paragraph("III.  Teacher's Feedback").setFont(bold).setFontSize(9));
        teacherFeedback(document,info);
        
        //footer
        
         //Close document
        
      }
      catch(com.google.gson.JsonSyntaxException a){
          document.add(new Paragraph("Wrong input format!!!"));
          return 1;
      }
      catch(YearException b){
          document.add(new Paragraph("Invalid Year!!!"));
          return 2;
      }
      catch(SemesterException c){
          document.add(new Paragraph("Invalid Semester!!!"));
          return 3;
      }
      catch(AssignmentNumberException d){
          document.add(new Paragraph("Invalid Assignment number!!!"));
          return 4;
      }
      catch(StudentIDLengthException e){
          document.add(new Paragraph("Invalid Student ID!!!"));
          return 5;
      }
      catch(MissingStudentIDException f){
          document.add(new Paragraph("Missing Student ID!!!"));
          return 6;
      }
      catch(MissingCourseCodeException g){
          document.add(new Paragraph("Missing Course Code!!!"));
          return 7;
      }
      catch(MissingCaseIDException h){
          document.add(new Paragraph("Missing Case ID!!!"));
          return 8;
      }
      catch(ParagraphTooLongException i){
          document.add(new Paragraph("Paragraph too long!!!"));
          return 9;
      }
     
     document.close();
     
       return 0;
    }
  
   public void submissionInfo(Table si, info info) throws IOException{
     
      PdfFont chinese = PdfFontFactory.createFont("src/fonts/NotoSansCJKtc-Regular.otf",PdfEncodings.IDENTITY_H, false);
        
        Cell cell2;
        int i,j;
        j=info.SubmissionNumInfoNo();
        for(i=0;i<j;i++){
            cell2=new Cell().add(info.SubmissionNumInfo[i].name).setFontSize(8);
            cell2.setBorderTop(new SolidBorder(Color.WHITE, 1));
            cell2.setBorderBottom(new SolidBorder(Color.WHITE, 1));
            cell2.setBorderLeft(new SolidBorder(Color.WHITE, 1));
            cell2.setBorderRight(new SolidBorder(Color.WHITE, 1));
            si.addCell(cell2);
            
            cell2=new Cell().add(new Integer(info.SubmissionNumInfo[i].info).toString()).setFontSize(8);
            cell2.setBorderTop(new SolidBorder(Color.WHITE, 1));
            cell2.setBorderBottom(new SolidBorder(Color.WHITE, 1));
            cell2.setBorderLeft(new SolidBorder(Color.WHITE, 1));
            cell2.setBorderRight(new SolidBorder(Color.WHITE, 1));
            si.addCell(cell2);
            
        }
        j=info.SubmissionStringInfoNo();
        for(i=0;i<j;i++){
            
         
            cell2=new Cell().add(info.SubmissionStringInfo[i].name).setFontSize(8);
            cell2.setBorderTop(new SolidBorder(Color.WHITE, 1));
            cell2.setBorderBottom(new SolidBorder(Color.WHITE, 1));
            cell2.setBorderLeft(new SolidBorder(Color.WHITE, 1));
            cell2.setBorderRight(new SolidBorder(Color.WHITE, 1));
            si.addCell(cell2);
            
            //cell2=new Cell().add(info.SubmissionStringInfo[i].info).setFontSize(8);
            
            if(new isChinese().isChinese(info.SubmissionStringInfo[i].info)){
               cell2=new Cell().add(info.SubmissionStringInfo[i].info).setFont(chinese).setFontSize(8); 
            }
            else{
            cell2=new Cell().add(info.SubmissionStringInfo[i].info).setFontSize(8);
            }
            cell2.setBorderTop(new SolidBorder(Color.WHITE, 1));
            cell2.setBorderBottom(new SolidBorder(Color.WHITE, 1));
            cell2.setBorderLeft(new SolidBorder(Color.WHITE, 1));
            cell2.setBorderRight(new SolidBorder(Color.WHITE, 1));
            si.addCell(cell2);
            
        }
        
       
   }
   
   public void paragraphComparision(Document document , info info,PdfDocument pdf)throws IOException{
        int comparisionNo=info.comparisionNo();
        PdfFont chinese = PdfFontFactory.createFont("src/fonts/NotoSansCJKtc-Regular.otf",PdfEncodings.IDENTITY_H, false);
       
        PdfFont bold = PdfFontFactory.createFont(FontConstants.TIMES_BOLD);
        PdfFont roman = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);
        int i,j;
        String id;
        Table sourceinfo;
        Table compare;
        float[] cw={9,92};
        
        
        for(i=0;i<comparisionNo;i++){
            sourceinfo=new Table(cw);
            
           compare= new Table(new float[] {0,230, 230}) // in points
        .setWidthPercent(100)
        .setFixedLayout();
          
            document.add(new Paragraph("Source"+(i+1)).setFontSize(9).setUnderline(1,-2).setFont(roman));
            Cell cell;
            cell = new Cell().add("Source Type:").setFontSize(8).setFont(roman);
            cell.setBorderTop(new SolidBorder(Color.WHITE, 1));
            cell.setBorderBottom(new SolidBorder(Color.WHITE, 1));
            cell.setBorderLeft(new SolidBorder(Color.WHITE, 1));
            cell.setBorderRight(new SolidBorder(Color.WHITE, 1));
            sourceinfo.addCell(cell);
            
            
           if(new isChinese().isChinese(info.Comparisions[i].SourceType)){
               cell = new Cell().add(info.Comparisions[i].SourceType).setFont(chinese).setFontSize(8);
           }
           else{
                cell = new Cell().add(info.Comparisions[i].SourceType).setFont(roman).setFontSize(8);
           }
            cell.setBorderTop(new SolidBorder(Color.WHITE, 1));
            cell.setBorderBottom(new SolidBorder(Color.WHITE, 1));
            cell.setBorderLeft(new SolidBorder(Color.WHITE, 1));
            cell.setBorderRight(new SolidBorder(Color.WHITE, 1));
            sourceinfo.addCell(cell);
            cell = new Cell().add("Source:").setFontSize(8).setFont(roman);
            cell.setBorderTop(new SolidBorder(Color.WHITE, 1));
            cell.setBorderBottom(new SolidBorder(Color.WHITE, 1));
            cell.setBorderLeft(new SolidBorder(Color.WHITE, 1));
            cell.setBorderRight(new SolidBorder(Color.WHITE, 1));
            sourceinfo.addCell(cell);
            
            
            if(new isChinese().isChinese(info.Comparisions[i].Source)){
                 cell = new Cell().add(info.Comparisions[i].Source).setFont(chinese).setFontSize(8);
            }
            else{
                cell = new Cell().add(info.Comparisions[i].Source).setFont(roman).setFontSize(8);
            }
            cell.setBorderTop(new SolidBorder(Color.WHITE, 1));
            cell.setBorderBottom(new SolidBorder(Color.WHITE, 1));
            cell.setBorderLeft(new SolidBorder(Color.WHITE, 1));
            cell.setBorderRight(new SolidBorder(Color.WHITE, 1));
            sourceinfo.addCell(cell);
            cell = new Cell().add("Veriguide Remarks:").setFontSize(8).setFont(roman);
            cell.setBorderTop(new SolidBorder(Color.WHITE, 1));
            cell.setBorderBottom(new SolidBorder(Color.WHITE, 1));
            cell.setBorderLeft(new SolidBorder(Color.WHITE, 1));
            cell.setBorderRight(new SolidBorder(Color.WHITE, 1));
            sourceinfo.addCell(cell);
            
            if(new isChinese().isChinese(info.Comparisions[i].VeriguideRemarks)){
                 cell = new Cell().add(info.Comparisions[i].VeriguideRemarks).setFont(chinese).setFontSize(8);
            }
            else{
                cell = new Cell().add(info.Comparisions[i].VeriguideRemarks).setFont(roman).setFontSize(8);
            }
            cell.setBorderTop(new SolidBorder(Color.WHITE, 1));
            cell.setBorderBottom(new SolidBorder(Color.WHITE, 1));
            cell.setBorderLeft(new SolidBorder(Color.WHITE, 1));
            cell.setBorderRight(new SolidBorder(Color.WHITE, 1));
            sourceinfo.addCell(cell);
            
            
            document.add(sourceinfo);
            
            //the compare form
            
            cell = new Cell().add(new Paragraph("ID").setFont(bold).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            cell.setKeepTogether(true);
            compare.addHeaderCell(cell);
            cell = new Cell().add("Student's Submission").setFont(bold).setFontSize(8).setTextAlignment(TextAlignment.CENTER);
            cell.setKeepTogether(true);
            compare.addHeaderCell(cell);
            
            cell = new Cell().add("  Source Paragraph  ").setFont(bold).setFontSize(8).setTextAlignment(TextAlignment.CENTER);
            cell.setKeepTogether(true);
            compare.addHeaderCell(cell);
            //document.add(compare);
            for(j=0;j<info.Comparisions[i].ParagraphNo();j++){
                   /*compare= new Table(new float[] {0,230, 230}) // in points
        .setWidthPercent(100)
        .setFixedLayout();*/
               //pdf.addEventHandler(PdfDocumentEvent.END_PAGE, new TableHeaderEventHandler(document,compare));
              
                id="1."+(j+1);
                cell = new Cell().add(id).setFontSize(8).setTextAlignment(TextAlignment.CENTER);
                cell.setKeepTogether(true);
                compare.addCell(cell);
                if(new isChinese().isChinese(info.Comparisions[i].Paragragh[j])){
                    cell = new Cell().add(new Paragraph(info.Comparisions[i].Paragragh[j]).setFont(chinese).setFontSize(8));
                    if(info.Comparisions[i].Paragragh[j].length()<=121){
                        cell.setKeepTogether(true);
                    }
                }
                else{
                    cell = new Cell().add(new Paragraph(info.Comparisions[i].Paragragh[j]).setFontSize(8));
                    if(info.Comparisions[i].Paragragh[j].length()<=260){
                        cell.setKeepTogether(true);
                    }
                }
                
                
                //cell.setKeepTogether(true);
                compare.addCell(cell);
                
                if(new isChinese().isChinese(info.Comparisions[i].SourceParagragh[j])){
                    cell = new Cell().add(new Paragraph(info.Comparisions[i].SourceParagragh[j]).setFont(chinese).setFontSize(8));
                    if(info.Comparisions[i].SourceParagragh[j].length()<=121){
                        cell.setKeepTogether(true);
                    }
                }
                else{
                    cell = new Cell().add(new Paragraph(info.Comparisions[i].SourceParagragh[j]).setFontSize(8));
                    if(info.Comparisions[i].SourceParagragh[j].length()<=260){
                        cell.setKeepTogether(true);
                    }
                }
                //cell.setKeepTogether(true);
                
                compare.addCell(cell);
              
              
              
               
               
               //leave for later
                
                
            }
             //compare.setKeepTogether(true);
             document.add(compare);
          
            document.add(new Paragraph(""));
            //document.add(compare);
            document.add(new Paragraph(""));
            
        }
   }
    
   public void teacherFeedback(Document document, info info)throws IOException{
       //Teacher's Feedback
       PdfFont zapfdingbats = PdfFontFactory.createFont(FontConstants.ZAPFDINGBATS);
      PdfFont chinese = PdfFontFactory.createFont("src/fonts/NotoSansCJKtc-Regular.otf",PdfEncodings.IDENTITY_H, false);
       
       
        PdfFont bold = PdfFontFactory.createFont(FontConstants.TIMES_BOLD);
        PdfFont roman = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);
       document.add(new Paragraph(" "));
      
       Text tx1=new Text("1. Do you think this submission is plagiarized case?          ").setFont(roman).setFontSize(9);
       Text tx2=new Text("o").setFont(zapfdingbats).setFontSize(14);
       Text tx3=new Text("Yes    ").setFont(roman).setFontSize(9);
       Text tx4=new Text("No").setFont(roman).setFontSize(9);
       Paragraph p=new Paragraph();
       p.add(tx1);
       p.add(tx2);
       p.add(tx3);
       p.add(tx2);
       p.add(tx4);
       document.add(p);
        document.add(new Paragraph("(Put a tick in the appropriate box.)").setFont(roman).setFontSize(9).setItalic());
        
        document.add(new Paragraph(""));
       
        
        document.add(new Paragraph("2. Reasons for Judgment: ____________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________").setFont(roman).setFontSize(9));
        //the signature part
        Table signature= new Table(new float[] {40,180, 40,180}) // in points
        .setWidthPercent(100)
        .setFixedLayout();
        Cell cell=new Cell();
        
        //reviewer
        cell.add(new Paragraph("Reviewed by:").setFont(roman).setFontSize(9).setTextAlignment(TextAlignment.LEFT));
        cell.setBorderTop(new SolidBorder(Color.WHITE, 1));
        cell.setBorderBottom(new SolidBorder(Color.WHITE, 1));
        cell.setBorderLeft(new SolidBorder(Color.WHITE, 1));
        cell.setBorderRight(new SolidBorder(Color.WHITE, 1));
        signature.addCell(cell);
        cell=new Cell();
        cell.add(new Paragraph("___________________________________________").setFont(roman).setFontSize(9));
        
        if(new isChinese().isChinese(info.reviewer)){
           cell.add(new Paragraph("("+info.reviewer+")").setFont(chinese).setFontSize(9).setTextAlignment(TextAlignment.RIGHT));
        }  
        else{
            cell.add(new Paragraph("("+info.reviewer+")").setFont(roman).setFontSize(9).setTextAlignment(TextAlignment.RIGHT));
        }
        
         cell.setBorderTop(new SolidBorder(Color.WHITE, 1));
        cell.setBorderBottom(new SolidBorder(Color.WHITE, 1));
        cell.setBorderLeft(new SolidBorder(Color.WHITE, 1));
        cell.setBorderRight(new SolidBorder(Color.WHITE, 1));
        signature.addCell(cell);
        cell=new Cell();
        cell.add(new Paragraph("Date:").setFont(roman).setFontSize(9).setTextAlignment(TextAlignment.CENTER));
         cell.setBorderTop(new SolidBorder(Color.WHITE, 1));
        cell.setBorderBottom(new SolidBorder(Color.WHITE, 1));
        cell.setBorderLeft(new SolidBorder(Color.WHITE, 1));
        cell.setBorderRight(new SolidBorder(Color.WHITE, 1));
        signature.addCell(cell);
        cell=new Cell();
        cell.add(new Paragraph("___________________________________________").setFont(roman).setFontSize(9));
         cell.setBorderTop(new SolidBorder(Color.WHITE, 1));
        cell.setBorderBottom(new SolidBorder(Color.WHITE, 1));
        cell.setBorderLeft(new SolidBorder(Color.WHITE, 1));
        cell.setBorderRight(new SolidBorder(Color.WHITE, 1));
        signature.addCell(cell);
        
         //comfirmer
        cell=new Cell();
        cell.add(new Paragraph("Confirmed by:").setFont(roman).setFontSize(9).setTextAlignment(TextAlignment.LEFT));
        cell.setBorderTop(new SolidBorder(Color.WHITE, 1));
        cell.setBorderBottom(new SolidBorder(Color.WHITE, 1));
        cell.setBorderLeft(new SolidBorder(Color.WHITE, 1));
        cell.setBorderRight(new SolidBorder(Color.WHITE, 1));
        signature.addCell(cell);
        cell=new Cell();
        cell.add(new Paragraph("___________________________________________").setFont(roman).setFontSize(9));
        
        if(new isChinese().isChinese(info.confirmer)){
             cell.add(new Paragraph("("+info.confirmer+")").setFont(chinese).setFontSize(9).setTextAlignment(TextAlignment.RIGHT));
        }
        else{
            cell.add(new Paragraph("("+info.confirmer+")").setFont(roman).setFontSize(9).setTextAlignment(TextAlignment.RIGHT));
        }
        cell.setBorderTop(new SolidBorder(Color.WHITE, 1));
        cell.setBorderBottom(new SolidBorder(Color.WHITE, 1));
        cell.setBorderLeft(new SolidBorder(Color.WHITE, 1));
        cell.setBorderRight(new SolidBorder(Color.WHITE, 1));
        signature.addCell(cell);
        cell=new Cell();
        cell.add(new Paragraph("Date:").setFont(roman).setFontSize(9).setTextAlignment(TextAlignment.CENTER));
         cell.setBorderTop(new SolidBorder(Color.WHITE, 1));
        cell.setBorderBottom(new SolidBorder(Color.WHITE, 1));
        cell.setBorderLeft(new SolidBorder(Color.WHITE, 1));
        cell.setBorderRight(new SolidBorder(Color.WHITE, 1));
        signature.addCell(cell);
        cell=new Cell();
        cell.add(new Paragraph("___________________________________________").setFont(roman).setFontSize(9));
         cell.setBorderTop(new SolidBorder(Color.WHITE, 1));
        cell.setBorderBottom(new SolidBorder(Color.WHITE, 1));
        cell.setBorderLeft(new SolidBorder(Color.WHITE, 1));
        cell.setBorderRight(new SolidBorder(Color.WHITE, 1));
        signature.addCell(cell);
        
        //endorsor
        cell=new Cell();
        cell.add(new Paragraph("Endorsed by:").setFont(roman).setFontSize(9).setTextAlignment(TextAlignment.LEFT));
        cell.setBorderTop(new SolidBorder(Color.WHITE, 1));
        cell.setBorderBottom(new SolidBorder(Color.WHITE, 1));
        cell.setBorderLeft(new SolidBorder(Color.WHITE, 1));
        cell.setBorderRight(new SolidBorder(Color.WHITE, 1));
        signature.addCell(cell);
        cell=new Cell();
        cell.add(new Paragraph("___________________________________________").setFont(roman).setFontSize(9));
        
        if(new isChinese().isChinese(info.endorsor)){
             cell.add(new Paragraph("("+info.endorsor+")").setFont(chinese).setFontSize(9).setTextAlignment(TextAlignment.RIGHT));
        }
        else{
            cell.add(new Paragraph("("+info.endorsor+")").setFont(roman).setFontSize(9).setTextAlignment(TextAlignment.RIGHT));
        }
             cell.setBorderTop(new SolidBorder(Color.WHITE, 1));
        cell.setBorderBottom(new SolidBorder(Color.WHITE, 1));
        cell.setBorderLeft(new SolidBorder(Color.WHITE, 1));
        cell.setBorderRight(new SolidBorder(Color.WHITE, 1));
        signature.addCell(cell);
        cell=new Cell();
        cell.add(new Paragraph("Date:").setFont(roman).setFontSize(9).setTextAlignment(TextAlignment.CENTER));
         cell.setBorderTop(new SolidBorder(Color.WHITE, 1));
        cell.setBorderBottom(new SolidBorder(Color.WHITE, 1));
        cell.setBorderLeft(new SolidBorder(Color.WHITE, 1));
        cell.setBorderRight(new SolidBorder(Color.WHITE, 1));
        signature.addCell(cell);
        cell=new Cell();
        cell.add(new Paragraph("___________________________________________").setFont(roman).setFontSize(9));
         cell.setBorderTop(new SolidBorder(Color.WHITE, 1));
        cell.setBorderBottom(new SolidBorder(Color.WHITE, 1));
        cell.setBorderLeft(new SolidBorder(Color.WHITE, 1));
        cell.setBorderRight(new SolidBorder(Color.WHITE, 1));
        signature.addCell(cell);
        
       
      
        document.add(signature);
        
        
        
        
        
   }
  }

//set Exceptions
class YearException extends Exception{
    public YearException(){};
}
class SemesterException extends Exception{
    public SemesterException(){};
}
class AssignmentNumberException extends Exception{
    public AssignmentNumberException(){};
}
class ParagraphException extends Exception{
    public ParagraphException(){};
}
class StudentIDLengthException extends Exception{
    public StudentIDLengthException(){};
}
class MissingStudentIDException extends Exception{
    public MissingStudentIDException(){};
}
class MissingCourseCodeException extends Exception{
    public MissingCourseCodeException(){};
}

class MissingCaseIDException extends Exception{
    public MissingCaseIDException(){};
}
class ParagraphTooLongException extends Exception{
    public ParagraphTooLongException(){};
}
