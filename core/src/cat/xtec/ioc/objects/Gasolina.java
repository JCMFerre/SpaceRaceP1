package cat.xtec.ioc.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import cat.xtec.ioc.helpers.AssetManager;
import cat.xtec.ioc.utils.Settings;

public class Gasolina extends Actor {

    private int width, height;
    private Vector2 position;

    private Rectangle collisionRect;

    public Gasolina(float x, float y, int width, int height) {
        this.width = width;
        this.height = height;
        position = new Vector2(x, y);
        collisionRect = new Rectangle();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        position.x += Settings.GASOLINE_SPEED * delta;
        collisionRect.set(position.x, position.y, width, height);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(AssetManager.gasolina, position.x, position.y, width, height);
    }

    public boolean collides(Spacecraft spacecraft) {
        return (Intersector.overlaps(this.collisionRect, spacecraft.getCollisionRect()));
    }

    public Vector2 getPosition() {
        return position;
    }

    public float getWidthBuena() {
        return width;
    }
}
