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
package com.skpd.pixeldungeonskills.items.bags;

import com.skpd.pixeldungeonskills.items.Item;
import com.skpd.pixeldungeonskills.items.wands.Wand;
import com.skpd.pixeldungeonskills.messages.Messages;
import com.skpd.pixeldungeonskills.sprites.ItemSpriteSheet;

public class WandHolster extends Bag {

	{
		image = ItemSpriteSheet.HOLSTER;
		
		size = 12;
	}
	
	@Override
	public boolean grab( Item item ) {
		return item instanceof Wand;
	}
	
	@Override
	public boolean collect( Bag container ) {
		if (super.collect( container )) {
			if (owner != null) {
				for (Item item : items) {
					((Wand)item).charge( owner );
				}
			}
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void onDetach( ) {
		for (Item item : items) {
			((Wand)item).stopCharging();
		}
	}
	
	@Override
	public int price() {
		return 50;
	}

	@Override
	public String info() {
		return Messages.format(Messages.get(WandHolster.class,"desc",size));
	}
}
