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
package io.lettuce.core.commands;

import static org.assertj.core.api.Assertions.assertThat;

import javax.inject.Inject;

import io.lettuce.core.*;
import io.lettuce.core.addb.FpScanArgs;
import io.lettuce.core.addb.FpWriteArgs;
import io.lettuce.core.addb.MetakeysArgs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;

import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.test.LettuceExtension;

import java.util.List;

/**
 * @author Doyoung Kim
 */
@ExtendWith(LettuceExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AddbCommandIntegrationTests extends TestSupport {

    private final RedisCommands<String, String> redis;

    @Inject
    protected AddbCommandIntegrationTests(RedisCommands<String, String> redis) {
        this.redis = redis;
    }

    @BeforeEach
    void setUp() {
        this.redis.flushall();
    }

    @Test
    void fpwrite() {
        FpWriteArgs args = FpWriteArgs.Builder.dataKey("D:{100:1:2}")
                .partitionInfo("1:2")
                .columnCount("4")
                .data("D1", "D2", "D3", "D4");
        assertThat(redis.fpwrite(args)).isEqualTo("OK");
    }

    @Test
    void fpscan() {
        FpWriteArgs wargs = FpWriteArgs.Builder.dataKey("D:{100:1:2}")
                .partitionInfo("1:2")
                .columnCount("4")
                .data("D1", "D2", "D3", "D4");
        redis.fpwrite(wargs);

        FpScanArgs sargs = FpScanArgs.Builder.dataKey("D:{100:1:2}")
                .columns("1", "2", "3", "4");
        List<String> results = redis.fpscan(sargs);
        assertThat(results).hasSize(4);
        assertThat(results.contains("D1")).isTrue();
        assertThat(results.contains("D2")).isTrue();
        assertThat(results.contains("D3")).isTrue();
        assertThat(results.contains("D4")).isTrue();
    }

    @Test
    void metakeys() {
        FpWriteArgs wargs = FpWriteArgs.Builder.dataKey("D:{100:1:2}")
                .partitionInfo("1:2")
                .columnCount("4")
                .data("D1", "D2", "D3", "D4");
        redis.fpwrite(wargs);

        MetakeysArgs margs = MetakeysArgs.Builder.pattern("*")
                .statements("D1*1*EqualTo:$D2*2*EqualTo:$");
        List<String> results = redis.metakeys(margs);
        assertThat(results).hasSize(1);
        assertThat(results.contains("M:{100:1:2}")).isTrue();
    }
}
