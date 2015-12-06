package com.adventofcode;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Day 6: Probably a Fire Hazard
 */
public class Day6 {
    public static void main(String[] args) {
        final String fileName = "sources/Day6Input.txt";

        InstructionParser parser = new InstructionParser();

        LightGrid onOffLightGrid = new OnOffLightGrid();
        LightGrid variableLightGrid = new VariableLightGrid();

        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.forEach(line -> {
                Instruction instruction = parser.parseLine(line);

                onOffLightGrid.manipulateSubgrid(instruction);
                variableLightGrid.manipulateSubgrid(instruction);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(onOffLightGrid.getReport());
        System.out.println(variableLightGrid.getReport());
    }
}

class InstructionParser {
    public Instruction parseLine(String line) {
        String[] Instructions;
        String[] start;
        String[] end;
        int action;

        Instructions = StringUtils.split(line);

        if (Instructions[0].contains("toggle")) {
            action = 2;
            start = StringUtils.split(Instructions[1], ",");
            end = StringUtils.split(Instructions[3], ",");
        } else {
            if (Instructions[1].contains("on")) {
                action = 1;
            } else {
                action = 0;
            }

            start = StringUtils.split(Instructions[2], ",");
            end = StringUtils.split(Instructions[4], ",");
        }

        return new Instruction(
            action,
            new int[]{Integer.parseInt(start[0]), Integer.parseInt(end[0]) + 1},
            new int[]{Integer.parseInt(start[1]), Integer.parseInt(end[1]) + 1}
        );
    }
}

class Instruction {
    int action;
    int[] x = new int[2];
    int[] y = new int[2];

    public Instruction(int action, int[] x, int[] y) {
        this.action = action;
        this.x = x;
        this.y = y;
    }

    public int getAction() {
        return action;
    }

    public int getX(int i) {
        return x[i];
    }

    public int getY(int i) {
        return y[i];
    }
}

interface LightGrid {
    public void manipulateSubgrid(Instruction instruction);
    public String getReport();
}

class OnOffLightGrid implements LightGrid {
    Boolean[][] grid = new Boolean[1000][1000];

    public OnOffLightGrid() {
        for (Boolean[] cells : grid) {
            Arrays.fill(cells, false);
        }
    }

    public void manipulateSubgrid(Instruction instruction) {
        for (int i = instruction.getX(0); i < instruction.getX(1); i++) {
            for (int j = instruction.getY(0); j < instruction.getY(1); j++) {
                this.manipulateCell(i, j, instruction.getAction());
            }
        }
    }

    protected void manipulateCell(int i, int j, int action) {
        switch (action) {
            case 0: // off
                grid[i][j] = false;
                break;
            case 1: // on
                grid[i][j] = true;
                break;
            case 2: // toggle
                grid[i][j] = !grid[i][j];
                break;
        }
    }

    public String getReport() {
        int numberOfLit = 0;

        for (Boolean[] booleans : grid) {
            for (Boolean aBoolean : booleans) {
                if (aBoolean) {
                    numberOfLit += 1;
                }
            }
        }

        return "Number of lights light in this grid: " + numberOfLit;
    }
}


class VariableLightGrid implements LightGrid {
    int[][] grid = new int[1000][1000];

    public VariableLightGrid() {
        for (int[] cells: grid) {
            Arrays.fill(cells, 0);
        }
    }

    public void manipulateSubgrid(Instruction instruction) {
        for (int i = instruction.getX(0); i < instruction.getX(1); i++) {
            for (int j = instruction.getY(0); j < instruction.getY(1); j++) {
                this.manipulateCell(i, j, instruction.getAction());
            }
        }
    }

    protected void manipulateCell(int i, int j, int action) {
        switch (action) {
            case 0: // off
                if (grid[i][j] > 0) {
                    grid[i][j] -= 1;
                }
                break;
            case 1: // on
                grid[i][j] += 1;
                break;
            case 2: // toggle
                grid[i][j] += 2;
                break;
        }
    }

    public String getReport() {
        int brightness = 0;

        for (int[] cells : grid) {
            for (int cell : cells) {
                brightness += cell;
            }
        }

        return "The total brightness of lights in this grid is: " + brightness;
    }
}