package pl.easykostium;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributeModifier.Operation;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

/** Definicje kostiumów. Dodanie kolejnego = nowa metoda + wpis w {@link #create(String)}. */
public final class Kostiums {

	public static final String WALENTYNKOWY = "walentynkowy";

	/** rgb(255, 170, 220) - jasny róż, dopasowany do zrzutu od użytkownika */
	public static final int WALENTYNKOWY_COLOR =(255 << 16) | (170 << 8) | 220;

	private Kostiums() {
	}

	/** @return nowy ItemStack kostiumu albo null, jeśli id jest nieznane */
	public static ItemStack create(String id) {
		if (WALENTYNKOWY.equals(id)) {
			return walentynkowy();
		}
		return null;
	}

	private static ItemStack walentynkowy() {
		ItemStack stack = new ItemStack(Items.LEATHER_CHESTPLATE, 1);

		stack.set(DataComponentTypes.CUSTOM_NAME, ColorText.parse("&7Kostium &dwalentynkowy"));

		List<String> loreLines = List.of(
				"&7Unikatowa wersja!",
				"&7Został on wydany jako pierwszy w historii serwerów",
				"&7w dniu &dWalentynek (2026)&7, aby uczcić miłość!",
				"",
				"&7Dzięki temu przedmiotowi otrzymasz:",
				"&funikatowy &7wygląd oraz &fepickie &7bonusy!",
				"",
				"&7Lista bonusów:",
				"&8» &c12% &7dodatkowych obrażeń",
				"&8» &b+5% &7obrony",
				"&8» &e100% &7odporności od upadków",
				"&8» &4+3 &7serc"
		);
		List<net.minecraft.text.Text> lore = new ArrayList<>();
		for (String line : loreLines) {
			lore.add(ColorText.parse(line));
		}
		stack.set(DataComponentTypes.LORE, new LoreComponent(lore));

		// Kolor zbroi (1.21.4: drugi argument = pokazywanie w tooltipie)
		stack.set(DataComponentTypes.DYED_COLOR, new DyedColorComponent(WALENTYNKOWY_COLOR, false));

		// Atrybuty. Własna lista ZASTĘPUJE domyślną zbroję skórzanego napierśnika,
		// więc bazowe 3 punkty pancerza dodajemy ręcznie.
		AttributeModifiersComponent attributes = AttributeModifiersComponent.builder()
				.add(EntityAttributes.ARMOR,
						new EntityAttributeModifier(id("base_armor"), 3.0, Operation.ADD_VALUE),
						AttributeModifierSlot.CHEST)
				.add(EntityAttributes.ARMOR,
						new EntityAttributeModifier(id("armor_bonus"), 0.05, Operation.ADD_MULTIPLIED_TOTAL),
						AttributeModifierSlot.CHEST)
				.add(EntityAttributes.ATTACK_DAMAGE,
						new EntityAttributeModifier(id("damage_bonus"), 0.12, Operation.ADD_MULTIPLIED_TOTAL),
						AttributeModifierSlot.CHEST)
				.add(EntityAttributes.FALL_DAMAGE_MULTIPLIER,
						new EntityAttributeModifier(id("no_fall_damage"), -1.0, Operation.ADD_MULTIPLIED_TOTAL),
						AttributeModifierSlot.CHEST)
				.add(EntityAttributes.MAX_HEALTH,
						new EntityAttributeModifier(id("extra_hearts"), 6.0, Operation.ADD_VALUE),
						AttributeModifierSlot.CHEST)
				.build();
		stack.set(DataComponentTypes.ATTRIBUTE_MODIFIERS, attributes);

		return stack;
	}

	private static Identifier id(String path) {
		return Identifier.of(EasyKostium.MOD_ID, "walentynkowy_" + path);
	}
}
