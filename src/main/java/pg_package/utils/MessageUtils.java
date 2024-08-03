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

import net.md_5.bungee.api.ChatColor;
import pg_package.TopData;

import org.bukkit.command.CommandSender;
import java.util.List;
import java.util.ListIterator;

public class MessageUtils {

    public static String convertColorMessage(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
    
    public static void sendColorMessage(CommandSender sender, String message) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', TopData.prefix + message));
    }
    
    public static void sendListMessage(CommandSender sender, List<String> messages) {
        for (String message : messages) {
            MessageUtils.sendColorMessage(sender, message);
        }
    }
    
    public static void sendMessageFormatUnit(CommandSender sender, String message, String regex, String replace) {      
        MessageUtils.sendColorMessage(sender,message.replaceAll(regex,replace));
    }

    public static void sendMessageFormatPlural(CommandSender sender, String message, List<String> regex, List<String> replace) {
        if(regex.size() == replace.size()) {
            ListIterator<String> regexIterator = regex.listIterator();
            ListIterator<String> replaceIterator = replace.listIterator();
            while(regexIterator.hasNext()) {
                message = message.replaceAll(regexIterator.next(),replaceIterator.next());
            }
            MessageUtils.sendColorMessage(sender, message);
        }
    }
    
    public static void sendListMessageFormat(CommandSender sender, List<String> messages, List<String> regex, List<String> replace) {
        for (String message: messages) {
            MessageUtils.sendMessageFormatPlural(sender, message, regex, replace);
        }
    }
    
}
