
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FileWalker {
    private static StringBuffer fileContent = new StringBuffer() ;
    private static ArrayList<SingleFile> fileTreeContent = new ArrayList<>();
    private static String properFileLanguage = "";

    public static ArrayList<SingleFile> processDir(String dirName) {

        try {
            Files.walkFileTree(Paths.get(dirName), new FileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                    if(!dir.toAbsolutePath().toString().equals(dirName)) {
                        properFileLanguage = dir.getFileName().toString();
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    FileChannel inChannel = FileChannel.open(file);
                    ByteBuffer inBuffer = ByteBuffer.allocate(1024);
                    Charset set = Charset.forName("Cp1250");
                    inChannel.read(inBuffer);
                    inChannel.close();
                    inBuffer.flip();
                    CharBuffer cb = set.decode(inBuffer);

                    while (cb.hasRemaining()){
                        fileContent.append(cb.get());
                    }
                    fileTreeContent.add(new SingleFile(countLetters(fileContent.toString()),properFileLanguage));
                    fileContent.setLength(0);

                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) {
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
                    return FileVisitResult.CONTINUE;
                }
            });


        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileTreeContent;
    }

    //liczenie ilości wystąpień każdej z liter, zwrócenie Listy o długości alfabetu, indeks elementu odpowiada kolejności w alfabecie
    //każda wartość jest proporcją
    public static List<Double> countLetters(String word){
        final ArrayList<Double> parameters = new ArrayList<>(Collections.nCopies(26,0.0));
        final long allChars =
                word.toLowerCase()
                .chars()
                .filter(f-> f>='a' && f<='z').count();

            word.toLowerCase()
                    .chars()
                    .filter(f-> f>='a' && f<='z')
                    .map(g->g-97)
                    .forEach(h->parameters.set(h,parameters.get(h)+1));

            return  parameters.stream().map(param-> (param/allChars)).collect(Collectors.toList());


        }





    
}
