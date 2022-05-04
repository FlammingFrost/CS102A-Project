package data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class UserList {
    private ArrayList<String> nickname=new ArrayList<>();
    private ArrayList<Integer> userID=new ArrayList<>();
    private ArrayList<Integer> score=new ArrayList<>();
    private ArrayList<User> user_list = new ArrayList<>();
    private final String path = "./resource/user/";
    private String pathOfList = "./resource/user/userlist.txt";

    public UserList(){
        try {
            String[] temp;User tempu;
            List<String> readline = Files.readAllLines(Paths.get(pathOfList), StandardCharsets.UTF_8);
            for (int i = 0; i < readline.size(); i++) {
                temp=readline.get(i).split("@");
                tempu=new User(temp);
                user_list.add(tempu);
                userID.add(tempu.getID());
                nickname.add(tempu.getNickname());
                score.add(tempu.getScore());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public UserList(String pathOflist){
        this.pathOfList=pathOflist;
        new UserList();
    }

    //private void CheckDuplicate(){}

    public boolean CheckIdentity(int id,String password){
        boolean result =false;
        for (int i = 0; i < user_list.size(); i++) {
            if (user_list.get(i).getID()==id){
                result=user_list.get(i).CheckPassword(password);
                if (result){
                    return result;
                }else{
                    System.out.println("Wrong Password.");
                    return result;
                }
            }
        }
        System.out.println("No such User.");
        return result;
    }
    public boolean CheckIdentity(User u,String password){
        if (u.CheckPassword(password)){
            return true;
        }else {
            System.out.println("Wrong password.");
            return false;
        }
    }
    public boolean set_new_password(User u,String old, String newp){
        boolean result = u.setPassword(old, newp);
        return result;
    }
    public void setUsernickname(User u,String nickname){
        u.setNickname(nickname);
    }
    public void serUserScore(User u, int new_score){
        u.setScore(new_score);
    }
    public boolean CreatUser(String nickname, String password){
        try {
            User u = new User(nickname,password);
            user_list.add(u);
            userID.add(u.getID());
            this.nickname.add(u.getNickname());
            score.add(u.getScore());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public ArrayList<String> getNickname() {
        return nickname;
    }
    public ArrayList<Integer> getUserID() {
        return userID;
    }
    public ArrayList<Integer> getScore() {
        return score;
    }
    public ArrayList<User> getUser_list() {
        return user_list;
    }
    public User getUserById(int id){
        int index = userID.indexOf(id);
        return user_list.get(index);
    }

    public void SaveUserList(){
        File writename = new File(pathOfList); // 相对路径，如果没有则要建立一个新的output。txt文件
        try {
            writename.createNewFile(); // 创建新文件

            BufferedWriter out = new BufferedWriter(new FileWriter(writename));
            for (User u:user_list) {
                out.write(u.getID()+"@"+u.getPassword()+"@"+u.getNickname()+"@"+u.getScore()+"\r\n");
            }
            // \r\n即为换行

            out.flush(); // 把缓存区内容压入文件
            out.close(); // 最后记得关闭文件
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
