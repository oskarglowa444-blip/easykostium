package pl.easykostium;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Formatting;

public class EasyKostium implements ModInitializer {

	public static final String MOD_ID = "easykostium";

	@Override
	public void onInitialize() {
		PayloadTypeRegistry.playC2S().register(KostiumSelectPayload.ID, KostiumSelectPayload.CODEC);

		ServerPlayNetworking.registerGlobalReceiver(KostiumSelectPayload.ID, (payload, context) -> {
			ServerPlayerEntity player = context.player();
			ItemStack kostium = Kostiums.create(payload.kostiumId());
			if (kostium == null) {
				return;
			}

			ItemStack held = player.getStackInHand(Hand.MAIN_HAND);
			if (held.isEmpty()) {
				player.sendMessage(Text.literal("Weź do ręki przedmiot, który ma zostać zamieniony na kostium.")
						.formatted(Formatting.RED), true);
				return;
			}

			if (held.getCount() > 1) {
				// zamieniamy tylko jedną sztukę, resztę stosu zostawiamy
				held.decrement(1);
				player.getInventory().offerOrDrop(kostium);
			} else {
				player.setStackInHand(Hand.MAIN_HAND, kostium);
			}

			player.sendMessage(Text.literal("Otrzymałeś Kostium walentynkowy!")
					.formatted(Formatting.LIGHT_PURPLE), true);
		});
	}
}
