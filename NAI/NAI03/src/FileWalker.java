
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;

public class FileWalker {
    private static StringBuffer fileContent = new StringBuffer() ;
    private static ArrayList<SingleFile> fileTreeContent = new ArrayList<>();
    private static String properFileLanguage = "";
    private static int languageCounter=0;

    public static ArrayList<SingleFile> processDir(String dirName) {

        try {
            Files.walkFileTree(Paths.get(dirName), new FileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    if(!dir.toAbsolutePath().toString().equals(dirName)) {
                        properFileLanguage = dir.getFileName().toString();
                        languageCounter++;
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
                    fileTreeContent.add(new SingleFile(Process(fileContent.toString()),properFileLanguage));
                    fileContent.setLength(0);

                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    return FileVisitResult.CONTINUE;
                }
            });


        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileTreeContent;
    }

    public static ArrayList<Double> Process(String word){
        final ArrayList<Double> parameters = new ArrayList<>(Collections.nCopies(26,0.0));
            word.toLowerCase()
                    .chars()
                    .filter(f-> f>96 && f<123)
                    .map(g->g-97)
                    .forEach(h->parameters.set(h,parameters.get(h)+1));
            return parameters;

        }

    public static int getLanguageCounter() {
        return languageCounter;
    }




    
}
