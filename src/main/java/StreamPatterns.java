import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class StreamPatterns {

    public static void main(String[] args) throws IOException {
        //merge large amount of text and split the words
        Stream<String> bookStreamOfLines = Files.lines(Paths.get("/Users/home/Documents/StreamsAndCollectors/src/main/resources/TomSwayer.txt"));
        //System.out.println(bookStreamOfLines.count());
        //Stream of streams
        //# Stream <Stream<String>> streamOfStreams = Stream.of(stream1,s2,s3);
        //Stream<String> streamOfLines = streamOfStreams.flatMap(stream->stream); //faster way
        // 'Function.identity()' instead of 'stream -> stream'
        Function <String, Stream<String>> lineSplitter = line-> Pattern.compile(" ").splitAsStream(line);
        Stream<String> streamOfWords = bookStreamOfLines.flatMap(lineSplitter).
                map(word->word.toLowerCase()).
                filter(word->word.length()==4).
                distinct();
        System.out.println("#words: " + streamOfWords.count());



    }

}
