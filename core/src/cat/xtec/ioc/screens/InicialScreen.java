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
import com.badlogic.gdx.utils.viewport.StretchViewport;

import cat.xtec.ioc.SpaceRace;
import cat.xtec.ioc.helpers.AssetManager;
import cat.xtec.ioc.utils.Settings;

public class InicialScreen implements Screen {

    private Stage stage;
    private SpaceRace game;

    public InicialScreen(SpaceRace game) {
        this.game = game;
        iniciar();
    }

    private void iniciar() {
        configurarCamaraYStage();
        configurarLabelsYSpaceCraft();
        Gdx.input.setInputProcessor(stage);
    }

    private void cargarSpacecraft(float heightLabels) {
        Image spacecraft = new Image(AssetManager.spacecraft);
        float y = Settings.GAME_HEIGHT / 2 + heightLabels;
        spacecraft.addAction(Actions.repeat(RepeatAction.FOREVER, Actions.sequence(Actions.moveTo(0 - spacecraft.getWidth(), y), Actions.moveTo(Settings.GAME_WIDTH, y, 5))));
        stage.addActor(spacecraft);
    }

    private void configurarLabelsYSpaceCraft() {
        Label labelTitulo = new Label("SpaceRace", new Label.LabelStyle(AssetManager.font, null));
        Label labelInfo = new Label("Pulsa para jugar", new Label.LabelStyle(AssetManager.font, null));
        labelInfo.setFontScale(0.3f);
        Container<Label> containerTitulo = crearContenedorConLabel(labelTitulo, true, Settings.GAME_HEIGHT / 2 - labelTitulo.getHeight());
        Container<Label> containerLabelInfo = crearContenedorConLabel(labelInfo, false, Settings.GAME_HEIGHT / 2 + labelInfo.getHeight());
        stage.addActor(containerTitulo);
        stage.addActor(containerLabelInfo);
        cargarSpacecraft(labelInfo.getHeight() + labelTitulo.getHeight());
    }

    private void configurarCamaraYStage() {
        OrthographicCamera camera = new OrthographicCamera(Settings.GAME_WIDTH, Settings.GAME_HEIGHT);
        camera.setToOrtho(true);
        StretchViewport viewport = new StretchViewport(Settings.GAME_WIDTH, Settings.GAME_HEIGHT, camera);
        stage = new Stage(viewport);
        stage.addActor(new Image(AssetManager.background));
    }

    private Container<Label> crearContenedorConLabel(Label label, boolean anadirAccionEscalar, float height) {
        Container<Label> container = new Container(label);
        container.setPosition(Settings.GAME_WIDTH / 2, height);
        container.setTransform(true);
        if (anadirAccionEscalar) {
            container.addAction(Actions.repeat(RepeatAction.FOREVER,
                    Actions.sequence(Actions.scaleTo(1.25f, 1.25f, 1),
                            Actions.scaleTo(1, 1, 1))));
        }
        return container.center();
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        stage.draw();
        stage.act(delta);
        if (Gdx.input.isTouched()) {
            game.setScreen(new DificultatScreen(game));
        }
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
