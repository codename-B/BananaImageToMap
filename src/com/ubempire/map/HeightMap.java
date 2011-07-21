package com.ubempire.map;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Class representing a height map loaded from an image.
 */
public class HeightMap {

    private byte[] pixels;
    private int width;
    private int height;
    public HeightMap(String file) {
        BufferedImage image = null;
        new File("plugins/BananaImageToMap/byte.bmp");

		try {
			image = ImageIO.read(new File(file));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        readPixels(image);
        image=null;
     }

    

    private void readPixels(BufferedImage image) {
        PixelGrabber grabber;
        width = image.getWidth();
        height = image.getHeight();
        int[] temp = new int[width * height];

        try {
            grabber = new PixelGrabber(image, 0, 0, image.getWidth(), image.getHeight(), temp, 0, image.getWidth());
            grabber.grabPixels(0);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        pixels = new byte[temp.length];
        for (int i = 0; i < pixels.length; i++) {
            Color color = new Color(temp[i]);
            pixels[i] = (byte) ((color.getRed() + color.getGreen() + color.getBlue()) / 12);
        }
        temp=null;
    }

    public int getSize() {
        return pixels.length;
    }

    public byte get(int x, int z) {
        if (x + z * width >= pixels.length) return pixels[pixels.length - 1];
        if (x + z * width < 0) return pixels[0];
        return pixels[x + z * width];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getXBound() {
        return width / 32;
    }

    public int getZBound() {
        return height / 32;
    }

}
