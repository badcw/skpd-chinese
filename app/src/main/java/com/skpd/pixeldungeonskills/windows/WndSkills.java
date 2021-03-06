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

import android.graphics.RectF;

import com.skpd.noosa.ColorBlock;
import com.skpd.noosa.Image;
import com.skpd.noosa.RenderedText;
import com.skpd.noosa.audio.Sample;
import com.skpd.pixeldungeonskills.Assets;
import com.skpd.pixeldungeonskills.Dungeon;
import com.skpd.pixeldungeonskills.PixelDungeon;
import com.skpd.pixeldungeonskills.items.bags.Bag;
import com.skpd.pixeldungeonskills.items.bags.Keyring;
import com.skpd.pixeldungeonskills.items.bags.ScrollHolder;
import com.skpd.pixeldungeonskills.items.bags.SeedPouch;
import com.skpd.pixeldungeonskills.items.bags.WandHolster;
import com.skpd.pixeldungeonskills.messages.Languages;
import com.skpd.pixeldungeonskills.messages.Messages;
import com.skpd.pixeldungeonskills.scenes.GameScene;
import com.skpd.pixeldungeonskills.scenes.PixelScene;
import com.skpd.pixeldungeonskills.skills.BranchSkill;
import com.skpd.pixeldungeonskills.skills.Skill;
import com.skpd.pixeldungeonskills.ui.Icons;
import com.skpd.pixeldungeonskills.ui.SkillSlot;

public class WndSkills extends WndTabbed {


	protected static final int COLS_P	= 4;
	protected static final int COLS_L	= 6;

	protected static final int SLOT_SIZE	= 28;
	protected static final int SLOT_MARGIN	= 1;

	protected static final int TAB_WIDTH	= 25;

	protected static final int TITLE_HEIGHT	= 12;

	private RenderedText txtTitle;

	private Listener listener;
	private String title;

	private int nCols;
	private int nRows;

	protected int count;
	protected int col;
	protected int row;


    public boolean noDegrade = PixelDungeon.itemDeg();

	public WndSkills(Listener listener, String title) {

		super();

		this.listener = listener;
		this.title = title;
		

		nCols = 4;
		nRows = 3;
		
		int slotsWidth = SLOT_SIZE * nCols + SLOT_MARGIN * (nCols - 1);
		int slotsHeight = SLOT_SIZE * nRows + SLOT_MARGIN * (nRows - 1);

		if (Messages.lang()== Languages.ENGLISH) {
			txtTitle = PixelScene.renderText(title != null ? title : Messages.capitalize("Skills" + (Skill.availableSkill > 0 ? " (" + Skill.availableSkill + " points)" : "")), 9);
		}else {
			txtTitle = PixelScene.renderText(title != null ? title : Messages.capitalize("技能" + (Skill.availableSkill > 0 ? " (剩余" + Skill.availableSkill + " 个技能点)" : "")), 9);
		}
		txtTitle.hardlight( TITLE_COLOR );
		txtTitle.x = (int)(slotsWidth - txtTitle.width()) / 2;
		txtTitle.y = (int)(TITLE_HEIGHT - txtTitle.height()) / 2;
		add( txtTitle );
		
		placeSkills();

		resize( slotsWidth, slotsHeight + TITLE_HEIGHT );
	}
	


	protected void placeSkills(  ) {


        placeSkill( Dungeon.hero.heroSkills.branchPA, true);
        placeSkill( Dungeon.hero.heroSkills.passiveA1, false);
        placeSkill( Dungeon.hero.heroSkills.passiveA2, false);
        placeSkill( Dungeon.hero.heroSkills.passiveA3, false);

        placeSkill( Dungeon.hero.heroSkills.branchPB, true);
        placeSkill( Dungeon.hero.heroSkills.passiveB1, true);
        placeSkill( Dungeon.hero.heroSkills.passiveB2, true);
        placeSkill( Dungeon.hero.heroSkills.passiveB3, true);

        placeSkill( Dungeon.hero.heroSkills.branchA, true);
        placeSkill( Dungeon.hero.heroSkills.active1, true);
        placeSkill( Dungeon.hero.heroSkills.active2, true);
        placeSkill( Dungeon.hero.heroSkills.active3, true);

    }
	
	protected void placeSkill( final Skill skill, boolean showBackground ) {
		
		int x = col * (SLOT_SIZE + SLOT_MARGIN);
		int y = TITLE_HEIGHT + row * (SLOT_SIZE + SLOT_MARGIN);

		add( new SkillButton( skill ).setPos( x, y ) );
		
		if (++col >= nCols) {
			col = 0;
			row++;
		}
		
		count++;
	}
	
	@Override
	public void onMenuPressed() {
		if (listener == null) {
			hide();
		}
	}
	
	@Override
	public void onBackPressed() {
		if (listener != null) {
			listener.onSelect( null );
		}
		super.onBackPressed();
	}
	
	@Override
	protected void onClick( Tab tab ) {
		hide();
		GameScene.show( new WndSkills( listener, title ) );
	}
	
	@Override
	protected int tabHeight() {
		return 20;
	}
	
	private class BagTab extends Tab {
		
		private Image icon;

		private Bag bag;
		
		public BagTab( Bag bag ) {
			super();
			
			this.bag = bag;
			
			icon = icon();
			add( icon );
		}
		
		@Override
		protected void select( boolean value ) {
			super.select( value );
			icon.am = selected ? 1.0f : 0.6f;
		}
		
		@Override
		protected void layout() {
			super.layout();
			
			icon.copy( icon() );
			icon.x = x + (width - icon.width) / 2;
			icon.y = y + (height - icon.height) / 2 - 2 - (selected ? 0 : 1);
			if (!selected && icon.y < y + CUT) {
				RectF frame = icon.frame();
				frame.top += (y + CUT - icon.y) / icon.texture.height;
				icon.frame( frame );
				icon.y = y + CUT;
			}
		}
		
		private Image icon() {
			if (bag instanceof SeedPouch) {
				return Icons.get( Icons.SEED_POUCH );
			} else if (bag instanceof ScrollHolder) {
				return Icons.get( Icons.SCROLL_HOLDER );
			} else if (bag instanceof WandHolster) {
				return Icons.get( Icons.WAND_HOLSTER );
			} else if (bag instanceof Keyring) {
				return Icons.get( Icons.KEYRING );
			} else {
				return Icons.get( Icons.BACKPACK );
			}
		}
	}
	
	private static class Placeholder extends Skill {
		{
			name = null;
		}
		
		public Placeholder( int image ) {
			this.image = image;
		}
	}
	
	private class SkillButton extends SkillSlot {
		
		private static final int NORMAL		= 0xFF4A4D44;
		private static final int EQUIPPED	= 0xFF63665B;

		
		private Skill skill;
		private ColorBlock bg;
		
		private ColorBlock durability[];
		
		public SkillButton(Skill skill) {
			
			super( skill );

			this.skill = skill;


			width = height = SLOT_SIZE;

            durability = new ColorBlock[Skill.MAX_LEVEL];

            if(skill != null && skill.name != null && skill.level > 0  && skill.level <= Skill.MAX_LEVEL) {
                for (int i = 0; i < skill.level; i++) {
                    durability[i] = new ColorBlock(2, 2, 0xFF00EE00);
                    add(durability[i]);
                }
                for (int i = skill.level; i < Skill.MAX_LEVEL; i++) {
                    durability[i] = new ColorBlock(2, 2, 0x4000EE00);
                    add(durability[i]);
                }
            }

            if(skill instanceof BranchSkill)
                bg.visible = false;
		}
		
		@Override
		protected void createChildren() {	
			bg = new ColorBlock( SLOT_SIZE, SLOT_SIZE, NORMAL );
			add( bg );
			
			super.createChildren();
		}
		
		@Override
		protected void layout() {
			bg.x = x;
			bg.y = y;


            if(skill != null && skill.name != null && skill.level > 0  && skill.level <= Skill.MAX_LEVEL) {
                for (int i = 0; i < Skill.MAX_LEVEL; i++) {
                    durability[i].x = x + width - 9 + i * 3;
                    durability[i].y = y + 3;

                }
            }
			
			super.layout();
		}
		

		
		@Override
		protected void onTouchDown() {
			bg.brightness( 1.5f );
			Sample.INSTANCE.play( Assets.SND_CLICK, 0.7f, 0.7f, 1.2f );
		};
		
		protected void onTouchUp() {
			bg.brightness( 1.0f );
		};

		@Override
		protected void onClick() {
            if (listener != null) {

                hide();
                listener.onSelect(skill);

            } else {

                WndSkills.this.add( new WndSkill( WndSkills.this, skill ) );

            }
		}
		
		@Override
		protected boolean onLongClick() {
				return true;
		}
	}

	public interface Listener {
		void onSelect(Skill skill);
	}
}
