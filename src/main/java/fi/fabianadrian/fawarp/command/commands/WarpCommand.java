package fi.fabianadrian.fawarp.command.commands;

import fi.fabianadrian.fawarp.FAWarp;
import fi.fabianadrian.fawarp.command.FAWarpCommand;
import fi.fabianadrian.fawarp.warp.Warp;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.incendo.cloud.Command;
import org.incendo.cloud.bukkit.data.Selector;
import org.incendo.cloud.context.CommandContext;

import java.util.Collection;

import static fi.fabianadrian.fawarp.command.parser.WarpParser.warpParser;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.translatable;
import static org.incendo.cloud.bukkit.parser.selector.MultiplePlayerSelectorParser.multiplePlayerSelectorParser;

public final class WarpCommand extends FAWarpCommand {
	private static final TranslatableComponent COMPONENT_WARP = translatable("fawarp.command.warp");
	private static final TranslatableComponent COMPONENT_MULTIPLE = translatable("fawarp.command.warp.player.multiple");
	private static final TranslatableComponent COMPONENT_SINGLE = translatable("fawarp.command.warp.player.single");

	public WarpCommand(FAWarp plugin) {
		super(plugin);
	}

	@Override
	public void register() {
		Command.Builder<CommandSender> builder = this.manager.commandBuilder("warp").permission("fawarp.command.warp").required("warp", warpParser());
		this.manager.command(builder.senderType(Player.class).handler(this::warpHandler));
		this.manager.command(builder.required("player", multiplePlayerSelectorParser()).permission("fawarp.command.warp.player").handler(this::warpOtherHandler));
	}

	private void warpHandler(CommandContext<Player> context) {
		Warp warp = context.get("warp");
		context.sender().teleport(warp.location());
		context.sender().sendMessage(COMPONENT_WARP.arguments(warp.nameAsComponent()));
	}

	private void warpOtherHandler(CommandContext<CommandSender> context) {
		Warp warp = context.get("warp");
		Selector<Player> selector = context.get("player");

		Collection<Player> players = selector.values();
		players.forEach(player -> {
			player.teleport(warp.location());
			player.sendMessage(COMPONENT_WARP.arguments(warp.nameAsComponent()));
		});

		Component message;
		if (players.size() > 1) {
			message = COMPONENT_MULTIPLE.arguments(text(players.size()), warp.nameAsComponent());
		} else {
			Entity entity = players.iterator().next();
			message = COMPONENT_SINGLE.arguments(entity.name(), warp.nameAsComponent());
		}

		context.sender().sendMessage(message);
	}
}
