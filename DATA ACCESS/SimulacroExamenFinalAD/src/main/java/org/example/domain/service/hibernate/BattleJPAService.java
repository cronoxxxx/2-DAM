package org.example.domain.service.hibernate;

import jakarta.inject.Inject;
import org.example.dao.hibernate.model.Battle;
import org.example.dao.hibernate.repo.BattleRepoJPA;

import java.util.List;

public class BattleJPAService {
    private final BattleRepoJPA battleRepo;
    @Inject
    public BattleJPAService(BattleRepoJPA battleRepo) {
        this.battleRepo = battleRepo;
    }

    public List<Battle> getAllBattles() {
        return battleRepo.getAllBattles();
    }
    public int addBattle(Battle battle) {
        return battleRepo.saveBattle(battle);
    }
}
