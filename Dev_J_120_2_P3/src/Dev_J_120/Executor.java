package Dev_J_120;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Executor {
    
    private Set<String> scriptOperators = new HashSet<>();
    private List<String> scriptList = new ArrayList<>();
    private final  Map<String, Integer> mapVar = new HashMap<>();

    public Executor() {
        this.scriptList = Loader.loadScript();
        this.scriptOperators = Loader.loadOperators();       
    }    
    public void execute(){
    
         for(int i=0; i<scriptList.size(); i++) {
             String currentRow = scriptList.get(i);
             String begin = currentRow.split(" ", 2)[0];
             if(!scriptOperators.contains(begin)){
                 System.out.println("The script contains an unknown operator "
                     + "and cannot be executed. The application will be stopped.");
                 System.exit(0); }
             currentRow = currentRow.split(" ", 2)[1].trim();    
             switch(begin) {
                 case ("print"):
                    toPrint(currentRow); 
                 break;
                 case ("set"): 
                     toSet(currentRow);
                 break;                   
              }              
           }
    }
    public void toPrint(String currentRow){
        
        boolean quote = false;
        StringBuilder sb = new StringBuilder();
        char[] chars = currentRow.toCharArray();
        int iMax = chars.length - 1;
        int j = 0;
        for(int i = 0; i <= iMax; i++){                     
            switch(chars[i]){
                case('$'):
                    i++;
                    StringBuilder varName = new StringBuilder();
                    while(chars[i] != ',' && chars[i] != ' '){
                          varName.append(chars[i]);
                          if(i < iMax)
                             i++;
                          else break;
                        }
                    sb.append(mapVar.get(varName.toString()).toString());
                    if(i < iMax)
                       i++;
                       j = i;
                    break;
                case('"'):
                    if(!quote){
                       i++;
                       quote = true;}
                    else if(quote && i<iMax){
                       quote = false;
                        i = i+2;
                        j=i;}                   
                    break;            
                } 
            if(i < iMax && i!=j)
               sb.append(chars[i]);                                                           
        }
            System.out.println(sb);          
    }
    
    public void toSet(String currentRow){
        
        if(currentRow.matches("^\\$[^ ]+ = [^ ].+")) {
           String varName = currentRow.split(" = ")[0];
           String value = currentRow.split(" = ")[1];
           if(varName.startsWith("$") && value.matches("\\d+")){
              varName = varName.replace("$", "");
              if(mapVar.containsKey(varName))
                 mapVar.replace(varName, Integer.parseInt(value));               
              else
                 mapVar.put(varName, Integer.parseInt(value));
             }
           else if(varName.startsWith("$") && value.contains("$")){
                   boolean plus = false;
                   boolean minus = false;
                   int resultValue = 0;
                   Scanner scanner = new Scanner(value);
                   while(scanner.hasNext()){
                        String part = scanner.next().trim();
                        switch(part.charAt(0)){
                            case('$'):
                                part = part.replace("$", "");
                                if(plus == false && minus == false)
                                   resultValue = mapVar.get(part); 
                                if(plus)
                                   resultValue = resultValue + mapVar.get(part);
                                if(minus)
                                   resultValue = resultValue - mapVar.get(part);   
                                plus = false;
                                minus = false;      
                                break;
                            case('+'):
                                plus = true;
                                break;
                            case('-'):
                                minus = true;
                                break; }
                        if(part.matches("\\d+")) {
                            if(plus == false && minus == false)
                                   resultValue = Integer.parseInt(part);
                                if(plus)
                                   resultValue = resultValue + Integer.parseInt(part);
                                if(minus)
                                   resultValue = resultValue - Integer.parseInt(part);
                            plus = false;
                            minus = false;
                            }
                        }
                    scanner.close();
                    varName = varName.replace("$", "");
                    if(mapVar.containsKey(varName))
                       mapVar.replace(varName, resultValue);               
                    else
                       mapVar.put(varName, resultValue);
               }
        }
        else {
            System.out.println("The script contains errors and cannot be executed. "
                    + "The application will be stopped.");
            System.exit(0); }    
    }
}



/*
script.txt

vari = vari.replace("$", "");
if(!mapVar.containsKey(vari);
mapVar.put(vari, 0);
*/