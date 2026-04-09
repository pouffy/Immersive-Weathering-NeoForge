package io.github.pouffy.immersive_weathering.datagen.bootstrap;

import static net.minecraft.data.worldgen.placement.PlacementUtils.register;

import io.github.pouffy.immersive_weathering.ImmersiveWeathering;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.blockpredicates.MatchingFluidsPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.Optional;

public class ModPlacedFeatures {

    public static final ResourceKey<PlacedFeature> ICICLES = key("icicles");
    public static final ResourceKey<PlacedFeature> FROST_PATCH = key("frost_patch");

    public static final ResourceKey<PlacedFeature> LOAM = key("loam");
    public static final ResourceKey<PlacedFeature> SILT = key("silt");
    public static final ResourceKey<PlacedFeature> SILT_AQUIFER = key("silt_aquifer");
    public static final ResourceKey<PlacedFeature> SANDY_DIRT = key("sandy_dirt");
    public static final ResourceKey<PlacedFeature> EARTHEN_CLAY = key("earthen_clay");
    public static final ResourceKey<PlacedFeature> PERMAFROST = key("permafrost");

    public static final ResourceKey<PlacedFeature> DRY_LAKEBED = key("dry_lakebed");
    public static final ResourceKey<PlacedFeature> DRY_LAKEBED_LARGE = key("dry_lakebed_large");

    public static final ResourceKey<PlacedFeature> IVY_PATCH = key("ivy_patch");

    public static final ResourceKey<PlacedFeature> DUNE_GRASS_PATCH = key("dune_grass_patch");

    public static final ResourceKey<PlacedFeature> MOSS_PATCH = key("moss_patch");


    private static ResourceKey<PlacedFeature> key(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ImmersiveWeathering.res(name));
    }

    public static void bootstrap(BootstrapContext<PlacedFeature> ctx) {
        HolderGetter<ConfiguredFeature<?, ?>> featureLookup = ctx.lookup(Registries.CONFIGURED_FEATURE);

        register(ctx, ICICLES, featureLookup.getOrThrow(ModConfiguredFeatures.ICICLES),
                noiseBased(10, 50, 0.1),
                noiseThreshold(0.3, 1, 10),
                countEveryLayer(UniformInt.of(1, 5)),
                heightRange(UniformHeight.of(VerticalAnchor.absolute(92), VerticalAnchor.absolute(256))), biome());
        register(ctx, FROST_PATCH, featureLookup.getOrThrow(ModConfiguredFeatures.FROST_PATCH),
                noiseBased(12, 50, 0.2),
                noiseThreshold(0.3, 1, 5),
                countEveryLayer(UniformInt.of(4, 16)),
                heightRange(UniformHeight.of(VerticalAnchor.absolute(92), VerticalAnchor.absolute(256))), biome());

        register(ctx, LOAM, featureLookup.getOrThrow(ModConfiguredFeatures.LOAM),
                noiseBased(50, 100, 0.1),
                inSquare(),
                heightmap(Heightmap.Types.MOTION_BLOCKING), biome());
        register(ctx, SILT, featureLookup.getOrThrow(ModConfiguredFeatures.SILT),
                noiseBased(50, 100, 0.1),
                inSquare(),
                heightmap(Heightmap.Types.OCEAN_FLOOR_WG), biome());
        register(ctx, SILT_AQUIFER, featureLookup.getOrThrow(ModConfiguredFeatures.SILT_AQUIFER),
                noiseBased(50, 50, 0.4),
                countEveryLayer(UniformInt.of(1, 2)),
                heightRange(UniformHeight.of(VerticalAnchor.aboveBottom(1), VerticalAnchor.absolute(10))),
                surfaceThreshold(Heightmap.Types.OCEAN_FLOOR_WG, Optional.empty(), Optional.of(-13)),
                blockPredicate(new MatchingFluidsPredicate(new Vec3i(0, 1, 0), BuiltInRegistries.FLUID.getOrCreateTag(FluidTags.WATER))), biome());
        register(ctx, SANDY_DIRT, featureLookup.getOrThrow(ModConfiguredFeatures.SANDY_DIRT),
                noiseBased(50, 100, 0.1),
                inSquare(),
                heightmap(Heightmap.Types.MOTION_BLOCKING), biome());
        register(ctx, EARTHEN_CLAY, featureLookup.getOrThrow(ModConfiguredFeatures.EARTHEN_CLAY),
                noiseBased(-50, 100, -0.1),
                inSquare(),
                heightmap(Heightmap.Types.MOTION_BLOCKING), biome());
        register(ctx, PERMAFROST, featureLookup.getOrThrow(ModConfiguredFeatures.PERMAFROST),
                noiseBased(50, 100, 0.1),
                inSquare(),
                heightmap(Heightmap.Types.MOTION_BLOCKING), biome());

        register(ctx, DRY_LAKEBED, featureLookup.getOrThrow(ModConfiguredFeatures.DRY_LAKEBED),
                noiseBased(8, 50, 0.2),
                inSquare(),
                heightmap(Heightmap.Types.MOTION_BLOCKING), biome());
        register(ctx, DRY_LAKEBED_LARGE, featureLookup.getOrThrow(ModConfiguredFeatures.DRY_LAKEBED),
                noiseBased(64, 50, 0),
                inSquare(),
                heightmap(Heightmap.Types.MOTION_BLOCKING), biome());

        register(ctx, IVY_PATCH, featureLookup.getOrThrow(ModConfiguredFeatures.IVY_PATCH),
                count(UniformInt.of(104, 157)),
                heightRange(UniformHeight.of(VerticalAnchor.absolute(50), VerticalAnchor.absolute(100))),
                inSquare(),
                surfaceThreshold(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Optional.empty(), Optional.empty()), biome());

        register(ctx, DUNE_GRASS_PATCH, featureLookup.getOrThrow(ModConfiguredFeatures.DUNE_GRASS_PATCH),
                rarityFilter(16),
                inSquare(),
                heightmap(Heightmap.Types.WORLD_SURFACE_WG), biome());

        register(ctx, MOSS_PATCH, featureLookup.getOrThrow(ModConfiguredFeatures.MOSS_PATCH),
                noiseBased(10, 50, 0.4),
                countEveryLayer(UniformInt.of(1, 5)),
                heightRange(UniformHeight.of(VerticalAnchor.absolute(1), VerticalAnchor.absolute(256))),
                surfaceThreshold(Heightmap.Types.OCEAN_FLOOR_WG, Optional.empty(), Optional.of(-13)),
                blockPredicate(BlockPredicate.anyOf(
                        new MatchingFluidsPredicate(new Vec3i(0, -1, 0), BuiltInRegistries.FLUID.getOrCreateTag(FluidTags.WATER)),
                        new MatchingFluidsPredicate(new Vec3i(0, -2, 0), BuiltInRegistries.FLUID.getOrCreateTag(FluidTags.WATER)),
                        new MatchingFluidsPredicate(new Vec3i(0, -3, 0), BuiltInRegistries.FLUID.getOrCreateTag(FluidTags.WATER)),
                        new MatchingFluidsPredicate(new Vec3i(0,  0,-1), BuiltInRegistries.FLUID.getOrCreateTag(FluidTags.WATER)),
                        new MatchingFluidsPredicate(new Vec3i(0,  0,-2), BuiltInRegistries.FLUID.getOrCreateTag(FluidTags.WATER)),
                        new MatchingFluidsPredicate(new Vec3i(0,  0,-3), BuiltInRegistries.FLUID.getOrCreateTag(FluidTags.WATER)),
                        new MatchingFluidsPredicate(new Vec3i(-1, 0, 0), BuiltInRegistries.FLUID.getOrCreateTag(FluidTags.WATER)),
                        new MatchingFluidsPredicate(new Vec3i(-2, 0, 0), BuiltInRegistries.FLUID.getOrCreateTag(FluidTags.WATER)),
                        new MatchingFluidsPredicate(new Vec3i(-3, 0, 0), BuiltInRegistries.FLUID.getOrCreateTag(FluidTags.WATER)),
                        new MatchingFluidsPredicate(new Vec3i(-1, -1, -1), BuiltInRegistries.FLUID.getOrCreateTag(FluidTags.WATER)),
                        new MatchingFluidsPredicate(new Vec3i(-2, -2, -2), BuiltInRegistries.FLUID.getOrCreateTag(FluidTags.WATER)),
                        new MatchingFluidsPredicate(new Vec3i(-3, -3, -3), BuiltInRegistries.FLUID.getOrCreateTag(FluidTags.WATER)),
                        new MatchingFluidsPredicate(new Vec3i(0, 0, 1), BuiltInRegistries.FLUID.getOrCreateTag(FluidTags.WATER)),
                        new MatchingFluidsPredicate(new Vec3i(0, 0, 2), BuiltInRegistries.FLUID.getOrCreateTag(FluidTags.WATER)),
                        new MatchingFluidsPredicate(new Vec3i(0, 0, 3), BuiltInRegistries.FLUID.getOrCreateTag(FluidTags.WATER)),
                        new MatchingFluidsPredicate(new Vec3i(1, 0, 0), BuiltInRegistries.FLUID.getOrCreateTag(FluidTags.WATER)),
                        new MatchingFluidsPredicate(new Vec3i(2, 0, 0), BuiltInRegistries.FLUID.getOrCreateTag(FluidTags.WATER)),
                        new MatchingFluidsPredicate(new Vec3i(3, 0, 0), BuiltInRegistries.FLUID.getOrCreateTag(FluidTags.WATER)),
                        new MatchingFluidsPredicate(new Vec3i(1, -1, 1), BuiltInRegistries.FLUID.getOrCreateTag(FluidTags.WATER)),
                        new MatchingFluidsPredicate(new Vec3i(2, -2, 2), BuiltInRegistries.FLUID.getOrCreateTag(FluidTags.WATER)),
                        new MatchingFluidsPredicate(new Vec3i(3, -3, 3), BuiltInRegistries.FLUID.getOrCreateTag(FluidTags.WATER))
                )), biome());
    }

    private static NoiseBasedCountPlacement noiseBased(int noiseToCountRatio, double noiseFactor, double noiseOffset) {
        return NoiseBasedCountPlacement.of(noiseToCountRatio, noiseFactor, noiseOffset);
    }

    private static NoiseThresholdCountPlacement noiseThreshold(double noiseLevel, int belowNoise, int aboveNoise) {
        return NoiseThresholdCountPlacement.of(noiseLevel, belowNoise, aboveNoise);
    }

    private static CountPlacement count(IntProvider count) {
        return CountPlacement.of(count);
    }

    private static RarityFilter rarityFilter(int chance) {
        return RarityFilter.onAverageOnceEvery(chance);
    }

    private static CountOnEveryLayerPlacement countEveryLayer(IntProvider count) {
        return CountOnEveryLayerPlacement.of(count);
    }

    private static HeightRangePlacement heightRange(HeightProvider provider) {
        return HeightRangePlacement.of(provider);
    }

    private static HeightmapPlacement heightmap(Heightmap.Types types) {
        return HeightmapPlacement.onHeightmap(types);
    }

    private static InSquarePlacement inSquare() {
        return InSquarePlacement.spread();
    }

    private static BiomeFilter biome() {
        return BiomeFilter.biome();
    }

    private static SurfaceRelativeThresholdFilter surfaceThreshold(Heightmap.Types types, Optional<Integer> min, Optional<Integer> max) {
        return SurfaceRelativeThresholdFilter.of(types, min.orElse(Integer.MIN_VALUE), max.orElse(Integer.MAX_VALUE));
    }

    private static BlockPredicateFilter blockPredicate(BlockPredicate predicate) {
        return BlockPredicateFilter.forPredicate(predicate);
    }
}
