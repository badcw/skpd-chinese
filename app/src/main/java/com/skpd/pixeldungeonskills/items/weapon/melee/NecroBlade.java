/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.skpd.pixeldungeonskills.items.weapon.melee;

import com.skpd.noosa.tweeners.AlphaTweener;
import com.skpd.pixeldungeonskills.actors.Actor;
import com.skpd.pixeldungeonskills.actors.hero.Hero;
import com.skpd.pixeldungeonskills.actors.mobs.npcs.Skeleton;
import com.skpd.pixeldungeonskills.effects.CellEmitter;
import com.skpd.pixeldungeonskills.effects.Pushing;
import com.skpd.pixeldungeonskills.effects.Speck;
import com.skpd.pixeldungeonskills.effects.particles.ShadowParticle;
import com.skpd.pixeldungeonskills.levels.Level;
import com.skpd.pixeldungeonskills.messages.Messages;
import com.skpd.pixeldungeonskills.scenes.GameScene;
import com.skpd.pixeldungeonskills.sprites.ItemSpriteSheet;
import com.skpd.pixeldungeonskills.utils.GLog;
import com.skpd.utils.Bundle;
import com.skpd.utils.Random;

import java.util.ArrayList;

public class NecroBlade extends MeleeWeapon {

    public static final String AC_HEAL = Messages.get(NecroBlade.class,"1");
    public static final String AC_SUMMON = Messages.get(NecroBlade.class,"2");
    public static final String AC_UPGRADE = Messages.get(NecroBlade.class,"3");

    {
		image = ItemSpriteSheet.NecroBlade5;
	}

    public int charge = 100;

	public NecroBlade() {
		super( 1, 0.7f, 1f );
	}

    @Override
    public ArrayList<String> actions( Hero hero ) {
        ArrayList<String> actions = super.actions( hero );
        if(charge > 25)
            actions.add(AC_HEAL);
        if(charge > 55)
            actions.add(AC_SUMMON);
        if(charge == 100)
            actions.add(AC_UPGRADE);
        return actions;
    }

    private int getCharge8(){
        return (int)(charge/8);
    }

    @Override
    public void execute( Hero hero, String action ) {
        if (action.equals( AC_HEAL )) {

            hero.HP += hero.HT * 0.35;
            if(hero.HP > hero.HT)
                hero.HP = hero.HT;
            GLog.p(Messages.format(Messages.get(this,"4"),(int) (hero.HT * 0.35)));
            updateCharge(-25);

         CellEmitter.center(hero.pos).burst(Speck.factory(Speck.HEALING), 1);
        // CellEmitter.center(hero.pos).burst(ShadowParticle.UP, Random.IntRange(1, 2));


        } else if (action.equals( AC_SUMMON )) {



            int newPos = hero.pos;
            if (Actor.findChar(newPos) != null) {
                ArrayList<Integer> candidates = new ArrayList<Integer>();
                boolean[] passable = Level.passable;

                for (int n : Level.NEIGHBOURS4) {
                    int c = hero.pos + n;
                    if (passable[c] && Actor.findChar(c) == null) {
                        candidates.add(c);
                    }
                }
                newPos = candidates.size() > 0 ? Random.element(candidates) : -1;
            }
            if (newPos != -1) {

                updateCharge(-55);
                Skeleton skel = new Skeleton();
                int skelLevel = this.level() > 1 ? 1 + this.level() : 1;
                if(skelLevel > 7);
                    skelLevel = 7;
                skel.spawn(skelLevel);
                skel.HP = skel.HT;
                skel.pos = newPos;

                GameScene.add(skel);
                Actor.addDelayed(new Pushing(skel, hero.pos, newPos), -1);

                skel.sprite.alpha(0);
                skel.sprite.parent.add(new AlphaTweener(skel.sprite, 1, 0.15f));
                CellEmitter.center(newPos).burst(ShadowParticle.UP, Random.IntRange(3, 5));
                GLog.p(Messages.get(NecroBlade.class,"5"));
            }
            else
            {
                GLog.i(Messages.get(NecroBlade.class,"6"));
            }

        }else if (action.equals( AC_UPGRADE )) {
            updateCharge(-100);
            this.upgrade(1);
            GLog.p(Messages.get(NecroBlade.class,"7"));
        }
        else
        {

            super.execute( hero, action );

        }
    }

    @Override
    public int damageRoll( Hero hero ) {
        int damage = super.damageRoll( hero );
        damage += Random.Int((int) (charge / 8));
        return damage;
    }


    public void updateCharge(int change)
    {
        charge += change;
        if(charge > 100)
            charge = 100;
        if(charge < 0)
            charge = 0;

        switch((int)Math.floor(charge / 20))
        {
            case 0: image = ItemSpriteSheet.NecroBlade0;
                break;
            case 1: image = ItemSpriteSheet.NecroBlade1;
                break;
            case 2: image = ItemSpriteSheet.NecroBlade2;
                break;
            case 3: image = ItemSpriteSheet.NecroBlade3;
                break;
            case 4: image = ItemSpriteSheet.NecroBlade4;
                break;
            case 5: image = ItemSpriteSheet.NecroBlade5;
                break;
        }
    }
    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle(bundle);
        charge = bundle.getInt("CHARGE");
    }

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle(bundle);
        bundle.put( "CHARGE", charge );
    }

    @Override
	public String desc() {
		return Messages.format(Messages.get(this,"desc"),charge,getCharge8());
	}
}
