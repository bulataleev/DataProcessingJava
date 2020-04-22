import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ErrorlessPipelineDataProcessing {

    public static void main(String... args){
        //this is how optional should be used
        //Optional class is private and has static method

//        Optional<String> empty = Optional.empty();
//        Optional<String> nonempty = Optional.of(null); //NullPointerException
//        Optional<String> couldBeEmpty = Optional.ofNullable(null); //NullPointerException

        List<Double> result = new ArrayList<>();

        ThreadLocalRandom.current()  //cant't add parallel here!
                .doubles(10000).boxed()
                .forEach(
                        d -> NewMath.inv(d)
                                .ifPresent(
                                        inv -> NewMath.sqrt(inv)
                                                .ifPresent(sqrt -> result.add(sqrt))
                )

        );
        System.out.println(result.size());

        Function<Double, Stream<Double>> flatMapper =
                d-> NewMath.inv(d)
                    .flatMap(inv -> NewMath.sqrt(inv))
                    .map(sqrt -> Stream.of(sqrt)) //if there is a value returns stream
                    .orElseGet(() -> Stream.empty()); // d is empty - empty stream will be returned

        List<Double> rightResult = ThreadLocalRandom.current().doubles(10_000).parallel()
                .map(d -> d*20 - 10)
                .boxed()
                .flatMap(flatMapper)
                .collect(Collectors.toList());
        System.out.println(" #null handled and parallelly computed result: " + rightResult.size());



    }
}
