package org.frasers.flickr.services;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.auth.Auth;
import com.flickr4java.flickr.auth.AuthInterface;
import com.flickr4java.flickr.auth.Permission;

import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Scanner;

/**
 * Simple class to get OAuth token and secret
 */
@Component
@EnableAutoConfiguration
public class CmdLineManualAuth implements CommandLineRunner {

    @Value("${flickr.apiKey}")
    private String flickrApiKey;

    @Value("${flickr.apiKey.secret}")
    private String flickrApiKeySecret;

    public void run(String... args) throws IOException, FlickrException {

        final Flickr flickr = new Flickr(flickrApiKey, flickrApiKeySecret, new REST());
        Flickr.debugStream = false;
        AuthInterface authInterface = flickr.getAuthInterface();

        Scanner scanner = new Scanner(System.in);

        Token token = authInterface.getRequestToken();
        System.out.println("token: " + token);

        String url = authInterface.getAuthorizationUrl(token, Permission.READ);
        System.out.println("Follow this URL to authorise yourself on Flickr");
        System.out.println(url);
        System.out.println("Paste in the token it gives you:");
        System.out.print(">>");

        String tokenKey = scanner.nextLine();
        scanner.close();

        Token requestToken = authInterface.getAccessToken(token, new Verifier(tokenKey));
        System.out.println("Authentication success");

        Auth auth = authInterface.checkToken(requestToken);

        // This token can be used until the user revokes it.
        System.out.println("Token: " + requestToken.getToken());
        System.out.println("Secret: " + requestToken.getSecret());
        System.out.println("nsid: " + auth.getUser().getId());
        System.out.println("Realname: " + auth.getUser().getRealName());
        System.out.println("Username: " + auth.getUser().getUsername());
        System.out.println("Permission: " + auth.getPermission().getType());
    }

    public static void main(String[] args) {
        SpringApplication.run(CmdLineManualAuth.class, args);
    }
}
