package fi.fabianadrian.fawarp.command.commands;

import fi.fabianadrian.fawarp.FAWarp;
import fi.fabianadrian.fawarp.command.FAWarpCommand;
import fi.fabianadrian.fawarp.command.parser.WarpParser;
import fi.fabianadrian.fawarp.warp.Warp;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.incendo.cloud.Command;
import org.incendo.cloud.context.CommandContext;

public final class UnsetWarpCommand extends FAWarpCommand {
	public UnsetWarpCommand(FAWarp plugin) {
		super(plugin);
	}

	@Override
	public void register() {
		Command.Builder<CommandSender> builder = this.manager.commandBuilder("unsetwarp").permission("fawarp.command.unsetwarp");
		this.manager.command(builder.required("warp", WarpParser.warpParser()).handler(this::unsetHandler));
	}

	private void unsetHandler(CommandContext<CommandSender> context) {
		Warp warp = context.get("warp");
		this.plugin.warpManager().unset(warp);
		context.sender().sendMessage(Component.translatable("fawarp.command.unsetwarp").arguments(Component.text(warp.name())));
	}
}
