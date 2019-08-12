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
 * Argument list builder for the ADDB FPWRITE command. Static import the methods from {@link Builder} and
 * chain the method calls: {@code dataKey("D:{100:1:2}").partitionInfo("1:2")}.
 *
 * <p>
 * {@link FpWriteArgs} is a mutable object and instances should be used only once to avoid shared mutable state.
 * </p>
 *
 * @author Doyoung Kim
 */
public class FpWriteArgs implements CompositeArgument {
    private String dataKey;
    private String columnCount;
    private String partitionInfo;
    private List<String> data;

    /**
     * Builder entry points for {@link FpWriteArgs}.
     */
    public static class Builder {

        /**
         * Utility constructor.
         */
        private Builder() {
        }

        /**
         * Creates new {@link FpWriteArgs} setting {@literal dataKey} using string.
         *
         * @return new {@link FpWriteArgs} with {@literal dataKey} set.
         * @see FpWriteArgs#dataKey
         */
        public static FpWriteArgs dataKey(String dataKey) {
            return new FpWriteArgs().dataKey(dataKey);
        }

        /**
         * Creates new {@link FpWriteArgs} setting {@literal columnCount} using string.
         *
         * @return new {@link FpWriteArgs} with {@literal columnCount} set.
         * @see FpWriteArgs#columnCount
         */
        public static FpWriteArgs columnCount(String columnCount) {
            return new FpWriteArgs().columnCount(columnCount);
        }

        /**
         * Creates new {@link FpWriteArgs} setting {@literal partitionInfo} using string.
         *
         * @return new {@link FpWriteArgs} with {@literal partitionInfo} set.
         * @see FpWriteArgs#partitionInfo
         */
        public static FpWriteArgs partitionInfo(String partitionInfo) {
            return new FpWriteArgs().partitionInfo(partitionInfo);
        }

        /**
         * Creates new {@link FpWriteArgs} setting {@literal data} using string.
         *
         * @return new {@link FpWriteArgs} with {@literal data} set.
         * @see FpWriteArgs#data
         */
        public static FpWriteArgs data(List<String> data) {
            return new FpWriteArgs().data(data);
        }

        /**
         * Creates new {@link FpWriteArgs} setting {@literal data} using string.
         *
         * @return new {@link FpWriteArgs} with {@literal data} set.
         * @see FpWriteArgs#data
         */
        public static FpWriteArgs data(String... data) {
            return new FpWriteArgs().data(data);
        }
    }

    /**
     * Specify Data key for storing relational data.
     *
     * @param dataKey must not be {@literal null}.
     * @return {@code this} {@link FpWriteArgs}.
     */
    public FpWriteArgs dataKey(String dataKey) {
        LettuceAssert.notNull(dataKey, "dataKey must not be null");

        this.dataKey = dataKey;
        return this;
    }

    /**
     * Specify count of columns for storing relational data.
     *
     * @param columnCount must not be {@literal null}.
     * @return {@code this} {@link FpWriteArgs}.
     */
    public FpWriteArgs columnCount(String columnCount) {
        LettuceAssert.notNull(columnCount, "columnCount must not be null");

        this.columnCount = columnCount;
        return this;
    }

    /**
     * Specify partition info for storing relational data.
     *
     * @param partitionInfo must not be {@literal null}.
     * @return {@code this} {@link FpWriteArgs}.
     */
    public FpWriteArgs partitionInfo(String partitionInfo) {
        LettuceAssert.notNull(partitionInfo, "partitionInfo must not be null");

        this.partitionInfo = partitionInfo;
        return this;
    }

    /**
     * Specify column data for storing relational data.
     *
     * @param data must not be {@literal null}.
     * @return {@code this} {@link FpWriteArgs}.
     */
    public FpWriteArgs data(List<String> data) {
        LettuceAssert.notNull(data, "data must not be null");

        return this.data(toStringArray(data));
    }

    /**
     * Specify column data for storing relational data.
     *
     * @param data must not be {@literal null}.
     * @return {@code this} {@link FpWriteArgs}.
     */
    public FpWriteArgs data(String... data) {

        LettuceAssert.notNull(data, "data must not be null");

        this.data = new ArrayList<>(data.length);
        this.data.addAll(Arrays.asList(data));

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
        args.add(dataKey)
                .add(partitionInfo)
                .add(columnCount)
                .add(0);
        for (String datum : data) {
            args.add(datum);
        }
    }
}
