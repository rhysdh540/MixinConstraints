package com.moulberry.mixinconstraints.util;

import com.moulberry.mixinconstraints.MixinConstraints;
import com.moulberry.mixinconstraints.MixinConstraints.Loader;

public abstract class Abstractions {
	private static final Abstractions instance;
	static {
		try {
			String name;
			if(MixinConstraints.LOADER == Loader.FORGE) {
				name = "com.moulberry.mixinconstraints.ForgeAbstractionsImpl";
			} else if(MixinConstraints.LOADER == Loader.NEOFORGE) {
				name = "com.moulberry.mixinconstraints.NeoForgeAbstractionsImpl";
			} else if(MixinConstraints.LOADER == Loader.FABRIC) {
				name = "com.moulberry.mixinconstraints.FabricAbstractionsImpl";
			} else {
				throw new IllegalStateException("Unknown loader: " + MixinConstraints.LOADER);
			}
			instance = (Abstractions) Class.forName(name).getDeclaredConstructor().newInstance();
		} catch (ReflectiveOperationException e) {
			throw new RuntimeException(e);
		}
	}

	public static boolean isDevelopmentEnvironment() {
		return instance.isDevEnvironment();
	}

	public static boolean isModLoadedWithinVersion(String modId, String minVersion, String maxVersion) {
		String version = instance.getModVersion(modId);
		if(version == null) {
			return false;
		}

		return instance.isVersionInRange(version, minVersion, maxVersion);
	}

	protected abstract boolean isDevEnvironment();
	protected abstract String getModVersion(String modId);
	protected abstract boolean isVersionInRange(String version, String minVersion, String maxVersion);
}
