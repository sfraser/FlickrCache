package org.frasers.flickr.services;

/**
 * Created by sfraser on 7/18/14.
 *
 * See https://github.com/Logitech/slimserver/blob/public/7.9/Slim/Plugin/Flickr/Plugin.pm#L105
 */
public class SlimserverScreensaverResponse {

    /**
     * HTTP URL of an image to load.
     */
    private String _imageURL;


    /**
     * Text to caption image with
     */
    private String _caption;

    public SlimserverScreensaverResponse(String _imageURL, String _caption) {
        this._imageURL = _imageURL;
        this._caption = _caption;
    }

    public String getImage() {
        return _imageURL;
    }

    public void setImage(String _image) {
        this._imageURL = _image;
    }

    public String getCaption() {
        return _caption;
    }

    public void setCaption(String _caption) {
        this._caption = _caption;
    }
}
