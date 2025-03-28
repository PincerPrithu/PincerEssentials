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

### Inventory Viewing Commands  
- **/openinv [player]**: View and edit another player's inventory.  
  - **Permission**: `essentials.openinv`  
- **/openender [player]**: View and edit another player's ender chest.  
  - **Permission**: `essentials.openender`  

### Teleport Request Commands  
- **/tpa [player]**: Send a teleport request to another player.  
  - **Permission**: `essentials.tpa`  
- **/tpaccept**: Accept a pending teleport request.  
  - **Permission**: `default`  

### Additional Ephemeral Commands  
- **/vanish**: Toggle vanish mode.  
  - **Permission**: `essentials.vanish`  
- **/speed [walk|fly] <0-10> [player]**: Adjust walk or fly speed.  
  - **Permission**: `essentials.speed`  
- **/fly [player]**: Toggle flight mode.  
  - **Permission**: `essentials.fly`  
- **/kill <player>**: Instantly kill a player.  
  - **Permission**: `essentials.kill`  
- **/feed [player]**: Replenish hunger.  
  - **Permission**: `essentials.feed`  
- **/heal [player]**: Restore health and saturation.  
  - **Permission**: `essentials.heal`  
- **/top**: Teleport to the highest block at your current location.  
  - **Permission**: `essentials.top`  
- **/jump**: Jump to the nearest solid block in your line of sight.  
  - **Permission**: `essentials.jump`  
- **/ping**: Check your latency.  
  - **Permission**: `essentials.ping`  
- **/me <message...>**: Broadcast an action message.  
  - **Permission**: `essentials.me`  
- **/broadcast <message...>**: Broadcast a message server-wide.  
  - **Permission**: `essentials.broadcast`  
- **/tptoggle**: Toggle receiving teleport requests.  
  - **Permission**: `essentials.tptoggle`  
- **/lightning [player]**: Strike lightning at a player's location.  
  - **Permission**: `essentials.lightning`  
- **/ice <player>**: Freeze a player (simulate freeze effect).  
  - **Permission**: `essentials.ice`  
- **/item <material> [amount]**: Give yourself an item.  
  - **Permission**: `essentials.item`  
- **/clearinventory [player]**: Clear a player's inventory.  
  - **Permission**: `essentials.clearinventory`  
- **/fireball**: Launch a fireball.  
  - **Permission**: `essentials.fireball`  
- **/depth**: Display your current Y-level.  
  - **Permission**: `essentials.depth`  

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

### Inventory Viewing Commands  
- **/openinv [player]**: View and edit another player's inventory.  
- **/openender [player]**: View and edit another player's ender chest.  

### Teleport Request Commands  
- **/tpa [player]**: Send a teleport request to another player.  
- **/tpaccept**: Accept a pending teleport request.  

## License  

This project is licensed under the MIT License. See the `LICENSE` file for details.
