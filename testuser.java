import data.User;
import data.UserList;

import java.util.List;

public class testuser {
    public static void main(String[] args) {
        UserList ul = new UserList();
        ul.CreatUser("44444","444444");
        System.out.println(ul.getUser_list().toString());
        List<User> k=ul.getUser_list();
        ul.SaveUserList();
    }
}
