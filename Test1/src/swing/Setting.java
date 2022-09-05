package swing;

public class Setting {
    private String imgPath="Test1/src/img/"; //img까지 경로
    private int blockCount=300; //생성할 블록 갯수
    private int hp=10; //hp
    private int gameTime= 120;//게임 진행시간
    
    private String charName[]={"snowChar","ghostChar","miraChar"};
    private int charW[] = {180,130,100}, charH[] = {180,130,100} ,charX[] = {410,430,460}, charY[] = {330,375,410};

    public String getImgPath(){
        return imgPath;
    }
    public int getBlockCount() {
        return blockCount;
    }

    public void setBlockCount(int blockCount) {
        this.blockCount = blockCount;
    }
    public int getHp() {
        return hp;
    }
    public String[] getCharName() {
        return charName;
    }
    public void setCharName(String[] charName) {
        this.charName = charName;
    }
    public void setHp(int hp) {
        this.hp = hp;
    }
    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
    public int getGameTime() {
        return gameTime;
    }
    public void setGameTime(int gameTime) {
        this.gameTime = gameTime;
    }
    public int[] getCharW() {
        return charW;
    }
    public void setCharW(int[] charW) {
        this.charW = charW;
    }
    public int[] getCharH() {
        return charH;
    }
    public void setCharH(int[] charH) {
        this.charH = charH;
    }
    public int[] getCharX() {
        return charX;
    }
    public void setCharX(int[] charX) {
        this.charX = charX;
    }
    public int[] getCharY() {
        return charY;
    }
    public void setCharY(int[] charY) {
        this.charY = charY;
    }
    
    
}
