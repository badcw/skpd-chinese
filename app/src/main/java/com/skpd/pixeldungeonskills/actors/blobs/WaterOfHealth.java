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
package com.skpd.pixeldungeonskills.actors.blobs;

import com.skpd.noosa.audio.Sample;
import com.skpd.pixeldungeonskills.Assets;
import com.skpd.pixeldungeonskills.Dungeon;
import com.skpd.pixeldungeonskills.Journal;
import com.skpd.pixeldungeonskills.Journal.Feature;
import com.skpd.pixeldungeonskills.actors.buffs.Hunger;
import com.skpd.pixeldungeonskills.actors.hero.Hero;
import com.skpd.pixeldungeonskills.effects.BlobEmitter;
import com.skpd.pixeldungeonskills.effects.CellEmitter;
import com.skpd.pixeldungeonskills.effects.Speck;
import com.skpd.pixeldungeonskills.effects.particles.ShaftParticle;
import com.skpd.pixeldungeonskills.items.DewVial;
import com.skpd.pixeldungeonskills.items.Item;
import com.skpd.pixeldungeonskills.items.potions.PotionOfHealing;
import com.skpd.pixeldungeonskills.messages.Messages;
import com.skpd.pixeldungeonskills.utils.GLog;

public class WaterOfHealth extends WellWater {

	private static final String TXT_PROCCED =
			Messages.get(WaterOfHealth.class,"1");
	
	@Override
	protected boolean affectHero( Hero hero ) {
		
		Sample.INSTANCE.play( Assets.SND_DRINK );
		
		PotionOfHealing.heal( hero );
		hero.belongings.uncurseEquipped();
		((Hunger)hero.buff( Hunger.class )).satisfy( Hunger.STARVING );
		
		CellEmitter.get( pos ).start( ShaftParticle.FACTORY, 0.2f, 3 );

		Dungeon.hero.interrupt();
	
		GLog.p( TXT_PROCCED );
		
		Journal.remove( Feature.WELL_OF_HEALTH );
		
		return true;
	}
	
	@Override
	protected Item affectItem( Item item ) {
		if (item instanceof DewVial && !((DewVial)item).isFull()) {
			((DewVial)item).fill();
			Journal.remove( Feature.WELL_OF_HEALTH );
			return item;
		}
		
		return null;
	}
	
	@Override
	public void use( BlobEmitter emitter ) {
		super.use( emitter );	
		emitter.start( Speck.factory( Speck.HEALING ), 0.5f, 0 );
	}
	
	@Override
	public String tileDesc() {
		return 
			Messages.get(this,"2");
	}
}
