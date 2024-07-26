package com.uvt.bachelor.pawfectmatch;

public class DistanceUtil {

    private static final int EARTH_RADIUS = 6371; // Radius of the earth in kilometers

    /**
     * Calculate the distance between two points in latitude and longitude taking
     * into account height difference. If you are not interested in height
     * difference pass 0.0.
     *
     * @param lat1 Start point latitude
     * @param lon1 Start point longitude
     * @param lat2 End point latitude
     * @param lon2 End point longitude
     * @return Distance in kilometers
     */
    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS * c;
    }
}
