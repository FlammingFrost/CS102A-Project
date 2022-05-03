package data;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class test {
    public static void main(String[] args) throws IOException {
        System.out.println(Paths.get("./ChessDemo/src/data/tt.txt"));
        List<String> list1 = Files.readAllLines(Paths.get("./ChessDemo/src/data/tt.txt"), StandardCharsets.UTF_8);
        for (int i = 0; i < list1.size(); i++) {
            System.out.println(list1.get(i));
        }
        try {
            UserInfo u=new UserInfo("dfdf");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
