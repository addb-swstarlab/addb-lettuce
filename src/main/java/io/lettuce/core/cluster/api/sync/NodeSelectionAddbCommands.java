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
package io.lettuce.core.cluster.api.sync;

import io.lettuce.core.FpScanArgs;
import io.lettuce.core.FpWriteArgs;

import java.util.List;

/**
 * Synchronous executed commands on a node selection for ADDB.
 *
 * @author Doyoung Kim
 * @since 5.1.8-ADDB
 */
public interface NodeSelectionAddbCommands {

    /**
     * Add relational model and store the row-column data on relation.
     *
     * @param fpWriteArgs dataKey, partitionInfo, columnCount, data
     *
     * @return String simple-string-reply {@code OK} if {@code SET} was executed correctly.
     */
    Executions<String> fpwrite(FpWriteArgs fpWriteArgs);

    /**
     * Scan row-column data on relation from ADDB.
     *
     * @param fpScanArgs dataKey, partitionInfo, columnCount, data
     *
     * @return List&lt;String&gt; array-reply list of row-column data.
     */
    Executions<List<String>> fpscan(FpScanArgs fpScanArgs);
}
