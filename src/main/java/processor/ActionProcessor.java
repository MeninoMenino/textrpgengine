package processor;

import model.Action;
import model.Player;

public class ActionProcessor {

    public static void processAction(Player player, Action action) {
        action.getOperation().execute(player, action.getAmount());
    }

}
