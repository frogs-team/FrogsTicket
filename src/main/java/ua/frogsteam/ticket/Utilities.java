package ua.frogsteam.ticket;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Activity;

import java.awt.*;

public class Utilities {

    private static final String TICKET_SUFFIX_CHARS = "abcdefghijklmnopqrstuvwxyz0123456789";
    public static EmbedBuilder getDefaultEmbed(String title, String description) {
        return new EmbedBuilder().setTitle(title).setDescription(description).setColor(Color.decode(Config.HEX_COLOR));
    }
	
    static Activity getActivity() {
        String activityRaw = Config.BOT_ACTIVITY;
        if (activityRaw.trim().isEmpty()) {
            System.out.println("Please make sure that you enter an activity.");
            System.exit(1);
        }

        String activityType = activityRaw.trim().split(" ")[0];
        String activity = activityRaw.substring(activityType.length());

        switch (activityType.toLowerCase()) {
            case "watching":
                return Activity.watching(activity);
            case "listening":
                return Activity.listening(activity);
            case "playing":
                return Activity.playing(activity);
            default:
                System.out.println("The activity you've entered is invalid.");
                System.exit(1);
                return null;
        }
    }

    public static String getSuffix(int length) {
        StringBuilder builder = new StringBuilder();
        while (length > 0) {
            builder.append(TICKET_SUFFIX_CHARS.charAt((int)(Math.random() * TICKET_SUFFIX_CHARS.length())));
            length--;
        }
        return builder.toString().trim().isEmpty() ? null : builder.toString();
    }
}
