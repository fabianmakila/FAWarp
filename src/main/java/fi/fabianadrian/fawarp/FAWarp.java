package fi.fabianadrian.fawarp;

import fi.fabianadrian.fawarp.command.FAWarpCommand;
import fi.fabianadrian.fawarp.command.FAWarpComponentCaptionFormatter;
import fi.fabianadrian.fawarp.command.commands.*;
import fi.fabianadrian.fawarp.command.processor.FAWarpCommandPreprocessor;
import fi.fabianadrian.fawarp.config.ConfigurationManager;
import fi.fabianadrian.fawarp.listener.ServerListener;
import fi.fabianadrian.fawarp.locale.TranslationManager;
import fi.fabianadrian.fawarp.warp.WarpManager;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.incendo.cloud.bukkit.CloudBukkitCapabilities;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.minecraft.extras.MinecraftExceptionHandler;
import org.incendo.cloud.minecraft.extras.caption.TranslatableCaption;
import org.incendo.cloud.paper.PaperCommandManager;

import java.util.List;

public final class FAWarp extends JavaPlugin {
	private PaperCommandManager<CommandSender> commandManager;
	private ConfigurationManager configurationManager;
	private WarpManager warpManager;

	@Override
	public void onEnable() {
		new TranslationManager(getSLF4JLogger());

		this.configurationManager = new ConfigurationManager(this);
		this.warpManager = new WarpManager(this);

		setupCommandManager();
		registerCommands();

		registerListeners();
	}

	public PaperCommandManager<CommandSender> commandManager() {
		return this.commandManager;
	}

	public void reload() {
		this.configurationManager.reload();
		this.warpManager.reload();
	}

	public ConfigurationManager configurationManager() {
		return this.configurationManager;
	}

	public WarpManager warpManager() {
		return this.warpManager;
	}

	private void setupCommandManager() {
		this.commandManager = PaperCommandManager.createNative(this, ExecutionCoordinator.simpleCoordinator());

		if (this.commandManager.hasCapability(CloudBukkitCapabilities.NATIVE_BRIGADIER)) {
			this.commandManager.registerBrigadier();
		} else if (commandManager.hasCapability(CloudBukkitCapabilities.ASYNCHRONOUS_COMPLETION)) {
			this.commandManager.registerAsynchronousCompletions();
		}

		this.commandManager.registerCommandPreProcessor(new FAWarpCommandPreprocessor<>(this));
		this.commandManager.captionRegistry().registerProvider(TranslatableCaption.translatableCaptionProvider());
		MinecraftExceptionHandler.<CommandSender>createNative().defaultHandlers().captionFormatter(FAWarpComponentCaptionFormatter.translatable()).registerTo(this.commandManager);
	}

	private void registerCommands() {
		List.of(
				new RootCommand(this),
				new SetWarpCommand(this),
				new UnsetWarpCommand(this),
				new WarpCommand(this),
				new WarpListCommand(this)
		).forEach(FAWarpCommand::register);
	}

	private void registerListeners() {
		PluginManager manager = getServer().getPluginManager();
		List.of(new ServerListener(this)).forEach(listener -> manager.registerEvents(listener, this));
	}
}
