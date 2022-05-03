package data;

import controller.CT;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
/*
数据格式
对局id
白id
黑id
时间
是否结束
棋盘信息（64） 接下俩下棋的一方（字符串1） 接下来下棋的这一方的移动（4，可能为空）
 */
public class DataStorage {
    private String path;
    public DataStorage(String id){
        path="./resource/";
        this.recordID=Integer.parseInt(id);
        try {
            this.ReadData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public DataStorage(){ path="./resource/";}
    public DataStorage(int WhitePlayerid, int BlackPlayerid){
        boardflow.add(CT.initial());
        Random r = new Random();
        recordID = r.nextInt(1000000)+10000000;
        isFinish=false;
        this.WhithPlayerId=WhitePlayerid;
        this.BlackPlayerId=BlackPlayerid;
        this.date=new Date().toString();
        colorflow.add(true);
    }
    List<String> readline;

    //
    //attribution of information
    private List<CT[][]> boardflow = new ArrayList<>();
    private List<Boolean> colorflow=new ArrayList<>();
    private int WhithPlayerId,BlackPlayerId;
    private String date;
    private boolean isFinish;
    private int recordID;
    private List<int[]> stepflow=new ArrayList<>();
    //



    //读取数据
    public void ReadData() throws IOException{
        String[] linein;
        CT[][] chessboard;
        int[] step = new int[4];
        boolean color=true;

        readline = Files.readAllLines(Paths.get(path+Integer.toString(recordID)+".txt"), StandardCharsets.UTF_8);
        recordID=Integer.parseInt(readline.get(0));
        WhithPlayerId=Integer.parseInt(readline.get(1));BlackPlayerId=Integer.parseInt(readline.get(2));
        date = readline.get(3);
        isFinish = Boolean.parseBoolean(readline.get(4));
        for (int i = 5; i < readline.size(); i++) {
            linein = readline.get(i).split(" ");
            if (linein.length!=69)
                try {
                    throw new Exception("BoardFlow error. Length:"+linein.length);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            switch (linein[64]){
                case "white":
                    color=true;
                    break;
                case "black":
                    color=false;
                    break;
                default:
                    try {
                        throw new Exception("Playturn error. Read:"+linein[64]);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
            chessboard = new CT[8][8];
            for (int x = 0; x < 8; x++) {
                for (int y = 0; y < 8; y++) {
                    try {
                        chessboard[x][y]=CT.generate(linein[8*x+y]);
                    } catch (Exception e) {
                        System.out.println("the storage is broken at "+i+"th turn, location x;"+x+" y:"+y+"  ||Replaced by BLANK.");
                        chessboard[x][y]=CT.Blank;
                    }
                }
            }
            int coor;
            if (!(i==readline.size()-1)){
                for (int location = 0; location < 4; location++) {
                    coor=Integer.parseInt(linein[65+i]);
                    if (coor>=8||coor<0){
                        System.out.println("the storage is broken at "+i+"th turn, Playswitch.  ||Replaced by -1.");
                        step[location]=-1;
                        continue;
                    }
                    step[location]=coor;
                }
                stepflow.add(step);
            }

            boardflow.add(chessboard);
            colorflow.add(color);
        }
    }
    //更新数据
    public void SaveTurn(boolean color, CT[][] updateChessboard, int[] step){//这里step的下棋手恰好不是color
        colorflow.add(color);
        boardflow.add(updateChessboard);
        stepflow.add(step);
    };
    //保存数据到本地 txt
    public void SaveData(){
        Date time = new Date();

        /* 写入Txt文件 */
        File writename = new File("./resource/"+recordID+".txt"); // 相对路径，如果没有则要建立一个新的output。txt文件
        try {
            writename.createNewFile(); // 创建新文件

        BufferedWriter out = new BufferedWriter(new FileWriter(writename));
        out.write(recordID+"\r\n"); // \r\n即为换行
        out.write(Integer.toString(WhithPlayerId)+"\r\n"+Integer.toString(BlackPlayerId)+"\r\n");
        out.write(time.toString()+"\r\n");
        out.write(Boolean.toString(isFinish)+"\r\n");
        //开始写入对局数据
            for (int i = 0; i < boardflow.size(); i++) {
                for (int x = 0; x < 8; x++) {
                    for (int y = 0; y < 8; y++) {
                        out.write(boardflow.get(i)[x][y].toString()+" ");
                    }
                }
                out.write(colorflow.get(i)?"white":"black");
                if (i!=boardflow.size()-1){
                    for (int j = 0; j < 4; j++) {
                        out.write(" "+stepflow.get(i)[j]);
                    }
                }
                out.write("\r\n");

            }
        out.flush(); // 把缓存区内容压入文件
        out.close(); // 最后记得关闭文件
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //检查数据是否合法
    public boolean CheckValidation(){return true;}
    public boolean getCurrentColor(){
        return colorflow.get(colorflow.size()-1);
    }
    public CT[][] getCurrentChessBoard(){
        return boardflow.get(boardflow.size()-1);
    }

    public int getRecordID() {
        return recordID;
    }
    public int[] last_step(){
        return stepflow.get(stepflow.size()-1);
    }
}
