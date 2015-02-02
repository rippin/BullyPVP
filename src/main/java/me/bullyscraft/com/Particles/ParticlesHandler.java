package me.bullyscraft.com.Particles;

import me.bullyscraft.com.BullyPVP;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ParticlesHandler {

    public static void sendParticles(Location location, String particleName, BullyPVP plugin){
        try {
            Object packet = Class.forName("net.minecraft.server." + parseMinecraftVersion() + ".PacketPlayOutWorldParticles").getConstructor().newInstance();
            setPacketValues(location, packet, particleName);
            for (Player p : plugin.getServer().getOnlinePlayers()){
                Method getHandle = p.getClass().getMethod("getHandle");
                Object nmsPlayer = getHandle.invoke(p);
                Field con_field = nmsPlayer.getClass().getField("playerConnection");
                Object con = con_field.get(nmsPlayer);
                Method sendPacket = con.getClass().getMethod("sendPacket", Class.forName("net.minecraft.server." + parseMinecraftVersion() + ".Packet"));
                sendPacket.invoke(con, packet);
            }


        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void setValue(Object instance, String fieldName, Object value) throws Exception {
        Field field = instance.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(instance, value);
    }
    private static void setPacketValues(Location location, Object packet, String particle){
        try {
            setValue(packet, "a", particle);
            setValue(packet, "b", (float) location.getX());
            setValue(packet, "c", (float) location.getY());
            setValue(packet, "d", (float) location.getZ());
            setValue(packet, "e", 0.2F);
            setValue(packet, "f", 0.2F);
            setValue(packet, "g", 0.2F);
            setValue(packet, "h", 0.2F);
            setValue(packet, "i", 6);
        } catch (Exception ex){
            ex.printStackTrace();
        }

    }

    private static String parseMinecraftVersion(){
        String name = Bukkit.getServer().getClass().getPackage().getName();
        String version = name.substring(name.lastIndexOf('.') + 1);
        return version;
    }



}

