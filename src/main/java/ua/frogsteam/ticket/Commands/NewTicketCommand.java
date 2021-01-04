package ua.frogsteam.ticke.Commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.doc.standard.CommandInfo;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.managers.ChannelManager;
import ua.frogsteam.ticket.*;

import static ua.frogsteam.ticket.Utilities.getDefaultEmbed;

@CommandInfo(name = "new", description =  "Creates a new text channel in the category specified, with an appropriate name.")
public class NewTicketCommand extends Command {

    private String ticketCategory = Config.TICKET_CATEGORY_NAME;
    private int suffixLength = Config.RANDOM_SUFFIX_LENGTH;
    private String[] supportRoles = Config.SUPPORT_ROLE_LIST;

    public NewTicketCommand() {
        super.name = "new";
        super.arguments = "[reason]";
        super.help = "Create a new ticket.";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (ticketCategory.isEmpty()) {
            System.out.println("The ticket category \"" + ticketCategory + "\" is not specified.");
            System.exit(1);
        }

        boolean withReason = !event.getArgs().isEmpty();
        if (!(event.getGuild().getCategoriesByName(ticketCategory, true).size() > 0)) {
            System.out.println("The ticket category \"" + ticketCategory + "\" does not exist.");
            System.exit(1);
        }

        String suffix = Utilities.getSuffix(suffixLength);
        String ticketName = "ticket-" + event.getAuthor().getName();

        if (suffix != null) {
            ticketName += "-" + suffix;
        }

        TextChannel ticket = event.getGuild().createTextChannel(ticketName, event.getGuild().getCategoriesByName(ticketCategory, true).get(0)).complete();
		ChannelManager ticketManager = ticket.getManager().putPermissionOverride(event.getMember(), 3072L, 8192L)
                .putPermissionOverride(event.getGuild().getRolesByName("@everyone", true).get(0), 0L, 1024L);
        for (String supportRole : supportRoles) {
            if (!event.getGuild().getRolesByName(supportRole, true).isEmpty()) {
                ticketManager = ticketManager.putPermissionOverride(event.getGuild().getRolesByName(supportRole, true).get(0), 3072L, 8192L);
            }
        }
        ticketManager.queue();
        event.reply(getDefaultEmbed("Ticket => Created",
                "The ticket <#" + ticket.getId() + "> was created." + (withReason ? "\n\n**Reason**: " + event.getArgs() : ""))
                        .build());
        ticket.sendMessage(getDefaultEmbed("Ticket => Created",
                "Please wait! Our staff will be there to assist you shortly.\n\n**Created By:** " + event.getAuthor().getAsMention() + (withReason ? "\n**Reason:** " + event.getArgs() : ""))
                .build()).queue();
    }
}
