package dev.tom.customtnt;

import de.dustplanet.util.SilkUtil;
import de.exlll.configlib.*;
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
import java.nio.file.Path;

public final class CustomTNT extends JavaPlugin {

    public static ConfigurableTNT tntSettings;
    private static CustomTNT instance;
    private static SilkUtil silkUtil;

    @Override
    public void onLoad() {
        CommandAPI.onLoad(new CommandAPIBukkitConfig(this).silentLogs(true));
        new TntCommands().registerCommands(this);
    }

    @Override
    public void onEnable() {
        instance = this;
        CommandAPI.onEnable();
        silk();

        reloadConfigurationFiles();

        // Load all TNT items
        new TntBuilder(this).buildAllItems();

        // Register events
        getServer().getPluginManager().registerEvents(new TntExplosionListeners(), this);
        getServer().getPluginManager().registerEvents(new TntPlaceListeners(), this);
        getServer().getPluginManager().registerEvents(new TntPrimedListeners(), this);
    }

    private void silk(){
        silkUtil = SilkUtil.hookIntoSilkSpanwers();
    }

    public void reloadConfigurationFiles() {
        YamlConfigurationProperties props = ConfigLib.BUKKIT_DEFAULT_PROPERTIES.toBuilder().header(
                """
               This is the configuration file for CustomTNT
                You can enable or disable TNT types and configure their ItemStack properties
               """
        ).setNameFormatter(NameFormatters.LOWER_KEBAB_CASE).build();
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

    public static SilkUtil getSilkUtil() {
        return silkUtil;
    }
}