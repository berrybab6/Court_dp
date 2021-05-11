package courtcase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Admin {

    private String id;
    private String password;
    Scanner in;
    
    private String pwd;
    private String adminId;
    DataHandle dataHundle = new DataHandle();
    public Admin() {
        
        
    }
    
    public void addIDAndPassword(String adminId, String pwd){
        this.pwd = pwd;
        this.adminId = adminId;
    }
    
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////   
    // Displaying the menu of the admin 
    
    public void menu(){
        
        System.out.println("\r\n<< ---  Welcome to Admin Page  --- >>");
        in = new Scanner(System.in);
        while(true){
            try{
                System.out.println("| ******************************************************************** |");
                System.out.println("|>> 1. Add new Case                                                    |");
                System.out.println("|>> 2. Add new Judge                                                   |");
                //System.out.println("|>> 3. Deactivate Judge                                                    |");
                System.out.println("|>> 3. Update Waiting Cases                                            |");
                System.out.println("|>> 4. Change Password                                                 |");
                System.out.println("|>> 5. View Cases                                                 |");
                System.out.println("|>> 6. Exit                                                            |");
                System.out.println("| ******************************************************************** |\r\n");

                System.out.print(" >> Type >> ");
                int val = in.nextInt();

                if(val==1){
                    addNewCase();
                }
                else if(val==2){
                    addJudge();
                }
                else if(val==3){
                    waitingUpdate();
                }
                else if(val==4){
                    changePassword();
                }
                else if(val==5){
                    dataHundle.allViewForAll(id,1);
                }
                else if(val==6){
                    break;
                }
                else{
                    System.out.println(" << --- Please use appropriate input value!! Try again --- >> ");
                }
            
          } catch(InputMismatchException ex){
            System.out.println("| ************************* Please use appropriate input value!! **************************** |");
        }
                
        } 
        
       

    }
    
    public String getId(){
        return id;
    }
    
    public void setId(){
        id = dataHundle.setId();
    }
    
    public String getpwd(){
        return password;
    }

    public void setpwd(String pwd){
        
        dataHundle.setpwd(pwd, password);
        
    }
    
    
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////   
    // Adding judge form 
    // Accepts the judge information and calls for the DataHandle to add it to the database
    // Asks for confirmation to add the judge to the database
    
    private void addJudge(){
        
        try {
            
            System.out.println("\r\n<<<<  ---------------- Adding Judge ----------------  >>>>\r\n");
            System.out.print("Enter His/Her First Name: ");
            String Fname = in.next();
            System.out.print("Enter His/Her Last Name: ");
            String Lname = in.next();  
            String name = Fname + " " + Lname;
            int status = 0;
            String idStore;
            String sex;
            int numberOfCases=0;
            String psword = "0000";
            int state = 1;
            
            while(true){
                System.out.println("<<<<  -----------------  Judge Status  -----------------  >>>> " + "\r\n" + "1. Criminal and Civil case judge " + "\r\n" + "2. Only civil case" + "\r\n" + "3. Quit" );
                System.out.print("Enter The Judge Status : ");
                int st = in.nextInt();
                
                if(st == 1){
                    status = 0;
                    break;
                }
                else if(st==2){
                    status = 1;
                    break;
                }
                else if(st==3){
                    break;
                }
                else{
                    System.out.println(">> Please enter form the given options only!!");
                }
                
            }
            
            String[] store = dataHundle.judgeIdGenerate();
            
            idStore = store[0];
            int last = Integer.parseInt(store[1]);
            
            // choosing for the judge's sex
            while(true){
                System.out.println(">> Choose Sex : " + "\r\n" + "1. Male " + "\r\n" + "2. Female ");
                int val = in.nextInt();
                if(val==1){
                    sex = "Male";
                    break;
                }
                else if(val==2){
                    sex = "Female";
                    break;
                }
                else{
                    System.out.println(">> Try agsin!!");
                }
            }
            
            while(true){
                
                try{
                System.out.println("<<< ***********  Are you sure to add this judge!! *********** >>>" + "\r\n" + "1. YES  ----  |  ----   2. NO");
                int val = in.nextInt();
                if (val==1){
                    // to add the collected information to the judge database
                    addJudgeDatabase(idStore,psword,numberOfCases,status,name,sex,last,state);
                    break;
                }
                else if(val==2){
                    break;
                }
                } catch(Exception e){
                    System.out.println(">> Please enter 1 or 2 only! ");
                }
            
            }
            
            System.out.println("\r\n<<< ---------------------------   Report  ------------------------------ >>>>\r\n");
            System.out.println("The Judge , " + name + " is successfuly added to the database!!");
            System.out.println("* "+name + "'s New Id is : " + idStore);
            System.out.println("* "+name + "'s New Password is : " + psword 
                       + "\r\n>> |** Please make sure to change your default PASSWORD as soon as possible!! **|");
            System.out.println("\r\n<<< -------------------------------------------------------------------- >>>\r\n");
            
        } catch (Exception ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
        
   
    }
    
    
    /////////////////////////////////////////////////// addJudgeDatabase(idStore,psword,numberOfCases,status,name,sex,last,state); ////////////////////////////////
    //Adding the new judge to the judge excel table 
    
    private void addJudgeDatabase(String id, String pwd, int numberOfCases, int status, String name, String sex, int r,int state){
        
        dataHundle.addJudgeDatabase(id, pwd, numberOfCases, status, name, sex, r, state);
         
    }
    
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////       
    // The form to add new case to the court 
    // And asks for confirmation to add the data to the database, by using the DataHandle object
    
    private void addNewCase(){
        
        try {
            System.out.println("Adding new Case . . .");
            System.out.println("\r\n" + "<< ----------------------- >>" + "\r\n");
            System.out.print("Enter The case title: ");
            String CaseTitle = in.next(); 
            String caseDisc="";
            int caseStatus = 0;
            String CaseNum = "";
            int caseType = 0;
            String date = "";
            String juId="";
            String plId="";
            String acId="";
            String result = "";
            
            String[] store = dataHundle.caseIdGenerate();
            
            CaseNum = store[0];
            int last = Integer.parseInt(store[1]);
        
            // choosing for the case type 0-Criminal and 1-Civil . . .
            while(true){
                
                System.out.println(">> Choose for the case type : " + "\r\n" + "1. Criminal case " + "\r\n" + "2. Civil Case ");
                System.out.print(">> Type here : ");
                int val = in.nextInt();
                if (val==1){
                    caseType = 0;
                    break;
                }
                else if (val==2){
                    caseType = 1;
                    break;
                }
                else{
                    System.out.println("Try again!!");
                }
            
            }
            
            System.out.println("<< --- Judges list who are available --- >>");
            ArrayList<String> val = availableJudgeList(caseType);
            if (val.size()==0){
                System.out.println(">> There is no judges available >> the case is added to the Waiting List");
                juId = "";
                caseStatus = 0;
            }
            else if(val.size()>0){
                while(true){
                    System.out.print(">> Enter judge id from the above list : ");
                    juId = in.next();

                    int ctr = 0;
                    for(String jID:val){
                        if(jID.equals(juId)){
                            ctr +=1;
                        }
                    }

                    if(ctr==0){
                        System.out.println(">> Wrong Judge Id, Try again!!" + "\r\n");
                    }
                    else{
                        break;
                    }
                }
                
                
                caseStatus = 1;
                
            }
            
            System.out.print(">> Enter the SSN or Office number of the Plaintiff : ");
            String tempPlaintiffSSN = in.next();
            String tempPlid = checkSSNorOfficeNum(tempPlaintiffSSN,3);
            if(tempPlid==""){
                System.out.println(">> The plaintiff does not exist in the database, Please fill the following Plaintiff Registration Form ");
                plId = createPlaintiffORAcused("PL",3,tempPlaintiffSSN);
            }
            else{
                plId = tempPlid;
            }
            
            System.out.print(">> Enter the SSN or Office number of the Acused : ");
            String tempAcusedSSN = in.next();
            String tempAcid = checkSSNorOfficeNum(tempAcusedSSN,4);
            if(tempAcid==""){
                System.out.println(">> The Acused does not exist in the database, Please fill the following Acused Registration Form ");
                acId = createPlaintiffORAcused("AC",4,tempAcusedSSN);
            }
            else{
                acId = tempAcid;
            }
            
            if(caseStatus==0){
                date = "";
            }
            else{
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
                
                //////////////////////////////////////////////////////////////////////////////////////////////////////////////   
                // Accepting the date to add to the case Court date
                
                int b = 0;
                while(b == 0){   
                    System.out.println(">> Enter the Court date of the case ");
                    while(true){
                        System.out.print(">> Year ");
                        int yr = in.nextInt();
                        System.out.print(">> Month ");
                        int mn = in.nextInt();
                        System.out.print(">> Date ");
                        int dy = in.nextInt();
                        
                        if(yr>YYYY && mn<=12 && dy<=30){
                            date += yr + "/" +mn + "/" +dy;
                            b = 1;
                            break;
                        }
                        else if(yr==YYYY && mn>MM && mn<=12 && dy<=30){
                            date += yr + "/" +mn + "/" +dy;
                            b = 1;
                            break;
                        }
                        else if(yr==YYYY && mn==MM && dy>=DD && mn<=12 && dy<=30){
                            date += yr + "/" +mn + "/" +dy;
                            b = 1;
                            break;
                        }
                        else{
                            break;
                        }       
                    }
                     
                }
            }
            
            while(true){
                
                try{
                System.out.println("Are you sure to add this Case : " + "\r\n" + "1. YES         2. NO");
                int confirm = in.nextInt();
                if (confirm==1){
                    ///////////////////////////////////////////// UpdateJudgeCaseNumber(juId) ///////////////////////////////////////////////////////////////////////////////////////////////
                    
                    dataHundle.UpdateJudgeCaseNumber(juId);
                    
                    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    //Adding the information to the case table in Court database
                    
                    caseToDatabase(CaseNum, juId, plId, acId, CaseTitle, caseDisc, caseStatus, caseType, date, result);
                    break;
                }
                else if(confirm==2){
                    break;
                }
                } catch(Exception e){
                    System.out.println(">> Please enter 1 or 2 only! ");
                }
            
            }
            
            
        } catch (Exception e) {
            System.out.println("Adding case error --> " + e.getMessage());
        }
        
    
    }
    
    private void caseToDatabase(String caseNum,String juId, String plId, String acId, String caseTitle, String caseDisc, int caseStatus, int caseType, String date, String result){
    
        dataHundle.caseToDatabase(caseNum, juId, plId, acId, caseTitle, caseDisc, caseStatus, caseType, date, result);

        
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////   
    // creating the Opponents (Plaintiff or Acused) by using the infix("PL"-if it is Plaintiff or "AC"- if it is Acused)
    // and using the Sheet Number to differenciate the Acused and the Plaintiff 
    // and the Social Security Number (SSN) of the palintiff or the acused
    
    private String createPlaintiffORAcused(String idPrefix, int shNum, String ssn){
    
        String id0 = "";
        try {      
            
            String pwd = "0000";
            
            System.out.print("Enter First name : ");
            String Fname = in.next();
            System.out.print("Enter Last name : ");
            String Lname = in.next();
            String name = Fname + " " + Lname;
            
            System.out.print("Enter Age : ");
            int age = in.nextInt();
            
            
            String sex;
            while(true){
                System.out.println(">> Choose Sex : " + "\r\n" + "1. Male " + "\r\n" + "2. Female ");
                int val = in.nextInt();
                if(val==1){
                    sex = "Male";
                    break;
                }
                else if(val==2){
                    sex = "Female";
                    break;
                }
                else{
                    System.out.println(">> Try agsin!!");
                }
            }
            
            ////////////////////////////////////AddingOpponentToDatabase//////////////////////////////////////////////////////
            
            dataHundle.AddingOpponentToDatabase(idPrefix, shNum, id0, pwd, name, sex, age, ssn);
            
            
        } catch (Exception ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return id0;
    }
    
     //////////////////////////////////////////////////////////////////////////////////////////////////////////////   
    // Checking if the Plaintiff or the Acused SSN already find in the database involved in some other Case before 
    private String checkSSNorOfficeNum(String ssn_officeNum, int shNum){
    
        String value = dataHundle.checkSSNorOfficeNum(ssn_officeNum, shNum);
        
        return value;
    }
    
     //////////////////////////////////////////////////////////////////////////////////////////////////////////////     
    //Returns Available judges list by using the category of Criminal and civil from the parameter caseType
    private ArrayList<String> availableJudgeList(int caseType){
        ArrayList<String> availableJudges = dataHundle.availableJudgeList(caseType);
    
       return availableJudges;
    }
    
     ////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
    //Updating waiting list by assigning available judges to the waiting cases and assign new court date
    private void waitingUpdate(){
 
        System.out.println("| ************** Updating the waiting Cases . . . *************** |");
        
        ArrayList<String> allJudgesAvailableCriminal = availableJudgeList(0);
        ArrayList<String> allJudgesAvailableCivil = availableJudgeList(1);
        
        ArrayList<String> allWaitingCaseCriminal = allWaitingCase(0);
        ArrayList<String> allWaitingCaseCivil = allWaitingCase(1);
        
        while(allJudgesAvailableCriminal.size()>0 && allWaitingCaseCriminal.size()>0){
        
           dataHundle.addWaitingToOpen(allJudgesAvailableCriminal.get(0), allWaitingCaseCriminal.get(0));
           allJudgesAvailableCriminal.remove(0);
           allWaitingCaseCriminal.remove(0);
        }
        
        while(allJudgesAvailableCivil.size()>0 && allWaitingCaseCivil.size()>0){
        
            dataHundle.addWaitingToOpen(allJudgesAvailableCivil.get(0), allWaitingCaseCivil.get(0));
            allJudgesAvailableCivil.remove(0);
            allWaitingCaseCivil.remove(0);
            
        }
        
        if(allJudgesAvailableCriminal.size()>0 && allWaitingCaseCivil.size()>0){
            
            while(allJudgesAvailableCriminal.size()>0 && allWaitingCaseCivil.size()>0){
                dataHundle.addWaitingToOpen(allJudgesAvailableCriminal.get(0), allWaitingCaseCivil.get(0));
                allJudgesAvailableCriminal.remove(0);
                allWaitingCaseCivil.remove(0);
            }
        
        }
        
    
    }
    
     //////////////////////////////////////////////////////////////////////////////////////////////////////////////     
    //Returns all waiting cases by category of Criminal and civil case using the parameter needed
    private ArrayList<String> allWaitingCase(int needed){
        
        ArrayList<String> allWaiting = dataHundle.allWaitingCase(needed);
    return allWaiting;
    
    }
    
     //////////////////////////////////////////////////////////////////////////////////////////////////////////////     
    //the form to change the password first by checking it with the old password
    public void changePassword(){    
        
        dataHundle.adminChangePassword(pwd, adminId);
        
        
    }
    
    
}
