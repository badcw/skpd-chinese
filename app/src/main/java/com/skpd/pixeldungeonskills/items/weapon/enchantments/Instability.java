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
package com.skpd.pixeldungeonskills.items.weapon.enchantments;

import com.skpd.pixeldungeonskills.actors.Char;
import com.skpd.pixeldungeonskills.items.weapon.Weapon;
import com.skpd.pixeldungeonskills.messages.Messages;

public class Instability extends Weapon.Enchantment {

	private static final String TXT_UNSTABLE	= Messages.get(Instability.class,"");
	
	@Override
	public boolean proc( Weapon weapon, Char attacker, Char defender, int damage ) {
		return random().proc( weapon, attacker, defender, damage );
	}
	
	@Override
	public String name( String weaponName) {
		return Messages.format( TXT_UNSTABLE, weaponName );
	}

}
