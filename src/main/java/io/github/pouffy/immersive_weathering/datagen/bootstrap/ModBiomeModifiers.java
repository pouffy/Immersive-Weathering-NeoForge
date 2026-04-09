package io.github.pouffy.immersive_weathering.datagen.bootstrap;

import io.github.pouffy.immersive_weathering.ImmersiveWeathering;
import io.github.pouffy.immersive_weathering.reg.ModTags;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModBiomeModifiers {

    public static final ResourceKey<BiomeModifier> ICY = key("icy");
    public static final ResourceKey<BiomeModifier> LOAM = key("loam");
    public static final ResourceKey<BiomeModifier> SILT = key("silt");
    public static final ResourceKey<BiomeModifier> SILT_AQUIFER = key("silt_aquifer");
    public static final ResourceKey<BiomeModifier> SANDY_DIRT = key("sandy_dirt");
    public static final ResourceKey<BiomeModifier> EARTHEN_CLAY = key("earthen_clay");
    public static final ResourceKey<BiomeModifier> PERMAFROST = key("permafrost");
    public static final ResourceKey<BiomeModifier> DRY_LAKEBED = key("dry_lakebed");
    public static final ResourceKey<BiomeModifier> IVY = key("ivy");
    public static final ResourceKey<BiomeModifier> DUNE_GRASS = key("dune_grass");
    public static final ResourceKey<BiomeModifier> MOSS = key("moss");

    private static ResourceKey<BiomeModifier> key(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ImmersiveWeathering.res(name));
    }

    public static void bootstrap(BootstrapContext<BiomeModifier> ctx) {
        HolderGetter<Biome> biomeLookup = ctx.lookup(Registries.BIOME);
        HolderGetter<PlacedFeature> featureLookup = ctx.lookup(Registries.PLACED_FEATURE);

        ctx.register(ICY, addFeatures(biomeLookup.getOrThrow(ModTags.ICY), GenerationStep.Decoration.TOP_LAYER_MODIFICATION, featureLookup.getOrThrow(ModPlacedFeatures.ICICLES), featureLookup.getOrThrow(ModPlacedFeatures.FROST_PATCH)));

        ctx.register(LOAM, addFeatures(biomeLookup.getOrThrow(ModTags.HAS_LOAM), GenerationStep.Decoration.RAW_GENERATION, featureLookup.getOrThrow(ModPlacedFeatures.LOAM)));
        ctx.register(SILT, addFeatures(biomeLookup.getOrThrow(ModTags.HAS_SILT), GenerationStep.Decoration.RAW_GENERATION, featureLookup.getOrThrow(ModPlacedFeatures.SILT)));
        ctx.register(SILT_AQUIFER, addFeatures(biomeLookup.getOrThrow(BiomeTags.IS_OVERWORLD), GenerationStep.Decoration.RAW_GENERATION, featureLookup.getOrThrow(ModPlacedFeatures.SILT_AQUIFER)));
        ctx.register(SANDY_DIRT, addFeatures(biomeLookup.getOrThrow(ModTags.HAS_SANDY_DIRT), GenerationStep.Decoration.RAW_GENERATION, featureLookup.getOrThrow(ModPlacedFeatures.SANDY_DIRT)));
        ctx.register(EARTHEN_CLAY, addFeatures(biomeLookup.getOrThrow(ModTags.HAS_EARTHEN_CLAY), GenerationStep.Decoration.RAW_GENERATION, featureLookup.getOrThrow(ModPlacedFeatures.EARTHEN_CLAY)));
        ctx.register(PERMAFROST, addFeatures(biomeLookup.getOrThrow(ModTags.HAS_PERMAFROST), GenerationStep.Decoration.RAW_GENERATION, featureLookup.getOrThrow(ModPlacedFeatures.PERMAFROST)));

        ctx.register(DRY_LAKEBED, addFeatures(biomeLookup.getOrThrow(ModTags.HAS_LAKEBED), GenerationStep.Decoration.RAW_GENERATION, featureLookup.getOrThrow(ModPlacedFeatures.DRY_LAKEBED), featureLookup.getOrThrow(ModPlacedFeatures.DRY_LAKEBED_LARGE)));
        ctx.register(IVY, addFeatures(biomeLookup.getOrThrow(ModTags.HAS_IVY), GenerationStep.Decoration.VEGETAL_DECORATION, featureLookup.getOrThrow(ModPlacedFeatures.IVY_PATCH)));
        ctx.register(DUNE_GRASS, addFeatures(biomeLookup.getOrThrow(ModTags.HAS_DUNE_GRASS), GenerationStep.Decoration.VEGETAL_DECORATION, featureLookup.getOrThrow(ModPlacedFeatures.DUNE_GRASS_PATCH)));
        ctx.register(MOSS, addFeatures(biomeLookup.getOrThrow(ModTags.HAS_MOSS), GenerationStep.Decoration.VEGETAL_DECORATION, featureLookup.getOrThrow(ModPlacedFeatures.MOSS_PATCH)));

    }

    @SafeVarargs
    private static BiomeModifiers.AddFeaturesBiomeModifier addFeatures(HolderSet<Biome> biomes, GenerationStep.Decoration decoration, Holder<PlacedFeature>... features) {
        return new BiomeModifiers.AddFeaturesBiomeModifier(biomes, HolderSet.direct(features), decoration);
    }
}
