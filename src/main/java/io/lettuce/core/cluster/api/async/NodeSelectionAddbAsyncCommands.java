/*
 * Copyright 2017-2019 the original author or authors.
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
package io.lettuce.core.cluster.api.async;

import io.lettuce.core.addb.FpScanArgs;
import io.lettuce.core.addb.FpWriteArgs;
import io.lettuce.core.addb.MetakeysArgs;

import java.util.List;

/**
 * Asynchronous executed commands on a node selection for Strings.
 *
 * @author Doyoung Kim
 * @since 5.1.8-ADDB
 */
public interface NodeSelectionAddbAsyncCommands {

    /**
     * Add relational model and store the row-column data on relation.
     *
     * @param fpWriteArgs dataKey, partitionInfo, columnCount, data
     *
     * @return String simple-string-reply {@code OK} if {@code SET} was executed correctly.
     */
    AsyncExecutions<String> fpwrite(FpWriteArgs fpWriteArgs);

    /**
     * Scan row-column data on relation from ADDB.
     *
     * @param fpScanArgs dataKey, partitionInfo, columnCount, data
     *
     * @return List&lt;String&gt; array-reply list of row-column data.
     */
    AsyncExecutions<List<String>> fpscan(FpScanArgs fpScanArgs);

    /**
     * Scan meta-data on relation from ADDB.
     *
     * @param metakeysArgs pattern, statements tree
     *
     * @return List&lt;String&gt; array-reply list of meta-data keys.
     */
    AsyncExecutions<List<String>> metakeys(MetakeysArgs metakeysArgs);
}
