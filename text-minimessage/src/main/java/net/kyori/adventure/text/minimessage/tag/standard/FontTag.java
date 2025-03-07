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

import net.kyori.adventure.key.InvalidKeyException;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.minimessage.Context;
import net.kyori.adventure.text.minimessage.ParsingException;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import org.intellij.lang.annotations.Subst;

/**
 * A decoration that applies a font name.
 *
 * @since 4.10.0
 */
public final class FontTag {
  public static final String FONT = "font";

  private FontTag() {
  }

  static Tag create(final ArgumentQueue args, final Context ctx) throws ParsingException {
    final Key font;
    final @Subst("empty") String valueOrNamespace = args.popOr("A font tag must have either arguments of either <value> or <namespace:value>").value();
    try {
      if (!args.hasNext()) {
        font = Key.key(valueOrNamespace);
      } else {
        @Subst("empty") final String fontKey = args.pop().value();
        font = Key.key(valueOrNamespace, fontKey);
      }
    } catch (final InvalidKeyException ex) {
      throw ctx.newException(ex.getMessage(), args);
    }

    return Tag.styling(builder -> builder.font(font));
  }
}
