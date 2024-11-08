package fi.fabianadrian.fawarp.command.commands;

import fi.fabianadrian.fawarp.FAWarp;
import fi.fabianadrian.fawarp.command.FAWarpCommand;
import fi.fabianadrian.fawarp.warp.Warp;
import net.kyori.adventure.text.TranslatableComponent;
import org.bukkit.command.CommandSender;
import org.incendo.cloud.Command;
import org.incendo.cloud.context.CommandContext;

import static fi.fabianadrian.fawarp.command.parser.WarpParser.warpParser;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.translatable;

public final class UnsetWarpCommand extends FAWarpCommand {
	private static final TranslatableComponent COMPONENT_UNSETWARP = translatable("fawarp.command.unsetwarp");

	public UnsetWarpCommand(FAWarp plugin) {
		super(plugin);
	}

	@Override
	public void register() {
		Command.Builder<CommandSender> builder = this.manager.commandBuilder("unsetwarp").permission("fawarp.command.unsetwarp");
		this.manager.command(builder.required("warp", warpParser()).handler(this::unsetHandler));
	}

	private void unsetHandler(CommandContext<CommandSender> context) {
		Warp warp = context.get("warp");
		this.plugin.warpManager().unset(warp);
		context.sender().sendMessage(COMPONENT_UNSETWARP.arguments(text(warp.name())));
	}
}
