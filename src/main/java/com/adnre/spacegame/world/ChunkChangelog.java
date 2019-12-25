package com.adnre.spacegame.world;

import java.io.Serializable;
import java.util.ArrayList;

public class ChunkChangelog implements Serializable {

    // These are really just keyboard mashes
    private static final long serialVersionUID = 38298091203482L;
    private ArrayList<ChunkChange> changes;

    public ChunkChangelog(){
        changes = new ArrayList<>();
    }

    public void add (ChunkChange change){
        changes.add(change);
    }

    public ArrayList<ChunkChange> getChanges() {
        return changes;
    }
}
