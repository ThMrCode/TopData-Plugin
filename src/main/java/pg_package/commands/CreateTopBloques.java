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
 
 public class CreateTopBloques implements CommandExecutor{
     private TopData plugin;
     private FuntionUtils funtionUtils;
     private String type = "bloques";
     private String command = "Create Top Bloques";
 
     public CreateTopBloques(TopData plugin_) {
         this.plugin = plugin_;
         this.funtionUtils = plugin.getFuntionUtils();
     }
 
     @Override
     public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
         if(sender.isOp()) {
             boolean help = true;
             if (sender instanceof Player) {
                 if(args.length == 2) {
                     if(funtionUtils.getHologramManager().existHologram(type,args[0])) {
                         MessageUtils.sendColorMessage(sender, "El Holograma " + type + " " + args[0] + " ya existe");
                         help = false;
                     }
                     else {
                         Player player = (Player)sender;
                         try {
                             double newY = Double.parseDouble(args[1]);
                             funtionUtils.createTopBloques(player, args[0], newY);
                             funtionUtils.sendCorrect(sender, command);
                             help = false;
                         } catch (Exception e) {
                             funtionUtils.sendIncorrect(sender, command);
                         }
                     }
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