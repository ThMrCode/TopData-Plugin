package pg_package.config;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import pg_package.TopData;

import java.io.File;
import java.io.IOException;

public class CustomConfig {
    private TopData plugin;
    private String folderName;
    private String fileName;
    private FileConfiguration fileConfiguration = null;
    private File file = null;

    public CustomConfig(String fileName_, String folderName_, TopData plugin_){
        this.fileName = fileName_;
        this.folderName = folderName_;
        this.plugin = plugin_;
    }

    public String getPath(){
        return this.fileName;
    }

    public void registerConfig(){
        // Crea archivo de configuracion fuera del jar accesible al usuario administrador
        // Pregunta si ya existe este archivo de configuracion externo
        if(folderName != null){
            file = new File(plugin.getDataFolder() +File.separator + folderName,fileName);
        }else{
            file = new File(plugin.getDataFolder(), fileName);
        }

        // Si no existe entonces lo crea copiando el config del jar (desde carpeta resources) a la ruta por defecto de archivos externos del plugin (plugins/Plugin)
        if(!file.exists()){
            if(folderName != null){
                plugin.saveResource(folderName+File.separator+fileName, false);
            }else{
                plugin.saveResource(fileName, false);
            }
        }

        // Crea un objeto Yaml que contiene la configuracion que hay en el archivo de configuracion externo, sea que haya sido recien cargado, o ya previamente editado
        fileConfiguration = new YamlConfiguration();
        try {
            fileConfiguration.load(file);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void saveConfig() {
        // Sobreescribe el archivo de configuracion externo
        try {
            fileConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getConfig() {
        if (fileConfiguration == null) {
            reloadConfig();
        }
        return fileConfiguration;
    }

    public boolean reloadConfig() {
        // Presupone que el archivo de configuracion externo ya existe
        if (fileConfiguration == null) {
            if(folderName != null){
                file = new File(plugin.getDataFolder() + File.separator + folderName, fileName);
            }else{
                file = new File(plugin.getDataFolder(), fileName);
            }

        }
        // Sobreescribe el objeto Yaml
        fileConfiguration = YamlConfiguration.loadConfiguration(file);

        // Si el archivo de configuracion externo ya existe selecciona que sea el estado por defecto del yaml config
        if(file != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(file);
            fileConfiguration.setDefaults(defConfig);
        }
        return true;
    }
}
