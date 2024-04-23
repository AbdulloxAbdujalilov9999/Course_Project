package com.epam.app.util;

import static com.epam.app.constant.AnsiColorConstants.ANSI_RED;
import static com.epam.app.constant.AnsiColorConstants.ANSI_RESET;

public class SystemUtils {
    public static void wrongEntered() {
        System.out.println(ANSI_RED + "Unknown command, please enter again!" + ANSI_RESET);
    }

    public static void searchSystemHeader() {
        System.out.println("_______________________________________________________________");
        System.out.println("           Bed Lines and Dishes Werehouse ©2024");
        System.out.println("Contact: ");
        System.out.println("Tel: +998979995330");
        System.out.println("Developer: Abdujalilov Abdullox\n");
    }
}
