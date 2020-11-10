package fr.mistergoliath.votes.utils.managers;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import fr.mistergoliath.votes.Main;

public class ConfigManager {

	private File configFile;
	private YamlConfiguration yamlConfig;
	
	public ConfigManager(File configFile) {
		this.configFile = configFile;
	}
	
	public ConfigManager createConfig() {
		configFile = new File(getDataFolder() + File.separator + configFile.getName());
		if (!configFile.exists()) {
			try {
				configFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return this;
	}
	
	public YamlConfiguration getConfig() {
		configFile = new File(getDataFolder() + File.separator + configFile.getName());
		yamlConfig = YamlConfiguration.loadConfiguration(configFile);
		return yamlConfig;
	}
	
	public <T> ConfigManager set(String valueLocation, T value) {
		try {
			configFile = new File(getDataFolder() + File.separator + configFile.getName());
			yamlConfig = YamlConfiguration.loadConfiguration(configFile);
			yamlConfig.set(valueLocation, value);
			yamlConfig.save(configFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}
	
	public <T> List<ItemStack> getList(String valueLocation) {
		try {
			configFile = new File(getDataFolder() + File.separator + configFile.getName());
			yamlConfig = YamlConfiguration.loadConfiguration(configFile);
			return (List<ItemStack>)yamlConfig.getList(valueLocation);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String getDataFolder() {
		String name = null;
		Scanner sc = null;
		try {
			sc = new Scanner(getClass().getResourceAsStream("/plugin.yml"));
			while (sc.hasNext()) {
				String s = sc.nextLine();
				if (s != null && s.startsWith("name:")) {
					name = s.split(" ")[1];
				}
			}
			return Bukkit.getPluginManager().getPlugin(name).getDataFolder().getAbsolutePath();
		} catch (Exception e) {
			return null;
		} finally {
			sc.close();
		}
	}

}
