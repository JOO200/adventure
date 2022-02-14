package net.kyori.adventure.text.minimessage.tag.standard;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.TestBase;

import org.junit.jupiter.api.Test;

import static net.kyori.adventure.key.Key.key;
import static net.kyori.adventure.text.Component.empty;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.event.ClickEvent.openUrl;
import static net.kyori.adventure.text.format.TextColor.color;

class FontTagTest extends TestBase {

  @Test
  void testFont() {
    final String input = "Nothing <font:minecraft:uniform>Uniform <font:minecraft:alt>Alt  </font> Uniform";
    final Component expected = text("Nothing ")
      .append(empty().style(s -> s.font(key("uniform")))
        .append(text("Uniform "))
        .append(text("Alt  ").style(s -> s.font(key("alt"))))
        .append(text(" Uniform"))
      );

    this.assertParsedEquals(expected, input);
  }

  @Test
  void testCustomFont() {
    final String input = "Default <font:myfont:best_font>Custom font <font:custom:worst_font>Another custom font </font>Back to previous font";
    final Component expected = text("Default ")
      .append(empty().style(s -> s.font(key("myfont", "best_font")))
        .append(text("Custom font "))
        .append(text("Another custom font ").style(s -> s.font(key("custom", "worst_font"))))
        .append(text("Back to previous font"))
      );

    this.assertParsedEquals(expected, input);
  }

  @Test
  void testFontNoNamespace() {
    final String input = "Nothing <font:uniform>Uniform <font:alt>Alt  </font> Uniform";
    final Component expected = text("Nothing ")
      .append(empty().style(s -> s.font(key("uniform")))
        .append(text("Uniform "))
        .append(text("Alt  ").style(s -> s.font(key("alt"))))
        .append(text(" Uniform"))
      );

    this.assertParsedEquals(expected, input);
  }
}
