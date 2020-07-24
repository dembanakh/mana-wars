package com.mana_wars.model.repository;

import com.mana_wars.model.entity.ShopSkill;
import com.mana_wars.model.entity.skills.Skill;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface DailySkillsRepository {
    Single<List<ShopSkill>> getDailySkills();
    Completable buySkill(Skill purchaseSkill);
}
