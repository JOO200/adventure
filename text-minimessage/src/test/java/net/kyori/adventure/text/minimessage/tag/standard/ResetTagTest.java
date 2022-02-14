package net.kyori.adventure.text.minimessage.tag.standard;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.TestBase;

import org.junit.jupiter.api.Test;

import static net.kyori.adventure.text.Component.empty;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.YELLOW;
import static net.kyori.adventure.text.format.TextColor.color;

class ResetTagTest extends TestBase {

  @Test
  void testReset() {
    final String input = "Click <yellow><insert:test>this<rainbow> wooo<reset> to insert!";
    final Component expected = text("Click ")
      .append(empty().color(YELLOW).insertion("test")
        .append(text("this"))
        .append(empty()
          .append(text(" ", color(0xf3801f)))
          .append(text("w", color(0x71f813)))
          .append(text("o", color(0x03ca9c)))
          .append(text("o", color(0x4135fe)))
          .append(text("o", color(0xd507b1)))
        )
      ).append(text(" to insert!"));

    this.assertParsedEquals(expected, input);
  }
}
