package pl.easykostium.client;

import org.lwjgl.glfw.GLFW;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

public class EasyKostiumClient implements ClientModInitializer {

	private static KeyBinding openMenuKey;

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
		});
	}
}
