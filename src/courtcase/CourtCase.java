package courtcase;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;


public class CourtCase {

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // The main court Page to create and display The Court Case Managment program , , ,
    
    public static void main(String[] args) {
        
        DataHandle dataHandle = new DataHandle();
        
        Scanner inn = new Scanner(System.in);
        
        try{
        System.out.println("| ************************************************************************** |");
        System.out.println("| ************ Welcome to IBEX Court Case Management System  *************** |");
        System.out.println("| ************************************************************************** |\r\n");
        
        File f = new File("Court.xls");
        if(!f.exists()){
            System.out.println("| ************************************************************************** |");
            System.out.println("| *********************** System Installation ****************************** |");
            System.out.println("| ************************************************************************** |");

            
            while(true){
                System.out.print("\r\n>> Court name : ");
                String cName = inn.nextLine();
                System.out.print(">> Number of Cases per Judge : ");
                int numCasePerJudge = inn.nextInt();

                System.out.println("1. Finish Installation              2. Back         3. Cancel Installation");
                int choice = inn.nextInt();
                if(choice==1){
                    StartCreator st = new StartCreator();  
                    st.start(cName,numCasePerJudge);
                    break;
                }
                else if(choice==3){
                    System.exit(0); 
                }
                
                else if(choice==2){
                    System.out.println("Starting Reinstalling . . .");
                    inn.nextLine();
                }
                else{
                    System.out.println(">>Enter appropriate key!!\r\nStarting Reinstalling . . .");
                }
            
            }
            

            
        } 
        
        // the code below will excute after checking the admin information wheither it is first time or not
        
        boolean loop1 = true;
        boolean loop2 = true;
        while(loop1){
            System.out.println("\r\n| ********************************************************************* |");
            System.out.println("1. Check for basic case information ");
            System.out.println("2. Login ");
            System.out.println("3. Exit");
            System.out.println("| ********************************************************************* |\r\n");
        
            try{
            Scanner in = new Scanner(System.in);
            System.out.print("Type here >> ");
            int val = in.nextInt();
            
            switch (val) {
                case 1:
                    
                    System.out.print("Enter the case number here >> ");
                    String caseSearch = in.next();
                    
                    dataHandle.allView(caseSearch);
                    break;
                case 2:
                    //Creating admin
                    
        InputStream fsIP;
        HSSFWorkbook workBook;
        Cell cell = null;
        
        try {
                    
            fsIP = new FileInputStream("Court.xls");
     
            workBook = new HSSFWorkbook(fsIP);   
            cell = workBook.getSheetAt(1).getRow(1).getCell(1);
            //System.out.println("the id : " + cell.getStringCellValue());
            
        } catch (Exception ex) {
            System.out.println("Admin Checking error --> " + ex.getMessage());
        }
                    
                    if(cell==null){
                        Admin ad = new Admin();
                        ad.setId();
                        System.out.println("\r\n| ******************************************************************** |");
                        System.out.println("<< ************ HELLO! new ADMIN create your account here ************* >>");
                        System.out.println("Your Id number is : " + ad.getId());
                        
                        System.out.print("Enter Your new Password here : ");
                        String pwd = in.next();
                        ad.setpwd(pwd);
                        
                    }
                    System.out.println("\r\n| ************************** LOGIN PAGE ************************************ |\r\n");
                    while(loop2){
                        System.out.print("Enter your id number here : ");
                        String id = in.next();
                        System.out.print("Enter your password : ");
                        String pwd = in.next();
            
                        if(id.equals("") && pwd.equals("")){
                            break;
                        }
                            
                                char[] chars = id.toCharArray();
                                String a = "";
                                
                                for(int i=0;i<2;i++){
                                    a += chars[i];
                                }
                                
                                    if(a.equals("AD")){
                                        userCheck(id, pwd, 1);
                                        break;
                                    } 
                                    else if(a.equals("JU")){
                                        userCheck(id, pwd, 2);
                                        break; 
                                    }
                                    else if(a.equals("PL")){
                                        userCheck(id, pwd, 3);
                                        break;
                                    }
                                    else if(a.equals("AC")){
                                        userCheck(id, pwd, 4);
                                        break;
                                    }
                                    else{ 
                                        System.out.println("Please check your id first");
                                    }
                                        
                        
                    }   break;
                case 3:
                    break;
                    
                default:
                    break;
            }
          }catch(Exception e){
            System.out.println("| ************************* Please use appropriate input value!! **************************** |");
            }
        }
        
        }catch(InputMismatchException ex){
            System.out.println("| ************************* Please use appropriate input value!! **************************** |");
        }
    }
    
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Checking the user input id and creating the appropriate object of the User sending their id as a parameter
    // for their created object
    
    public static void userCheck(String id, String pwd, int sheetNum){
    
        try {
            InputStream fsIP = null;
            HSSFWorkbook workBook = null;
            Row row = null;
            Cell cell = null;
            Cell cell2 = null;
            int num = 0;
            
            fsIP = new FileInputStream("Court.xls");
            
            workBook = new HSSFWorkbook(fsIP);   
            
            Iterator<Row> ri = workBook.getSheetAt(sheetNum).rowIterator();
            
            while(ri.hasNext()){
                row = ri.next();
                num += 1;
                cell = row.getCell(0);
                cell2 = row.getCell(1);
            
                if(cell2.getStringCellValue().equals(pwd) && cell.getStringCellValue().equals(id)){
                        
                    if(sheetNum==1){
                        Admin ad = new Admin();
                        ad.addIDAndPassword(id, pwd);
                        ad.menu();
                        break;
                    }
                    else if(sheetNum==2){
                        Judge ju = new Judge(id , pwd);
                        break;
                    }
                    else if(sheetNum==3){
                        Plaintiff pl = new Plaintiff(id , pwd);
                        break;
                    }
                    else if(sheetNum==4){
                        Acused ac = new Acused(id, pwd);
                        break;
                    }
                }
                
            }
            
        } catch (Exception ex) {
            Logger.getLogger(CourtCase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////   
    //Check if any admin has registered for the software on the first running of the app
    public static boolean adminCheck(){
        
        try {
        InputStream fsIP = null;
        HSSFWorkbook workBook = null;
        Cell cell = null;
            
            fsIP = new FileInputStream("Court.xls");
            //Workbook wb = WorkbookFactory.create(fsIP);
     
            workBook = new HSSFWorkbook(fsIP);   
            cell = workBook.getSheetAt(1).getRow(1).getCell(1);
            //System.out.println("the id : " + cell.getStringCellValue());
            
            if(cell==null){
                return false;
            }
            
        } catch (Exception ex) {
            System.out.println("Admin Checking error --> " + ex.getMessage());
        } 
        
        return true;
    }
    
}
