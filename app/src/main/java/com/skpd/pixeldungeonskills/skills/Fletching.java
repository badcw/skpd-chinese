package com.skpd.pixeldungeonskills.skills;

/**
 * Created by Moussa on 20-Jan-17.
 */
public class Fletching extends PassiveSkillA1{


    {
        image = 73;
        tier = 1;
    }

    @Override
    public int fletching() {return level;}

    @Override
    protected boolean upgrade()
    {
        return true;
    }

}
