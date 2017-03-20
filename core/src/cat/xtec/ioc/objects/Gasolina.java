package cat.xtec.ioc.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

import cat.xtec.ioc.helpers.AssetManager;

public class Gasolina extends Scrollable {

    private Rectangle collisionRect;
    private float dimension;

    public Gasolina(float x, float y, float width, float height, float velocity) {
        super(x, y, width, height, velocity);
        dimension = width;
        collisionRect = new Rectangle();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        collisionRect.set(position.x, position.y, dimension, dimension);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(AssetManager.gasolina, position.x, position.y, dimension, dimension);
    }
}
