package Takenoko;

import Takenoko.Util.Console;

public class Main {
    public static void main(String[] args){
        Console.Log.init(Console.Mode.DEBUG);

        Game partie = new Game();

        partie.play();
    }
}
