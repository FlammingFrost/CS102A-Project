package controller;

import data.DataStorage;

import java.io.IOException;

public class GameController {
    private CT[][] chessboard;
    //private String path;
    private DataStorage DS;
    //positive
    private boolean currentColor;   //true for White; false for Black;
    public GameController(DataStorage DS){

        this.chessboard=DS.getCurrentChessBoard();
        if (chessboard.length!=8||chessboard[0].length!=8){
            try {
                throw new Exception("The ChessBoard Size is abnormal!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.currentColor=DS.getCurrentColor();
        //this.path=path;
        this.DS=DS;
    }



    //Method
    public int MoveChess(int start_x, int start_y, int end_x, int end_y,String changetypeforN){
        int move_result=0;
        //假设坐标都合法
        if (chessboard[start_x][start_y].isBlank())
            return -1;
        CT chosen_Chess = chessboard[start_x][start_y];
        if (start_x==end_x&&start_y==end_y)//重复点击
            return -7;
        if (chosen_Chess.getColor()!=currentColor)//当前走子方错误
            return -2;
        boolean available=false;
        switch (chosen_Chess.getType()){
            case "K":
                if (Math.abs(end_x-start_x)<=1&&Math.abs(end_y-start_y)<=1){
                   available=true;
                    for (int i = end_x-1; i <=end_x+1 ; i++) {
                        if (i<0||i>7)
                            continue;
                        for (int j = end_y-1; j <=end_y+1 ; j++) {
                            if (j<0||j>7)
                                continue;
                            if (i==end_x&&j==end_y)
                                continue;
                            if (chessboard[i][j].getType().equals("K"))
                                return -6;
                        }
                    }
                   //这里暂时没有考虑“送王”这一规则
                }else return -3;
                break;
            case "Q":
                int dx = end_x-start_x,dy=end_y-start_y;
                if (!(Math.abs(dx)==Math.abs(dy)||dx*dy==0))
                    return -3;
                int max = Math.max(Math.abs(dx),Math.abs(dy));
                for (int i = 1; i < max; i++) {
                    if (!chessboard[start_x+dx/max*i][start_y+dy/max*i].isBlank())
                        return -3;
                }
                available=true;
                break;
            case "R":
                int dxR = end_x-start_x,dyR=end_y-start_y;
                if (!(dxR*dyR==0))
                    return -3;
                int maxR = Math.max(Math.abs(dxR),Math.abs(dyR));
                for (int i = 1; i < maxR; i++) {
                    if (!chessboard[start_x+dxR/maxR*i][start_y+dyR/maxR*i].isBlank())
                        return -3;
                }
                available=true;
                break;
            case "B":
                int dxB = end_x-start_x,dyB=end_y-start_y;
                if (!(Math.abs(dxB)==Math.abs(dyB)))
                    return -3;
                int maxB = Math.max(Math.abs(dxB),Math.abs(dyB));
                for (int i = 1; i < maxB; i++) {
                    if (!chessboard[start_x+dxB/maxB*i][start_y+dyB/maxB*i].isBlank())
                        return -3;
                }
                available=true;
                break;
            case "N":
                int dxN = end_x-start_x,dyN=end_y-start_y;
                if (!(Math.abs(dxN*dyN)==2))
                    return -3;
                else
                    available=true;
                break;
            case "P":
                if (chessboard[start_x][start_y].getColor()){//可以走两步
                        if (end_y==3&&start_x==end_x&&start_y==1){
                            if (!chessboard[start_x][start_y+1].isBlank())
                                return -3;
                            available=true;
                        }
                        if (end_y==start_x+1&&start_x==end_x){//正常走一格
                            if (!chessboard[start_x][start_y+1].isBlank())
                                return -3;
                            available=true;
                        }
                        if (end_y==start_y+1&&Math.abs(end_x-start_x)==1){//斜方向吃子
                            if (chessboard[end_x][end_y].isBlank()){
                                //顺路杀
                                if (chessboard[end_x][start_y].getType().equals("P")&&end_x==DS.last_step()[2]&&start_y==DS.last_step()[3]&&chessboard[end_x][start_y].getColor()==!currentColor){
                                    chessboard[end_x][end_y]=chessboard[start_x][start_y];
                                    chessboard[start_x][start_y]=CT.Blank;
                                    chessboard[end_x][start_y]=CT.Blank;
                                    currentColor=!currentColor;
                                    SavetoDataStorage(new int[] {start_x,end_x,start_y,end_y});
                                    System.out.println("顺路杀！");
                                    return 1;
                                //
                                }else
                                return -3;
                            }
                            else {
                                if (chessboard[end_x][end_y].getColor()==!currentColor)
                                    available=true;
                            }
                        }
                }else {
                    if (end_y==4&&start_x==end_x&&start_y==6){
                        if (!chessboard[start_x][start_y-1].isBlank())
                            return -3;
                        available=true;
                    }
                    if (end_y==start_x-1&&start_x==end_x){//正常走一格
                        if (!chessboard[start_x][start_y-1].isBlank())
                            return -3;
                        available=true;
                    }
                    if (end_y==start_x-1&&Math.abs(end_x-start_x)==1){//斜方向吃子
                        if (chessboard[end_x][end_y].isBlank()){
                            return -3;
                        }
                        else {
                            if (chessboard[end_x][end_y].getColor()==!currentColor)
                                available=true;
                        }
                    }
                }
                break;

        }
        if (chessboard[end_x][end_y].getColor()==currentColor&&!chessboard[end_x][end_y].isBlank())
            return -4;
        if (available){
            chessboard[end_x][end_y]=chessboard[start_x][start_y];
            chessboard[start_x][start_y]=CT.Blank;
        }
        //升级！
        if (chessboard[end_x][end_y].getColor()&&end_y==7&&chessboard[end_x][end_y].getType().equals("P")){
            chessboard[end_x][end_y]=CT.generate(changetypeforN,currentColor);
        }
        if ((!chessboard[end_x][end_y].getColor())&&end_y==0&&chessboard[end_x][end_y].getType().equals("P")){
            chessboard[end_x][end_y]=CT.generate(changetypeforN,currentColor);
        }
        //走子已经完成，棋盘已经改变
        currentColor=!currentColor;//交换下棋方。
        //save to datastorage
        SavetoDataStorage(new int[] {start_x,start_y,end_x,end_y});
        return 1;
    }
    public void SaveData(){
        DS.SaveData();
    }
    private void SavetoDataStorage(int[] move){
        DS.SaveTurn(currentColor,chessboard,move);
    }
    public void printChessBoard(){
        System.out.println();
        for (int y = 7; y >=0; y--) {
            for (int x = 0; x < 8; x++) {
                System.out.print(chessboard[x][y].toString()+" ");
            }
            System.out.println();
        }
    }

    public DataStorage getDS() {
        return DS;
    }
    /*Output description:
    * -1: choose blank
    * 1:  move successfully, chessboard has been changed
    * -2: action turn error
    * -3: violate game rule: this chess can not be move in this way
    * -4: violate game rule: can not move to block that has been placed by ally chess
    * -5: other error
    * -6: 王“亲密接触”
    * -7: 重复点击
    * 0: should not has this output*/
}
