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
package com.skpd.pixeldungeonskills.items.food;

import com.skpd.pixeldungeonskills.actors.buffs.Buff;
import com.skpd.pixeldungeonskills.actors.buffs.Burning;
import com.skpd.pixeldungeonskills.actors.buffs.Hunger;
import com.skpd.pixeldungeonskills.actors.buffs.Paralysis;
import com.skpd.pixeldungeonskills.actors.buffs.Poison;
import com.skpd.pixeldungeonskills.actors.buffs.Roots;
import com.skpd.pixeldungeonskills.actors.buffs.Slow;
import com.skpd.pixeldungeonskills.actors.hero.Hero;
import com.skpd.pixeldungeonskills.messages.Messages;
import com.skpd.pixeldungeonskills.sprites.ItemSpriteSheet;
import com.skpd.pixeldungeonskills.utils.GLog;
import com.skpd.utils.Random;

public class MysteryMeat extends Food {

	{
		image = ItemSpriteSheet.MEAT;
		energy = Hunger.STARVING - Hunger.HUNGRY;
		message = Messages.get(MysteryMeat.class,"1");
	}
	
	@Override
	public void execute( Hero hero, String action ) {
		
		super.execute( hero, action );
		
		if (action.equals( AC_EAT )) {
			
			switch (Random.Int( 5 )) {
			case 0:
				GLog.w(  Messages.get(MysteryMeat.class,"2") );
				Buff.affect( hero, Burning.class ).reignite( hero );
				break;
			case 1:
				GLog.w(  Messages.get(MysteryMeat.class,"3") );
				Buff.prolong( hero, Roots.class, Paralysis.duration( hero ) );
				break;
			case 2:
				GLog.w(  Messages.get(MysteryMeat.class,"4") );
				Buff.affect( hero, Poison.class ).set( Poison.durationFactor( hero ) * hero.HT / 5 );
				break;
			case 3:
				GLog.w(  Messages.get(MysteryMeat.class,"5") );
				Buff.prolong( hero, Slow.class, Slow.duration( hero ) );
				break;
			}
		}
	}
	
	public int price() {
		return 5 * quantity;
	};
}
