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
        System.out.println("Укажите файл (путь к файлу), содержащий срипт.");
        try{     
            String pathname = new Scanner(System.in, "cp1251").nextLine();
            System.out.println("------------------Script-------------------");
            try (Scanner scanScript = new Scanner(new File(pathname))) {
                while(scanScript.hasNextLine()){
                    String currentRow = scanScript.nextLine();
                    System.out.println(currentRow);
                    if(!currentRow.trim().startsWith("#") && !currentRow.trim().isEmpty())
                        scriptList.add(currentRow); }
                System.out.println("------------------End of Script-------------------");
                }
            } 
       catch (IOException ex) {
             System.out.println("Произошла ошибка при загрузке данных. "
                     + "Приложение будет остановлено."); 
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
