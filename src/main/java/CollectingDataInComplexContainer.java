import model.Person;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class CollectingDataInComplexContainer {

    public static void main(String... args) throws IOException {
        List<Person> people = SpliteratorUsage.personList();
        Optional<Person> oldest =
                people.stream().collect(
                        Collectors.maxBy(Comparator.comparing(p->p.getAge()))
                );
        System.out.println(oldest);

        String names = people.stream().map(p->p.getName()).collect(Collectors.joining(", "));
        System.out.println(names);

//        TreeMap<Integer, TreeSet<String>> namesByAge =
//                people.stream()
//                        .collect(
//                                Collectors.groupingBy(person->person.getAge()),
//                    ()-> new TreeMap<>(),
//                    Collectors.mapping(
//                            p->p.getName(),
//                            Collectors.toCollection(()->TreeSet())
//                    )
//                );
//        Map<Integer,List<Person>> peopleByAge=
//                people.stream()
//                .collect(
//                        Collectors.collectingAndThen(
//                                Collectors.groupingBy(person -> person.getAge()),
//                                Collections::unmodifiableMap
//                        )
//                );

    }


}
