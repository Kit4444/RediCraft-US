package at.mlps.rc.cmd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import at.mlps.rc.api.APIs;

public class TPA_System implements CommandExecutor{
	
    static HashMap<UUID, UUID> tprequests = new HashMap<>();
    static HashMap<UUID, Boolean> tprequesttype = new HashMap<>();
    static ArrayList<UUID> tpblock = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            Bukkit.getConsoleSender().sendMessage("§cBitte ingame ausführen.");
        }else {
            Player p = (Player)sender;
            if(cmd.getName().equalsIgnoreCase("tpa")) {
                if(args.length == 0) {
                    p.sendMessage(APIs.prefix("main") + APIs.returnStringReady(p, "usage") + "§7/tpa <Player>");
                }else if(args.length == 1) {
                    Player p2 = Bukkit.getPlayerExact(args[0]);
                    if(p2 == null) {
                        APIs.sendMSGReady(p, "cmd.tpa.notonline");
                    }else {
                        if(p2.getName() == p.getName()) {
                        	APIs.sendMSGReady(p, "cmd.tpa.ownrequest");
                            //p.sendMessage(APIs.prefix("main") + "§cDu kannst dir selbst keine TPA-Anfrage senden. Du Trottel!");
                        }else {
                            if(tpblock.contains(p2.getUniqueId())) {
                            	APIs.sendMSGReady(p, "cmd.tpa.playerblocked");
                                //p.sendMessage(APIs.prefix("main") + "§cDieser Spieler hat TPA-Anfragen geblockt.");
                            }else {
                                tprequests.put(p2.getUniqueId(), p.getUniqueId());
                                tprequesttype.put(p2.getUniqueId(), false);
                                p.sendMessage(APIs.prefix("main") + APIs.returnStringReady(p, "cmd.tpa.success.ownmsg").replace("%displayer", p2.getDisplayName()));
                                p2.sendMessage(APIs.prefix("main") + APIs.returnStringReady(p2, "cmd.tpa.success.othermsg.main").replace("%displayer", p.getDisplayName()));
                                APIs.sendMSGReady(p2, "cmd.tpa.success.othermsg.info");
                                //p.sendMessage(APIs.prefix("main") + "§7Du hast dem Spieler §a" + p2.getName() + " §7eine TPA-Anfrage geschickt.");
                                //p2.sendMessage(APIs.prefix("main") + "§7Du hast eine TPA-Anfrage von §a" + p.getName() + " §7bekommen.");
                            }
                        }
                    }
                }else {
                	p.sendMessage(APIs.prefix("main") + APIs.returnStringReady(p, "usage") + "§7/tpa <Player>");
                }
            }else if(cmd.getName().equalsIgnoreCase("tpahere")) {
            	if(args.length == 0) {
                    p.sendMessage(APIs.prefix("main") + APIs.returnStringReady(p, "usage") + "§7/tpahere <Player>");
                }else if(args.length == 1) {
                    Player p2 = Bukkit.getPlayerExact(args[0]);
                    if(p2 == null) {
                        APIs.sendMSGReady(p, "cmd.tpahere.notonline");
                    }else {
                        if(p2.getName() == p.getName()) {
                        	APIs.sendMSGReady(p, "cmd.tpahere.ownrequest");
                            //p.sendMessage(APIs.prefix("main") + "§cDu kannst dir selbst keine TPA-Anfrage senden. Du Trottel!");
                        }else {
                            if(tpblock.contains(p2.getUniqueId())) {
                            	APIs.sendMSGReady(p, "cmd.tpahere.playerblocked");
                                //p.sendMessage(APIs.prefix("main") + "§cDieser Spieler hat TPA-Anfragen geblockt.");
                            }else {
                                tprequests.put(p2.getUniqueId(), p.getUniqueId());
                                tprequesttype.put(p2.getUniqueId(), true);
                                p.sendMessage(APIs.prefix("main") + APIs.returnStringReady(p, "cmd.tpahere.success.ownmsg").replace("%displayer", p2.getDisplayName()));
                                p2.sendMessage(APIs.prefix("main") + APIs.returnStringReady(p2, "cmd.tpahere.success.othermsg.main").replace("%displayer", p.getDisplayName()));
                                APIs.sendMSGReady(p2, "cmd.tpahere.success.othermsg.info");
                                //p.sendMessage(APIs.prefix("main") + "§7Du hast dem Spieler §a" + p2.getName() + " §7eine TPA-Anfrage geschickt.");
                                //p2.sendMessage(APIs.prefix("main") + "§7Du hast eine TPA-Anfrage von §a" + p.getName() + " §7bekommen.");
                            }
                        }
                    }
                }else {
                	p.sendMessage(APIs.prefix("main") + APIs.returnStringReady(p, "usage") + "§7/tpa <Player>");
                }
            }else if(cmd.getName().equalsIgnoreCase("tpaccept")) {
                if(tprequests.containsKey(p.getUniqueId())) {
                    Player p2 = Bukkit.getPlayer(tprequests.get(p.getUniqueId()));
                    boolean state = tprequesttype.get(p.getUniqueId());
                    if(state == true) {
                    	p.teleport(p2);
                    }else {
                    	p2.teleport(p);
                    }
                    tprequests.remove(p.getUniqueId());
                    tprequesttype.remove(p.getUniqueId());
                }else {
                    APIs.sendMSGReady(p, "cmd.tpaccept.noreqopen");
                }
            }else if(cmd.getName().equalsIgnoreCase("tpdeny")) {
                if(tprequests.containsKey(p.getUniqueId())) {
                    tprequests.remove(p.getUniqueId());
                    tprequesttype.remove(p.getUniqueId());
                    APIs.sendMSGReady(p, "cmd.tpdeny.declined");
                }else {
                	APIs.sendMSGReady(p, "cmd.tpdeny.noreqopen");
                }
            }else if(cmd.getName().equalsIgnoreCase("blocktpa")) {
                if(tpblock.contains(p.getUniqueId())) {
                    tpblock.remove(p.getUniqueId());
                    APIs.sendMSGReady(p, "cmd.blocktpa.removed");
                }else {
                    tpblock.add(p.getUniqueId());
                    APIs.sendMSGReady(p, "cmd.blocktpa.added");
                }
            }
        }
        return false;
    }

}
