package cat.xtec.ioc.objects;

import com.badlogic.gdx.scenes.scene2d.Group;

import cat.xtec.ioc.utils.Settings;

/**
 * Created by ALUMNEDAM on 16/03/2017.
 */

public class FondosInicialGroup extends Group {

    private Background back1, back2;

    public FondosInicialGroup() {
        this.back1 = new Background(0, 0, Settings.GAME_WIDTH * 2, Settings.GAME_HEIGHT, Settings.BG_SPEED);
        this.back2 = new Background(back1.getTailX(), 0, Settings.GAME_WIDTH * 2, Settings.GAME_HEIGHT, Settings.BG_SPEED);
        addActor(back1);
        addActor(back2);
    }
}
