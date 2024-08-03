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

package pg_package.listeners;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import pg_package.TopData;
import pg_package.utils.FuntionUtils;

public class ScoreListener implements Listener {
    private TopData plugin;
    private FuntionUtils funtionUtils;
    private HashMap<UUID, Long> joinTimes = new HashMap<>();

    public ScoreListener(TopData plugin_) {
        this.plugin = plugin_;
        this.funtionUtils = plugin.getFuntionUtils();
    }

    // Incremento de kills
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player killer = event.getEntity().getKiller();
        if (killer != null) {
            funtionUtils.updateTopKills(killer.getName());
        }
        else {
            // Se añade cualquier otra causa de muerte a ThMisaBossxddd
            funtionUtils.updateTopKills("ThMisaBossxddd");
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // Se añade a la lista playerID con tiempo de conexion desde 1970
        UUID playerId = event.getPlayer().getUniqueId();
        joinTimes.put(playerId, System.currentTimeMillis());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        // Obtener el tiempo de session
        UUID playerId = event.getPlayer().getUniqueId();
        long exitTime = System.currentTimeMillis();
        long joinTime = joinTimes.get(playerId);
        // Entre mil porque esta en milisegundos, guardare en segundos
        long sessionTime = (exitTime - joinTime) / 1000;

        // Remover de la lista
        joinTimes.remove(playerId);

        // Añadir al top horas el tiempo de session
        funtionUtils.updateTopHoras(event.getPlayer().getName(), sessionTime);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if(player != null) {
            funtionUtils.updateTopBloques(player.getName());
        }
    }

}