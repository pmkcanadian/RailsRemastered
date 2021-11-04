package com.pmkcanadian.mc.RailsRemastered;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


public class RailsRemastered extends JavaPlugin {
	private static final Configuration CONFIGURATION = new Configuration();
	
	public static Configuration getConfiguration () {
		return CONFIGURATION;
	}
	
	private static Set<CommandSender> receivedHeadsUp = new HashSet<>();
	
	
	public void onEnable() {
		saveDefaultConfig();
		Logger logger = getLogger();
		
		logger.info("Reading config");
		CONFIGURATION.readConfig(getConfig(), logger);
		
		logger.info("Registering event listener");
		PluginManager pm = getServer().getPluginManager();
	    pm.registerEvents(new MinecartListener(CONFIGURATION.getBoostBlock(), CONFIGURATION.isCheatMode()), (Plugin)this);
	}
	
	
	public void onDisable() {
		   getLogger().info("unloading...");
	  }
	
	
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("hsrails")) {
			if (sender instanceof Player) {
				Player player = (Player)sender;
				if (!player.hasPermission("RailsRemastered.cmd")) {
					player.sendMessage(ChatColor.DARK_RED + "You don't have permission to use this command");
					return true;
			}
		}
		
			try {
			CONFIGURATION.setSpeedMultiplier(Double.parseDouble(args[0]));
			} 
			catch (Exception ignore) {
				sender.sendMessage(ChatColor.DARK_RED + "multiplier should be a number");
				return false;
			}
			
			double speedMultiplier = CONFIGURATION.getSpeedMultiplier();
			if (speedMultiplier > 0.0D && speedMultiplier <= 8.0D) {
		        String message = ChatColor.AQUA + "Speed multiplier set to: " + speedMultiplier;
		        String headsUp = ChatColor.YELLOW + "\nNote: multiplier set to more than 4x. Servers often struggle to provide max speeds above 4x, and the carts may appear to be capped at 4x. However, carts will still have their momentum increased, meaning they will coast for longer.";
		        
		        
		        
		        
		        boolean sendHeadsUp = (!receivedHeadsUp.contains(sender) && speedMultiplier > 4.0D);
		        sender.sendMessage(String.format("%s%s", new Object[] { message, sendHeadsUp ? headsUp : "" }));
		        if (sendHeadsUp)
		          receivedHeadsUp.add(sender); 
		        
		        return true;
		      } 
			
		      sender.sendMessage(ChatColor.RED + "multiplier must be greater than 0 and max 8");
		      return true;
		    } 
		
		    return false;
		  }
		}
