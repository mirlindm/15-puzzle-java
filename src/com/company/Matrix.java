package com.company;

import java.awt.*;
import java.util.Arrays;

public class Matrix {

    public enum DIRECTION {UP, DOWN, LEFT, RIGHT}
    private final int[][] matrix;
    private int[][] correctMatrix;
    private String path = "";
    public int zeroX, zeroY;
    private Point zeroPoint;
    public Matrix(int[][] matrix, int[][] correctMatrix) {
        this.matrix = matrix;
        this.correctMatrix = correctMatrix;
        findZeroLocation();
    }

    public Matrix(Matrix newMatrix) {
        matrix = new int[4][4];
        for (int i = 0; i < matrix.length; i++) {
            matrix[i] = Arrays.copyOf(newMatrix.matrix[i], 4);
        }
        correctMatrix = newMatrix.correctMatrix;
        zeroX = newMatrix.zeroX;
        zeroY = newMatrix.zeroY;
        path = newMatrix.path;
    }
    public boolean isSolved() {
        if(Arrays.deepEquals(matrix, correctMatrix))
            return true;

        return false;
    }

    public boolean move(DIRECTION direction) {
        switch (direction) {
            case UP: //if it is not the first row, then can go: UP
                if (zeroY != 0) {
                    swap(zeroY, zeroX, (zeroY - 1), zeroX);
                    path += "U";
                    return true;
                }
                break;
            case DOWN: // if zeroY or the row is not the last row of the matrix, then can go: DOWN
                if (zeroY != matrix.length - 1) {
                    swap(zeroY, zeroX, (zeroY + 1), zeroX);
                    path += "D";
                    return true;
                }
                break;
            case LEFT: // if it is not the first column then go: LEFT (towards the first column)
                if (zeroX != 0) {
                    swap(zeroY, zeroX, zeroY, (zeroX - 1));
                    path += "L";
                    return true;
                }
                break;
            case RIGHT: // if it is not the last column, the go: RIGHT
                if (zeroX != matrix[zeroY].length - 1) {
                    swap(zeroY, zeroX, zeroY, (zeroX + 1));
                    path += "R";
                    return true;
                }
                break;
        }

        return false;
    }

    public String getPath() {
        return path;
    }

    private void swap(int y1, int x1, int y2, int x2) {
        int previous = getValue(y1, x1);
        setValue(y1, x1, getValue(y2, x2));
        setValue(y2, x2, previous);
        zeroX = x2;
        zeroY = y2;

    }
    private void setValue(int y, int x, int tile) {
        matrix[y][x] = tile;
    }
    private int getValue(int y, int x) {
        return matrix[y][x];
    }

    private void findZeroLocation() {

        for (int y = 0; y < matrix.length; ++y) {
            for (int x = 0; x < matrix[y].length; ++x) {
                if (matrix[y][x] == 0) {
                    zeroY = y;
                    zeroX = x;
                }
            }
        }
    }

}
