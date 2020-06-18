package com.mana_wars.model.entity.user;

import com.mana_wars.model.entity.battle.BattleParticipant;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.PassiveSkill;
import com.mana_wars.model.repository.UserLevelRepository;
import com.mana_wars.model.repository.UserManaRepository;
import com.mana_wars.model.repository.UsernameRepository;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserTest {

    private User user;

    private UserManaRepository userManaRepository;
    private UserLevelRepository userLevelRepository;
    private UsernameRepository usernameRepository;

    @Before
    public void setup() {
        userManaRepository = mock(UserManaRepository.class);
        when(userManaRepository.getUserMana()).thenReturn(100);
        userLevelRepository = mock(UserLevelRepository.class);
        when(userLevelRepository.getUserLevel()).thenReturn(1);
        usernameRepository = mock(UsernameRepository.class);
        when(usernameRepository.getUsername()).thenReturn("a");
        user = new User(userManaRepository,userLevelRepository, usernameRepository);
    }

    @Test(expected = IllegalStateException.class)
    public void testPrepareBattleParticipant_Exception() {
        user.prepareBattleParticipant();
    }

    @Test
    public void testPrepareBattleParticipant() {
        List<ActiveSkill> activeSkills = Collections.singletonList(mock(ActiveSkill.class));
        List<PassiveSkill> passiveSkills = Collections.singletonList(mock(PassiveSkill.class));
        user.initSkills(activeSkills, passiveSkills);

        BattleParticipant bp = user.prepareBattleParticipant();

        assertEquals("a", bp.getName());
        assertEquals(passiveSkills, bp.getPassiveSkills());
    }

    @Test
    public void testSetName() {
        user.setName("b");
        verify(usernameRepository).setUsername("b");
    }

    @Test
    public void testUpdateManaAmount() {
        user.updateManaAmount(10);
        verify(userManaRepository).setUserMana(110);
    }

}