package org.pattonvillerobotics;

import org.pattonvillerobotics.commoncode.robotclasses.vuforia.VuforiaParameters;

/**
 * Created by pieperm on 10/12/17.
 */

public class CustomVuforiaParameters {

    public static VuforiaParameters VUFORIA_PARAMTERS;

    static {

        VUFORIA_PARAMTERS = new VuforiaParameters.Builder()
                .build();

    }

}
