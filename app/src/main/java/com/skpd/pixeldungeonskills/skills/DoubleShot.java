package com.skpd.pixeldungeonskills.skills;

import com.skpd.pixeldungeonskills.Dungeon;
import com.skpd.pixeldungeonskills.actors.hero.Hero;
import com.skpd.pixeldungeonskills.ui.StatusPane;

/**
 * Created by Moussa on 20-Jan-17.
 */
public class DoubleShot extends ActiveSkill2{

    {
        image = 90;
        tier = 2;
        mana = 5;
    }

    private boolean onDouble = false; // prevent infinite loop

    @Override
    public void execute( Hero hero, String action ) {
        super.execute(hero, action);
        if(action == Skill.AC_ACTIVATE)
        {
            hero.heroSkills.active3.active = false; // Disable Bombvoyage
        }
    }

    @Override
    public boolean doubleShot()
    {
        if(active == false || Dungeon.hero.MP < getManaCost())
            return false;
        else if(onDouble == false)
        {
            onDouble = true;
            castTextYell();
            Dungeon.hero.MP -= getManaCost();
            StatusPane.manaDropping += getManaCost();
            return true;
        }
        onDouble = false;
        return false;
    }

    @Override
    protected boolean upgrade()
    {
        return true;
    }

}
