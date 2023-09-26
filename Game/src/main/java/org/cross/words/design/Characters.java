package org.cross.words.design;

public enum Characters {

    vline("│"),
    hline("─"),
    llcorner("└"),
    ulcorner("┌"),
    lrcorner("┘"),
    urcorner("┐"),
    ttee("┬"),
    btee("┴"),
    ltee("├"),
    rtee("┤"),
    bigplus("┼"),
    lhblock("▌"),
    rhblock("▐"),
    fullblock("█");

    private final String value;

    Characters(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
