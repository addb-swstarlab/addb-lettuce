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
package io.lettuce.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.lettuce.core.internal.LettuceAssert;
import io.lettuce.core.protocol.CommandArgs;

/**
 * Argument list builder for the ADDB FPSCAN command. Static import the methods from {@link Builder} and
 * chain the method calls: {@code dataKey("D:{100:1:2}").columns("1", "2", "3")}.
 *
 * <p>
 * {@link FpScanArgs} is a mutable object and instances should be used only once to avoid shared mutable state.
 * </p>
 *
 * @author Doyoung Kim
 */
public class FpScanArgs implements CompositeArgument {
    private String dataKey;
    private List<String> columns;

    /**
     * Builder entry points for {@link FpScanArgs}.
     */
    public static class Builder {

        /**
         * Utility constructor.
         */
        private Builder() {
        }

        /**
         * Creates new {@link FpScanArgs} setting {@literal dataKey} using string.
         *
         * @return new {@link FpScanArgs} with {@literal dataKey} set.
         * @see FpScanArgs#dataKey
         */
        public static FpScanArgs dataKey(String dataKey) {
            return new FpScanArgs().dataKey(dataKey);
        }

        /**
         * Creates new {@link FpScanArgs} setting {@literal columns} using string.
         *
         * @return new {@link FpScanArgs} with {@literal columns} set.
         * @see FpScanArgs#columns
         */
        public static FpScanArgs columns(List<String> columns) {
            return new FpScanArgs().columns(columns);
        }

        /**
         * Creates new {@link FpScanArgs} setting {@literal data} using string.
         *
         * @return new {@link FpScanArgs} with {@literal data} set.
         * @see FpScanArgs#columns
         */
        public static FpScanArgs columns(String... columns) {
            return new FpScanArgs().columns(columns);
        }
    }

    /**
     * Specify Data key for storing relational data.
     *
     * @param dataKey must not be {@literal null}.
     * @return {@code this} {@link FpScanArgs}.
     */
    public FpScanArgs dataKey(String dataKey) {
        LettuceAssert.notNull(dataKey, "dataKey must not be null");

        this.dataKey = dataKey;
        return this;
    }

    /**
     * Specify column data for storing relational data.
     *
     * @param columns must not be {@literal null}.
     * @return {@code this} {@link FpScanArgs}.
     */
    public FpScanArgs columns(List<String> columns) {
        LettuceAssert.notNull(columns, "columns must not be null");

        return this.columns(toStringArray(columns));
    }

    /**
     * Specify column data for storing relational data.
     *
     * @param columns must not be {@literal null}.
     * @return {@code this} {@link FpScanArgs}.
     */
    public FpScanArgs columns(String... columns) {

        LettuceAssert.notNull(columns, "data must not be null");

        this.columns = new ArrayList<>(columns.length);
        this.columns.addAll(Arrays.asList(columns));

        return this;
    }

    private static String[] toStringArray(List<String> data) {
        String[] result = new String[data.size()];
        for (int i = 0; i < data.size(); i++) {
            result[i] = data.get(i);
        }
        return result;
    }

    public <K, V> void build(CommandArgs<K, V> args) {
        args.add(dataKey);
        args.add(String.join(",", columns));
    }
}
