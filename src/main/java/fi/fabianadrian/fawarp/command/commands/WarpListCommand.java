package fi.fabianadrian.fawarp.command.commands;

import fi.fabianadrian.fawarp.FAWarp;
import fi.fabianadrian.fawarp.command.FAWarpCommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import org.bukkit.command.CommandSender;
import org.incendo.cloud.Command;
import org.incendo.cloud.context.CommandContext;

import java.util.StringJoiner;

public final class WarpListCommand extends FAWarpCommand {
	public WarpListCommand(FAWarp plugin) {
		super(plugin);
	}

	@Override
	public void register() {
		Command.Builder<CommandSender> builder = this.manager.commandBuilder("warplist", "warps").permission("fawarp.command.warplist");
		this.manager.command(builder.handler(this::listHandler));
	}

	private void listHandler(CommandContext<CommandSender> context) {
		Component header = Component.translatable("fawarp.command.warplist.header");

		StringJoiner joiner = new StringJoiner(", ");
		this.plugin.warpManager().warps().forEach(warp -> {
			if (!context.sender().hasPermission(warp.permission())) {
				return;
			}

			joiner.add(warp.name());
		});

		String availableWarps = joiner.toString();
		if (availableWarps.isBlank()) {
			context.sender().sendMessage(Component.translatable("fawarp.command.warplist.empty"));
			return;
		}

		context.sender().sendMessage(Component.join(JoinConfiguration.newlines(), header, Component.text(joiner.toString())));
	}
}
