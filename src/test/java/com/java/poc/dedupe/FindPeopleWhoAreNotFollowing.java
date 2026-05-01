package com.java.poc.dedupe;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class FindPeopleWhoAreNotFollowing {

    public static void main(String[] args) {
        // Paths to the JSON files
        String followersFile = "src/test/java/com/java/poc/dedupe/followers.json";
        String followingFile = "src/test/java/com/java/poc/dedupe/following.json";

        // Parse both followers and following JSON files
        Set<String> followers = parseJson(followersFile);
        Set<String> following = parseJson(followingFile);

        // Find people not following back (i.e., people in 'following' but not in 'followers')
        Set<String> notFollowingBack = new HashSet<>(following);
        notFollowingBack.removeAll(followers);

        // Print the list of people who are not following back
        System.out.println("People not following back: ");
        for (String person : notFollowingBack) {
            System.out.println("https://www.instagram.com/" + person);
        }
    }

    public static Set<String> parseJson(String filePath) {
        Set<String> resultSet = new HashSet<>();
        try {
            // Read the file to a String
            String content = new String(Files.readAllBytes(Paths.get(filePath)));

            // Create a JSONArray
            JSONArray jsonArray = new JSONArray(content);

            // Loop through the array
            for (int i = 0; i < jsonArray.length(); i++) {
                // Get each JSONObject in the array
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                // Get the nested JSONArray
                JSONArray stringListData = jsonObject.getJSONArray("string_list_data");

                // Loop through the nested array
                for (int j = 0; j < stringListData.length(); j++) {
                    // Get each nested JSONObject
                    JSONObject obj = stringListData.getJSONObject(j);

                    // Get the value of the "value" key
                    String value = obj.getString("value");

                    // Add the value to the result set
                    resultSet.add(value);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultSet;
    }
}