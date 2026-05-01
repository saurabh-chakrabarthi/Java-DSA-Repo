package com.java.poc.design_collection;

import java.util.HashMap;

public class MyURLShortener {
    private static final String BASE_URL = "http://short.ly/";
    private static final String CHARSET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int BASE = CHARSET.length();

    private HashMap<String, String> urlMap; // Maps short URL to original URL
    private HashMap<String, String> shortUrlMap; // Maps original URL to short URL
    private int idCounter; // To generate unique IDs

    public MyURLShortener() {
        urlMap = new HashMap<>();
        shortUrlMap = new HashMap<>();
        idCounter = 0; // Start id counter
    }

    // Encodes a URL to a shortened URL
    public String encode(String longUrl) {
        if (shortUrlMap.containsKey(longUrl)) {
            return shortUrlMap.get(longUrl); // Return existing short URL if already encoded
        }

        // Generate a unique ID and convert it to a short string
        String shortUrl = BASE_URL + encodeId(idCounter);
        urlMap.put(shortUrl, longUrl); // Map short URL to original URL
        shortUrlMap.put(longUrl, shortUrl); // Map original URL to short URL
        idCounter++; // Increment ID counter

        return shortUrl; // Return the new short URL
    }

    // Decodes a shortened URL to its original URL
    public String decode(String shortUrl) {
        return urlMap.get(shortUrl); // Retrieve the original URL
    }

    // Converts an integer ID to a base-62 encoded string
    private String encodeId(int id) {
        StringBuilder shortUrl = new StringBuilder();
        while (id > 0) {
            shortUrl.append(CHARSET.charAt(id % BASE)); // Get character from CHARSET
            id /= BASE; // Reduce ID
        }
        // Pad the short URL with 'a' for consistent length (optional)
        while (shortUrl.length() < 6) {
            shortUrl.append('a');
        }
        return shortUrl.reverse().toString(); // Reverse to get the correct order
    }

    public static void main(String[] args) {
        MyURLShortener urlShortener = new MyURLShortener();

        String longUrl = "https://www.example.com/some/long/url/path";
        String shortUrl = urlShortener.encode(longUrl);
        System.out.println("Short URL: " + shortUrl); // Output: Short URL: http://short.ly/abc123

        String decodedUrl = urlShortener.decode(shortUrl);
        System.out.println("Decoded URL: " + decodedUrl); // Output: Decoded URL: https://www.example.com/some/long/url/path
    }
}
