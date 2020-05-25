package com.mana_wars.model.entity.battle;

import com.mana_wars.model.entity.User;
import com.mana_wars.model.entity.enemy.EnemyFactory;
import com.mana_wars.model.entity.enemy.Mob;
import com.mana_wars.model.entity.skills.ActiveSkill;

import java.util.Collections;


public class PvEBattle extends Battle{


    public PvEBattle(User user, EnemyFactory enemyFactory){
        super(user, Collections.singletonList(user), enemyFactory.generateEnemies());
    }

    @Override
    public void init() {
        super.init();
        for (BattleParticipant mob : enemySide){
            double activationTime = 0;
            for(ActiveSkill skill : ((Mob)mob).getInitialAutoSkills()){
                activationTime += skill.getCastTime();
                battleEvents.add(new BattleEvent(activationTime, skill, mob));
            }
        }
    }

    @Override
    protected void activateParticipantSkill(BattleEvent be) {
        super.activateParticipantSkill(be);
        be.skill.activate(be.participant, getOpponents(be.participant).get(0));

        //Todo Change into Bots instance
        //Todo handle intersactions
        if (be.participant != user){
            battleEvents.add(new BattleEvent(battleTime + be.skill.getCooldown() + be.skill.getCastTime(), be.skill, be.participant));
        }
    }
}
