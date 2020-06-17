package com.mana_wars.model.entity.user;

import com.mana_wars.model.entity.battle.BattleParticipant;
import com.mana_wars.model.entity.battle.Characteristic;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.BattleSkill;
import com.mana_wars.model.entity.skills.PassiveSkill;
import com.mana_wars.model.entity.skills.SkillCharacteristic;

import java.util.List;

import io.reactivex.functions.Consumer;

class UserBattleParticipant extends BattleParticipant {

    private final List<ActiveSkill> activeSkills;
    private final Consumer<? super Integer> manaOnChanged;
    private BattleSkill toApply;
    private double battleTime;

    UserBattleParticipant(String name, int currentUserMana, Consumer<? super Integer> manaOnChanged,
                          List<ActiveSkill> activeSkills, List<PassiveSkill> passiveSkills) {
        super(name, 1000, activeSkills, passiveSkills);
        this.activeSkills = activeSkills;
        this.manaOnChanged = manaOnChanged;
        setCharacteristicValue(Characteristic.MANA, currentUserMana);
    }

    @Override
    public void update(double currentTime) {
        synchronized (this) {
            battleTime = currentTime;

            if (toApply != null && toApply.isAvailableAt(currentTime)) {
                super.applySkill(toApply.skill, currentTime);
            }
            toApply = null;
        }
    }

    @Override
    public void applySkillCharacteristic(SkillCharacteristic sc) {
        super.applySkillCharacteristic(sc);

        Characteristic c = sc.getCharacteristic();
        if (c == Characteristic.MANA) {
            setManaAmount(getCharacteristicValue(c));
        }
    }

    boolean tryApplyActiveSkill(int skillIndex) {
        BattleSkill targetBattleSkill = battleSkills.get(skillIndex);

        if (getCharacteristicValue(Characteristic.MANA) >= targetBattleSkill.skill.getManaCost()
                && targetBattleSkill.isAvailableAt(battleTime)) {
            synchronized (this) {
                toApply = targetBattleSkill;
            }
            return true;
        }
        return false;
    }

    private void setManaAmount(int userMana) {
        setCharacteristicValue(Characteristic.MANA, userMana);
        try {
            this.manaOnChanged.accept(userMana);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Iterable<ActiveSkill> getActiveSkills() {
        return activeSkills;
    }

}
