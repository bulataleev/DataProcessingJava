import model.Person;
import model.PersonSpliterator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class SpliteratorUsage {

    public static void main(String[] args){

        Path path = Paths.get("/Users/home/Documents/StreamsAndCollectors/src/main/resources/people.txt");
        try (Stream<String> lines = Files.lines(path)) {
            Spliterator<String> lineSpliterator =lines.spliterator();
            Spliterator<Person> peopleSpliterator = new PersonSpliterator(lineSpliterator);

            Stream<Person> people = StreamSupport.stream(peopleSpliterator,false);
            //will be stream of people build on a stream of lines, not going parallel with peopleSpliterator stream
            //if false is given as a second parameter the trysplit is not used
            people.forEach(System.out::println);

        } catch(IOException oie){
            oie.printStackTrace();
        }
    }
    public static List<Person> personList() throws IOException {
        Stream<String> personStreamOfLines = Files.lines(Paths.get("/Users/home/Documents/StreamsAndCollectors/src/main/resources/people.txt"));
        Spliterator<String> lineSpliterator = personStreamOfLines.spliterator();
        Spliterator<Person> peopleSpliterator = new PersonSpliterator(lineSpliterator);
        Stream<Person> people = StreamSupport.stream(peopleSpliterator,false);
        return people.collect(Collectors.toList());
    }

}
