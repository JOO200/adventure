package net.kyori.adventure.text.minimessage.tag.standard;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.TestBase;

import org.junit.jupiter.api.Test;

import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.TextDecoration.BOLD;
import static net.kyori.adventure.text.format.TextDecoration.ITALIC;

class DecorationTagTest extends TestBase {

  @Test
  void testDisabledDecoration() {
    final String input = "<italic:false>Test<bold:false>Test2<bold>Test3";
    final Component expected = text().decoration(ITALIC, false)
      .append(text("Test"))
      .append(text().decoration(BOLD, false)
        .append(text("Test2"))
        .append(text("Test3").decorate(BOLD))
      ).build();

    this.assertParsedEquals(expected, input);
  }

  @Test
  void testDisabledDecorationShorthand() {
    final String input = "<!italic>Test<!bold>Test2<bold>Test3";
    final Component expected = text().decoration(ITALIC, false)
      .append(text("Test"))
      .append(text().decoration(BOLD, false)
        .append(text("Test2"))
        .append(text("Test3").decorate(BOLD))
      ).build();

    this.assertParsedEquals(expected, input);
  }

  @Test
  void testErrorOnShorthandAndLongHand() {
    final String input = "<!italic:true>Go decide on something, god dammit!";
    final Component expected = text("<!italic:true>Go decide on something, god dammit!");
    this.assertParsedEquals(expected, input);
  }

  @Test
  void testDecorationShorthandClosing() {
    final String input = "<italic:false>Hello! <italic>spooky</italic> not spooky</italic:false>";
    final Component expected = text().decoration(ITALIC, false)
      .append(text("Hello! "))
      .append(text().decoration(ITALIC, true)
        .append(text("spooky")))
      .append(text(" not spooky"))
      .build();
    this.assertParsedEquals(expected, input);
  }
}
