package com.skpd.pixeldungeonskills.skills;

import com.skpd.pixeldungeonskills.Dungeon;
import com.skpd.pixeldungeonskills.sprites.CharSprite;

/**
 * Created by Moussa on 25-Jan-17.
 */
public class MercArcherSkillA extends KeenShot {
    {
        tag = "mercA";
    }

    @Override
    protected boolean upgrade()
    {
        return false;
    }

    @Override
    public void castTextYell()
    {
        if(castText != "")
            Dungeon.hero.hiredMerc.sprite.showStatus(CharSprite.NEUTRAL, castText);
    }

}
