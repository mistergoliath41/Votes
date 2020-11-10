package fr.mistergoliath.votes.commands;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.mistergoliath.votes.utils.MessageUtils;
import fr.mistergoliath.votes.utils.RpgAPI;
import fr.mistergoliath.votes.utils.managers.VoteManager;
import fr.mistergoliath.votes.Main;
import fr.mistergoliath.votes.listeners.PlayerListener;

public class Votes implements CommandExecutor {

	private Main main;
	private RpgAPI rapi;

	public Votes(Main main) {
		this.main = main;
		rapi = new RpgAPI(getConfig().getString("votes.server_name"));
	}

	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if (cs instanceof Player) {
			Player p = (Player)cs;
			if (args.length >= 1) {
				switch (args[0].toLowerCase()) {
					case "reset":
						if (!p.hasPermission("votes.reset")) {
							MessageUtils.sendFormatedMessage(p, getConfig().getString("messages.permission_denied"));
							return false;
						}
						if (args.length == 2 && Bukkit.getServer().getPlayer(args[1]) != null) {
							Player t = Bukkit.getServer().getPlayer(args[1]);
							getVoteManager().resetTime(t);
							MessageUtils.sendFormatedMessage(p, getConfig().getString("messages.vote_time_reset").replace("%player%", t.getName()));
							MessageUtils.sendFormatedMessage(t, getConfig().getString("messages.you_can_now_vote"));
							return true;
						}
						break;
		
					default:
						if (args.length == 1 && p.hasPermission("votes.use")) {
							if (!getVoteManager().canVote(p)) {
								try {
									if (Integer.parseInt(args[0]) == rapi.getOut()) {
										getVoteManager().vote(p);
									} else {
										MessageUtils.sendFormatedMessage(p, getConfig().getString("messages.wrong_out"));
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
								return true;
							} else {
								MessageUtils.sendFormatedMessage(p, getConfig().getString("messages.you_cant_vote").replace("%time%", getVoteManager().getFormattedTime(getVoteManager().getTimeRemaining(p))));
								return true;
							}
						}
						break;
				}
			} else {
				if (!p.hasPermission("votes.use")) {
					MessageUtils.sendFormatedMessage(p, getConfig().getString("messages.permission_denied"));
					return false;
				}
				Inventory inv = Bukkit.createInventory(null, 9, "§eVotes");
				ItemStack is = new ItemStack(Material.EXP_BOTTLE);
				ItemMeta im = is.getItemMeta();
				im.setDisplayName("§6RPG Paradize");
				is.setItemMeta(im);
				inv.setItem(1, is);
				p.openInventory(inv);
				return true;
			}
		}
		return false;
	}
	
	public Votes vote(Player p) {
		if (!getVoteManager().canVote(p)) {
			getVoteManager().vote(p);
			return this;
		} else {
			MessageUtils.sendFormatedMessage(p, getConfig().getString("messages.you_cant_vote").replace("%time%", getVoteManager().getFormattedTime(getVoteManager().getTimeRemaining(p))));
			return this;
		}
	}
	
	public VoteManager getVoteManager() {
		return this.main.getVoteManager();
	}
	
	private FileConfiguration getConfig() {
		return this.main.getConfig();
	}

}
