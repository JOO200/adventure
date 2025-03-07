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
package net.kyori.adventure.text.minimessage;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.minimessage.tree.Node;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static java.util.Objects.requireNonNull;

/**
 * not public api.
 *
 * @since 4.10.0
 */
final class MiniMessageImpl implements MiniMessage {
  static final UnaryOperator<Component> DEFAULT_COMPACTING_METHOD = Component::compact;

  static final MiniMessage INSTANCE = new MiniMessageImpl(TagResolver.standard(), false, null, DEFAULT_COMPACTING_METHOD);

  private final boolean strict;
  private final @Nullable Consumer<String> debugOutput;
  private final UnaryOperator<Component> postProcessor;
  final MiniMessageParser parser;

  MiniMessageImpl(final @NotNull TagResolver resolver, final boolean strict, final @Nullable Consumer<String> debugOutput, final @NotNull UnaryOperator<Component> postProcessor) {
    this.parser = new MiniMessageParser(resolver);
    this.strict = strict;
    this.debugOutput = debugOutput;
    this.postProcessor = postProcessor;
  }

  @Override
  public @NotNull Component deserialize(final @NotNull String input) {
    return this.parser.parseFormat(input, this.newContext(input, null));
  }

  @Override
  public @NotNull Component deserialize(final @NotNull String input, final @NotNull TagResolver tagResolver) {
    return this.parser.parseFormat(input, this.newContext(input, requireNonNull(tagResolver, "tagResolver")));
  }

  @Override
  public @NotNull Node deserializeToTree(final @NotNull String input) {
    return this.parser.parseToTree(input, this.newContext(input, null));
  }

  @Override
  public @NotNull Node deserializeToTree(final @NotNull String input, final @NotNull TagResolver tagResolver) {
    return this.parser.parseToTree(input, this.newContext(input, requireNonNull(tagResolver, "tagResolver")));
  }

  @Override
  public @NotNull String serialize(final @NotNull Component component) {
    return MiniMessageSerializer.serialize(requireNonNull(component, "component"));
  }

  @Override
  public @NotNull String escapeTags(final @NotNull String input) {
    return this.parser.escapeTokens(input, this.newContext(input, null));
  }

  @Override
  public @NotNull String escapeTags(final @NotNull String input, final @NotNull TagResolver tagResolver) {
    return this.parser.escapeTokens(input, this.newContext(input, tagResolver));
  }

  @Override
  public @NotNull String stripTags(final @NotNull String input) {
    return this.parser.stripTokens(input, this.newContext(input, null));
  }

  @Override
  public @NotNull String stripTags(final @NotNull String input, final @NotNull TagResolver tagResolver) {
    return this.parser.stripTokens(input, this.newContext(input, tagResolver));
  }

  private @NotNull ContextImpl newContext(final @NotNull String input, final @Nullable TagResolver resolver) {
    requireNonNull(input, "input");
    if (resolver == null) {
      return ContextImpl.of(this.strict, this.debugOutput, input, this, TagResolver.empty(), this.postProcessor);
    } else {
      return ContextImpl.of(this.strict, this.debugOutput, input, this, resolver, this.postProcessor);
    }
  }

  @Override
  public @NotNull Builder toBuilder() {
    return new BuilderImpl(this);
  }

  static final class BuilderImpl implements Builder {
    private TagResolver tagResolver = TagResolver.standard();
    private boolean strict = false;
    private Consumer<String> debug = null;
    private UnaryOperator<Component> postProcessor = DEFAULT_COMPACTING_METHOD;

    BuilderImpl() {
    }

    BuilderImpl(final MiniMessageImpl serializer) {
      this.tagResolver = serializer.parser.tagResolver;
      this.strict = serializer.strict;
      this.debug = serializer.debugOutput;
    }

    @Override
    public @NotNull Builder tags(final @NotNull TagResolver tags) {
      this.tagResolver = requireNonNull(tags, "tags");
      return this;
    }

    @Override
    public @NotNull Builder editTags(final @NotNull Consumer<TagResolver.Builder> adder) {
      requireNonNull(adder, "adder");
      final TagResolver.Builder builder = TagResolver.builder().resolver(this.tagResolver);
      adder.accept(builder);
      this.tagResolver = builder.build();
      return this;
    }

    @Override
    public @NotNull Builder strict(final boolean strict) {
      this.strict = strict;
      return this;
    }

    @Override
    public @NotNull Builder debug(final @Nullable Consumer<String> debugOutput) {
      this.debug = debugOutput;
      return this;
    }

    @Override
    public @NotNull Builder postProcessor(final @NotNull UnaryOperator<Component> postProcessor) {
      this.postProcessor = Objects.requireNonNull(postProcessor, "postProcessor");
      return this;
    }

    @Override
    public @NotNull MiniMessage build() {
      return new MiniMessageImpl(this.tagResolver, this.strict, this.debug, this.postProcessor);
    }
  }
}
