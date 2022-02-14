package net.kyori.adventure.text.minimessage.tag.standard;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.TestBase;

import org.junit.jupiter.api.Test;

import static net.kyori.adventure.text.Component.empty;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.BLUE;
import static net.kyori.adventure.text.format.NamedTextColor.GREEN;
import static net.kyori.adventure.text.format.NamedTextColor.RED;
import static net.kyori.adventure.text.format.NamedTextColor.YELLOW;
import static net.kyori.adventure.text.format.TextColor.color;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ColorTagTest extends TestBase {

  @Test
  void test() {
    final Component expected1 = empty().color(YELLOW)
      .append(text("TEST"))
      .append(text(" nested").color(GREEN))
      .append(text("Test"));
    final Component expected2 = empty().color(YELLOW)
      .append(text("TEST"))
      .append(empty().color(GREEN)
        .append(text(" nested"))
        .append(text("Test").color(YELLOW))
      );

    final String input1 = "<yellow>TEST<green> nested</green>Test";
    final String input2 = "<yellow>TEST<green> nested<yellow>Test";

    this.assertParsedEquals(expected1, input1);
    this.assertParsedEquals(expected2, input2);
  }

  @Test
  void testBritish() {
    final String input1 = "<grey>This is english"; // no it's british
    final String input2 = "<gray>This is english";
    final String input3 = "<dark_grey>This is still english"; // British is superior english
    final String input4 = "<dark_gray>This is still english";
    final Component out1 = PARSER.deserialize(input1);
    final Component out2 = PARSER.deserialize(input2);
    final Component out3 = PARSER.deserialize(input3);
    final Component out4 = PARSER.deserialize(input4);

    assertEquals(out1, out2);
    assertEquals(out3, out4);
  }

  @Test
  void testBritishColour() {
    final String input1 = "<colour:grey>This is english"; // no it's british
    final String input2 = "<color:gray>This is english";
    final Component out1 = PARSER.deserialize(input1);
    final Component out2 = PARSER.deserialize(input2);

    assertEquals(out1, out2);
  }

  @Test
  void testNewColor() {
    final Component expected1 = empty().color(YELLOW)
      .append(text("TEST"))
      .append(text(" nested").color(GREEN))
      .append(text("Test"));
    final Component expected2 = empty().color(YELLOW)
      .append(text("TEST"))
      .append(empty().color(GREEN)
        .append(text(" nested"))
        .append(text("Test").color(YELLOW))
      );

    final String input1 = "<color:yellow>TEST<color:green> nested</color:green>Test";
    final String input2 = "<color:yellow>TEST<color:green> nested<color:yellow>Test";

    this.assertParsedEquals(expected1, input1);
    this.assertParsedEquals(expected2, input2);
  }

  @Test
  void testHexColor() {
    final Component expected1 = empty().color(color(0xff00ff))
      .append(text("TEST"))
      .append(text(" nested").color(color(0x00ff00)))
      .append(text("Test"));
    final Component expected2 = empty().color(color(0xff00ff))
      .append(text("TEST"))
      .append(empty().color(color(0x00ff00))
        .append(text(" nested"))
        .append(text("Test").color(color(0xff00ff)))
      );

    final String input1 = "<color:#ff00ff>TEST<color:#00ff00> nested</color:#00ff00>Test";
    final String input2 = "<color:#ff00ff>TEST<color:#00ff00> nested<color:#ff00ff>Test";

    this.assertParsedEquals(expected1, input1);
    this.assertParsedEquals(expected2, input2);
  }

  @Test
  void testHexColorShort() {
    final Component expected1 = empty().color(color(0xff00ff))
      .append(text("TEST"))
      .append(text(" nested").color(color(0x00ff00)))
      .append(text("Test"));
    final Component expected2 = empty().color(color(0xff00ff))
      .append(text("TEST"))
      .append(empty().color(color(0x00ff00))
        .append(text(" nested"))
        .append(text("Test").color(color(0xff00ff)))
      );

    final String input1 = "<#ff00ff>TEST<#00ff00> nested</#00ff00>Test";
    final String input2 = "<#ff00ff>TEST<#00ff00> nested<#ff00ff>Test";

    this.assertParsedEquals(expected1, input1);
    this.assertParsedEquals(expected2, input2);
  }

  @Test
  void testHexColorC() {
    final Component expected1 = empty().color(color(0xff00ff))
      .append(text("TEST"))
      .append(text(" nested").color(color(0x00ff00)))
      .append(text("Test"));
    final Component expected2 = empty().color(color(0xff00ff))
      .append(text("TEST"))
      .append(empty().color(color(0x00ff00))
        .append(text(" nested"))
        .append(text("Test").color(color(0xff00ff)))
      );

    final String input1 = "<c:#ff00ff>TEST<c:#00ff00> nested</c>Test";
    final String input2 = "<c:#ff00ff>TEST<c:#00ff00> nested<c:#ff00ff>Test";

    this.assertParsedEquals(expected1, input1);
    this.assertParsedEquals(expected2, input2);
  }

  @Test
  void testAllColorAliases() {
    final Component expectedColorHex = text("AGGRESSIVE TEST").color(color(0xff00ff));
    final String inputColorHex = "<color:#ff00ff>AGGRESSIVE TEST</color>";

    final Component expectedColourHex = text("less aggressive test").color(color(0x00ffff));
    final String inputColourHex = "<colour:#00ffff>less aggressive test</colour>";

    final Component expectedCHex = text("Mildly Aggressive Test").color(color(0x1234de));
    final String inputCHex = "<c:#1234de>Mildly Aggressive Test</c>";

    final Component expectedColorNamed = text("AGGRESSIVE TEST").color(color(RED));
    final String inputColorNamed = "<color:red>AGGRESSIVE TEST</color>";

    final Component expectedColourNamed = text("less aggressive test").color(color(GREEN));
    final String inputColourNamed = "<colour:green>less aggressive test</colour>";

    final Component expectedCNamed = text("Mildly Aggressive Test").color(color(BLUE));
    final String inputCNamed = "<c:blue>Mildly Aggressive Test</c>";

    this.assertParsedEquals(expectedColorHex, inputColorHex);
    this.assertParsedEquals(expectedColourHex, inputColourHex);
    this.assertParsedEquals(expectedCHex, inputCHex);
    this.assertParsedEquals(expectedColorNamed, inputColorNamed);
    this.assertParsedEquals(expectedColourNamed, inputColourNamed);
    this.assertParsedEquals(expectedCNamed, inputCNamed);
  }

  @Test
  void testColorSimple() {
    final String input = "<yellow>TEST";

    this.assertParsedEquals(text("TEST").color(YELLOW), input);
  }
}
