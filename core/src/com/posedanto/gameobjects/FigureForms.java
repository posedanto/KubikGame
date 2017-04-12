package com.posedanto.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Created by Agitatore on 25.03.2017.
 */

public abstract class FigureForms {
    public enum forms{I_FORM, J_FORM, L_FORM, O_FORM, S_FORM, Z_FORM, T_FORM};
    public enum rotation{FLIP_0, FLIP_90, FLIP_180, FLIP_270};

    public static final forms[] formByNumber = {forms.I_FORM, forms.J_FORM, forms.L_FORM,
            forms.O_FORM, forms.S_FORM, forms.Z_FORM, forms.T_FORM};
    public static final rotation[] rotationByNumber = {rotation.FLIP_0, rotation.FLIP_90,
            rotation.FLIP_180, rotation.FLIP_270};

    private static Random random = new Random();
    private static Vector2[] ret = new Vector2[4];
    private static rotation nextRotation;

    public static void initRet() {
        ret[0] = new Vector2();
        ret[1] = new Vector2();
        ret[2] = new Vector2();
        ret[3] = new Vector2();
    }

    public static forms getRandomForm() {
        int formNumber = random.nextInt(formByNumber.length);
        return formByNumber[formNumber];
    }

    public static rotation getRandomRotation() {
        int rotationNumber = random.nextInt(rotationByNumber.length);
        return rotationByNumber[rotationNumber];
    }

    public static rotation getNextRotation(rotation curRotation) {
        switch (curRotation) {
            case FLIP_0:
                nextRotation = rotation.FLIP_90;
                break;
            case FLIP_90:
                nextRotation = rotation.FLIP_180;
                break;
            case FLIP_180:
                nextRotation = rotation.FLIP_270;
                break;
            case FLIP_270:
                nextRotation = rotation.FLIP_0;
                break;
        }
        return nextRotation;
    }

    public static int getFormNumber(forms f) {
        int i = 0;
        while (f != formByNumber[i])
            i++;
        return i;
    }

    public static int getHeight(Figure fig) {
        return getLength(fig.getForm(), getNextRotation(fig.getRotation()), fig.getPosition());
    }

    public static int getHeight(forms f, rotation r, Vector2 p) {
        return getLength(f, getNextRotation(r), p);
    }

    public static int getLength(Figure fig) {
        return getLength(fig.getForm(), fig.getRotation(), fig.getPosition());
    }

    //Длина фигуры по горизонтали
    public static int getLength(forms f, rotation r, Vector2 p) {
        ret = FigureForms.getCoordinates(f, r, p);
        int max = 0;
        for(Vector2 pos : ret)
            if (pos.x > max) max = (int) pos.x;
        return max - (int) p.x + 1;
    }

    public static Vector2[] getCoordinates(forms f, rotation r, Vector2 initialCoord) {
        switch (f) {
            case I_FORM:
                switch (r){
                    case FLIP_0:
                    case FLIP_180:
                        ret[0].set(initialCoord.x, initialCoord.y);
                        ret[1].set(initialCoord.x, initialCoord.y - 1);
                        ret[2].set(initialCoord.x, initialCoord.y - 2);
                        ret[3].set(initialCoord.x, initialCoord.y - 3);
                        break;
                    case FLIP_270:
                    case FLIP_90:
                        ret[0].set(initialCoord.x, initialCoord.y);
                        ret[1].set(initialCoord.x + 1, initialCoord.y);
                        ret[2].set(initialCoord.x + 2, initialCoord.y);
                        ret[3].set(initialCoord.x + 3, initialCoord.y);
                        break;

                }
                break;


            /* Кирпичик  []
             *           [][][]
             */
            case J_FORM:
                switch (r){
                    case FLIP_0:
                        ret[0].set(initialCoord.x + 1 , initialCoord.y);
                        ret[1].set(initialCoord.x + 1, initialCoord.y - 1);
                        ret[2].set(initialCoord.x + 1, initialCoord.y - 2);
                        ret[3].set(initialCoord.x, initialCoord.y - 2);
                        break;
                    case FLIP_180:
                        ret[0].set(initialCoord.x + 1 , initialCoord.y);
                        ret[1].set(initialCoord.x, initialCoord.y);
                        ret[2].set(initialCoord.x, initialCoord.y - 1);
                        ret[3].set(initialCoord.x, initialCoord.y - 2);
                        break;
                    case FLIP_270:
                        ret[0].set(initialCoord.x, initialCoord.y);
                        ret[1].set(initialCoord.x + 1, initialCoord.y);
                        ret[2].set(initialCoord.x + 2, initialCoord.y);
                        ret[3].set(initialCoord.x + 2, initialCoord.y - 1);
                        break;
                    case FLIP_90:
                        ret[0].set(initialCoord.x, initialCoord.y);
                        ret[1].set(initialCoord.x, initialCoord.y - 1);
                        ret[2].set(initialCoord.x + 1, initialCoord.y - 1);
                        ret[3].set(initialCoord.x + 2, initialCoord.y - 1);
                        break;
                }

                break;
            /* Кирпичик     []
             *          [][][]
             */
            case L_FORM:
                switch (r){
                    case FLIP_0:
                        ret[0].set(initialCoord.x, initialCoord.y);
                        ret[1].set(initialCoord.x, initialCoord.y - 1);
                        ret[2].set(initialCoord.x, initialCoord.y - 2);
                        ret[3].set(initialCoord.x + 1, initialCoord.y - 2);
                        break;
                    case FLIP_180:
                        ret[0].set(initialCoord.x, initialCoord.y);
                        ret[1].set(initialCoord.x + 1, initialCoord.y);
                        ret[2].set(initialCoord.x + 1, initialCoord.y - 1);
                        ret[3].set(initialCoord.x + 1, initialCoord.y - 2);
                        break;
                    case FLIP_270:
                        ret[0].set(initialCoord.x, initialCoord.y - 1);
                        ret[1].set(initialCoord.x + 1, initialCoord.y - 1);
                        ret[2].set(initialCoord.x + 2, initialCoord.y - 1);
                        ret[3].set(initialCoord.x + 2, initialCoord.y);
                        break;
                    case FLIP_90:
                        ret[0].set(initialCoord.x, initialCoord.y - 1);
                        ret[1].set(initialCoord.x, initialCoord.y);
                        ret[2].set(initialCoord.x + 1, initialCoord.y);
                        ret[3].set(initialCoord.x + 2, initialCoord.y);
                        break;
                }

                break;

            /* Кирпичик [][]
             *          [][]
             */
            case O_FORM:
                ret[0].set(initialCoord.x, initialCoord.y);
                ret[1].set(initialCoord.x, initialCoord.y - 1);
                ret[2].set(initialCoord.x + 1, initialCoord.y - 1);
                ret[3].set(initialCoord.x + 1, initialCoord.y);

                break;

            /* Кирпичик   [][]
             *          [][]
             */
            case S_FORM:
                switch (r){
                    case FLIP_0:
                    case FLIP_180:
                        ret[0].set(initialCoord.x , initialCoord.y - 1);
                        ret[1].set(initialCoord.x + 1 , initialCoord.y - 1);
                        ret[2].set(initialCoord.x + 1, initialCoord.y);
                        ret[3].set(initialCoord.x + 2, initialCoord.y);
                        break;
                    case FLIP_270:
                    case FLIP_90:
                        ret[0].set(initialCoord.x, initialCoord.y);
                        ret[1].set(initialCoord.x, initialCoord.y - 1);
                        ret[2].set(initialCoord.x + 1, initialCoord.y - 1);
                        ret[3].set(initialCoord.x + 1, initialCoord.y - 2);
                        break;
                }

                break;

            /* Кирпичик [][]
             *            [][]
             */
            case Z_FORM:
                switch (r){
                    case FLIP_0:
                    case FLIP_180:
                        ret[0].set(initialCoord.x, initialCoord.y);
                        ret[1].set(initialCoord.x + 1 , initialCoord.y);
                        ret[2].set(initialCoord.x + 1, initialCoord.y - 1);
                        ret[3].set(initialCoord.x + 2, initialCoord.y - 1);
                        break;
                    case FLIP_270:
                    case FLIP_90:
                        ret[0].set(initialCoord.x + 1, initialCoord.y);
                        ret[1].set(initialCoord.x + 1, initialCoord.y - 1);
                        ret[2].set(initialCoord.x, initialCoord.y - 1);
                        ret[3].set(initialCoord.x, initialCoord.y - 2);
                        break;
                }

                break;

            /* Кирпичик [][][]
             *            []
             */
            case T_FORM:
                switch (r){
                    case FLIP_0:
                        ret[0].set(initialCoord.x, initialCoord.y);
                        ret[1].set(initialCoord.x + 1, initialCoord.y);
                        ret[2].set(initialCoord.x + 1, initialCoord.y - 1);
                        ret[3].set(initialCoord.x + 2, initialCoord.y);
                        break;
                    case FLIP_180:
                        ret[0].set(initialCoord.x, initialCoord.y - 1);
                        ret[1].set(initialCoord.x + 1, initialCoord.y - 1);
                        ret[2].set(initialCoord.x + 1, initialCoord.y);
                        ret[3].set(initialCoord.x + 2, initialCoord.y - 1);
                        break;
                    case FLIP_270:
                        ret[0].set(initialCoord.x, initialCoord.y);;
                        ret[1].set(initialCoord.x, initialCoord.y - 1);
                        ret[2].set(initialCoord.x + 1, initialCoord.y - 1);
                        ret[3].set(initialCoord.x, initialCoord.y - 2);
                        break;
                    case FLIP_90:
                        ret[0].set(initialCoord.x + 1, initialCoord.y);
                        ret[1].set(initialCoord.x + 1, initialCoord.y - 1);
                        ret[2].set(initialCoord.x, initialCoord.y - 1);
                        ret[3].set(initialCoord.x + 1, initialCoord.y - 2);
                        break;
                }

                break;

        }
        return ret;
    }
}
