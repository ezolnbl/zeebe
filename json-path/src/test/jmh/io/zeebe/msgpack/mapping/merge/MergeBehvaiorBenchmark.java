/*
 * Copyright © 2017 camunda services GmbH (info@camunda.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.zeebe.msgpack.mapping.merge;

import io.zeebe.msgpack.mapping.MappingCtx;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.*;

@BenchmarkMode(Mode.Throughput)
@Fork(1)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
public class MergeBehvaiorBenchmark {

  @Benchmark
  @Threads(1)
  public int overwriteDocument(final MappingCtx mappingCtx, final MsgPackDocuments documents) {
    return mappingCtx.processor.merge(
        documents.sourceDocument, documents.targetDocument, mappingCtx.rootMappings);
  }

  @Benchmark
  @Threads(1)
  public int topLevelMergeViaMappings(
      final MappingCtx mappingCtx, final MsgPackDocuments documents) {
    return mappingCtx.processor.merge(
        documents.sourceDocument, documents.targetDocument, documents.mappings);
  }

  @Benchmark
  @Threads(1)
  public int topLevelMergeDefaultBehavior(
      final MappingCtx mappingCtx, final MsgPackDocuments documents) {
    return mappingCtx.processor.merge(documents.sourceDocument, documents.targetDocument);
  }
}