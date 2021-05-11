package courtcase;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Judge {

    private String id;
    private String pwd;
    Scanner in;
    private ArrayList<String> currentDateCases;
    private ArrayList<String> allCasesOfTheJudge;
    
    DataHandle dataHandle = new DataHandle();
    
    public Judge(String id, String pwd) {
        this.id = id;
        this.pwd = pwd;
        this.allCasesOfTheJudge = allCasesOfTheJudge(0);
        System.out.println("\r\n"+"<< --- Wellcome Judge --- >> " + id + "\r\n");
        in = new Scanner(System.in);
        menu();
        
    }
   
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
    // Displaying the menu of the Judge and accepts for Choice from the list to call other methods 
    private void menu(){
        Scanner inn = new Scanner(System.in);
        while(true){
        System.out.println("<<-----  Judge Menu ----->>" + "\r\n");
        System.out.println("1. Update a case\r\n2. View All Cases\r\n3. View Today's Cases\r\n4. Change Password\r\n5. Add Witness for a case\r\n6. View Cases Information\r\n7. Exit");
        System.out.print(">> Type here : ");
        int choose = inn.nextInt();
        if(choose==1){
            caseUpdate();
        }
        else if(choose==2){
            //View all cases
            allCasesOfTheJudge(1);
        }
        else if(choose==3){
            this.currentDateCases = todaysCases();
        }
        else if(choose==4){
            changePassword();
        }
        else if(choose==5){
            addWitnessDocument();
        }
        else if(choose==6){
            dataHandle.allViewForAll(id,2);
        }
        else if(choose==7){
            break;
        }
        else{
            System.out.println("Try again!!");
        }
        
        }
    
    }
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // The judge case update menu
    // 1. Extend the date of the case 
    // 2. Close a case by addint the description and the final decision 
    public void caseUpdate() {
        Scanner inn = new Scanner(System.in);
        try {
            while(true){
                System.out.println("\r\n| ***************************** Case Update Menu ************************************* |\r\n");
                System.out.println("1. Extend case date  |  2. Set case description and final decision   |  3. Exit");
                int choice = inn.nextInt();
                if(choice==1){
                    //extend
                    System.out.print(">> Enter the case number to extend the date : ");
                    String tempCaseNum = in.next();
                    
                    //Updating new case date 
                    if(checkInTheList(allCasesOfTheJudge, tempCaseNum)){      
                        while(true){
                            System.out.println(">> Enter the new date : ");
                            System.out.print(">> Year : ");
                            int yr = inn.nextInt();
                            System.out.print(">> Month : ");
                            int mn = inn.nextInt();
                            System.out.print(">> Date : ");
                            int dy = inn.nextInt();


                            if(!dataHandle.createDate(yr, mn, dy).equals("")){

                                dataHandle.AddDate(yr,mn,dy,tempCaseNum, this.id);
                                
                                break;
                            }
                            else{
                                System.out.println(">> Try again!!");
                            }
                        }
                    }
                    
                    break;
                }
                else if(choice==2){
                    
                    this.currentDateCases = todaysCases();
                    
                    //update the case information - Description - Case decision - Change the state to closed
                    System.out.print(">> Enter the case number to update information : ");
                    String tempCaseNum = inn.next();     
                    
                    if(checkInTheList(currentDateCases, tempCaseNum)){
                        inn.nextLine();
                        System.out.print(">> Enter the Description of the case : ");
                        String caseDisc = inn.nextLine();
                        //inn.nextLine();
                        System.out.print(">> Enter the Decision of the case");
                        String caseDecision = inn.nextLine();
                        System.out.println("");
                                
                        dataHandle.judgeDecisionToDatabase(this.id ,tempCaseNum, caseDisc, caseDecision);

                    }
                    
                    break;
                }
                else if(choice==3){
                    //exit
                    break;
                }
                else{
                    System.out.println(">> Please enter a valid input ");
                }
            
            }
            
            System.out.println("\r\n| ******************************************************************** |\r\n");
            
        } catch (Exception ex) {
            Logger.getLogger(Judge.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // checking the case number exists in the caselist 
    // it accepts an ArrayList of cases and a string of the caseNumber to be Checked
    // it return "True" if the CaseNumber exists and "False" if it doesnot exist
    
    private boolean checkInTheList(ArrayList<String> caseList, String CaseNumber){
        for(String tempCaseNum:caseList){ 
            if(tempCaseNum.equals(CaseNumber)){
                return true;
            }
        }
        return false;
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Adding the witness description document. 
    
    private void addWitnessDocument(){
    
        ArrayList<String> allCases = allCasesOfTheJudge(1);
        
        //Adding witness to the witness table at the Court database.........
        while(true){
        System.out.print("Enter the case number : ");
        String caseNum = in.next();
        int trueCounter = 0;
        for(String CaseForJudge:allCases){
            if(CaseForJudge.equals(caseNum)){
                trueCounter = 1;
                break;
            }
        }
        if(trueCounter==1){
            if(dataHandle.checkForWitness(caseNum)){
                Scanner input = new Scanner(System.in);
                while(true){
                    System.out.print("Write the witness document description here >> ");
                    String witnessDocument = input.nextLine();
                    System.out.print("Are you sure :       1. YES       2. NO");
                    int sure = input.nextInt();
                    if(sure==1){
                        // Add witness description to the database
                        dataHandle.addWitnessDocument(witnessDocument,caseNum);
                        break;
                    }
                    else if(sure==2){
                        break;
                    }
                    else{
                        input.nextLine();
                        System.out.println("| ****************** Invalid Input, Try Again!! ********************* |");
                    }
                }
            }
            break;
        }
        else{
            System.out.println(">> The case is not in the list, Try Again!! ");
        }
        
        }
        
        
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////    
    //Returns all cases dealt by the judge in the ArrayList form
    private ArrayList<String> allCasesOfTheJudge(int printPreference){
        ArrayList<String> allCases;
        allCases = new ArrayList<>();
        
        // asking the dataHandle database checker method to return us the cases list using the Judge id
        allCases = dataHandle.AllCasesOfTheJudge(printPreference, this.id);
        
        return allCases;
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Returns the cases can be updated on the current date
    public ArrayList<String> todaysCases(){
        ArrayList<String> today;
        today = new ArrayList<>();
        
        today = dataHandle.JudgesTodaysCases(this.id);
        
       return today;
    
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////    
    // Password changing form 
    public void changePassword(){
        
        dataHandle.judgeChangePassword(this.pwd, this.id);
 
    }
    
    
}
