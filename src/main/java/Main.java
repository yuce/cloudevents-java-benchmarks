import io.scaleplan.cloudevents.codecs.Json;

public class Main {
    public static void main(String[] args) {
        Json.getDecoder().decode(sampleEventText());
    }

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
                "  \"time\": \"2018-04-26T14:48:09+02:00\"\n" +
                "}";
        return text.getBytes();
    }
}
