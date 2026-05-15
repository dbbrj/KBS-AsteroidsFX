package dk.sdu.cbse.scoring;

public class ScoreData {
    private final int asteroidsDestroyed;
    private final int enemiesDestroyed;
    private final int totalScore;

    public ScoreData(int asteroidsDestroyed, int enemiesDestroyed, int totalScore) {
        this.asteroidsDestroyed = asteroidsDestroyed;
        this.enemiesDestroyed   = enemiesDestroyed;
        this.totalScore         = totalScore;
    }

    public int getAsteroidsDestroyed() { return asteroidsDestroyed; }
    public int getEnemiesDestroyed()   { return enemiesDestroyed; }
    public int getTotalScore()         { return totalScore; }
}
