package fi.fabianadrian.fawarp.command;

import fi.fabianadrian.fawarp.FAWarp;
import org.incendo.cloud.key.CloudKey;

public final class FAWarpContextKeys {
	public static final CloudKey<FAWarp> PLUGIN = CloudKey.cloudKey("FAWarp", FAWarp.class);
}
