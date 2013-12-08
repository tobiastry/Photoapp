package model;


public class Picture {

	private String thumbUrl;
	private String largeUrl;
        private String tag;
        private String id;
        private long unixDate;
        
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
    
     /**
     * @return the unixDate
     */
    public long getUnixDate() {
        return unixDate;
    }

    /**
     * @param unixDate the unixDate to set
     */
    public void setUnixDate(long unixDate) {
        this.unixDate = unixDate;
    }

}
