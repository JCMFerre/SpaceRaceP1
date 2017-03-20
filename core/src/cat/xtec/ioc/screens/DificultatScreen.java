package cat.xtec.ioc.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.util.Set;

import cat.xtec.ioc.SpaceRace;
import cat.xtec.ioc.helpers.AssetManager;
import cat.xtec.ioc.utils.Settings;

public class DificultatScreen extends ChangeListener implements Screen {

    private SpaceRace game;
    private Stage stage;

    public DificultatScreen(SpaceRace game) {
        this.game = game;
        configurarCamaraYStage();
        anadirTextButtons();
        Gdx.input.setInputProcessor(stage);
    }

    private void anadirTextButtons() {
        TextButton.TextButtonStyle estilo = new TextButton.TextButtonStyle();
        estilo.font = AssetManager.font;

        TextButton facil = new TextButton("Facil", estilo);
        facil.addListener(this);
        facil.setName("1");
        facil.setPosition(Settings.GAME_WIDTH / 2 - facil.getWidth() / 2,
                Settings.GAME_HEIGHT * 0.25f - facil.getHeight() / 2);

        TextButton medio = new TextButton("Medio", estilo);
        medio.setName("2");
        medio.addListener(this);
        medio.setPosition(Settings.GAME_WIDTH / 2 - medio.getWidth() / 2,
                Settings.GAME_HEIGHT * 0.5f - medio.getHeight() / 2);

        TextButton dificil = new TextButton("Dificil", estilo);
        dificil.setName("3");
        dificil.addListener(this);
        dificil.setPosition(Settings.GAME_WIDTH / 2 - dificil.getWidth() / 2,
                Settings.GAME_HEIGHT * 0.75f - dificil.getHeight() / 2);

        Label infoLabel = new Label("Selecciona la dificultad", new Label.LabelStyle(AssetManager.font, null));

        infoLabel.setFontScale(0.25f);
        infoLabel.setPosition(Settings.GAME_WIDTH / 2 - infoLabel.getWidth() * 0.30f, infoLabel.getHeight() / 2);

        stage.addActor(facil);
        stage.addActor(medio);
        stage.addActor(dificil);
        stage.addActor(infoLabel);
    }

    private void configurarCamaraYStage() {
        OrthographicCamera camera = new OrthographicCamera(Settings.GAME_WIDTH, Settings.GAME_HEIGHT);
        camera.setToOrtho(true);
        StretchViewport viewport = new StretchViewport(Settings.GAME_WIDTH, Settings.GAME_HEIGHT, camera);
        stage = new Stage(viewport);
        stage.addActor(new Image(AssetManager.background));
    }

    @Override
    public void changed(ChangeEvent event, Actor actor) {
        int dificultad = Integer.parseInt(actor.getName());
        guardarDificultatPreferencias(dificultad);
        //Gdx.app.log("dif", "" + dificultad);
        game.setScreen(new GameScreen(game, dificultad));
    }

    private void guardarDificultatPreferencias(int dificultad) {
        Preferences preferences = Gdx.app.getPreferences("preferencias");
        preferences.putInteger("dificultad", dificultad);
        preferences.flush();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        stage.draw();
        stage.act(delta);
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
        game.dispose();
    }

}
