package app.persistence;

import com.google.gson.Gson; 
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonLoader {
    
    private static final String DATA_DIR = "src/main/resources/data/";

    
    public static <T> T load(String filename, Type typeOfT) {
        try {
            
            if (!Files.exists(Paths.get(DATA_DIR + filename))) {
                System.err.println("Error: File not found " + filename);
                return null;
            }

            Gson gson = new Gson();
            try (FileReader reader = new FileReader(DATA_DIR + filename)) {
                return gson.fromJson(reader, typeOfT);
            }
        } catch (IOException e) {
            System.err.println("Failed to load data from " + filename);
            e.printStackTrace();
            return null;
        }
    }
}
