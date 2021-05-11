package courtcase;

import java.util.ArrayList;
import java.util.Scanner;

public class Acused {

    DataHandle dataHandle = new DataHandle();
    String id;
    String pwd;
    Scanner in;
    public Acused(String id, String pwd) {
        
        this.id = id;
        this.pwd = pwd;
        in = new Scanner(System.in);
        menu();
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Displaying the menu of the Acused and accepts for Choice from the list to call other methods 
    private void menu(){
        while(true){
            System.out.println("<<-----  Acused Menu ----->>" + "\r\n");
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
    // The Appeal form to generate the appeal .TXT file of the appeal document usint the Acused id and The caseNumber
    // This will also check if the case is CLOSED and if it is the case of this specific Acused person
    public void appeal(){
        //Appeal
        ArrayList<String> appealForm = new ArrayList<>();
        
        System.out.println("| *********************  Cases you are involved in   ******************** |");
        dataHandle.CasesForAppeal(this.id,3);
        
        System.out.print("\r\nEnter the case number you want to appeal >> ");
        String caseNum = in.next();
        
        if(dataHandle.checkIfCaseForAppeal(this.id,caseNum,3)){
        
            ////
            appealForm = dataHandle.appealFormGenerator(caseNum);
            
            ////
            dataHandle.appealWriter(appealForm, caseNum); 
            
        }
        
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////    
    // Password Changing method, calls for AcusedChangePassword method from the dataHandle to change it in the database
    public void changePassword(){
              
        dataHandle.AcusedChangePassword(this.pwd,this.id);
        
    }
    
    
}
