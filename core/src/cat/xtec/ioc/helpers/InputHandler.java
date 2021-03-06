package cat.xtec.ioc.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

import cat.xtec.ioc.objects.Spacecraft;
import cat.xtec.ioc.screens.GameScreen;

public class InputHandler implements InputProcessor {

    // Enter per a la gesitó del moviment d'arrastrar
    int previousY = 0;
    // Objectes necessaris
    private Spacecraft spacecraft;
    private GameScreen screen;
    private Vector2 stageCoord;
    private int dificultat;

    private Stage stage;

    public InputHandler(GameScreen screen, int dificultat) {
        this.screen = screen;
        spacecraft = screen.getSpacecraft();
        stage = screen.getStage();
        this.dificultat = dificultat;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.UP && keycode != Input.Keys.DOWN) {
            spacecraft.goUp();
        } else if (keycode == Input.Keys.DOWN && keycode != Input.Keys.UP) {
            spacecraft.goDown();
        } else if (keycode == Input.Keys.RIGHT && keycode != Input.Keys.LEFT) {
            spacecraft.goStraight();
        } else if (keycode == Input.Keys.LEFT && keycode != Input.Keys.RIGHT) {
            spacecraft.goBack();
        } else {
            spacecraft.pause();
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        spacecraft.pause();
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        previousY = screenY;
        stageCoord = stage.screenToStageCoordinates(new Vector2(screenX, screenY));
        Actor actorHit = stage.hit(stageCoord.x, stageCoord.y, true);
        if (actorHit != null)
            Gdx.app.log("HIT", actorHit.getName());
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        spacecraft.goStraight();
        return true;
    }


    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        // Posem un umbral per evitar gestionar events quan el dit està quiet
        if (Math.abs(previousY - screenY) > 2)

            // Si la Y és major que la que tenim
            // guardada és que va cap avall
            if (previousY < screenY) {
                spacecraft.goDown();
            } else {
                // En cas contrari cap a dalt
                spacecraft.goUp();
            }
        // Guardem la posició de la Y
        previousY = screenY;
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
