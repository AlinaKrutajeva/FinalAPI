package finalApi.helpers;

import java.util.Random;

public class RandomDataGenerator {

        public class RandomSymbolGenerator {
            private static final String ALPHANUMERIC = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

            public static String generateRandomAlphanumeric(int length) {
                Random random = new Random();
                return random.ints(length, 0, ALPHANUMERIC.length())
                        .mapToObj(ALPHANUMERIC::charAt)
                        .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                        .toString();
            }

            public static void main(String[] args) {
                System.out.println("Random Alphanumeric String: " + generateRandomAlphanumeric(24));
            }
        }

    }
