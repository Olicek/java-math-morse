package org.olisar.settings;

import java.util.ResourceBundle;

public class PropertiesSetting implements ISetting {

    private static final ResourceBundle CONFIG = ResourceBundle.getBundle("config");

    private PropertiesSetting() {
    }

    public static int getMaxLimit() {
        return Integer.parseInt(CONFIG.getString("maxLimit"));
    }

    public static String[] getAllowedSigns() {
        return CONFIG.getString("allowedSigns").split("");
    }

}
