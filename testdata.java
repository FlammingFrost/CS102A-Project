import controller.GameController;
import data.DataStorage;

public class testdata {
    public static void main(String[] args) {
        DataStorage test =new DataStorage(21111111,21111112);
        test.SaveData();
        DataStorage read = new DataStorage(Integer.toString(test.getRecordID()));
        GameController gtest = new GameController(read);
        gtest.printChessBoard();
        System.out.println();
        int moveresult = gtest.MoveChess(3,1,3,3,"none");
        gtest.printChessBoard();
        System.out.println(moveresult);
        gtest.SaveData();
        GameController gtest2=new GameController(read);
        gtest2.MoveChess(0,6,0,4,"");
        gtest2.printChessBoard();
        gtest2.MoveChess(3,3,3,4,"");
        gtest2.printChessBoard();
        gtest2.MoveChess(4,6,4,4,"");
        gtest2.printChessBoard();
        moveresult =gtest2.MoveChess(3,4,4,5,"");
        gtest2.printChessBoard();
        System.out.println(moveresult);
    }

}
