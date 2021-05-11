package courtcase;

import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public final class StartCreator {

    static HSSFWorkbook workBook;
    static HSSFSheet sheet;
    
    public StartCreator() {
        
       

    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Creates the database on the first launch of the program
    public void start(String cName, int numCases){
        workBook = new HSSFWorkbook();
        workBook.createSheet("Case");
        workBook.createSheet("Admin");
        workBook.createSheet("Judge");
        workBook.createSheet("Plaintiff");
        workBook.createSheet("Acused");
        workBook.createSheet("Witness");
            
        CaseCreator();
        AdminCreator(cName,numCases);
        JudgeCreator();
        PlaintiffCreator();
        AcusedCreator();  
        witnessCreator();
        
    }
    
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Creating the case
    public void CaseCreator(){
        
        try {
            sheet = workBook.getSheetAt(0);
            
            HSSFRow row = sheet.createRow(0);  
                
                row.createCell(0).setCellValue("CaseNo");
                row.createCell(1).setCellValue("JudgeId");
                row.createCell(2).setCellValue("PlaintiffId");
                row.createCell(3).setCellValue("AcusedId");
                row.createCell(4).setCellValue("Case Title");
                row.createCell(5).setCellValue("Case Description");
                row.createCell(6).setCellValue("Case Status");
                row.createCell(7).setCellValue("case Type");
                row.createCell(8).setCellValue("Court Date");
                row.createCell(9).setCellValue("Final Decision");
                
                for(int i=0; i<10; i++){
                    sheet.autoSizeColumn(i);
                }
            
            workBook.write(new FileOutputStream("Court.xls"));
            workBook.close();
            
            System.out.println("The Court Database created . . .");
            
        } catch (Exception ex) {
            Logger.getLogger(StartCreator.class.getName()).log(Level.SEVERE, null, ex);
        } 
    
        
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Creating the Admin Sheet    
    public void AdminCreator(String cName, int numCases){
    
        try {
            sheet = workBook.getSheetAt(1);
            
            HSSFRow row = sheet.createRow(0); 
            HSSFRow row2 = sheet.createRow(1);
                
                row.createCell(0).setCellValue("AdminId");
                row2.createCell(0).setCellValue("AD1");
                row.createCell(1).setCellValue("Admin_Password");
                row.createCell(2).setCellValue("Court Name");
                row2.createCell(2).setCellValue(cName);
                row.createCell(3).setCellValue("Cases per Judge");
                row2.createCell(3).setCellValue(numCases);
                
                for(int i=0; i < 4; i++){
                    sheet.autoSizeColumn(i);
                }
            
            workBook.write(new FileOutputStream("Court.xls"));
            workBook.close();
            
            System.out.println(">>> Admin table created . . .");
            
        } catch (Exception ex) {
            Logger.getLogger(StartCreator.class.getName()).log(Level.SEVERE, null, ex);
        } 
    
    
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Creating the Judge Sheet        
    public void JudgeCreator(){
        
        try {
            sheet = workBook.getSheetAt(2);
            
            HSSFRow row = sheet.createRow(0);  
                
                row.createCell(0).setCellValue("JudgeId");
                row.createCell(1).setCellValue("Judge_Password");
                row.createCell(2).setCellValue("Number_of_Cases");
                row.createCell(3).setCellValue("Judge_Status");
                row.createCell(4).setCellValue("Judge_Name");
                row.createCell(5).setCellValue("Sex");
                row.createCell(6).setCellValue("State");
                
                for(int i=0; i < 7; i++){
                    sheet.autoSizeColumn(i);
                }
            
            workBook.write(new FileOutputStream("Court.xls"));
            workBook.close();
            
            System.out.println(">>> Judge table created . . .");
            
        } catch (Exception ex) {
            Logger.getLogger(StartCreator.class.getName()).log(Level.SEVERE, null, ex);
        } 
    
    
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Creating the Plaintiff Sheet        
    public void PlaintiffCreator(){
        
        try {
            sheet = workBook.getSheetAt(3);
            
            HSSFRow row = sheet.createRow(0);  
                
                row.createCell(0).setCellValue("PlaintiffId");
                row.createCell(1).setCellValue("Plaintiff_Password");
                row.createCell(2).setCellValue("Plaintiff_Name");
                row.createCell(3).setCellValue("Sex");
                row.createCell(4).setCellValue("Age");
                row.createCell(5).setCellValue("SSN or Office Number");
                
                
                for(int i=0; i < 6; i++){
                    sheet.autoSizeColumn(i);
                }
            
            workBook.write(new FileOutputStream("Court.xls"));
            workBook.close();
            
            System.out.println(">>> Plaintiff table created . . .");
            
        } catch (Exception ex) {
            Logger.getLogger(StartCreator.class.getName()).log(Level.SEVERE, null, ex);
        } 
    
    
    
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Creating the Acused Sheet        
    public void AcusedCreator(){
        
        try {
            sheet = workBook.getSheetAt(4);
            
            HSSFRow row = sheet.createRow(0);  
                
                row.createCell(0).setCellValue("AcusedId");
                row.createCell(1).setCellValue("Acused_Password");
                row.createCell(2).setCellValue("Acused_Name");
                row.createCell(3).setCellValue("Sex");
                row.createCell(4).setCellValue("Age");
                row.createCell(5).setCellValue("SSN or Office Number");
                
                for(int i=0; i < 6; i++){
                    sheet.autoSizeColumn(i);
                }
            
            workBook.write(new FileOutputStream("Court.xls"));
            workBook.close();
            
            System.out.println(">>> Acused table created . . .");
            
        } catch (Exception ex) {
            Logger.getLogger(StartCreator.class.getName()).log(Level.SEVERE, null, ex);
        } 
    
    
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Creating the Witness Sheet        
    public void witnessCreator(){
        
        try {
            sheet = workBook.getSheetAt(5);
            
            HSSFRow row = sheet.createRow(0);  
                
                row.createCell(0).setCellValue("Case Number");
                row.createCell(1).setCellValue("Witness Document Description");
                
                for(int i=0; i < 2; i++){
                    sheet.autoSizeColumn(i);
                }
            
            workBook.write(new FileOutputStream("Court.xls"));
            workBook.close();
            
            System.out.println(">>> Witness table created . . .");
            
        } catch (Exception ex) {
            Logger.getLogger(StartCreator.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    }
    
}
