package Dev_J_120;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Loader {
    
    public static List<String> loadScript(){
        List<String> scriptList = new ArrayList<>();
        System.out.println("You must specify the path to the file containing the script.");
        try{     
            String pathname = new Scanner(System.in, "cp1251").nextLine();
            try (Scanner scanScript = new Scanner(new File(pathname))) {
                while(scanScript.hasNextLine()){
                    String currentRow = scanScript.nextLine();
                    if(!currentRow.trim().startsWith("#") && !currentRow.trim().isEmpty())
                        scriptList.add(currentRow); }
                }
            } 
       catch (IOException ex) {
             System.out.println("An error occurred while loading the data. "
                     + "The application will be stopped."); 
             System.exit(0);}
        return scriptList;
    }
    public static Set<String> loadOperators(){
        
        Set<String> scriptOperators = new HashSet<>();
        try {           
            String operators = ScriptProperties.get().getProperty("operators");
            try (Scanner scanOperators = new Scanner(operators).useDelimiter(",")) {
                while(scanOperators.hasNext()){
                    scriptOperators.add(scanOperators.next());}
                }            
            } 
        catch (IOException ex) {
              System.out.println("Не удалось загрузить список доступных операторов.");}
        return scriptOperators;
    }
}
