package pl.easykostium;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

/** Pakiet klient -> serwer: gracz wybrał kostium o danym id. */
public record KostiumSelectPayload(String kostiumId) implements CustomPayload {

	public static final CustomPayload.Id<KostiumSelectPayload> ID =
			new CustomPayload.Id<>(Identifier.of(EasyKostium.MOD_ID, "select"));

	public static final PacketCodec<RegistryByteBuf, KostiumSelectPayload> CODEC =
			PacketCodec.tuple(PacketCodecs.STRING, KostiumSelectPayload::kostiumId, KostiumSelectPayload::new);

	@Override
	public CustomPayload.Id<? extends CustomPayload> getId() {
		return ID;
	}
}
