package com.intellij.librarymanager.model;

public enum  Abonnement {
    BASIC(0),PREMIUM(1),VIP(2);
    private int index;

    Abonnement(int index)
    {
        this.index = index;
    }

    //valueOf:use to set
    //toString:used to display
}
