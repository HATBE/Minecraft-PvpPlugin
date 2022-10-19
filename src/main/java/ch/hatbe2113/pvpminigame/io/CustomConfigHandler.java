package ch.hatbe2113.pvpminigame.io;

import ch.hatbe2113.pvpminigame.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class CustomConfigHandler extends ConfigHandler {
    private File configFile;
    private String name;

    public CustomConfigHandler(Main main, String name)  {
        super(main);

        this.name = name;

        configFile = new File(main.getDataFolder(), name + ".yaml");
        config = YamlConfiguration.loadConfiguration(configFile);

        save();
    }

    @Override
    public void save() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            Bukkit.getLogger().warning(String.format("%s Failed to save Customconfig: %s", Main.PLUGIN_NAME, name));
            e.printStackTrace();
        }
    }
}
