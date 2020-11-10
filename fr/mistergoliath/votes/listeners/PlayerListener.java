package fr.mistergoliath.votes.listeners;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.mistergoliath.votes.Main;
import fr.mistergoliath.votes.utils.MessageUtils;
import fr.mistergoliath.votes.utils.managers.VoteManager;
import net.md_5.bungee.api.chat.TextComponent;

public class PlayerListener implements Listener {

	private Main main;

	public PlayerListener(Main main) {
		this.main = main;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInventoryClick(InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player && e.getInventory().getName().equals("§eVotes")) {
			Player p = (Player)e.getWhoClicked();
			if (p.hasPermission("votes.use")) {
				if (e.getCurrentItem() != null && e.getCurrentItem().getItemMeta().getDisplayName().startsWith("§")) {
					e.setCancelled(true);
					ItemStack is = e.getCurrentItem();
					ItemMeta im = is.getItemMeta();
					String name = im.getDisplayName().substring((countChars(im.getDisplayName(), '§') * 2));
					switch (name.toLowerCase()) {
						case "rpg paradize":
							MessageUtils.sendFormatedMessage(p, getConfig().getString("messages.click_here_to_vote").replace("%url%", getConfig().getString("vote_url.rpg_paradize"))
															 + "\n" + getConfig().getString("messages.type_vote_command"));
							break;
					}
				}
			}
		}
	}
	
	public int countChars(String str, char chr) {
		int count = 0;
		for (char c : str.toCharArray()) {
			if (c == chr) {
				count++;
			}
		}
		return count;
	}
	
	private FileConfiguration getConfig() {
		return this.main.getConfig();
	}

}
