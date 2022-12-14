package ru.netology.graphics.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.net.URL;

public class ImageToUnicodeConverter implements TextGraphicsConverter{

    double ratio;
    double maxRatio;
    int width;
    int height;
    int MaxWidth;
    int MaxHeight;
    int newWidth;
    int newHeight;


    @Override
    public String convert(String url) throws IOException, BadImageSizeException {

        BufferedImage img = ImageIO.read(new URL(url));

        width = img.getWidth();
        height = img.getHeight();
        setRatio(width, height);

        if (ratio > maxRatio && maxRatio > 0) {
            throw new BadImageSizeException(maxRatio, ratio);
        }

        while(true) {
            if (MaxWidth > 0 && MaxHeight > 0) {
                if (width < MaxWidth && height < MaxHeight) {
                    newWidth = width;
                    newHeight = height;
                } else if (width > MaxWidth && height > MaxHeight) {
                    newWidth = MaxWidth;
                    newHeight = height / (width / MaxWidth);
                } else if (width > MaxWidth && height < MaxHeight) {
                    newWidth = MaxWidth;
                    newHeight = height / (width / MaxWidth);
                } else if (width < MaxWidth && height > MaxHeight) {
                    newHeight = MaxHeight;
                    newWidth = width / (height / MaxHeight);
                }
            } else {
                newWidth = width;
                newHeight = height;
            }
            break;
        }

        Image scaledImage = img.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH);
        BufferedImage bwImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D graphics = bwImg.createGraphics();
        graphics.drawImage(scaledImage, 0, 0, null);
        WritableRaster bwRaster = bwImg.getRaster();
        TextColorSchema schema = new UnicodeTextColorSchema();


        char[][] picture = new char[newHeight][newWidth];
        for (int h = 0; h < newHeight; h++) {
            for (int w = 0; w < newWidth; w++) {
                int color = bwRaster.getPixel(w, h, new int[3])[0];
                char c = schema.convert(color);
                picture[h][w] = c;
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < picture.length; i++) {
            for (int j = 0; j < picture[i].length; j++) {
                sb.append(picture[i][j]);
                sb.append(picture[i][j]);

            }
            sb.append("\n");
        }

        String imgTxt = String.valueOf(sb);

        return imgTxt;
    }

    @Override
    public void setMaxWidth(int width) {
        this.MaxWidth = width;
    }

    @Override
    public void setMaxHeight(int height) {
        this.MaxHeight = height;
    }

    @Override
    public void setMaxRatio(double maxRatio) {
        this.maxRatio = maxRatio;
    }

    @Override
    public void setTextColorSchema(TextColorSchema schema) {

    }

    public double setRatio(int width, int height) {
        if (width/height == 1) {
            double ratio = 1;
            return ratio;
        }
        else if (width/height > 0) {
            double ratio = width/height;
            return ratio;
        }
        else {
            double ratio = height/width;
            return ratio;
        }
    }
}
