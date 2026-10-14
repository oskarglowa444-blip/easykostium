package pl.easykostium.client;

import org.lwjgl.glfw.GLFW;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import pl.easykostium.Kostiums;

public class EasyKostiumClient implements ClientModInitializer {

	private static KeyBinding openMenuKey;

	/**
	 * Tryb kosmetyczny: na serwerach bez tego moda przemalowuje (tylko u nas, po stronie klienta)
	 * założony skórzany napierśnik na kolor kostiumu walentynkowego.
	 */
	private static boolean localPink = false;

	public static boolean isLocalPink() {
		return localPink;
	}

	public static boolean toggleLocalPink() {
		localPink = !localPink;
		return localPink;
	}

	@Override
	public void onInitializeClient() {
		openMenuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.easykostium.open",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_B,
				"category.easykostium"
		));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (openMenuKey.wasPressed()) {
				if (client.player != null && client.currentScreen == null) {
					client.setScreen(new KostiumScreen());
				}
			}

			if (localPink && client.player != null) {
				ItemStack chest = client.player.getEquippedStack(EquipmentSlot.CHEST);
				if (chest.isOf(Items.LEATHER_CHESTPLATE)) {
					DyedColorComponent current = chest.get(DataComponentTypes.DYED_COLOR);
					if (current == null || current.rgb() != Kostiums.WALENTYNKOWY_COLOR) {
						chest.set(DataComponentTypes.DYED_COLOR,
								new DyedColorComponent(Kostiums.WALENTYNKOWY_COLOR, false));
					}
				}
			}
		});
	}
}
