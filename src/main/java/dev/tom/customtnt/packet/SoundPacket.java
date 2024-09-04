package dev.tom.customtnt.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import dev.tom.customtnt.CustomTNT;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLOutput;

public class SoundPacket {

    public SoundPacket(JavaPlugin plugin){
        System.out.println("Sound Packet Registered");
        CustomTNT.getProto().addPacketListener(new PacketAdapter(plugin, ListenerPriority.NORMAL, PacketType.Play.Server.ENTITY_SOUND) {

            @Override
            public void onPacketReceiving(PacketEvent event) {
                System.out.println("Packet Received: " + event.getPacketType());
            }

            @Override
            public void onPacketSending(PacketEvent event) {
                System.out.println("Packet Sent: " + event.getPacketType());
                for (Integer value : event.getPacket().getIntegers().getValues()) {
                    System.out.println("Listed Value: " + value);
                }
            }

        });
    }

}
