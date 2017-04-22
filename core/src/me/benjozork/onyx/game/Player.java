package me.benjozork.onyx.game;

import me.benjozork.onyx.game.entity.PlayerEntity;

/**
 * @author Rishi Raj
 */
public class Player {

    private int score;
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
    }

    public void addScore(int v) {
        score += v;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public PlayerEntity getPlayerEntity() {
        return playerEntity;
    }

    public void setPlayerEntity(PlayerEntity playerEntity) {
        this.playerEntity = playerEntity;
        playerEntity.setPlayer(this);
    }
}
