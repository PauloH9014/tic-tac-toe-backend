package Domain.Store;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class DataBaseConfig {
        private static final Properties configProperties = new Properties();

        static{
            try(FileInputStream inputStream = new FileInputStream("config.properties")){
                configProperties.load(inputStream);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        public static String get(String key){
            return configProperties.getProperty(key);
        }
}
