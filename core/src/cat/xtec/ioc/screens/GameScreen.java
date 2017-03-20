package cat.xtec.ioc.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import cat.xtec.ioc.SpaceRace;
import cat.xtec.ioc.helpers.AssetManager;
import cat.xtec.ioc.helpers.InputHandler;
import cat.xtec.ioc.objects.ScrollHandler;
import cat.xtec.ioc.objects.Spacecraft;
import cat.xtec.ioc.utils.Settings;

public class GameScreen implements Screen {

    private boolean gameOver = false;
    private SpaceRace game;
    private Stage stage;
    private Spacecraft spacecraft;
    private ScrollHandler scrollHandler;
    private ShapeRenderer shapeRenderer;
    private Batch batch;

    private boolean animacionDesactivada;

    private Label labelPuntuacion;
    private Container<Label> contenedorPuntuacion;

    private float explosionTime = 0;

    private int recordActual;
    private int puntuacion;

    public GameScreen(SpaceRace game, int dificultat) {
        Gdx.app.log("difGame", "" + dificultat);
        this.game = game;
        shapeRenderer = new ShapeRenderer();
        OrthographicCamera camera = new OrthographicCamera(Settings.GAME_WIDTH, Settings.GAME_HEIGHT);
        camera.setToOrtho(true);
        StretchViewport viewport = new StretchViewport(Settings.GAME_WIDTH, Settings.GAME_HEIGHT, camera);
        stage = new Stage(viewport);
        batch = stage.getBatch();
        spacecraft = new Spacecraft(Settings.SPACECRAFT_STARTX, Settings.SPACECRAFT_STARTY, Settings.SPACECRAFT_WIDTH, Settings.SPACECRAFT_HEIGHT);
        scrollHandler = new ScrollHandler();
        stage.addActor(scrollHandler);
        stage.addActor(spacecraft);
        spacecraft.setName("spacecraft");
        recordActual = obtenerRecordActualPrefs();
        animacionDesactivada = true;
        labelPuntuacion = new Label("Puntuacion: 0", new Label.LabelStyle(AssetManager.font, null));
        labelPuntuacion.setFontScale(0.27f);
        contenedorPuntuacion = new Container<Label>(labelPuntuacion);
        contenedorPuntuacion.center();
        contenedorPuntuacion.setPosition(Settings.GAME_WIDTH / 2, labelPuntuacion.getHeight());
        contenedorPuntuacion.setName("contenedorPuntuacion");
        contenedorPuntuacion.setTransform(true);
        stage.addActor(contenedorPuntuacion);
        Gdx.app.log("record", recordActual + "");
        Gdx.input.setInputProcessor(new InputHandler(this, dificultat));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        stage.draw();
        stage.act(delta);
        puntuacion = scrollHandler.getPuntuacion();
        labelPuntuacion.setText("Puntuacion: " + puntuacion);
        if (!gameOver) {
            if (scrollHandler.collides(spacecraft)) {
                AssetManager.explosionSound.play();
                stage.getRoot().findActor("spacecraft").remove();
                gameOver = true;
            }
        } else {
            batch.begin();
            batch.draw(AssetManager.explosionAnim.getKeyFrame(explosionTime, false), (spacecraft.getX() + spacecraft.getWidth() / 2) - 32, spacecraft.getY() + spacecraft.getHeight() / 2 - 32, 64, 64);
            batch.end();
            explosionTime += delta;
            if (puntuacion > recordActual) {
                recordActual = puntuacion;
                guardarPuntuacionPrefs(puntuacion);
            }
            asteroideColisionado();
        }
        if (animacionDesactivada && puntuacion > recordActual) {
            contenedorPuntuacion.addAction(Actions.repeat(RepeatAction.FOREVER,
                    Actions.sequence(Actions.scaleTo(1.25f, 1.25f, 1),
                            Actions.scaleTo(1, 1, 1))));
            animacionDesactivada = false;
        }
    }

    private void guardarPuntuacionPrefs(int puntuacion) {
        Preferences preferences = Gdx.app.getPreferences("preferencias");
        preferences.putInteger("recordPuntuacion", puntuacion);
        preferences.flush();
    }

    private int obtenerRecordActualPrefs() {
        return Gdx.app.getPreferences("preferencias").getInteger("recordPuntuacion", -33);
    }

    private void asteroideColisionado() {
        game.setScreen(new PuntuacionScreen(game, recordActual, puntuacion));
    }


    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public Spacecraft getSpacecraft() {
        return spacecraft;
    }

    public Stage getStage() {
        return stage;
    }

}