package com.nyan.nyanMod.guis.descriptions;

import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import io.github.cottonmc.cotton.gui.widget.WTextField;

public class Plain extends WPlainPanel {

    public WTextField label;
    public WLabel cost;

    public Plain() {
        label = new WTextField();
        label.setMaxLength(100);
        this.add(label, 2, 2, 10*18, 18);
        cost = new WLabel("1000 Xp");
        this.add(cost, 2, 20, 6*18, 18);

        this.setSize(10*18, 2*18);
    }
}