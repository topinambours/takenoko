package takenoko;

import dnl.utils.text.table.TextTable;
import org.springframework.context.ApplicationContext;
import takenoko.util.Console;
import takenoko.util.exceptions.EmptyDeckException;
import takenoko.util.exceptions.NoActionSelectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Component
@ImportResource("classpath*:resources/default-config.xml")
@Import(Game.class)
public class GameStarter {

    private List<Game> games;

    @Autowired
    public GameStarter(int nbGame ,ApplicationContext appContext) throws EmptyDeckException, NoActionSelectedException {

        if (nbGame > 1){
            Console.Log.init("test");
        }

        games = new ArrayList<>();

        IntStream.range(0,nbGame).forEach(i -> games.add(appContext.getBean("standardGame", Game.class)));

        int i = 0;

        games.parallelStream().forEach(game1 -> {
            try {
                game1.play();
            } catch (EmptyDeckException e) {
                e.printStackTrace();
            } catch (NoActionSelectedException e) {
                e.printStackTrace();
            }
        });

        System.out.println("Bench Complete");
        report();
    }


    public void report(){
        Game support = games.get(0);

        String[] columnNames = new String[3];
        columnNames[0] = "Joueur";
        columnNames[1] = "Total Points";
        columnNames[2] = "Points/parties (moy)";

        String[][] data = new String[support.getJoueurs().size()][];

        int totalPoint;

        for (int i = 0; i < support.getJoueurs().size(); i++){
            totalPoint = 0;
            data[i] = new String[3];

            data[i][0] = "Robot_" + support.getJoueurs().get(i).getId();

            for (Game game : games){
                totalPoint += game.getJoueurs().get(i).getScore();
            }

            data[i][1] = "" + totalPoint;
            data[i][2] = "" + totalPoint / games.size();
        }

        TextTable tt = new TextTable(columnNames, data);
        tt.setSort(2, SortOrder.DESCENDING);
        tt.printTable();
    }

}
