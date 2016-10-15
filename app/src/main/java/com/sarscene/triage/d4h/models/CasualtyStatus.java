package com.sarscene.triage.d4h.models;

import android.graphics.Color;

public enum CasualtyStatus {
    MINOR(Color.GREEN, Color.BLACK),
    DELAYED(Color.YELLOW, Color.BLACK),
    IMMEDIATE(Color.RED, Color.BLACK),
    DECEASED(Color.BLACK, Color.WHITE);

    private int cardColor;
    private int textColor;

    CasualtyStatus(int cardColor, int textColor) {
        this.cardColor = cardColor;
        this.textColor = textColor;
    }

    public int getCardColor() {
        return cardColor;
    }

    public int getTextColor() {
        return textColor;
    }
}