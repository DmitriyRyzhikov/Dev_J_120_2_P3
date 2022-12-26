
package Dev_J_120;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {
    
    private  Map<String, Double> mapVar = new HashMap<>();

    public Calculator(Map<String, Double> mapVar) {
        this.mapVar = mapVar;
    }
        
    public double plusAndMinus(String mathExpression){
        String[] summands = mathExpression.split(" ");
        Collection<Double> doubles = new ArrayList<>();
        for (int i=0; i<summands.length; i++) {
            Double preResult = null;
            if(summands[i].startsWith("$")) 
               preResult = mapVar.get(summands[i].replace("$", "").trim());            
            if(summands[i].matches("^-?\\d+\\.?\\d*")) 
               preResult = Double.parseDouble(summands[i].trim());
            if(i>0 && summands[i-1].matches("-") && summands[i]!=null)
               preResult = -preResult;            
            if(preResult!=null)
               doubles.add(preResult);
            }        
        double result = doubles.stream().reduce(0., Double::sum); 
        return result;
    }
    public String multAndDiv(String mathExpression) throws IllegalArgumentException {
        String multOrDivPattern = "(-?[$\\p{Alnum}_]*\\.?\\d* [\\/*] -?[$\\p{Alnum}_]*\\.?\\d*)";
        Pattern pattern = Pattern.compile(multOrDivPattern);
        Matcher matcher = pattern.matcher(mathExpression);
        ArrayList<Double> multipliers = new ArrayList<>();
        while(matcher.find()) {
            StringBuilder sb = new StringBuilder(mathExpression);              
            String part = matcher.group(1).trim();
            String[] parts = part.split(" ");
            Double multiplier = 1.;
            for (int i=0; i<parts.length; i++) {
                if(parts[i].startsWith("$")) {
                   multiplier = mapVar.get(parts[i].replace("$", "").trim()); 
                   multipliers.add(multiplier);
                   }
                else if(parts[i].matches("^-?\\d+\\.?\\d*"))  {
                   multiplier = Double.parseDouble(parts[i].trim());
                   multipliers.add(multiplier);
                   }             
                } 
                if(parts[1].equals("/") && multipliers.get(1)==0)
                    throw new IllegalArgumentException();
                multiplier = parts[1].equals("*") ? multipliers.get(0)*multipliers.get(1) : multipliers.get(0)/multipliers.get(1); 
                sb = sb.delete(matcher.start(1), matcher.end(1));
                mathExpression = sb.insert(matcher.start(1), String.valueOf(multiplier)).toString(); 
                matcher = pattern.matcher(mathExpression); 
                multipliers.clear();
            }  
        return mathExpression;   
    }
    public String powCalc(String mathExpression) {
        String powPattern = "(-?[$\\p{Alnum}_]*\\.?\\d* [\\^] -?[$\\p{Alnum}_]*\\.?\\d*)";
        Pattern pattern = Pattern.compile(powPattern);
        Matcher matcher = pattern.matcher(mathExpression);
        ArrayList<Double> powMembers = new ArrayList<>();
        while(matcher.find()) {
            StringBuilder sb = new StringBuilder(mathExpression);              
            String part = matcher.group(1).trim();
            String[] parts = part.split(" ");
            Double powMember = 1.;
            for (int i=0; i<parts.length; i++) {
                if(parts[i].startsWith("$")) {
                   powMember = mapVar.get(parts[i].replace("$", "").trim()); 
                   powMembers.add(powMember);
                   }
                else if(parts[i].matches("^-?\\d+\\.?\\d*"))  {
                   powMember = Double.parseDouble(parts[i].trim());
                   powMembers.add(powMember);
                   }             
                } 
                Double powResult = Math.pow(powMembers.get(0), powMembers.get(1));  
                sb = sb.delete(matcher.start(1), matcher.end(1));
                mathExpression = sb.insert(matcher.start(1), String.valueOf(powResult)).toString(); 
                matcher = pattern.matcher(mathExpression); 
                powMembers.clear();
            }  
        return mathExpression;   
    }
}
