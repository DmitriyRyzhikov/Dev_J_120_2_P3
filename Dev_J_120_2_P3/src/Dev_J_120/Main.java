
package Dev_J_120;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Main {

 
    public static void main(String[] args) {
       new Executor().execute();
       
      /*
       Pattern pattern = Pattern.compile("print ");
       String row = "print \"$sum = \", $n1, \"+\", $n2, \"-42 = \", $sum";
       Stream<String> stream = pattern.splitAsStream(row);
       List<String> asList = stream.collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
       asList.forEach(System.out::println); */
    }
    
}



// script.txt