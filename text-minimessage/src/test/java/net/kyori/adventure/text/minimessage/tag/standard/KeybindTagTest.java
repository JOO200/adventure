package net.kyori.adventure.text.minimessage.tag.standard;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.TestBase;

import org.junit.jupiter.api.Test;

import static net.kyori.adventure.text.Component.keybind;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.RED;

class KeybindTagTest extends TestBase {

  @Test
  void testKeyBind() {
    final String input = "Press <key:key.jump> to jump!";
    final Component expected = text("Press ")
      .append(keybind("key.jump")
        .append(text(" to jump!")));

    this.assertParsedEquals(expected, input);
  }

  @Test
  void testKeyBindWithColor() {
    final String input = "Press <red><key:key.jump> to jump!";
    final Component expected = text("Press ")
      .append(
        keybind("key.jump", RED)
          .append(text(" to jump!"))
      );

    this.assertParsedEquals(expected, input);
  }
}
