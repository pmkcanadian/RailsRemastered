package com.pmkcanadian.mc.RailsRemastered;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Minecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleMoveEvent;

public class MinecartListener implements Listener {
  
	private static final double BUKKIT_SPEED_MULTIPLIER = 0.4D;
  
	private final Material boostBlock;
  
	private final boolean isCheatMode;
  
	public MinecartListener(Material boostBlock, boolean isCheatMode) {
    this.boostBlock = boostBlock;
    this.isCheatMode = isCheatMode;
  }
  
  @EventHandler(priority = EventPriority.NORMAL)
  public void onVehicleMove(VehicleMoveEvent event) {
    
	  if (event.getVehicle() instanceof Minecart) {
		  Minecart cart = (Minecart)event.getVehicle();
		  Location cartLocation = cart.getLocation();
		  World cartsWorld = cart.getWorld();
		  
		  Block rail = cartsWorld.getBlockAt(cartLocation);
		  Block blockBelow = cartsWorld.getBlockAt(cartLocation.add(0.0D, -1.0D, 0.0D));
		  
		  if (rail.getType() == Material.POWERED_RAIL)
			  if (this.isCheatMode || blockBelow.getType() == this.boostBlock) {
				  cart.setMaxSpeed(0.4D * RailsRemastered.getConfiguration().getSpeedMultiplier());
				  } else {
          
					  cart.setMaxSpeed(0.4D);
        }  
    } 
  }
}
