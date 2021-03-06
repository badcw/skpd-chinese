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
package com.skpd.pixeldungeonskills;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.skpd.noosa.Game;
import com.skpd.noosa.RenderedText;
import com.skpd.noosa.audio.Music;
import com.skpd.noosa.audio.Sample;
import com.skpd.pixeldungeonskills.items.rings.C;
import com.skpd.pixeldungeonskills.items.rings.H;
import com.skpd.pixeldungeonskills.items.rings.I;
import com.skpd.pixeldungeonskills.items.scrolls.D;
import com.skpd.pixeldungeonskills.items.scrolls.K;
import com.skpd.pixeldungeonskills.items.scrolls.S;
import com.skpd.pixeldungeonskills.messages.Languages;
import com.skpd.pixeldungeonskills.scenes.GameScene;
import com.skpd.pixeldungeonskills.scenes.PixelScene;
import com.skpd.pixeldungeonskills.scenes.TitleScene;

import javax.microedition.khronos.opengles.GL10;

public class PixelDungeon extends Game {
	
	public PixelDungeon() {
		super( TitleScene.class );
		
		com.skpd.utils.Bundle.addAlias(
			S.class,
			"com.watabou.pixeldungeon.items.scrolls.ScrollOfEnhancement" );
		com.skpd.utils.Bundle.addAlias(
			com.skpd.pixeldungeonskills.actors.blobs.WaterOfHealth.class,
			"com.watabou.pixeldungeon.actors.blobs.Light" );
		com.skpd.utils.Bundle.addAlias(
			H.class,
			"com.watabou.pixeldungeon.items.rings.RingOfRejuvenation" );
		com.skpd.utils.Bundle.addAlias(
			com.skpd.pixeldungeonskills.items.wands.WandOfReach.class,
			"com.watabou.pixeldungeon.items.wands.WandOfTelekenesis" );
		com.skpd.utils.Bundle.addAlias(
			com.skpd.pixeldungeonskills.actors.blobs.Foliage.class,
			"com.watabou.pixeldungeon.actors.blobs.Blooming" );
		com.skpd.utils.Bundle.addAlias(
			com.skpd.pixeldungeonskills.actors.buffs.Shadows.class,
			"com.watabou.pixeldungeon.actors.buffs.Rejuvenation" );
		com.skpd.utils.Bundle.addAlias(
			K.class,
			"com.watabou.pixeldungeon.items.scrolls.ScrollOfNuclearBlast" );
		com.skpd.utils.Bundle.addAlias(
			com.skpd.pixeldungeonskills.actors.hero.Hero.class,
			"com.watabou.pixeldungeon.actors.Hero" );
		com.skpd.utils.Bundle.addAlias(
			com.skpd.pixeldungeonskills.actors.mobs.npcs.Shopkeeper.class,
			"com.watabou.pixeldungeon.actors.mobs.Shopkeeper" );
		// 1.6.1
		com.skpd.utils.Bundle.addAlias(
			com.skpd.pixeldungeonskills.items.quest.DriedRose.class,
			"com.watabou.pixeldungeon.items.DriedRose" );
		com.skpd.utils.Bundle.addAlias(
			com.skpd.pixeldungeonskills.actors.mobs.npcs.MirrorImage.class,
			"com.watabou.pixeldungeon.items.scrolls.J$MirrorImage" );
		// 1.6.4
		com.skpd.utils.Bundle.addAlias(
			C.class,
			"com.watabou.pixeldungeon.items.rings.RingOfCleansing" );
		com.skpd.utils.Bundle.addAlias(
			C.class,
			"com.watabou.pixeldungeon.items.rings.RingOfResistance" );
		com.skpd.utils.Bundle.addAlias(
			com.skpd.pixeldungeonskills.items.weapon.missiles.Boomerang.class,
			"com.watabou.pixeldungeon.items.weapon.missiles.RangersBoomerang" );
		com.skpd.utils.Bundle.addAlias(
			I.class,
			"com.watabou.pixeldungeon.items.rings.RingOfEnergy" );
		// 1.7.2
		com.skpd.utils.Bundle.addAlias(
			com.skpd.pixeldungeonskills.plants.Dreamweed.class,
			"com.watabou.pixeldungeon.plants.Blindweed" );
		com.skpd.utils.Bundle.addAlias(
			com.skpd.pixeldungeonskills.plants.Dreamweed.Seed.class,
			"com.watabou.pixeldungeon.plants.Blindweed$Seed" );
		// 1.7.4
		com.skpd.utils.Bundle.addAlias(
			com.skpd.pixeldungeonskills.items.weapon.enchantments.Shock.class,
			"com.watabou.pixeldungeon.items.weapon.enchantments.Piercing" );
		com.skpd.utils.Bundle.addAlias(
			com.skpd.pixeldungeonskills.items.weapon.enchantments.Shock.class,
			"com.watabou.pixeldungeon.items.weapon.enchantments.Swing" );
		com.skpd.utils.Bundle.addAlias(
			D.class,
			"com.watabou.pixeldungeon.items.scrolls.ScrollOfWeaponUpgrade" );
		// 1.7.5
		com.skpd.utils.Bundle.addAlias(
			D.class,
			"com.watabou.pixeldungeon.items.Stylus" );
		// 1.8.0
		com.skpd.utils.Bundle.addAlias(
			com.skpd.pixeldungeonskills.actors.mobs.FetidRat.class,
			"com.watabou.pixeldungeon.actors.mobs.npcs.Ghost$FetidRat" );
		com.skpd.utils.Bundle.addAlias(
			com.skpd.pixeldungeonskills.plants.Rotberry.class,
			"com.watabou.pixeldungeon.actors.mobs.npcs.Wandmaker$Rotberry" );
		com.skpd.utils.Bundle.addAlias(
			com.skpd.pixeldungeonskills.plants.Rotberry.Seed.class,
			"com.watabou.pixeldungeon.actors.mobs.npcs.Wandmaker$Rotberry$Seed" );
		// 1.9.0
		com.skpd.utils.Bundle.addAlias(
			com.skpd.pixeldungeonskills.items.wands.WandOfReach.class,
			"com.watabou.pixeldungeon.items.wands.WandOfTelekinesis" );
	}
	
	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		
		updateImmersiveMode();
		
		DisplayMetrics metrics = new DisplayMetrics();
		instance.getWindowManager().getDefaultDisplay().getMetrics( metrics );
		boolean landscape = metrics.widthPixels > metrics.heightPixels;
		
		if (Preferences.INSTANCE.getBoolean( Preferences.KEY_LANDSCAPE, false ) != landscape) {
			landscape( !landscape );
		}
		
		Music.INSTANCE.enable( music() );
		Sample.INSTANCE.enable( soundFx() );
		
		Sample.INSTANCE.load( 
			Assets.SND_CLICK, 
			Assets.SND_BADGE, 
			Assets.SND_GOLD,
			
			Assets.SND_DESCEND,
			Assets.SND_STEP,
			Assets.SND_WATER,
			Assets.SND_OPEN,
			Assets.SND_UNLOCK,
			Assets.SND_ITEM,
			Assets.SND_DEWDROP, 
			Assets.SND_HIT, 
			Assets.SND_MISS,
			Assets.SND_EAT,
			Assets.SND_READ,
			Assets.SND_LULLABY,
			Assets.SND_DRINK,
			Assets.SND_SHATTER,
			Assets.SND_ZAP,
			Assets.SND_LIGHTNING,
			Assets.SND_LEVELUP,
			Assets.SND_DEATH,
			Assets.SND_CHALLENGE,
			Assets.SND_CURSED,
			Assets.SND_EVOKE,
			Assets.SND_TRAP,
			Assets.SND_TOMB,
			Assets.SND_ALERT,
			Assets.SND_MELD,
			Assets.SND_BOSS,
			Assets.SND_BLAST,
			Assets.SND_PLANT,
			Assets.SND_RAY,
			Assets.SND_BEACON,
			Assets.SND_TELEPORT,
			Assets.SND_CHARMS,
			Assets.SND_MASTERY,
			Assets.SND_PUFF,
			Assets.SND_ROCKS,
			Assets.SND_BURNING,
			Assets.SND_FALLING,
			Assets.SND_GHOST,
			Assets.SND_SECRET,
			Assets.SND_BONES,
			Assets.SND_BEE,
			Assets.SND_DEGRADE,
			Assets.SND_MIMIC );

		if (classicFont()) {
			RenderedText.setFont("pixelfont.ttf");
		} else {
			RenderedText.setFont( null );
		}
	}

	public static void language(Languages lang) {
		Preferences.INSTANCE.put( Preferences.KEY_LANG, lang.code());
	}

	public static Languages language() {
		return Languages.matchCode();
	}

	public static void classicFont(boolean classic){
		Preferences.INSTANCE.put(Preferences.KEY_CLASSICFONT, classic);
		if (classic) {
			RenderedText.setFont("pixelfont.ttf");
		} else {
			RenderedText.setFont( null );
		}
	}

	public static boolean classicFont(){
		return Preferences.INSTANCE.getBoolean(Preferences.KEY_CLASSICFONT,
				(language() != Languages.CHINESE ));
	}

	@Override
	public void onWindowFocusChanged( boolean hasFocus ) {
		
		super.onWindowFocusChanged( hasFocus );
		
		if (hasFocus) {
			updateImmersiveMode();
		}
	}

	public static void switchNoFade(Class<? extends PixelScene> c) {
		PixelScene.noFade = true;
		switchScene(c);
	}
	
	public static void switchNoFade(Class<? extends PixelScene> c, SceneChangeCallback callback) {
		PixelScene.noFade = true;
		switchScene( c,callback );
	}
	
	/*
	 * ---> Prefernces
	 */

	public static void landscape( boolean value ) {
		if (android.os.Build.VERSION.SDK_INT >= 9) {
			Game.instance.setRequestedOrientation(value ?
					ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE :
					ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
		} else {
			Game.instance.setRequestedOrientation(value ?
					ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE :
					ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
		Preferences.INSTANCE.put( Preferences.KEY_LANDSCAPE, value );
		((PixelDungeon)instance).updateDisplaySize();
	}
	
	public static boolean landscape() {
		return width > height;
	}

	public static void scale(int value) {
		Preferences.INSTANCE.put(Preferences.KEY_SCALE, value);
		switchScene(TitleScene.class);
	}
	// *** IMMERSIVE MODE ****
	
	private static boolean immersiveModeChanged = false;
	
	@SuppressLint("NewApi")
	public static void immerse( boolean value ) {
		Preferences.INSTANCE.put( Preferences.KEY_IMMERSIVE, value );
		
		instance.runOnUiThread( new Runnable() {
			@Override
			public void run() {
				updateImmersiveMode();
				immersiveModeChanged = true;
			}
		} );
	}

	@Override
	public void onSurfaceChanged( GL10 gl, int width, int height ) {

		super.onSurfaceChanged( gl, width, height );

		updateDisplaySize();

		if (immersiveModeChanged) {
			requestedReset = true;
			immersiveModeChanged = false;
		}
	}


	@SuppressLint("NewApi")
	public static void updateImmersiveMode() {
		if (android.os.Build.VERSION.SDK_INT >= 19) {
			try {
				// Sometime NullPointerException happens here
				instance.getWindow().getDecorView().setSystemUiVisibility( 
					immersed() ?
					View.SYSTEM_UI_FLAG_LAYOUT_STABLE | 
					View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | 
					View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | 
					View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | 
					View.SYSTEM_UI_FLAG_FULLSCREEN | 
					View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY 
					:
					0 );
			} catch (Exception e) {
				reportException( e );
			}
		}
	}

	public static boolean immersed() {
		return Preferences.INSTANCE.getBoolean( Preferences.KEY_IMMERSIVE, false );
	}
	
	// *****************************

	public static int scale() {
		return Preferences.INSTANCE.getInt(Preferences.KEY_SCALE, 0);
	}

	public static void zoom( int value ) {
		Preferences.INSTANCE.put( Preferences.KEY_ZOOM, value );
	}
	
	public static int zoom() {
		return Preferences.INSTANCE.getInt( Preferences.KEY_ZOOM, 0 );
	}
	
	public static void music( boolean value ) {
		Music.INSTANCE.enable( value );
		Preferences.INSTANCE.put( Preferences.KEY_MUSIC, value );
	}
	
	public static boolean music() {
		return Preferences.INSTANCE.getBoolean( Preferences.KEY_MUSIC, true );
	}
	
	public static void soundFx( boolean value ) {
		Sample.INSTANCE.enable( value );
		Preferences.INSTANCE.put( Preferences.KEY_SOUND_FX, value );
	}
	
	public static boolean soundFx() {
		return Preferences.INSTANCE.getBoolean( Preferences.KEY_SOUND_FX, true );
	}

    public static void itemDeg( boolean value ) {
        Preferences.INSTANCE.put( Preferences.KEY_DEGRADATION, value );
    }

    public static boolean itemDeg() {
        return Preferences.INSTANCE.getBoolean( Preferences.KEY_DEGRADATION, true );
    }
	
	public static void brightness( boolean value ) {
		Preferences.INSTANCE.put( Preferences.KEY_BRIGHTNESS, value );
		if (scene() instanceof GameScene) {
			((GameScene)scene()).brightness( value );
		}
	}
	
	public static boolean brightness() {
		return Preferences.INSTANCE.getBoolean( Preferences.KEY_BRIGHTNESS, false );
	}

	public static void donated( int value ) {
		Preferences.INSTANCE.put( Preferences.KEY_DONATED, value );
	}
	
	public static int donated() {
		return Preferences.INSTANCE.getInt( Preferences.KEY_DONATED, 0 );
	}

    public static void maidenUnlocked( Boolean value ) {
        Preferences.INSTANCE.put( Preferences.KEY_ARCHER_MAIDEN, value );
    }

    public static boolean maidenUnlocked() {
        return Preferences.INSTANCE.getBoolean( Preferences.KEY_ARCHER_MAIDEN, false );
    }

    public static void disableChampionsUnlocked( Boolean value ) {
        Preferences.INSTANCE.put( Preferences.KEY_DISABLE_CHAMPIONS, value );
    }

    public static boolean disableChampionsUnlocked() {
        return Preferences.INSTANCE.getBoolean( Preferences.KEY_DISABLE_CHAMPIONS, false );
    }
	
	public static void lastClass( int value ) {
		Preferences.INSTANCE.put( Preferences.KEY_LAST_CLASS, value );
	}
	
	public static int lastClass() {
		return Preferences.INSTANCE.getInt( Preferences.KEY_LAST_CLASS, 0 );
	}
	
	public static void challenges( int value ) {
		Preferences.INSTANCE.put( Preferences.KEY_CHALLENGES, value );
	}

	public static int challenges() {
		return Preferences.INSTANCE.getInt( Preferences.KEY_CHALLENGES, 0, 0, Challenges.MAX_VALUE );
	}
	
	public static void intro( boolean value ) {
		Preferences.INSTANCE.put( Preferences.KEY_INTRO, value );
	}
	
	public static boolean intro() {
		return Preferences.INSTANCE.getBoolean( Preferences.KEY_INTRO, true );
	}
	
	/*
	 * <--- Preferences
	 */
	private void updateDisplaySize(){
		DisplayMetrics m = new DisplayMetrics();
		if (immersed() && Build.VERSION.SDK_INT >= 19)
			getWindowManager().getDefaultDisplay().getRealMetrics( m );
		else
			getWindowManager().getDefaultDisplay().getMetrics( m );
		dispHeight = m.heightPixels;
		dispWidth = m.widthPixels;

		runOnUiThread(new Runnable() {
				@Override
				public void run() {
					view.getHolder().setSizeFromLayout();
				}
			});

	}

	public static void reportException( Throwable tr ) {
		Log.e( "PD", Log.getStackTraceString( tr ) ); 
	}
}