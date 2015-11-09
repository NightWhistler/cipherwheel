import java.util.HashMap;
import java.util.Map;
import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.StreamSupport;

public class Wheels {

    public static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final Map<Integer, String> wheels = new HashMap<>();;
    private final Map<Integer, Map<Character, Integer>> zipped = new HashMap<>();
    private AtomicBoolean zippedWheels;

    public static String sanitize(String wheel) {
        String w = wheel.replace(" ", "").toUpperCase();
        if (w.length() != 26) throw new IllegalStateException();
        for (char letter : ALPHABET.toCharArray()) {
            if (!w.contains("" + letter)) {
                throw new IllegalStateException("wheel [" + wheel + "] does not contain character: " + letter);
            }
        }
        return w;
    }

    public Wheels () {
        wheels.put(60, sanitize(" JLNM OQPR TSUW VXZY BACE DFHG IK"));
        wheels.put(71, sanitize(" YVWX ZAEB CDFJ GHIK OLMN PTQR SU"));
        wheels.put(62, sanitize(" GIKM OQSU WYBL FHJD NPRT VXZA CE"));
        wheels.put(50, sanitize(" ABCD EFGH IJKL MNOP QRST UVWX YZ"));
        wheels.put(61, sanitize(" ZXVT RPND JHFL BYWU SQOM KIGE CA"));
        wheels.put(70, sanitize(" KJIH GFED CBAZ YXWV UTSR QPON ML"));
        wheels.put(53, sanitize(" BEHG FILK JMPO NQTS RUXW VZYA DC"));
        wheels.put(72, sanitize(" ZXWV YUSR QTPN MLOK IHGJ FDCB EA"));
        wheels.put(63, sanitize(" HKNL JMPQ ORUS TWZX VBYA DECF IG"));
        wheels.put(51, sanitize(" PNOQ TRSU XVWZ YADC BEHF GILJ KM"));
        wheels.put(52, sanitize(" SRTQ ONPM KJLI GFHE BCDA YZWV XU"));
        wheels.put(73, sanitize(" TUVS PQRO LMNK HIJG DEFB CAXY ZW"));

        for (Map.Entry<Integer, String> wheel : this.wheels.entrySet()) {
            zipped.put(wheel.getKey(), zipWithIndex(wheel.getValue()));
        }
//        StreamSupport.
    }

    public Map<Integer, String> getWheels() {
        return wheels;
    }


    public static Map<Character, Integer> zipWithIndex(String wheel) {
        Map<Character, Integer> ww = new HashMap<>();
        for (int i = 0; i < wheel.length(); i++) {
            ww.put(wheel.charAt(i), i);
        }
        return ww;
    }

    public int getZippedWheels(int i, char c) {
        return zipped.get(i).get(c);
    }

    // Zipping Spliterator

//    class Zip implements Spliterator<T> {
//
//    }
}
