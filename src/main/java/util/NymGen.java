package util;

public class NymGen {

    // All the phonemes!!! You get all of them!!!
    static final String[] vowels = new String[]{"a", "e", "i", "o", "u", "y", "ie", "ae", "ou", "ea"};
    static final String[] consonants = new String[]{"b", "c", "d", "f", "g", "gh", "h", "j", "k", "ch",
    "l", "m", "n", "p", "r", "s", "t", "v", "w", "z", "sh", "th", "t"};
    static final char[] greekletters = new char[]{224, 225, 226, 235, 238};

    public static String newName(){
        StringBuilder output = new StringBuilder();
        String startConsonant;
        String endConsonant;
        String vowel;
        int syllableCount = RandomUtil.fromRangeI(1, 4);
        for (int i = 0; i < syllableCount; i++){

            // Syllable onset
            if (RandomUtil.percentChance(75)){
                startConsonant = consonants[RandomUtil.fromRangeI(0, consonants.length - 1)];
                output.append(startConsonant);
            }

            // Syllable nucleus
            vowel = vowels[RandomUtil.fromRangeI(0, vowels.length - 1)];
            output.append(vowel);

            // Syllable coda
            if (RandomUtil.percentChance(45)){
                endConsonant = consonants[RandomUtil.fromRangeI(0, consonants.length - 1)];
                output.append(endConsonant);
            }

        }
        String str = output.toString();
        str = str.substring(0, 1).toUpperCase() + str.substring(1);
        return str;
    }

    public static char[] greekLetters(){
        return greekletters;
    }
}
