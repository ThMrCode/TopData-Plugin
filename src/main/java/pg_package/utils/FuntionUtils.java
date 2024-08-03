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

package pg_package.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import pg_package.TopData;
import pg_package.config.ConfigManager;
import pg_package.config.CustomConfig;
import pg_package.managers.HologramManager;
import pg_package.managers.TopManager;

public class FuntionUtils {
    private TopData plugin;
    private ConfigManager configManager;
    private CustomConfig dataConfig;
    private HologramManager hologramManager;
    private TopManager topKillsManager;
    private TopManager topHorasManager;
    private TopManager topBloquesManager;
    // Tipos a Usar de Hologramas (hologram.)
    // kills

    public FuntionUtils(TopData plugin_) {
        // Inicializar Plugin y Managers
        this.plugin = plugin_;
        this.configManager = plugin.getConfigManager();
        this.dataConfig = new CustomConfig("data.yml",null,plugin);
        dataConfig.registerConfig();
        this.topKillsManager = new TopManager(dataConfig,"kills");
        this.topHorasManager = new TopManager(dataConfig,"horas");
        this.topBloquesManager = new TopManager(dataConfig,"bloques");
        this.hologramManager = new HologramManager(plugin);
        reloadHologram();
    }

    public void sendHelp(CommandSender sender) {
        MessageUtils.sendListMessage(sender,configManager.getMessageHelp());
    }

    public void sendCorrect(CommandSender sender, String command) {
        String message = configManager.getMessageExecuteCorrect().replaceAll("%command%", command);
        MessageUtils.sendColorMessage(sender, message);
    }

    public void sendIncorrect(CommandSender sender, String command) {
        String message = configManager.getMessageExecuteIncorrect().replaceAll("%command%", command);
        MessageUtils.sendColorMessage(sender, message);
    }

    public void sendNoPermission(CommandSender sender, String command) {
        String message = configManager.getMessageExecuteNoPermission().replaceAll("%command%", command);
        MessageUtils.sendColorMessage(sender, message);
    }

    // FUNCIONES DE HOLOGRAMAS
    public HologramManager getHologramManager() {
        return hologramManager;
    }

    // FUNCIONES DE TOP KILLS
    public TopManager getTopKillsManager() {
        return topKillsManager;
    }

    public void updateTopKills(String name) {
        topKillsManager.updateTop(name,1);
        hologramManager.updateTopHologram("kills", topKillsManager.getTopList(), configManager.getKillsFormat());
    }

    public void updateTopKillsNoIncrement() {
        hologramManager.updateTopHologram("kills", topKillsManager.getTopList(), configManager.getKillsFormat());
    }

    public void createTopKills(Player player, String name, double newY) {
        String type = "kills";
        World world = player.getWorld();
        Location location = player.getLocation().add(0, newY, 0);
        hologramManager.spawnHologram(world, location, configManager.getKillsDefault(), type,name);
        hologramManager.updateTopHologram(type,topKillsManager.getTopList(),configManager.getKillsFormat());
    }
    
    // FUNCIONES DE TOP HORAS
    public TopManager getTopHorasManager() {
        return topHorasManager;
    }

    public void updateTopHoras(String name, long value) {
        topHorasManager.updateTop(name,value);
        hologramManager.updateTopHologramRescale("horas",3600,topHorasManager.getTopList(),configManager.getHorasFormat());
    }

    public void updateTopHorasNoIncrement() {
        hologramManager.updateTopHologramRescale("horas",3600,topHorasManager.getTopList(),configManager.getHorasFormat());
    }

    public void createTopHoras(Player player, String name, double newY) {
        String type = "horas";
        World world = player.getWorld();
        Location location = player.getLocation().add(0, newY, 0);
        hologramManager.spawnHologram(world, location, configManager.getHorasDefault(), type,name);
        hologramManager.updateTopHologramRescale(type,3600,topHorasManager.getTopList(),configManager.getHorasFormat());
    }
    
    // FUNCIONES DE TOP BLOQUES
    public TopManager getTopBloquesManager() {
        return topBloquesManager;
    }

    public void updateTopBloques(String name) {
        topBloquesManager.updateTop(name,1);
        hologramManager.updateTopHologram("bloques",topBloquesManager.getTopList(),configManager.getBloquesFormat());
    }

    public void updateTopBloquesNoIncrement() {
        hologramManager.updateTopHologram("bloques",topBloquesManager.getTopList(),configManager.getBloquesFormat());
    }

    public void createTopBloques(Player player, String name, double newY) {
        String type = "bloques";
        World world = player.getWorld();
        Location location = player.getLocation().add(0, newY, 0);
        hologramManager.spawnHologram(world, location, configManager.getBloquesDefault(), type,name);
        hologramManager.updateTopHologram(type,topBloquesManager.getTopList(),configManager.getBloquesFormat());
    }
    
    // Funciones Adicionales
    public void removeAllArmorStands() {
        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (entity instanceof ArmorStand) {
                    entity.remove();
                }
            }
        }
    }
  
    public void reloadDataConfig() {
        dataConfig.reloadConfig();
    }

    public void reloadHologram() {
        hologramManager.reload();
        updateTopKillsNoIncrement();
        updateTopHorasNoIncrement();
        updateTopBloquesNoIncrement();
    }

}
