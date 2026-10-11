package pl.easykostium;

import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

/** Zamienia tekst z kodami kolorów w stylu &amp;7 / &amp;d na obiekt Text. */
public final class ColorText {

	private ColorText() {
	}

	public static MutableText parse(String input) {
		MutableText result = Text.empty();
		Formatting current = Formatting.WHITE;
		StringBuilder buffer = new StringBuilder();

		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c == '&' && i + 1 < input.length()) {
				Formatting fmt = Formatting.byCode(input.charAt(i + 1));
				if (fmt != null) {
					flush(result, buffer, current);
					current = fmt;
					i++;
					continue;
				}
			}
			buffer.append(c);
		}
		flush(result, buffer, current);
		return result;
	}

	private static void flush(MutableText target, StringBuilder buffer, Formatting color) {
		if (buffer.isEmpty()) {
			return;
		}
		// withItalic(false) - domyślnie nazwa i lore w grze są kursywą
		target.append(Text.literal(buffer.toString())
				.setStyle(Style.EMPTY.withFormatting(color).withItalic(false)));
		buffer.setLength(0);
	}
}
