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
package com.skpd.pixeldungeonskills.windows;

import com.skpd.noosa.NinePatch;
import com.skpd.noosa.audio.Sample;
import com.skpd.noosa.ui.Component;
import com.skpd.pixeldungeonskills.Assets;
import com.skpd.pixeldungeonskills.Chrome;
import com.skpd.pixeldungeonskills.actors.hero.Hero;
import com.skpd.pixeldungeonskills.actors.mobs.npcs.Blacksmith;
import com.skpd.pixeldungeonskills.items.Item;
import com.skpd.pixeldungeonskills.messages.Messages;
import com.skpd.pixeldungeonskills.scenes.GameScene;
import com.skpd.pixeldungeonskills.scenes.PixelScene;
import com.skpd.pixeldungeonskills.ui.ItemSlot;
import com.skpd.pixeldungeonskills.ui.RedButton;
import com.skpd.pixeldungeonskills.ui.RenderedTextMultiline;
import com.skpd.pixeldungeonskills.ui.Window;
import com.skpd.pixeldungeonskills.utils.Utils;

public class WndBlacksmith extends Window {

	private static final int BTN_SIZE	= 36;
	private static final float GAP		= 2;
	private static final float BTN_GAP	= 10;
	private static final int WIDTH		= 116;
	
	private ItemButton btnPressed;
	
	private ItemButton btnItem1;
	private ItemButton btnItem2;
	private RedButton btnReforge;
	
	private static final String TXT_PROMPT = Messages.get(WndBlacksmith.class,"1");
	private static final String TXT_SELECT = Messages.get(WndBlacksmith.class,"2");
	private static final String TXT_REFORGE = Messages.get(WndBlacksmith.class,"3");
	
	public WndBlacksmith( Blacksmith troll, Hero hero ) {
		
		super();
		
		IconTitle titlebar = new IconTitle();
		titlebar.icon( troll.sprite() );
		titlebar.label( Utils.capitalize( troll.name ) );
		titlebar.setRect( 0, 0, WIDTH, 0 );
		add( titlebar );
		
		RenderedTextMultiline message = PixelScene.renderMultiline( TXT_PROMPT, 6 );
		message.maxWidth ( WIDTH);

		message.setPos(0,titlebar.bottom() + GAP);
		add( message );
		
		btnItem1 = new ItemButton() {
			@Override
			protected void onClick() {
				btnPressed = btnItem1;
				GameScene.selectItem( itemSelector, WndBag.Mode.UPGRADEABLE, TXT_SELECT );
			}
		};
		btnItem1.setRect( (WIDTH - BTN_GAP) / 2 - BTN_SIZE, message.top() + message.height() + BTN_GAP, BTN_SIZE, BTN_SIZE );
		add( btnItem1 );
		
		btnItem2 = new ItemButton() {
			@Override
			protected void onClick() {
				btnPressed = btnItem2;
				GameScene.selectItem( itemSelector, WndBag.Mode.UPGRADEABLE, TXT_SELECT );
			}
		};
		btnItem2.setRect( btnItem1.right() + BTN_GAP, btnItem1.top(), BTN_SIZE, BTN_SIZE );
		add( btnItem2 );
		
		btnReforge = new RedButton( TXT_REFORGE ) {
			@Override
			protected void onClick() {
				Blacksmith.upgrade( btnItem1.item, btnItem2.item );
				hide();
			}
		};
		btnReforge.enable( false );
		btnReforge.setRect( 0, btnItem1.bottom() + BTN_GAP, WIDTH, 20 );
		add( btnReforge );
		
		
		resize( WIDTH, (int)btnReforge.bottom() );
	}
	
	protected WndBag.Listener itemSelector = new WndBag.Listener() {
		@Override
		public void onSelect( Item item ) {
			if (item != null) {
				btnPressed.item( item );
				
				if (btnItem1.item != null && btnItem2.item != null) {
					String result = Blacksmith.verify( btnItem1.item, btnItem2.item );
					if (result != null) {
						GameScene.show( new WndMessage( result ) );
						btnReforge.enable( false );
					} else {
						btnReforge.enable( true );
					}
				}
			}
		}
	};
	
	public static class ItemButton extends Component {
		
		protected NinePatch bg;
		protected ItemSlot slot;
		
		public Item item = null;
		
		@Override
		protected void createChildren() {
			super.createChildren();
			
			bg = Chrome.get( Chrome.Type.BUTTON );
			add( bg );
			
			slot = new ItemSlot() {
				@Override
				protected void onTouchDown() {
					bg.brightness( 1.2f );
					Sample.INSTANCE.play( Assets.SND_CLICK );
				};
				@Override
				protected void onTouchUp() {
					bg.resetColor();
				}
				@Override
				protected void onClick() {
					ItemButton.this.onClick();
				}
			};
			add( slot );
		}
		
		protected void onClick() {};
		
		@Override
		protected void layout() {	
			super.layout();
			
			bg.x = x;
			bg.y = y;
			bg.size( width, height );
			
			slot.setRect( x + 2, y + 2, width - 4, height - 4 );
		};
		
		public void item( Item item ) {
			slot.item( this.item = item );
		}
	}
}
