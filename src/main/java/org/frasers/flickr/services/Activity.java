package org.frasers.flickr.services;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.RequestContext;
import com.flickr4java.flickr.activity.ActivityInterface;
import com.flickr4java.flickr.activity.Event;
import com.flickr4java.flickr.activity.Item;
import com.flickr4java.flickr.activity.ItemList;
import com.flickr4java.flickr.auth.Auth;
import com.flickr4java.flickr.auth.Permission;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

/**
 * Created by sfraser on 8/4/14.
 */
@Controller
@RequestMapping("/activity")
public class Activity {

    @Value("${flickr.apiKey}")
    private String flickrApiKey;

    @Value("${flickr.apiKey.secret}")
    private String flickrApiKeySecret;

    @Value("${flickr.oauth.token}")
    private String flickrOauthToken;

    @Value("${flickr.oauth.secret}")
    private String flickrOauthSecret;

    public Activity() {
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody ActivityResponse getActivity() throws FlickrException {

        // should these be cached?
        final Flickr f = new Flickr(flickrApiKey, flickrApiKeySecret, new REST());
        final RequestContext requestContext = RequestContext.getRequestContext();
        Auth auth = new Auth();
        auth.setPermission(Permission.READ);
        auth.setToken(flickrOauthToken);
        auth.setTokenSecret(flickrOauthSecret);
        requestContext.setAuth(auth);
        Flickr.debugRequest = false;
        Flickr.debugStream = false;

        int total = 0;

        {
            final ActivityInterface iface = f.getActivityInterface();
            final ItemList<Item> list = iface.userComments(10, 0);
            int counter = 1;
            total += list.getTotal();

            for( Item item : list ) {
                System.out.println("Item " + (counter++) + "/" + list.size() + " type: " + item.getType());
                System.out.println("Item-id:       " + item.getId() + "\n");
                final ArrayList events = (ArrayList) item.getEvents();
                for (int i = 0; i < events.size(); i++) {
                    System.out.println("Event " + (i + 1) + "/" + events.size() + " of Item " + counter);
                    System.out.println("Event-type: " + ((Event) events.get(i)).getType());
                    System.out.println("User:       " + ((Event) events.get(i)).getUser());
                    System.out.println("Username:   " + ((Event) events.get(i)).getUsername());
                    System.out.println("Value:      " + ((Event) events.get(i)).getValue() + "\n");
                }
            }
        }

        {
            final ActivityInterface iface2 = f.getActivityInterface();
            final ItemList<Item> list = iface2.userPhotos(50, 0, "300d");
            int counter = 1;
            total += list.getTotal();

            for ( Item item : list ) {
                System.out.println("Item " + (counter++) + "/" + list.size() + " type: " + item.getType());
                System.out.println("Item-id:       " + item.getId() + "\n");
                final ArrayList events = (ArrayList) item.getEvents();
                for (int i = 0; i < events.size(); i++) {
                    System.out.println("Event " + (i + 1) + "/" + events.size() + " of Item " + counter);
                    System.out.println("Event-type: " + ((Event) events.get(i)).getType());
                    if (((Event) events.get(i)).getType().equals("note")) {
                        System.out.println("Note-id:    " + ((Event) events.get(i)).getId());
                    } else if (((Event) events.get(i)).getType().equals("comment")) {
                        System.out.println("Comment-id: " + ((Event) events.get(i)).getId());
                    }
                    System.out.println("User:       " + ((Event) events.get(i)).getUser());
                    System.out.println("Username:   " + ((Event) events.get(i)).getUsername());
                    System.out.println("Value:      " + ((Event) events.get(i)).getValue());
                    System.out.println("Dateadded:  " + ((Event) events.get(i)).getDateadded() + "\n");
                }
            }
        }

        return new ActivityResponse( Integer.toString(total) );
    }

}
