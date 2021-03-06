package com.mana_wars.model.entity.user;

import com.mana_wars.model.entity.base.Rarity;
import com.mana_wars.model.entity.battle.participant.BattleParticipant;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.PassiveSkill;
import com.mana_wars.model.repository.UserLevelExperienceRepository;
import com.mana_wars.model.repository.UserManaRepository;
import com.mana_wars.model.repository.UserSkillCasesRepository;
import com.mana_wars.model.repository.UsernameRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

public class User implements
        UserMenuAPI, UserSkillsAPI, UserDungeonsAPI,
        UserBattleAPI, UserGreetingAPI, UserBattleSummaryAPI,
        UserShopAPI {

    private final UsernameRepository usernameRepository;
    private final UserManaRepository userManaRepository;
    private final UserLevelExperienceRepository userLevelExperienceRepository;
    private final UserSkillCasesRepository userSkillCasesRepository;
    private final Subject<Integer> manaAmountObservable;
    private final Subject<Integer> levelObservable;
    private final Subject<Integer> nextLevelRequiredExperienceObservable;
    private final Subject<Integer> experienceObservable;

    private UserBattleParticipant user;

    private int nextLevelRequiredExperience;
    private boolean isMaxLvl;

    public User(UserManaRepository userManaRepository, UserLevelExperienceRepository userLevelExperienceRepository,
                UsernameRepository usernameRepository, UserSkillCasesRepository userSkillCasesRepository) {
        this.userManaRepository = userManaRepository;
        this.userLevelExperienceRepository = userLevelExperienceRepository;
        this.usernameRepository = usernameRepository;
        this.userSkillCasesRepository = userSkillCasesRepository;

        int userLevel = userLevelExperienceRepository.getUserLevel();
        List<Integer> lvlRequirements = userLevelExperienceRepository.getUserLevelRequiredExperience();
        isMaxLvl = lvlRequirements.size() <= userLevel;
        if(!isMaxLvl){
            this.nextLevelRequiredExperience = lvlRequirements.get(userLevel);
        }

        levelObservable = BehaviorSubject.createDefault(userLevel);
        manaAmountObservable = BehaviorSubject.createDefault(userManaRepository.getUserMana());
        nextLevelRequiredExperienceObservable = BehaviorSubject.createDefault(nextLevelRequiredExperience);
        experienceObservable = BehaviorSubject.createDefault(userLevelExperienceRepository.getCurrentUserExperience());
    }

    @Override
    public BattleParticipant prepareBattleParticipant(List<ActiveSkill> activeSkills, Iterable<PassiveSkill> passiveSkills) {
        return user = new UserBattleParticipant(usernameRepository.getUsername(),
                userLevelExperienceRepository.getUserLevel(), userManaRepository.getUserMana(),
                this::setManaAmount, activeSkills, cleanPassiveSkills(passiveSkills));
    }

    @Override
    public void updateManaAmount(int delta) {
        setManaAmount(userManaRepository.getUserMana() + delta);
    }

    @Override
    public boolean tryApplyActiveSkill(ActiveSkill skill) {
        return user.tryApplyActiveSkill(skill);
    }

    @Override
    public void checkNextLevel() {
        if (isMaxLvl) return;

        int experienceCount = userLevelExperienceRepository.getCurrentUserExperience();
        List<Integer> lvlRequirements = userLevelExperienceRepository.getUserLevelRequiredExperience();
        int maxLvl = lvlRequirements.size();

        while (experienceCount >= nextLevelRequiredExperience) {
            int userLevel = getLevel() + 1;
            experienceCount -= nextLevelRequiredExperience;
            userLevelExperienceRepository.setUserLevel(userLevel);
            if(userLevel == maxLvl){
                isMaxLvl = true;
                experienceCount = 0;
                levelObservable.onNext(userLevel);
                break;
            }
            nextLevelRequiredExperience = userLevelExperienceRepository.getUserLevelRequiredExperience().get(userLevel);
            nextLevelRequiredExperienceObservable.onNext(nextLevelRequiredExperience);
            levelObservable.onNext(userLevel);
        }
        userLevelExperienceRepository.setCurrentUserExperience(experienceCount);
    }

    @Override
    public void updateExperience(int delta) {
        if (isMaxLvl) return;
        int experienceCount = userLevelExperienceRepository.getCurrentUserExperience() + delta;
        userLevelExperienceRepository.setCurrentUserExperience(experienceCount);
        experienceObservable.onNext(experienceCount);
        checkNextLevel();
    }

    @Override
    public int updateSkillCases(int delta) {
        userSkillCasesRepository.updateSkillCasesNumber(delta);
        return userSkillCasesRepository.getSkillCasesNumber();
    }

    private Iterable<PassiveSkill> cleanPassiveSkills(Iterable<PassiveSkill> passiveSkills) {
        List<PassiveSkill> cleaned = new ArrayList<>();
        for (PassiveSkill skill : passiveSkills) {
            if (skill.getRarity() != Rarity.EMPTY)
                cleaned.add(skill);
        }
        return cleaned;
    }

    //region Getters and Setters
    @Override
    public Iterable<ActiveSkill> getActiveSkills() {
        return user.getActiveSkills();
    }

    @Override
    public String getName() {
        return usernameRepository.getUsername();
    }

    @Override
    public void setName(String name) {
        usernameRepository.setUsername(name);
    }

    @Override
    public int getLevel() {
        return userLevelExperienceRepository.getUserLevel();
    }

    @Override
    public int getManaAmount() {
        return userManaRepository.getUserMana();
    }

    private void setManaAmount(int mana) {
        userManaRepository.setUserMana(mana);
        manaAmountObservable.onNext(mana);
    }

    @Override
    public Observable<Integer> getManaAmountObservable() {
        return manaAmountObservable;
    }

    @Override
    public Observable<Integer> getUserLevelObservable() {
        return levelObservable;
    }

    @Override
    public Observable<Integer> getLevelObservable() {
        return levelObservable;
    }

    @Override
    public Observable<Integer> getExperienceObservable() {
        return experienceObservable;
    }

    @Override
    public Observable<Integer> getNextLevelRequiredExperienceObservable() {
        return nextLevelRequiredExperienceObservable;
    }

    @Override
    public int getSkillCasesNumber() {
        return userSkillCasesRepository.getSkillCasesNumber();
    }
    //endregion

}
