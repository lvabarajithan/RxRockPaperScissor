package com.abb.rockpaperscissor.util;

import com.abb.rockpaperscissor.R;
import com.abb.rockpaperscissor.game.model.Move;

/**
 * Created by Abarajithan
 */
public class ImageUtil {

    public static int getImageRes(@Move.Type int type) {
        switch (type) {
            case Move.Type.ROCK:
                return R.drawable.rock;
            case Move.Type.PAPER:
                return R.drawable.paper;
            case Move.Type.SCISSOR:
                return R.drawable.scissor;
            default:
                return 0;
        }
    }

}
