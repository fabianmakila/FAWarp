package fi.fabianadrian.fawarp.command.commands;

import fi.fabianadrian.fawarp.FAWarp;
import fi.fabianadrian.fawarp.command.FAWarpCommand;
import fi.fabianadrian.fawarp.command.parser.WarpParser;
import fi.fabianadrian.fawarp.warp.Warp;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.incendo.cloud.Command;
import org.incendo.cloud.bukkit.data.Selector;
import org.incendo.cloud.bukkit.parser.selector.MultiplePlayerSelectorParser;
import org.incendo.cloud.context.CommandContext;

import java.util.Collection;

public final class WarpCommand extends FAWarpCommand {
	public WarpCommand(FAWarp plugin) {
		super(plugin);
	}

	@Override
	public void register() {
		Command.Builder<CommandSender> builder = this.manager.commandBuilder("warp").permission("fawarp.command.warp").required("warp", WarpParser.warpParser());
		this.manager.command(builder.senderType(Player.class).handler(this::warpHandler));
		this.manager.command(builder.required("entity", MultiplePlayerSelectorParser.multiplePlayerSelectorParser()).permission("fawarp.command.warp.other").handler(this::warpOtherHandler));
	}

	private void warpHandler(CommandContext<Player> context) {
		Warp warp = context.get("warp");
		context.sender().teleport(warp.location());
		context.sender().sendMessage(Component.translatable("fawarp.command.warp", warp.nameAsComponent()));
	}

	private void warpOtherHandler(CommandContext<CommandSender> context) {
		Warp warp = context.get("warp");
		Selector<Player> selector = context.get("entity");

		Collection<Player> players = selector.values();
		players.forEach(player -> {
			player.teleport(warp.location());
			player.sendMessage(Component.translatable("fawarp.command.warp", warp.nameAsComponent()));
		});

		Component message;
		if (players.size() > 1) {
			message = Component.translatable("fawarp.command.warp.player.multiple", Component.text(players.size()), warp.nameAsComponent());
		} else {
			Entity entity = players.iterator().next();
			message = Component.translatable("fawarp.command.warp.player.single", entity.name(), warp.nameAsComponent());
		}

		context.sender().sendMessage(message);
	}
}
