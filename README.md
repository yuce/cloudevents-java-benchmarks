# CloudEvents Java Benchmarks

Benchmarks of CloudEvents implementations for Java.

## Usage

Requirements:

* JDK 8

Run `make benchmark` on Linux, MacOS, etc. On Windows `gradlew --no-daemon jmh` should work.

## Benchmarks

### CloudEvents SDK for Java

Official CloudEvents SDK for Java: https://github.com/cloudevents/sdk-java

## JSON

CloudEvents 1.0 JSON spec.

* `benchmarkSdkJsonDecodeEvent`: Decode a single event
* `benchmarkSdkJsonEncodeEvent`: Encode a single event

## ScalePlan CloudEvents for Java

SPCE CloudEvents for Java: https://github.com/scaleplandev/spce-java

### JSON

CloudEvents 1.0 JSON spec.

* `benchmarkSpceJsonDecodeEvent`: Decode a single event
* `benchmarkSpceJsonEncodeEvent`: Encode a single event
* `benchmarkSpceJsonDecodeEventBundle10`: Decode a bundle of 10 events
* `benchmarkSpceJsonEncodeEventBundle10`: Encode a bundle of 10 events

### Avro

Using CloudEvents 1.0 Avro schema at https://github.com/cloudevents/spec/blob/v1.0/spec.avsc

* `benchmarkAvroDecodeEvent`: Decode a single event
* `benchmarkAvroEncodeEvent`: Encode a single event

### Avro SPCE

Using SPCE 1.0 Avro schema at: https://github.com/scaleplandev/spce-java/blob/master/etc/cloudevents_spce_spec.avsc

* `benchmarkAvroSpceDecodeEvent`: Decode a single event
* `benchmarkAvroSpceEncodeEvent`: Encode a single event
* `benchmarkAvroSpceDecodeEventBundle10`: Decode a bundle of 10 events
* `benchmarkAvroSpceEncodeEventBundle10`: Encode a bundle of 10 events

## Sample Result

Below is a result from a typical run on my system. The exact numbers may be different on your system, but the overall picture should be the same.

    Benchmark                                                  Mode  Cnt    Score    Error   Units
    EventCodecBenchmark.benchmarkAvroDecodeEvent              thrpt   10  253.527 ±  8.203  ops/ms
    EventCodecBenchmark.benchmarkAvroEncodeEvent              thrpt   10  269.207 ±  9.189  ops/ms
    EventCodecBenchmark.benchmarkAvroSpceDecodeEvent          thrpt   10  461.694 ± 16.964  ops/ms
    EventCodecBenchmark.benchmarkAvroSpceDecodeEventBundle10  thrpt   10   39.816 ±  7.879  ops/ms
    EventCodecBenchmark.benchmarkAvroSpceEncodeEvent          thrpt   10  408.037 ± 17.207  ops/ms
    EventCodecBenchmark.benchmarkAvroSpceEncodeEventBundle10  thrpt   10   40.102 ±  3.235  ops/ms
    EventCodecBenchmark.benchmarkSdkJsonDecodeEvent           thrpt   10  268.276 ± 16.190  ops/ms
    EventCodecBenchmark.benchmarkSdkJsonEncodeEvent           thrpt   10  319.856 ± 12.765  ops/ms
    EventCodecBenchmark.benchmarkSpceJsonDecodeEvent          thrpt   10  519.292 ± 19.978  ops/ms
    EventCodecBenchmark.benchmarkSpceJsonDecodeEventBundle10  thrpt   10   50.152 ± 10.112  ops/ms
    EventCodecBenchmark.benchmarkSpceJsonEncodeEvent          thrpt   10  942.562 ± 16.331  ops/ms
    EventCodecBenchmark.benchmarkSpceJsonEncodeEventBundle10  thrpt   10   98.450 ±  5.817  ops/ms

## License

(c) 2020 Scale Plan Yazılım A.Ş. https://scaleplan.io

Licensed under [Apache 2.0](LICENSE). See the [LICENSE](LICENSE).

    Copyright 2020 Scale Plan Yazılım A.Ş.
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
        http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
