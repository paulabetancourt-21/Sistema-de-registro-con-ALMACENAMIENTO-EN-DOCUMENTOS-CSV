package co.edu.uptc.config;
import co.edu.uptc.config.GlobalConfigProperties;

public class GlobalConfig {
    private static String nameFileConfig = "app.conf";
    private static GlobalConfigProperties properties;
    public static GlobalConfig instance = null;

    private GlobalConfig(){
    }

    public static GlobalConfig getInstance(){
        if (instance == null) {
            instance  = new GlobalConfig();
            load();
        }
        return instance;
    }

    private static void load(){
        properties = new GlobalConfigProperties();
        properties.load(nameFileConfig);
    }

    public static String getProperty(String key){
        return properties.getProperty(key);
    }

    public String nameFolder(){
        String name = getProperty("Folder");
        return name;
    }

    public String nameFilePeople(){
        String name = getProperty("PeopleFile");
        return name;
    }

    public String nameFileProduct(){
        String name = getProperty("ProductFile");
        return name;
    }


}
