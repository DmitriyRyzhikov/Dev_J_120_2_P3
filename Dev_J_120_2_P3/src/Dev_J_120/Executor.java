package Dev_J_120;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;

public class Executor {

    private Set<String> scriptOperators = new HashSet<>();
    private List<String> scriptList = new ArrayList<>();
    private final Map<String, Double> mapVar = new HashMap<>();

    public Executor() {
        this.scriptList = Loader.loadScript();
        this.scriptOperators = Loader.loadOperators();
    }

    public void execute() {

        for (int i = 0; i < scriptList.size(); i++) {
            String currentRow = scriptList.get(i);
            String operator = currentRow.split(" ", 2)[0];
            if (!scriptOperators.contains(operator)) {
                System.out.println("Скрипт содержит неизвестный оператор "
                        + "и не может быть выполнен. Приложение будет остановлено.");
                System.exit(0);
            }
            currentRow = currentRow.split(" ", 2)[1].trim();
            switch (operator) {
                case ("print"):
                    doPrint(currentRow);
                    break;
                case ("set"):
                     try {
                    doSet(currentRow);
                } catch (IllegalArgumentException iae) {
                    System.out.println("При попытке выполнить операцию деления, делитель оказался равен 0. "
                            + "Приложение будет остановлено.");
                    System.exit(0);
                }
                break;
                case ("input"):
                    doInput(currentRow);
                    break;
            }
        }
    }

    public void doPrint(String currentRow) {

        Scanner scanner = new Scanner(currentRow).useDelimiter(", ");
        StringBuilder sb = new StringBuilder();
        String row = scanner.findInLine("^\".+\"$");
        if (row != null) {
            System.out.println(row.replace("\"", ""));
        } else {
            while (scanner.hasNext()) {
                String part = scanner.next();
                if (part.matches("\".+\"") && !part.matches("\"=\"")) {
                    sb.append(part.replace("\"", ""));
                } else if (part.startsWith("$")) {
                    Double temp = mapVar.get(part.replace("$", ""));
                    part = (temp < 0 && scanner.hasNext()) ? ("(" + temp.toString() + ")") : temp.toString();
                    sb.append(part);
                } else if (part.matches("\"=\"")) {
                    sb.append(part.replace("\"", " "));
                }
            }
            System.out.println(sb.toString());
            scanner.close();
        }
    }

    public void doSet(String currentRow) throws IllegalArgumentException {

        if (currentRow.matches("^\\$[^ ]+ = [^ ].*")) {
            String varName = currentRow.split(" = ")[0];
            String value = currentRow.split(" = ")[1];
            Calculator calculator = new Calculator(mapVar);
            BracketHandler bracketHandler = new BracketHandler(mapVar);
            value = bracketHandler.bracketParser(value);
            if (value.contains("^")) 
                value = calculator.powCalc(value); 
            if (value.contains("*") || value.contains("/")) 
                value = calculator.multAndDiv(value);           
            double resultValue = calculator.plusAndMinus(value);
            varName = varName.replace("$", "");
            if (mapVar.containsKey(varName)) {
                mapVar.replace(varName, resultValue);
            } else {
                mapVar.put(varName, resultValue);
            }
        } else {
            System.out.println("Скрипт содержит ошибки и не может быть выполнен. "
                    + "Приложение будет остановлено.");
            System.exit(0);
        }
    }

    public void doInput(String currentRow) {
        System.out.println(currentRow.split(", ")[0].replace("\"", ""));
        Double tempDouble = 0.;
        try ( Scanner scanner = new Scanner(System.in)) {
            tempDouble = scanner.nextDouble();
        } catch (NoSuchElementException nsee) {
            System.out.println("Произошла ошибка при вводе данных. "
                    + "Введенные данные не являются числом. "
                    + "Приложение будет остановлено.");
            System.exit(0);
        }
        String varNameInput = currentRow.split(", ")[1].replace("$", "");
        if (mapVar.containsKey(varNameInput)) {
            mapVar.replace(varNameInput, tempDouble);
        } else {
            mapVar.put(varNameInput, tempDouble);
        }
    }
}

//script.txt

