package me.screwage.MentionMe;

import me.screwage.MentionMe.Resources.MentionMeTabCompleter;
import me.screwage.MentionMe.Resources.NotePitch;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Note;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashSet;

public class MentionMe extends JavaPlugin implements Listener, CommandExecutor {

    private Sound notificationSound;
    private float notificationVolume;
    private float notificationPitch;
    private ChatColor notificationColor;

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
        this.getCommand("mentionme").setTabCompleter(new MentionMeTabCompleter());
        this.notificationSound = Sound.BLOCK_NOTE_BLOCK_PLING;
        this.notificationVolume = 1.0f;
        this.notificationPitch = NotePitch.valueOf("C_TWO").getPitch();
        this.notificationColor = ChatColor.LIGHT_PURPLE;

    }

    @Override
    public void onDisable() {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("mentionme")){
            if(args.length == 1){
                if(args[0].equalsIgnoreCase("info")){
                    sender.sendMessage("Volume: " + this.notificationVolume);
                    sender.sendMessage("Sound: " + this.notificationSound);
                    sender.sendMessage("Color: " + this.notificationColor.name());
                    sender.sendMessage("Pitch: " + this.notificationPitch);
                    NotePitch note = NotePitch.getNoteFromPitch(this.notificationPitch);
                    if(note != null)
                        sender.sendMessage("Note: " + note.name());
                    return true;
                }
            }
            if(args.length != 2){
                sendIncorrectUsageMessage(sender, "all");
                return true;
            }

            if(args[0].equalsIgnoreCase("volume")){
                if(args.length != 2){
                    sendIncorrectUsageMessage(sender, "volume");
                }
                else{
                    try{
                        this.notificationVolume = Float.parseFloat(args[1]);
                    }
                    catch (Exception e){
                        sendIncorrectUsageMessage(sender, "volume");
                        this.getLogger().info(sender.getName() + " tried to use /mentionme volume, but there was an error!");
                        this.getLogger().info("Exception: " + e);
                    }
                    this.notificationVolume = Float.parseFloat(args[1]);
                }

            }else if(args[0].equalsIgnoreCase("pitch")){
                if(args.length != 2){
                    sendIncorrectUsageMessage(sender, "pitch");
                }else{
                    try{
                        this.notificationPitch = Float.parseFloat(args[1]);
                    } catch(Exception e){
                        try{
                            this.notificationPitch = NotePitch.valueOf(args[1].toUpperCase()).getPitch();
                        } catch(Exception e1){
                            sendIncorrectUsageMessage(sender, "pitch");
                            this.getLogger().info(sender.getName() + " tried to use /mentionme pitch, but there was an error!");
                            this.getLogger().info("Exception: " + e1);
                        }
                    }
                }

            }else if(args[0].equalsIgnoreCase("sound")){
                if(args.length != 2){
                    sendIncorrectUsageMessage(sender, "sound");
                }else{
                    try{
                        this.notificationSound = Sound.valueOf(args[1].toUpperCase());
                    } catch (Exception e){
                        sendIncorrectUsageMessage(sender, "sound");
                        this.getLogger().info(sender.getName() + " tried to use /mentionme sound, but there was an error!");
                        this.getLogger().info("Exception: " + e);
                    }
                }
            }else if(args[0].equalsIgnoreCase("color")){
                if(args.length != 2){
                    sendIncorrectUsageMessage(sender, "color");
                }else{
                    try{
                        this.notificationColor = ChatColor.valueOf(args[1].toUpperCase());
                    } catch (Exception e){
                        sendIncorrectUsageMessage(sender, "color");
                        this.getLogger().info(sender.getName() + " tried to use /mentionme color, but there was an error!");
                        this.getLogger().info("Exception: " + e);
                    }
                }
            }
        }
        if(sender instanceof Player){
            Player player = (Player) sender;
            player.sendMessage("There you go, " + this.notificationColor + "" + ChatColor.BOLD + player.getDisplayName() + "!");
            player.playSound(player.getLocation(), this.notificationSound, this.notificationVolume, this.notificationPitch);
        }
        return true;
    }

    public void sendIncorrectUsageMessage(CommandSender sender, String command){
        sender.sendMessage("Incorrect Usage!");
        switch(command){
            case "info":
                sender.sendMessage("/mentionme info");
                break;
            case "volume":
                sender.sendMessage("/mentionme volume <0.0-1.0>");
                break;
            case "pitch":
                sender.sendMessage("/mentionme pitch <#.# OR notename>");
                break;
            case "sound":
                sender.sendMessage("/mentionme sound <BUKKIT_SOUND>");
                break;
            case "color":
                sender.sendMessage("/mentionme color <CHATCOLOR>");
            case "all":
                sender.sendMessage("/mentionme info");
                sender.sendMessage("/mentionme volume <0.0-1.0>");
                sender.sendMessage("/mentionme pitch <#.# OR notename>");
                sender.sendMessage("/mentionme sound <BUKKIT_SOUND>");
                sender.sendMessage("/mentionme color <CHATCOLOR>");
                break;

        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        event.setCancelled(true);
        HashSet<Player> recievedMessage = new HashSet<>();

        for (Player player : Bukkit.getOnlinePlayers()) {
            // If the player has already received a message, skip them
            if(recievedMessage.contains(player))
                break;

            // This player's about to get a message, so add them to the receivedMessage Set
            recievedMessage.add(player);

            // If the message contains the player's name...
            // Send them a formatted message with all instances of their name highlights
            if (event.getMessage().toLowerCase().contains(player.getDisplayName().toLowerCase())) {
                String message = event.getMessage();

                // Uses regex to find all instances of the player's name in the message, regardless of capitalization
                message = message.replaceAll("(?i)"+player.getDisplayName(), this.notificationColor + "" + ChatColor.BOLD + player.getDisplayName() + ChatColor.RESET);
                player.sendMessage(String.format(event.getFormat(), event.getPlayer().getDisplayName(), message));
                player.playSound(player.getEyeLocation(), this.notificationSound, this.notificationVolume, this.notificationPitch);

            }else {
                // If their name's not in the message, just send them the original message
                player.sendMessage(String.format(event.getFormat(), event.getPlayer().getDisplayName(), event.getMessage()));
            }
        }
    }
}