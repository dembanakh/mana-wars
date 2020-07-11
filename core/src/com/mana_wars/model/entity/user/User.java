package com.mana_wars.model.entity.user;

import com.mana_wars.model.entity.base.Rarity;
import com.mana_wars.model.entity.battle.participant.BattleParticipant;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.PassiveSkill;
import com.mana_wars.model.repository.UserLevelExperienceRepository;
import com.mana_wars.model.repository.UserManaRepository;
import com.mana_wars.model.repository.UsernameRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

public class User implements
        UserMenuAPI, UserSkillsAPI, UserDungeonsAPI,
        UserBattleAPI, UserGreetingAPI, UserBattleSummaryAPI,
        UserShopAPI {

    private UserBattleParticipant user;

    private final UsernameRepository usernameRepository;
    private final UserManaRepository userManaRepository;
    private final UserLevelExperienceRepository userLevelExperienceRepository;

    private final Subject<Integer> manaAmountObservable;
    private final Subject<Integer> userLevelObservable;
    private List<ActiveSkill> activeSkills;
    private List<PassiveSkill> passiveSkills;

    private int experienceCount;
    private int nextLevelRequiredExperience;

    public User(UserManaRepository userManaRepository, UserLevelExperienceRepository userLevelExperienceRepository,
                UsernameRepository usernameRepository) {
        this.userManaRepository = userManaRepository;
        this.userLevelExperienceRepository = userLevelExperienceRepository;
        this.usernameRepository = usernameRepository;

        this.experienceCount = userLevelExperienceRepository.getCurrentUserExperience();
        int userLevel = userLevelExperienceRepository.getUserLevel();
        this.nextLevelRequiredExperience = userLevelExperienceRepository.getUserLevelRequiredExperience().get(userLevel);

        System.out.println(experienceCount);

        userLevelObservable = BehaviorSubject.createDefault(userLevel);
        manaAmountObservable = BehaviorSubject.createDefault(userManaRepository.getUserMana());
    }

    @Override
    public BattleParticipant prepareBattleParticipant() {
        if (activeSkills == null || passiveSkills == null) throw new IllegalStateException("User was not provided with active or passive skills");

        return user = new UserBattleParticipant(usernameRepository.getUsername(), userManaRepository.getUserMana(),
                this::setManaAmount, activeSkills, cleanPassiveSkills(passiveSkills));
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
        setManaAmount(userManaRepository.getUserMana() + delta);
    }

    @Override
    public String getName() {
        return usernameRepository.getUsername();
    }

    @Override
    public int getLevel() {
        return userLevelExperienceRepository.getUserLevel();
    }

    @Override
    public int getManaAmount() {
        return userManaRepository.getUserMana();
    }

    @Override
    public boolean tryApplyActiveSkill(int skillIndex) {
        return user.tryApplyActiveSkill(skillIndex);
    }

    @Override
    public Iterable<ActiveSkill> getActiveSkills() {
        return user.getActiveSkills();
    }

    private void setManaAmount(int mana) {
        userManaRepository.setUserMana(mana);
        manaAmountObservable.onNext(mana);
    }

    @Override
    public void checkNextLevel(){
        while (experienceCount >= nextLevelRequiredExperience){
            int userLevel = getLevel() + 1;
            userLevelExperienceRepository.setUserLevel(userLevel);
            this.nextLevelRequiredExperience = userLevelExperienceRepository.getUserLevelRequiredExperience().get(userLevel);
            userLevelObservable.onNext(userLevel);
        }
    }

    @Override
    public void updateExperience(int delta) {
        experienceCount = userLevelExperienceRepository.getCurrentUserExperience() + delta;
        userLevelExperienceRepository.setCurrentUserExperience(experienceCount);
        checkNextLevel();
    }

    private Iterable<PassiveSkill> cleanPassiveSkills(Iterable<PassiveSkill> passiveSkills) {
        List<PassiveSkill> cleaned = new ArrayList<>();
        for (PassiveSkill skill : passiveSkills) {
            if (skill.getRarity() != Rarity.EMPTY)
                cleaned.add(skill);
        }
        return cleaned;
    }

}
