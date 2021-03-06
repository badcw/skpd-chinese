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

import android.content.Intent;
import android.net.Uri;

import com.skpd.input.Touchscreen.Touch;
import com.skpd.noosa.Camera;
import com.skpd.noosa.Game;
import com.skpd.noosa.Image;
import com.skpd.noosa.RenderedText;
import com.skpd.noosa.TouchArea;
import com.skpd.pixeldungeonskills.PixelDungeon;
import com.skpd.pixeldungeonskills.effects.Flare;
import com.skpd.pixeldungeonskills.ui.Archs;
import com.skpd.pixeldungeonskills.ui.ExitButton;
import com.skpd.pixeldungeonskills.ui.Icons;
import com.skpd.pixeldungeonskills.ui.RenderedTextMultiline;
import com.skpd.pixeldungeonskills.ui.Window;

public class AboutScene extends PixelScene {

    private static final String TTL_SHPX = "技巧地牢汉化版";

    private static final String TXT_SHPX =
            "制作者：雷霆";

    private static final String LNK_SHPX = "github源码地址";

    private static final String TTL_WATA = "Pixel Dungeon";

    private static final String TXT_WATA =
            "Code & Graphics: Watabou\n" +
                    "Music: Cube_Code";

    private static final String LNK_WATA = "pixeldungeon.watabou.ru";

    @Override
    public void create() {
        super.create();

        final float colWidth = Camera.main.width / (PixelDungeon.landscape() ? 2 : 1);
        final float colTop = (Camera.main.height / 2) - (PixelDungeon.landscape() ? 30 : 90);
        final float wataOffset = PixelDungeon.landscape() ? colWidth : 0;

        Image shpx = Icons.WARRIOR.get();
        shpx.x = (colWidth - shpx.width()) / 2;
        shpx.y = colTop;
        align(shpx);
        add( shpx );

        new Flare( 7, 64 ).color( 0x225511, true ).show( shpx, 0 ).angularSpeed = +20;

        RenderedText shpxtitle = renderText( TTL_SHPX, 8 );
        shpxtitle.hardlight( Window.SHPX_COLOR );
        add( shpxtitle );

        shpxtitle.x = (colWidth - shpxtitle.width()) / 2;
        shpxtitle.y = shpx.y + shpx.height + 5;
        align(shpxtitle);

        RenderedTextMultiline shpxtext = renderMultiline( TXT_SHPX, 8 );
        shpxtext.maxWidth((int)Math.min(colWidth, 120));
        add( shpxtext );

        shpxtext.setPos((colWidth - shpxtext.width()) / 2, shpxtitle.y + shpxtitle.height() + 12);
        align(shpxtext);

        RenderedTextMultiline shpxlink = renderMultiline( LNK_SHPX, 8 );
        shpxlink.maxWidth(shpxtext.maxWidth());
        shpxlink.hardlight( Window.SHPX_COLOR );
        add( shpxlink );

        shpxlink.setPos((colWidth - shpxlink.width()) / 2, shpxtext.bottom() + 6);
        align(shpxlink);

        TouchArea shpxhotArea = new TouchArea( shpxlink.left(), shpxlink.top(), shpxlink.width(), shpxlink.height() ) {
            @Override
            protected void onClick( Touch touch ) {
                Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( "https://github.com/badcw/skpd-chinese" ) );
                Game.instance.startActivity( intent );
            }
        };
        add( shpxhotArea );

        Image wata = Icons.WATA.get();
        wata.x = wataOffset + (colWidth - wata.width()) / 2;
        wata.y = PixelDungeon.landscape() ?
                colTop:
                shpxlink.top() + wata.height + 20;
        align(wata);
        add( wata );

        new Flare( 7, 64 ).color( 0x112233, true ).show( wata, 0 ).angularSpeed = +20;

        RenderedText wataTitle = renderText( TTL_WATA, 8 );
        wataTitle.hardlight(Window.TITLE_COLOR);
        add( wataTitle );

        wataTitle.x = wataOffset + (colWidth - wataTitle.width()) / 2;
        wataTitle.y = wata.y + wata.height + 11;
        align(wataTitle);

        RenderedTextMultiline wataText = renderMultiline( TXT_WATA, 8 );
        wataText.maxWidth((int)Math.min(colWidth, 120));
        add( wataText );

        wataText.setPos(wataOffset + (colWidth - wataText.width()) / 2, wataTitle.y + wataTitle.height() + 12);
        align(wataText);

        RenderedTextMultiline wataLink = renderMultiline( LNK_WATA, 8 );
        wataLink.maxWidth((int)Math.min(colWidth, 120));
        wataLink.hardlight(Window.TITLE_COLOR);
        add(wataLink);

        wataLink.setPos(wataOffset + (colWidth - wataLink.width()) / 2 , wataText.bottom() + 6);
        align(wataLink);

        TouchArea hotArea = new TouchArea( wataLink.left(), wataLink.top(), wataLink.width(), wataLink.height() ) {
            @Override
            protected void onClick( Touch touch ) {
                Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( "http://" + LNK_WATA ) );
                Game.instance.startActivity( intent );
            }
        };
        add( hotArea );


        Archs archs = new Archs();
        archs.setSize( Camera.main.width, Camera.main.height );
        addToBack( archs );

        ExitButton btnExit = new ExitButton();
        btnExit.setPos( Camera.main.width - btnExit.width(), 0 );
        add( btnExit );

        fadeIn();
    }

    @Override
    protected void onBackPressed() {
        PixelDungeon.switchNoFade(TitleScene.class);
    }
}
