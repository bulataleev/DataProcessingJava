import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.Set;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

public class StreamPatternsForNumbers {

    public static void main(String[] args) throws IOException {
        Set<String> shWords = Files.lines(Paths.get("/Users/home/Documents/StreamsAndCollectors/src/main/" +
                "resources/words" +
                ".shakespeare.txt"))
                .map(word -> word.toLowerCase())
                .collect(Collectors.toSet());

        Set<String> scrabbleWords = Files.lines(Paths.
                get("/Users/home/Documents/StreamsAndCollectors/src/main/resources/ospd.txt"))
                .map(word -> word.toLowerCase())
                .collect(Collectors.toSet());

        final int[] scrabbleENScore = {
                1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10};

        Function<String, Integer> score =
                word -> word.chars().map(letter -> scrabbleENScore[letter - 'a']).sum();
        ToIntFunction<String> intScore =
                word -> word.chars().map(letter -> scrabbleENScore[letter - 'a']).sum();


        System.out.println("score of hi: " + intScore.applyAsInt("hi"));
        System.out.println("score of hi: " + score.apply("hj"));

        String word =shWords.stream()
                .filter(existingWord->scrabbleWords.contains(existingWord))
                .max(Comparator.comparing(score))
                .get();
        System.out.println("Best shaekspare word: " + word);

        IntSummaryStatistics summaryStatistics =
                shWords.stream().parallel()
                .filter(scrabbleWords::contains)
                .mapToInt(intScore)
                .summaryStatistics();
        System.out.println("Stats: " +summaryStatistics);



    }

}
