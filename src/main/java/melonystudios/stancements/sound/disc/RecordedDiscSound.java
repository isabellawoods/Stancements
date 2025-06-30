package melonystudios.stancements.sound.disc;

import melonystudios.stancements.Stancements;
import net.minecraft.client.audio.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class RecordedDiscSound extends LocatableSound {
    private final Sound musicSound = new Sound(this.location.toString().replace("sounds/","").replace(".ogg", ""),
            1, 1, 1, Sound.Type.FILE, true, false, 16);

    public RecordedDiscSound(ResourceLocation musicID, int x, int y, int z) {
        super(musicID, SoundCategory.RECORDS);
        this.sound = this.musicSound;
        this.volume = 4;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public ResourceLocation shortenMusicID(ResourceLocation musicID) {
        return new ResourceLocation(musicID.getNamespace(), musicID.getPath()
                .replace(":", ".")
                .replace("/", ".")
                .replace("sounds.", "")
                .replace("music.", "")
                .replace(".ogg", ""));
    }

    @Override
    @Nonnull
    public ResourceLocation getLocation() {
        return this.shortenMusicID(this.location);
    }

    @Override
    @Nonnull
    public Sound getSound() {
        return this.musicSound;
    }

    @Override
    @Nonnull
    public SoundEventAccessor resolve(SoundHandler handler) {
        SoundEventAccessor accessor = new SoundEventAccessor(Stancements.stancements("recorded." + this.shortenMusicID(this.location).getPath()), null);
        accessor.addSound(new ISoundEventAccessor<Sound>() {
            @Override
            public int getWeight() {
                return 1;
            }

            @Override
            @Nonnull
            public Sound getSound() {
                return RecordedDiscSound.this.musicSound;
            }

            @Override
            public void preloadIfRequired(SoundEngine engine) {
                RecordedDiscSound.this.musicSound.preloadIfRequired(engine);
            }
        });
        return accessor;
    }
}
