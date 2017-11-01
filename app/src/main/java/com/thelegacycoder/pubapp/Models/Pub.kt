package com.thelegacycoder.pubapp.Models

/**
 * Created by Aditya on 028, 28 Oct, 2017.
 */

/*
{
    "pubName": "Pub 1",
    "timings": "4PM to 3AM",
    "location": {
        "lat": 1234,
        "lon": 5678
    },
    "city": "Mumbai",
    "backgroundPic": "background_url",
    "displayPic": "display_url"
}
*/


data class Pub(private val pubName: String = "",
               private val timings: String = "",
               private val location: Location = Location(1.0, 2.0),
               private val city: String = "",
               private val backgroundPic: String = "",
               private val displayPic: String = "") {
    override fun toString(): String {
        return "{\n" +
                "    \"pubName\": \"$pubName\",\n" +
                "    \"timings\": \"$timings\",\n" +
                "    \"location\": {\n" +
                "        \"lat\": ${location.lat},\n" +
                "        \"lon\": ${location.lon}\n" +
                "    },\n" +
                "    \"city\": \"$city\",\n" +
                "    \"backgroundPic\": \"$backgroundPic\",\n" +
                "    \"displayPic\": \"$displayPic\"\n" +
                "}"
    }
}