package org.kingdoms.commands.outposts;

import org.bukkit.command.CommandSender;
import org.kingdoms.commands.CommandContext;
import org.kingdoms.commands.KingdomsParentCommand;
import org.kingdoms.locale.compiler.MessageCompiler;
import org.kingdoms.locale.provider.MessageBuilder;
import org.kingdoms.outposts.Outpost;
import org.kingdoms.outposts.OutpostsLang;
import org.kingdoms.services.managers.SoftService;

public class CommandOutpost extends KingdomsParentCommand {
    public static boolean worldGuardMissing(CommandSender sender) {
        if (SoftService.WORLD_GUARD.isAvailable()) return false;
        MessageCompiler.compile("&4You need to install &ehover:{&nWorldGuard;Click to open the download page;@https://dev.bukkit.org/projects/worldguard} " +
                "&4plugin in order to use Outpost events."
        ).getExtraProvider().err().send(sender, new MessageBuilder().usePrefix());
        return true;
    }

    protected static Outpost getOutpost(CommandContext context, int index) {
        String outpostName = context.arg(index);
        Outpost outpost = Outpost.getOutpost(outpostName);
        if (outpost == null) context.sendError(OutpostsLang.COMMAND_OUTPOST_NOT_FOUND, "outpost", outpostName);
        return outpost;
    }

    public CommandOutpost() {
        super("outpost");
        if (isDisabled()) return;

        new CommandOutpostCreate(this);
        new CommandOutpostJoin(this);
        new CommandOutpostStart(this);
        new CommandOutpostStop(this);
        new CommandOutpostEdit(this);
    }
}
