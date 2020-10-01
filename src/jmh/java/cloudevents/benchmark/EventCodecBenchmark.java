package cloudevents.benchmark;

import io.cloudevents.core.provider.EventFormatProvider;
import io.cloudevents.jackson.JsonFormat;
import io.scaleplan.spce.codecs.Avro;
import io.scaleplan.spce.codecs.AvroSpce;
import io.scaleplan.spce.codecs.Json;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(value = 2)
@Warmup(iterations = 2)
public class EventCodecBenchmark {
    private static final byte[] text = sampleEventText();
    //    private static final Event sampleEvent = sampleScalePlanEventWithOptionalAttributes();
    private static final io.scaleplan.spce.CloudEvent scaleplanEvent = Json.decode(text);
    private static final byte[] scaleplanAvroEncodedEvent = Avro.encode(scaleplanEvent);
    private static final byte[] getScaleplanAvroAltEncodedEvent = AvroSpce.encode(scaleplanEvent);

    // CloudEvents SDK stuff
    private static final JsonFormat format = getFormat();
    private static final io.cloudevents.CloudEvent sdkEvent = format.deserialize(text);

    @Benchmark
    public void benchmarkScalePlanDecodeEvent(Blackhole blackhole) {
        blackhole.consume(Json.decode(text));
    }

    /*
    @Benchmark
    public void benchmarkCloudEventsSdkDecodeEvent(Blackhole blackhole) {
        blackhole.consume(format.deserialize(text));
    }
     */

    @Benchmark
    public void benchmarkAvroDecodeEvent(Blackhole blackhole) {
        blackhole.consume(Avro.decode(scaleplanAvroEncodedEvent));
    }

    @Benchmark
    public void benchmarkAvroAltDecodeEvent(Blackhole blackhole) {
        blackhole.consume(AvroSpce.decode(getScaleplanAvroAltEncodedEvent));
    }

    @Benchmark
    public void benchmarkScalePlanEncodeEvent(Blackhole blackhole) {
        blackhole.consume(Json.encode(scaleplanEvent));
    }

    @Benchmark
    public void benchmarkCloudEventsSdkEncodeEvent(Blackhole blackhole) {
        blackhole.consume(format.withForceJsonDataToBase64().serialize(sdkEvent));
    }

    @Benchmark
    public void benchmarkAvroEncodeEvent(Blackhole blackhole) {
        blackhole.consume(Avro.encode(scaleplanEvent));
    }

    @Benchmark
    public void benchmarkAvroAltEncodeEvent(Blackhole blackhole) {
        blackhole.consume(AvroSpce.encode(scaleplanEvent));
    }

    /*
    private static Event sampleEventWsampleEventWithRequiredAttributes() {
        return MutableEventImpl.create(
                "OximeterMeasured",
                "/user/123#",
                "567"
        );
    }

    private static Event sampleScalePlanEventWithOptionalAttributes() {
        MutableEvent event = (MutableEvent)sampleEventWithRequiredAttributes();
        event
                .setTime("2020-07-13T09:15:12Z")
                .setDataContentType("application/json")
                .setData(sampleData())
                .setDataSchema("http://json-schema.org/draft-07/schema#")
                .setSubject("SampleSubject");
        return event;
    }

    private static byte[] sampleData() {
        String text = "{\"user_id\": \"bc1459c5-378d-4835-b4b6-a2c7d9ca75e3\", \"spo2\": 96, \"event\": \"OximiterMeasured\"}";
        return text.getBytes(StandardCharsets.UTF_8);
    }
     */

    private static byte[] sampleEventText() {
        String text = "{\n" +
                "  \"specversion\": \"1.0\",\n" +
                "  \"id\": \"1\",\n" +
                "  \"type\": \"mock.test\",\n" +
                "  \"source\": \"http://localhost/source\",\n" +
                "  \"dataschema\": \"http://localhost/schema\",\n" +
                "  \"datacontenttype\": \"application/json\",\n" +
                "  \"data_base64\": \"e30=\",\n" +
                "  \"subject\": \"sub\",\n" +
                "  \"external1\": \"foobar\",\n" +
                "  \"time\": \"2018-04-26T14:48:09+02:00\"\n" +
                "}";
        return text.getBytes();
    }

    private static JsonFormat getFormat() {
        return (JsonFormat) EventFormatProvider.getInstance().resolveFormat(JsonFormat.CONTENT_TYPE);
    }

}
