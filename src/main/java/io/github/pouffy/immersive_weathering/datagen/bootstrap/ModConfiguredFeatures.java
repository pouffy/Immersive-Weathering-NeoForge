package io.github.pouffy.immersive_weathering.datagen.bootstrap;

import static net.minecraft.data.worldgen.features.FeatureUtils.register;

import io.github.pouffy.immersive_weathering.ImmersiveWeathering;
import io.github.pouffy.immersive_weathering.blocks.soil_types.EarthenClayBlock;
import io.github.pouffy.immersive_weathering.features.IcicleClusterFeatureConfig;
import io.github.pouffy.immersive_weathering.reg.ModBlocks;
import io.github.pouffy.immersive_weathering.reg.ModFeatures;
import io.github.pouffy.immersive_weathering.reg.ModTags;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedBlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.Arrays;
import java.util.List;

public class ModConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> ICICLES = key("icicles");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FROST_PATCH = key("frost_patch");

    public static final ResourceKey<ConfiguredFeature<?, ?>> LOAM = key("loam");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SILT = key("silt");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SILT_AQUIFER = key("silt_aquifer");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SANDY_DIRT = key("sandy_dirt");
    public static final ResourceKey<ConfiguredFeature<?, ?>> EARTHEN_CLAY = key("earthen_clay");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PERMAFROST = key("permafrost");

    public static final ResourceKey<ConfiguredFeature<?, ?>> DRY_LAKEBED = key("dry_lakebed");

    public static final ResourceKey<ConfiguredFeature<?, ?>> IVY_PATCH = key("ivy_patch");

    public static final ResourceKey<ConfiguredFeature<?, ?>> DUNE_GRASS_VEGETATION = key("dune_grass_vegetation");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DUNE_GRASS_PATCH = key("dune_grass_patch");

    public static final ResourceKey<ConfiguredFeature<?, ?>> MOSS_PATCH = key("moss_patch");


    private static ResourceKey<ConfiguredFeature<?, ?>> key(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ImmersiveWeathering.res(name));
    }

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> ctx) {
        HolderGetter<ConfiguredFeature<?, ?>> holdergetter = ctx.lookup(Registries.CONFIGURED_FEATURE);

        register(ctx, ICICLES, ModFeatures.ICICLE_FEATURE.get(), new IcicleClusterFeatureConfig(12, UniformInt.of(3, 6), UniformInt.of(2, 8), 1, 3, UniformInt.of(2, 4), UniformFloat.of(0.3f, 0.7f), ClampedNormalFloat.of(0.1f, 0.3f, 0.1f, 0.9f), 0.1f, 3, 8));
        register(ctx, FROST_PATCH, Feature.MULTIFACE_GROWTH, new MultifaceGrowthConfiguration(ModBlocks.FROST.get(), 20, false, true, true, 0.5f, makeSet(Blocks.DIRT, Blocks.SNOW_BLOCK, Blocks.STONE, Blocks.GRANITE, Blocks.ANDESITE, Blocks.TUFF, Blocks.DEEPSLATE)));

        register(ctx, LOAM, Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(new BlockMatchTest(Blocks.GRASS_BLOCK), ModBlocks.LOAM.get().defaultBlockState())), 32, 0));
        register(ctx, SILT, Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(new BlockMatchTest(Blocks.GRASS_BLOCK), ModBlocks.GRASSY_SILT.get().defaultBlockState()), OreConfiguration.target(new TagMatchTest(ModTags.SILT_REPLACEABLE), ModBlocks.SILT.get().defaultBlockState())), 32, 0));
        register(ctx, SILT_AQUIFER, Feature.DISK, new DiskConfiguration(RuleBasedBlockStateProvider.simple(BlockStateProvider.simple(ModBlocks.SILT.get())), BlockPredicate.matchesTag(BlockTags.BASE_STONE_OVERWORLD), UniformInt.of(2, 4), 1));
        register(ctx, SANDY_DIRT, Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(new BlockMatchTest(Blocks.GRASS_BLOCK), ModBlocks.GRASSY_SANDY_DIRT.get().defaultBlockState()), OreConfiguration.target(new BlockMatchTest(Blocks.DIRT), ModBlocks.SANDY_DIRT.get().defaultBlockState())), 32, 0));
        register(ctx, EARTHEN_CLAY, Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(new BlockMatchTest(Blocks.GRASS_BLOCK), ModBlocks.GRASSY_EARTHEN_CLAY.get().defaultBlockState()), OreConfiguration.target(new BlockMatchTest(Blocks.DIRT), ModBlocks.EARTHEN_CLAY.get().defaultBlockState())), 32, 0));
        register(ctx, PERMAFROST, Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(new BlockMatchTest(Blocks.GRASS_BLOCK), ModBlocks.GRASSY_PERMAFROST.get().defaultBlockState()), OreConfiguration.target(new TagMatchTest(ModTags.PERMAFROST_REPLACEABLE), ModBlocks.PERMAFROST.get().defaultBlockState())), 32, 0));

        register(ctx, DRY_LAKEBED, Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(new BlockMatchTest(Blocks.WATER), ModBlocks.EARTHEN_CLAY.get().defaultBlockState().setValue(EarthenClayBlock.WATERLOGGED, true))), 32, 0));

        register(ctx, IVY_PATCH, Feature.MULTIFACE_GROWTH, new MultifaceGrowthConfiguration(ModBlocks.IVY.get(), 20, false, true, true, 0.5f, makeSet(Blocks.BIRCH_LOG, Blocks.OAK_LOG, Blocks.DARK_OAK_LOG)));

        register(ctx, DUNE_GRASS_VEGETATION, Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(Blocks.SHORT_GRASS.defaultBlockState(), 1).add(Blocks.DEAD_BUSH.defaultBlockState(), 5).add(ModBlocks.DUNE_GRASS.get().defaultBlockState(), 10))));
        register(ctx, DUNE_GRASS_PATCH, Feature.VEGETATION_PATCH, new VegetationPatchConfiguration(
                ModTags.DUNE_GRASS_PATCH_REPLACEABLE,
                new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(Blocks.SAND.defaultBlockState(), 2).add(ModBlocks.SANDY_DIRT.get().defaultBlockState(), 1)),
                PlacementUtils.inlinePlaced(holdergetter.getOrThrow(DUNE_GRASS_VEGETATION)), CaveSurface.FLOOR, ConstantInt.of(1), 0, 5, 0.1f, UniformInt.of(3, 5), 0.3f));

        register(ctx, MOSS_PATCH, Feature.MULTIFACE_GROWTH, new MultifaceGrowthConfiguration(ModBlocks.MOSS.get(), 20, true, true, true, 0.5f, makeSet(Blocks.STONE, Blocks.GRANITE, Blocks.ANDESITE, Blocks.DIORITE, Blocks.TUFF, Blocks.DEEPSLATE, Blocks.GRAVEL, Blocks.CLAY, Blocks.MOSS_BLOCK)));

    }

    private static HolderSet<Block> makeSet(Block... blocks) {
        return HolderSet.direct(Arrays.stream(blocks).map(Block::builtInRegistryHolder).toList());
    }
}
