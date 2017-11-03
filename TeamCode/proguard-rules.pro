#-verbose
-dontobfuscate

#Team Code
-keep,includedescriptorclasses class org.pattonvillerobotics.** {
    *;
}

-keep,includedescriptorclasses class com.vuforia.** {
    *;
}

-keep,includedescriptorclasses class org.firstinspires.** {
    *;
}

-keep,includedescriptorclasses class com.qualcomm.** {
    *;
}

-dontwarn com.sun.tools.**
-dontwarn org.pattonvillerobotics.commoncode.**
-dontwarn com.google.gson.**
-dontwarn com.vuforia.ar.pl.**
-dontwarn org.firstinspires.ftc.robotcore.**
-dontwarn com.qualcomm.analytics.**
-dontwarn sun.misc.Unsafe**
-dontwarn java.lang.ClassValue**
-dontwarn java.awt.geom.**