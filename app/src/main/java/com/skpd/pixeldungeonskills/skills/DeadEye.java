package com.skpd.pixeldungeonskills.skills;


import com.skpd.pixeldungeonskills.Dungeon;
import com.skpd.pixeldungeonskills.ui.StatusPane;

/**
 * Created by Moussa on 20-Jan-17.
 */
public class DeadEye extends ActiveSkill2{


    {
        tier = 2;
        image = 66;
        mana = 3;
    }

    @Override
    public int damageBonus(int hp)
    {
        return damageBonus(hp, false);
    }

    @Override
    public int damageBonus(int hp, boolean castText)
    {
        if(active == false || Dungeon.hero.MP < getManaCost())
            return 0;
        else
        {
            if(castText)
            {
                castTextYell();
                Dungeon.hero.MP -= getManaCost();
                StatusPane.manaDropping += getManaCost();
            }

            return (int)(hp * 0.1f * level);
        }
    }

    @Override
    public int getManaCost()
    {
        return (int)Math.ceil(mana * (1 + 0.55 * level));
    }

    @Override
    protected boolean upgrade()
    {
        return true;
    }

}
