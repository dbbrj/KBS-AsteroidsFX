package dk.sdu.cbse.common.data;

public class GameData {

    private int displayWidth  = 800 ;
    private int displayHeight = 800;
    private final GameKeys keys = new GameKeys();
    private int asteroidsDestroyed = 0;
    private int enemiesDestroyed = 0;

    public GameKeys getKeys() {
        return keys;
    }

    public void setDisplayWidth(int width) {
        this.displayWidth = width;
    }

    public int getDisplayWidth() {
        return displayWidth;
    }

    public void setDisplayHeight(int height) {
        this.displayHeight = height;
    }

    public int getDisplayHeight() {
        return displayHeight;
    }

    public void addAsteroidDestroyed() { asteroidsDestroyed++; }
    public void addEnemyDestroyed() { enemiesDestroyed++; }
    public int getAsteroidsDestroyed() { return asteroidsDestroyed; }
    public int getEnemiesDestroyed() { return enemiesDestroyed; }
    public int getTotalScore() { return (asteroidsDestroyed + enemiesDestroyed) * 5; }
}
