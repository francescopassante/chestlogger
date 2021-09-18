<h1>ChestLogger Plugin</h1>
<h3>Introduction</h3>

This plugin lets players consult a log of their protected chests. By default, every chest that a player places is considered "protected".

When performing the /chests command, a GUI will open up with all the protected chests and the info about them. If another player destroys one of the player's protected chests, additional info is displayed in the /chests GUI (it will show who destroyed their chest).

 

The plugin is made to track not only the manual break of a chest (by hand or with a tool) but also to track TNT explosion (it will show up the name of the player who placed and lit the TNT).

 

This plugin also comes with a completely configurable config.yml file (messages, chests info and formatting are completely customizable) and a few extra commands to manage it.

 

The GUI also supports multiple pages and there is no need for a database, the storage is handled in the local chests.yml file. (Database support will be released soon)

 
<h3>Commands</h3>

    /chests -> Opens up the chests GUI with all the info of the protected chests
    /chestlogger reload -> Reloads the config.yml file, without the need to reload all the server
    /chestlogger clear -> Clears the list of the player's protected and logged chests
    /chestlogger clear <player> -> Clears the list of another player's protected and logged chests

<h3>Permissions</h3>

 

    chestlogger.chests -> Lets the player perform /chests
    chestlogger.reload -> Lets the player reload the configuration file (config.yml) with the /chestlogger reload command
    chestlogger.clear.self -> Lets the player clear his list of logged chests with the /chestlogger clear commnad
    chestlogger.clear.other -> Lets the player clear other players' lists of logged chests with the /chestlogger clear <player> command


<h3>Default config.yml file</h3>

     messages:
      # This prefix will show before every message sent by the ChestLogger plugin
      prefix: "§f[Chest§aLogger§f] "
      no-permission: "§cNo permission"
      # This is the message that will be sent when someone places a chest. Only if "send-message-on-chest-placed" is set to true
      chest-placed-message: "§fChestLogger will now log your chest!"
      cleared-own-chests: "§fSuccesfully cleared your logged chests list!"
      cleared-other-chests: "§fSuccesfully cleared §a%target§f's chests! "
      no-chests-to-clear: "§cThere are no chests to clear in the chest.yml file"
      no-such-player: "§a %target §cis not online!"
      config-reloaded: "The config has been §asuccesfully §freloaded!"

    chests:
      # This is the title of the /chests GUI
      inventory-title: "§a%owner§f's chests"
      # This is the display name of the chests inside the /chests GUI
      display-name: "§aProtected §fchest"
      # These are the settings for the data that shows under every chest when performing the /chests command (the placeholders are %x, %y, %z, %world for the four lines and %destroyer for the destroyed-by-line setting)
      first-line: "§aX:§f %x"
      second-line: "§aY:§f %y"
      third-line: "§aZ:§f %z"
      fourth-line: "§aWorld:§f %world"
      destroyed-by-line: "§cDestroyed by:§f %destroyer"


    settings:
    # If set to true, the player will receive the "chest-placed-message" when placing a chest
      send-message-on-chest-placed: true
