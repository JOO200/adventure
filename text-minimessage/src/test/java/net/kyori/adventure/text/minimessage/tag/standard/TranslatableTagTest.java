package net.kyori.adventure.text.minimessage.tag.standard;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.TestBase;

import org.junit.jupiter.api.Test;

import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.translatable;
import static net.kyori.adventure.text.event.HoverEvent.showText;
import static net.kyori.adventure.text.format.NamedTextColor.BLUE;
import static net.kyori.adventure.text.format.NamedTextColor.RED;

class TranslatableTagTest extends TestBase {

  @Test
  void testTranslatable() {
    final String input = "You should get a <lang:block.minecraft.diamond_block>!";
    final Component expected = text("You should get a ")
      .append(translatable("block.minecraft.diamond_block")
        .append(text("!")));

    this.assertParsedEquals(expected, input);
  }

  @Test
  void testTranslatableWith() {
    final String input = "Test: <lang:commands.drop.success.single:'<red>1':'<blue>Stone'>!";
    final Component expected = text("Test: ")
      .append(translatable("commands.drop.success.single", text("1", RED), text("Stone", BLUE))
        .append(text("!")));

    this.assertParsedEquals(expected, input);
  }

  @Test
  void testTranslatableWithHover() {
    final String input = "Test: <lang:commands.drop.success.single:'<hover:show_text:\\'<red>dum\\'><red>1':'<blue>Stone'>!";
    final Component expected = text("Test: ")
      .append(
        translatable(
          "commands.drop.success.single",
          text("1", RED).hoverEvent(showText(text("dum", RED))),
          text("Stone", BLUE)
        )
          .append(text("!"))
      );

    this.assertParsedEquals(expected, input);
  }

  @Test
  void testKingAlter() {
    final String input = "Ahoy <lang:offset.-40:'<red>mates!'>";
    final Component expected = text("Ahoy ")
      .append(translatable("offset.-40", text("mates!", RED)));

    this.assertParsedEquals(expected, input);
  }
}
