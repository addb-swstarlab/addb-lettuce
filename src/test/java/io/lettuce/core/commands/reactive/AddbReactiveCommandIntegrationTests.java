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
package io.lettuce.core.commands.reactive;

import javax.inject.Inject;

import io.lettuce.core.FpWriteArgs;
import io.lettuce.core.commands.AddbCommandIntegrationTests;
import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import io.lettuce.core.KeyValue;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.reactive.RedisReactiveCommands;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.commands.StringCommandIntegrationTests;
import io.lettuce.test.ReactiveSyncInvocationHandler;

/**
 * @author Doyoung Kim
 */
class AddbReactiveCommandIntegrationTests extends AddbCommandIntegrationTests {

    private final RedisCommands<String, String> redis;
    private final RedisReactiveCommands<String, String> reactive;

    @Inject
    AddbReactiveCommandIntegrationTests(StatefulRedisConnection<String, String> connection) {
        super(ReactiveSyncInvocationHandler.sync(connection));
        this.redis = connection.sync();
        this.reactive = connection.reactive();
    }

    @Test
    void fpwrite() {
        FpWriteArgs args = FpWriteArgs.Builder.dataKey("D:{100:1:2}")
                .partitionInfo("1:2")
                .columnCount("4")
                .data("1", "2", "3", "4");

        Mono<String> fpwrite = reactive.fpwrite(args);
        StepVerifier.create(fpwrite.flux().next())
                .expectNext("OK")
                .verifyComplete();
    }
}
