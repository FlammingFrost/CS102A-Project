import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class testread {
    public static void main(String[] args) {
        try {
            List<String> readline = Files.readAllLines(Paths.get("resource/record/10664452.txt"), StandardCharsets.UTF_8);
            System.out.println(readline.size());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
