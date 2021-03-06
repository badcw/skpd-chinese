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

import com.skpd.gltextures.SmartTexture;
import com.skpd.gltextures.TextureCache;
import com.skpd.noosa.Group;
import com.skpd.noosa.Image;
import com.skpd.noosa.RenderedText;
import com.skpd.noosa.TextureFilm;
import com.skpd.pixeldungeonskills.Assets;
import com.skpd.pixeldungeonskills.Dungeon;
import com.skpd.pixeldungeonskills.Statistics;
import com.skpd.pixeldungeonskills.actors.buffs.Buff;
import com.skpd.pixeldungeonskills.actors.hero.Hero;
import com.skpd.pixeldungeonskills.messages.Messages;
import com.skpd.pixeldungeonskills.scenes.GameScene;
import com.skpd.pixeldungeonskills.scenes.PixelScene;
import com.skpd.pixeldungeonskills.ui.BuffIndicator;
import com.skpd.pixeldungeonskills.ui.RedButton;
import com.skpd.pixeldungeonskills.utils.Utils;

import java.util.Locale;

public class WndHero extends WndTabbed {
	
	private static final String TXT_STATS	= Messages.get(WndHero.class,"1");
	private static final String TXT_BUFFS	= Messages.get(WndHero.class,"2");
	
	private static final String TXT_EXP		= Messages.get(WndHero.class,"3");
	private static final String TXT_STR		= Messages.get(WndHero.class,"4");
    private static final String TXT_MANA	= Messages.get(WndHero.class,"5");
	private static final String TXT_HEALTH	= Messages.get(WndHero.class,"6");
	private static final String TXT_GOLD	= Messages.get(WndHero.class,"7");
	private static final String TXT_DEPTH	= Messages.get(WndHero.class,"8");


    private static final String TXT_Difficulty		= Messages.get(WndHero.class,"9");

	private static final int WIDTH		= 100;
	private static final int TAB_WIDTH	= 40;
	
	private StatsTab stats;
	private BuffsTab buffs;
	
	private SmartTexture icons;
	private TextureFilm film;
	
	public WndHero() {
		
		super();
		
		icons = TextureCache.get( Assets.BUFFS_LARGE );
		film = new TextureFilm( icons, 16, 16 );
		
		stats = new StatsTab();
		add( stats );
		
		buffs = new BuffsTab();
		add( buffs );
		
		add( new LabeledTab( TXT_STATS ) {
			protected void select( boolean value ) {
				super.select( value );
				stats.visible = stats.active = selected;
			};
		} );
		add( new LabeledTab( TXT_BUFFS ) {
			protected void select( boolean value ) {
				super.select( value );
				buffs.visible = buffs.active = selected;
			};
		} );
		for (Tab tab : tabs) {
			tab.setSize( TAB_WIDTH, tabHeight() );
		}
		
		resize( WIDTH, (int)Math.max( stats.height(), buffs.height() ) );
		
		select( 0 );
	}
	
	private class StatsTab extends Group {
		
		private final String TXT_TITLE		= Messages.get(WndHero.class,"0");
		private final String TXT_CATALOGUS	= Messages.get(WndHero.class,"-");
		private final String TXT_JOURNAL		= Messages.get(WndHero.class,"10");
		
		private static final int GAP = 5;
		
		private float pos;
		
		public StatsTab() {
			
			Hero hero = Dungeon.hero; 

			RenderedText title = PixelScene.renderText(
				Utils.format( TXT_TITLE, hero.lvl, hero.className() ).toUpperCase( Locale.ENGLISH ), 9 );
			title.hardlight( TITLE_COLOR );
			add( title );
			
			RedButton btnCatalogus = new RedButton( TXT_CATALOGUS ) {
				@Override
				protected void onClick() {
					hide();
					GameScene.show( new WndCatalogus() );
				}
			};
			btnCatalogus.setRect( 0, title.y + title.height(), btnCatalogus.reqWidth() + 2, btnCatalogus.reqHeight() + 2 );
			add( btnCatalogus );
			
			RedButton btnJournal = new RedButton( TXT_JOURNAL ) {
				@Override
				protected void onClick() {
					hide();
					GameScene.show( new WndJournal() );
				}
			};
			btnJournal.setRect( 
				btnCatalogus.right() + 1, btnCatalogus.top(), 
				btnJournal.reqWidth() + 2, btnJournal.reqHeight() + 2 );
			add( btnJournal );
			
			pos = btnCatalogus.bottom() + GAP;
			
			statSlot( TXT_STR, hero.STR() );
			statSlot( TXT_HEALTH, hero.HP + "/" + hero.HT );
            statSlot( TXT_MANA, hero.MP + "/" + hero.MMP );
			statSlot( TXT_EXP, hero.exp + "/" + hero.maxExp() );

			pos += GAP;

            statSlot( TXT_Difficulty, Dungeon.currentDifficulty.title() );

			statSlot( TXT_GOLD, Statistics.goldCollected );
			statSlot( TXT_DEPTH, Statistics.deepestFloor );
			
			pos += GAP;
		}
		
		private void statSlot( String label, String value ) {
			
			RenderedText txt = PixelScene.renderText( label, 8 );
			txt.y = pos;
			add( txt );
			
			txt = PixelScene.renderText( value, 8 );
			txt.x = PixelScene.align( WIDTH * 0.65f );
			txt.y = pos;
			PixelScene.align(txt);
			add( txt );
			
			pos += GAP + txt.baseLine();
		}
		
		private void statSlot( String label, int value ) {
			statSlot( label, Integer.toString( value ) );
		}
		
		public float height() {
			return pos;
		}
	}
	
	private class BuffsTab extends Group {
		
		private static final int GAP = 2;
		
		private float pos;
		
		public BuffsTab() {
			for (Buff buff : Dungeon.hero.buffs()) {
				buffSlot( buff );
			}
		}
		
		private void buffSlot( Buff buff ) {
			
			int index = buff.icon();
			
			if (index != BuffIndicator.NONE) {
				
				Image icon = new Image( icons );
				icon.frame( film.get( index ) );
				icon.y = pos;
				add( icon );
				
				RenderedText txt = PixelScene.renderText( buff.toString(), 8 );
				txt.x = icon.width + GAP;
				txt.y = pos + (int)(icon.height - txt.baseLine()) / 2;
				add( txt );
				
				pos += GAP + icon.height;
			}
		}
		
		public float height() {
			return pos;
		}
	}
}
