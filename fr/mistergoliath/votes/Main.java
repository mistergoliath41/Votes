package fr.mistergoliath.votes;

import java.io.File;
import java.io.IOException;

import org.bukkit.plugin.java.JavaPlugin;

import fr.mistergoliath.votes.commands.Votes;
import fr.mistergoliath.votes.listeners.PlayerListener;
import fr.mistergoliath.votes.utils.RpgAPI;
import fr.mistergoliath.votes.utils.managers.ConfigManager;
import fr.mistergoliath.votes.utils.managers.VoteManager;

public class Main extends JavaPlugin {
	
	private File defaultConfigFile;
	private VoteManager kv = new VoteManager();
	
	public static void main(String[] args) {
		RpgAPI rpgapi = new RpgAPI("pactify");
		try {
			System.out.println(rpgapi.getOut());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void onEnable() {
		this.defaultConfigFile = new File(getDataFolder().getAbsolutePath() + File.separator + "config.yml");
		if (!getDataFolder().exists()) getDataFolder().mkdirs();
		if (!getDefaultConfigFile().exists()) saveDefaultConfig();
		getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
		getCommand("vote").setExecutor(new Votes(this));
	}
	
	public File getDefaultConfigFile() {
		return this.defaultConfigFile;
	}
	
	public VoteManager getVoteManager() {
		return this.kv;
	}
	
}
