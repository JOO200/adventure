/*
 * This file is part of adventure, licensed under the MIT License.
 *
 * Copyright (c) 2017-2022 KyoriPowered
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package net.kyori.adventure.text.minimessage.tag.standard;

import java.util.HashMap;
import java.util.Map;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.Context;
import net.kyori.adventure.text.minimessage.ParsingException;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A transformation applying a single text color.
 *
 * @since 4.10.0
 */
@ApiStatus.Internal
public final class ColorTagResolver implements TagResolver {
  public static final String HEX = "#";
  public static final String COLOR_3 = "c";
  public static final String COLOR_2 = "colour";
  public static final String COLOR = "color";

  private static final Map<String, TextColor> COLOR_ALIASES = new HashMap<>();

  static {
    COLOR_ALIASES.put("dark_grey", NamedTextColor.DARK_GRAY);
    COLOR_ALIASES.put("grey", NamedTextColor.GRAY);
  }

  private static boolean isColorOrAbbreviation(final String name) {
    return name.equals(COLOR) || name.equals(COLOR_2) || name.equals(COLOR_3);
  }

  ColorTagResolver() {
  }

  @Override
  public @Nullable Tag resolve(final @NotNull String name, final @NotNull ArgumentQueue args, final @NotNull Context ctx) throws ParsingException {
    if (!this.has(name)) {
      return null;
    }

    final String colorName;
    if (isColorOrAbbreviation(name)) {
      colorName = args.popOr("Expected to find a color parameter: <name>|#RRGGBB").lowerValue();
    } else {
      colorName = name;
    }

    final TextColor color;
    if (COLOR_ALIASES.containsKey(colorName)) {
      color = COLOR_ALIASES.get(colorName);
    } else if (colorName.charAt(0) == '#') {
      color = TextColor.fromHexString(colorName);
    } else {
      color = NamedTextColor.NAMES.value(colorName);
    }

    if (color == null) {
      throw ctx.newException("Don't know how to turn '" + colorName + "' into a color");
    }

    return Tag.styling(color);
  }

  @Override
  public boolean has(final @NotNull String name) {
    return isColorOrAbbreviation(name)
      || TextColor.fromHexString(name) != null
      || NamedTextColor.NAMES.value(name) != null
      || COLOR_ALIASES.containsKey(name);
  }
}
