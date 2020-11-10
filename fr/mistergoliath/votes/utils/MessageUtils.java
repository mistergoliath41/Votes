package fr.mistergoliath.votes.utils;

import java.io.File;

import org.bukkit.entity.Player;

import fr.mistergoliath.votes.utils.managers.ConfigManager;

public class MessageUtils {
	
	private static ConfigManager defaultConfig = new ConfigManager(new File("config.yml"));

	public static void sendFormatedMessage(Player p, String s) {
		p.sendMessage((defaultConfig.getConfig().getString("messages.prefix") + s).replace("&", "�"));
	}
	
}
