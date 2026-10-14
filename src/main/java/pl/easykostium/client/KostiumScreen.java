package pl.easykostium.client;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import pl.easykostium.KostiumSelectPayload;
import pl.easykostium.Kostiums;

/** Menu kostiumów otwierane klawiszem B. */
public class KostiumScreen extends Screen {

	private static final int BUTTON_WIDTH = 200;
	private static final int BUTTON_HEIGHT = 20;

	public KostiumScreen() {
		super(Text.literal("EasyKostium"));
	}

	@Override
	protected void init() {
		int x = this.width / 2 - BUTTON_WIDTH / 2;
		int y = this.height / 2 - 20;

		this.addDrawableChild(ButtonWidget.builder(
				Text.literal("Kostium walentynkowy"),
				button -> {
					if (ClientPlayNetworking.canSend(KostiumSelectPayload.ID)) {
						// serwer ma moda: prawdziwy przedmiot z bonusami
						ClientPlayNetworking.send(new KostiumSelectPayload(Kostiums.WALENTYNKOWY));
					} else if (this.client != null && this.client.player != null) {
						// serwer bez moda: tylko kosmetyka u nas
						boolean on = EasyKostiumClient.toggleLocalPink();
						this.client.player.sendMessage(Text.literal(on
								? "Różowy kolor włączony (widoczny tylko u Ciebie). Załóż skórzany napierśnik."
								: "Różowy kolor wyłączony."), true);
					}
					this.close();
				}
		).dimensions(x, y, BUTTON_WIDTH, BUTTON_HEIGHT).build());

		this.addDrawableChild(ButtonWidget.builder(
				Text.literal("Zamknij"),
				button -> this.close()
		).dimensions(x, y + 30, BUTTON_WIDTH, BUTTON_HEIGHT).build());
	}

	@Override
	public void render(DrawContext context, int mouseX, int mouseY, float delta) {
		super.render(context, mouseX, mouseY, delta);

		context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, this.height / 2 - 55, 0xFFFFFF);
		context.drawCenteredTextWithShadow(this.textRenderer,
				Text.literal("Trzymany przedmiot zamieni się w wybrany kostium"),
				this.width / 2, this.height / 2 - 40, 0xAAAAAA);

		ItemStack icon = new ItemStack(Items.LEATHER_CHESTPLATE);
		context.drawItem(icon, this.width / 2 - 8, this.height / 2 - 75);
	}

	@Override
	public boolean shouldPause() {
		return false;
	}
}
