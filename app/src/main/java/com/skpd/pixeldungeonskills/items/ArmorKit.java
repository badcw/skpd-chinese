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
import com.skpd.pixeldungeonskills.actors.hero.Hero;
import com.skpd.pixeldungeonskills.effects.Speck;
import com.skpd.pixeldungeonskills.items.armor.Armor;
import com.skpd.pixeldungeonskills.items.armor.ClassArmor;
import com.skpd.pixeldungeonskills.messages.Messages;
import com.skpd.pixeldungeonskills.scenes.GameScene;
import com.skpd.pixeldungeonskills.sprites.HeroSprite;
import com.skpd.pixeldungeonskills.sprites.ItemSpriteSheet;
import com.skpd.pixeldungeonskills.utils.GLog;
import com.skpd.pixeldungeonskills.windows.WndBag;

import java.util.ArrayList;

public class ArmorKit extends Item {

	private static final String TXT_SELECT_ARMOR	= Messages.get(ArmorKit.class,"1");
	private static final String TXT_UPGRADED		= Messages.get(ArmorKit.class,"2");
	
	private static final float TIME_TO_UPGRADE = 2;
	
	private static final String AC_APPLY = Messages.get(ArmorKit.class,"3");
	
	{
		image = ItemSpriteSheet.KIT;
		unique = true;
	}
	
	@Override
	public ArrayList<String> actions( Hero hero ) {
		ArrayList<String> actions = super.actions( hero );
		actions.add( AC_APPLY );
		return actions;
	}
	
	@Override
	public void execute( Hero hero, String action ) {
		if (action == AC_APPLY) {

			curUser = hero;
			GameScene.selectItem( itemSelector, WndBag.Mode.ARMOR, TXT_SELECT_ARMOR );
			
		} else {
			
			super.execute( hero, action );
			
		}
	}
	
	@Override
	public boolean isUpgradable() {
		return false;
	}
	
	@Override
	public boolean isIdentified() {
		return true;
	}
	
	private void upgrade( Armor armor ) {
		
		detach( curUser.belongings.backpack );
		
		curUser.sprite.centerEmitter().start( Speck.factory( Speck.KIT ), 0.05f, 10 );
		curUser.spend( TIME_TO_UPGRADE );
		curUser.busy();
		
		GLog.w( TXT_UPGRADED, armor.name() );
		
		ClassArmor classArmor = ClassArmor.upgrade( curUser, armor );
		if (curUser.belongings.armor == armor) {
			
			curUser.belongings.armor = classArmor;
			((HeroSprite)curUser.sprite).updateArmor();
			
		} else {
			
			armor.detach( curUser.belongings.backpack );
			classArmor.collect( curUser.belongings.backpack );
			
		}
		
		curUser.sprite.operate( curUser.pos );
		Sample.INSTANCE.play( Assets.SND_EVOKE );
	}
	
	private final WndBag.Listener itemSelector = new WndBag.Listener() {
		@Override
		public void onSelect( Item item ) {
			if (item != null) {
				ArmorKit.this.upgrade( (Armor)item );
			}
		}
	};
}
