package courtcase;

import java.util.ArrayList;
import java.util.Scanner;


public class Plaintiff{

    DataHandle dataHandle = new DataHandle();
    String id;
    String pwd;
    Scanner in;
    public Plaintiff(String id, String pwd) {
        
        this.id = id;
        this.pwd = pwd;
        in = new Scanner(System.in);
        menu();
        
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Displaying the menu of the Plaintiff and accepts for Choice from the list to call other methods     
    private void menu(){
        
        while(true){
            System.out.println("<<-----  Plaintiff Menu ----->>" + "\r\n");
            System.out.println("1. Appeal to my case\r\n2. Change Password\r\n3. Exit");
            int choose = in.nextInt();
            if(choose==1){
                //appeal form
                appeal();
            }
            else if(choose==2){
                changePassword();
            }
            else if(choose==3){
                break;
            }
            else{
                System.out.println(">> Please enter the correct number from the menu" + "\r\n");
            }
        }
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // The Plaintiff form to generate the appeal .TXT file of the appeal document usint the Plaintiff id and The caseNumber
    // This will also check if the case is CLOSED and if it is the case of this specific Plaintiff person
    public void appeal(){
        //Appeal
        ArrayList<String> appealForm = new ArrayList<>();
        
        System.out.println("| ********  Cases you are involved in   ********* |");
        dataHandle.CasesForAppeal(this.id,2);
        
        System.out.print("\r\nEnter the case number you want to appeal >> ");
        String caseNum = in.next();
        
        if(dataHandle.checkIfCaseForAppeal(this.id,caseNum,2)){
        
            ////
            appealForm = dataHandle.appealFormGenerator(caseNum);
            
            ////
            dataHandle.appealWriter(appealForm, caseNum); 
            
        }
        ArrayList<String> appealTextList = new  ArrayList<>();
        
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////    
    // Password Changing method, calls for AcusedChangePassword method from the dataHandle to change it in the database    
    public void changePassword(){
        
        dataHandle.PlaintiffChangePassword(this.pwd, this.id);
        
    }

    
}
