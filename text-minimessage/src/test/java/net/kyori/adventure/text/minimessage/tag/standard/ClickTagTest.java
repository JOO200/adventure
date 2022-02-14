package net.kyori.adventure.text.minimessage.tag.standard;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.TestBase;

import org.junit.jupiter.api.Test;

import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.event.ClickEvent.runCommand;

class ClickTagTest extends TestBase {

  @Test
  void testClick() {
    final String input = "<click:run_command:test>TEST";
    final Component expected = text("TEST").clickEvent(runCommand("test"));

    this.assertParsedEquals(expected, input);
  }

  @Test
  void testClickExtendedCommand() {
    final String input = "<click:run_command:/test command>TEST";
    final Component expected = text("TEST").clickEvent(runCommand("/test command"));

    this.assertParsedEquals(expected, input);
  }

  @Test
  void testInvalidClick() {
    final String input = "<click:pet_a_kitty:'a very cute one'>best click event";

    final Component expected = text("<click:pet_a_kitty:'a very cute one'>best click event");

    this.assertParsedEquals(expected, input);
  }
}
