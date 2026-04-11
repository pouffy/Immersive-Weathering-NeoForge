package io.github.pouffy.immersive_weathering.datagen.client;

import com.mojang.datafixers.util.Pair;
import io.github.pouffy.immersive_weathering.ImmersiveWeathering;
import io.github.pouffy.immersive_weathering.reg.ModSoundEvents;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Supplier;

public class ModSoundsProvider extends SoundDefinitionsProvider {
    public static final Set<Pair<Supplier<SoundEvent>, String>> subtitles = new LinkedHashSet<>();
    public ModSoundsProvider(PackOutput output, ExistingFileHelper fileHelper) {
        super(output, ImmersiveWeathering.MOD_ID, fileHelper);
    }

    @Override
    public void registerSounds() {
        this.add(ModSoundEvents.ICICLE_CRACK, "Thin Ice cracks", SoundDefinition.definition()
                .with(this.simpleSound("icicles_crack_0"))
                .with(this.simpleSound("icicles_crack_1"))
        );
    }

    protected void add(final Supplier<SoundEvent> soundEvent, String subtitle, final SoundDefinition definition) {
        this.add(soundEvent, definition.subtitle("immersive_weathering.subtitle.%s".formatted(soundEvent.get().getLocation().getPath())));
        subtitles.add(Pair.of(soundEvent, subtitle));
    }

    private SoundDefinition.Sound simpleSound(String name) {
        return SoundDefinition.Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeathering.MOD_ID, name), SoundDefinition.SoundType.SOUND);
    }
}
