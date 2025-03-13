package model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import model.Player;

@AllArgsConstructor
@Getter
public enum OperationEnum {

    CHANGE_HP {
        @Override
        public void execute(Player player, Integer amount) {
            player.setHp(Integer.sum(player.getHp(), amount));
        }
    },
    CHANGE_MP{
        @Override
        public void execute(Player player, Integer amount) {
            player.setMp(Integer.sum(player.getMp(), amount));
        }
    },
    CHANGE_GOLD{
        @Override
        public void execute(Player player, Integer amount) {
            player.setGold(Integer.sum(player.getGold(), amount));
        }
    },
    ADD_ITEM{
        @Override
        public void execute(Player player, Integer amount) {

        }
    },
    REMOVE_ITEM{
        @Override
        public void execute(Player player, Integer amount) {

        }
    };

    public abstract void execute(Player player, Integer amount);

}
