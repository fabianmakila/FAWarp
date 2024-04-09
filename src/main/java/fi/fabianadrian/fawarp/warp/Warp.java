package fi.fabianadrian.fawarp.warp;

import net.kyori.adventure.text.Component;
import org.bukkit.Location;

public record Warp(String name, Location location) {

	public String permission() {
		return "fawarp.warp." + this.name.toLowerCase();
	}

	public Component nameAsComponent() {
		return Component.text(this.name);
	}
}
