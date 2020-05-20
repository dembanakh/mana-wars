package com.mana_wars.model.entity.skills;

import com.mana_wars.model.entity.base.Rarity;
import com.mana_wars.model.entity.battle.BattleParticipant;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class SkillTest {


    @Test
    public void testActivate() {
        SkillCharacteristic sc1 = mock(SkillCharacteristic.class);
        when(sc1.getTarget()).thenReturn(SkillCharacteristic.Target.SELF);
        SkillCharacteristic sc2 = mock(SkillCharacteristic.class);
        when(sc2.getTarget()).thenReturn(SkillCharacteristic.Target.ENEMY);
        List<SkillCharacteristic> scList = new ArrayList<>();
        scList.add(sc1);
        scList.add(sc2);
        Skill sk = new Skill(1,1, Rarity.COMMON,"name", 10, scList);
        BattleParticipant self = mock(BattleParticipant.class);
        BattleParticipant enemy = mock(BattleParticipant.class);
        sk.activate(self,enemy);

        verify(self).applySkillCharacteristic(sc1);
        verify(enemy).applySkillCharacteristic(sc2);
        verifyNoMoreInteractions(self, enemy);
    }


}