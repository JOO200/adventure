package net.kyori.adventure.text.minimessage.tag.standard;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.TestBase;

import org.junit.jupiter.api.Test;

import static net.kyori.adventure.text.Component.text;

class InsertionTagTest extends TestBase {

  @Test
  void testInsertion() {
    final String input = "Click <insert:test>this</insert> to insert!";
    final Component expected = text("Click ")
      .append(text("this").insertion("test"))
      .append(text(" to insert!"));

    this.assertParsedEquals(expected, input);
  }
}
