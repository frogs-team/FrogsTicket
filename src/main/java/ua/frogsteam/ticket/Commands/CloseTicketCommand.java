package ua.frogsteam.ticket.Commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.doc.standard.CommandInfo;
import ua.frogsteam.ticket.Config;

import java.util.concurrent.TimeUnit;

import static ua.frogsteam.ticket.Utilities.getDefaultEmbed;

@CommandInfo(name = "close", description = "Deletes the ticket channel " +
        "in which the command was executed.")

public class CloseTicketCommand extends Command {

    private int closeCooldown = Config.TICKET_CLOSE_COOLDOWN;

    public CloseTicketCommand() {
        super.name = "close";
        super.help = "Close an open ticket.";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (!event.getTextChannel().getName().contains("ticket-"))
            return;

        event.reply(getDefaultEmbed("Ticket => Close", "Closing this ticket in " + closeCooldown
                + " second(s).").build());

        event.getTextChannel().delete().queueAfter(closeCooldown, TimeUnit.SECONDS);
    }
}
