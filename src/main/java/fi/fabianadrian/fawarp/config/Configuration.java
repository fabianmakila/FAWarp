package fi.fabianadrian.fawarp.config;

import org.bukkit.Location;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.util.Map;

@ConfigSerializable
public class Configuration {
	private Map<String, Location> warps;

	public Map<String, Location> warps() {
		return this.warps;
	}
}
