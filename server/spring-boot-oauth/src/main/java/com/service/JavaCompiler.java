package com.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * This class contains methods to run java source files
 */
@Component
public class JavaCompiler {
    private final Logger log = LoggerFactory.getLogger(JavaCompiler.class);

    private final String MVN = "mvn";
    private final String JAVA = "java";
    private final String XML = "xml";
    private final String SRC_PATH = "/src/main/java/com/";

    /**
     * This method compile java source code
     * @param command
     * @param output
     * @throws IOException
     * @throws InterruptedException
     */
    private void compileSource(String command, List<String> output) throws IOException, InterruptedException {

        Runtime rt = Runtime.getRuntime();
        Process proc = rt.exec(command);
        InputStream stderr;
        if (command.startsWith(MVN)){
            stderr = proc.getInputStream();
        } else {
            stderr = proc.getErrorStream();
        }
        InputStreamReader isr = new InputStreamReader(stderr);
        BufferedReader br = new BufferedReader(isr);
        String line = null;
        StringBuilder obr = new StringBuilder();
        while ((line = br.readLine()) != null) {
            obr.append(line);
            log.debug(line);
        }
        if (!obr.toString().isEmpty()) {
            output.add(obr.toString());
        }

        proc.waitFor();
    }


    /**
     * This method gets java class file in given directory
     * @param directoryPath
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    private  List<String> getJavaFilesInDirectory(String directoryPath) throws IOException, InterruptedException{

        Runtime rt = Runtime.getRuntime();
        StringBuilder command = new StringBuilder()
                .append("ls ")
                .append(directoryPath);

        Process proc = rt.exec(command.toString());
        InputStream is = proc.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line = null;
        List<String> lineStr = new ArrayList<>();
        while ( (line = br.readLine()) != null) {
            if(line.endsWith(JAVA)) {
                lineStr.add(line);
            } else if(line.endsWith(XML)){
                lineStr.add(line);
            }
        }
        proc.waitFor();
        return lineStr;
    }


    /**
     * main method for java compilation process
     * @param path
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public List<String> compile(String path) throws IOException, InterruptedException
    {
        List<String> lsOutput = getJavaFilesInDirectory(path);
        List<String> output = new ArrayList<>();

        if(lsOutput.contains("pom.xml")){
            StringBuilder command = new StringBuilder()
                    .append("mvn ")
                    .append("-f ")
                    .append(path)
                    .append(" test");
            compileSource(command.toString(), output);
        } else {
            //get java source file
            StringBuilder absPath = new StringBuilder()
                    .append(path)
                    .append(SRC_PATH);
            lsOutput = getJavaFilesInDirectory(absPath.toString());

            if(lsOutput.size()<1){
                 throw new IOException("Directory does not contain executable");
            }
            for (String l: lsOutput){
                StringBuilder command = new StringBuilder().append("javac ").append(absPath).append(l);
                compileSource(command.toString(), output);
            }
        }

        if(output.isEmpty()){
            output.add("Compilation successful");
        }
        return output;
    }

//
//    public static void main( String[] args ) throws Exception
//    {
//
//        JavaCompiler jc= new JavaCompiler();
//        String path = "/tmp/javasample-error/";
//        List<String> output = jc.compile(path);
//
//        System.out.println("Below is the output...");
//        for(String o: output){
//            System.out.println(output);
//        }
//
//    }


}
