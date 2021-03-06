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
package com.skpd.pixeldungeonskills.items.weapon.missiles;

import com.skpd.pixeldungeonskills.actors.Char;
import com.skpd.pixeldungeonskills.actors.buffs.Buff;
import com.skpd.pixeldungeonskills.actors.buffs.Burning;
import com.skpd.pixeldungeonskills.items.Item;
import com.skpd.pixeldungeonskills.messages.Messages;
import com.skpd.pixeldungeonskills.sprites.CharSprite;
import com.skpd.pixeldungeonskills.sprites.ItemSpriteSheet;
import com.skpd.pixeldungeonskills.utils.GLog;
import com.skpd.utils.Random;

public class FlameBow extends Bow {

	{
		image = ItemSpriteSheet.FlameBow;


        stackable = false;
	}



	public FlameBow() {
		this( 1 );
	}

	public FlameBow(int number) {
		super();
		quantity = number;
	}
	
	@Override
	public Item random() {
		quantity = 1;
		return this;
	}
	
	@Override
	public int price() {
		return quantity * 55;
	}



    @Override
    public void bowSpecial(Char target)
    {
        try
        {
            if (Random.Int(2) == 1)
            {
                Buff.affect(target, Burning.class).reignite(target);
                GLog.p(Messages.get(FlameBow.class,"1"));

                target.sprite.showStatus( CharSprite.NEUTRAL, Messages.get(FlameBow.class,"2"));
            }
        }
        catch (Exception ex)
        {

        }
    }
}
