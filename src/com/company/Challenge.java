package com.company;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;


public class Challenge {
    public static void main(String[] args) {

        System.out.println("Please enter the location where p15 files are stored:");
        String directoryPath = new Scanner(System.in).nextLine();
        File listOfFiles[] = null;
        try {
            File directory = new File(directoryPath);
            listOfFiles = directory.listFiles();
        }catch (Exception e) {
            // TODO: handle exception
            System.out.println("Please specify a correct path.");

        }

        if(listOfFiles ==null) {
            System.out.println("No file exists at " + directoryPath + " or this is an invalid directory.");
            return;
        }
        // reading the files and validating their contents
        FileReading:
        for(File file:listOfFiles) {
            Matrix matrix;
            int[][] solution= new int[4][4];
            int[][] problem=new int[4][4];
            if(file.isDirectory() || !file.getName().endsWith(".p15"))
                continue;


            try {

                List<String> lines= Files.readAllLines(Paths.get(file.getAbsolutePath()));
                if(lines.size() > 4) {
                    //System.out.println("count error");
                    System.out.println(file.getName() + " - -3");
                    continue;
                }

                int lineIndex=0;
                Set<Integer> allElements= new HashSet<Integer>();

                for(String line: lines) {

                    if(line.matches("^[0-9 ]+$")) {
                        String[] arrayData = line.split(" ");
                        if(arrayData.length < 4) {
                            System.out.println(file.getName() + " - -2");
                            continue FileReading;
                        }

                        int colIndex = 0;
                        for(String data: arrayData) {

                            if(data.length() > 0) {

                                try {
                                    Integer.parseInt(data.trim());
                                }
                                catch (Exception e) {
                                    //e.printStackTrace();
                                    System.out.println(file.getName()+" - -2");
                                    continue FileReading;
                                }
                            }
                            else
                            continue;

                            int val = Integer.parseInt(data.trim());
                            if(val >= 0 && val <= 15) {
                                problem[lineIndex][colIndex] = val;
                                colIndex++;
                                allElements.add(val);
                            }
                            else {
                                //System.out.println(val + " 0-15 range error");
                                System.out.println(file.getName() + " - -2");
                                continue FileReading;
                            }
                        }
                        if(allElements.size()%4 != 0) {
                            //System.out.println("formatting error");
                            System.out.println(file.getName() + " - -2");
                            continue FileReading;
                        }
                    }
                    else {
                        //System.out.println("Regex not matching error");
                        System.out.println(file.getName() + " - -2");
                        continue FileReading;
                    }
                    lineIndex++;
                }

                if(allElements.size() != 16) {
                    //System.out.println("All element not unique error");
                    System.out.println(file.getName() + " - -2");
                    continue FileReading;
                }


                solution = new int[][] {{ 1, 2, 3, 4 },
                                        { 5, 6, 7, 8 },
                                        { 9, 10, 11, 12 },
                                        { 13, 14, 15, 0 }};

                matrix = new Matrix(problem, solution);

                ProblemSolver solver = new ProblemSolver();
                Matrix.DIRECTION[] directions = {Matrix.DIRECTION.RIGHT, Matrix.DIRECTION.DOWN, Matrix.DIRECTION.UP, Matrix.DIRECTION.LEFT};

                if(solver.isSolvable(problem)) {
                    Matrix solvedMatrix = solver.solve(matrix, directions);
                    System.out.println(file.getName() + " - " + solvedMatrix.getPath().length());

                }
                else {
                    System.out.println(file.getName() + " - -1");
                    continue FileReading;
                }

            }catch (Exception e) {
                // TODO: handle exception
            }

        }

    }

}
