import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class WC {
    String[] options = {"-l","-w","-m","-c"}; 
    static Set<String> myOptions = new HashSet<String>();
    static Set<String> files = new HashSet<String>();
    static HashMap<String,Long> answers = new HashMap<>();
    //num bytes
    static long numBytes(File  file){
        return file.length();
    }

    //num lines
    static long numLines(File file){
        int c = 0;
        try{
            Scanner reader = new Scanner(file);
            while(reader.hasNextLine()){
                reader.nextLine();
                c++;    
            }
        }
        catch(FileNotFoundException ex){
            System.out.println(ex);
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        return c;
    }
 
    //num words
    static long numWords(File file){
        int c = 0;
        String[] words;
        String s = "";
        try{
            Scanner reader = new Scanner(file);
            while(reader.hasNextLine()){
                s += reader.nextLine()+"\n";   
            }
            words = s.split("\\s+");
            c+=words.length;  
        }
        catch(FileNotFoundException ex){
            System.out.println(ex);
        }
        return c;
    }

    //num chars
    static long numCharacters(File file){
        int c = 0;
        String s = "";
        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            String line;
            while ((line = br.readLine()) != null) {
                s += line;
            }
            c = s.length();
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
        return c;
    }

    static void optionsSelector(Set<String> options,File file){
        for(String option : options){
            switch (option) {
                case "-l":
                    answers.put("lines", numLines(file));
                    break;
            
                case "-c":
                    answers.put("bytes", numBytes(file));
                    break;

                case "-w":
                    answers.put("words", numWords(file));
                    break; 

                case "-m":
                    answers.put("words", numCharacters(file));
                    break;
                    
                default:
                    break;    
            }
        }
    }

    public static void main(String[] args) {
        String folderName = System.getProperty("user.dir");
        String fileName = "";
        if(args.length > 0){
            for(String s : args){
                if(s.startsWith("-") && !files.isEmpty()){
                    System.err.println("the file names must be at the end of the command");
                    return;
                }
                if(s.startsWith("-"))
                    myOptions.add(s);
                else    
                    files.add(s);    
            }
        }  
        if(fileName.isEmpty()) return;
        File file = new File(folderName+fileName);
        System.out.println(fileName + "\t" + numBytes(file));
        System.out.println(fileName + "\t" + numLines(file));
        System.out.println(fileName + "\t" + numWords(file));
        System.out.println(fileName + "\t" + numCharacters(file));
    }

}
