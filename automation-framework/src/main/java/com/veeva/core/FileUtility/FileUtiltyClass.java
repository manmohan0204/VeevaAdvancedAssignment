package com.veeva.core.FileUtility;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.veeva.core.Logger.VeevaLog;
import com.veeva.core.Logger.VeevaLog.VeevaLogLevel;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * <b>Name :</b> FileUtiltyClass </br>
 * <b>Description : </b> This is a Utility class to handle all the file operation, like read , write and all data</br>
 *
 * @author <i>Manmohan Dash</i>
 */

public class FileUtiltyClass {

    public static Logger _log = Logger.getLogger(FileUtiltyClass.class);

    // Method to write the Data to a file
    public static void writeDataToFile(String filePath, String data) {
        try {
            VeevaLog.log(_log, "Writing to File Started.....", VeevaLogLevel.info, VeevaLogLevel.reporter);
            File file = new File(filePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
            }
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(data);
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to read the Data from file
    public static List<String> readDataFromFile(String filePath) {
        VeevaLog.log(_log, "Reading of File Started.....", VeevaLogLevel.info, VeevaLogLevel.reporter);
        List<String> data = new ArrayList<>();
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                return null;
            }
            FileReader fw = new FileReader(file);
            BufferedReader br = new BufferedReader(fw);
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                data.addAll(Arrays.asList(values));
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    // Method to check If file is already present in directory and delete if file already exist
    public static void checkIfFileExistAndDelete(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            boolean b = file.delete();
            if (b) {
                VeevaLog.log(_log, "File present and Successfully deleted from directory...", VeevaLogLevel.info, VeevaLogLevel.reporter);
            }
        }
    }

    // Method to retrieve Data from JSON file and Convert data to Map
    public static HashMap<String, Object> getJsonDataToMap(String filePath) throws IOException {
        //read json content to string content
        String jsonContent = FileUtils.readFileToString(new File(filePath),
                StandardCharsets.UTF_8);
        //String Content to HashMap using - Jackson Databind
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String, Object>> reference = new TypeReference<HashMap<String, Object>>() {
        };
        HashMap<String, Object> data = mapper.readValue(jsonContent, reference);
        return data;
    }
}
