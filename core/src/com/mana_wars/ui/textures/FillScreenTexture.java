package com.mana_wars.ui.textures;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

import static com.mana_wars.ui.UIElementsSize.SCREEN_HEIGHT;
import static com.mana_wars.ui.UIElementsSize.SCREEN_WIDTH;

public class FillScreenTexture extends AdaptiveTexture {

    public FillScreenTexture(String fileName, ImageFormat format) {
        super(fileName, format);
    }

    @Override
    protected Texture adapt(FileHandle file) {
        Pixmap pixmapFrom = new Pixmap(file);
        Pixmap pixmapTo = new Pixmap(SCREEN_WIDTH(), SCREEN_HEIGHT(), pixmapFrom.getFormat());
        pixmapAdaptiveCopy(pixmapFrom, pixmapTo);
        Texture result = new Texture(pixmapTo);
        pixmapFrom.dispose();
        pixmapTo.dispose();
        return result;
    }

    private void pixmapAdaptiveCopy(Pixmap source, Pixmap target) {
        int sourceWidth = source.getWidth();
        int sourceHeight = source.getHeight();
        int targetWidth = target.getWidth();
        int targetHeight = target.getHeight();

        int scaleWidth = (int)Math.ceil((double)targetWidth / sourceWidth);
        int scaleHeight = (int)Math.ceil((double)targetHeight / sourceHeight);

        int srcx, srcy;
        int srcWidth, srcHeight;

        if (scaleHeight > scaleWidth) {
            srcx = (int)Math.round(((double) scaleHeight * sourceWidth - targetWidth) / (2 * scaleHeight));
            srcy = 0;

            srcWidth = (int)Math.ceil((double)targetWidth / scaleHeight);
            srcHeight = sourceHeight;
        } else {
            srcx = 0;
            srcy = (int)Math.round(((double)scaleWidth * sourceHeight - targetHeight) / (2 * scaleWidth));

            srcWidth = sourceWidth;
            srcHeight = (int)Math.ceil((double)targetHeight / scaleWidth);
        }

        target.drawPixmap(source,
                srcx, srcy, srcWidth, srcHeight,
                0, 0, targetWidth, targetHeight);
    }

}
