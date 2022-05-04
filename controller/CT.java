package controller;

import java.util.Locale;

public enum CT {
    WP("P",true),WN("N",true),WB("B",true),WR("R",true),WQ("Q",true),WK("K",true),
    BP("P",false),BN("N",false),BB("B",false),BR("R",false),BQ("Q",false),BK("K",false),
    Blank("no chess here",true,true);

    private String type;
    private boolean Ccolor;
    private boolean blank=false;
    static String[] validChess = {"K","Q","B","P","N","R","0","k","q","b","p","n","r"};
    CT (String type,boolean Ccolor){
        this.type=type;
        this.Ccolor=Ccolor;
    }
    CT (String type,boolean Ccolor,boolean blank){
        this.type=type;
        this.Ccolor=Ccolor;
        this.blank=blank;
    }
    public boolean getColor() {
        return Ccolor;
    }

    public String getType() {
        return type;
    }

    public boolean isBlank() {
        return blank;
    }
    public static CT generate(String type, boolean color){
        CT.values();
        for (int i = 0; i <CT.values().length ; i++) {
            if (CT.values()[i].getColor()==color&&CT.values()[i].getType().equals(type)){
                return CT.values()[i];
            }
        }
        return Blank;
    }
    public static CT generate(String type)throws Exception{
        boolean match=false;
        boolean White=true;
        if (Character.isLowerCase(type.toCharArray()[0]))
            White = false;
        type=type.toUpperCase(Locale.ROOT);
        for (String valid:validChess) {
            if (valid.equals(type)) {
                match = true;
                break;
            }
        }
        if (!match){
            throw new Exception("Wrong Chess Type.");
        }

        return generate(type,White);
    }
    public String toString(){
        if (this.blank)
            return "0";
        if (getColor())
            return type;
        else
            return type.toLowerCase();
    }
    public static CT[][] initial(){
        CT[][] init = {
                {WR,WP,Blank,Blank,Blank,Blank,BP,BR},
                {WN,WP,Blank,Blank,Blank,Blank,BB,BN},
                {WB,WP,Blank,Blank,Blank,Blank,BP,BB},
                {WK,WP,Blank,Blank,Blank,Blank,BP,BK},
                {WQ,WP,Blank,Blank,Blank,Blank,BP,BQ},
                {WB,WP,Blank,Blank,Blank,Blank,BP,BB},
                {WN,WP,Blank,Blank,Blank,Blank,BP,BN},
                {WR,WP,Blank,Blank,Blank,Blank,BB,BR}
        };
        return init;
    }
}
