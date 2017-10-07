package com.skpd.pixeldungeonskills.skills;

import com.skpd.pixeldungeonskills.Dungeon;
import com.skpd.pixeldungeonskills.actors.hero.Hero;

import java.util.ArrayList;

/**
 * Created by Moussa on 20-Jan-17.
 */
public class WarriorActive extends BranchSkill{



    {
        image = 16;
        level = 0;
    }

    @Override
    public ArrayList<String> actions( Hero hero ) {
        ArrayList<String> actions = new ArrayList<String>();
        if(canUpgrade())
            actions.add(AC_ADVANCE);
        return actions;
    }


    @Override
    public void execute( Hero hero, String action ) {
        if(action == Skill.AC_ADVANCE)
            hero.heroSkills.advance(CurrentSkills.BRANCHES.ACTIVE);
    }

    @Override
    protected int totalSpent()
    {
        return Dungeon.hero.heroSkills.totalSpent(CurrentSkills.BRANCHES.ACTIVE);
    }

    @Override
    protected int nextUpgradeCost()
    {
        return Dungeon.hero.heroSkills.nextUpgradeCost(CurrentSkills.BRANCHES.ACTIVE);
    }

    @Override
    protected boolean canUpgrade()
    {
        return Dungeon.hero.heroSkills.canUpgrade(CurrentSkills.BRANCHES.ACTIVE);
    }
}
