package org.frasers.flickr.services;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.RequestContext;
import com.flickr4java.flickr.auth.Auth;
import com.flickr4java.flickr.auth.Permission;
import com.flickr4java.flickr.photos.PhotosInterface;
import com.flickr4java.flickr.tags.TagRaw;
import com.flickr4java.flickr.tags.TagsInterface;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by sfraser on 8/4/14.
 */
@Controller
@RequestMapping("/photos")
public class Photos {

    @Value("${flickr.apiKey}")
    private String flickrApiKey;

    @Value("${flickr.apiKey.secret}")
    private String flickrApiKeySecret;

    @Value("${flickr.oauth.token}")
    private String flickrOauthToken;

    @Value("${flickr.oauth.secret}")
    private String flickrOauthSecret;

    public Photos() {
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody TagRaw getPhotos(@RequestParam(
            value="tags", required=false) String tags) throws FlickrException {

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

        final PhotosInterface photoInt = f.getPhotosInterface();
        final TagsInterface tagInt = f.getTagsInterface();

        TagRaw retTagRaw = new TagRaw();

        for (TagRaw tagRaw : tagInt.getListUserRaw(tags)) {
            retTagRaw = tagRaw;
            for( String sTag : tagRaw.getRaw() ) {
                System.out.println(sTag);
            }
        }

        return retTagRaw;
    }

}
