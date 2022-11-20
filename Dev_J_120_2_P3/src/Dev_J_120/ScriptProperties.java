
package Dev_J_120;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
//задача класса - загрузить из Property файла сисок всех используемых операторов скрипта
public class ScriptProperties extends Properties{
    
    private static ScriptProperties sp;
    
    public static ScriptProperties get() throws IOException{
        if(sp==null) {
           sp = new ScriptProperties();
           File file = new File("script.properties");
           if(file.exists()) 
               file.createNewFile();
           sp.load(new FileInputStream(file));                    
           }
        return sp;
    }
}
