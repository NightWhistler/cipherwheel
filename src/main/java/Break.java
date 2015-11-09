import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*
 * note:
 * eerste wiel y?
 * twweede wiel w ??
 *
 * fmpp  2x ?
 * dlxx ?
 */
public class Break {

    public static String LOCATION = "/media/jeroen/DATA/tmp/meuk";

    public static void main(String[] arg) {
        String message;
        try {
            message = new String(Files.readAllBytes(Paths.get("src/main/resources/secret")), "UTF-8");
            message = "thelazyfoxjumpsoverthequickbrowndogaapnootmiesdewereldeenpaard".toUpperCase();
//            message = new String(Files.readAllBytes(Paths.get("src/main/resources/plaintext")), "UTF-8");
            message = message.replace("\n", "").replace(" ", "").toUpperCase();
            System.out.println("Decoding message : " + message);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

        new Break(message, new Wheels());
    }

    public Break(String message, Wheels wheels) {

        System.out.println("Using [" + wheels.getWheels().size() + "] wheels.");

        Setup setup = new Setup(new int[]{60, 71, 62}, "DOG");
        String cipher = encode(message, new Secret(setup, wheels));
        String decrypted = decode(cipher, new Secret(setup, wheels));

        String bruteforce = bruteforce(cipher, wheels);

        System.out.println("Message: " + message);
        System.out.println("Cipher: " + cipher);
        System.out.println("Bruteforce: " + bruteforce);
        System.out.println("Decrypted: " + decrypted);

        if (cipher.equals(message)) {
            throw new IllegalStateException("You still need to encrypt!");
        }
        if (!message.equals(decrypted)) {
            throw new IllegalStateException("encode and decode do not work yet. Message [" + message + "] yield cipher [" + cipher + "] but decoded it reads as [" + decrypted + "].");
        }
        if (bruteforce.equals(message)) {
            System.out.println("Well done!");
        } else {
            throw new IllegalStateException("bruteforce does not work yet.");
        }
    }

    public String encode(String plaintext, Secret secret) {
        StringBuilder sb = new StringBuilder();
        for (char c : plaintext.toCharArray()) {
            sb.append(secret.encode(c));
        }
        return sb.toString();
    }

    public String decode(String cipher, Secret secret) {
        StringBuilder sb = new StringBuilder();
        for (char c : cipher.toCharArray()) {
            sb.append(secret.decode(c));
        }
        return sb.toString();
    }

    public String bruteforce(String cipher, Wheels wheels) {
        // all answers that contain "the" are ok!
        Map<Setup, String> answers = new HashMap<>();

        Path path = Paths.get(LOCATION + "/result");
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (int c1 : wheels.getWheels().keySet()) {
                for (int c2 : wheels.getWheels().keySet()) {
                    if (c2 == c1) continue; // make sure we filter out the dups / speeds up a little bit
                    for (int c3 : wheels.getWheels().keySet()) {
                        if (c3 == c1 || c3 == c2) continue;  // make sure we filter out the dups / speeds up a little bit

                        System.out.println("Trying wheel [" + c1 + c2 + c3 + "]");

                        // TODO add multithreading here

                        for (char t1 : Wheels.ALPHABET.toCharArray()) {
                            for (char t2 : Wheels.ALPHABET.toCharArray()) {
                                for (char t3 : Wheels.ALPHABET.toCharArray()) {
                                    String key = "" + t1 + t2 + t3;
                                    Setup setup = new Setup(new int[]{c1, c2, c3}, key);
                                    String plaintext = decode(cipher, new Secret(setup, wheels));

                                    writer.write(String.format("%d|%d|%d|%s|%s\n",c1,c2,c3,key, plaintext));

                                    // TODO pass in a function to do the writing to disk or direct hacking.

//                                    if (frequenceAnalesys(plaintext) && containsKeywords(plaintext)) {
//                                        System.out.println("Found " + plaintext);
//                                        answers.put(x, plaintext);
//                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Found " + answers.values().size());
        answers.forEach((k, v) -> {
            System.out.println(k + " -> " + v);
        });
        if (answers.isEmpty()) {
            throw new IllegalStateException("Did not find anything ...");
        }
        return answers.values().iterator().next();

    }

    /**
     *
     */
    class Secret {
        private final Setup setup;
        private final Wheels wheels;
        String wheel1;
        String wheel2;
        String wheel3;
        // counter to keep even
        int counter = 0;
        // curent wheel locations
        int l1;
        int l2;
        int l3;

        public Secret(Setup setup, Wheels wheels) {
            this.setup = setup;
            this.wheels = wheels;
            wheel1 = wheels.getWheels().get(setup.order[0]);
            wheel2 = wheels.getWheels().get(setup.order[1]);
            wheel3 = wheels.getWheels().get(setup.order[2]);

            l1 = wheels.getZippedWheels(setup.order[0], setup.code.charAt(0));
            l2 = wheels.getZippedWheels(setup.order[1], setup.code.charAt(1));
            l3 = wheels.getZippedWheels(setup.order[2], setup.code.charAt(2));
        }

        private void printLocation() {
            System.out.println("Location 1: " + l1 + " (this is char " + wheel1.charAt(l1) + ").");
            System.out.println("Location 2: " + l2 + " (this is char " + wheel2.charAt(l2) + ").");
            System.out.println("Location 3: " + l3 + " (this is char " + wheel3.charAt(l3) + ").");
        }

        Character encode(Character c) {
            int change = wheels.getZippedWheels(setup.order[2], c) - l3;
            l1 = (l1 + change + 26) % 26;
            l2 = (l2 - change + 26) % 26;
            l3 = (l3 + change + 26) % 26;

            boolean isEven = isEven();

            Character result;
            if (isEven) {
                result = wheel1.charAt(l1);
            } else {
                result = wheel2.charAt(l2);
            }
            counter++;
            return result;
        }

        private boolean isEven() {
            return counter % 2 != 0;
        }

        public Character decode(char c) {
            int change;
            boolean isEven = isEven();

            if (isEven) {
                change = wheels.getZippedWheels(setup.order[0], c) - l1;
            } else {
                change = -1 * (wheels.getZippedWheels(setup.order[1], c) - l2);
            }

            l1 = (l1 + change + 26) % 26;
            l2 = (l2 - change + 26) % 26;
            l3 = (l3 + change + 26) % 26;

            counter++;
            return wheel3.charAt(l3);
        }
    }


    /**
     * defines the setup of the enigma (wheels and code)
     */
    public static class Setup {
        final int[] order;
        final String code;

        public Setup(int[] order, String code) {
            this.order = order;
            this.code = code;
        }

        public int[] getOrder() {
            return order;
        }

        public String getCode() {
            return code;
        }

        @Override
        public String toString() {
            return "" + Arrays.toString(order) + "," + code;
        }
    }

}
