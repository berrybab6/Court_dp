package courtcase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class DataHandle {
   Scanner in;
    public DataHandle() {
        in = new Scanner(System.in);
              
        
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // return the admin ID on admin login  
    public String setId(){
        
        String id="";
        FileInputStream fsIP;
        HSSFWorkbook workBook;
        Cell cell;
        
        try {
            
            fsIP = new FileInputStream(new File("Court.xls"));
            workBook = new HSSFWorkbook(fsIP);   
            cell = workBook.getSheetAt(1).getRow(1).getCell(0);
            id = cell.getStringCellValue();
                 
            fsIP.close();
            workBook.close();
        } catch (Exception ex) {
            System.out.println("Admin Id and password check error --> " + ex.getMessage());
        } 
        
        return id;
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //the form to change the password first by checking it with the old password
    public void adminChangePassword(String pwd, String adminId){    
        
        System.out.print(">> Enter your old password : ");
        String oldPwd = in.next();
        if(pwd.equals(oldPwd)){
            while(true){
                try {
                    System.out.print("Enter your new password : ");
                    String newPwd = in.next();
                    System.out.print("Confirm the password : ");
                    String confirmPwd = in.next();

                    //Checking if the password confirmation is true to change the password
                    if(newPwd.equals(confirmPwd)){
                        InputStream fs;

                        Row row;
                        Cell cell;
                        Cell cell2;
                        fs = new FileInputStream("Court.xls");
                        HSSFWorkbook wb = new HSSFWorkbook(fs);

                        Iterator<Row> ri = wb.getSheetAt(1).rowIterator();

                        while(ri.hasNext()){
                            row = ri.next();
                            cell = row.getCell(0);
                            cell2 = row.getCell(1);
                            if(cell.getStringCellValue().equals(adminId)){
                                cell2.setCellValue(newPwd);
                            }

                        }

                        FileOutputStream fileOut = new FileOutputStream("Court.xls");
                        wb.write(fileOut);

                        fs.close();
                        wb.close();
                        fileOut.close();


                        pwd = newPwd;
                        System.out.println("| ************ You have changed your password successfully!! ************ |" 
                                + "\r\n" + "<<< ------------------------------------- ----------------------------- >>>");
                        break;
                    }
                    else{
                        System.out.println("| ************** The password doesnot match, Try again!! **************** |");
                    }
                } catch (Exception ex) {
                    Logger.getLogger(Judge.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Returns all waiting cases by category of Criminal and civil case using the parameter needed
    public ArrayList<String> allWaitingCase(int needed){
        
        ArrayList<String> allWaiting = new ArrayList<>();
        try {
            //System.out.println("Displaying all waiting cases from the database with preference order");
            InputStream fs;
            
            Row row;
            Cell cell;
            Cell cell2;
            Cell cell3;
            fs = new FileInputStream("Court.xls");
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            
            Iterator<Row> ri = wb.getSheetAt(0).rowIterator();
            
            while(ri.hasNext()){
                row = ri.next();
                cell = row.getCell(0);
                cell2 = row.getCell(1);
                cell3 = row.getCell(7);
                if(needed==0){
                    if(cell2.getStringCellValue().equals("") && (int)cell3.getNumericCellValue()==0){
                        allWaiting.add(cell.getStringCellValue());  
                    }   
                }
                else{
                    if(cell2.getStringCellValue().equals("")){
                        allWaiting.add(cell.getStringCellValue());  
                    }              
                }
                
            }
            
        } catch (Exception ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    return allWaiting;
    
    }
    
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Adding Plaintiff or Acused to the database
    public void AddingOpponentToDatabase(String idPrefix,int shNum, String id0, String pwd, String name, String sex, int age, String ssn){
        try {
            InputStream fs;
            
            Row row;
            Cell cell;
            fs = new FileInputStream("Court.xls");
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            
            int rowNUm = wb.getSheetAt(shNum).getLastRowNum() + 1;
            id0 = idPrefix + rowNUm; 
            System.out.println(">> Id ");
            
            System.out.println("\r\n<<< ---------------------------   Report  ------------------------------ >>>>\r\n");
            System.out.println( name + " is successfuly added to the database!!");
            System.out.println("* "+ name + "'s New Id is : " + id0);
            System.out.println("* "+ name + "'s New Password is :  0000"
                       + "\r\n>> |** Please make sure to change your default PASSWORD as soon as possible!! **|");
            System.out.println("\r\n<<< -------------------------------------------------------------------- >>>\r\n");
            
            row = wb.getSheetAt(shNum).createRow(rowNUm);
            row.createCell(0).setCellValue(id0);
            row.createCell(1).setCellValue(pwd);
            row.createCell(2).setCellValue(name);
            row.createCell(3).setCellValue(sex);
            row.createCell(4).setCellValue(age);
            row.createCell(5).setCellValue(ssn);
            
            wb.write(new FileOutputStream("Court.xls"));
            wb.close();
            
        } catch (Exception ex) {
            Logger.getLogger(DataHandle.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Updating the number of cases of the judge in the judge table of the Court databse
    public void UpdateJudgeCaseNumber(String juId){
        try {
            
            InputStream fsJudge;
            
            Row rowJudge;
            Cell cellJ;
            fsJudge = new FileInputStream("Court.xls");
            HSSFWorkbook wbJudge = new HSSFWorkbook(fsJudge);
            
            Iterator<Row> riJudge = wbJudge.getSheetAt(2).rowIterator();
            rowJudge = riJudge.next();
            while(riJudge.hasNext()){
                rowJudge = riJudge.next();
                cellJ = rowJudge.getCell(0);
                if(cellJ.getStringCellValue().equals(juId)){
                    rowJudge.getCell(2).setCellValue(rowJudge.getCell(2).getNumericCellValue()+1);
                }
                
            }
            wbJudge.write(new FileOutputStream("Court.xls"));
            wbJudge.close();
            
        } catch (Exception ex) {
            Logger.getLogger(DataHandle.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
   
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Adding the admin password to the database on the first lauch
    public void setpwd(String pwd, String password){
        
        FileInputStream fsIP;
        HSSFWorkbook workBook;
        Cell cell;
        
        try {
            
            fsIP = new FileInputStream(new File("Court.xls"));
            workBook = new HSSFWorkbook(fsIP);
            cell = workBook.getSheetAt(1).getRow(1).createCell(1);
            cell.setCellValue(pwd);
            FileOutputStream fileOut = new FileOutputStream("Court.xls");
            workBook.write(fileOut);
            password = cell.getStringCellValue();
            
            fsIP.close();
            workBook.close();
        } catch (IOException ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////    
     //Adding the new judge to the judge excel table 
    public void addJudgeDatabase(String id, String pwd, int numberOfCases, int status, String name, String sex, int r,int state){
        
        try {
            
            InputStream fs2;
            HSSFWorkbook wb2;
            Row row = null;
            Cell cell = null;
            
            fs2 = new FileInputStream("Court.xls");
            wb2 = new HSSFWorkbook(fs2);
            
            row = wb2.getSheetAt(2).createRow(r);
            row.createCell(0).setCellValue(id);
            row.createCell(1).setCellValue(pwd);
            row.createCell(2).setCellValue(numberOfCases);
            row.createCell(3).setCellValue(status);
            row.createCell(4).setCellValue(name);
            row.createCell(5).setCellValue(sex);
            row.createCell(6).setCellValue(state);
            
            wb2.write(new FileOutputStream("Court.xls"));
            wb2.close();
            
        } catch (Exception ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////    
    //Adding all the case information to the databse 
    public void caseToDatabase(String caseNum,String juId, String plId, String acId, String caseTitle, String caseDisc, int caseStatus, int caseType, String date, String result){
    
            try {
            
            InputStream fs2;
            HSSFWorkbook wb2;
            Row row = null;
            Cell cell = null;
            
            fs2 = new FileInputStream("Court.xls");
            wb2 = new HSSFWorkbook(fs2);
            int lastRow = wb2.getSheetAt(0).getLastRowNum() + 1;
                    
            row = wb2.getSheetAt(0).createRow(lastRow);
            row.createCell(0).setCellValue(caseNum);
            row.createCell(1).setCellValue(juId);
            row.createCell(2).setCellValue(plId);
            row.createCell(3).setCellValue(acId);
            row.createCell(4).setCellValue(caseTitle);
            row.createCell(5).setCellValue(caseDisc);
            row.createCell(6).setCellValue(caseStatus);
            row.createCell(7).setCellValue(caseType);
            row.createCell(8).setCellValue(date);
            row.createCell(9).setCellValue(result);
            
            wb2.write(new FileOutputStream("Court.xls"));
            wb2.close();
            
        } catch (Exception ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
   
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Checks if the SSN Number is already found in the database
    public String checkSSNorOfficeNum(String ssn_officeNum, int shNum){ 
        InputStream fs = null;
        try {
            Row row;
            Cell cell;
            Cell cell2;
            fs = new FileInputStream("Court.xls");
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            Iterator<Row> ri = wb.getSheetAt(shNum).rowIterator();
            while(ri.hasNext()){
                row = ri.next();
                cell2 = row.getCell(5);
                if(cell2.getStringCellValue().equals(ssn_officeNum)){
                    cell = row.getCell(0);
                    return cell.getStringCellValue();
                }
                
            }
                 
        } catch (Exception ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fs.close();
                
            } catch (IOException ex) {
                Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return "";
    }    


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Returns Available judges list by using the category of Criminal and civil from the parameter caseType
    public ArrayList<String> availableJudgeList(int caseType){
        ArrayList<String> availableJudges = new ArrayList<>();
        try {
            
            Row row;
            Row row2;
            Cell adminCell;
            Cell cell;
            Cell cell1;
            Cell cell2;
            Cell cell0;
            InputStream fs = new FileInputStream("Court.xls");
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            
            Iterator<Row> ri = wb.getSheetAt(2).rowIterator();
            
            row2 = wb.getSheetAt(1).getRow(1);
            adminCell = row2.getCell(3);
            int numJudges = (int)adminCell.getNumericCellValue();
            if(caseType==0){
                int couter = 1;
                row = ri.next();
                while(ri.hasNext()){
                    row = ri.next();
                    cell = row.getCell(3);
                    cell1 = row.getCell(4);
                    cell2 = row.getCell(2);
                    cell0 = row.getCell(0);
                    
                    if(cell.getNumericCellValue()==0 && cell2.getNumericCellValue() < numJudges){
                        System.out.println(couter + ". Id: " + cell0.getStringCellValue() +" -  Name: "+ cell1.getStringCellValue());
                        couter += 1;
                        availableJudges.add(cell0.getStringCellValue());
                    }
                }
            }
            else if(caseType==1){
                int couter = 1;
                row = ri.next();
                while(ri.hasNext()){
                    row = ri.next();
                    cell1 = row.getCell(4);
                    cell2 = row.getCell(2);
                    cell0 = row.getCell(0);
                    if(cell2.getNumericCellValue() < numJudges){
                        System.out.println(couter +". Id: " + cell0.getStringCellValue() +" - Name: "+ cell1.getStringCellValue());
                        couter += 1;
                        availableJudges.add(cell0.getStringCellValue());
                    }
                }
                
            }
            
           fs.close();
           wb.close();
        } catch (Exception ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       return availableJudges;
    }
    
    
    
    
    ///////////////////////////////////////////// Judge Database handle ////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Adding the judge decision to the database
    public void judgeDecisionToDatabase(String id,String tempCaseNum, String caseDisc, String caseDecision){
    
       try {
           InputStream fs;
           
           Row row;
           Cell cell0;
           Cell cell;
           Cell cell2;
           Cell cell3;
           Cell cell4;
           fs = new FileInputStream("Court.xls");
           HSSFWorkbook wb = new HSSFWorkbook(fs);
           
           Iterator<Row> ri = wb.getSheetAt(0).rowIterator();
           Iterator<Row> rJudge = wb.getSheetAt(2).rowIterator();
           
           while(ri.hasNext()){
               row = ri.next();
               cell0 = row.getCell(0);
               cell = row.getCell(5);
               cell2 = row.getCell(9);
               cell3 = row.getCell(6);
               if(cell0.getStringCellValue().equals(tempCaseNum)){
                   cell.setCellValue(caseDisc);
                   cell2.setCellValue(caseDecision);
                   cell3.setCellValue(2);
                   System.out.println(">> The Case with Case Number " + tempCaseNum + " is CLOSED!! "
                           + "\r\n>> The Plaintiff and Acused has the right to appeal with in the next 100 Working days ");
                   
                   
                   
                   
                   
                   break;
               }
               
           }
           
           while(rJudge.hasNext()){
               row = rJudge.next();
               cell0 = row.getCell(0);
               cell = row.getCell(2);
               if(cell0.getStringCellValue().equals(id)){
                   cell.setCellValue(cell.getNumericCellValue()-1);
                   break;
               }
           }
           
           wb.write(new FileOutputStream("Court.xls"));
           wb.close();
       } catch (Exception ex) {
           Logger.getLogger(DataHandle.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Adding the date to the database
    public void AddDate(int yr, int mn, int dy, String tempCaseNum , String id){
    
       try {
           InputStream fs;
           
           Row row;
           Cell cell;
           Cell cell2;
           Cell cell3;
           fs = new FileInputStream("Court.xls");
           HSSFWorkbook wb = new HSSFWorkbook(fs);
           
           Iterator<Row> ri = wb.getSheetAt(0).rowIterator();
           
           while(ri.hasNext()){
               row = ri.next();
               cell = row.getCell(0);
               cell2 = row.getCell(1);
               cell3 = row.getCell(8);
               if(cell.getStringCellValue().equals(tempCaseNum) && cell2.getStringCellValue().equals(id)){
                   cell3.setCellValue(createDate(yr, mn, dy));
                   System.out.println("\r\n>> You have successfully extend the date of the case with case number - " + tempCaseNum);
                   break;
               }
               
           }
           
           wb.write(new FileOutputStream("Court.xls"));
           wb.close();
       } catch (Exception ex) {
           Logger.getLogger(DataHandle.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Creating the date to be updated by checking if it is possible to create
    public String createDate(int yr, int mn, int dy){
        
                Date d = new Date();
                String yearFormat = "YYYY";
                String monthFormat = "MM";
                String dayFormat = "DD";
                DateFormat year = new SimpleDateFormat(yearFormat);
                DateFormat month = new SimpleDateFormat(monthFormat);
                DateFormat day = new SimpleDateFormat(dayFormat);
                int YYYY = Integer.parseInt(year.format(d));
                int MM= Integer.parseInt(month.format(d));
                int DD= Integer.parseInt(day.format(d));
                String date="";  
                        if(yr>YYYY && mn<=12 && dy<=30){
                            date += yr + "/" +mn + "/" +dy;
                        }
                        else if(yr==YYYY && mn>MM && mn<=12 && dy<=30){
                            date += yr + "/" +mn + "/" +dy;
                        }
                        else if(yr==YYYY && mn==MM && dy>=DD && mn<=12 && dy<=30){
                            date += yr + "/" +mn + "/" +dy;
                        }
                        else{
                            System.out.println(">> Tyr again");
                        }       
        return date;
    }
    
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Generating the cases of the judge 
    public ArrayList<String> AllCasesOfTheJudge(int printPreference, String id){
    
        ArrayList<String> allCases = new ArrayList<>();
        try {
            FileInputStream fs;
            HSSFWorkbook wb;
            Cell cell;
            Cell cell2;
            Cell cell3;
            Cell cell4;
            Row row;
            
            fs = new FileInputStream(new File("Court.xls"));
            wb = new HSSFWorkbook(fs);
            Iterator<Row> ri = wb.getSheetAt(0).rowIterator();
            
            while(ri.hasNext()){
                row = ri.next();
                cell = row.getCell(0);
                cell2 = row.getCell(1);
                cell3 = row.getCell(4);
                cell4 = row.getCell(6);
                if(cell2.getStringCellValue().equals(id) && cell4.getNumericCellValue()==1){
                    allCases.add(cell.getStringCellValue());
                    if(printPreference==1){
                    System.out.println( ">> Case Number :  | " + cell.getStringCellValue() + " |        >> Case Title :  | " + cell3.getStringCellValue() + " | \r\n" );
                    }
                }
            }
            
            
            
        } catch (Exception ex) {
            Logger.getLogger(Judge.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    return allCases;
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Adding the witness document to the database
    public void addWitnessDocument(String witnessDocument, String caseNum){
       try {
            FileInputStream fs;
            HSSFWorkbook wb;
            Cell cell;
            Cell cell2;
            Row row;
           
            fs = new FileInputStream(new File("Court.xls"));
            wb = new HSSFWorkbook(fs);
            Iterator<Row> ri = wb.getSheetAt(5).rowIterator();
           
            while(ri.hasNext()){
               row = ri.next();
               cell = row.getCell(0);
               cell2 = row.getCell(1);
               if(cell.getStringCellValue().equals(caseNum)){
                   cell2.setCellValue(witnessDocument);
               }
               
            }
           
            FileOutputStream fileOut = new FileOutputStream("Court.xls");
            wb.write(fileOut);
            
            fs.close();
            wb.close();
            fileOut.close();
       } catch (Exception ex) {
           Logger.getLogger(DataHandle.class.getName()).log(Level.SEVERE, null, ex);
       }
        
        
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Checking if the witness form is alread filled before and return a boolean value
    public boolean checkForWitness(String caseNum){
    
        try {
            FileInputStream fs;
            HSSFWorkbook wb;
            Cell cell;
            Row row;
            
            fs = new FileInputStream(new File("Court.xls"));
            wb = new HSSFWorkbook(fs);
            Iterator<Row> ri = wb.getSheetAt(5).rowIterator();
            
            while(ri.hasNext()){
                row = ri.next();
                cell = row.getCell(0);
                if(cell.getStringCellValue().equals(caseNum)){
                    return false;
                }
            }
            
        } catch (Exception ex) {
            Logger.getLogger(Judge.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    return true;
    }
    
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // returns arrayList of cases of the judges cases in which he have for today
    public ArrayList<String> JudgesTodaysCases(String id){
        ArrayList<String> today = new ArrayList<>();
        try {
            String CurrentDate = getDate();
            FileInputStream fs;
            HSSFWorkbook wb;
            Cell cell;
            Cell cell1;
            Cell cell2;
            Cell cell3;
            Row row;
            
            fs = new FileInputStream(new File("Court.xls"));
            wb = new HSSFWorkbook(fs);
            Iterator<Row> ri = wb.getSheetAt(0).rowIterator();
            
            int caseCounter = 0;
            while(ri.hasNext()){
                row = ri.next();
                cell = row.getCell(1);
                cell1 = row.getCell(8);
                cell3 = row.getCell(6);
                if(cell.getStringCellValue().equals(id) && cell1.getStringCellValue().equals(CurrentDate) && cell3.getNumericCellValue()==1){
                    cell2 = row.getCell(0);
                    today.add(cell2.getStringCellValue());
                    System.out.println(cell2.getStringCellValue() + "  Date : " + cell1.getStringCellValue());
                    caseCounter += 1;
                }
                
            }
            
            if(caseCounter==0){
                System.out.println("\r\n" + "<< There is no case for today >>");
                System.out.println("\r\n| ******************************************************************** |\r\n");
            }
            
            FileOutputStream fileOut = new FileOutputStream("Court.xls");
            wb.write(fileOut);
            
            fs.close();
            wb.close();
            fileOut.close();
            
            } catch (Exception ex) {
            Logger.getLogger(Judge.class.getName()).log(Level.SEVERE, null, ex);
        }
    return today;
    
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //to get the current date from the computer date information with the format | YYYY/MM/DD |
    public String getDate(){
        String date = "";
        
                Date d = new Date();
                String yearFormat = "YYYY";
                String monthFormat = "MM";
                String dayFormat = "DD";
                DateFormat year = new SimpleDateFormat(yearFormat);
                DateFormat month = new SimpleDateFormat(monthFormat);
                DateFormat day = new SimpleDateFormat(dayFormat);
                int YYYY = Integer.parseInt(year.format(d));
                int MM= Integer.parseInt(month.format(d));
                int DD= Integer.parseInt(day.format(d));
                
                date = YYYY + "/" + MM + "/" + DD;   
                
        return date;
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Password changing form for the judge
    public void judgeChangePassword(String pwd, String id){
        while(true){
        System.out.println("\r\n| ******************************************************************** |\r\n");
        System.out.print(">> Enter your old password : ");
        String oldPwd = in.next();
        //checking if the oldpassword entered and the one in the database matchs. 
        if(pwd.equals(oldPwd)){
            while(true){
                try {
                    System.out.print("Enter your new password : ");
                    String newPwd = in.next();
                    System.out.print("Confirm the password : ");
                    String confirmPwd = in.next();

                    if(newPwd.equals(confirmPwd)){
                        InputStream fs;

                        Row row;
                        Cell cell;
                        Cell cell2;
                        fs = new FileInputStream("Court.xls");
                        HSSFWorkbook wb = new HSSFWorkbook(fs);

                        Iterator<Row> ri = wb.getSheetAt(2).rowIterator();

                        while(ri.hasNext()){
                            row = ri.next();
                            cell = row.getCell(0);
                            cell2 = row.getCell(1);
                            if(cell.getStringCellValue().equals(id)){
                                cell2.setCellValue(newPwd);
                            }

                        }

                        FileOutputStream fileOut = new FileOutputStream("Court.xls");
                        wb.write(fileOut);

                        fs.close();
                        wb.close();
                        fileOut.close();


                        pwd = newPwd;
                        System.out.println("\r\n|>> You have changed your password successfully!!");
                        System.out.println("\r\n| ******************************************************************** |\r\n");
                        break;
                    }
                    else{
                        System.out.println(">>The password doesnot match, Try again!!");
                    }
                } catch (Exception ex) {
                    Logger.getLogger(Judge.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        else{
            System.out.print("Do you want to try again!  1. YES     2. NO");
            if(in.nextInt()!=1){
                break;
            }
        }
        
        }
        
    }
 
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Generate the judge id
    public String[] judgeIdGenerate(){
        String[] store = new String[2];
       String idStore="";
        try {
           // to generate new id number for the new judge 
           InputStream fs;
           
           Row row;
           Cell cell;
           fs = new FileInputStream("Court.xls");
           HSSFWorkbook wb = new HSSFWorkbook(fs);
           
           int last = wb.getSheetAt(2).getLastRowNum()+1;
           
           idStore = "JU" + last;
           
           store[0] = idStore;
           store[1] = ""+last; 
           
           fs.close();
           wb.close();
       } catch (Exception ex) {
           Logger.getLogger(DataHandle.class.getName()).log(Level.SEVERE, null, ex);
       }
    return store;
    }
    
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Generate case id generator
    public String[] caseIdGenerate(){
        String[] store = new String[2];
        String CaseNum = "";
       try {
           //Generating new id number for the new case
           InputStream fs;
           
           Row row;
           Cell cell;
           fs = new FileInputStream("Court.xls");
           HSSFWorkbook wb = new HSSFWorkbook(fs);
           
           int last=wb.getSheetAt(0).getLastRowNum() + 1;
           CaseNum = "CS" + last;
           
           store[0] = CaseNum;
           store[1] = ""+last; 
           
           fs.close();
           wb.close();
       } catch (Exception ex) {
           Logger.getLogger(DataHandle.class.getName()).log(Level.SEVERE, null, ex);
       }
       
       return store;
    }
    
       
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Adding the waiting case to the open status
    public void addWaitingToOpen(String JudgeID, String CaseID){
    
        try {
           InputStream fs;
           
           Row row;
           Cell cell0;
           Cell cell;
           Cell cell2;
           fs = new FileInputStream("Court.xls");
           HSSFWorkbook wb = new HSSFWorkbook(fs);
           
           Iterator<Row> ri = wb.getSheetAt(0).rowIterator();
           Iterator<Row> rJudge = wb.getSheetAt(2).rowIterator();
           
           while(ri.hasNext()){
               row = ri.next();
               cell0 = row.getCell(0);
               cell = row.getCell(1);
               cell2 = row.getCell(6);
               if(cell0.getStringCellValue().equals(CaseID)){
                   cell.setCellValue(JudgeID);
                   cell2.setCellValue(1);
                   break;
               }
               
           }
           
           while(rJudge.hasNext()){
               row = rJudge.next();
               cell0 = row.getCell(0);
               cell = row.getCell(2);
               if(cell0.getStringCellValue().equals(JudgeID)){
                   cell.setCellValue(cell.getNumericCellValue()+1);
                   break;
               }
           }
           
           wb.write(new FileOutputStream("Court.xls"));
           wb.close();
       } catch (Exception ex) {
           Logger.getLogger(DataHandle.class.getName()).log(Level.SEVERE, null, ex);
       }    
        
    
    }
  
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Prints out the cases for all users who have a case number
    public void allView(String caseNum){
    
        try {

                InputStream fs;

                Row row;
                Cell cell;
                fs = new FileInputStream("Court.xls");
                HSSFWorkbook wb = new HSSFWorkbook(fs);

                Iterator<Row> ri = wb.getSheetAt(0).rowIterator();

                int ctr = 0;
                while(ri.hasNext()){
                    row = ri.next();
                    cell = row.getCell(0);
                    if(cell.getStringCellValue().equals(caseNum)){
                        System.out.println(">>>  The case Title is : " + row.getCell(4).getStringCellValue() + "\r\n" + ">>> The case court date is : " + row.getCell(8).getStringCellValue());
                        ctr += 1;
                        break;
                    }

                }
                
                if(ctr==0){
                    System.out.println("<< ----------- Wrong Case number!! Try again! ----------- >>");
                }

                fs.close();
                wb.close();
           
        } catch (Exception ex) {
            System.out.println("View error --> " + ex.getMessage());
        }
    
        System.out.println("\r\n<<< ------------------------------------------------------ >>>");
        
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Checking if the case exists before asking for printing 
    public boolean checkCaseExists(String caseNum){
        try {
            
            FileInputStream fs;
            HSSFWorkbook wb;
            Cell cell;
            Row row;
            
            fs = new FileInputStream(new File("Court.xls"));
            wb = new HSSFWorkbook(fs);
            Iterator<Row> ri = wb.getSheetAt(0).rowIterator();
            
            while(ri.hasNext()){
                row = ri.next();
                cell = row.getCell(0);
                if(cell.getStringCellValue().equals(caseNum)){
                    return true;
                }
            }
            
        } catch (Exception ex) {
            Logger.getLogger(Judge.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    return false;
    
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Checking a case for appeal, using the acused and plaintiff id and the casenumber
    public boolean checkIfCaseForAppeal(String idNum, String caseNum , int columnNum){
    
        try {
            
            FileInputStream fs;
            HSSFWorkbook wb;
            Cell cell;
            Cell cell2;
            Row row;
            
            fs = new FileInputStream(new File("Court.xls"));
            wb = new HSSFWorkbook(fs);
            Iterator<Row> ri = wb.getSheetAt(0).rowIterator();
            
            while(ri.hasNext()){
                row = ri.next();
                cell = row.getCell(0);
                cell2 = row.getCell(columnNum);
                if(cell.getStringCellValue().equals(caseNum)){
                    if(cell2.getStringCellValue().equals(idNum)){
                        return true;
                    }
                }
            }
            
        } catch (Exception ex) {
            Logger.getLogger(Judge.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Listing the cases can be appealed for a specific user
    public void CasesForAppeal(String idNum, int columnNum){
    
        try {
            
            FileInputStream fs;
            HSSFWorkbook wb;
            Cell cell;
            Cell cell2;
            Row row;
            
            fs = new FileInputStream(new File("Court.xls"));
            wb = new HSSFWorkbook(fs);
            Iterator<Row> ri = wb.getSheetAt(0).rowIterator();
            
            while(ri.hasNext()){
                row = ri.next();
                cell = row.getCell(0);
                cell2 = row.getCell(columnNum);
                
                if(cell2.getStringCellValue().equals(idNum)){
                    System.out.println("Case Number -> " + cell.getStringCellValue());
                }
                
            }
            
        } catch (Exception ex) {
            Logger.getLogger(Judge.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Gennerating the appeal for in its order by returning the arrayList of strings( The basic case discriptions )
    public ArrayList<String> appealFormGenerator(String caseNum){
        ArrayList<String> appealForm = new ArrayList<>();
    
        try {
            
            FileInputStream fs;
            HSSFWorkbook wb;
            Cell cell;
            Cell cell2;
            Cell cell3;
            Cell cell4;
            Cell cell5;
            String juID="";
            String plID="";
            String acID="";
            Row row;
            
            fs = new FileInputStream(new File("Court.xls"));
            wb = new HSSFWorkbook(fs);
            Iterator<Row> rCase = wb.getSheetAt(0).rowIterator();
            Iterator<Row> rJudge = wb.getSheetAt(2).rowIterator();
            Iterator<Row> rPlaintiff = wb.getSheetAt(3).rowIterator();
            Iterator<Row> rAcused = wb.getSheetAt(4).rowIterator();
            Iterator<Row> rWitness = wb.getSheetAt(5).rowIterator();
                    
            appealForm.add("Court Name : " + wb.getSheetAt(1).getRow(1).getCell(2).getStringCellValue());
            
            while(rCase.hasNext()){
                row = rCase.next();
                cell = row.getCell(0);
                cell2 = row.getCell(8);
                cell3 = row.getCell(4);
                cell4 = row.getCell(5);
                cell5 = row.getCell(9);
                if(cell.getStringCellValue().equals(caseNum)){
                    juID = row.getCell(1).getStringCellValue();
                    plID = row.getCell(2).getStringCellValue();
                    acID = row.getCell(3).getStringCellValue();        
                    appealForm.add( "Decisison Date : " + cell2.getStringCellValue());
                    appealForm.add( "Case Title : " + cell3.getStringCellValue());
                    appealForm.add( "Case Description : " + cell4.getStringCellValue());
                    appealForm.add( "Final Decision : " + cell5.getStringCellValue());
                    break;
                    
                }
                
            }
            
            //Iterate on the judge table
            while(rJudge.hasNext()){
                row = rJudge.next();
                if(row.getCell(0).getStringCellValue().equals(juID)){
                    appealForm.add( "Judge Name : " + row.getCell(4).getStringCellValue());
                }  
            }
            
            while(rPlaintiff.hasNext()){
                row = rPlaintiff.next();
                if(row.getCell(0).getStringCellValue().equals(plID)){
                    appealForm.add( "Plaintiff Name : " + row.getCell(2).getStringCellValue());
                }  
            }
            
            while(rAcused.hasNext()){
                row = rAcused.next();
                if(row.getCell(0).getStringCellValue().equals(acID)){
                    appealForm.add( "Acused Name : " + row.getCell(2).getStringCellValue());
                }  
            }
            
            while(rWitness.hasNext()){
                row = rWitness.next();
                if(row.getCell(0).getStringCellValue().equals(caseNum)){
                    appealForm.add( "Witness Document Description : " + row.getCell(1).getStringCellValue());
                }  
            }
            
            
        } catch (Exception ex) {
            Logger.getLogger(Judge.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return appealForm;
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Writing the appeal form generated above to a .TXT file 
    public void appealWriter(ArrayList<String> appealForm, String caseNum){
        
        try {
            FileOutputStream fout = new FileOutputStream( caseNum + ".txt");
            
            for(String element:appealForm){
                byte b[] = (element + "\r\n").getBytes();
                fout.write(b);
            }
            

            fout.close();
            
            //printing on the console to check if the process is working or not
            System.out.println("Success!!");


        } catch (java.io.IOException e) {
            // TODO Auto-generated catch block
            System.out.println("The Error " + e.getMessage());
        }
    
    }
    
    
    ///////////////////////////////////////////////////////////////////////////////////////// Acused and Plaintiff ///////////////////////////////////////////
    
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Acused password change form 
        public void AcusedChangePassword(String pwd, String id){
        
        
        System.out.print(">> Enter your old password : ");
        String oldPwd = in.next();
        if(pwd.equals(oldPwd)){
            while(true){
                try {
                    System.out.print("Enter your new password : ");
                    String newPwd = in.next();
                    System.out.print("Confirm the password : ");
                    String confirmPwd = in.next();

                    if(newPwd.equals(confirmPwd)){
                        InputStream fs;

                        Row row;
                        Cell cell;
                        Cell cell2;
                        fs = new FileInputStream("Court.xls");
                        HSSFWorkbook wb = new HSSFWorkbook(fs);

                        Iterator<Row> ri = wb.getSheetAt(3).rowIterator();

                        while(ri.hasNext()){
                            row = ri.next();
                            cell = row.getCell(0);
                            cell2 = row.getCell(1);
                            if(cell.getStringCellValue().equals(id)){
                                cell2.setCellValue(newPwd);
                            }

                        }

                        FileOutputStream fileOut = new FileOutputStream("Court.xls");
                        wb.write(fileOut);

                        fs.close();
                        wb.close();
                        fileOut.close();


                        pwd = newPwd;
                        System.out.println(">> You have changed your password successfully!!" + "\r\n" + " <<< -------------------------------------------------------- >>>");
                        break;
                    }
                    else{
                        System.out.println(">>The password doesnot match, Try again!!");
                    }
                } catch (Exception ex) {
                    Logger.getLogger(Judge.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
    }
        
        
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Plaintiff password change form
    public void PlaintiffChangePassword(String pwd, String id){
        
        
        System.out.print(">> Enter your old password : ");
        String oldPwd = in.next();
        if(pwd.equals(oldPwd)){
            while(true){
                try {
                    System.out.print("Enter your new password : ");
                    String newPwd = in.next();
                    System.out.print("Confirm the password : ");
                    String confirmPwd = in.next();

                    if(newPwd.equals(confirmPwd)){
                        InputStream fs;

                        Row row;
                        Cell cell;
                        Cell cell2;
                        fs = new FileInputStream("Court.xls");
                        HSSFWorkbook wb = new HSSFWorkbook(fs);

                        Iterator<Row> ri = wb.getSheetAt(3).rowIterator();

                        while(ri.hasNext()){
                            row = ri.next();
                            cell = row.getCell(0);
                            cell2 = row.getCell(1);
                            if(cell.getStringCellValue().equals(id)){
                                cell2.setCellValue(newPwd);
                            }

                        }

                        FileOutputStream fileOut = new FileOutputStream("Court.xls");
                        wb.write(fileOut);

                        fs.close();
                        wb.close();
                        fileOut.close();


                        pwd = newPwd;
                        System.out.println(">> You have changed your password successfully!!" + "\r\n" + " <<< -------------------------------------------------------- >>>");
                        break;
                    }
                    else{
                        System.out.println(">>The password doesnot match, Try again!!");
                    }
                } catch (Exception ex) {
                    Logger.getLogger(Judge.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
    }

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Prints out the cases for Admin and Judge users who have a case number
    public void allViewForAll(String caseNum, int access){
        
        System.out.print(">> Enter the Case Number : ");
        caseNum = in.next();
        
        System.out.println("| ************************************************** |\r\n");
    
        try {

                InputStream fs;

                Row row;
                Cell cell;
                fs = new FileInputStream("Court.xls");
                HSSFWorkbook wb = new HSSFWorkbook(fs);

                Iterator<Row> ri = wb.getSheetAt(0).rowIterator();

                int ctr = 0;
                while(ri.hasNext()){
                    row = ri.next();
                    cell = row.getCell(0);
                    if(cell.getStringCellValue().equals(caseNum) && access==1){
                        System.out.println(">>>  The case Title is : " + row.getCell(4).getStringCellValue() + "\r\n"
                                + ">>> Court date is : " + row.getCell(8).getStringCellValue() + "\r\n" + ">>> Judge ID : " + row.getCell(1).getStringCellValue() + 
                                "\r\n" + ">>> Plaintiff ID : " + row.getCell(2).getStringCellValue() + "\r\n" + ">>> Acused ID : " + row.getCell(3).getStringCellValue()
                                ); 
                        ctr += 1;
                        break;
                    }
                    else if(cell.getStringCellValue().equals(caseNum) && access==2){
                        System.out.println(">>>  The case Title is : " + row.getCell(4).getStringCellValue() + "\r\n"
                                + ">>> Court date is : " + row.getCell(8).getStringCellValue() + "\r\n" + ">>> Plaintiff ID : "
                                + row.getCell(2).getStringCellValue() + "\r\n" + ">>> Acused ID : " + row.getCell(3).getStringCellValue() + 
                                "\r\n" + ">>> Case Description : " + row.getCell(5).getStringCellValue() + "\r\n" + ">>> Final Decision : " 
                                + row.getCell(9).getStringCellValue());
                        ctr += 1;
                        break;
                    }

                }
                
                if(ctr==0){
                    System.out.println("<< ----------- Wrong Case number!! Try again! ----------- >>");
                }

                fs.close();
                wb.close();
           
        } catch (Exception ex) {
            System.out.println("View error --> " + ex.getMessage());
        }
    
        System.out.println("\r\n<<< ------------------------------------------------------ >>>");
        
    }
    
}
