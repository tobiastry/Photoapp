package model;

public class Picture {

	private String thumbUrl;
	private String largeUrl;
        
        public Picture (String url, String thumb){
            this.largeUrl = url;
            this.thumbUrl = thumb;
        }

    /**
     * @return the thumbUrl
     */
    public String getThumbUrl() {
        return thumbUrl;
    }

    /**
     * @return the largeUrl
     */
    public String getLargeUrl() {
        return largeUrl;
    }

}
