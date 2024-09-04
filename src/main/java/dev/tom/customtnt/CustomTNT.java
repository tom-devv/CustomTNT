package dev.tom.customtnt;

import de.exlll.configlib.ConfigLib;
import de.exlll.configlib.YamlConfigurationProperties;
import de.exlll.configlib.YamlConfigurations;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import dev.tom.customtnt.commands.TntCommands;
import dev.tom.customtnt.files.ConfigurableTNT;
import dev.tom.customtnt.listeners.TntExplosionListeners;
import dev.tom.customtnt.listeners.TntPlaceListeners;
import dev.tom.customtnt.listeners.TntPrimedListeners;
import dev.tom.customtnt.tnt.TntBuilder;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public final class CustomTNT extends JavaPlugin {

    public static ConfigurableTNT tntSettings;
    private static CustomTNT instance;

    @Override
    public void onLoad() {
        CommandAPI.onLoad(new CommandAPIBukkitConfig(this).silentLogs(true));
        new TntCommands().registerCommands(this);
    }

    @Override
    public void onEnable() {
        instance = this;
        CommandAPI.onEnable();

        try {
            loadConfig();
        }catch (IOException e){
            getLogger().severe("Failed to load config file: " + e.getMessage());
        }


        // Load all TNT items
        new TntBuilder(this).buildAllItems();

        // Register events
        getServer().getPluginManager().registerEvents(new TntExplosionListeners(), this);
        getServer().getPluginManager().registerEvents(new TntPlaceListeners(), this);
        getServer().getPluginManager().registerEvents(new TntPrimedListeners(), this);

    }

    public void loadConfig() throws IOException {
        YamlConfigurationProperties props = ConfigLib.BUKKIT_DEFAULT_PROPERTIES.toBuilder().header(
                """
               This is the configuration file for CustomTNT
                You can enable or disable TNT types and configure their ItemStack properties
               """
        )
                .build();
        Path configFile = new File(getDataFolder(), "tnt-settings.yml").toPath();

        tntSettings = YamlConfigurations.update(
                configFile,
                ConfigurableTNT.class,
                props
        );
    }

    @Override
    public void onDisable() {
        CommandAPI.onDisable();
    }

    public static ConfigurableTNT getTntSettings() {
        return tntSettings;
    }

    public static CustomTNT getInstance() {
        return instance;
    }
}