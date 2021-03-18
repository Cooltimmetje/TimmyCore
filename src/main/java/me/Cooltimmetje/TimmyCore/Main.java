package me.Cooltimmetje.TimmyCore;

import github.scarsz.discordsrv.DiscordSRV;
import me.Cooltimmetje.TimmyCore.Commands.NicknameCommand;
import me.Cooltimmetje.TimmyCore.Commands.PronounsCommand;
import me.Cooltimmetje.TimmyCore.Data.Database.HikariManager;
import me.Cooltimmetje.TimmyCore.Data.Profiles.User.ProfileManager;
import me.Cooltimmetje.TimmyCore.Data.Profiles.User.Settings.Setting;
import me.Cooltimmetje.TimmyCore.Listeners.*;
import me.Cooltimmetje.TimmyCore.Packages.Discord.DiscordReady;
import me.Cooltimmetje.TimmyCore.Packages.Npcs.NpcManager;
import me.Cooltimmetje.TimmyCore.Packages.Rank.RankCommand;
import me.Cooltimmetje.TimmyCore.Packages.Warp.WarpCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static final ProfileManager pm = ProfileManager.getInstance();
    private static final DiscordReady discordReadyListener = new DiscordReady();

    private static Plugin plugin;

    @Override
    public void onEnable() {
        plugin = this;
        getLogger().info("Loading...");

        getLogger().info("Loading config..."); //TODO: make cleaner
        this.saveDefaultConfig();
        String databaseHost = this.getConfig().getString("mysql.host");
        String databasePort = this.getConfig().getString("mysql.port");
        String databaseName = this.getConfig().getString("mysql.database");
        String databaseUser = this.getConfig().getString("mysql.username");
        String databasePass = this.getConfig().getString("mysql.password");

        getLogger().info("Setting up DB connection...");
        HikariManager.setup(databaseHost, databasePort, databaseName, databaseUser, databasePass);

        getLogger().info("Updating database...");
        Setting.saveToDatabase();

        getLogger().info("Registering listeners...");
        registerEvent(new DeathListener(), new JoinQuitListener(), new ChatListener(), new ServerPingListener(), new EntityExplodeListener());
        DiscordSRV.api.subscribe(discordReadyListener);

        getLogger().info("Registering commands");
        getCommand("warp").setExecutor(new WarpCommand());
        getCommand("nick").setExecutor(new NicknameCommand());
        getCommand("rank").setExecutor(new RankCommand());
        getCommand("pronouns").setExecutor(new PronounsCommand());

        getLogger().info("Loading players...");
        for(Player p : Bukkit.getOnlinePlayers())
            pm.getUser(p); //We just need to load here, nothing else.

        getLogger().info("Spawning NPC's...");
        NpcManager.getInstance().spawnAll();

//        getLogger().info("Registering crafting recipes...");
//        new GoldSmelting(getServer(), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("Shutting down...");
        HikariManager.close();
        pm.unload();
        DiscordSRV.api.unsubscribe(discordReadyListener);
        NpcManager.getInstance().despawnAll();
    }

    public void registerEvent(Listener... listeners){
        for(Listener listener : listeners)
            registerEvent(listener);
    }

    public void registerEvent(Listener listener){
        getLogger().info("Registering listener " + listener.toString());
        getServer().getPluginManager().registerEvents(listener, this);
    }

    public static Plugin getPlugin(){
        return plugin;
    }




}
