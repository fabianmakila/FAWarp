package fi.fabianadrian.fawarp.command.commands;

import fi.fabianadrian.fawarp.FAWarp;
import fi.fabianadrian.fawarp.command.FAWarpCommand;
import fi.fabianadrian.fawarp.util.ComponentUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.incendo.cloud.Command;
import org.incendo.cloud.bukkit.parser.location.LocationParser;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.parser.standard.StringParser;

public final class SetWarpCommand extends FAWarpCommand {
	public SetWarpCommand(FAWarp plugin) {
		super(plugin);
	}

	@Override
	public void register() {
		Command.Builder<CommandSender> setBuilder = this.manager.commandBuilder("setwarp").permission("fawarp.command.setwarp").required("name", StringParser.stringParser());
		this.manager.command(setBuilder.senderType(Player.class).handler(this::setHandler));
		this.manager.command(setBuilder.required("location", LocationParser.locationParser()).handler(this::setCoordinateHandler));
	}

	private void setHandler(CommandContext<Player> context) {
		Player sender = context.sender();
		String warpName = context.get("name");

		Location previousLocation = this.plugin.warpManager().set(warpName, sender.getLocation());

		if (previousLocation != null) {
			sender.sendMessage(Component.translatable("fawarp.command.setwarp.updated").arguments(Component.text(warpName), ComponentUtils.locationComponent(previousLocation), ComponentUtils.locationComponent(sender.getLocation())));
		} else {
			sender.sendMessage(Component.translatable("fawarp.command.setwarp").arguments(Component.text(warpName), ComponentUtils.locationComponent(sender.getLocation())));
		}
	}

	private void setCoordinateHandler(CommandContext<CommandSender> context) {
		Location location = context.get("location");
		String warpName = context.get("name");

		Location previousLocation = this.plugin.warpManager().set(warpName, location);
		if (context.sender() instanceof Player player) {
			player.teleport(location);
		}

		if (previousLocation != null) {
			context.sender().sendMessage(Component.translatable("fawarp.command.setwarp.update").arguments(Component.text(warpName), ComponentUtils.locationComponent(previousLocation), ComponentUtils.locationComponent(location)));
		} else {
			context.sender().sendMessage(Component.translatable("fawarp.command.setwarp").arguments(Component.text(warpName), ComponentUtils.locationComponent(location)));
		}
	}
}
