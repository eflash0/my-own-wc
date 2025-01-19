import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class WC {
    static String[] options = {"-l","-w","-m","-c"}; 
    static Set<String> myOptions = new HashSet<String>();
    static Set<String> files = new HashSet<String>();
    static HashMap<String,Long> answers = new HashMap<>();
    //num bytes
    static long numBytes(Object object) throws Exception{
        if(object instanceof File){
            File file = (File) object;
            return file.length();
        }
        else if(object instanceof String){
            String input = (String) object;
            return input.getBytes(StandardCharsets.UTF_8).length;
        }
        return 0;
    }

    //num lines
    static long numLines(Object object) throws Exception{
        int c = 0;
        if (object instanceof File) {
            File file = (File) object;
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
        }
        else if (object instanceof String) {
            String input = (String) object;
            return input.lines().count();
        }
        return c;
    }
 
    //num words
    static long numWords(Object object) throws Exception{
        int c = 0;
        String[] words;
        String s = "";
        if (object instanceof File) {
            File file = (File) object;
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
        }
        else if (object instanceof String) {
            String input = (String) object;
            words = input.split("\\s+");
            return words.length;
        }
        return c;
    }

    //num chars
    static long numCharacters(Object object) throws Exception{
        String s = "";
        if (object instanceof File) {
            File file = (File) object;
            try(BufferedReader br = new BufferedReader(new FileReader(file))){
                String line;
                while ((line = br.readLine()) != null) {
                    s += line;
                }
            }
            catch(IOException e){
                System.out.println(e.getMessage());
            }
        }
        else if (object instanceof String) {
            String input = (String) object;
            return input.length();
        }
        return s.length();
    }

    static void optionSelector(Set<String> options,Object object,StringBuilder sb) throws Exception{
        if(options.isEmpty()){
            sb.append("   "+numBytes(object)+"\t");
            sb.append(numCharacters(object)+"\t");
            sb.append(numWords(object)+"\t");
            sb.append(numLines(object)+"\t");
            return;
        }
        for(String option : options){
            switch (option) {
                case "-l":
                    sb.append("   "+numLines(object)+"\t");
                    break;
            
                case "-c":
                    sb.append("   "+numBytes(object)+"\t");
                    break;

                case "-w":
                    sb.append("   "+numWords(object)+"\t");
                    break; 

                case "-m":
                    sb.append("   "+numCharacters(object)+"\t");
                    break;
                    
                default:
                    break;    
            }
        }
    }

    static void optionSelectorStandardInput(Set<String> options,BufferedReader reader,StringBuilder sb) throws Exception{
        String line;
        int countWords = 0;
        int countBytes = 0;
        int countChars = 0;
        int countLines = 0;
        while ((line = reader.readLine()) != null) {
            countLines++;
            //i will pass in the methdos above Object for type and check whether the instance is
            // a file or a bufferReader
        }
    }

    static boolean argsProcessor(String[] args){
        for(String s : args){
            if(s.startsWith("-")){
                if(Arrays.asList(options).contains(s)){
                    if(!files.isEmpty()){
                        System.out.println("Wc: illegal input format\\n" + //
                                                        "usage: java Wc [-clwfm] [file ...]");
                        return false;                                
                    }
                    else{
                        myOptions.add(s);
                    }    
                } 
            }
            else{
                files.add(s);
            }
        }
        return true;
    } 

    public static void main(String[] args) {
        boolean b = false;
        String folderName = System.getProperty("user.dir");
        StringBuilder sb = new StringBuilder();
        b = argsProcessor(args);
        if(b){   
            try {
                if(!files.isEmpty()){
                    for(String fileName : files){
                        File file = new File(folderName+"\\"+fileName);
                        optionSelector(myOptions, file,sb);
                        sb.append(fileName);   
                    }
                }
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String input = reader.lines().collect(Collectors.joining("\n"));
                if (!input.isEmpty()) { // Check if there's input in the stdin
                    optionSelector(myOptions, input, sb);
                }
                System.out.println(sb.toString());
            } 
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
