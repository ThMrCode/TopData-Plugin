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
import org.bukkit.entity.Player;

import pg_package.TopData;
import pg_package.utils.FuntionUtils;
import pg_package.utils.MessageUtils;

public class RemoveTopData implements CommandExecutor{
    private TopData plugin;
    private FuntionUtils funtionUtils;
    private String command = "Remove Top Data";

    public RemoveTopData(TopData plugin_) {
        this.plugin = plugin_;
        this.funtionUtils = plugin.getFuntionUtils();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender.isOp()) {
            boolean help = true;
            if (sender instanceof Player) {
                if(args.length == 2) {
                    if(funtionUtils.getHologramManager().existHologram(args[0],args[1])) {
                        funtionUtils.getHologramManager().removeHologram(args[0],args[1]);
                        funtionUtils.sendCorrect(sender, command);
                    }
                    else {
                        MessageUtils.sendColorMessage(sender, "El Holograma " + args[0] + " " + args[1] + " no existe");
                    }
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
