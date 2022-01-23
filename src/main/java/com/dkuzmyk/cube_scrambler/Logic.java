package com.dkuzmyk.cube_scrambler;

import java.lang.reflect.Array;
import java.util.*;
import java.util.regex.Pattern;

public class Logic {
    private String word;                            // contains the word
    private boolean[] used;                         // contains the active cubes, true for used as default
    private Cube[] listOfCubes;                     // list of Cube objects
    private Stack<Integer> rowUsed;                 // list of utilized cubes in the word search
    private HashMap<Integer, Integer> map_used;     // mapping the face used
    private ArrayList<String> finalMap;       // since we clear the map after wrapping up, we make a copy to have a path
    private int numCombinations;

    Logic() {
        word = "";
        listOfCubes = new Cube[6];
        finalMap = new ArrayList<>();
        used = new boolean[6];
        rowUsed = new Stack<>();
        map_used = new HashMap<>();
        numCombinations = 0;

        for (int i = 0; i < 6; i++) {
            used[i] = true;
        }
    }
    // converts the input string into an array of strings and creates a new Cube obj
    public void addCube(String str, int index){
        String[] value = str.split(",");
        Cube cube = new Cube(value);
        listOfCubes[index] = cube;
        used[index] = false;
        System.out.println("Added new Cube idx: "+index+" value: " + Arrays.toString(value));
    }
    // reset the cube after textfield is toggled off
    public void resetCube(int index){
        System.out.println("Resetting cube "+index+" "+listOfCubes[index]);
        used[index] = true;
        for (int i = 0; i < 6; i++) {
            if(listOfCubes[index]!=null)
                listOfCubes[index].setFaces("", i);
        }
    }
    // regex to check the input validity
    public boolean checkValidCube(String str){
        return Pattern.matches("[a-z]{1},[a-z]{1},[a-z]{1},[a-z]{1},[a-z]{1},[a-z]", str);
    }
    // update existing cube
    public boolean updateCube(String c, int index){
        boolean changed = false;
        String[] value = c.split(",");
        System.out.println("SETTING -> Cube: "+index+" faces: "+ Arrays.toString(value));
        if(listOfCubes[index] == null)
            return false;
        String[] presentCube = listOfCubes[index].getFaces();
        for (int i = 0; i < value.length; i++) {
            if(!value[i].equals(presentCube[i])) {
                listOfCubes[index].setFaces(value[i], i);
                changed = true;
            }
        }
        if(changed)
            System.out.println("Edited cube");
        else
            System.out.println("Not edited cube");
        return changed;
    }
    // backtracking recursive helper
    public boolean solveHelper(int index){
        boolean result = false;
        if(index == word.length()) {
            numCombinations++;
            finalMap.add("Possible combination #"+numCombinations);
            copyMap();
            return true;
        }

        int count = 0;
        for (Cube cube : listOfCubes) {
            if (cube != null){
                for (int j = 0; j < cube.getFaces().length; j++) {
                    if (String.valueOf(word.charAt(index)).equals(cube.getFaces()[j]) && !rowUsed.contains(count)) {
                        map_used.put(count, j);
                        rowUsed.add(count);
                        result = solveHelper(index + 1);
                        map_used.remove(count);
                        rowUsed.pop();
                    }
                }
            }
            count++;
        }
        return result;
    }
    // recursive setting up
    public boolean solve(){
        // convert Cubes to a 2d array
        numCombinations = 0;
        finalMap.clear();
        map_used.clear();
        boolean result = solveHelper(0);
        printResultPath();
        return result;
    }

    private void printResultPath() {
        System.out.println(finalMap.toString());
    }

    public ArrayList<String> getResultPath(){
        return finalMap;
    }

    private void copyMap() {
        System.out.println(map_used.toString());
        for (Map.Entry<Integer, Integer> e : map_used.entrySet()) {
            finalMap.add("Cube: "+(e.getKey()+1) + " Face: "+(e.getValue()+1) + " Value: "+listOfCubes[e.getKey()].getFaces()[e.getValue()]);
        }
    }

    public void setWord(String word) {
        this.word = word;
        System.out.println("word set: "+word);
    }

    public boolean[] getUsed() {
        return used;
    }

    public void printCubes() {
        System.out.println("TOTAL CUBES:");
        for(int i = 0; i < used.length; i++){
            if(!used[i])
                System.out.println(Arrays.toString(listOfCubes[i].getFaces()));
        }
    }
}
