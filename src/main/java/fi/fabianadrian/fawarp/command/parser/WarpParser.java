package fi.fabianadrian.fawarp.command.parser;

import fi.fabianadrian.fawarp.command.FAWarpContextKeys;
import fi.fabianadrian.fawarp.warp.Warp;
import org.bukkit.command.CommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.caption.CaptionVariable;
import org.incendo.cloud.component.CommandComponent;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.context.CommandInput;
import org.incendo.cloud.exception.parsing.ParserException;
import org.incendo.cloud.minecraft.extras.caption.TranslatableCaption;
import org.incendo.cloud.parser.ArgumentParseResult;
import org.incendo.cloud.parser.ArgumentParser;
import org.incendo.cloud.parser.ParserDescriptor;
import org.incendo.cloud.suggestion.BlockingSuggestionProvider;

import java.util.List;

public final class WarpParser implements ArgumentParser<CommandSender, Warp>, BlockingSuggestionProvider.Strings<CommandSender> {
	public static @NonNull ParserDescriptor<CommandSender, Warp> warpParser() {
		return ParserDescriptor.of(new WarpParser(), Warp.class);
	}

	public static CommandComponent.@NonNull Builder<CommandSender, Warp> warpComponent() {
		return CommandComponent.<CommandSender, Warp>builder().parser(warpParser());
	}

	@Override
	public @NonNull ArgumentParseResult<@NonNull Warp> parse(@NonNull CommandContext<@NonNull CommandSender> context, @NonNull CommandInput input) {
		final String inputString = input.peekString();

		Warp warp = context.get(FAWarpContextKeys.PLUGIN).warpManager().warp(inputString);

		if (warp == null) {
			return ArgumentParseResult.failure(new WarpParseException(inputString, context));
		}

		input.readString();
		return ArgumentParseResult.success(warp);
	}

	@Override
	public @NonNull Iterable<@NonNull String> stringSuggestions(@NonNull CommandContext<CommandSender> context, @NonNull CommandInput input) {
		List<Warp> warps = context.get(FAWarpContextKeys.PLUGIN).warpManager().warps();
		return warps.stream().filter(warp -> context.sender().hasPermission(warp.permission())).map(Warp::name).toList();
	}

	public static final class WarpParseException extends ParserException {
		private WarpParseException(
				final @NonNull String input,
				final @NonNull CommandContext<?> context
		) {
			super(
					WarpParser.class,
					context,
					TranslatableCaption.translatableCaption("argument.parse.failure.warp"),
					CaptionVariable.of("input", input)
			);
		}
	}
}
