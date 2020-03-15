package me.screwage.MentionMe.Resources;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MentionMeTabCompleter implements TabCompleter {
    private ArrayList<String> baseCommands;
    private ArrayList<String> soundValues;
    private ArrayList<String> noteValues;
    private ArrayList<String> colorValues;

    public MentionMeTabCompleter(){
        baseCommands = new ArrayList<>();
        baseCommands.add("pitch");
        baseCommands.add("volume");
        baseCommands.add("sound");
        baseCommands.add("color");
        baseCommands.add("info");

        soundValues = new ArrayList<>();
        for(Sound sound : Sound.values())
            soundValues.add(sound.toString());

        noteValues = new ArrayList<>();
        for(NotePitch note : NotePitch.values())
            noteValues.add(note.toString());

        colorValues = new ArrayList<>();
        for(ChatColor color : ChatColor.values())
            colorValues.add(color.name());
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {

        if(command.getName().equalsIgnoreCase("mentionme")){
            if(args.length == 1){
                ArrayList<String> list = new ArrayList<>();
                for(String cmd : baseCommands){
                    if(cmd.startsWith(args[0].toLowerCase()))
                        list.add(cmd);
                }
                return list;
            }else if(args.length == 2 && args[0].equalsIgnoreCase("sound")){
                ArrayList<String> list = new ArrayList<>();
                for(String sound : this.soundValues){
                    if(sound.contains(args[1].toUpperCase()))
                        list.add(sound);
                }
                return list;
            }else if(args.length == 2 && args[0].equalsIgnoreCase("pitch")){
                ArrayList<String> list = new ArrayList<>();
                for(String note : this.noteValues){
                    if(note.startsWith(args[1].toUpperCase()))
                        list.add(note);
                }
                return list;
            }else if(args.length == 2 && args[0].equalsIgnoreCase("color")){
                ArrayList<String> list = new ArrayList<>();
                for(String color : this.colorValues){
                    if(color.contains(args[1].toUpperCase()))
                        list.add(color);
                }
                return list;
            }
        }

        return null;
    }
}
