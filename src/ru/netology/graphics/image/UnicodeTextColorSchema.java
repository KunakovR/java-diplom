package ru.netology.graphics.image;

public class UnicodeTextColorSchema implements TextColorSchema{

    @Override
    public char convert(int color) {
        if (color >= 0 && color < 10) {
            return '#';
        }
        else if (color >= 10 && color < 50) {
            return '$';
        }
        else if (color >= 50 && color < 90) {
            return '@';
        }
        else if (color >= 90 && color < 120) {
            return '%';
        }
        else if (color >= 120 && color < 160) {
            return '*';
        }
        else if (color >= 160 && color < 190) {
            return '+';
        }
        else if (color >= 190 && color < 230) {
            return '-';
        }
        else if (color >= 230 && color < 255) {
            return '.';
        }
        return 0;
    }
}
