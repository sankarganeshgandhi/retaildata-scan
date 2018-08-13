package com.sgglabs.retail;

public enum StatusEnum {
    Inactive(1),
    Active(2);

    private int enumValue;

    StatusEnum(int value) {
        this.enumValue = value;
    }

    public int getValue() {
        return this.enumValue;
    }
}