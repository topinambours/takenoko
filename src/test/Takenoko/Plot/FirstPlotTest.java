package Takenoko.Plot;

import Takenoko.Game;
import Takenoko.Joueur.Joueur;
import Takenoko.Joueur.Strategie.StrategieAction.Action;
import Takenoko.Util.Exceptions.EmptyDeckException;
import Takenoko.Util.Exceptions.NoActionSelectedException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FirstPlotTest {

    @Autowired
    private Game game;

    @Test public void test() throws EmptyDeckException, NoActionSelectedException {
        Joueur joueur = game.getJoueurs().get(0);

        joueur.turn(game, Action.Card);
        joueur.turn(game, Action.Plot);
        assertTrue(joueur.getPlot().haveWater());
    }
}
