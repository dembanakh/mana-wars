package com.mana_wars.model.entity.user;

import com.mana_wars.model.entity.battle.participant.BattleParticipant;
import com.mana_wars.model.entity.base.Characteristic;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.ImmutableBattleSkill;
import com.mana_wars.model.entity.skills.PassiveSkill;
import com.mana_wars.model.entity.skills.SkillCharacteristic;

import java.util.List;

import io.reactivex.functions.Consumer;

class UserBattleParticipant extends BattleParticipant {

    private final List<ActiveSkill> activeSkills;
    private final Consumer<? super Integer> manaOnChanged;
    private ActiveSkill toApply;
    private double battleTime;

    UserBattleParticipant(String name, int currentUserMana, Consumer<? super Integer> manaOnChanged,
                          List<ActiveSkill> activeSkills, Iterable<PassiveSkill> passiveSkills) {
        super(name, 1000, activeSkills, passiveSkills, 0,0,0);
        this.activeSkills = activeSkills;
        this.manaOnChanged = manaOnChanged;
        setCharacteristicValue(Characteristic.MANA, currentUserMana);
    }

    @Override
    public void update(double currentTime) {
        synchronized (this) {
            battleTime = currentTime;

            if (toApply != null && isAvailableAt(toApply)) {
                super.applySkill(toApply, currentTime);
            }
            toApply = null;
        }
    }

    @Override
    public void applySkillCharacteristic(SkillCharacteristic sc, int skillLevel) {
        super.applySkillCharacteristic(sc, skillLevel);

        if (sc.isManaCost()) {
            updateManaAmount(getCharacteristicValue(sc.getCharacteristic()));
        }
    }

    boolean tryApplyActiveSkill(ActiveSkill skill) {
        if (getCharacteristicValue(Characteristic.MANA) >= skill.getManaCost()
                && isAvailableAt(skill)) {
            synchronized (this) {
                toApply = skill;
            }
            return true;
        }
        return false;
    }

    private boolean isAvailableAt(ActiveSkill appliedSkill) {
        for (ImmutableBattleSkill skill : skills) {
            if (skill.getSkill() == appliedSkill) return skill.isAvailableAt(battleTime);
        }
        return false;
    }

    private void updateManaAmount(int userMana) {
        setCharacteristicValue(Characteristic.MANA, userMana);
        try {
            manaOnChanged.accept(userMana);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Iterable<ActiveSkill> getActiveSkills() {
        return activeSkills;
    }

}
