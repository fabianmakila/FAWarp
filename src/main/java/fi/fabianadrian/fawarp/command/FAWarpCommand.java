package fi.fabianadrian.fawarp.command;

import fi.fabianadrian.fawarp.FAWarp;
import org.bukkit.command.CommandSender;
import org.incendo.cloud.paper.PaperCommandManager;

public abstract class FAWarpCommand {
	protected final FAWarp plugin;
	protected final PaperCommandManager<CommandSender> manager;

	public FAWarpCommand(FAWarp plugin) {
		this.plugin = plugin;
		this.manager = plugin.commandManager();
	}

	public abstract void register();
}
