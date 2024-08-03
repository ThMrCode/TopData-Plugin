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
import java.util.stream.Collectors;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import pg_package.config.CustomConfig;

public class TopManager {
    private CustomConfig topConfig;
    private String type;
    private List<SimpleEntry<String,Long>> topList;

    public TopManager(CustomConfig dataConfig, String type_) {
        this.type = type_;
        this.topConfig = dataConfig;
        this.topList = new ArrayList<>();
        registerTop();
    }

    public String getPath(String name) {
        String stringReturn = type + "." + name;
        return stringReturn;
    }

    public void registerTop() {
        // Inicializa el top List con los datos del YML
        FileConfiguration topFile = topConfig.getConfig();
        ConfigurationSection section = topFile.getConfigurationSection(type);
        if (section != null) {
            Set<String> players = section.getKeys(false);
            for (String name : players) {
                if(name != null) {
                    long scoreCount = topFile.getLong(getPath(name));
                    topList.add(new SimpleEntry<>(name,scoreCount));
                }
            }
            topList = topList.stream()
                                .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                                .limit(10)
                                .collect(Collectors.toList());
        } 
    }

    public void updateTop(String name, long value) {
        FileConfiguration topFile = topConfig.getConfig();
        long scoreCount = topFile.getLong(getPath(name),0) + value;
        // Actualizar Score en el YML
        topFile.set(getPath(name),scoreCount);
        topConfig.saveConfig();
        // Actualizar Top Kills
        boolean valueNotFind = true;
        for (int i = 0; i < topList.size(); i++) {
            if(name.equals(topList.get(i).getKey())) {
                // Si el jugador ya se encuentra en la Lista Actualizar
                topList.get(i).setValue(scoreCount);
                valueNotFind = false;
                break;
            }
        }
        // Si el jugador no se encuentra en la Lista, añadirlo
        if(valueNotFind) topList.add(new SimpleEntry<>(name,scoreCount));
        topList = topList.stream()
        .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
        .limit(10)
        .collect(Collectors.toList());
    }

    public List<SimpleEntry<String, Long>> getTopList() {
        return topList;
    }
    
    public void reloadTopList() {
        this.topList = new ArrayList<>();
        registerTop();
    }

}
