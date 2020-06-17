package com.mana_wars.model.entity.user;

import com.mana_wars.model.entity.battle.BattleParticipant;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.PassiveSkill;
import com.mana_wars.model.repository.UserLevelRepository;
import com.mana_wars.model.repository.UserManaRepository;
import com.mana_wars.model.repository.UsernameRepository;

import java.util.List;

import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

public class User implements UserMenuAPI, UserSkillsAPI, UserDungeonsAPI, UserBattleAPI, UserGreetingAPI, UserBattleSummaryAPI {

    private UserBattleParticipant user;

    private final UsernameRepository usernameRepository;
    private final UserManaRepository userManaRepository;
    private final UserLevelRepository userLevelRepository;

    private final Subject<Integer> manaAmountObservable;
    private final Subject<Integer> userLevelObservable;
    private List<ActiveSkill> activeSkills;
    private List<PassiveSkill> passiveSkills;

    public User(UserManaRepository userManaRepository, UserLevelRepository userLevelRepository,
                UsernameRepository usernameRepository) {
        this.userManaRepository = userManaRepository;
        this.userLevelRepository = userLevelRepository;
        this.usernameRepository = usernameRepository;
        userLevelObservable = BehaviorSubject.createDefault(userLevelRepository.getUserLevel());
        manaAmountObservable = BehaviorSubject.createDefault(userManaRepository.getUserMana());
    }

    @Override
    public BattleParticipant prepareBattleParticipant() {
        return user = new UserBattleParticipant(usernameRepository.getUsername(), userManaRepository.getUserMana(),
                this::setUserMana, activeSkills, passiveSkills);
    }

    @Override
    public void initSkills(List<ActiveSkill> activeSkills, List<PassiveSkill> passiveSkills) {
        this.activeSkills = activeSkills;
        this.passiveSkills = passiveSkills;
    }

    @Override
    public void setName(String name) {
        usernameRepository.setUsername(name);
    }

    @Override
    public Subject<Integer> getManaAmountObservable() {
        return manaAmountObservable;
    }

    @Override
    public Subject<Integer> getUserLevelObservable() {
        return userLevelObservable;
    }

    @Override
    public void updateManaAmount(int delta) {
        setUserMana(userManaRepository.getUserMana() + delta);
    }

    @Override
    public String getName() {
        return usernameRepository.getUsername();
    }

    @Override
    public int getLevel() {
        return userLevelRepository.getUserLevel();
    }

    @Override
    public boolean tryApplyActiveSkill(int skillIndex) {
        return user.tryApplyActiveSkill(skillIndex);
    }

    @Override
    public Iterable<ActiveSkill> getActiveSkills() {
        return user.getActiveSkills();
    }

    private void setUserMana(int mana) {
        userManaRepository.setUserMana(mana);
        manaAmountObservable.onNext(mana);
    }

}
