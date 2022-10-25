package persistence;

import model.Entry;
import model.MyJournal;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// A reader that allows for data stored in JSON file to be loaded
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads journal from file and returns it;
    // throws IOException if an error occurs reading data from file
    public MyJournal read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseJournal(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses journal from JSON object and returns it
    private MyJournal parseJournal(JSONObject jsonObject) {
        MyJournal mj = new MyJournal();
        addEntries(mj, jsonObject);
        return mj;
    }

    // MODIFIES: mj
    // EFFECTS: method parses entries from JSON object and adds them to journal
    private void addEntries(MyJournal mj, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("entries");
        for (Object json : jsonArray) {
            JSONObject nextEntry = (JSONObject) json;
            addEntry(mj, nextEntry);
        }
    }

    // MODIFIES: mj
    // EFFECTS: parses entry from JSON object and adds it to journal
    private void addEntry(MyJournal mj, JSONObject jsonObject) {
        String title = jsonObject.getString("title");
        String content = jsonObject.getString("content");
        String date = jsonObject.getString("date");

        Entry entry = new Entry(title, content, date);
        mj.addEntry(entry);
    }
}
