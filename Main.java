import structure.Lobby;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Lobby lobby = new Lobby(1000, 760);
            lobby.setVisible(true);
        });
    }
}
/*注释看的不是很懂的地方请及时交流。现在这个程序还跑不起来，很多功能都没有实现。
* 这个版本和demo的差距相对来说比较大，不管是一些方法还是类的结构。不过有些也许可以参考demo里面的
* 如果在编写程序的过程中，需要用方法调用我写的那些类相关的数据，直接qq联系*/

/*以下程序的几乎所有内容需要hxd完成
* chessgameai.java
* chessgamelocal.java
* chessgamenet.java(optional)
* lobby.java
* 以上三个类会用到但不仅限于：
* 图片等多媒体的读取、多媒体处理、GUI界面的布置（如菜单balabal）*/