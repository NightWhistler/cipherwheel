import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Analyze {

    public static void main(String[] a) {
        try {
            new Analyze();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Result parseLine(String line) {
        String[] r = line.split ("\\|");
//        50|51|52|AAV,TRVFBLBDVE
//
        int[] order = new int[]{Integer.valueOf(r[0]), Integer.valueOf(r[1]), Integer.valueOf(r[2])};

        return new Result(new Break.Setup(order, r[3]), r[4]);
    }

    public Analyze() throws IOException {
        Stream<String> lines = Files.lines(Paths.get(Break.LOCATION + "/result"));
        Stream<Result> s = lines.map(this::parseLine);

        System.out.println("Searching ...");
        FrequencyAnalysis frequencyAnalysis = new FrequencyAnalysis(FrequencyAnalysis.simple(), 0.8);

        Map<Break.Setup, String> answers =
                s.filter(result -> frequencyAnalysis.analyse(result.line))
//                        .filter(result -> frequencyAnalysis.containsKeywords(result.line))
                        .collect(Collectors.toMap(r -> r.setup, r -> r.line)
                        );

        System.out.println("Found " + answers.values().size());
        answers.forEach((k, v) -> {
            System.out.println(k + " -> " + v);
        });
        if (answers.isEmpty()) {
            throw new IllegalStateException("Did not find anything ...");
        }

    }

    static public class Result {
        Break.Setup setup;
        String line;

        public Result(Break.Setup setup, String line) {
            this.setup = setup;
            this.line = line;
        }
    }
}
