package me.screwage.MentionMe.Resources;

public enum NotePitch {
    F_SHARP("F_SHARP", 0.5f),
    G("G", 0.529732f),
    G_SHARP("G_SHARP", 0.561231f),
    A("A", 0.594604f),
    A_SHARP("A_SHARP", 0.629961f),
    B("B", 0.667420f),
    C("C", 0.707107f),
    C_SHARP("C_SHARP", 0.749154f),
    D("D", 0.793701f),
    D_SHARP("D_SHARP", 0.840896f),
    E("E", 0.890899f),
    F("F", 0.943874f),
    F_SHARP_TWO("F_SHARP_TWO", 1.0f),
    G_TWO("G_TWO", 1.059463f),
    G_SHARP_TWO("G_SHARP_TWO", 1.122462f),
    A_TWO("A_TWO", 1.189207f),
    A_SHARP_TWO("A_SHARP_TWO", 1.259921f),
    B_TWO("B_TWO", 1.334840f),
    C_TWO("C_TWO", 1.414214f),
    C_SHARP_TWO("C_SHARP_TWO", 1.498307f),
    D_TWO("D_TWO", 1.587401f),
    D_SHARP_TWO("D_SHARP_TWO", 1.681793f),
    E_TWO("E_TWO", 1.781797f),
    F_TWO("F_TWO", 1.887749f),
    F_SHARP_THREE("F_SHARP_THREE", 2.0f);

    private final float pitch;
    private final String label;

    NotePitch(String label, float pitch){
        this.label = label;
        this.pitch = pitch;
    }

    public float getPitch(){
        return this.pitch;
    }

    public static NotePitch getNoteFromPitch(float pitch){
        for (NotePitch note : values())
            if(note.getPitch() == pitch)
                return note;
        return null;
    }
}

