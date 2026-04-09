package io.github.pouffy.immersive_weathering.reg;

import io.github.pouffy.immersive_weathering.ImmersiveWeathering;
import io.github.pouffy.immersive_weathering.features.IcicleClusterFeature;
import io.github.pouffy.immersive_weathering.features.IcicleClusterFeatureConfig;
import io.github.pouffy.immersive_weathering.util.ModUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = ModUtils.createRegister(Registries.FEATURE);

    public static final DeferredHolder<Feature<?>, Feature<IcicleClusterFeatureConfig>> ICICLE_FEATURE = FEATURES.register("icicle_cluster", () -> new IcicleClusterFeature(IcicleClusterFeatureConfig.CODEC));

    public static void staticInit() {
        ImmersiveWeathering.LOGGER.info("[Immersive Weathering] Feature Registry");
    }
}
