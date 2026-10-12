package bt7s7k7.packing_tape.components;

import com.mojang.serialization.Codec;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public record IsPacked() {
	public static final IsPacked INSTANCE = new IsPacked();

	public static final Codec<IsPacked> CODEC = Codec.unit(INSTANCE);

	public static final StreamCodec<RegistryFriendlyByteBuf, IsPacked> STREAM_CODEC = StreamCodec.unit(INSTANCE);
}
