package courtcase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;


public class View_Types {

    int check;
    String caseNum;
    public View_Types(String caseNum, int check) {
        
        this.check = check;
        this.caseNum = caseNum;
        
        if(this.check==1){
            adminView();
        }
        else if(this.check==2){
            judgeView();
        }
        else if(this.check==3){
            oppenentsView();
        }
        else if(this.check==4){
            allView();
        }
        else{
            System.out.println("There is no such view type");
        }
    }
    
    private void adminView(){
    
        System.out.println("The admin display");
    
    }
    
    private void judgeView(){
    
        System.out.println("The judge view");
        
    }
    
    private void oppenentsView(){
    
        System.out.println("The opponents view");
        
    }
    
    private void allView(){
    
        
        try {
                System.out.println("<<< ---- Any person with case number can see this ---- >>>\r\n");

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
    
}
