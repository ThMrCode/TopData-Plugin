/*
 * Copyright (c) 2024 [ThMrCode]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package pg_package.config;

import org.bukkit.configuration.file.FileConfiguration;

import pg_package.TopData;
import java.util.List;

public class ConfigManager {
    private CustomConfig configFile;
    private TopData plugin;
    private List<String> killsFormat;
    private List<String> killsDefault;
    private List<String> horasFormat;
    private List<String> horasDefault;
    private List<String> bloquesFormat;
    private List<String> bloquesDefault;
    private List<String> messageHelp;
    private String messageExecuteCorrect;
    private String messageExecuteIncorrect;
    private String messageExecuteNoPermission;

    public ConfigManager(TopData plugin_) {
        this.plugin = plugin_;
        this.configFile = new CustomConfig("config.yml",null,plugin);
        configFile.registerConfig();
        loadConfig();
    }

    public void loadConfig() {
        FileConfiguration config = configFile.getConfig();
        this.killsFormat = config.getStringList("config.kills.format");
        this.killsDefault = config.getStringList("config.kills.default");
        this.horasFormat = config.getStringList("config.horas.format");
        this.horasDefault = config.getStringList("config.horas.default");
        this.bloquesFormat = config.getStringList("config.bloques.format");
        this.bloquesDefault = config.getStringList("config.bloques.default");
        this.messageHelp = config.getStringList("message.help");
        this.messageExecuteCorrect = config.getString("message.execute.correct");
        this.messageExecuteIncorrect = config.getString("message.execute.incorrect");
        this.messageExecuteNoPermission = config.getString("message.execute.no_permission");
    }

    public void reloadConfig() {
        configFile.reloadConfig();
        loadConfig();
    }
    
    public List<String> getKillsFormat() {
        return killsFormat;
    }

    public List<String> getKillsDefault() {
        return killsDefault;
    }
    
    public List<String> getHorasFormat() {
        return horasFormat;
    }
    
    public List<String> getHorasDefault() {
        return horasDefault;
    }

    public List<String> getBloquesFormat() {
        return bloquesFormat;
    }

    public List<String> getBloquesDefault() {
        return bloquesDefault;
    }
    
    public List<String> getMessageHelp() {
        return messageHelp;
    }

    public String getMessageExecuteCorrect() {
        return messageExecuteCorrect;
    }

    public String getMessageExecuteIncorrect() {
        return messageExecuteIncorrect;
    }

    public String getMessageExecuteNoPermission() {
        return messageExecuteNoPermission;
    }

}
