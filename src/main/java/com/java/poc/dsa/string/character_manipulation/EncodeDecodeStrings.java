package com.java.poc.dsa.string.character_manipulation;

import java.util.HashMap;
import java.util.Map;

/**
 * Leetcode 535. Encode and Decode TinyURL
 *
 * TinyURL is a URL shortening service where you enter a URL such as https://leetcode.com/problems/design-tinyurl and it returns a short URL such as http://tinyurl.com/4e9iAk. Design a class to encode a URL and decode a tiny URL.
 *
 * There is no restriction on how your encode/decode algorithm should work. You just need to ensure that a URL can be encoded to a tiny URL and the tiny URL can be decoded to the original URL.
 *
 * Implement the Solution class:
 *
 * Solution() Initializes the object of the system.
 * String encode(String longUrl) Returns a tiny URL for the given longUrl.
 * String decode(String shortUrl) Returns the original long URL for the given shortUrl. It is guaranteed that the given shortUrl was encoded by the same object.
 */
public class EncodeDecodeStrings {

    Map<String,String> urlMap = new HashMap<>();
    String host = "http://tinyurl.com/";

    // Encodes a URL to a shortened URL.
    public String encode(String longUrl) {
        String encoded = host + longUrl.hashCode();
        urlMap.put(encoded,longUrl);

        return encoded;
    }

    // Decodes a shortened URL to its original URL.
    public String decode(String shortUrl) {
        return urlMap.get(shortUrl);
    }

    public static void main(String[] args) {
        String url = "www.google.com/account";

        EncodeDecodeStrings obj = new EncodeDecodeStrings();
        String encodedStr = obj.encode(url);
        System.out.println(encodedStr);
        String decodedStr = obj.decode(encodedStr);
        System.out.println(decodedStr);
    }
}
