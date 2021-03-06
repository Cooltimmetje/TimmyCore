package me.VanadeysHaven.TimmyCore.Utilities;

import me.VanadeysHaven.TimmyCore.Data.Profiles.User.CorePlayer;
import org.bukkit.command.CommandSender;

public final class MessageUtilities {

    public static void sendMessage(CorePlayer cp, String message){
        sendMessage(cp.getPlayer(), message);
    }

    public static void sendMessage(CommandSender player, String message){
        player.sendMessage(StringUtilities.colorify(message));
    }

    public static void sendMessage(CorePlayer cp, String tag, String message) {
        sendMessage(cp.getPlayer(), tag, message);
    }

    public static void sendMessage(CommandSender player, String tag, String message){
        sendMessage(player, tag, message, false);
    }

    public static void sendMessage(CorePlayer cp, String tag, String message, boolean isError){
        sendMessage(cp.getPlayer(), tag, message, isError);
    }

    public static void sendMessage(CommandSender player, String tag, String message, boolean isError) {
        if (isError) {
            sendMessage(player, StringUtilities.formatMessageWithError(tag, message));
        } else {
            sendMessage(player, StringUtilities.formatMessageWithTag(tag, message));
        }
    }


}
