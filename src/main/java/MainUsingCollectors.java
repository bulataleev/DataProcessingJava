import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

public class MainUsingCollectors {

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
        System.out.println("score of hj: " + score.apply("hj"));

        String word =shWords.stream()
                .filter(existingWord->scrabbleWords.contains(existingWord))
                .max(Comparator.comparing(score))
                .get();
        System.out.println("Best shaekspare word: " + word);

        IntSummaryStatistics summaryStatistics =
                shWords.stream().parallel()
                .filter(scrabbleWords::contains)//el->scrabbleWords.contains(el)
                .mapToInt(intScore)
                .summaryStatistics();
        System.out.println("Stats: " +summaryStatistics);

        //Advanced Use of Optionals:
        //data processing pipeline in ErrorlessPipelineDataProcessing.class
        Map<Integer, List<String>> histWordsByScore =
                shWords.stream()
                        .filter(scrabbleWords::contains)
                        .collect(
                        Collectors.groupingBy(
                                score //to avoid double letters use score2
                        )
                );
        System.out.println("by score using collector: " + histWordsByScore.size());


        histWordsByScore.entrySet() //set<Map.entry<Intger,List<String>>
                .stream()
                .sorted(
                    Comparator.comparing(entry-> -entry.getKey())
                )
                .limit(3)
                .forEach(entry-> System.out.println(entry.getKey() + " - " + entry.getValue()));


        //solving double letter problem is scrabble game:
        int [] scrabbleEnDistribution = {
              //a,b,c,d,e, f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z
                9,2,2,1,12,2,3,2,9,1,1,4,2,6,8,2,1,6,4,6,4,2,2,1,2,1
        };

        Function<String, Map<Integer, Long>> histWord = //takes word as a paramter and returns map
                fword->fword.chars().boxed()
                    .collect(
                            Collectors.groupingBy(letter->letter, Collectors.counting())
                    );
        Function <String, Long> nBlanks =
                nword -> histWord.apply(nword)
                .entrySet()
                .stream()
                .mapToLong(
                        entry->
                                Long.max(
                                        entry.getValue() - (long)scrabbleEnDistribution[entry.getKey()-'a'], 0L
                                )
                ).sum();
        System.out.println("# of blanks fow whizzing: " + nBlanks.apply("whizzing"));

        Function <String, Integer> score2 =
                nword->histWord.apply(nword)
                .entrySet()
                .stream()
                .mapToInt(
                        entry->
                                scrabbleENScore[entry.getKey()-'a']*
                                        Integer.min(
                                                entry.getValue().intValue(),
                                                scrabbleEnDistribution[entry.getKey()-'a']
                                        )

                ).sum();
        System.out.println("score of blanks fow whizzing: " + score.apply("whizzing"));
        System.out.println("score2 of blanks fow whizzing: " + score2.apply("whizzing"));


    }

}
