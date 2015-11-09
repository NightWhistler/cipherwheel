import java.util.HashMap;
import java.util.Map;

public class FrequencyAnalysis {

    private static final Map<String, Double> frequency = new HashMap<>();

    private final double margin;

    public FrequencyAnalysis() {
        this(FrequencyAnalysis.custom(), 0.35);
    }

    public FrequencyAnalysis(Map<String,Double> alphabet , double margin) {
        this.margin = margin;
        this.addFrequence(alphabet);
    }

    public void addFrequence(Map<String, Double> alpabet) {
        for (Map.Entry<String, Double> entry : alpabet.entrySet()) {
            frequency.put(entry.getKey(), entry.getValue() - entry.getValue() * margin);
        }
    }

    public boolean analyse(String plaintext) {
        for (Map.Entry<String, Double> e : frequency.entrySet()) {
            int cnt = plaintext.length() - plaintext.replace(e.getKey(), "").length();
            if (plaintext.length() * (e.getValue()) > cnt) {
//                System.out.println("Failed " + cnt  + " of " + e.getKey() + ", expected a least " + plaintext.length() * e.getValue() + " ");
                return false;
            }
        }
        return true;
    }


    //    String[] words = new String[]{"THE", "BE", "AN", "IN"};
    String[] words = new String[]{"THE"};

    public boolean containsKeywords(String bf) {
        for (String w : words) {
            if (!bf.contains(w)) {
                return false;
            }
        }
        return true;
    }


    // https://en.wikipedia.org/wiki/Letter_frequency#Relative_frequencies_of_letters_in_the_English_language
    public static Map<String, Double> fullDutch() {
        Map<String, Double> alpabet = new HashMap<>();
        alpabet.put("E", 0.01891d);
        alpabet.put("N", 0.010032d);
        alpabet.put("A", 0.07486d);
        alpabet.put("T", 0.0679d);
        alpabet.put("I", 0.06499d);
        alpabet.put("R", 0.06411d);
        alpabet.put("O", 0.06063d);
        alpabet.put("D", 0.05933d);
        alpabet.put("S", 0.0373d);
        alpabet.put("L", 0.03568d);
        alpabet.put("G", 0.03403d);
        alpabet.put("V", 0.0285d);
        alpabet.put("H", 0.02380d);
        alpabet.put("K", 0.02248d);
        alpabet.put("M", 0.02213d);
        alpabet.put("U", 0.0199d);
        alpabet.put("B", 0.01584d);
        alpabet.put("P", 0.0157d);
        alpabet.put("W", 0.0152d);
        alpabet.put("J", 0.0146d);
        alpabet.put("Z", 0.0139d);
        alpabet.put("C", 0.01242d);
        alpabet.put("F", 0.00805d);
        alpabet.put("X", 0.00036d);
        alpabet.put("Y", 0.00035d);
        alpabet.put("Q", 0.00009d);
        return alpabet;
    }

    public static Map<String, Double> fullEnglish() {
        Map<String, Double> alpabet = new HashMap<>();
        alpabet.put("A", 0.08167d);
        alpabet.put("B", 0.01492d);
        alpabet.put("C", 0.02782d);
        alpabet.put("D", 0.04253d);
        alpabet.put("E", 0.12702d);
        alpabet.put("F", 0.02228d);
        alpabet.put("G", 0.02015d);
        alpabet.put("H", 0.06094d);
        alpabet.put("I", 0.06966d);
        alpabet.put("J", 0.00153d);
        alpabet.put("K", 0.00772d);
        alpabet.put("L", 0.04025d);
        alpabet.put("M", 0.02406d);
        alpabet.put("N", 0.06749d);
        alpabet.put("O", 0.07507d);
        alpabet.put("P", 0.01929d);
        alpabet.put("Q", 0.00095d);
        alpabet.put("R", 0.05987d);
        alpabet.put("S", 0.06327d);
        alpabet.put("T", 0.09056d);
        alpabet.put("U", 0.02758d);
        alpabet.put("V", 0.00978d);
        alpabet.put("W", 0.02361d);
        alpabet.put("X", 0.00150d);
        alpabet.put("Y", 0.01974d);
        alpabet.put("Z", 0.00074d);
        return alpabet;
    }

    public static Map<String, Double> custom() {
        Map<String, Double> alpabet = new HashMap<>();
        alpabet.put("A", 0.08167d);
        alpabet.put("C", 0.02782d);
        alpabet.put("D", 0.04253d);
        alpabet.put("E", 0.12702d);
        alpabet.put("F", 0.02228d);
        alpabet.put("L", 0.04025d);
        alpabet.put("N", 0.06749d);
        alpabet.put("O", 0.07507d);
        alpabet.put("T", 0.09056d);
        return alpabet;
    }

    public static Map<String, Double> simple() {
        Map<String, Double> alpabet = new HashMap<>();
        alpabet.put("E", 0.12702d);
        alpabet.put("O", 0.07507d);
        return alpabet;
    }

}
