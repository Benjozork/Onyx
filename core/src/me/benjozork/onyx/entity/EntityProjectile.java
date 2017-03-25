package me.benjozork.onyx.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import me.benjozork.onyx.internal.GameManager;
import me.benjozork.onyx.internal.Utils;
import me.benjozork.onyx.screen.GameScreen;

/**
 * Created by Benjozork on 2017-03-04.
 */

public class EntityProjectile extends Entity {

     private SpriteBatch batch;

     private float maxTimer;
     private float bulletTimer;
     private float damage = 0;

     private Vector2 mouse = new Vector2();

     private String texturePath = new String();
     private Texture texture;

     public EntityProjectile(float x, float y) {
          super(new Vector2(x, y));
     }

     @Override
     public void init() {
          batch = GameManager.getBatch();
          // initialize hitbox
          setBounds(new Rectangle(getX(), getY(), 15, 25));

          // get mouse point, unproject it, and set velocity accordingly
          mouse.x = Gdx.input.getX();
          mouse.y = Gdx.input.getY();
          mouse = Utils.unprojectWorld(mouse);
          velocity.x = mouse.x - getX();
          velocity.y = mouse.y - getY();
     }

     @Override
     public void update() {

     }

     @Override
     public void draw() {
          // For every entity registered, check if it is an LivingEntity and apply damage, then destroy entity
           /* for (Entity e : GameManager.getRegisteredEntities()) {
                if (this.collidesWith(e)) {
                    if (e instanceof LivingEntity) {
                        ((LivingEntity) e).damage(damage);
                    }
                    //this.dispose();
                }
            }*/

          batch.begin();
          batch.draw(texture, getX(), getY(), 10, 10);
          batch.end();
     }

     @Override
     public void dispose() {

     }

     public float getDamage() {
          return damage;
     }

     public void setDamage(float damage) {
          this.damage = damage;
     }

     public String getTexturePath() {
          return texturePath;
     }

     public void setTexturePath(String texturePath) {
          this.texturePath = texturePath;
          this.texture = new Texture(texturePath);
     }

}