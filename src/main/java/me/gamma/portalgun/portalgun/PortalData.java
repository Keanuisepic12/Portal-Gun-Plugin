package me.gamma.portalgun.portalgun;

import org.bukkit.Location;

import java.util.UUID;

public class PortalData {

    private Location blue;
    private Location orange;
    private UUID uuid;
    private Integer blueLifetime;
    private Integer orangeLifetime;

    public PortalData(Location b, Location o, Integer blueLifetime, Integer orangeLifetime){

        blue = b;
        orange = o;
        this.blueLifetime = blueLifetime;
        this.orangeLifetime = orangeLifetime;

    }

    public void setBlue(Location blue) {
        this.blue = blue;
    }

    public void setBlue(Location blue, Integer blueLifetime) {
        this.blue = blue;
        this.blueLifetime = blueLifetime;
    }



    public void setOrange(Location orange) {
        this.orange = orange;
    }
    public void setOrange(Location orange, Integer orangeLifetime) {
        this.orange = orange;
        this.orangeLifetime = orangeLifetime;
    }

    public Location getOrange() {
        return orange;
    }
    public Location getBlue() {
        return blue;
    }

    public UUID getUUID() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public boolean portalsActive(){

         if(blue != null && orange != null){

             return true;

         }else{return false;}

    }

    public Integer getBlueLifetime() {
        return blueLifetime;
    }

    public Integer getOrangeLifetime() {

         return orangeLifetime;

    }

    public void setBlueLifetime(Integer blueLifetime) {
        this.blueLifetime = blueLifetime;
    }

    public void setOrangeLifetime(Integer orangeLifetime) {
        this.orangeLifetime = orangeLifetime;
    }

    @Override
    public String toString() {

        if(portalsActive()){
            return "Blue: " + blue.toVector() + " Orange: " + orange.toVector();
        }
        if(blue == null){
            return "Blue: null" + " Orange: " + orange.toVector();
        }
        if(orange == null){
            return "Blue: " + blue.toVector() + " Orange: null";
        }

        return "both are inactive";
    }
}
