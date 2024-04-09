package fi.fabianadrian.fawarp.command.commands;

import fi.fabianadrian.fawarp.FAWarp;
import fi.fabianadrian.fawarp.command.FAWarpCommand;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.incendo.cloud.Command;
import org.incendo.cloud.context.CommandContext;

public final class RootCommand extends FAWarpCommand {
	public RootCommand(FAWarp plugin) {
		super(plugin);
	}

	@Override
	public void register() {
		Command.Builder<CommandSender> builder = this.manager.commandBuilder("fawarp");
		this.manager.command(builder.literal("reload").permission("fawarp.command.root.reload").handler(this::reloadHandler));
	}

	private void reloadHandler(CommandContext<CommandSender> context) {
		this.plugin.reload();
		context.sender().sendMessage(Component.translatable("fawarp.command.root.reload"));
	}
}
