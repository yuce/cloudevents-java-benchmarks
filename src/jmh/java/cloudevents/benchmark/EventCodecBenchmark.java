// Copyright 2020 Scale Plan Yazılım A.Ş.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package cloudevents.benchmark;

import io.cloudevents.core.provider.EventFormatProvider;
import io.cloudevents.jackson.JsonFormat;
import io.scaleplan.spce.codecs.Avro;
import io.scaleplan.spce.codecs.AvroSpce;
import io.scaleplan.spce.codecs.Json;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(value = 2)
@Warmup(iterations = 2)
public class EventCodecBenchmark {
    private static final byte[] text = eventText();

    // CloudEvents SDK stuff
    private static final JsonFormat format = getFormat();
    private static final io.cloudevents.CloudEvent sdkEvent = format.deserialize(text);

    // SPCE stuff
    private static final io.scaleplan.spce.CloudEvent spceEvent = Json.decode(text);
    private static final List<io.scaleplan.spce.CloudEvent> spceEventBundle10 = eventBundle(10);

    private static final byte[] spceJsonEncodedEvent = Json.encode(spceEvent);
    private static final byte[] spceJsonEncodedEventBundle10 = Json.encode(spceEventBundle10);

    private static final byte[] spceAvroEncodedEvent = Avro.encode(spceEvent);
    private static final byte[] spceAvroSpceEncodedEvent = AvroSpce.encode(spceEvent);
    private static final byte[] spceAvroSpceEncodedEventBundle10 = AvroSpce.encode(spceEventBundle10);

    // CloudEvents SDK JSON Benchmarks

    @Benchmark
    public void benchmarkSdkJsonDecodeEvent(Blackhole blackhole) {
        blackhole.consume(format.deserialize(text));
    }

    @Benchmark
    public void benchmarkSdkJsonEncodeEvent(Blackhole blackhole) {
        blackhole.consume(format.withForceJsonDataToBase64().serialize(sdkEvent));
    }

    // SPCE JSON Benchmarks

    @Benchmark
    public void benchmarkSpceJsonDecodeEvent(Blackhole blackhole) {
        blackhole.consume(Json.decode(spceJsonEncodedEvent));
    }

    @Benchmark
    public void benchmarkSpceJsonEncodeEvent(Blackhole blackhole) {
        blackhole.consume(Json.encode(spceEvent));
    }

    @Benchmark
    public void benchmarkSpceJsonDecodeEventBundle10(Blackhole blackhole) {
        blackhole.consume(Json.decodeBatch(spceJsonEncodedEventBundle10));
    }

    @Benchmark
    public void benchmarkSpceJsonEncodeEventBundle10(Blackhole blackhole) {
        blackhole.consume(Json.encode(spceEventBundle10));
    }

    // SPCE Avro Benchmarks (CloudEvents Avro schema)

    @Benchmark
    public void benchmarkAvroDecodeEvent(Blackhole blackhole) {
        blackhole.consume(Avro.decode(spceAvroEncodedEvent));
    }

    @Benchmark
    public void benchmarkAvroEncodeEvent(Blackhole blackhole) {
        blackhole.consume(Avro.encode(spceEvent));
    }

    // SPCE AvroSpce Benchmarks (SPCE Avro schema)

    @Benchmark
    public void benchmarkAvroSpceDecodeEvent(Blackhole blackhole) {
        blackhole.consume(AvroSpce.decode(spceAvroSpceEncodedEvent));
    }

    @Benchmark
    public void benchmarkAvroSpceEncodeEvent(Blackhole blackhole) {
        blackhole.consume(AvroSpce.encode(spceEvent));
    }

    @Benchmark
    public void benchmarkAvroSpceDecodeEventBundle10(Blackhole blackhole) {
        blackhole.consume(AvroSpce.decodeBatch(spceAvroSpceEncodedEventBundle10));
    }

    @Benchmark
    public void benchmarkAvroSpceEncodeEventBundle10(Blackhole blackhole) {
        blackhole.consume(AvroSpce.encode(spceEventBundle10));
    }

    // Utility

    private static byte[] eventText() {
        String text = "{\n" +
                "  \"specversion\": \"1.0\",\n" +
                "  \"id\": \"1\",\n" +
                "  \"type\": \"mock.test\",\n" +
                "  \"source\": \"http://localhost/source\",\n" +
                "  \"dataschema\": \"http://localhost/schema\",\n" +
                "  \"datacontenttype\": \"application/octet-stream\",\n" +
                "  \"data_base64\": \"e30=\",\n" +
                "  \"subject\": \"sub\",\n" +
                "  \"external1\": \"foobar\",\n" +
                "  \"time\": \"2018-04-26T14:48:09+02:00\"\n" +
                "}";
        return text.getBytes();
    }

    private static List<io.scaleplan.spce.CloudEvent> eventBundle(int n) {
        List<io.scaleplan.spce.CloudEvent> bundle = new ArrayList<>(n);
        for (int i = 0; i < n; i++) bundle.add(spceEvent);
        return bundle;
    }

    private static JsonFormat getFormat() {
        return (JsonFormat) EventFormatProvider.getInstance().resolveFormat(JsonFormat.CONTENT_TYPE);
    }

}
