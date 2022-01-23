package com.dkuzmyk.cube_scrambler;

public class Cube {
    private String[] faces;
    private boolean used;
    private int num;

    Cube(String[] input){
        faces = new String[6];
        System.arraycopy(input, 0, faces, 0, 6);
    }

    public String[] getFaces() {
        return faces;
    }

    public void resetFaces(String[] faces) {
        for (int e = 0; e < 6; e++ ){
            this.faces[e] = "";
        }
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setFaces(String s, int i) {
        this.faces[i] = s;
    }
}
