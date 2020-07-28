package com.mana_wars.model.repository;

import com.mana_wars.model.SkillsListTriple;
import com.mana_wars.model.db.entity.base.DBDungeon;
import com.mana_wars.model.db.entity.base.DBDungeonRoundDescription;
import com.mana_wars.model.db.entity.base.DBSkill;
import com.mana_wars.model.db.entity.base.DBSkillCharacteristic;
import com.mana_wars.model.db.entity.query.CompleteUserSkill;
import com.mana_wars.model.db.entity.query.DBDungeonWithRoundsDescription;
import com.mana_wars.model.db.entity.query.UserSkill;
import com.mana_wars.model.entity.base.Rarity;
import com.mana_wars.model.entity.dungeon.Dungeon;
import com.mana_wars.model.entity.skills.Skill;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;

import static com.mana_wars.model.GameConstants.MAX_CHOSEN_ACTIVE_SKILL_COUNT;
import static com.mana_wars.model.GameConstants.USER_PASSIVE_SKILL_COUNT;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class DBMapperRepositoryTest {

    private static final CompositeDisposable disposable = new CompositeDisposable();

    private DBMapperRepository repository;

    @Mock
    private RoomRepository room;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        repository = new DBMapperRepository(room);
    }

    @Test
    public void testGetUserSkills() {
        List<CompleteUserSkill> mockUserSkills = new ArrayList<>(3);
        mockUserSkills.add(prepareMockSkill(true, 1, Rarity.COMMON, 1, 1, Collections.emptyList()));
        mockUserSkills.add(prepareMockSkill(false, 2, Rarity.EPIC, 2, 0, Collections.emptyList()));
        mockUserSkills.add(prepareMockSkill(false, 3, Rarity.ARCANE, 3, 3, Collections.emptyList()));
        when(room.getCompleteUserSkills()).thenReturn(Single.just(mockUserSkills));

        AtomicReference<SkillsListTriple> triple = new AtomicReference<>();
        disposable.add(repository.getUserSkills().subscribe(triple::set));

        assertNotNull(triple.get());
        assertNotNull(triple.get().activeSkills);
        assertNotNull(triple.get().passiveSkills);
        assertNotNull(triple.get().allSkills);

        assertEquals(1, triple.get().allSkills.size());
        Skill allSkill = triple.get().allSkills.get(0);
        assertEqualSkills(mockUserSkills.get(1), allSkill);

        assertEquals(MAX_CHOSEN_ACTIVE_SKILL_COUNT, triple.get().activeSkills.size());
        assertEqualSkills(mockUserSkills.get(0), triple.get().activeSkills.get(0));
        for (int i = 1; i < MAX_CHOSEN_ACTIVE_SKILL_COUNT; i++) {
            assertEquals(Rarity.EMPTY, triple.get().activeSkills.get(i).getRarity());
        }

        assertEquals(USER_PASSIVE_SKILL_COUNT, triple.get().passiveSkills.size());
        assertEqualSkills(mockUserSkills.get(2), triple.get().passiveSkills.get(2));
        for (int i = 0; i < USER_PASSIVE_SKILL_COUNT; i++) {
            if (i == 2) continue;
            assertEquals(Rarity.EMPTY, triple.get().passiveSkills.get(i).getRarity());
        }
    }

    @Test
    public void testGetDungeons() {
        List<DBDungeonWithRoundsDescription> mockDungeons = new ArrayList<>();
        mockDungeons.add(prepareDungeonDescription(1, 5, prepareRoundDescription(1, 2, 4)));
        when(room.getDBDungeonsWithRoundsDescription()).thenReturn(Single.just(mockDungeons));

        AtomicReference<List<Dungeon>> dungeons = new AtomicReference<>();
        disposable.add(repository.getDungeons().subscribe(dungeons::set));

        assertNotNull(dungeons.get());
        assertEquals(1, dungeons.get().size());
        Dungeon dungeon = dungeons.get().get(0);
        assertEquals(1, dungeon.getIconID());
        assertEquals("1", dungeon.getName());
        assertEquals(5, dungeon.getRequiredLvl());
        assertEquals(1, dungeon.getRoundDescriptions().size());
        assertEquals(2, dungeon.getRoundDescriptions().get(0).getMinOpponents());
        assertEquals(4, dungeon.getRoundDescriptions().get(0).getMaxOpponents());
    }

    @Test
    public void testGetReqManaAmountForBattle() {
        List<CompleteUserSkill> mockChosenSkills = new ArrayList<>();
        mockChosenSkills.add(prepareMockSkill(false, 1, Rarity.COMMON, 1, 2,
                Arrays.asList(prepareSkillChar(1, 50, 2, false, 0),
                        prepareSkillChar(2, 20, 1, true, -2))));
        mockChosenSkills.add(prepareMockSkill(false, 2, Rarity.EPIC, 2, 3,
                Arrays.asList(prepareSkillChar(3, 5, 3, true, 0),
                        prepareSkillChar(4, 15, 2, false, 0))));
        when(room.getChosenPassiveSkills()).thenReturn(Single.just(mockChosenSkills));

        AtomicInteger mana = new AtomicInteger();
        disposable.add(repository.getRequiredManaAmountForBattle().subscribe(mana::set));

        assertEquals(65, mana.get());
    }

    private void assertEqualSkills(CompleteUserSkill userSkill, Skill skill) {
        assertEquals(userSkill.skill.isActive(), skill.isActive());
        assertEquals(userSkill.skill.getId(), skill.getIconID());
    }

    private static DBDungeonWithRoundsDescription prepareDungeonDescription(int id, int reqLvl, DBDungeonRoundDescription roundDesc) {
        DBDungeonWithRoundsDescription dungeon = new DBDungeonWithRoundsDescription();
        dungeon.dungeon = new DBDungeon();
        dungeon.dungeon.setId(id);
        dungeon.dungeon.setName(String.valueOf(id));
        dungeon.dungeon.setRequiredLvl(reqLvl);
        roundDesc.setDungeonId(id);
        dungeon.roundDescriptions = Collections.singletonList(roundDesc);
        return dungeon;
    }

    private static DBSkillCharacteristic prepareSkillChar(int id, int value, int type, boolean increase, int target) {
        DBSkillCharacteristic sc = new DBSkillCharacteristic();
        sc.setId(id);
        sc.setValue(value);
        sc.setType(type);
        sc.setChangeType(increase);
        sc.setTarget(target);
        sc.setUpgradeFunction("LINEAR");
        return sc;
    }

    private static DBDungeonRoundDescription prepareRoundDescription(int id, int minOp, int maxOp) {
        DBDungeonRoundDescription round = new DBDungeonRoundDescription();
        round.setId(id);
        round.setRound(1);
        round.setMinOpponents(minOp);
        round.setMaxOpponents(maxOp);
        return round;
    }

    private static CompleteUserSkill prepareMockSkill(boolean active, int id, Rarity rarity, int level, int chosenID, List<DBSkillCharacteristic> characteristics) {
        CompleteUserSkill mock = new CompleteUserSkill();
        for (DBSkillCharacteristic sc : characteristics) sc.setSkillID(id);
        mock.characteristics = characteristics;
        mock.skill = new DBSkill();
        mock.skill.setActive(active);
        mock.skill.setCastTime(1d);
        mock.skill.setCooldown(2d);
        mock.skill.setId(id);
        mock.skill.setName(String.valueOf(id));
        mock.skill.setRarity(rarity.getId());
        mock.userSkill = new UserSkill();
        mock.userSkill.setId(id);
        mock.userSkill.setLvl(level);
        mock.userSkill.setChosen_id(chosenID);
        mock.userSkill.setSkillID(id);
        return mock;
    }

}