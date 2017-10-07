package com.skpd.pixeldungeonskills.skills;

import com.skpd.utils.Random;

/**
 * Created by Moussa on 20-Jan-17.
 */
public class IronTip extends PassiveSkillB3{


    {
        image = 83;
        tier = 3;
    }

    @Override
    public int passThroughTargets(boolean shout)
    {
        if(shout == false)
            return level;

        if(level > 0 && Random.Int(level + 1) > 0)
        {
            //castTextYell();
            multiTargetActive = true;
            return level;
        }
        multiTargetActive = false;

        return 0;
    }

    @Override
    protected boolean upgrade()
    {
        return true;
    }

}
