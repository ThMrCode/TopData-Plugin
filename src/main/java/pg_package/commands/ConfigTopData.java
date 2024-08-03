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

package pg_package.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import pg_package.TopData;
import pg_package.config.ConfigManager;
import pg_package.utils.FuntionUtils;

public class ConfigTopData implements CommandExecutor{
    private TopData plugin;
    private ConfigManager configManager;
    private FuntionUtils funtionUtils;
    private String command = "Config Top Data";

    public ConfigTopData(TopData plugin_) {
        this.plugin = plugin_;
        this.configManager = plugin.getConfigManager();
        this.funtionUtils = plugin.getFuntionUtils();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender.isOp()) {
            boolean help = true;
            if(args.length == 1) {
                if(args[0].equalsIgnoreCase("reload")) {
                    configManager.reloadConfig();
                    funtionUtils.sendCorrect(sender, command);
                    help = false;
                }
                else if(args[0].equals("REMOVEARMORSTAND")) {
                    funtionUtils.removeAllArmorStands();
                    funtionUtils.sendCorrect(sender, command + " REMOVE ARMOR STAND");
                    help = false;
                }
                else if(args[0].equalsIgnoreCase("help")) {
                    funtionUtils.sendHelp(sender);
                    help = false;
                }
            }
            else if(args.length == 2) {
                if(args[0].equalsIgnoreCase("TopData") && args[1].equalsIgnoreCase("reload")) {
                    funtionUtils.reloadDataConfig();
                    funtionUtils.getTopKillsManager().reloadTopList();
                    funtionUtils.getTopHorasManager().reloadTopList();
                    funtionUtils.getTopBloquesManager().reloadTopList();
                    funtionUtils.sendCorrect(sender, command + " TopData reload");
                    help = false;
                }
                else if(args[0].equalsIgnoreCase("Hologram") && args[1].equalsIgnoreCase("reload")) {
                    funtionUtils.reloadHologram();
                    funtionUtils.sendCorrect(sender, command + " Hologram reload");
                    help = false;
                }
            }
            if(help) funtionUtils.sendHelp(sender);
        }
        else {
            funtionUtils.sendNoPermission(sender, command);
        }
        return true;
    }
}
