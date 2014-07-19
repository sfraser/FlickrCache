### Start of project to hack Squeezebox cloud services broken Flickr API calls. They broke when Flickr went SSL.

I am going to make a RESTful local server that uses my Flickr apikey to pull down images and cache local.
I will patch the SqueezeboxServer perl (barf) to then pull images from this instead of going up to the SB cloud.
I have a stubbed out POC working - see example perl in Notes.PL.

# See http://code.flickr.net/2014/04/30/flickr-api-going-ssl-only-on-june-27th-2014/
