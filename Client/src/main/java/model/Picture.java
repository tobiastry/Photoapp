package model;

public class Picture {

	private String thumbUrl;
	private String largeUrl;
        private String tag;
        private String id;
        
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

    /**
     * @return the tag
     */
    public String getTag() {
        return tag;
    }

    /**
     * @param tag the tag to set
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

}
