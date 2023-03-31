package com.company.async;

import com.company.Utils;

import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class AsyncWork {

    public static void main(String[] args) throws IOException {
        fix("Resources/gifs/Dragon Kick.gif");
        fix("Resources/gifs/Twin Snakes.gif");
        fix("Resources/gifs/True Strike.gif");
        fix("Resources/gifs/Demolish.gif");
        fix("Resources/gifs/Snap Punch.gif");
        fix("Resources/gifs/Bootshine.gif");
        fix("Resources/gifs/Brotherhood.gif");
        fix("Resources/gifs/Elixir Field.gif");
        fix("Resources/gifs/Forbidden Chakra.gif");
        fix("Resources/gifs/Perfect Balance.gif");
        fix("Resources/gifs/Phantom Rush.gif");
        fix("Resources/gifs/Riddle of Fire.gif");
        fix("Resources/gifs/Rising Phoenix.gif");
    }

    public static void fix(String filename) throws IOException {
        Utils.ImageFrame[] frames = Utils.readGif(new FileInputStream(filename));
        for( int i = 0; i < frames.length; i++ ){
            //code to resize the image
            BufferedImage image = frames[ i ].getImage().getSubimage(820,300, 1080-820, 670-300);//.getScaledInstance(192, 108, Image.SCALE_DEFAULT);
            //BufferedImage buffered = new BufferedImage(192, 108, BufferedImage.TYPE_INT_ARGB);
            //buffered.getGraphics().drawImage(image, 0, 0 , null);
            frames[ i ].setImage( image );
        }
        ImageOutputStream output =
                new FileImageOutputStream( new File( filename.substring(0, filename.length() - 4) + "_s.gif" ) );

        GifSequenceWriter writer =
                new GifSequenceWriter( output, frames[0].getImage().getType(), frames[0].getDelay()*10, true );

        writer.writeToSequence( frames[0].getImage() );
        for ( int i = 1; i < frames.length; i++ ) {
            BufferedImage nextImage = frames[i].getImage();
            writer.writeToSequence( nextImage );
        }

        writer.close();
        output.close();
    }
}
