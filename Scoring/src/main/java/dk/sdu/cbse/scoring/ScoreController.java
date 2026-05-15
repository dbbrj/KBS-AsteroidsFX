package dk.sdu.cbse.scoring;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/score")
public class ScoreController {

    private int asteroidsDestroyed = 0;
    private int enemiesDestroyed   = 0;

    @PostMapping("/asteroid")
    public void recordAsteroid() {
        asteroidsDestroyed++;
    }

    @PostMapping("/enemy")
    public void recordEnemy() {
        enemiesDestroyed++;
    }

    @GetMapping
    public ScoreData getScore() {
        return new ScoreData(asteroidsDestroyed, enemiesDestroyed,
                (asteroidsDestroyed + enemiesDestroyed) * 5);
    }

    @PostMapping("/reset")
    public void resetScore() {
        asteroidsDestroyed = 0;
        enemiesDestroyed   = 0;
    }
}
