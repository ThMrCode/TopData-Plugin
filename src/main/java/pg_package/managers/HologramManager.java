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

package pg_package.managers;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

import pg_package.TopData;
import pg_package.config.CustomConfig;
import pg_package.utils.MessageUtils;

public class HologramManager {
    private TopData plugin;
    private CustomConfig hologramConfig;
    private String pathFile = "hologramUUID.yml";
    // Estructura de los paths "hologram.type.name"
    // Estructura de formats %player%, %type%
    // Estructura de tops
    // Title
    // Position 1 ...

    public HologramManager(TopData plugin_) {
        this.plugin = plugin_;
        this.hologramConfig = new CustomConfig(pathFile,null,plugin);
        hologramConfig.registerConfig();
    }

    public String spawnHologramText(World world, Location loc, String text) {
        ArmorStand armor = (ArmorStand)world.spawnEntity(loc, EntityType.ARMOR_STAND);
        armor.setCustomName(MessageUtils.convertColorMessage(text));
        armor.setCustomNameVisible(true);
        armor.setInvisible(true);
        armor.setGravity(false);
        armor.setMarker(true);
        return armor.getUniqueId().toString();
    }

    public String getPath(String type, String name) {
        String stringReturn = "hologram." + type + "." + name;
        return stringReturn;
    }

    public String getSection(String type) {
        String stringReturn = "hologram." + type;
        return stringReturn;
    }

    public boolean existHologram(String type, String name) {
        // Estructura de los paths "hologram.type.name"
        FileConfiguration hologramFile = hologramConfig.getConfig();
        if(hologramFile.contains(getPath(type, name))) return true;
        else return false;
    }

    public void spawnHologram(World world, Location loc, List<String> text, String type, String name) {
        // Spawnear Hologram
        FileConfiguration hologramFile = hologramConfig.getConfig();
        List<String> hologramUUID= new ArrayList<>();
        for (String message : text) {
            loc.add(0, -0.3, 0);
            String uuid = spawnHologramText(world, loc, MessageUtils.convertColorMessage(message));
            hologramUUID.add(uuid);
        }
        // Guardar los datos del holograma en el YML
        hologramFile.set(getPath(type, name), hologramUUID);
        hologramConfig.saveConfig();
    }

    public void removeHologram(String type, String name) {
        FileConfiguration hologramFile = hologramConfig.getConfig();
        List<String> hologramUUID = hologramFile.getStringList(getPath(type, name));
        for (String uuid : hologramUUID) {
            // Borrar las entidades
            Bukkit.getEntity(UUID.fromString(uuid)).remove();
        }
        // Borrar los datos del holograma en el YML
        hologramFile.set(getPath(type, name), null);
        hologramConfig.saveConfig();
    }

    public void listHologram(CommandSender sender, String type) {
        // Listar los hologramaas existentes
        FileConfiguration hologramFile = hologramConfig.getConfig();
        ConfigurationSection section = hologramFile.getConfigurationSection(getSection(type));
        boolean valueExits = true;
        if (section != null) {
            Set<String> keys = section.getKeys(false);
            for (String key : keys) {
                if(key != null) {
                    if(valueExits) {
                        MessageUtils.sendColorMessage(sender, "HOLOGRAMAS " + type + " EXISTENTES");
                        valueExits = false;
                    }
                    MessageUtils.sendColorMessage(sender, key);
                }
            }
        } 
        if(valueExits) {
            MessageUtils.sendColorMessage(sender, "No hay hologramas " + type + " existentes");
        }
    }

    // Funciones Adicionales
    public void updateTopHologram(String type, List<SimpleEntry<String,Long>> topList, List<String> format) {
        // Nos pasan el top y el formato, y se actualizan todos los hologramas
        FileConfiguration hologramFile = hologramConfig.getConfig();
        ConfigurationSection section = hologramFile.getConfigurationSection(getSection(type));
        if (section != null) {
            // Obtener Hologramas Name
            Set<String> holograms = section.getKeys(false);
            for (String hologramName : holograms) {
                // Obtener UUIDS Hologramas Text (ArmorStand)
                List<String> hologramUUID = hologramFile.getStringList(getPath(type, hologramName));
                for (int i = 0; i < topList.size(); i++) {
                    String uuid = hologramUUID.get(i+1);
                    ArmorStand armor = (ArmorStand)Bukkit.getEntity(UUID.fromString(uuid));
                    if(armor != null) {
                        String text = new String(format.get(i + 1));
                        text = text.replace("%player%", topList.get(i).getKey());
                        text = text.replace("%" + type + "%", topList.get(i).getValue().toString());
                        armor.setCustomName(MessageUtils.convertColorMessage(text));
                    }
                };
            }
        }
    }

    public void updateTopHologramRescale(String type, long rescale, List<SimpleEntry<String,Long>> topList, List<String> format) {
        // Nos pasan el top y el formato, y se actualizan todos los hologramas
        FileConfiguration hologramFile = hologramConfig.getConfig();
        ConfigurationSection section = hologramFile.getConfigurationSection(getSection(type));
        if (section != null) {
            // Obtener Hologramas Name
            Set<String> holograms = section.getKeys(false);
            for (String hologramName : holograms) {
                // Obtener UUIDS Hologramas Text (ArmorStand)
                List<String> hologramUUID = hologramFile.getStringList(getPath(type, hologramName));
                for (int i = 0; i < topList.size(); i++) {
                    String uuid = hologramUUID.get(i+1);
                    ArmorStand armor = (ArmorStand)Bukkit.getEntity(UUID.fromString(uuid));
                    if(armor != null) {
                        String text = new String(format.get(i + 1));
                        long resc = (topList.get(i).getValue() / rescale);
                        text = text.replace("%player%", topList.get(i).getKey());
                        text = text.replace("%" + type + "%", "" + resc);
                        armor.setCustomName(MessageUtils.convertColorMessage(text));
                    }
                };
            }
        }
    }

    public void reload() {
        hologramConfig.reloadConfig();
    }
}
