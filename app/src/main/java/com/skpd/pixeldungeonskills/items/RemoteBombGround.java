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
package com.skpd.pixeldungeonskills.items;

import com.skpd.noosa.audio.Sample;
import com.skpd.pixeldungeonskills.Assets;
import com.skpd.pixeldungeonskills.Dungeon;
import com.skpd.pixeldungeonskills.actors.Actor;
import com.skpd.pixeldungeonskills.actors.Char;
import com.skpd.pixeldungeonskills.actors.buffs.Buff;
import com.skpd.pixeldungeonskills.actors.buffs.Paralysis;
import com.skpd.pixeldungeonskills.actors.hero.Hero;
import com.skpd.pixeldungeonskills.effects.CellEmitter;
import com.skpd.pixeldungeonskills.effects.particles.BlastParticle;
import com.skpd.pixeldungeonskills.effects.particles.SmokeParticle;
import com.skpd.pixeldungeonskills.levels.Level;
import com.skpd.pixeldungeonskills.levels.Terrain;
import com.skpd.pixeldungeonskills.messages.Messages;
import com.skpd.pixeldungeonskills.scenes.GameScene;
import com.skpd.pixeldungeonskills.sprites.ItemSpriteSheet;
import com.skpd.pixeldungeonskills.utils.GLog;
import com.skpd.utils.Bundle;
import com.skpd.utils.Random;

public class RemoteBombGround extends Item {
	
	{
		image = ItemSpriteSheet.RemoteBomb;
		defaultAction = AC_THROW;
		stackable = true;

	}

    public int pos = 0;

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle(bundle);
        bundle.put( "pos", pos );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle(bundle);
        pos = bundle.getInt("pos");
    }

	@Override
	protected void onThrow( int cell ) {
		if (Level.pit[cell]) {
			super.onThrow( cell );
		} 


    }


    @Override
    public boolean doPickUp( Hero hero ) {
        GLog.i(Messages.get(RemoteBombGround.class,"1"));
        return false;
    }


	
	@Override
	public boolean isUpgradable() {
		return false;
	}
	
	@Override
	public boolean isIdentified() {
		return true;
	}
	
	@Override
	public Item random() {
		quantity = 1;
		return this;
	}	
	
	@Override
	public int price() {
		return 10 * quantity;
	}

    public void explode()
    {
        Sample.INSTANCE.play( Assets.SND_BLAST, 2 );

        if (Dungeon.visible[pos]) {
            CellEmitter.center(pos).burst( BlastParticle.FACTORY, 30 );
        }

        boolean terrainAffected = false;
        for (int n : Level.NEIGHBOURS9) {
            int c = pos + n;
            if (c >= 0 && c < Level.LENGTH) {
                if (Dungeon.visible[c]) {
                    CellEmitter.get( c ).burst( SmokeParticle.FACTORY, 4 );
                }

                if (Level.flamable[c]) {
                    Level.set( c, Terrain.EMBERS );
                    GameScene.updateMap( c );
                    terrainAffected = true;
                }

                Char ch = Actor.findChar(c);
                if (ch != null) {
                    int dmg = Random.Int(1 + Dungeon.depth, 10 + Dungeon.depth * 2) - Random.Int( ch.dr() );
                    if (dmg > 0) {
                        ch.damage( dmg, this );
                        if (ch.isAlive()) {
                            Buff.prolong(ch, Paralysis.class, 2);
                        }
                    }
                }
            }
        }


    }
}
