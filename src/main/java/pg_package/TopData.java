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

package pg_package;

import org.bukkit.plugin.java.JavaPlugin;

import pg_package.commands.ConfigTopData;
import pg_package.commands.CreateTopBloques;
import pg_package.commands.CreateTopHoras;
import pg_package.commands.CreateTopKills;
import pg_package.commands.ListTopData;
import pg_package.commands.RemoveTopData;
import pg_package.config.ConfigManager;
import pg_package.listeners.ScoreListener;
import pg_package.utils.FuntionUtils;

public class TopData extends JavaPlugin {
    public static String prefix = "&8&c&l[TopData]&8: ";
    private String version = getDescription().getVersion();
    private ConfigManager configManager;
    private FuntionUtils funtionUtils;

    @Override
    public void onEnable() {
        super.onEnable();
        getLogger().info("PLUGIN TOP DATA ENABLE");
        this.configManager = new ConfigManager(this);
        this.funtionUtils = new FuntionUtils(this);
        registerCommands();
        registerListeners();
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        getLogger().info("PLUGIN TOP DATA DISABLE");
    }

    public void registerCommands() {
        this.getCommand("createTopKills").setExecutor(new CreateTopKills(this));
        this.getCommand("createTopHoras").setExecutor(new CreateTopHoras(this));
        this.getCommand("createTopBloques").setExecutor(new CreateTopBloques(this));
        this.getCommand("removeTopData").setExecutor(new RemoveTopData(this));
        this.getCommand("listTopData").setExecutor(new ListTopData(this));
        this.getCommand("configTopData").setExecutor(new ConfigTopData(this));
    }

    public void registerListeners() {
        this.getServer().getPluginManager().registerEvents(new ScoreListener(this), this);
    }

    public String getVersion() {
        return version;
    }

    public FuntionUtils getFuntionUtils() {
        return funtionUtils;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }
    
}
