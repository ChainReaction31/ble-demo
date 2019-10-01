package tech.digitalbridge.oshblebeacons;

import org.altbeacon.beacon.Beacon;

import java.util.Set;
import java.util.SortedSet;

public class BLELocator {
    private SortedSet<LocatedBeacon> bestSignal;
    SortedSet<LocatedBeacon> potentialBeacons;
    double updateResolution;
    int numPotentials;
    int beaconsInRange = 0;

    public BLELocator(double updateResolution, int numPotentials) {
        this.updateResolution = updateResolution;
        this.numPotentials = numPotentials;
    }

    // TODO: make this a bit more error resistant
    public void addBeacon(Beacon beacon) {
        bestSignal.add(new LocatedBeacon(beacon, 0, 0, 0));
       if(bestSignal.size() > 3){
           potentialBeacons.add(bestSignal.first());
           bestSignal.remove(bestSignal.first());
       }
    }


    private class LocatedBeacon implements Comparable<LocatedBeacon> {
        private double latitude, longitude, altitude;
        private Beacon beacon;

        protected LocatedBeacon(Beacon beacon, double latitude, double longitude, double altitude) {
            this.beacon = beacon;
            this.latitude = latitude;
            this.longitude = longitude;
            this.altitude = altitude;
        }

        protected Beacon getBeacon() {
            return this.beacon;
        }

        // Compare two beacons by their running average rssi
        @Override
        public int compareTo(LocatedBeacon otherBeacon) {
            return (int) (this.getBeacon().getRunningAverageRssi() - otherBeacon.getBeacon().getRunningAverageRssi());
        }

    }
}


/*public class BLELocator {
    Beacon beacon;
    UUID uuid;
    double avgRssi;
    double distance;
    int measurements;
    boolean isDetected = false;

   public BLELocator(Beacon beacon){
        this.uuid = UUID.randomUUID();
        this.beacon = beacon;
        this.distance = beacon.getDistance();
        this.avgRssi = beacon.getRunningAverageRssi();
        this.measurements = 1;
        this.isDetected = true;
   }

   public void updateBeacon(){
       this.avgRssi = this.beacon.getRunningAverageRssi();
       this.distance = this.beacon.getDistance();
   }


}*/


