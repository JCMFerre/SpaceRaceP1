package cat.xtec.ioc.screens;

import com.badlogic.gdx.Game;
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
import com.badlogic.gdx.utils.viewport.StretchViewport;

import cat.xtec.ioc.helpers.AssetManager;
import cat.xtec.ioc.utils.Settings;

public class InicialScreen implements Screen {

    private Stage stage;
    private Game game;

    public InicialScreen(Game game) {
        this.game = game;
        OrthographicCamera camera = new OrthographicCamera(Settings.GAME_WIDTH, Settings.GAME_HEIGHT);
        camera.setToOrtho(true);
        StretchViewport viewport = new StretchViewport(Settings.GAME_WIDTH, Settings.GAME_HEIGHT, camera);
        stage = new Stage(viewport);
        Label label = new Label("SpaceRace", new Label.LabelStyle(AssetManager.font, null));
        label.setPosition(Settings.GAME_WIDTH / 2 - label.getWidth() / 2,
                Settings.GAME_HEIGHT / 2 - label.getHeight() / 2);
        label.addAction(Actions.repeat(RepeatAction.FOREVER, Actions.sequence(Actions.scaleTo(1.5f, 1.5f, 1), Actions.scaleTo(1, 1, 1))));
        // Container<Label> container = crearContenedorConLabel(label, true);
        /*TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = AssetManager.font;
        TextButton textButton = new TextButton("Toca aquí para jugar", textButtonStyle);
        textButton.setName("1");
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                InicialScreen.this.game.setScreen(new GameScreen(InicialScreen.this.game));
            }
        });
        textButton.setPosition(Settings.GAME_WIDTH / 2 - textButton.getWidth() / 2,
                Settings.GAME_HEIGHT / 2 - textButton.getHeight() / 2 + label.getHeight());*/
        Image spacecraft = new Image(AssetManager.spacecraft);
        float y = Settings.GAME_HEIGHT / 2 + label.getHeight();
        spacecraft.addAction(Actions.repeat(RepeatAction.FOREVER, Actions.sequence(Actions.moveTo(0 - spacecraft.getWidth(), y), Actions.moveTo(Settings.GAME_WIDTH, y, 5))));
        addActores(new Actor[]{new Image(AssetManager.background), /*container,*/ label, spacecraft/*, textButton*/});
        Gdx.input.setInputProcessor(stage);
    }

    private Container<Label> crearContenedorConLabel(Label label, boolean anadirAccionEscalar) {
        Container<Label> container = new Container(label);
        container.setPosition(Settings.GAME_WIDTH / 2, Settings.GAME_HEIGHT / 2);
        if (anadirAccionEscalar) {
            container.addAction(Actions.repeat(RepeatAction.FOREVER, Actions.sequence(Actions.scaleTo(1.5f, 1.5f, 1), Actions.scaleTo(1, 1, 1))));
        }
        return container.center();
    }

    private void addActores(Actor... actores) {
        for (Actor actor : actores) {
            stage.addActor(actor);
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        stage.draw();
        stage.act(delta);
        /*if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game));
        }*/
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
