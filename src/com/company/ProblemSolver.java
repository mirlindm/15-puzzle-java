package com.company;

import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ProblemSolver {

    private final Queue<Matrix> matrixQueue = new LinkedList<>();


    private int getRowNoHavingZero(int[][] problem) {

        for (int y = 0; y < problem.length; ++y) {
            for (int x = 0; x < problem[y].length; ++x) {
                if (problem[y][x] == 0) {
                    return y+1;
                }
            }
        }
        return -1;
    }

    public boolean isSolvable(int[][] problem) {

        int[] array = Stream.of(problem).flatMapToInt(IntStream::of).toArray();
        int zeroRowNo = getRowNoHavingZero(problem);
        int  totalInversions= getInversionCount(array);

        // is solvable if the row number where 0 is located and the  number of total inversions are both, either even either odd
        if((zeroRowNo %2 == 0 && totalInversions %2 == 0) || (zeroRowNo %2 != 0 && totalInversions %2 != 0)) {
            return true;
        }
        else
            return false;
    }


    int getInversionCount(int arr[])
    {
        int inv_count = 0;
        for (int i = 0; i < 15; i++)
        {
            for (int j = i + 1; j < 16; j++)
            {
                // count pairs(i, j) such that i appears
                // before j, but i > j.
                if (arr[j]!=0 && arr[i]!=0 && arr[i] > arr[j])
                    inv_count++;
            }
        }
        return inv_count;
    }


    public Matrix solve(Matrix matrixToSolve, Matrix.DIRECTION[] strategy) {
        matrixQueue.add(matrixToSolve);
        while (!matrixQueue.isEmpty()) {
            Matrix matrix = matrixQueue.poll();
            if (matrix.isSolved()) {
                return matrix;
            }
            for (int i = 0; i < strategy.length; i++) {

                Matrix newMatrix = new Matrix(matrix);
                if(newMatrix.move(strategy[i]))
                    matrixQueue.add(newMatrix);

            }
        }
        return null;
    }

}
