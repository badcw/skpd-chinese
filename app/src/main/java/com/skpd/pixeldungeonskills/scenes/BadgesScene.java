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
package com.skpd.pixeldungeonskills.scenes;

import com.skpd.noosa.Camera;
import com.skpd.noosa.Game;
import com.skpd.noosa.Image;
import com.skpd.noosa.RenderedText;
import com.skpd.noosa.audio.Music;
import com.skpd.noosa.audio.Sample;
import com.skpd.noosa.ui.Button;
import com.skpd.pixeldungeonskills.Assets;
import com.skpd.pixeldungeonskills.Badges;
import com.skpd.pixeldungeonskills.PixelDungeon;
import com.skpd.pixeldungeonskills.effects.BadgeBanner;
import com.skpd.pixeldungeonskills.messages.Messages;
import com.skpd.pixeldungeonskills.ui.Archs;
import com.skpd.pixeldungeonskills.ui.ExitButton;
import com.skpd.pixeldungeonskills.ui.Window;
import com.skpd.pixeldungeonskills.windows.WndBadge;
import com.skpd.utils.Callback;
import com.skpd.utils.Random;

import java.util.List;

public class BadgesScene extends PixelScene {
	
	@Override
	public void create() {
		
		super.create();
		
		Music.INSTANCE.play( Assets.THEME, true );
		Music.INSTANCE.volume( 1f );
		
		uiCamera.visible = false;
		
		int w = Camera.main.width;
		int h = Camera.main.height;
		
		Archs archs = new Archs();
		archs.setSize( w, h );
		add( archs );
		
		int pw = (int)Math.min( w, (PixelDungeon.landscape() ? MIN_WIDTH_L : MIN_WIDTH_P) * 3 ) - 16;
		int ph = (int)Math.min( h, (PixelDungeon.landscape() ? MIN_HEIGHT_L : MIN_HEIGHT_P) * 3 ) - 32;
		
		float size = (float)Math.sqrt( pw * ph / 27f );
		int nCols = (int)Math.ceil( pw / size );
		int nRows = (int)Math.ceil( ph / size );
		size = Math.min( pw / nCols, ph / nRows );
		
		float left = (w - size * nCols) / 2;
		float top = (h - size * nRows) / 2;

		RenderedText title = PixelScene.renderText( Messages.get(this, "a"), 9 );
		title.hardlight(Window.TITLE_COLOR);
		title.x = (w - title.width()) / 2 ;
		title.y = (top - title.baseLine()) / 2 ;
		align(title);
		add(title);
		
		Badges.loadGlobal();
		
		List<Badges.Badge> badges = Badges.filtered( true );
		for (int i=0; i < nRows; i++) {
			for (int j=0; j < nCols; j++) {
				int index = i * nCols + j;
				Badges.Badge b = index < badges.size() ? badges.get( index ) : null;
				BadgeButton button = new BadgeButton( b );
				button.setPos(
					left + j * size + (size - button.width()) / 2,
					top + i * size + (size - button.height()) / 2);
				add( button );
			}
		}
		
		ExitButton btnExit = new ExitButton();
		btnExit.setPos( Camera.main.width - btnExit.width(), 0 );
		add( btnExit );
		
		fadeIn();
		
		Badges.loadingListener = new Callback() {
			@Override
			public void call() {
				if (Game.scene() == BadgesScene.this) {
					PixelDungeon.switchNoFade( BadgesScene.class );
				}
			}
		};
	}
	
	@Override
	public void destroy() {
		
		Badges.saveGlobal();
		Badges.loadingListener = null;
		
		super.destroy();
	}
	
	@Override
	protected void onBackPressed() {
		PixelDungeon.switchNoFade( TitleScene.class );
	}
	
	private static class BadgeButton extends Button {

		private Badges.Badge badge;

		private Image icon;

		public BadgeButton( Badges.Badge badge ) {
			super();

			this.badge = badge;
			active = (badge != null);

			icon = active ? BadgeBanner.image( badge.image ) : new Image( Assets.LOCKED );
			add(icon);

			setSize( icon.width(), icon.height() );
		}

		@Override
		protected void layout() {
			super.layout();

			icon.x = align( x + (width - icon.width()) / 2 );
			icon.y = align( y + (height - icon.height()) / 2 );
		}

		@Override
		public void update() {
			super.update();

			if (Random.Float() < Game.elapsed * 0.1) {
				BadgeBanner.highlight( icon, badge.image );
			}
		}

		@Override
		protected void onClick() {
			Sample.INSTANCE.play( Assets.SND_CLICK, 0.7f, 0.7f, 1.2f );
			Game.scene().add( new WndBadge( badge ) );
		}
	}
}
