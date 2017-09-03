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

import android.opengl.GLES20;

import com.skpd.noosa.BitmapText;
import com.skpd.noosa.Camera;
import com.skpd.noosa.Game;
import com.skpd.noosa.Image;
import com.skpd.noosa.RenderedText;
import com.skpd.noosa.audio.Music;
import com.skpd.noosa.audio.Sample;
import com.skpd.noosa.ui.Button;
import com.skpd.pixeldungeonskills.Assets;
import com.skpd.pixeldungeonskills.PixelDungeon;
import com.skpd.pixeldungeonskills.VersionNewsInfo;
import com.skpd.pixeldungeonskills.effects.BannerSprites;
import com.skpd.pixeldungeonskills.effects.Fireball;
import com.skpd.pixeldungeonskills.messages.Messages;
import com.skpd.pixeldungeonskills.ui.Archs;
import com.skpd.pixeldungeonskills.ui.ExitButton;
import com.skpd.pixeldungeonskills.ui.PrefsButton;
import com.skpd.pixeldungeonskills.windows.LanguageButton;

import javax.microedition.khronos.opengles.GL10;


public class TitleScene extends PixelScene {

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
		
		Image title = BannerSprites.get( BannerSprites.Type.PIXEL_DUNGEON );
		add( title );
		
		float height = title.height + 
			(PixelDungeon.landscape() ? DashboardItem.SIZE : DashboardItem.SIZE * 2);
		
		title.x = (w - title.width()) / 2;
		title.y = (h - height) / 2;
		
		placeTorch( title.x + 18, title.y + 20 );
		placeTorch( title.x + title.width - 18, title.y + 20 );
		
		Image signs = new Image( BannerSprites.get( BannerSprites.Type.PIXEL_DUNGEON_SIGNS ) ) {
			private float time = 0;
			@Override
			public void update() {
				super.update();
				am = (float)Math.sin( -(time += Game.elapsed) );
			}
			@Override
			public void draw() {
				GLES20.glBlendFunc( GL10.GL_SRC_ALPHA, GL10.GL_ONE );
				super.draw();
				GLES20.glBlendFunc( GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA );
			}
		};
		signs.x = title.x;
		signs.y = title.y;
		add( signs );
		
		DashboardItem btnBadges = new DashboardItem( Messages.get(this, "badges"), 3 ) {
			@Override
			protected void onClick() {
				PixelDungeon.switchNoFade( BadgesScene.class );
			}
		};
		add( btnBadges );
		
		DashboardItem btnAbout = new DashboardItem( Messages.get(this, "about"), 1 ) {
			@Override
			protected void onClick() {
				PixelDungeon.switchNoFade( AboutScene.class );
			}
		};
		add( btnAbout );
		
		DashboardItem btnPlay = new DashboardItem( Messages.get(this, "play"), 0 ) {
			@Override
			protected void onClick() {
				PixelDungeon.switchNoFade( StartScene.class );
			}
		};
		add( btnPlay );
		
		DashboardItem btnHighscores = new DashboardItem( Messages.get(this, "rankings"), 2 ) {
			@Override
			protected void onClick() {
				PixelDungeon.switchNoFade( RankingsScene.class );
			}
		};
		add( btnHighscores );
		
		if (PixelDungeon.landscape()) {
			float y = (h + height) / 2 - DashboardItem.SIZE;
			btnHighscores	.setPos( w / 2 - btnHighscores.width(), y );
			btnBadges		.setPos( w / 2, y );
			btnPlay			.setPos( btnHighscores.left() - btnPlay.width(), y );
			btnAbout		.setPos( btnBadges.right(), y );
		} else {
			btnBadges.setPos( w / 2 - btnBadges.width(), (h + height) / 2 - DashboardItem.SIZE );
			btnAbout.setPos( w / 2, (h + height) / 2 - DashboardItem.SIZE );
			btnPlay.setPos( w / 2 - btnPlay.width(), btnAbout.top() - DashboardItem.SIZE );
			btnHighscores.setPos( w / 2, btnPlay.top() );
		}

        BitmapText version = new BitmapText( "ForChinese", font1x );
        version.measure();
        version.hardlight( 0xFFFFFF );
        version.x = w - version.width();
        version.y = h - version.height() - 9;
        add( version );

        BitmapText versionPD = new BitmapText( Game.version , font1x );
        versionPD.measure();
        versionPD.hardlight( 0x666666 );
        versionPD.x = w - versionPD.width();
        versionPD.y = h - versionPD.height();
        add( versionPD );

		LanguageButton btnLang = new LanguageButton();
		btnLang.setPos(16, 1);
		add( btnLang );


		PrefsButton btnPrefs = new PrefsButton();
		btnPrefs.setPos( 0, 0 );
		add( btnPrefs );
		
		ExitButton btnExit = new ExitButton();
		btnExit.setPos( w - btnExit.width(), 0 );
		add( btnExit );

        if(VersionNewsInfo.haveMessage())
        {
            add(VersionNewsInfo.getWelcomeWindow());
        }

		fadeIn();
	}
	
	private void placeTorch( float x, float y ) {
		Fireball fb = new Fireball();
		fb.setPos( x, y );
		add( fb );
	}

	private static class DashboardItem extends Button {

		public static final float SIZE	= 48;

		private static final int IMAGE_SIZE	= 32;

		private Image image;
		private RenderedText label;

		public DashboardItem( String text, int index ) {
			super();

			image.frame( image.texture.uvRect( index * IMAGE_SIZE, 0, (index + 1) * IMAGE_SIZE, IMAGE_SIZE ) );
			this.label.text( text );

			setSize( SIZE, SIZE );
		}

		@Override
		protected void createChildren() {
			super.createChildren();

			image = new Image( Assets.DASHBOARD );
			add( image );

			label = renderText( 9 );
			add( label );
		}

		@Override
		protected void layout() {
			super.layout();

			image.x = x + (width - image.width()) / 2;
			image.y = y;
			align(image);

			label.x = x + (width - label.width()) / 2;
			label.y = image.y + image.height() +2;
			align(label);
		}

		@Override
		protected void onTouchDown() {
			image.brightness( 1.5f );
			Sample.INSTANCE.play( Assets.SND_CLICK, 1, 1, 0.8f );
		}

		@Override
		protected void onTouchUp() {
			image.resetColor();
		}
	}
}
