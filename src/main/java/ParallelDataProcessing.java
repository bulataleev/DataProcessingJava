import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ParallelDataProcessing {


    public static void main(String[] args){
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", String.valueOf(2));

        List<String> strings = new ArrayList<>();//not concurrent and not thread safe
        List<String> safeStrings = new CopyOnWriteArrayList<>(); //concurrent and thread safe but poor performance

        //List<String> collect = //if need to assing to a variable
        Stream.iterate("+",s -> s+"+")
                .parallel() //if run in parallel adding to arrayList is not stable operation
                .limit(100)
                //.peek(s-> System.out.println(s+" processed in the thread: " + Thread.currentThread().getName()))
                //.forEach(s->strings.add(s)); //race condition happens here however this is not correct method to add items to lis
                .collect(Collectors.toList());
        System.out.println(strings.size());
    }


}
