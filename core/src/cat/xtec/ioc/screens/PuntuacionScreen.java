package cat.xtec.ioc.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import cat.xtec.ioc.SpaceRace;
import cat.xtec.ioc.helpers.AssetManager;
import cat.xtec.ioc.utils.Settings;

public class PuntuacionScreen implements Screen {

    private SpaceRace game;
    private Stage stage;

    public PuntuacionScreen(SpaceRace game, int recordActual, int puntuacion) {
        this.game = game;
        OrthographicCamera camera = new OrthographicCamera(Settings.GAME_WIDTH, Settings.GAME_HEIGHT);
        camera.setToOrtho(true);
        StretchViewport viewport = new StretchViewport(Settings.GAME_WIDTH, Settings.GAME_HEIGHT, camera);
        stage = new Stage(viewport);
        stage.addActor(new Image(AssetManager.background));
        cargarLabels(recordActual, puntuacion);
        Gdx.input.setInputProcessor(stage);
    }

    private void cargarLabels(int recordActual, int puntuacion) {
        Label labelRecord = new Label("Record actual: " + recordActual, new Label.LabelStyle(AssetManager.font, null));
        Label labelPuntuacion = new Label("Tu puntuacion: " + puntuacion, new Label.LabelStyle(AssetManager.font, null));
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = AssetManager.font;
        TextButton butonClick = new TextButton("Pulsa para volver a jugar", textButtonStyle);
        butonClick.getLabel().setFontScale(0.25f);
        butonClick.setPosition(Settings.GAME_WIDTH / 2 - butonClick.getWidth() / 2,
                Settings.GAME_HEIGHT * 0.75f - butonClick.getHeight() / 2);
        butonClick.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new GameScreen(game, obtenerDificultatPrefs()));
            }
        });

        /*Label labelInfoClick = new Label("Pulsa para volver a jugar", new Label.LabelStyle(AssetManager.font, null));
        labelInfoClick.setFontScale(0.30f);*/

        Container<Label> contenedorRecord = new Container<Label>(labelRecord);
        contenedorRecord.setPosition(Settings.GAME_WIDTH / 2, Settings.GAME_HEIGHT * 0.25f);

        Container<Label> contenedorPuntuacion = new Container<Label>(labelPuntuacion);
        contenedorPuntuacion.setPosition(Settings.GAME_WIDTH / 2, Settings.GAME_HEIGHT / 2);
        if (puntuacion >= recordActual) {
            contenedorPuntuacion.setTransform(true);
            contenedorPuntuacion.addAction(Actions.repeat(RepeatAction.FOREVER,
                    Actions.sequence(Actions.scaleTo(1.25f, 1.25f, 1),
                            Actions.scaleTo(1, 1, 1))));
        }

        /*Container<Label> contenedorInfoClick = new Container<Label>(labelInfoClick);
        contenedorInfoClick.setPosition(Settings.GAME_WIDTH / 2, Settings.GAME_HEIGHT * 0.75f);

        contenedorInfoClick.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
            }
        });*/
        stage.addActor(contenedorRecord);
        stage.addActor(contenedorPuntuacion);
        //stage.addActor(contenedorInfoClick);

        stage.addActor(butonClick);

        Image ajustes = new Image(AssetManager.ajustes);
        ajustes.setWidth(16f);
        ajustes.setHeight(16f);
        // ajustes.setScale(20f, 20f);
        ajustes.setPosition(Settings.GAME_WIDTH - ajustes.getWidth() - 4, 4f);
        TextButton settings = new TextButton("", textButtonStyle);
        settings.setWidth(ajustes.getWidth());
        settings.setHeight(ajustes.getHeight());
        settings.setPosition(ajustes.getX(), ajustes.getY());
        settings.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new DificultatScreen(game));
            }
        });
        stage.addActor(ajustes);

        stage.addActor(settings);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        stage.draw();
        stage.act(delta);
        /*if (Gdx.input.isTouched()) {
           game.setScreen(new GameScreen(game, obtenerDificultatPrefs()));
        }*/
    }

    private int obtenerDificultatPrefs() {
        return Gdx.app.getPreferences("preferencias").getInteger("dificultad", -33);
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
}
