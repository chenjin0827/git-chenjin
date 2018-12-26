
package com.chenjin.common.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

public class VerifyCodeUtil {
    public static final int TYPE_NUM_ONLY = 0;
    public static final int TYPE_LETTER_ONLY = 1;
    public static final int TYPE_ALL_MIXED = 2;
    public static final int TYPE_NUM_UPPER = 3;
    public static final int TYPE_NUM_LOWER = 4;
    public static final int TYPE_UPPER_ONLY = 5;
    public static final int TYPE_LOWER_ONLY = 6;

    private VerifyCodeUtil() {
    }

    private static Color generateRandomColor() {
        Random random = new Random();
        return new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }

    public static BufferedImage generateImageCode(int type, int length, String excludeString, int width, int height, int interLine, boolean randomLocation, Color backColor, Color foreColor, Color lineColor) {
        String textCode = generateTextCode(type, length, excludeString);
        return generateImageCode(textCode, width, height, interLine, randomLocation, backColor, foreColor, lineColor);
    }

    public static String generateTextCode(int type, int length, String excludeString) {
        if (length <= 0) {
            return "";
        } else {
            StringBuffer verifyCode = new StringBuffer();
            int i = 0;
            Random random = new Random();
            int t;
            switch (type) {
                case 0:
                    while (true) {
                        do {
                            if (i >= length) {
                                return verifyCode.toString();
                            }

                            t = random.nextInt(10);
                        } while (null != excludeString && excludeString.indexOf(t + "") >= 0);

                        verifyCode.append(t);
                        ++i;
                    }
                case 1:
                    while (true) {
                        do {
                            do {
                                if (i >= length) {
                                    return verifyCode.toString();
                                }

                                t = random.nextInt(123);
                            } while (t < 97 && (t < 65 || t > 90));
                        } while (null != excludeString && excludeString.indexOf((char) t) >= 0);

                        verifyCode.append((char) t);
                        ++i;
                    }
                case 2:
                    while (true) {
                        do {
                            do {
                                if (i >= length) {
                                    return verifyCode.toString();
                                }

                                t = random.nextInt(123);
                            } while (t < 97 && (t < 65 || t > 90) && (t < 48 || t > 57));
                        } while (null != excludeString && excludeString.indexOf((char) t) >= 0);

                        verifyCode.append((char) t);
                        ++i;
                    }
                case 3:
                    while (true) {
                        do {
                            do {
                                if (i >= length) {
                                    return verifyCode.toString();
                                }

                                t = random.nextInt(91);
                            } while (t < 65 && (t < 48 || t > 57));
                        } while (null != excludeString && excludeString.indexOf((char) t) >= 0);

                        verifyCode.append((char) t);
                        ++i;
                    }
                case 4:
                    while (true) {
                        do {
                            do {
                                if (i >= length) {
                                    return verifyCode.toString();
                                }

                                t = random.nextInt(123);
                            } while (t < 97 && (t < 48 || t > 57));
                        } while (null != excludeString && excludeString.indexOf((char) t) >= 0);

                        verifyCode.append((char) t);
                        ++i;
                    }
                case 5:
                    while (true) {
                        do {
                            do {
                                if (i >= length) {
                                    return verifyCode.toString();
                                }

                                t = random.nextInt(91);
                            } while (t < 65);
                        } while (null != excludeString && excludeString.indexOf((char) t) >= 0);

                        verifyCode.append((char) t);
                        ++i;
                    }
                case 6:
                    while (true) {
                        do {
                            do {
                                if (i >= length) {
                                    return verifyCode.toString();
                                }

                                t = random.nextInt(123);
                            } while (t < 97);
                        } while (null != excludeString && excludeString.indexOf((char) t) >= 0);

                        verifyCode.append((char) t);
                        ++i;
                    }
                default:
                    return verifyCode.toString();
            }
        }
    }

    public static BufferedImage generateImageCode(String textCode, int width, int height, int interLine, boolean randomLocation, Color backColor, Color foreColor, Color lineColor) {
        BufferedImage bufferedImage = new BufferedImage(width, height, 1);
        Graphics graphics = bufferedImage.getGraphics();
        graphics.setColor(null == backColor ? generateRandomColor() : backColor);
        graphics.fillRect(0, 0, width, height);
        Random random = new Random();
        int fy;
        int fx;
        int i;
        if (interLine > 0) {
            int x = 0;
            fy = width;
            for (int j = 0; j < interLine; ++j) {
                graphics.setColor(null == lineColor ? generateRandomColor() : lineColor);
                fx = random.nextInt(height);
                j = random.nextInt(height);
                graphics.drawLine(x, fx, fy, j);
            }
        }

        int fsize = (int) ((double) height * 0.8D);
        fx = height - fsize;
        fy = fsize;
        graphics.setFont(new Font("Default", 0, fsize));

        for (i = 0; i < textCode.length(); ++i) {
            fy = randomLocation ? (int) ((Math.random() * 0.3D + 0.6D) * (double) height) : fy;
            graphics.setColor(null == foreColor ? generateRandomColor() : foreColor);
            graphics.drawString(textCode.charAt(i) + "", fx, fy);
            fx = (int) ((double) fx + (double) fsize * 0.9D);
        }

        graphics.dispose();
        return bufferedImage;
    }
}
