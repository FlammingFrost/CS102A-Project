package data;

import java.util.Random;

public class User {
    private int ID;
    private String nickname;
    private String password;
    private int score=0;
    public User(String[] info){
        this.ID=Integer.parseInt(info[0]);
        this.password=info[1];
        this.nickname=info[2];
        this.score=Integer.parseInt(info[3]);
    }
    public User(String nickname, String password)throws Exception{
        if (nickname.contains("@")||password.contains("@")){
            throw new Exception("Invalid Nickname or Password.");
        }
        Random r = new Random();
        this.ID=r.nextInt(10000000)+20000000;
        this.nickname=nickname;
        this.password=password;
    }

    public boolean CheckPassword(String password){
        if (this.password.equals(password))
            return true;
        else
            return false;
    }








    public boolean setPassword(String old,String newp){
        if (newp.contains("@")){
            System.out.println("invalid Char @");
            return false;
        }else {
            if (CheckPassword(old)){
                this.password=newp;
                return true;
            }else {
                System.out.println("old password incorrect.");
                return false;
            }
        }
    }

    public int getID() {
        return ID;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
