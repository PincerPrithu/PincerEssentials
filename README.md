# PincerEssentials Plugin  

## Overview  

Who needs 5 MB worth of commands just to play God on your own Minecraft server? PincerEssentials trims the fat, serving up only the essentials—no filler, just killer.  

PincerEssentials is a Minecraft plugin that provides a variety of essential commands and features for server management and player convenience. This plugin includes commands for game mode switching, god mode, item repair, trash inventory, inventory viewing, ender chest viewing, and teleport requests. Additionally, it supports customizable messages through a language manager.  

## Features  

### Game Mode Commands  
- **/gms**: Switch to survival mode.  
  - **Permission**: `gamemode.survival`  
- **/gmc**: Switch to creative mode.  
  - **Permission**: `gamemode.creative`  
- **/gmsp**: Switch to spectator mode.  
  - **Permission**: `gamemode.spectator`  
- **/gma**: Switch to adventure mode.  
  - **Permission**: `gamemode.adventure`  

### God Mode Command  
- **/god**: Toggle god mode (invulnerability).  
  - **Permission**: `essentials.godmode`  

### Item Repair Command  
- **/fix**: Repair the item in your hand.  
  - **Permission**: `essentials.fix`  

### Trash Command  
- **/trash**: Open a trash inventory to dispose of items.  
  - **Permission**: `essentials.trash`  

### Inventory Viewing Command  
- **/openinv [player]**: View and edit another player's inventory.  
  - **Permission**: `essentials.openinv`  

### Ender Chest Viewing Command  
- **/openender [player]**: View and edit another player's ender chest.  
  - **Permission**: `essentials.openender`  

### Teleport Request Commands  
- **/tpa [player]**: Send a teleport request to another player.  
  - **Permission**: `essentials.tpa`  
- **/tpaccept**: Accept a pending teleport request.  
  - **Permission**: `default`  

## Customizable Messages  

All messages sent by the plugin can be customized through the `messages.yml` file. This allows server administrators to tailor the plugin's messages to their preferences.  

### Example `messages.yml`  
```yaml  
# GodModeCommand messages  
god_mode_enabled: "God mode enabled."  
god_mode_disabled: "God mode disabled."  

# FixCommand messages  
item_repaired: "The item in your hand has been repaired."  

# TrashCommand messages  
trash_inventory_title: "Trash"  

# GMCommand messages  
gamemode_survival: "Your gamemode has been set to survival mode."  
gamemode_creative: "Your gamemode has been set to creative mode."  
gamemode_spectator: "Your gamemode has been set to spectator mode."  
gamemode_adventure: "Your gamemode has been set to adventure mode."  

# TPACommand messages  
tpa_request_sent: "Teleport request sent to {player}."  
tpa_request_received: "{player} has requested to teleport to you. Type /tpaccept to accept."  
tpa_request_accepted: "Teleport request accepted."  
tpa_teleporting: "Teleporting to {player}."  
tpa_no_request: "No pending teleport requests."  
tpa_player_not_found: "Player not found."  
```  

## Installation  

1. Download the PincerEssentials plugin jar file.  
2. Place the jar file in your server's `plugins` directory.  
3. Start or restart your server to load the plugin.  
4. Customize the `messages.yml` file in the `PincerEssentials` directory if desired.  

## Configuration  

### Currently only messages.yml are configurable.  

## Usage  

### Game Mode Commands  
- **/gms**: Switch to survival mode.  
- **/gmc**: Switch to creative mode.  
- **/gmsp**: Switch to spectator mode.  
- **/gma**: Switch to adventure mode.  

### God Mode Command  
- **/god**: Toggle god mode (invulnerability).  

### Item Repair Command  
- **/fix**: Repair the item in your hand.  

### Trash Command  
- **/trash**: Open a trash inventory to dispose of items.  

### Inventory Viewing Command  
- **/openinv [player]**: View and edit another player's inventory.  

### Ender Chest Viewing Command  
- **/openender [player]**: View and edit another player's ender chest.  

### Teleport Request Commands  
- **/tpa [player]**: Send a teleport request to another player.  
- **/tpaccept**: Accept a pending teleport request.  

## License  

This project is licensed under the MIT License. See the `LICENSE` file for details.  
