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
package io.lettuce.core.cluster.commands.reactive;

import javax.inject.Inject;

import io.lettuce.core.addb.FpScanArgs;
import io.lettuce.core.addb.FpWriteArgs;
import io.lettuce.core.addb.MetakeysArgs;
import io.lettuce.core.cluster.api.reactive.RedisAdvancedClusterReactiveCommands;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.lettuce.core.TestSupport;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.test.LettuceExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * @author Doyoung Kim
 */
@ExtendWith(LettuceExtension.class)
class AddbClusterReactiveCommandIntegrationTests extends TestSupport {

    private final StatefulRedisClusterConnection<String, String> connection;
    private RedisAdvancedClusterReactiveCommands<String, String> reactive;

    @Inject
    AddbClusterReactiveCommandIntegrationTests(StatefulRedisClusterConnection<String, String> connection) {
        this.connection = connection;
        this.reactive = connection.reactive();
    }

    @BeforeEach
    void setUp() {
        this.reactive.flushall().block();
    }

    @Test
    void fpwrite() {
        FpWriteArgs args = FpWriteArgs.Builder.dataKey("D:{100:1:2}")
                .partitionInfo("1:2")
                .columnCount("4")
                .data("D1", "D2", "D3", "D4");

        Mono<String> fpwrite = reactive.fpwrite(args);
        StepVerifier.create(fpwrite.flux())
                .expectNext("OK")
                .verifyComplete();
    }

    @Test
    void fpscan() {
        FpWriteArgs wargs = FpWriteArgs.Builder.dataKey("D:{100:1:2}")
                .partitionInfo("1:2")
                .columnCount("4")
                .data("D1", "D2", "D3", "D4");
        Mono<String> fpwrite = reactive.fpwrite(wargs);
        fpwrite.subscribe();

        FpScanArgs sargs = FpScanArgs.Builder.dataKey("D:{100:1:2}")
                .columns("1", "2", "3", "4");
        Flux<String> fpscan = reactive.fpscan(sargs);
        StepVerifier.create(fpscan)
                .expectNext("D1", "D2", "D3", "D4")
                .verifyComplete();
    }

    @Test
    void metakeys() {
        FpWriteArgs wargs = FpWriteArgs.Builder.dataKey("D:{100:1:2}")
                .partitionInfo("1:2")
                .columnCount("4")
                .data("D1", "D2", "D3", "D4");
        Mono<String> fpwrite = reactive.fpwrite(wargs);
        fpwrite.subscribe();

        MetakeysArgs margs = MetakeysArgs.Builder.pattern("*")
                .statements("D1*1*EqualTo:$D2*2*EqualTo:$");
        Flux<String> metakeys = reactive.metakeys(margs);
        StepVerifier.create(metakeys)
                .expectNext("M:{100:1:2}")
                .verifyComplete();
    }
}
