package fi.fabianadrian.fawarp.listener;

import fi.fabianadrian.fawarp.FAWarp;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;

public final class ServerListener implements Listener {
	private final FAWarp plugin;

	public ServerListener(FAWarp plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onServerLoad(ServerLoadEvent event) {
		this.plugin.reload();
	}
}
