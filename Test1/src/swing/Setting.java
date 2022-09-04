package swing;

public class Setting {
    private String imgPath="Test1/src/img/"; //img까지 경로
    private int blockCount=450; //생성할 블록 갯수
    private int hp=10; //hp

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
    public void setHp(int hp) {
        this.hp = hp;
    }
    
    
}
