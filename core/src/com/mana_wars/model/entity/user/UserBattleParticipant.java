package com.mana_wars.model.entity.user;

import com.mana_wars.model.entity.battle.BattleParticipant;
import com.mana_wars.model.entity.battle.Characteristic;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.BattleSkill;
import com.mana_wars.model.entity.skills.PassiveSkill;
import com.mana_wars.model.entity.skills.SkillCharacteristic;
import com.mana_wars.model.repository.UserManaRepository;

import java.util.List;

class UserBattleParticipant extends BattleParticipant {

    private final List<ActiveSkill> activeSkills;

    private BattleSkill toApply;
    private double battleTime;

    private final UserManaRepository userManaRepository;

    UserBattleParticipant(String name, final UserManaRepository userManaRepository,
                          List<ActiveSkill> activeSkills, List<PassiveSkill> passiveSkills) {
        super(name, 1000, activeSkills, passiveSkills);
        this.activeSkills = activeSkills;
        this.userManaRepository = userManaRepository;
        setCharacteristicValue(Characteristic.MANA, userManaRepository.getUserMana());
    }

    @Override
    public void update(double currentTime) {
        synchronized (this){
            battleTime = currentTime;

            if (toApply != null && toApply.isAvailableAt(currentTime)){
                super.applySkill(toApply.skill, currentTime);
            }
            toApply = null;
        }
    }

    @Override
    public void applySkillCharacteristic(SkillCharacteristic sc) {
        super.applySkillCharacteristic(sc);

        Characteristic c = sc.getCharacteristic();
        if (c == Characteristic.MANA){
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
        userManaRepository.setUserMana(userMana);
    }

    Iterable<ActiveSkill> getActiveSkills() {
        return activeSkills;
    }

}
