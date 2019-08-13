/*
 * Copyright 2011-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.lettuce.core.addb;

import io.lettuce.core.CompositeArgument;
import io.lettuce.core.internal.LettuceAssert;
import io.lettuce.core.protocol.CommandArgs;

/**
 * Argument list builder for the ADDB METAKEYS command. Static import the methods from {@link Builder} and
 * chain the method calls: {@code pattern("M:{100:2:0}").statements("3*2*EqualTo:$1*2*EqualTo:0*2*EqualTo:Or:$")}.
 *
 * <p>
 * {@link MetakeysArgs} is a mutable object and instances should be used only once to avoid shared mutable state.
 * </p>
 *
 * @author Doyoung Kim
 */
public class MetakeysArgs implements CompositeArgument {
    private String pattern;
    private String statements;

    /**
     * Builder entry points for {@link MetakeysArgs}.
     */
    public static class Builder {

        /**
         * Utility constructor.
         */
        private Builder() {
        }

        /**
         * Creates new {@link MetakeysArgs} setting {@literal pattern} using string.
         *
         * @return new {@link MetakeysArgs} with {@literal pattern} set.
         * @see MetakeysArgs#pattern
         */
        public static MetakeysArgs pattern(String pattern) {
            return new MetakeysArgs().pattern(pattern);
        }

        /**
         * Creates new {@link MetakeysArgs} setting {@literal statements} using string.
         *
         * @return new {@link MetakeysArgs} with {@literal statements} set.
         * @see MetakeysArgs#statements
         */
        public static MetakeysArgs statements(String statements) {
            return new MetakeysArgs().statements(statements);
        }
    }

    /**
     * Specify Pattern for scanning relational meta-data.
     *
     * @param pattern must not be {@literal null}.
     * @return {@code this} {@link MetakeysArgs}.
     */
    public MetakeysArgs pattern(String pattern) {
        LettuceAssert.notNull(pattern, "pattern must not be null");

        this.pattern = pattern;
        return this;
    }

    /**
     * Specify Statements for tree-scanning relational meta-data.
     *
     * @param statements must not be {@literal null}.
     * @return {@code this} {@link MetakeysArgs}.
     */
    public MetakeysArgs statements(String statements) {
        LettuceAssert.notNull(statements, "statements must not be null");

        this.statements = statements;
        return this;
    }

    public <K, V> void build(CommandArgs<K, V> args) {
        args.add(pattern);
        args.add(statements);
    }
}
