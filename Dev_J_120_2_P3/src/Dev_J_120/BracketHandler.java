
package Dev_J_120;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BracketHandler {
    
    private  Map<String, Double> mapVar = new HashMap<>();

    public BracketHandler(Map<String, Double> mapVar) {
        this.mapVar = mapVar;
    }   
    public String bracketParser(String value)throws IllegalArgumentException {
        Calculator calculator = new Calculator(mapVar);
        String patternBracket = "(?:\\()([^\\(\\)]+)(?:\\))";
        Pattern pattern = Pattern.compile(patternBracket);
        Matcher matcher = pattern.matcher(value);
        while(matcher.find()) {
            StringBuilder sb = new StringBuilder(value);              
            String bracketsСontent = matcher.group(1).trim();
            if(bracketsСontent.contains("^"))
               bracketsСontent = calculator.powCalc(bracketsСontent);
            if(bracketsСontent.contains("*") || bracketsСontent.contains("/"))
               bracketsСontent = calculator.multAndDiv(bracketsСontent);            
            double result = calculator.plusAndMinus(bracketsСontent);            
            sb = sb.delete(matcher.start(1) - 1, matcher.end(1) + 1);
            value = sb.insert(matcher.start(1) - 1, String.valueOf(result)).toString(); 
            matcher = pattern.matcher(value);
            }
        return value;
    }
}
/*
script.txt
*/