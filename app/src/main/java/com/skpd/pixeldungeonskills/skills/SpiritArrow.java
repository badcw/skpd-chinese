package com.skpd.pixeldungeonskills.skills;

import com.skpd.pixeldungeonskills.Dungeon;
import com.skpd.pixeldungeonskills.actors.hero.Hero;
import com.skpd.pixeldungeonskills.effects.CellEmitter;
import com.skpd.pixeldungeonskills.effects.particles.ElmoParticle;
import com.skpd.pixeldungeonskills.items.weapon.missiles.Arrow;
import com.skpd.pixeldungeonskills.ui.StatusPane;

import java.util.ArrayList;

/**
 * Created by Moussa on 20-Jan-17.
 */
public class SpiritArrow extends ActiveSkill1 {


    {
        image = 89;
        tier = 1;
        mana = 5;
    }

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = new ArrayList<String>();
        if (level > 0 && hero.MP >= getManaCost())
            actions.add(AC_CAST);
        return actions;
    }

    @Override
    public void execute(Hero hero, String action) {
        if (action == Skill.AC_CAST) {
            Arrow arrow = new Arrow(level);
            Dungeon.level.drop(arrow, hero.pos).sprite.drop();
            CellEmitter.get(hero.pos).burst(ElmoParticle.FACTORY, 4);
            hero.MP -= getManaCost();
            StatusPane.manaDropping += getManaCost();
            castTextYell();
            Dungeon.hero.heroSkills.lastUsed = this;
            hero.spend(TIME_TO_USE);
            hero.busy();
            hero.sprite.operate(hero.pos);
        }
    }

    @Override
    protected boolean upgrade() {
        return true;
    }

}

