package fi.fabianadrian.fawarp.command.commands;

import fi.fabianadrian.fawarp.FAWarp;
import fi.fabianadrian.fawarp.command.FAWarpCommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.CommandSender;
import org.incendo.cloud.Command;
import org.incendo.cloud.component.DefaultValue;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.help.result.CommandEntry;
import org.incendo.cloud.minecraft.extras.AudienceProvider;
import org.incendo.cloud.minecraft.extras.MinecraftHelp;
import org.incendo.cloud.parser.standard.StringParser;
import org.incendo.cloud.suggestion.Suggestion;
import org.incendo.cloud.suggestion.SuggestionProvider;

import java.util.stream.Collectors;

public final class RootCommand extends FAWarpCommand {
	public RootCommand(FAWarp plugin) {
		super(plugin);
	}

	@Override
	public void register() {
		Command.Builder<CommandSender> builder = this.manager.commandBuilder("fawarp");
		this.manager.command(builder.literal("reload").permission("fawarp.command.root.reload").handler(this::reloadHandler));

		MinecraftHelp<CommandSender> help = MinecraftHelp.<CommandSender>builder()
				.commandManager(this.manager)
				.audienceProvider(AudienceProvider.nativeAudience())
				.commandPrefix("/fawarp help")
				.colors(MinecraftHelp.helpColors(NamedTextColor.WHITE, TextColor.color(56, 189, 248), NamedTextColor.WHITE, NamedTextColor.WHITE, TextColor.color(17, 24, 39)))
				.build();

		Command.Builder<CommandSender> helpBuilder = builder.literal("help")
				.optional(
						"query",
						StringParser.greedyStringParser(),
						DefaultValue.constant(""),
						SuggestionProvider.blocking((ctx, in) -> this.manager.createHelpHandler()
								.queryRootIndex(ctx.sender())
								.entries()
								.stream()
								.map(CommandEntry::syntax)
								.map(Suggestion::simple)
								.collect(Collectors.toList())))
				.handler(context -> help.queryCommands(context.get("query"), context.sender()));
		this.manager.command(helpBuilder);
	}

	private void reloadHandler(CommandContext<CommandSender> context) {
		this.plugin.reload();
		context.sender().sendMessage(Component.translatable("fawarp.command.root.reload"));
	}
}
