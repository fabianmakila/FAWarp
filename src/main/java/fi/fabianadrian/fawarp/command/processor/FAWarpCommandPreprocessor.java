package fi.fabianadrian.fawarp.command.processor;

import fi.fabianadrian.fawarp.FAWarp;
import fi.fabianadrian.fawarp.command.FAWarpContextKeys;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.execution.preprocessor.CommandPreprocessingContext;
import org.incendo.cloud.execution.preprocessor.CommandPreprocessor;

public final class FAWarpCommandPreprocessor<C> implements CommandPreprocessor<C> {
	private final FAWarp plugin;

	public FAWarpCommandPreprocessor(FAWarp plugin) {
		this.plugin = plugin;
	}

	@Override
	public void accept(@NonNull CommandPreprocessingContext<C> context) {
		CommandContext<C> commandContext = context.commandContext();
		commandContext.store(FAWarpContextKeys.PLUGIN, this.plugin);
	}
}
