package Save_System;

import Base.Pair;

/**
 * Automatically refactored by Rek'Sai on 3/23/16.
 */



//This is basically a Typedef
//Prevents us from having to write "Triple<Pair<int[], int[]>, Pair<int[], int[]>, Pair<int[], int[]>>" everywhere
//Still not sure if this is enough to justify it though
class RGBTuple extends Triple<Pair<int[], int[]>, Pair<int[], int[]>, Pair<int[], int[]>>
{
    public RGBTuple(Pair<int[], int[]> left, Pair<int[], int[]> middle, Pair<int[], int[]> right) {
        super(left, middle, right);
    }
}
