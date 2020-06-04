package com.mana_wars.model.entity.battle;

import com.mana_wars.model.entity.User;
import com.mana_wars.model.entity.enemy.EnemyFactory;

import java.util.Collections;

public class PvEBattle extends BaseBattle {

    public PvEBattle(User user, EnemyFactory enemyFactory) {
        super(user, Collections.singletonList(user), enemyFactory.generateEnemies());
    }

    @Override
    public void init() {
        super.init();
        for (BattleParticipant mob : getEnemySide()) {
            mob.start();
        }
    }

    @Override
    protected void activateParticipantSkill(BattleEvent be) {
        super.activateParticipantSkill(be);
        //Todo Change into Bots instance
        //Todo handle intersections
        if (be.participant != getUser()) {
            addBattleEvent(new BattleEvent(
                    battleTime + be.skill.getCooldown() + be.skill.getCastTime(),
                    be.skill, be.participant));
        }
    }
}
