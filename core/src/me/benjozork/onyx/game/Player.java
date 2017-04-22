package me.benjozork.onyx.game;

import me.benjozork.onyx.game.entity.PlayerEntity;

/**
 * @author Rishi Raj
 */
public class Player {

    private int score;

    private static int highScore;

    private int lives;

    private PlayerEntity playerEntity;

    public Player(int lives, PlayerEntity playerEntity) {
        setLives(lives);
        setPlayerEntity(playerEntity);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
        if (score > highScore) highScore = score;
    }

    public void addScore(int v) {
        score += v;
        if (score > highScore) highScore = score;
    }

    public int getHighScore() {
        return highScore;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void addLife() {
        lives++;
    }

    public void removeLife() {
        lives--;
    }

    public PlayerEntity getPlayerEntity() {
        return playerEntity;
    }

    public void setPlayerEntity(PlayerEntity playerEntity) {
        this.playerEntity = playerEntity;
        playerEntity.setPlayer(this);
    }

}
