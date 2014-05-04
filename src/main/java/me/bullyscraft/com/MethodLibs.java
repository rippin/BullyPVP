package me.bullyscraft.com;


import me.bullyscraft.com.Classes.Kit;
import java.util.LinkedHashMap;


public class MethodLibs {

public static int calculatePriceBuffEnchant(int level, Kit k, String buff ){

    LinkedHashMap<String, Integer> h = k.getBuffsandPrice();

    int p = h.get(buff);

        if (level == 0 ){
            return p;
        }
        else{
            return (p + (level * (level * 50)));
        }
    }

}
