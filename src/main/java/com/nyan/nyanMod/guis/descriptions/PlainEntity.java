package com.nyan.nyanMod.guis.descriptions;

import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WTextField;

public class PlainEntity extends Plain {
    public WButton button;
    public PlainEntity() {
        button = new WButton();
        this.add(button,10*18+3,2);

    }
}
